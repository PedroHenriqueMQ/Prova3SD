package hospital.data.DataHospital.controller;

import hospital.data.DataHospital.DTO.HistoricoChamadaDTO;
import hospital.data.DataHospital.DTO.PacienteDetailedDTO;
import hospital.data.DataHospital.model.HistoricoChamada;
import hospital.data.DataHospital.model.Paciente;
import hospital.data.DataHospital.service.HistoricoChamadaService;
import hospital.data.DataHospital.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historico-chamada")
public class HistoricoChamadaController {
    HistoricoChamadaService historicoChamadaService;
    PacienteService pacienteService;

    @Autowired
    public HistoricoChamadaController(HistoricoChamadaService historicoChamadaService, PacienteService pacienteService) {
        this.historicoChamadaService = historicoChamadaService;
        this.pacienteService = pacienteService;
    }

    @GetMapping("/cpf={cpf}")
    public List<HistoricoChamadaDTO> readAllChamadasFromPaciente(@PathVariable("cpf") String cpf) {
        return historicoChamadaService.findAllByPacienteCpfWithoutId(cpf);
    }

    @GetMapping("/all")
    public List<HistoricoChamadaDTO> readAllChamadas() {
        return historicoChamadaService.findAllWithoutId();
    }

    @PutMapping("/cpf={cpf}/horario={horario}")
    public Optional<HistoricoChamadaDTO> updateChamada(
            @RequestBody HistoricoChamadaDTO historicoChamadaDTO,
            @PathVariable("cpf") String cpf,
            @PathVariable("horario") LocalDateTime horario) {

        Optional<Long> idPaciente = pacienteService.findIdByCpf(historicoChamadaDTO.getPacienteCpf());
        if (idPaciente.isEmpty()) return Optional.empty();

        Optional<PacienteDetailedDTO> pacienteDetailedDTO = pacienteService.findById(idPaciente.get());
        if (pacienteDetailedDTO.isEmpty()) return Optional.empty();

        Paciente paciente = new Paciente();
        paciente.setId(idPaciente.get());
        paciente.setCpf(pacienteDetailedDTO.get().getCpf());
        paciente.setNome(pacienteDetailedDTO.get().getNome());
        paciente.setCondicaoDescricao(pacienteDetailedDTO.get().getCondicaoDescricao());
        paciente.setPrioridade(pacienteDetailedDTO.get().getPrioridade());

        List<HistoricoChamada> historicoChamadaList = historicoChamadaService.findAllByPacienteCpfWithId(paciente.getCpf());
        Optional<Long> idHistoricoChamada = Optional.empty();

        for (HistoricoChamada historicoChamadaAtual : historicoChamadaList) {
            if (historicoChamadaAtual.getPaciente().getCpf().equals(cpf) &&
                    historicoChamadaAtual.getDataHorario().isEqual(horario)) {
                idHistoricoChamada = Optional.of(historicoChamadaAtual.getId());
                break;
            }
        }

        if (idHistoricoChamada.isEmpty()) return Optional.empty();

        HistoricoChamada historicoChamada = new HistoricoChamada();
        historicoChamada.setId(idHistoricoChamada.get());
        historicoChamada.setPaciente(paciente);
        historicoChamada.setDataHorario(historicoChamadaDTO.getDataHorario());

        if (historicoChamadaService.findById(historicoChamada.getId()).isEmpty()) {
            return Optional.empty();
        }

        if (historicoChamadaService.save(historicoChamada)) {
            return Optional.of(new HistoricoChamadaDTO(historicoChamada));
        } else {
            return Optional.empty();
        }
    }

    @PostMapping
    public Optional<HistoricoChamadaDTO> createChamada(@RequestBody HistoricoChamadaDTO historicoChamadaDTO) {
        HistoricoChamada historicoChamada = new HistoricoChamada();
        Optional<Long> idPaciente = pacienteService.findIdByCpf(historicoChamadaDTO.getPacienteCpf());
        if(idPaciente.isEmpty()) return Optional.empty();

        Optional<PacienteDetailedDTO> pacienteDetailedDTO = pacienteService.findById(idPaciente.get());
        if(pacienteDetailedDTO.isEmpty()) return Optional.empty();

        Paciente paciente = new Paciente();
        paciente.setId(idPaciente.get());
        paciente.setCpf(pacienteDetailedDTO.get().getCpf());
        paciente.setNome(pacienteDetailedDTO.get().getNome());
        paciente.setCondicaoDescricao(pacienteDetailedDTO.get().getCondicaoDescricao());
        paciente.setPrioridade(pacienteDetailedDTO.get().getPrioridade());

        historicoChamada.setId(idPaciente.get());
        historicoChamada.setPaciente(paciente);
        historicoChamada.setDataHorario(historicoChamadaDTO.getDataHorario());

        if(historicoChamadaService.save(historicoChamada))
            return Optional.of(new HistoricoChamadaDTO(historicoChamada));
        else return Optional.empty();
    }

    @DeleteMapping("/cpf={cpf}/horario={horario}")
    public Optional<HistoricoChamadaDTO> deleteChamada(@PathVariable("cpf") String cpf, @PathVariable("horario") LocalDateTime horario) {
        List<HistoricoChamada> historicoChamadaList = historicoChamadaService.findAllByPacienteCpfWithId(cpf);
        Optional<HistoricoChamadaDTO> historicoChamadaDTO = Optional.empty();

        for (HistoricoChamada historicoChamada : historicoChamadaList) {
            if(historicoChamada.getDataHorario().isEqual(horario)) {
                if (historicoChamadaService.deleteById(historicoChamada.getId()))
                    historicoChamadaDTO = Optional.of(new HistoricoChamadaDTO(historicoChamada));
            }
        }

        return historicoChamadaDTO;
    }
}

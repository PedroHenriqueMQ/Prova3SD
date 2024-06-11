package hospital.data.DataHospital.controller;

import hospital.data.DataHospital.DTO.PacienteDetailedDTO;
import hospital.data.DataHospital.DTO.PacienteListViewDTO;
import hospital.data.DataHospital.model.HistoricoChamada;
import hospital.data.DataHospital.model.Paciente;
import hospital.data.DataHospital.service.HistoricoChamadaService;
import hospital.data.DataHospital.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paciente")
public class PacienteController {
    PacienteService pacienteService;
    HistoricoChamadaService historicoChamadaService;

    @Autowired
    public PacienteController(PacienteService pacienteService, HistoricoChamadaService historicoChamadaService) {
        this.pacienteService = pacienteService;
        this.historicoChamadaService = historicoChamadaService;
    }

    @GetMapping("/cpf={cpf}")
    public Optional<PacienteDetailedDTO> readOnePaciente (@PathVariable("cpf") String cpf) {
        Optional<Long> pacienteId = pacienteService.findIdByCpf(cpf);
        Optional<PacienteDetailedDTO> pacienteDetailed = Optional.empty();

        if (pacienteId.isPresent()) pacienteDetailed = pacienteService.findById(pacienteId.get());
        return pacienteDetailed;
    }

    @GetMapping("/all")
    public List<PacienteListViewDTO> readAllPacientes () {
        return pacienteService.findAllWithoutId();
    }

    @PutMapping("/cpf={cpf}")
    public Optional<PacienteDetailedDTO> updatePaciente(
            @RequestBody PacienteDetailedDTO pacienteDetailedDTO,
            @PathVariable("cpf") String cpf) {

        Optional<Long> pacienteId = pacienteService.findIdByCpf(cpf);

        if (pacienteId.isPresent()) {
            Paciente paciente = new Paciente();
            paciente.setId(pacienteId.get());
            paciente.setCpf(pacienteDetailedDTO.getCpf());
            paciente.setNome(pacienteDetailedDTO.getNome());
            paciente.setCondicaoDescricao(pacienteDetailedDTO.getCondicaoDescricao());
            paciente.setPrioridade(pacienteDetailedDTO.getPrioridade());

            pacienteService.save(paciente);

            if (!cpf.equals(pacienteDetailedDTO.getCpf())) {
                List<HistoricoChamada> historicoChamadaList = historicoChamadaService.findAllByPacienteCpfWithId(cpf);

                for (HistoricoChamada historicoChamada : historicoChamadaList) {
                    historicoChamada.getPaciente().setCpf(pacienteDetailedDTO.getCpf());
                    historicoChamadaService.save(historicoChamada);
                }
            }

            return Optional.of(new PacienteDetailedDTO(paciente));
        } else return Optional.empty();
    }

    @PostMapping
    public Optional<PacienteDetailedDTO> createPaciente(@RequestBody Paciente paciente) {
        if (pacienteService.save(paciente)) return Optional.of(new PacienteDetailedDTO(paciente));
        else return Optional.empty();
    }

    @DeleteMapping("/cpf={cpf}")
    public Optional<PacienteDetailedDTO> deletePaciente(@PathVariable("cpf") String cpf) {
        Optional<Long> pacienteId = pacienteService.findIdByCpf(cpf);

        if(pacienteId.isPresent()) {
            PacienteDetailedDTO pacienteDetailedDTO = pacienteService.findById(pacienteId.get()).get();
            Paciente paciente = new Paciente();
            paciente.setId(pacienteId.get());
            paciente.setCpf(pacienteDetailedDTO.getCpf());
            paciente.setNome(pacienteDetailedDTO.getNome());
            paciente.setCondicaoDescricao(pacienteDetailedDTO.getCondicaoDescricao());
            paciente.setPrioridade(pacienteDetailedDTO.getPrioridade());

            List<HistoricoChamada> historicoChamadaList = historicoChamadaService.findAllByPacienteCpfWithId(paciente.getCpf());
            for(HistoricoChamada historicoChamada : historicoChamadaList) {
                historicoChamadaService.deleteById(historicoChamada.getId());
            }

            if (pacienteService.deleteById(pacienteId.get()))
                return Optional.of(pacienteDetailedDTO);
        }
        return Optional.empty();
    }
}

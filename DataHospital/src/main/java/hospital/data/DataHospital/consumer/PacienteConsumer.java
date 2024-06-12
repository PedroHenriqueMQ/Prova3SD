package hospital.data.DataHospital.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import hospital.data.DataHospital.DTO.PacienteDetailedDTO;
import hospital.data.DataHospital.DTO.PacienteListViewDTO;
import hospital.data.DataHospital.model.HistoricoChamada;
import hospital.data.DataHospital.model.Paciente;
import hospital.data.DataHospital.service.HistoricoChamadaService;
import hospital.data.DataHospital.service.PacienteService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class PacienteConsumer {
    private AmqpTemplate amqpTemplate;
    ObjectMapper mapper;
    PacienteService pacienteService;
    HistoricoChamadaService historicoChamadaService;

    @Autowired
    public PacienteConsumer(AmqpTemplate amqpTemplate,
                            ObjectMapper mapper,
                            PacienteService pacienteService,
                            HistoricoChamadaService historicoChamadaService) {
        this.amqpTemplate = amqpTemplate;
        this.mapper = mapper;
        this.pacienteService = pacienteService;
        this.historicoChamadaService = historicoChamadaService;
    }

    @RabbitListener(queues = {"read-paciente"})
    public void getPaciente(Message message) throws IOException {
        byte[] body = message.getBody();
        PacienteDetailedDTO pacienteDetailedDTO = mapper.readValue(body, PacienteDetailedDTO.class);

        Optional<Long> pacienteId = pacienteService.findIdByCpf(pacienteDetailedDTO.getCpf());
        Optional<PacienteDetailedDTO> pacienteDetailed = Optional.empty();

        if (pacienteId.isPresent()) {
            pacienteDetailed = pacienteService.findById(pacienteId.get());
            System.out.println("=================================");
            System.out.println("Recuperar paciente pelo cpf:");
            System.out.println("Nome do paciente: " + pacienteDetailed.get().getNome());
            System.out.println("Cpf do paciente: " + pacienteDetailed.get().getCpf());
            System.out.println("Descrição da condição do paciente: " + pacienteDetailed.get().getCondicaoDescricao());
            System.out.println("Prioridade do paciente: " + pacienteDetailed.get().getPrioridade());
            System.out.println("=================================");
        }
        else System.out.println("Paciente não encontrado.");
    }

    @RabbitListener(queues = {"read-all-paciente"})
    public void getAllPaciente(Message message) {
        List<PacienteListViewDTO> listaPacientes = pacienteService.findAllWithoutId();

        System.out.println("=================================");
        System.out.println("Recuperar todos os pacientes:");
        for (PacienteListViewDTO pacienteListViewDTO : listaPacientes) {
            System.out.println("\nNome do paciente: " + pacienteListViewDTO.getNome());
            System.out.println("Cpf do paciente: " + pacienteListViewDTO.getCpf());
            System.out.println("Prioridade do paciente: " + pacienteListViewDTO.getPrioridade());
        }
        System.out.println("=================================");
    }

    @RabbitListener(queues = {"update-paciente"})
    public void updatePaciente(Message message) throws IOException {
        byte[] body = message.getBody();
        PacienteDetailedDTO pacienteDetailedDTO = mapper.readValue(body, PacienteDetailedDTO.class);

        Optional<Long> pacienteId = pacienteService.findIdByCpf(pacienteDetailedDTO.getCpf());

        if (pacienteId.isPresent()) {
            Paciente paciente = new Paciente();
            paciente.setId(pacienteId.get());
            paciente.setCpf(pacienteDetailedDTO.getCpf());
            paciente.setNome(pacienteDetailedDTO.getNome());
            paciente.setCondicaoDescricao(pacienteDetailedDTO.getCondicaoDescricao());
            paciente.setPrioridade(pacienteDetailedDTO.getPrioridade());

            System.out.println("=================================");
            if (pacienteService.save(paciente)) {
                System.out.println("Paciente " + paciente.getNome() + " atualizado(a) com sucesso!");
            } else System.out.println("Paciente NÃO foi atualizado!");
            System.out.println("=================================");
        }
    }

    @RabbitListener(queues = {"create-paciente"})
    public void createPaciente(Message message) throws IOException {
        byte[] body = message.getBody();
        Paciente paciente = mapper.readValue(body, Paciente.class);

        System.out.println("=================================");
        if (pacienteService.save(paciente)) {
            System.out.println("Paciente " + paciente.getNome() + " salvo(a) com sucesso!");
        } else System.out.println("Paciente NÃO foi salvo!");
        System.out.println("=================================");
    }

    @RabbitListener(queues = {"delete-paciente"})
    public void deletePaciente(Message message) throws IOException {
        byte[] body = message.getBody();
        PacienteDetailedDTO pacienteDetailed = mapper.readValue(body, PacienteDetailedDTO.class);

        Optional<Long> pacienteId = pacienteService.findIdByCpf(pacienteDetailed.getCpf());

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

            System.out.println("=================================");
            if (pacienteService.deleteById(pacienteId.get())) {
                System.out.println("Paciente " + paciente.getNome() + " deletado(a) com sucesso e todo seu histórico também!");
            } else System.out.println("Paciente NÃO foi salvo!");
            System.out.println("=================================");
        }
    }
}

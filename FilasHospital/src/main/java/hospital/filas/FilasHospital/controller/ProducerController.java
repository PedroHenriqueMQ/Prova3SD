package hospital.filas.FilasHospital.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hospital.filas.FilasHospital.DTO.PacienteDetailedDTO;
import hospital.filas.FilasHospital.service.PacienteRabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rabbitmq")
public class ProducerController {
    PacienteRabbitMQService pacienteRabbitMQService;

    @Autowired
    public ProducerController(PacienteRabbitMQService pacienteRabbitMQService) {
        this.pacienteRabbitMQService = pacienteRabbitMQService;
    }

    @GetMapping
    public void readOnePaciente(@RequestBody PacienteDetailedDTO pacienteDetailedDTO) throws JsonProcessingException {
        pacienteRabbitMQService.readPaciente(pacienteDetailedDTO);
    }

    @GetMapping("/all")
    public void readAllPacientes() {
        pacienteRabbitMQService.readAllPacientes();
    }

    @PutMapping
    public void updatePaciente(@RequestBody PacienteDetailedDTO pacienteDetailedDTO) throws JsonProcessingException {
        pacienteRabbitMQService.updatePaciente(pacienteDetailedDTO);
    }

    @PostMapping
    public void createPaciente(@RequestBody PacienteDetailedDTO pacienteDetailedDTO) throws JsonProcessingException {
        pacienteRabbitMQService.createPaciente(pacienteDetailedDTO);
    }

    @DeleteMapping
    public void deletePaciente(@RequestBody PacienteDetailedDTO pacienteDetailedDTO) throws JsonProcessingException {
        pacienteRabbitMQService.deletePaciente(pacienteDetailedDTO);
    }
}

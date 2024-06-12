package hospital.filas.FilasHospital.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hospital.filas.FilasHospital.DTO.PacienteDetailedDTO;
import hospital.filas.FilasHospital.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/rabbitmq")
public class ProducerController {
    RabbitMQService rabbitMQService;

    @Autowired
    public ProducerController(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @GetMapping
    public void readOnePaciente(@RequestBody PacienteDetailedDTO pacienteDetailedDTO) throws JsonProcessingException {
        rabbitMQService.readPaciente(pacienteDetailedDTO);
    }

    @GetMapping("/all")
    public void readAllPacientes() {
        rabbitMQService.readAllPacientes();
    }

    @PutMapping
    public void updatePaciente(@RequestBody PacienteDetailedDTO pacienteDetailedDTO) throws JsonProcessingException {
        rabbitMQService.updatePaciente(pacienteDetailedDTO);
    }

    @PostMapping
    public void createPaciente(@RequestBody PacienteDetailedDTO pacienteDetailedDTO) throws JsonProcessingException {
        rabbitMQService.createPaciente(pacienteDetailedDTO);
    }

    @DeleteMapping
    public void deletePaciente(@RequestBody PacienteDetailedDTO pacienteDetailedDTO) throws JsonProcessingException {
        rabbitMQService.deletePaciente(pacienteDetailedDTO);
    }
}

package hospital.filas.FilasHospital.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hospital.filas.FilasHospital.DTO.PacienteDetailedDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class RabbitMQService {
    private AmqpTemplate amqpTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public RabbitMQService(AmqpTemplate amqpTemplate, ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
    }

    public void readPaciente(PacienteDetailedDTO pacienteDetailedDTO) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(pacienteDetailedDTO);
        MessageProperties properties = new MessageProperties();
        properties.setPriority(pacienteDetailedDTO.getPrioridade().ordinal());
        Message message = new Message(json.getBytes(), properties);
        amqpTemplate.convertAndSend("read-paciente", message);
    }

    public void readAllPacientes() {
        String json = "";
        MessageProperties properties = new MessageProperties();
        Message message = new Message(json.getBytes(), properties);
        amqpTemplate.convertAndSend("read-all-paciente", message);
    }

    public void updatePaciente(PacienteDetailedDTO pacienteDetailedDTO) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(pacienteDetailedDTO);
        MessageProperties properties = new MessageProperties();
        properties.setPriority(pacienteDetailedDTO.getPrioridade().ordinal());
        Message message = new Message(json.getBytes(), properties);
        amqpTemplate.convertAndSend("update-paciente", message);
    }

    public void createPaciente(PacienteDetailedDTO pacienteDetailedDTO) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(pacienteDetailedDTO);
        MessageProperties properties = new MessageProperties();
        properties.setPriority(pacienteDetailedDTO.getPrioridade().ordinal());
        Message message = new Message(json.getBytes(), properties);
        amqpTemplate.convertAndSend("create-paciente", message);
    }

    public void deletePaciente(PacienteDetailedDTO pacienteDetailedDTO) throws JsonProcessingException {
        String json = objectMapper.writeValueAsString(pacienteDetailedDTO);
        MessageProperties properties = new MessageProperties();
        properties.setPriority(pacienteDetailedDTO.getPrioridade().ordinal());
        Message message = new Message(json.getBytes(), properties);
        amqpTemplate.convertAndSend("delete-paciente", message);
    }
}

package hospital.data.DataHospital.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import hospital.data.DataHospital.service.HistoricoChamadaService;
import hospital.data.DataHospital.service.PacienteService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HistoricoChamadaConsumer {
    private AmqpTemplate amqpTemplate;
    ObjectMapper mapper;
    PacienteService pacienteService;
    HistoricoChamadaService historicoChamadaService;

    @Autowired
    public HistoricoChamadaConsumer(AmqpTemplate amqpTemplate,
                            ObjectMapper mapper,
                            PacienteService pacienteService,
                            HistoricoChamadaService historicoChamadaService) {
        this.amqpTemplate = amqpTemplate;
        this.mapper = mapper;
        this.pacienteService = pacienteService;
        this.historicoChamadaService = historicoChamadaService;
    }


}

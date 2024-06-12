package hospital.filas.FilasHospital.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfiguration.class);

    @Bean
    public Queue readPaciente() {
        logger.info("Creating queue: read-paciente");
        return QueueBuilder.durable("read-paciente").maxPriority(4).build();
    }

    @Bean
    public Queue readAllPaciente() {
        logger.info("Creating queue: read-all-paciente");
        return QueueBuilder.durable("read-all-paciente").maxPriority(4).build();
    }

    @Bean
    public Queue updatePaciente() {
        logger.info("Creating queue: update-paciente");
        return QueueBuilder.durable("update-paciente").maxPriority(4).build();
    }

    @Bean
    public Queue createPaciente() {
        logger.info("Creating queue: create-paciente");
        return QueueBuilder.durable("create-paciente").maxPriority(4).build();
    }

    @Bean
    public Queue deletePaciente() {
        logger.info("Creating queue: delete-paciente");
        return QueueBuilder.durable("delete-paciente").maxPriority(4).build();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}

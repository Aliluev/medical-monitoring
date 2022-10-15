package liga.medical.medicalmonitoring.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.medical.dto.Message;
import liga.medical.medicalmonitoring.core.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SenderService {
    private final AmqpTemplate amqpTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public SenderService(AmqpTemplate amqpTemplate, ObjectMapper objectMapper) {
        this.amqpTemplate = amqpTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(Message message, String queue) throws JsonProcessingException {
        String messageFromJson = objectMapper.writeValueAsString(message);
        amqpTemplate.convertAndSend(queue, messageFromJson);
        System.out.println("Сообщение"+ messageFromJson + "отправлено в очередь " + queue);
    }


    public void sendError(String message) {
        amqpTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME_ERROR, message);
        System.out.println("Сообщение об ошибке " + message + " отправлено в очередь " + RabbitMQConfig.QUEUE_NAME_ERROR);
    }

}

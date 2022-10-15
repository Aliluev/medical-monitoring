package liga.medical.medicalmonitoring.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import liga.medical.dto.Message;
import liga.medical.dto.MessageType;
import liga.medical.medicalmonitoring.core.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageRouterService {

    private final ObjectMapper objectMapper;

    private SenderService senderService;

    @Autowired
    public MessageRouterService(ObjectMapper objectMapper, SenderService senderService) {
        this.objectMapper = objectMapper;
        this.senderService = senderService;
    }

    public void routeMessage(String message) {
        try {
            Message messageDto = objectMapper.readValue(message, Message.class);
            MessageType messageType = messageDto.getMessageType();

            switch (messageType) {
                case DAILY:
                    senderService.sendMessage(messageDto, RabbitMQConfig.QUEUE_NAME_DAILY);
                    break;
                case ALERT:
                    senderService.sendMessage(messageDto, RabbitMQConfig.QUEUE_NAME_ALERT);
                    break;
                case ERROR:
                    senderService.sendMessage(messageDto, RabbitMQConfig.QUEUE_NAME_ERROR);
                    break;
                default:
                    System.out.println("Can't routing message " + messageDto);
            }
        } catch (Exception e) {
            senderService.sendError(e.getMessage());
        }
    }
}

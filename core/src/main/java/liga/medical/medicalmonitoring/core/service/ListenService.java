package liga.medical.medicalmonitoring.core.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import liga.medical.medicalmonitoring.core.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListenService {

    private MessageRouterService messageRouterService;

    @Autowired
    public ListenService(MessageRouterService messageRouterService) {
        this.messageRouterService = messageRouterService;
    }

    @RabbitListener(queues = RabbitMQConfig.ROUTER_QUEUE_NAME)
    public void listenRouterQueue(String message) throws JsonProcessingException {
        messageRouterService.routeMessage(message);
    }

}

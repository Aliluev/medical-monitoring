package liga.medical.medicalmonitoring.core.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String HOST = "localhost";
    public static final String ROUTER_QUEUE_NAME = "common-monitoring";
    public static final String QUEUE_NAME_DAILY = "daily_queue";
    public static final String QUEUE_NAME_ALERT = "alert_queue";
    public static final String QUEUE_NAME_ERROR = "error_queue";


    @Bean
    public ConnectionFactory connectionFactory(){
        return new CachingConnectionFactory(HOST);
    }

    @Bean
    public AmqpAdmin amqpAdmin (){
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue getRouterQueue(){
        return new Queue(ROUTER_QUEUE_NAME);
    }
    @Bean
    public Queue getDailyQueue(){
        return new Queue(QUEUE_NAME_DAILY);
    }
    @Bean
    public Queue getAlertQueue(){
        return new Queue(QUEUE_NAME_ALERT);
    }
    @Bean
    public Queue getErrorQueue(){
        return new Queue(QUEUE_NAME_ERROR);
    }

}

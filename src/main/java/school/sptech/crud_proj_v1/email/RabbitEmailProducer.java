package school.sptech.crud_proj_v1.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.config.RabbitMQConfig;
import school.sptech.crud_proj_v1.dto.RabbitMQ.EmailMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitEmailProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendEmail(EmailMessage emailMessage) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.EMAIL_ROUTING_KEY,
                    emailMessage
            );
            log.info("Mensagem publicada no RabbitMQ com sucesso. brinksExchange: {}, RoutingKey: {}, EmailMessage: {}",
                    RabbitMQConfig.EXCHANGE, RabbitMQConfig.IMPORT_ROUTING_KEY, emailMessage);

        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar mensagem de email para RabbitMQ", e);
        }
    }

}

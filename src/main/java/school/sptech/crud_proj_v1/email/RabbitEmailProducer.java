package school.sptech.crud_proj_v1.email;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.config.RabbitMQConfig;
import school.sptech.crud_proj_v1.dto.RabbitMQ.EmailMessage;

@Service
@RequiredArgsConstructor
public class RabbitEmailProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendEmail(EmailMessage emailMessage) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.EMAIL_ROUTING_KEY,
                    emailMessage
            );
        } catch (Exception e) {
            throw new RuntimeException("Falha ao enviar mensagem de email para RabbitMQ", e);
        }
    }

}

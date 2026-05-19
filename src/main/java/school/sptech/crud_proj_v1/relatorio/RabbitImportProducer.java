package school.sptech.crud_proj_v1.relatorio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.config.RabbitMQConfig;
import school.sptech.crud_proj_v1.dto.RabbitMQ.JobMessage;

import java.util.UUID;

@Component
@Slf4j
public class RabbitImportProducer {
    private final RabbitTemplate rabbitTemplate;

    public RabbitImportProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String publish(String fileKey, Integer ano, String mes){
        String jobId = UUID.randomUUID().toString();

        log.debug("Gerando novo JobId: {} para FileKey: {}", jobId, fileKey);

        JobMessage message = new JobMessage(ano, mes, jobId, fileKey);

        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.IMPORT_ROUTING_KEY,
                    message
            );
            log.info("Mensagem publicada no RabbitMQ com sucesso. brinksExchange: {}, RoutingKey: {}, JobId: {}",
                    RabbitMQConfig.EXCHANGE, RabbitMQConfig.IMPORT_ROUTING_KEY, jobId);
        } catch (Exception e) {
            log.error("Erro ao publicar mensagem no RabbitMQ para JobId: {}", jobId, e);
            throw new RuntimeException("Falha ao publicar mensagem no RabbitMQ", e);
        }

        return jobId;
    }
}

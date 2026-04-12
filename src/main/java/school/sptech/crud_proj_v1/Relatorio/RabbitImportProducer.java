package school.sptech.crud_proj_v1.Relatorio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.config.RabbitMQConfig;

import java.util.UUID;

@Component
@Slf4j
public class RabbitImportProducer {
    private final RabbitTemplate rabbitTemplate;

    public RabbitImportProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public String publish(String fileKey){
        String jobId = UUID.randomUUID().toString();

        String path = fileKey.contains("/") ? fileKey.substring(0, fileKey.lastIndexOf("/") + 1) : "";

        log.debug("Gerando novo JobId: {} para FileKey: {} com o path: {}", jobId, fileKey, path);

        String compositeJobId = path + "__" + jobId;

        String jsonPayload = String.format("{\"jobId\":\"%s\", \"fileKey\":\"%s\"}", compositeJobId, fileKey);

        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY,
                    jsonPayload
            );
            log.info("Mensagem publicada no RabbitMQ com sucesso. Exchange: {}, RoutingKey: {}, JobId: {}",
                    RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, compositeJobId);
        } catch (Exception e) {
            log.error("Erro ao publicar mensagem no RabbitMQ para JobId: {}", compositeJobId, e);
            throw new RuntimeException("Falha ao publicar mensagem no RabbitMQ", e);
        }

        return compositeJobId;
    }
}

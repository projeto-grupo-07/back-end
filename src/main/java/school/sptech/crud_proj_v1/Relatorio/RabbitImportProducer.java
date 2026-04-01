package school.sptech.crud_proj_v1.Relatorio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.config.RabbitMQconfig;
import school.sptech.crud_proj_v1.dto.RabbitMQ.JobMessage;

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
        JobMessage payload = new JobMessage(jobId, fileKey);

        try{
            rabbitTemplate.convertAndSend(RabbitMQconfig.EXCHANGE, RabbitMQconfig.ROUTING_KEY, payload);
            log.info("Mensagem publicada no RabbitMQ com sucesso. Exchange: {}, RoutingKey: {}, JobId: {}",
                    RabbitMQconfig.EXCHANGE, RabbitMQconfig.ROUTING_KEY, jobId, fileKey);
        }catch (Exception e){
            log.error("Erro ao publicar mensagem no RabbitMQ. jobId={}, fileKey={}", jobId, fileKey, e);
            throw new RuntimeException("Falha ao publicar mensagem", e);
        }
        return jobId;
    }
}

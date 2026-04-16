package school.sptech.crud_proj_v1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.crud_proj_v1.Relatorio.RabbitImportProducer;
import school.sptech.crud_proj_v1.dto.RabbitMQ.JobResponse;

@RestController
@RequestMapping("/relatorio")
@Slf4j
public class RelatorioController {
    private final RabbitImportProducer producer;

    public RelatorioController(RabbitImportProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/{*fileKey}")
    public ResponseEntity<JobResponse> emitirRelatorio(@PathVariable String fileKey) {
        String normalizedKey = fileKey.startsWith("/") ? fileKey.substring(1) : fileKey;
        log.info("Recebendo requisição de importação. FileKey: {}", normalizedKey);
        try {
            String jobId = producer.publish(normalizedKey);
            log.info("Importação enfileirada com sucesso. JobId: {}, FileKey: {}", jobId, normalizedKey);
            return ResponseEntity.accepted().body(new JobResponse(jobId));
        } catch (Exception e) {
            log.error("Erro ao enfileirar importação para FileKey: {}", normalizedKey, e);
            throw e;
        }
    }
}

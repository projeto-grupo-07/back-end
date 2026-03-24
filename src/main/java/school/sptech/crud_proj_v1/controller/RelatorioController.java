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

    @PostMapping("/{fileKey}")
    public ResponseEntity<JobResponse> emitirRelatorio(@PathVariable String fileKey) {
        log.info("Recebendo pedido para enfileirar arquivo: {}", fileKey);
        String jobId = producer.publish(fileKey);
        log.info("Job enfileirado: jobId={}, fileKey={}", jobId, fileKey);
        return ResponseEntity.accepted().body(new JobResponse(jobId));
    }
}

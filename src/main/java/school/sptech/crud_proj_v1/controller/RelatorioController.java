package school.sptech.crud_proj_v1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import school.sptech.crud_proj_v1.relatorio.RabbitImportProducer;
import school.sptech.crud_proj_v1.dto.RabbitMQ.JobResponse;

@RestController
@RequestMapping("/relatorio")
@Slf4j
public class RelatorioController {
    private final RabbitImportProducer producer;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${API_GATEWAY_URL}")
    private String apiGatewayURL;

    public RelatorioController(RabbitImportProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/{*fileKey}")
    public ResponseEntity<JobResponse> gerarRelatorio(@PathVariable String fileKey) {

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

    public ResponseEntity<String> emitirRelatorio(Integer ano, Integer mes) {
        String url = String.format("%s/relatorio?ano=%d&mes=%d", apiGatewayURL, ano, mes);
        log.info("Enviando requisição para API externa. URL: {}", url);
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            log.info("Resposta recebida da API externa. Status: {}, Body: {}", response.getStatusCode(), response.getBody());
            return response;
        } catch (Exception e) {
            log.error("Erro ao chamar API externa para emissão de relatório. URL: {}", url, e);
            throw new RuntimeException("Falha ao emitir relatório", e);
        }
    }
}

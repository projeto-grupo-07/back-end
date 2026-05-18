package school.sptech.crud_proj_v1.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import school.sptech.crud_proj_v1.dto.Relatorio.RelatorioRequest;
import school.sptech.crud_proj_v1.relatorio.RabbitImportProducer;
import school.sptech.crud_proj_v1.dto.RabbitMQ.JobResponse;

import java.util.Map;

@RestController
@RequestMapping("/relatorio")
@Slf4j
public class RelatorioController {
    private final RabbitImportProducer producer;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${api.gateway.url}")
    private String apiGatewayURL;

    public RelatorioController(RabbitImportProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/enfileirar/{*fileKey}")
    public ResponseEntity<JobResponse> enfileirarRelatorio(@PathVariable String fileKey) {

        String normalizedKey = fileKey.startsWith("/") ? fileKey.substring(1) : fileKey;
        log.info("Recebendo requisição de gerar relatorio de importação. FileKey: {}", normalizedKey);
        try {
            String jobId = producer.publish(normalizedKey);
            log.info("Importação enfileirada com sucesso. JobId: {}, FileKey: {}", jobId, normalizedKey);
            return ResponseEntity.accepted().body(new JobResponse(jobId));
        } catch (Exception e) {
            log.error("Erro ao enfileirar importação para FileKey: {}", normalizedKey, e);
            throw e;
        }
    }

    @PostMapping("/gerar")
    public ResponseEntity<String> solicitarRelatorioNaLambdaAWS(@RequestBody RelatorioRequest req) {
        Integer mes = req.mes();
        Integer ano = req.ano();
        String url = String.format("%s/solicitar-relatorio", apiGatewayURL);
        log.info("Requisição para emitir relatorio de ano: {} e mes: {}. Utilizado a url: {}", ano, mes, url);
        try {
            Map<String,Object> payload = Map.of("ano", ano, "mes", mes);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String,Object>> request = new HttpEntity<>(payload, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (Exception e) {
            log.error("Erro ao chamar API externa", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao emitir relatório");
        }
    }
}

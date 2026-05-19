package school.sptech.crud_proj_v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import school.sptech.crud_proj_v1.dto.Relatorio.RelatorioRequest;
import school.sptech.crud_proj_v1.dto.Relatorio.RelatorioResponse;
import school.sptech.crud_proj_v1.relatorio.RabbitImportProducer;

import java.util.Map;

@RestController
@RequestMapping("/relatorio")
@Slf4j
public class RelatorioController {
    private final RabbitImportProducer producer;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${API_GATEWAY_URL}")
    private String apiGatewayURL;

    public RelatorioController(RabbitImportProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/gerar")
    public ResponseEntity<String> solicitarRelatorioNaLambdaAWS(@RequestBody RelatorioRequest req) {
        Integer mes = req.mes();
        Integer ano = req.ano();
        String mesFormatado = String.format("%02d", mes);
        String url = String.format("%s/solicitar-relatorio", apiGatewayURL);
        log.info("Requisição para emitir relatorio de ano: {} e mes: {}. Utilizado a url: {}", ano, mes, url);

        try {
            Map<String,Object> payload = Map.of("ano", ano, "mes", mes);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String,Object>> request = new HttpEntity<>(payload, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            log.info("Resposta recebida da API externa. Status: {}, Body: {}", response.getStatusCode(), response.getBody());

            String responseBody = response.getBody();
            RelatorioResponse relatorio = objectMapper.readValue(responseBody, RelatorioResponse.class);
            String fileKey = relatorio.arquivos().getFirst().replace(".csv", "_trusted.csv").replace("importacao", "trusted");
            log.info("Arquivo: {}", fileKey);
            Thread.sleep(5000);
            String publishedJobId = producer.publish(fileKey, ano, mesFormatado);
            log.info("FileKey recebido: {}. Publicado no RabbitMQ com jobId: {}", fileKey, publishedJobId);

            String result = String.format("Enfileirado com sucesso. jobId=%s, fileKey=%s", publishedJobId, fileKey);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
        } catch (Exception e) {
            log.error("Erro ao chamar API externa para emissão de relatório. URL: {}", url, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Falha ao emitir relatório");
        }
    }
}
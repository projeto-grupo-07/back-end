package school.sptech.crud_proj_v1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import school.sptech.crud_proj_v1.dto.Relatorio.DownloadRequestDto;
import school.sptech.crud_proj_v1.dto.Relatorio.RelatorioRequest;
import school.sptech.crud_proj_v1.dto.Relatorio.RelatorioResponse;
import school.sptech.crud_proj_v1.relatorio.RabbitImportProducer;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/relatorio")
@Slf4j
public class RelatorioController {
    private final RabbitImportProducer producer;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${api.gateway.url}")
    private String apiGatewayURL;

    @Value("${MS_REPORT_URL}")
    private String msReportUrl;

    public RelatorioController(RabbitImportProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/gerar")
    public ResponseEntity<byte[]> solicitarRelatorioNaLambdaAWS(@RequestBody RelatorioRequest req) {
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

            String publishedJobId = producer.publish(fileKey, ano, mesFormatado);
            log.info("FileKey recebido: {}. Publicado no RabbitMQ com jobId: {}", fileKey, publishedJobId);

            Thread.sleep(3000);
            ResponseEntity<byte[]> pdfResponse = baixarRelatorioMsReport(new DownloadRequestDto(mes, ano, publishedJobId));

            HttpHeaders pdfHeaders = new HttpHeaders();
            pdfHeaders.setContentType(MediaType.APPLICATION_PDF);
            pdfHeaders.setContentDispositionFormData("attachment", "relatorio-" + ano + "-" + mesFormatado + ".pdf");

            return ResponseEntity.ok()
                    .headers(pdfHeaders)
                    .body(pdfResponse.getBody());
        } catch (Exception e) {
            log.error("Erro ao processar relatório", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/baixar")
    public ResponseEntity<byte[]> baixarRelatorioMsReport(@RequestBody DownloadRequestDto downloadRequestDto) {
        Integer ano = downloadRequestDto.ano();
        Integer mes = downloadRequestDto.mes();
        String jobId = downloadRequestDto.jobId();

        if (jobId == null || jobId.isBlank() || ano == null || mes == null || mes < 1 || mes > 12 || ano <= 0) {
            log.warn("Parametros invalidos para download. Ano: {}, Mes: {}, JobId: {}", ano, mes, jobId);
            return ResponseEntity.badRequest().build();
        }

        try {
            String url = UriComponentsBuilder.fromHttpUrl(msReportUrl)
                    .path("/import/report")
                    .queryParam("ano", ano)
                    .queryParam("mes", mes)
                    .queryParam("jobId", jobId)
                    .toUriString();

            log.info("Enviando requisição GET para MS Report. URL: {}, Ano: {}, Mes: {}, JobId: {}", url, ano, mes, jobId);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(List.of(MediaType.APPLICATION_PDF));

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<byte[]> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    byte[].class
            );

            return ResponseEntity.status(response.getStatusCode())
                    .headers(response.getHeaders())
                    .body(response.getBody());

        } catch (Exception e) {
            log.error("Erro ao chamar MS Report. Ano: {}, Mes: {}, JobId: {}", ano, mes, jobId, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
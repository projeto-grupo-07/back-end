package school.sptech.crud_proj_v1.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeminiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(60000);
        this.restTemplate = new RestTemplate(factory);
    }

    public Map<String, String> gerarTextoCampanha(String promptUsuario) {
        String promptCompleto = "Você é um copywriter especialista em marketing. " +
                "Escreva um e-mail persuasivo, curto e direto com base neste tema: " +
                promptUsuario +
                ". Não use saudações genéricas como 'Olá [Nome]'. " +
                "Regra crítica: Retorne APENAS um objeto JSON válido, sem formatação markdown, com as exatas chaves 'assunto' e 'corpo'. Exemplo: {\"assunto\": \"título aqui\", \"corpo\": \"texto aqui\"}";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("contents", List.of(
                Map.of("parts", List.of(
                        Map.of("text", promptCompleto)
                ))
        ));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            String urlComChave = apiUrl + "?key=" + apiKey;
            ResponseEntity<String> response = restTemplate.postForEntity(urlComChave, entity, String.class);

            JsonNode rootNode = objectMapper.readTree(response.getBody());
            String textoGerado = rootNode
                    .path("candidates").get(0)
                    .path("content")
                    .path("parts").get(0)
                    .path("text").asText();

            textoGerado = textoGerado.replace("```json", "").replace("```", "").trim();

            return objectMapper.readValue(textoGerado, HashMap.class);

        } catch (Exception e) {
            log.error("Erro ao chamar API do Gemini: ", e);
            throw new RuntimeException("Falha ao gerar texto com IA. Tente novamente mais tarde.");
        }
    }
}
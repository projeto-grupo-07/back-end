package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Campanha.CampanhaRequestDto;
import school.sptech.crud_proj_v1.dto.Campanha.CampanhaResponseDto;
import school.sptech.crud_proj_v1.dto.paginacao.PaginaOffsetCampanhaResposta;
import school.sptech.crud_proj_v1.entity.Cliente;
import school.sptech.crud_proj_v1.service.CampanhaService;
import school.sptech.crud_proj_v1.service.GeminiService;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;

@Tag(name = "Campanha")
@RestController
@RequestMapping("/campanhas")
@Slf4j
@RequiredArgsConstructor
public class CampanhaController {

    private final CampanhaService campanhaService;
    private final GeminiService geminiService;

    @GetMapping
    public ResponseEntity<List<CampanhaResponseDto>> listarCampanhas() {
        List<CampanhaResponseDto> campanhas = campanhaService.listarCampanhas();
        return ResponseEntity.ok(campanhas);
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criarCampanha(@RequestBody CampanhaRequestDto campanhaRequestDTO) {
        try {
            CampanhaResponseDto novaCampanha = campanhaService.criarCampanha(campanhaRequestDTO);
            return ResponseEntity.status(201).body(novaCampanha); // 201 Created é mais semântico para criações

        } catch (IllegalArgumentException e) {
            // Captura o nosso erro específico de "Nenhum cliente encontrado"
            log.warn("Validação de campanha: {}", e.getMessage());

            // AGORA SIM: Envia o texto da exceção para o React ler!
            return ResponseEntity.status(400).body(e.getMessage());

        } catch (RuntimeException e) {
            log.error("Erro interno ao criar campanha: {}", e.getMessage());
            return ResponseEntity.status(500).body("Erro interno ao processar a campanha.");
        }
    }

    @PostMapping("/{id}/iniciar")
    public ResponseEntity<Void> iniciarCampanha(@PathVariable Integer id) {
        try {
            campanhaService.iniciarCampanha(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Erro ao iniciar campanha: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCampanha(@PathVariable Integer id) {
        campanhaService.deletarCampanha(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CampanhaResponseDto> buscarCampanhaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(campanhaService.buscarCampanhaPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CampanhaResponseDto> atualizarCampanha(
            @PathVariable Integer id,
            @RequestBody CampanhaRequestDto dto) {
        return ResponseEntity.ok(campanhaService.atualizarCampanha(id, dto));
    }

    @GetMapping("/{id}/clientes")
    public ResponseEntity<List<Cliente>> listarClientesDaCampanha(@PathVariable Integer id) {
        return ResponseEntity.ok(campanhaService.listarClientesDaCampanha(id));
    }

    @PostMapping("/{id}/clientes/{clienteId}")
    public ResponseEntity<Void> adicionarCliente(
            @PathVariable Integer id,
            @PathVariable Integer clienteId) {
        campanhaService.adicionarCliente(id, clienteId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/clientes/{clienteId}")
    public ResponseEntity<Void> removerCliente(
            @PathVariable Integer id,
            @PathVariable Integer clienteId) {
        campanhaService.removerCliente(id, clienteId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<CampanhaResponseDto>> filtrarCampanhas(
            @RequestParam(required = false) String assunto,
            @RequestParam(required = false) String status) {

        List<CampanhaResponseDto> campanhas = campanhaService.filtrarCampanhas(assunto, status);

        if (campanhas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(campanhas);
    }

    // Não esqueça de injetar o GeminiService no topo do Controller junto com o CampanhaService!
    // private final GeminiService geminiService;

    @PostMapping("/gerar-texto")
    public ResponseEntity<Map<String, String>> gerarTextoComIA(@RequestBody Map<String, String> payload) {
        String tema = payload.get("tema");
        if (tema == null || tema.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // Agora retorna um JSON estruturado: {"assunto": "...", "corpo": "..."}
        Map<String, String> resultado = geminiService.gerarTextoCampanha(tema);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/paginas")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Lista campanhas com paginação offset")
    public ResponseEntity<PaginaOffsetCampanhaResposta> listarComOffset(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int tamanho
    ) {
        log.info("Requisição para paginação offset de campanhas: pagina={}, tamanho={}", pagina, tamanho);
        PaginaOffsetCampanhaResposta resposta = PaginaOffsetCampanhaResposta.de(
                campanhaService.buscarPaginaOffset(pagina, tamanho)
        );
        return ResponseEntity.ok(resposta);
    }

    // Adicione esta importação lá em cima, se não tiver:
    //

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleValidacaoErro(IllegalArgumentException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }
}
package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Campanha.CampanhaRequestDto;
import school.sptech.crud_proj_v1.dto.Campanha.CampanhaResponseDto;
import school.sptech.crud_proj_v1.service.CampanhaService;

import java.util.List;

@Tag(name = "Campanha")
@RestController
@RequestMapping("/campanhas")
@Slf4j
@RequiredArgsConstructor
public class CampanhaController {
    private final CampanhaService campanhaService;

    @GetMapping
    public ResponseEntity<List<CampanhaResponseDto>> listarCampanhas() {
        List<CampanhaResponseDto> campanhas = campanhaService.listarCampanhas();
        return ResponseEntity.ok(campanhas);
    }

    @PostMapping("/criar")
    public ResponseEntity<CampanhaResponseDto> criarCampanha(@RequestBody CampanhaRequestDto campanhaRequestDTO) {
        try {
            CampanhaResponseDto novaCampanha = campanhaService.criarCampanha(campanhaRequestDTO);
            return ResponseEntity.ok(novaCampanha);
        } catch (RuntimeException e) {
            log.error("Erro ao criar campanha: {}", e.getMessage());
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/iniciar/{id}")
    public ResponseEntity<Void> iniciarCampanha(@PathVariable Integer id) {
        try {
            campanhaService.iniciarCampanha(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            log.error("Erro ao iniciar campanha: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}

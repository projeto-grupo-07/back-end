package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Endereco.EnderecoRequestDto;
import school.sptech.crud_proj_v1.dto.Endereco.EnderecoResponseDto;
import school.sptech.crud_proj_v1.service.EnderecoService;

import java.util.List;

@Tag(name = "Endereço")
@RestController
@RequestMapping("/enderecos")
public class EnderecoController {
    private final EnderecoService enderecoService;

    public EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EnderecoResponseDto> cadastrar(@Valid @RequestBody EnderecoRequestDto dto) {
        EnderecoResponseDto salvo = enderecoService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<EnderecoResponseDto>> listarTodos() {
        List<EnderecoResponseDto> enderecos = enderecoService.listarTodos();
        return enderecos.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(enderecos);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EnderecoResponseDto> listarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(enderecoService.listarPorId(id));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EnderecoResponseDto> atualizarTotal(
            @PathVariable Integer id,
            @Valid @RequestBody EnderecoRequestDto dto) {
        return ResponseEntity.ok(enderecoService.atualizarTotal(id, dto));
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EnderecoResponseDto> atualizarParcial(
            @PathVariable Integer id,
            @RequestBody EnderecoRequestDto dto) {
        return ResponseEntity.ok(enderecoService.atualizarParcial(id, dto));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id) {
        enderecoService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
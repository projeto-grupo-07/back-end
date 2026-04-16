package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Empresa.EmpresaRequestDto;
import school.sptech.crud_proj_v1.dto.Empresa.EmpresaResponseDto;
import school.sptech.crud_proj_v1.service.EmpresaService;

import java.util.List;

@Tag(name = "Empresa")
@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EmpresaResponseDto> cadastrar(@Valid @RequestBody EmpresaRequestDto dto) {
        EmpresaResponseDto salvo = empresaService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<EmpresaResponseDto>> listarTodos() {
        List<EmpresaResponseDto> empresas = empresaService.listarTodos();
        return empresas.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EmpresaResponseDto> listarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(empresaService.listarPorId(id));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<EmpresaResponseDto> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody EmpresaRequestDto dto) {
        return ResponseEntity.ok(empresaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer id) {
        empresaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
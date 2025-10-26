package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaRequestDto;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaResponseDto;
import school.sptech.crud_proj_v1.mapper.CategoriaMapper;
import school.sptech.crud_proj_v1.service.CategoriaService;

import java.util.List;

@Tag(name = "Categoria")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;


    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<List<CategoriaResponseDto>> listarTodos(){
        List<CategoriaResponseDto> categorias = categoriaService.listarTodos();

        return categorias.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(categorias);
    }



    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<CategoriaResponseDto> listarPorId(@PathVariable("id") Integer id) {
        return categoriaService.listarPorId(id) == null ? ResponseEntity.notFound().build() : ResponseEntity.ok().body(categoriaService.listarPorId(id));
    }

    @GetMapping("/por-nome")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<List<CategoriaResponseDto>> buscarPorNome(@RequestParam("nome") String descricao) {

        List<CategoriaResponseDto> categorias = categoriaService.listarPorNome(descricao);

        return categorias.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(categorias);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<CategoriaResponseDto> cadastrar(@Valid @RequestBody CategoriaRequestDto categoriaRequestDto) {
        return ResponseEntity.status(201).body(categoriaService.cadastrar(categoriaRequestDto));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<Void> excluir(@PathVariable("id") Integer id) {
        categoriaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<CategoriaResponseDto> atualizarTotal(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaRequestDto categoriaRequestDto
    ) {
        CategoriaResponseDto dtoSalvo = categoriaService.atualizarTotal(id, categoriaRequestDto);
        return ResponseEntity.ok(dtoSalvo);
    }


    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<CategoriaResponseDto> atualizarParcial(
            @PathVariable Integer id,
            @RequestBody CategoriaRequestDto categoriaRequestDto
    ) {
        CategoriaResponseDto dtoSalvo = categoriaService.atualizarParcial(id, categoriaRequestDto);
        return ResponseEntity.ok(dtoSalvo);
    }


}

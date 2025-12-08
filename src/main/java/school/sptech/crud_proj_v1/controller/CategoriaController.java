package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaPaiRequestDto;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaPaiResponseDto;
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

    @GetMapping("/pais")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<List<CategoriaPaiResponseDto>> listarTodosPais(){
        List<CategoriaPaiResponseDto> categorias = categoriaService.listarTodosPais();

        return categorias.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(categorias);
    }

    @GetMapping("/filhos")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<List<CategoriaResponseDto>> listarTodosFilho(){
        List<CategoriaResponseDto> categorias = categoriaService.listarTodosFilhos();

        return categorias.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(categorias);
    }


    @GetMapping("/pai/{id}")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<CategoriaPaiResponseDto> listarPaiPorId(@PathVariable("id") Integer id) {
        return categoriaService.listarPaiPorId(id) == null ? ResponseEntity.notFound().build() :
                ResponseEntity.ok().body(categoriaService.listarPaiPorId(id));
    }

    @GetMapping("/filho/{id}")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<CategoriaResponseDto> listarFilhoPorId(@PathVariable("id") Integer id) {
        return categoriaService.listarPaiPorId(id) == null ? ResponseEntity.notFound().build() :
                ResponseEntity.ok().body(categoriaService.listarFilhoPorId(id));
    }

    @GetMapping("/pai/por-nome")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<List<CategoriaPaiResponseDto>> buscarPaiPorNome(@RequestParam("nome") String descricao) {

        List<CategoriaPaiResponseDto> categorias = categoriaService.listarPorNomePai(descricao);

        return categorias.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(categorias);
    }

    @GetMapping("/filho/pai/{id}")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<List<CategoriaResponseDto>> buscarFilhosPorPai(@PathVariable("id") Integer id) {
        List<CategoriaResponseDto> filhos = categoriaService.listarFilhosPorPai(id);

        return filhos.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(filhos);
    }

    @GetMapping("/filho/por-nome")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<List<CategoriaResponseDto>> buscarFilhoPorNome(@RequestParam("nome") String descricao) {

        List<CategoriaResponseDto> categorias = categoriaService.listarPorNomeFilho(descricao);

        return categorias.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(categorias);
    }

    @PostMapping("/pai")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<CategoriaPaiResponseDto> cadastrar(@Valid @RequestBody CategoriaPaiRequestDto req) {
        return ResponseEntity.status(201).body(categoriaService.cadastrarPai(req));
    }

    @PostMapping("/filho")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<CategoriaResponseDto> cadastrar(@Valid @RequestBody CategoriaRequestDto req, CategoriaPaiRequestDto pai) {
        return ResponseEntity.status(201).body(categoriaService.cadastrarFilho(req));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<Void> excluir(@PathVariable("id") Integer id) {
        categoriaService.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/pai/{id}")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<CategoriaPaiResponseDto> atualizarTotalPai(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaPaiRequestDto req
    ) {
        CategoriaPaiResponseDto dtoSalvo = categoriaService.atualizarTotalPai(id, req);
        return ResponseEntity.ok(dtoSalvo);
    }

    @PutMapping("/filho/{id}")
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Categoria")
    public ResponseEntity<CategoriaResponseDto> atualizarTotalFilho(@PathVariable Integer id, @Valid @RequestBody CategoriaRequestDto req) {
        CategoriaResponseDto dtoSalvo = categoriaService.atualizarTotalFilho(id, req);
        return ResponseEntity.ok(dtoSalvo);
    }


//    @PatchMapping("/{id}")
//    @SecurityRequirement(name = "Bearer")
//    @Tag(name = "Categoria")
//    public ResponseEntity<CategoriaResponseDto> atualizarParcial(
//            @PathVariable Integer id,
//            @RequestBody CategoriaRequestDto categoriaRequestDto
//    ) {
//        CategoriaResponseDto dtoSalvo = categoriaService.atualizarParcial(id, categoriaRequestDto);
//        return ResponseEntity.ok(dtoSalvo);
//    }


}

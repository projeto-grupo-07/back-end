package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoResponseDTO;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoRequestDTO;
import school.sptech.crud_proj_v1.entity.Produto;
import school.sptech.crud_proj_v1.service.ProdutoService;

import java.util.List;

@Tag(name = "Produto")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todos os produtos cadastrados")
    public ResponseEntity<List<ProdutoResponseDTO>> listarProdutos() {
        List<ProdutoResponseDTO> listaProdutos = service.listarTodos();

        if (listaProdutos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(listaProdutos);
    }

    // EndPoint Gaby
    @GetMapping("/por-modelo")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista os produtos por modelo")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarPorModelo(@RequestParam String modelo) {
        List<ProdutoResponseDTO> produtosResponse = service.buscarProdutoPorModelo(modelo);
        if (produtosResponse.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtosResponse);
    }

    //EndPoint Gabriel
    @GetMapping("/por-marca/{marca}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista os produtos por marca")
    public ResponseEntity<List<ProdutoResponseDTO>> bucarProdutoPorMarca(@PathVariable String marca) {
        List<ProdutoResponseDTO> produtos = service.buscarProdutoPorMarca(marca);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/por-categoria")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista os produtos por categoria")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutoPorCategoria(@RequestParam String categoria) {
        List<ProdutoResponseDTO> achados = service.buscarProdutoPorCategoria(categoria);
        if (achados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(achados);
    }

    @GetMapping("/por-categoria-ordenado-preco-desc")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todos os produtos de uma determiada categoria ordenados pelo preço de forma decrescente")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutoPorCategoriaOrdenadoPorPrecoDesc(@RequestParam String categoria) {
        List<ProdutoResponseDTO> produtos = service.buscarProdutoPorCategoriaOrdenadoPorPrecoDesc(categoria);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        } return ResponseEntity.status(200).body(produtos);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método cadastra um produto")
    public ResponseEntity<ProdutoResponseDTO> cadastrar(@RequestBody @Valid ProdutoRequestDTO novoProdutoDTO) {
        ProdutoResponseDTO produtoSalvo = service.criar(novoProdutoDTO);
        return ResponseEntity.status(201).body(produtoSalvo);
    }


    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método atualiza algum campo do produto pelo id")
    public ResponseEntity<ProdutoResponseDTO> atualizarProdutoPorId(@PathVariable Integer id, @RequestBody ProdutoRequestDTO dto) {
        ProdutoResponseDTO produtoAtualizado = service.atualizarPorId(id, dto);
        return ResponseEntity.status(200).body(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método deleta um produto pelo id")
    public ResponseEntity<Void> deletarProdutoPorId(@PathVariable Integer id) {
        service.deletarPorId(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método atualiza o preço venda de um produto pelo id")
    public ResponseEntity<ProdutoResponseDTO> atualizarPrecoVendaPorId(@PathVariable Integer id, @RequestBody ProdutoRequestDTO dto){
        ProdutoResponseDTO produtoAtualizado = service.atualizarPrecoVendaPorId(id, dto);
        return ResponseEntity.status(200).body(produtoAtualizado);
    }
}

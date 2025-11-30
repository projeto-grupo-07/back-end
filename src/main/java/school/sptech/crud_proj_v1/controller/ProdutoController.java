package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Produto.*;
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

    @GetMapping("/todos")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todos os produtos cadastrados")
    public ResponseEntity<List<ProdutoResponse>> listarTodosProdutos() {
        List<ProdutoResponse> listaProdutos = service.listarTodos();

        if (listaProdutos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(listaProdutos);
    }

    @GetMapping("/todos/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método busca os produtos pelo id")
    public ResponseEntity<ProdutoResponse> buscarProdutosPorId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.buscarProdutoPorId(id));
    }

    @GetMapping("/calcado")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todos os produtos de categoria calçados cadastrados")
    public ResponseEntity<List<CalcadoProdutoResponse>> listarProdutosCalcados() {
        List<CalcadoProdutoResponse> listaProdutos = service.listarApenasCalcados();

        if (listaProdutos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(listaProdutos);
    }

    @GetMapping("/outros")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todos os produtos de categoria outros cadastrados")
    public ResponseEntity<List<OutrosProdutoResponse>> listarProdutosOutros() {
        List<OutrosProdutoResponse> listaProdutos = service.listarApenasOutros();

        if (listaProdutos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(listaProdutos);
    }

    // EndPoint Gaby
    @GetMapping("/calcado/por-modelo/{modelo}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista o calcado pelo modelo")
    public ResponseEntity<CalcadoProdutoResponse> buscarPorCalcadoModelo(@RequestParam String modelo) {
        return ResponseEntity.status(200).body(service.buscarCalcadoPorModelo(modelo));
    }

    //EndPoint Gabriel
    @GetMapping("/calcado/por-marca/{marca}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista os calcados por marca")
    public ResponseEntity<List<CalcadoProdutoResponse>> buscarCalcadoPorMarca(@PathVariable String marca) {
        List<CalcadoProdutoResponse> produtos = service.buscarCalcadoPorMarca(marca);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/calcado/por-numero/{numero}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista os calcados por numero")
    public ResponseEntity<List<CalcadoProdutoResponse>> buscarCalcadoPorNumero(@PathVariable Integer numero) {
        List<CalcadoProdutoResponse> produtos = service.buscarCalcadoPorNumero(numero);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/calcado/por-cor/{cor}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista os calcados por cor")
    public ResponseEntity<List<CalcadoProdutoResponse>> buscarCalcadoPorCor(@PathVariable String cor) {
        List<CalcadoProdutoResponse> produtos = service.buscarCalcadoPorCor(cor);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/outros/por-nome/{nome}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista os outros por nome")
    public ResponseEntity<OutrosProdutoResponse> buscarOutrosPorNome(@PathVariable String nome) {
        return ResponseEntity.status(200).body(service.buscarOutrosPorNome(nome));
    }


    @GetMapping("/outros/por-descricao/{descricao}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista os calcados por descrição")
    public ResponseEntity<List<OutrosProdutoResponse>> buscarOutrosPorDescricao(@PathVariable String descricao) {
        List<OutrosProdutoResponse> produtos = service.buscarOutrosPorDescricao(descricao);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/por-categoria")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista os produtos por categoria")
    public ResponseEntity<List<ProdutoResponse>> buscarProdutoPorCategoria(@RequestParam String categoria) {
        List<ProdutoResponse> achados = service.buscarProdutoPorCategoria(categoria);
        if (achados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(achados);
    }

    @GetMapping("/por-categoria-ordenado-preco-desc/{categoria}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todos os produtos de uma determinada categoria ordenados pelo preço de forma decrescente")
    public ResponseEntity<List<ProdutoResponse>> listarProdutosOrdenadoPorMaiorQuantidade(@RequestParam String categoria) {
        List<ProdutoResponse> produtos = service.buscarProdutoPorCategoriaOrdenadoPorPrecoDesc(categoria);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        } return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/por-quantidade-ordenado-desc")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todos os produtos ordenados pela quantidade decrescente, maior para o menor")
    public ResponseEntity<List<ProdutoResponse>> listarProdutosOrdenadoPorMaiorQuantidade() {
        List<ProdutoResponse> produtos = service.listarProdutosOrdenadoPorMaiorQuantidade();
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        } return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/por-quantidade-ordenado-asc")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todos os produtos ordenados pela quantidade ascendente, menor para o maior")
    public ResponseEntity<List<ProdutoResponse>> listarProdutosOrdenadoPorMenorQuantidade() {
        List<ProdutoResponse> produtos = service.listarProdutosOrdenadoPorMenorQuantidade();
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        } return ResponseEntity.status(200).body(produtos);
    }

    @PostMapping("/outros")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método cadastra um produto de categoria outros")
    public ResponseEntity<OutrosProdutoResponse> cadastrarOutros(@RequestBody @Valid OutrosProdutoRequest novoOutrosRequest) {
        OutrosProdutoResponse produtoSalvo = service.cadastrarOutros(novoOutrosRequest);
        return ResponseEntity.status(201).body(produtoSalvo);
    }

    @PostMapping("/calcado")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método cadastra um produto de categoria calcado")
    public ResponseEntity<CalcadoProdutoResponse> cadastrarCalcado(@RequestBody @Valid CalcadoProdutoRequest novoProdutoRequest) {
        CalcadoProdutoResponse produtoSalvo = service.cadastrarCalcado(novoProdutoRequest);
        return ResponseEntity.status(201).body(produtoSalvo);
    }

    @PutMapping("/calcado/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método atualiza campo do calçado pelo id")
    public ResponseEntity<CalcadoProdutoResponse> atualizarProdutoPorId(@PathVariable Integer id, @RequestBody CalcadoProdutoRequest dto) {
        return ResponseEntity.status(200).body(service.atualizarCalcado(id, dto));
    }

    @PutMapping("/outros/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método atualiza algum campo do outros pelo id")
    public ResponseEntity<OutrosProdutoResponse> atualizarProdutoPorId(@PathVariable Integer id, @RequestBody OutrosProdutoRequest dto) {
        return ResponseEntity.status(200).body(service.atualizarOutros(id, dto));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método deleta um produto pelo id")
    public ResponseEntity<Void> deletarProdutoPorId(@PathVariable Integer id) {
        service.deletarPorId(id);
        return ResponseEntity.status(204).build();
    }
}

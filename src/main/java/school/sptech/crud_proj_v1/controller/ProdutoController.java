package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Produto.*;
import school.sptech.crud_proj_v1.service.ProdutoService;

import java.util.List;

@Tag(name = "Produto")
@RestController
@RequestMapping("/produtos")
@Slf4j
public class ProdutoController {
    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping("/todos")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todos os produtos cadastrados")
    public ResponseEntity<List<ProdutoResponse>> listarTodosProdutos() {
        log.info("Requisição para listar todos os produtos recebida.");
        List<ProdutoResponse> listaProdutos = service.listarTodos();
        if (listaProdutos.isEmpty()) {
            log.warn("Nenhum produto encontrado na base de dados.");
            return ResponseEntity.status(204).build();
        }
        log.info("Listagem de produtos finalizada. Total de registros: {}", listaProdutos.size());
        return ResponseEntity.ok(listaProdutos);
    }

    @GetMapping("/todos/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método busca os produtos pelo id")
    public ResponseEntity<ProdutoResponse> buscarProdutosPorId(@PathVariable Integer id) {
        // Log inicial da requisição
        log.info("Buscando produto por ID: {}", id);

        ProdutoResponse produtoFound = service.buscarProdutoPorId(id);

        if (produtoFound == null) {
            log.warn("Produto com ID {} não encontrado.", id);
            return ResponseEntity.status(404).build();
        }

        if (produtoFound instanceof CalcadoProdutoResponse calcado) {
            log.info("Calçado encontrado - Marca: {}, Modelo: {}, Número: {}",
                    calcado.getMarca(), calcado.getModelo(), calcado.getNumero());
        } else if (produtoFound instanceof OutrosProdutoResponse outros) {
            log.info("Outro produto encontrado - Nome: {}, Descrição: {}",
                    outros.getNome(), outros.getDescricao());
        } else {
            log.info("Produto encontrado (tipo genérico): {}", produtoFound.getClass().getSimpleName());
        }

        // Log com resumo do que será retornado (tipo + toString)
        log.info("Retornando produto - tipo: {}, detalhes: {}",
                produtoFound.getClass().getSimpleName(), produtoFound);

        return ResponseEntity.status(200).body(produtoFound);
    }

    @GetMapping("/calcado")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todos os produtos de categoria calçados cadastrados")
    public ResponseEntity<List<CalcadoProdutoResponse>> listarProdutosCalcados() {
        log.info("Requisição para listar todos os calçados recebida.");
        List<CalcadoProdutoResponse> listaProdutos = service.listarApenasCalcados();

        if (listaProdutos.isEmpty()) {
            log.warn("Nenhum calçado encontrado na base de dados.");
            return ResponseEntity.status(204).build();
        }

        log.info("Listagem de calçados finalizada. Total de registros: {}", listaProdutos.size());
        return ResponseEntity.ok(listaProdutos);
    }

    @GetMapping("/outros")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todos os produtos de categoria outros cadastrados")
    public ResponseEntity<List<OutrosProdutoResponse>> listarProdutosOutros() {
        log.info("Requisição para listar todos os outros produtos recebida.");
        List<OutrosProdutoResponse> listaProdutos = service.listarApenasOutros();

        if (listaProdutos.isEmpty()) {
            log.warn("Nenhum outro produto encontrado na base de dados.");
            return ResponseEntity.status(204).build();
        }
        log.info("Listagem de outros produtos finalizada. Total de registros: {}", listaProdutos.size());
        return ResponseEntity.ok(listaProdutos);
    }

    // EndPoint Gaby
    @GetMapping("/calcado/por-modelo/{modelo}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista o calcado pelo modelo")
    public ResponseEntity<List<CalcadoProdutoResponse>> buscarPorCalcadoModelo(@PathVariable String modelo) {
        log.info("Requisição para listar calçados por modelo recebida. Modelo: {}", modelo);
        return ResponseEntity.status(200).body(service.buscarCalcadoPorModelo(modelo));
    }

    //EndPoint Gabriel
    @GetMapping("/calcado/por-marca/{marca}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista os calcados por marca")
    public ResponseEntity<List<CalcadoProdutoResponse>> buscarCalcadoPorMarca(@PathVariable String marca) {
        log.info("Requisição para listar todos os calçados por marca recebida.");
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
        log.info("Requisição para listar todos os calçados por numero recebida.");
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
        log.info("Requisição para listar todos os calçados por cor recebida.");
        List<CalcadoProdutoResponse> produtos = service.buscarCalcadoPorCor(cor);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/outros/por-nome/{nome}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista os outros por nome")
    public ResponseEntity<List<OutrosProdutoResponse>> buscarOutrosPorNome(@PathVariable String nome) {
        log.info("Requisição para listar todos os outros produtos por nome recebida. Nome: {}", nome);
        return  ResponseEntity.status(200).body(service.buscarOutrosPorNome(nome));
    }


    @GetMapping("/outros/por-descricao/{descricao}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista os calcados por descrição")
    public ResponseEntity<List<OutrosProdutoResponse>> buscarOutrosPorDescricao(@PathVariable String descricao) {
        log.info("Requisição para listar todos os outros produtos por descrição recebida. Descrição: {}", descricao);
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
        log.info("Requisição para listar todos os produtos por categoria recebida. Categoria: {}", categoria);
        List<ProdutoResponse> achados = service.buscarProdutoPorCategoria(categoria);
        if (achados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(achados);
    }

    @GetMapping("/por-categoria-ordenado-preco-desc/{categoria}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todos os produtos de uma determinada categoria ordenados pelo preço de forma decrescente")
    public ResponseEntity<List<ProdutoResponse>> listarProdutosOrdenadoPorMaiorQuantidade(@PathVariable String categoria) {
        log.info("Requisição para listar todos os produtos ordenado por maior quantidade por categoria");
        List<ProdutoResponse> produtos = service.buscarProdutoPorCategoriaOrdenadoPorPrecoDesc(categoria);
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        } return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/por-quantidade-ordenado-desc")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todos os produtos ordenados pela quantidade decrescente, maior para o menor")
    public ResponseEntity<List<ProdutoResponse>> listarProdutosOrdenadoPorMaiorQuantidade() {
        log.info("Requisição para listar todos os produtos ordenado por menor quantidade.");
        List<ProdutoResponse> produtos = service.listarProdutosOrdenadoPorMaiorQuantidade();
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        } return ResponseEntity.status(200).body(produtos);
    }

    @GetMapping("/por-quantidade-ordenado-asc")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todos os produtos ordenados pela quantidade ascendente, menor para o maior")
    public ResponseEntity<List<ProdutoResponse>> listarProdutosOrdenadoPorMenorQuantidade() {
        log.info("Requisição para listar todos os produtos ordenados pela quantidade ascendente, menor para o maior");
        List<ProdutoResponse> produtos = service.listarProdutosOrdenadoPorMenorQuantidade();
        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        } return ResponseEntity.status(200).body(produtos);
    }

    @PostMapping("/outros")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método cadastra um produto de categoria outros")
    public ResponseEntity<OutrosProdutoResponse> cadastrarOutros(@RequestBody @Valid OutrosProdutoRequest novoOutrosRequest) {
        log.info("Iniciando cadastro de novo outros produto: {}", novoOutrosRequest.getNome());
        OutrosProdutoResponse produtoSalvo = service.cadastrarOutros(novoOutrosRequest);
        log.info("Outro produto cadastrado com sucesso. ID gerado: {}", produtoSalvo.getId());
        return ResponseEntity.status(201).body(produtoSalvo);
    }

    @PostMapping("/calcado")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método cadastra um produto de categoria calcado")
    public ResponseEntity<CalcadoProdutoResponse> cadastrarCalcado(@RequestBody @Valid CalcadoProdutoRequest novoProdutoRequest) {
        log.info("Iniciando cadastro de calçado produto: {}", novoProdutoRequest.getModelo());
        CalcadoProdutoResponse produtoSalvo = service.cadastrarCalcado(novoProdutoRequest);
        log.info("Produto calçado com sucesso. ID gerado: {}", produtoSalvo.getId());
        return ResponseEntity.status(201).body(produtoSalvo);
    }

    @PutMapping("/calcado/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método atualiza campo do calçado pelo id")
    public ResponseEntity<CalcadoProdutoResponse> atualizarProdutoPorId(@PathVariable Integer id, @RequestBody CalcadoProdutoRequest dto) {
        log.info("Requisição para atualizar calçado com id: {}", id);
        return ResponseEntity.status(200).body(service.atualizarCalcado(id, dto));
    }

    @PutMapping("/outros/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método atualiza algum campo do outros pelo id")
    public ResponseEntity<OutrosProdutoResponse> atualizarProdutoPorId(@PathVariable Integer id, @RequestBody OutrosProdutoRequest dto) {
        log.info("Requisição para atualizar 'outros' com id: {}", id);
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

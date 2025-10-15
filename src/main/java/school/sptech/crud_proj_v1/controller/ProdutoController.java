package school.sptech.crud_proj_v1.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoListDTO;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoRequestDTO;
import school.sptech.crud_proj_v1.entity.Produto;
import school.sptech.crud_proj_v1.mapper.ProdutoMapper;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;
import school.sptech.crud_proj_v1.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoRepository repository;
    private final ProdutoService service;

    public ProdutoController(ProdutoRepository repository, ProdutoService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoListDTO>> listarProdutos(){
        List<ProdutoListDTO> listaProdutos = service.listarTodos();

        if (listaProdutos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(listaProdutos);
    }

    @PostMapping
    public ResponseEntity<ProdutoListDTO> cadastrarProduto(@RequestBody @Valid ProdutoRequestDTO novoProdutoDTO){
        ProdutoListDTO produtoSalvo = service.criar(novoProdutoDTO);
        return ResponseEntity.status(201).body(produtoSalvo);
    }

    @GetMapping("/por-categoria")
    public ResponseEntity<List<Produto>> buscarProdutoPorCategoria(@RequestParam String categoria){
        List<Produto> achados = repository.findByCategoriaDescricaoContainingIgnoreCase(categoria);
        if (achados.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(achados);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProdutoPorId(@PathVariable Integer id, @RequestBody Produto prod){
        prod.setId(id);
        if (repository.existsById(id)){
            repository.save(prod);
            return ResponseEntity.status(200).body(prod);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProdutoPorId(@PathVariable Integer id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    // EndPoint Gaby
    @GetMapping("/por-modelo")
    public ResponseEntity<List<ProdutoListDTO>> buscarPorModelo(@RequestParam String modelo){

        List<Produto> produtosEncontrados = repository.findByModeloContainingIgnoreCase(modelo);

        if(produtosEncontrados.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        List<ProdutoListDTO> resposta = produtosEncontrados.stream()
                .map(ProdutoMapper::toDTO)
                .toList();

        return ResponseEntity.status(200).body(resposta);
    }

    //EndPoint Gabriel
    @GetMapping("/por-marca/{marca}")
    public ResponseEntity<List<ProdutoListDTO>> bucarProdutoPorMarca(@PathVariable String marca) {
        List<ProdutoListDTO> produtos = service.buscarProdutoPorMarca(marca);

        if (produtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        } return ResponseEntity.status(200).body(produtos);
    }


}

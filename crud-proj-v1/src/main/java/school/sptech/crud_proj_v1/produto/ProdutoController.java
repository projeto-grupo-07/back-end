package school.sptech.crud_proj_v1.produto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoReposiitory repository;

    public ProdutoController(ProdutoReposiitory repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos(){
        List<Produto> all = repository.findAll();

        if (all.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(all);
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody Produto prod){
        prod.setId(null);
        repository.save(prod);
        return ResponseEntity.status(201).body(prod);
    }

    @GetMapping("/por-categoria")
    public ResponseEntity<List<Produto>> buscarProdutoPorCategoria(@RequestParam String categoria){
        List<Produto> achados = repository.findByCategoriaContainingIgnoreCase(categoria);
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


}

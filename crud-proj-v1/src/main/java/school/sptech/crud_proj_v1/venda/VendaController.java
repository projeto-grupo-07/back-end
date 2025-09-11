package school.sptech.crud_proj_v1.venda;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {
    private final VendaRepository repository;

    public VendaController(VendaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas(){
        List<Venda> all = repository.findAll();

        if (all.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(all);
    }

    @PostMapping
    public ResponseEntity<Venda> cadastrarVenda(@RequestBody Venda venda){
        venda.setId(null);
        repository.save(venda);
        return ResponseEntity.status(201).body(venda);
    }

    @GetMapping("/{nome}")
    public ResponseEntity<List<Venda>> buscarVendasPorNomeDoVendedor(@PathVariable String nome){
        List<Venda> vendasByNome = repository.findBynomeVendedorContainingIgnoreCase(nome);
        if (vendasByNome.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(vendasByNome);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venda> atualizarVendaPorId(@PathVariable Integer id, @RequestBody Venda venda){
        venda.setId(id);
        if (repository.existsById(id)){
            repository.save(venda);
            return ResponseEntity.status(200).body(venda);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVendaPorId(@PathVariable Integer id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }


}

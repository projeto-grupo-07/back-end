package school.sptech.crud_proj_v1;

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

//    @PostMapping
//    public ResponseEntity<Venda> cadastrarVenda(@RequestBody Venda venda){}

}

package school.sptech.crud_proj_v1.pessoa;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    private final FuncionarioRepository repository;

    public FuncionarioController(FuncionarioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarFuncionarios(){
        List<Funcionario> all = repository.findAll();

        if (all.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(all);
    }

    @PostMapping
    public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody Funcionario func){
        func.setId(null);
        repository.save(func);
        return ResponseEntity.status(201).body(func);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarFuncPorId(@PathVariable Integer id){
        return ResponseEntity.of(repository.findById(id));
//        Optional<Funcionario> opt = repository.findBycpfContainingIgnoreCase(cpf);
/*        if (opt.isEmpty()){
            return ResponseEntity.status(404).build();
        }*/
//        return ResponseEntity.status(200).body(opt.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionarioPorId(@PathVariable Integer id, @RequestBody Funcionario func){
        func.setId(id);
        if (repository.existsById(id)){
            repository.save(func);
            return ResponseEntity.status(200).body(func);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionarioPorId(@PathVariable Integer id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }


}

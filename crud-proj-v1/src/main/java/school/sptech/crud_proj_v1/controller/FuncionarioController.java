package school.sptech.crud_proj_v1.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.entity.Funcionario;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;
import school.sptech.crud_proj_v1.service.FuncionarioService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarFuncionarios(){
        List<Funcionario> all = service.listar();

        if (all.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(all);
    }

    @PostMapping
    public ResponseEntity<Funcionario> cadastrarFuncionario(@Valid @RequestBody Funcionario func){
        service.cadastrar(func);
        return ResponseEntity.status(201).body(func);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> buscarFuncPorId(@PathVariable int id){
        Funcionario func = service.buscarPorId(id);
        return ResponseEntity.status(200).body(func);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionarioPorId(@Valid @PathVariable Integer id, @RequestBody Funcionario func){
        service.atualizarPorId(id, func);
        return ResponseEntity.status(200).body(func);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFuncionarioPorId(@PathVariable Integer id){
        service.deletarPorId(id);
        return ResponseEntity.status(204).build();
    }


}

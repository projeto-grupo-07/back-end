package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioLoginDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioResponseDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioTokenDto;
import school.sptech.crud_proj_v1.entity.Funcionario;
import school.sptech.crud_proj_v1.mapper.FuncionarioMapper;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;
import school.sptech.crud_proj_v1.service.FuncionarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;
import java.util.Optional;

@Tag(name = "Funcionário")
@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Tag(name = "Funcionário")
    public ResponseEntity<List<FuncionarioResponseDto>> listarFuncionarios(){
        List<FuncionarioResponseDto> all = service.listar();

        if (all.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(all);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método cadastra um novo funcionário")
    public ResponseEntity<Funcionario> cadastrarFuncionario(@Valid @RequestBody Funcionario func){
        service.cadastrar(func);
        return ResponseEntity.status(201).body(func);
    }

    @PostMapping("/login")
    public ResponseEntity<FuncionarioTokenDto> login(@RequestBody FuncionarioLoginDto funcionarioLoginDto) {
        final Funcionario funcionario = FuncionarioMapper.of(funcionarioLoginDto);
        FuncionarioTokenDto funcionarioTokenDto = this.service.autenticar(funcionario);

        return ResponseEntity.status(200).body(funcionarioTokenDto);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método busca um funcionário por seu id")
    public ResponseEntity<Funcionario> buscarFuncPorId(@PathVariable int id){
        Funcionario func = service.buscarPorId(id);
        return ResponseEntity.status(200).body(func);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método atualiza algum campo do funcionário pelo id")
    public ResponseEntity<Funcionario> atualizarFuncionarioPorId(@Valid @PathVariable Integer id, @RequestBody Funcionario func){
        service.atualizarPorId(id, func);
        return ResponseEntity.status(200).body(func);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método deleta um funcionário pelo id")
    public ResponseEntity<Void> deletarFuncionarioPorId(@PathVariable Integer id){
        service.deletarPorId(id);
        return ResponseEntity.status(204).build();
    }


}

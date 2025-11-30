package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioLoginDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioRequestDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioResponseDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioTokenDto;
import school.sptech.crud_proj_v1.entity.Funcionario;
import school.sptech.crud_proj_v1.mapper.FuncionarioMapper;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;
import school.sptech.crud_proj_v1.service.FuncionarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@Tag(name = "Funcionário")
@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    private final FuncionarioService service;
    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioController(FuncionarioService service, FuncionarioRepository funcionarioRepository) {
        this.service = service;
        this.funcionarioRepository = funcionarioRepository;
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
    public ResponseEntity<FuncionarioResponseDto> cadastrarFuncionario(@Valid @RequestBody FuncionarioRequestDto func){
        return ResponseEntity.status(201).body(service.cadastrarFuncionario(func));
    }

    @PostMapping("/login")
    public ResponseEntity<FuncionarioTokenDto> login(@RequestBody FuncionarioLoginDto funcionarioLoginDto) {
        final Funcionario funcionario = FuncionarioMapper.of(funcionarioLoginDto);
        FuncionarioTokenDto funcionarioTokenDto = this.service.autenticar(funcionario);

        String tokenJWT = funcionarioTokenDto.getToken();

        ResponseCookie cookie = ResponseCookie.from("jwt", tokenJWT)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(3600)
                .sameSite("Lax")
                .build();

        funcionarioTokenDto.setToken(null);

        return ResponseEntity.status(200)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(funcionarioTokenDto);
    }

    @GetMapping("/me")
    public ResponseEntity<FuncionarioTokenDto> getUsuarioLogado(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Funcionario funcionario = funcionarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado"));

        return ResponseEntity.ok(FuncionarioMapper.of(funcionario, null));
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método busca um funcionário por seu id")
    public ResponseEntity<FuncionarioResponseDto> buscarFuncPorId(@PathVariable int id){
        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método atualiza algum campo do funcionário pelo id")
    public ResponseEntity<FuncionarioResponseDto> atualizarFuncionarioPorId(@Valid @PathVariable Integer id, @RequestBody FuncionarioRequestDto func){
        return ResponseEntity.status(200).body(service.atualizarPorId(id, func));
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método deleta um funcionário pelo id")
    public ResponseEntity<Void> deletarFuncionarioPorId(@PathVariable Integer id){
        service.deletarPorId(id);
        return ResponseEntity.status(204).build();
    }
}

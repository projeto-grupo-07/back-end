package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.info("Requisição para listar todos os funcionários recebida.");
        List<FuncionarioResponseDto> all = service.listar();
        if (all.isEmpty()){
            log.warn("Nenhum funcionário encontrado na base de dados.");
            return ResponseEntity.status(204).build();
        }
        log.info("Listagem finalizada. Total de registros: {}", all.size());
        return ResponseEntity.status(200).body(all);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método cadastra um novo funcionário")
    public ResponseEntity<FuncionarioResponseDto> cadastrarFuncionario(@Valid @RequestBody FuncionarioRequestDto func){
        log.info("Iniciando cadastro de novo funcionário: {}", func.getNome());
        FuncionarioResponseDto criado = service.cadastrarFuncionario(func);
        log.info("Funcionário cadastrado com sucesso. ID gerado: {}", criado.getId());
        return ResponseEntity.status(201).body(criado);
    }

    @PostMapping("/login")
    public ResponseEntity<FuncionarioTokenDto> login(@RequestBody FuncionarioLoginDto funcionarioLoginDto) {
        log.info("Tentativa de login para o usuário: {}", funcionarioLoginDto.getEmail());
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

        log.info("Login bem-sucedido para o usuário: {}", funcionarioLoginDto.getEmail());
        return ResponseEntity.status(200)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(funcionarioTokenDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        log.info("Processando solicitação de logout.");
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        log.info("Logout realizado. Cookie JWT invalidado.");
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
    }

    @GetMapping("/me")
    public ResponseEntity<FuncionarioTokenDto> getUsuarioLogado(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("Tentativa de acesso a '/me' sem autenticação válida.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        log.info("Buscando dados do usuário logado: {}", authentication.getName());
        Funcionario funcionario = funcionarioRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> {
                    log.error("Usuário autenticado '{}' não encontrado no banco de dados.", authentication.getName());
                    return new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado");
                });

        return ResponseEntity.ok(FuncionarioMapper.of(funcionario, null));
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método busca um funcionário por seu id")
    public ResponseEntity<FuncionarioResponseDto> buscarFuncPorId(@PathVariable int id){
        log.info("Buscando funcionário por ID: {}", id);
        FuncionarioResponseDto dto = service.buscarPorId(id);
        log.info("Funcionário encontrado: {}", dto.getNome());
        return ResponseEntity.status(200).body(dto);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método atualiza algum campo do funcionário pelo id")
    public ResponseEntity<FuncionarioResponseDto> atualizarFuncionarioPorId(@Valid @PathVariable Integer id, @RequestBody FuncionarioRequestDto func){
        log.info("Iniciando atualização do funcionário ID: {}", id);
        FuncionarioResponseDto atualizado = service.atualizarPorId(id, func);
        log.info("Funcionário ID: {} atualizado com sucesso.", id);
        return ResponseEntity.status(200).body(atualizado);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método deleta um funcionário pelo id")
    public ResponseEntity<Void> deletarFuncionarioPorId(@PathVariable Integer id){
        log.info("Solicitação para deletar funcionário ID: {}", id);
        service.deletarPorId(id);
        log.info("Funcionário ID: {} removido com sucesso.", id);
        return ResponseEntity.status(204).build();
    }
}

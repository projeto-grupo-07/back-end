package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Cliente.ClienteRequestDto;
import school.sptech.crud_proj_v1.dto.Cliente.ClienteResponseDto;
import school.sptech.crud_proj_v1.dto.paginacao.PaginaOffsetClienteResposta;
import school.sptech.crud_proj_v1.repository.ClienteRepository;
import school.sptech.crud_proj_v1.service.ClienteService;

import java.util.List;

@Tag(name = "Cliente")
@RestController
@RequestMapping("/clientes")
@Slf4j
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Lista todos os clientes existentes")
    public ResponseEntity<List<ClienteResponseDto>> listar(){
        log.info("Requisição para listar todos os clientes recebida");

        List<ClienteResponseDto> clientes = service.listar();

        if(clientes.isEmpty()){
            log.warn("Nenhum cliente encontrado");
            return ResponseEntity.status(204).build();
        }

        log.info("Listagem finalizada. Total de registros: {}", clientes.size());
        return ResponseEntity.status(200).body(clientes);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Busca um cliente pelo id")
    public ResponseEntity<ClienteResponseDto> buscarPorId(@PathVariable Integer id){
        log.info("Buscando cliente por ID: {}", id);

        ClienteResponseDto dto = service.buscarPorId(id);

        log.info("Cliente encontrado: {}", dto.getNome());
        return ResponseEntity.status(200).body(dto);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Cadastra um novo cliente")
    public ResponseEntity<ClienteResponseDto> cadastrar(@Valid @RequestBody ClienteRequestDto dto){
        log.info("Iniciando cadastro de novo cliente: {}", dto.getNome());

        ClienteResponseDto criado = service.cadastrar(dto);

        log.info("Cliente cadastrado com sucesso. ID gerado: {}", criado.getId());
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Atualiza um cliente pelo id")
    public ResponseEntity<ClienteResponseDto> atualizar(@Valid @PathVariable Integer id, @RequestBody ClienteRequestDto dto){
        log.info("Iniciando atualização do cliente de ID: {}", id);
        ClienteResponseDto atualizado = service.atualizarPorId(id, dto);
        log.info("Cliente ID: {} atualizado com sucesso", id);
        return ResponseEntity.status(200).body(atualizado);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Deleta um cliente pelo id")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        log.info("Solicitação para deletar cliente ID: {}", id);
        service.deletarPorId(id);
        log.info("Cliente ID: {} removido com sucesso", id);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/busca")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Busca clientes pelo nome")
    public ResponseEntity<List<ClienteResponseDto>> buscarPorNome(@RequestParam String nome){
        log.info("Buscando clientes pelo nome: {}", nome);
        List<ClienteResponseDto> clientes = service.buscarPorNome(nome);
        if(clientes.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(clientes);
    }

    @GetMapping("/filtro")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<ClienteResponseDto>> filtrar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String genero) {

        List<ClienteResponseDto> clientes = service.filtrarClientes(nome, genero);

        if (clientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(clientes);
    }

    @GetMapping("/cpf/{cpf}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Busca um cliente pelo CPF")
    public ResponseEntity<ClienteResponseDto> buscarPorCpf(@PathVariable String cpf) {
        log.info("Buscando cliente por CPF: {}", cpf);
        ClienteResponseDto dto = service.buscarPorCpf(cpf);
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/paginas")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Lista clientes com paginação offset")
    public ResponseEntity<PaginaOffsetClienteResposta> listarComOffset(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "15") int tamanho
    ) {
        log.info("Requisição para paginação offset de clientes: pagina={}, tamanho={}", pagina, tamanho);
        PaginaOffsetClienteResposta resposta = PaginaOffsetClienteResposta.de(
                service.buscarPaginaOffset(pagina, tamanho)
        );
        return ResponseEntity.ok(resposta);
    }
}




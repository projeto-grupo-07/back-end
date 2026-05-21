package school.sptech.crud_proj_v1.service;

import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.dto.Campanha.CampanhaRequestDto;
import school.sptech.crud_proj_v1.dto.Campanha.CampanhaResponseDto;
import school.sptech.crud_proj_v1.enumeration.StatusCampanha;
import school.sptech.crud_proj_v1.mapper.CampanhaMapper;
import school.sptech.crud_proj_v1.projection.EmailProjection;
import school.sptech.crud_proj_v1.dto.RabbitMQ.EmailMessage;
import school.sptech.crud_proj_v1.email.RabbitEmailProducer;
import school.sptech.crud_proj_v1.entity.Campanha;
import school.sptech.crud_proj_v1.entity.Cliente;
import school.sptech.crud_proj_v1.repository.CampanhaRepository;
import school.sptech.crud_proj_v1.repository.ClienteRepository;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
@Slf4j
@RequiredArgsConstructor
public class CampanhaService {
    private final CampanhaRepository campanhaRepository;
    private final ClienteRepository clienteRepository;
    private final RabbitEmailProducer rabbitEmailProducer;
    private final CampanhaMapper campanhaMapper;

    public CampanhaResponseDto criarCampanha(CampanhaRequestDto campanhaRequestDTO) {
        Specification<Cliente> spec = montarFiltro(campanhaRequestDTO);

        List<Cliente> clientesEncontrados = clienteRepository.findAll(spec);

        if (clientesEncontrados.isEmpty()) {
            throw new RuntimeException("Nenhum cliente encontrado para os critérios da campanha.");
        }

        Campanha campanha = campanhaMapper.toEntity(campanhaRequestDTO);
        campanha.setClientes(clientesEncontrados);
        campanha.setStatus(StatusCampanha.PENDENTE);

        return campanhaMapper.toResponseDto(campanhaRepository.save(campanha), campanhaRequestDTO);
    }

    private Specification<Cliente> montarFiltro(CampanhaRequestDto dto) {
        return Specification.allOf(
                comGenero(dto.getGenero()),
                comBairro(dto.getBairro()),
                comCidade(dto.getCidade()),
                comEstado(dto.getEstado()),
                comMesAniversario(dto.getMesAniversario())
        );
    }

    private Specification<Cliente> comGenero(Character genero) {
        return (root, query, cb) -> {
            if (genero == null) return null;
            return cb.equal(root.get("genero"), genero);
        };
    }

    private Specification<Cliente> comBairro(String bairro) {
        return (root, query, cb) -> {
            if (bairro == null || bairro.isBlank()) return null;
            return cb.equal(root.join("endereco", JoinType.LEFT).get("bairro"), bairro);
        };
    }

    private Specification<Cliente> comCidade(String cidade) {
        return (root, query, cb) -> {
            if (cidade == null || cidade.isBlank()) return null;
            return cb.equal(root.join("endereco", JoinType.LEFT).get("cidade"), cidade);
        };
    }

    private Specification<Cliente> comEstado(String estado) {
        return (root, query, cb) -> {
            if (estado == null || estado.isBlank()) return null;
            return cb.equal(root.join("endereco", JoinType.LEFT).get("estado"), estado);
        };
    }

    private Specification<Cliente> comMesAniversario(Integer mes) {
        return (root, query, cb) -> {
            if (mes == null) return null;
            return cb.equal(
                    cb.function("MONTH", Integer.class, root.get("dtNasc")),
                    mes
            );
        };
    }

    public List<CampanhaResponseDto> listarCampanhas() {
        var response = campanhaRepository.findAll();

        return response.stream()
                .map(campanha -> campanhaMapper.toResponseDto(campanha, campanhaMapper.toRequestDto(campanha)))
                .toList();
    }

    @Async
    @Transactional // Garante que a lista de clientes seja carregada do banco sem erro de Lazy Loading
    public void iniciarCampanha(Integer campanhaId) {
        Campanha campanha = campanhaRepository.findById(campanhaId)
                .orElseThrow(() -> new RuntimeException("Campanha com id " + campanhaId + " não encontrada."));

        // 1. Muda o status para não permitir mais edições
        campanha.setStatus(StatusCampanha.EM_ANDAMENTO);
        campanhaRepository.save(campanha);

        // 2. Pega EXATAMENTE os clientes que estão salvos nesta campanha (incluindo os manuais)
        List<Cliente> clientesDaCampanha = campanha.getClientes();

        if (clientesDaCampanha.isEmpty()) {
            campanha.setStatus(StatusCampanha.CONCLUIDA);
            campanhaRepository.save(campanha);
            return;
        }

        int failures = 0;

        // 3. Enfileira o e-mail para cada cliente da lista
        for (Cliente cliente : clientesDaCampanha) {
            // Pula se o cliente estiver sem e-mail cadastrado
            if (cliente.getEmail() == null || cliente.getEmail().isBlank()) {
                continue;
            }

            try {
                log.info("Enfileirando email para {}: Assunto: {}", cliente.getEmail(), campanha.getAssunto());
                rabbitEmailProducer.sendEmail(new EmailMessage(
                        cliente.getEmail(),
                        campanha.getAssunto(),
                        campanha.getCorpoTexto()
                ));
            } catch (Exception e) {
                failures++;
                log.error("Erro ao enfileirar email para {}: {}", cliente.getEmail(), e.getMessage());
            }
        }

        // 4. Finaliza a campanha
        campanha.setStatus(failures == 0 ? StatusCampanha.CONCLUIDA : StatusCampanha.CANCELADA);
        campanhaRepository.save(campanha);
    }

    public void deletarCampanha(Integer id) {
        if (!campanhaRepository.existsById(id)) {
            throw new RuntimeException("Campanha com id " + id + " não encontrada.");
        }
        campanhaRepository.deleteById(id);
    }

    public CampanhaResponseDto buscarCampanhaPorId(Integer id) {
        Campanha campanha = campanhaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campanha não encontrada."));
        return campanhaMapper.toResponseDto(campanha, campanhaMapper.toRequestDto(campanha));
    }

    public CampanhaResponseDto atualizarCampanha(Integer id, CampanhaRequestDto dto) {
        Campanha campanha = campanhaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campanha não encontrada."));

        if (campanha.getStatus() != StatusCampanha.PENDENTE) {
            throw new RuntimeException("Apenas campanhas pendentes podem ser editadas.");
        }

        campanha.setNome(dto.getNome());
        campanha.setAssunto(dto.getAssunto());
        campanha.setCorpoTexto(dto.getCorpoTexto());

        return campanhaMapper.toResponseDto(campanhaRepository.save(campanha), dto);
    }

    public List<Cliente> listarClientesDaCampanha(Integer campanhaId) {
        Campanha campanha = campanhaRepository.findById(campanhaId)
                .orElseThrow(() -> new RuntimeException("Campanha não encontrada."));
        return campanha.getClientes();
    }

    public void adicionarCliente(Integer campanhaId, Integer clienteId) {
        Campanha campanha = campanhaRepository.findById(campanhaId)
                .orElseThrow(() -> new RuntimeException("Campanha não encontrada."));

        if (campanha.getStatus() != StatusCampanha.PENDENTE) {
            throw new RuntimeException("Apenas campanhas pendentes podem ser editadas.");
        }

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        if (!campanha.getClientes().contains(cliente)) {
            campanha.getClientes().add(cliente);
            campanhaRepository.save(campanha);
        }
    }

    public void removerCliente(Integer campanhaId, Integer clienteId) {
        Campanha campanha = campanhaRepository.findById(campanhaId)
                .orElseThrow(() -> new RuntimeException("Campanha não encontrada."));

        if (campanha.getStatus() != StatusCampanha.PENDENTE) {
            throw new RuntimeException("Apenas campanhas pendentes podem ser editadas.");
        }

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        campanha.getClientes().remove(cliente);
        campanhaRepository.save(campanha);
    }

    public List<CampanhaResponseDto> filtrarCampanhas(String assunto, String status) {
        StatusCampanha statusEnum = (status != null && !status.isBlank())
                ? StatusCampanha.valueOf(status)
                : null;

        String assuntoFilter = (assunto != null && !assunto.isBlank()) ? assunto : null;

        List<Campanha> campanhas = campanhaRepository.filtrarCampanhas(assuntoFilter, statusEnum);

        return campanhas.stream()
                .map(campanhaMapper::toDto)
                .toList();
    }
}
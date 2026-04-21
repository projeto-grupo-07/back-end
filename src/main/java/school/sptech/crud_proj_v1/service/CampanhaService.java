package school.sptech.crud_proj_v1.service;

import jakarta.persistence.criteria.JoinType;
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
        if (response.isEmpty()) {
            throw new RuntimeException("Nenhuma campanha encontrada.");
        }

        return response.stream()
                .map(campanha -> campanhaMapper.toResponseDto(campanha, campanhaMapper.toRequestDto(campanha)))
                .toList();
    }

    @Async
    public void iniciarCampanha(Integer campanhaId) {
        Campanha campanha = campanhaRepository.findById(campanhaId)
                .orElseThrow(() -> new RuntimeException("Campanha com id " + campanhaId + " não encontrada."));

        campanha.setStatus(StatusCampanha.EM_ANDAMENTO);
        campanhaRepository.save(campanha);

        CampanhaRequestDto campanhaRequestDTO = campanhaMapper.toRequestDto(campanha);

        Specification<Cliente> spec = montarFiltro(campanhaRequestDTO);

        // obter emails em páginas para evitar carregar todos os clientes na memória
        int page = 0;
        final int size = 1000;
        List<String> emails = new ArrayList<>();
        Page<Cliente> pageResult;
        do {
            PageRequest pr = PageRequest.of(page, size);
            pageResult = clienteRepository.findAll(spec, pr);
            emails.addAll(pageResult.getContent().stream()
                    .map(Cliente::getEmail)
                    .filter(Objects::nonNull)
                    .toList());
            page++;
        } while (page < pageResult.getTotalPages());

        if (emails.isEmpty()) {
            campanha.setStatus(StatusCampanha.CONCLUIDA);
            campanhaRepository.save(campanha);
            return;
        }

        int failures = 0;
        for (String email : emails) {
            try {
                log.info("Enfileirando email para {}: Assunto: {}, Corpo: {}", email, campanha.getAssunto(), campanha.getCorpoTexto());
                rabbitEmailProducer.sendEmail(new EmailMessage(
                        email,
                        campanha.getAssunto(),
                        campanha.getCorpoTexto()
                ));
            } catch (Exception e) {
                failures++;
                log.error("Erro ao enfileirar email para {}: {}", email, e.getMessage());
            }
        }

        campanha.setStatus(failures == 0 ? StatusCampanha.CONCLUIDA : StatusCampanha.CANCELADA);
        campanhaRepository.save(campanha);
    }
}
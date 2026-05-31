package school.sptech.crud_proj_v1.paginacao.infraestrutura;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Cliente.ClienteResponseDto;
import school.sptech.crud_proj_v1.entity.Cliente;
import school.sptech.crud_proj_v1.mapper.ClienteMapper;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginaOffsetCliente;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginacaoStrategy;
import school.sptech.crud_proj_v1.repository.ClienteRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("clienteOffsetStrategy")
@RequiredArgsConstructor
public class ClienteOffsetStrategy implements PaginacaoStrategy<PaginaOffsetCliente> {

    private final ClienteRepository clienteRepository;

    @Override
    public PaginaOffsetCliente paginar(Map<String, Object> parametros) {
        int pagina = (int) parametros.get("pagina");
        int tamanho = (int) parametros.get("tamanho");

        PageRequest pageRequest = PageRequest.of(pagina, tamanho, Sort.by("id").descending());

        long inicio = System.currentTimeMillis();
        Page<Cliente> paginaResultado = clienteRepository.findAll(pageRequest);
        long tempo = System.currentTimeMillis() - inicio;

        List<ClienteResponseDto> conteudo = paginaResultado.getContent().stream()
                .map(ClienteMapper::of)
                .collect(Collectors.toList());

        return new PaginaOffsetCliente(
                conteudo,
                pagina,
                tamanho,
                paginaResultado.getTotalElements(),
                paginaResultado.getTotalPages(),
                tempo
        );
    }
}
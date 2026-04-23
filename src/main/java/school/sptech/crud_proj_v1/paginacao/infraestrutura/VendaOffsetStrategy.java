package school.sptech.crud_proj_v1.paginacao.infraestrutura;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.mapper.VendaMapper;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginaOffsetVenda;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginacaoStrategy;
import school.sptech.crud_proj_v1.repository.VendaRepository;

import java.util.List;
import java.util.Map;

@Component("vendaOffsetStrategy")
@RequiredArgsConstructor
public class VendaOffsetStrategy implements PaginacaoStrategy<PaginaOffsetVenda> {

    private final VendaRepository vendaRepository;
    private final VendaMapper vendaMapper;

    @Override
    public PaginaOffsetVenda paginar(Map<String, Object> parametros) {
        int pagina = (int) parametros.get("pagina");
        int tamanho = (int) parametros.get("tamanho");

        PageRequest pageRequest = PageRequest.of(pagina, tamanho, Sort.by("id").descending());

        long inicio = System.currentTimeMillis();
        Page<Venda> paginaResultado = vendaRepository.findAll(pageRequest);
        long tempo = System.currentTimeMillis() - inicio;

        List<VendaResponseDTO> conteudo = vendaMapper.toVendaResponseDTO(paginaResultado.getContent());

        return new PaginaOffsetVenda(
                conteudo,
                pagina,
                tamanho,
                paginaResultado.getTotalElements(),
                paginaResultado.getTotalPages(),
                tempo
        );
    }
}

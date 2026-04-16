package school.sptech.crud_proj_v1.paginacao.infraestrutura;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.mapper.VendaMapper;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginaCursorVenda;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginacaoStrategy;
import school.sptech.crud_proj_v1.repository.VendaRepository;

import java.util.List;
import java.util.Map;

@Component("vendaCursorStrategy")
@RequiredArgsConstructor
public class VendaCursorStrategy implements PaginacaoStrategy<PaginaCursorVenda> {

    private final VendaRepository vendaRepository;
    private final VendaMapper vendaMapper;

    @Override
    public PaginaCursorVenda paginar(Map<String, Object> parametros) {
        int cursor = (int) parametros.get("cursor");
        int tamanho = (int) parametros.get("tamanho");

        long inicio = System.currentTimeMillis();
        List<Venda> vendas = vendaRepository.findByIdGreaterThanOrderByIdAsc(cursor, PageRequest.of(0, tamanho));
        long tempo = System.currentTimeMillis() - inicio;

        List<VendaResponseDTO> conteudo = vendaMapper.toVendaResponseDTO(vendas);

        Integer proximoCursor = null;
        if (conteudo.size() == tamanho && !conteudo.isEmpty()) {
            proximoCursor = vendas.get(vendas.size() - 1).getId();
        }

        return new PaginaCursorVenda(conteudo, proximoCursor, tamanho, tempo);
    }
}

package school.sptech.crud_proj_v1.mapper;

import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.ItensVenda.ItensVendaResponseDTO;
import school.sptech.crud_proj_v1.entity.ItensVenda;
import java.util.List;

@Component
public class ItensVendaMapper {
    private final ProdutoMapper produtoMapper;

    public ItensVendaMapper(ProdutoMapper produtoMapper) {
        this.produtoMapper = produtoMapper;
    }

    public ItensVendaResponseDTO toProdutosVendaResponseDTO(ItensVenda itemVenda) {
        if (itemVenda == null) {
            return null;
        }

        ItensVendaResponseDTO dto = new ItensVendaResponseDTO();

        dto.setId(itemVenda.getId());
        dto.setQuantidade(itemVenda.getQuantidade());
        dto.setPrecoVenda(itemVenda.getPrecoVenda());

        if (itemVenda.getProduto() != null) {
            dto.setProduto(produtoMapper.toItensVendaDTO(itemVenda.getProduto()));
        }
        return dto;
    }

    public List<ItensVendaResponseDTO> toProdutosVendaResponseDTO(List<ItensVenda> itens) {
        if (itens == null) {
            return List.of();
        }
        return itens.stream()
                .map(this::toProdutosVendaResponseDTO)
                .toList();
    }
}
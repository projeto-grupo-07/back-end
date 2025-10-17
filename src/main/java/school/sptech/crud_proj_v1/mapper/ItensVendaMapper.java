package school.sptech.crud_proj_v1.mapper;

import school.sptech.crud_proj_v1.dto.ItensVenda.ItensVendaResponseDTO;
import school.sptech.crud_proj_v1.entity.ItensVenda;
import java.util.List;

public class ItensVendaMapper {

    public static ItensVendaResponseDTO toProdutosVendaResponseDTO(ItensVenda itemVenda) {
        if (itemVenda == null) {
            return null;
        }

        ItensVendaResponseDTO dto = new ItensVendaResponseDTO();

        dto.setId(itemVenda.getId());
        dto.setQuantidade(itemVenda.getQuantidade());
        dto.setPrecoVenda(itemVenda.getPrecoVenda());

        if (itemVenda.getProduto() != null) {
            dto.setProduto(ProdutoMapper.toItensVendaDTO(itemVenda.getProduto()));
        }

        return dto;
    }

    public static List<ItensVendaResponseDTO> toProdutosVendaResponseDTO(List<ItensVenda> itens) {
        if (itens == null) {
            return List.of();
        }
        return itens.stream()
                .map(ItensVendaMapper::toProdutosVendaResponseDTO)
                .toList();
    }
}
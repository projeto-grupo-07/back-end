package school.sptech.crud_proj_v1.mapper;

import school.sptech.crud_proj_v1.dto.ProdutoVenda.ProdutosVendaResponseDTO;
import school.sptech.crud_proj_v1.entity.ProdutoVenda;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutosVendaMapper {

    public static ProdutosVendaResponseDTO toProdutosVendaResponseDTO(ProdutoVenda itemVenda) {
        if (itemVenda == null) {
            return null;
        }

        ProdutosVendaResponseDTO dto = new ProdutosVendaResponseDTO();

        dto.setId(itemVenda.getId());
        dto.setQuantidade(itemVenda.getQuantidade());
        dto.setPrecoVenda(itemVenda.getPrecoVenda());

        // Mapeamento do Produto (Chamando o ProdutoMapper)
        if (itemVenda.getProduto() != null) {
            dto.setProduto(ProdutoMapper.toProdutoVendaItemDTO(itemVenda.getProduto()));
        }

        return dto;
    }

    public static List<ProdutosVendaResponseDTO> toProdutosVendaResponseDTO(List<ProdutoVenda> itens) {
        if (itens == null) {
            return List.of();
        }
        return itens.stream()
                .map(ProdutosVendaMapper::toProdutosVendaResponseDTO)
                .collect(Collectors.toList());
    }
}
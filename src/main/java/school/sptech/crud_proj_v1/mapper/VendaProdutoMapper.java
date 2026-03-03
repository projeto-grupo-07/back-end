package school.sptech.crud_proj_v1.mapper;

import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.VendaProduto.VendaProdutoResponseDTO;
import school.sptech.crud_proj_v1.entity.ItensVenda;
import java.util.List;

@Component
public class VendaProdutoMapper {
    private final ProdutoMapper produtoMapper;

    public VendaProdutoMapper(ProdutoMapper produtoMapper) {
        this.produtoMapper = produtoMapper;
    }

    public VendaProdutoResponseDTO toProdutosVendaResponseDTO(ItensVenda itemVenda) {
        if (itemVenda == null) {
            return null;
        }

        VendaProdutoResponseDTO dto = new VendaProdutoResponseDTO();

        dto.setId(itemVenda.getId());
        dto.setQuantidadeVendaProduto(itemVenda.getQuantidadeVendaProduto());
        dto.setValorTotalVendaProduto(itemVenda.getValorTotalVendaProduto());

        if (itemVenda.getProduto() != null) {
            dto.setProduto(produtoMapper.toItensVendaDTO(itemVenda.getProduto()));
        }
        return dto;
    }

    public List<VendaProdutoResponseDTO> toProdutosVendaResponseDTO(List<ItensVenda> itens) {
        if (itens == null) {
            return List.of();
        }
        return itens.stream()
                .map(this::toProdutosVendaResponseDTO)
                .toList();
    }
}
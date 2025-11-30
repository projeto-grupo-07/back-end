package school.sptech.crud_proj_v1.mapper;

import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.VendaProduto.VendaProdutoResponseDTO;
import school.sptech.crud_proj_v1.entity.VendaProduto;
import java.util.List;

@Component
public class VendaProdutoMapper {
    private final ProdutoMapper produtoMapper;

    public VendaProdutoMapper(ProdutoMapper produtoMapper) {
        this.produtoMapper = produtoMapper;
    }

    public VendaProdutoResponseDTO toProdutosVendaResponseDTO(VendaProduto itemVenda) {
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

    public List<VendaProdutoResponseDTO> toProdutosVendaResponseDTO(List<VendaProduto> itens) {
        if (itens == null) {
            return List.of();
        }
        return itens.stream()
                .map(this::toProdutosVendaResponseDTO)
                .toList();
    }
}
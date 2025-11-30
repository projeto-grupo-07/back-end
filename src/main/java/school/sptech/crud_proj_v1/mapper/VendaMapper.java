package school.sptech.crud_proj_v1.mapper;

import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.VendaProduto.VendaProdutoResponseDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaRequestDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;
import school.sptech.crud_proj_v1.entity.Venda;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VendaMapper {
    private final VendaProdutoMapper vendaProdutoMapper;

    public VendaMapper(VendaProdutoMapper vendaProdutoMapper) {
        this.vendaProdutoMapper = vendaProdutoMapper;
    }

    public Venda toEntity(VendaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Venda venda = new Venda();
        venda.setFormaDePagamento(dto.getFormaPagamento());
        return venda;
    }

    public VendaResponseDTO toVendaResponseDTO(Venda venda) {
        if (venda == null) {
            return null;
        }

        VendaResponseDTO dto = new VendaResponseDTO();

        dto.setId(venda.getId());
        dto.setValorTotalDaVenda(venda.getTotalVenda());
        dto.setFormaPagamento(venda.getFormaDePagamento());
        dto.setDataHora(venda.getDataHora());

        if (venda.getFuncionario() != null) {
            dto.setIdVendedor(venda.getFuncionario().getId());
        }

        if (venda.getItens() != null) {
            List<VendaProdutoResponseDTO> itensDto =
                    vendaProdutoMapper.toProdutosVendaResponseDTO(venda.getItens());
            dto.setItensDaVenda(itensDto);
        }
        return dto;
    }

    public List<VendaResponseDTO> toVendaResponseDTO(List<Venda> vendas) {
        if (vendas == null) {
            return List.of();
        }
        return vendas.stream()
                .map(this::toVendaResponseDTO)
                .collect(Collectors.toList());
    }
}
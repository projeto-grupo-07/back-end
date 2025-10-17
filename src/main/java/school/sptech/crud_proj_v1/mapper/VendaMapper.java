package school.sptech.crud_proj_v1.mapper;

import school.sptech.crud_proj_v1.dto.ItensVenda.ItensVendaResponseDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaRequestDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;
import school.sptech.crud_proj_v1.entity.Venda;

import java.util.List;
import java.util.stream.Collectors;

public class VendaMapper {

    public static Venda toEntity(VendaRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Venda venda = new Venda();
        venda.setFormaDePagamento(dto.getFormaPagamento());
        return venda;
    }

    public static VendaResponseDTO toVendaResponseDTO(Venda venda) {
        if (venda == null) {
            return null;
        }

        VendaResponseDTO dto = new VendaResponseDTO();

        dto.setId(venda.getId());
        dto.setValorTotal(venda.getTotalVenda());
        dto.setFormaPagamento(venda.getFormaDePagamento());
        dto.setDataHora(venda.getDataHora());

        if (venda.getFuncionario() != null) {
            dto.setIdVendedor(venda.getFuncionario().getId());
        }

        if (venda.getItens() != null) {
            List<ItensVendaResponseDTO> itensDto = venda.getItens().stream()
                    .map(ItensVendaMapper::toProdutosVendaResponseDTO)
                    .collect(Collectors.toList());
            dto.setItensDaVenda(itensDto);
        }
        return dto;
    }

    public static List<VendaResponseDTO> toVendaResponseDTO(List<Venda> vendas) {
        if (vendas == null) {
            return List.of();
        }
        return vendas.stream()
                .map(VendaMapper::toVendaResponseDTO)
                .collect(Collectors.toList());
    }
}
package school.sptech.crud_proj_v1.mapper;

import school.sptech.crud_proj_v1.dto.ProdutosVendaResponseDTO;
import school.sptech.crud_proj_v1.dto.VendaResponseDTO;
import school.sptech.crud_proj_v1.entity.Venda;

import java.util.List;
import java.util.stream.Collectors;

public class VendaMapper {

    public static VendaResponseDTO toVendaResponseDTO(Venda venda) {
        if (venda == null) {
            return null;
        }

        VendaResponseDTO dto = new VendaResponseDTO();

        dto.setId(venda.getId());
        dto.setValorTotal(venda.getValorTotal());
        dto.setFormaPagamento(venda.getFormaPagamento());
        dto.setDataHora(venda.getDataHora());

        if (venda.getVendedor() != null) {
            dto.setIdVendedor(venda.getVendedor().getId());
        }

        if (venda.getCliente() != null) {
            dto.setIdCliente(venda.getCliente().getId());
        }

        if (venda.getProdutos() != null) {
            List<ProdutosVendaResponseDTO> itensDto = venda.getProdutos().stream()
                    .map(ProdutosVendaMapper::toProdutosVendaResponseDTO)
                    .collect(Collectors.toList());
            dto.setProdutos(itensDto);
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
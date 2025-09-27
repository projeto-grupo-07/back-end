package school.sptech.crud_proj_v1.mapper;

import school.sptech.crud_proj_v1.dto.ProdutoVendaItemDTO;
import school.sptech.crud_proj_v1.entity.Produto;

public class ProdutoMapper {

    public static ProdutoVendaItemDTO toProdutoVendaItemDTO(Produto produto) {
        if (produto == null) {
            return null;
        }

        ProdutoVendaItemDTO dto = new ProdutoVendaItemDTO();

        dto.setId(produto.getId());
        dto.setModelo(produto.getModelo());
        dto.setMarca(produto.getMarca());
        dto.setTamanho(produto.getTamanho());
        dto.setCor(produto.getCor());

        return dto;
    }
}
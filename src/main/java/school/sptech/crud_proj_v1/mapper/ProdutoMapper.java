package school.sptech.crud_proj_v1.mapper;

import school.sptech.crud_proj_v1.dto.Produto.ProdutoListDTO;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoRequestDTO;
import school.sptech.crud_proj_v1.dto.ItensVenda.ItensVendaDTO;
import school.sptech.crud_proj_v1.entity.Produto;

import java.util.List;
import java.util.stream.Collectors;

public class ProdutoMapper {

    public static ItensVendaDTO toItensVendaDTO(Produto produto) {
        if (produto == null) {
            return null;
        }

        ItensVendaDTO dto = new ItensVendaDTO();

        dto.setId(produto.getId());
        dto.setModelo(produto.getModelo());
        dto.setMarca(produto.getMarca());
        dto.setTamanho(produto.getTamanho());
        dto.setCor(produto.getCor());

        return dto;
    }

    public static ProdutoListDTO toDTO(Produto produto) {
        if (produto == null) {
            return null;
        }


        ProdutoListDTO dto = new ProdutoListDTO();
        dto.setId(produto.getId());
        dto.setModelo(produto.getModelo());
        dto.setMarca(produto.getMarca());
        dto.setPrecoVenda(produto.getPrecoVenda());

        if (produto.getCategoria() != null) {
            dto.setNomeCategoria(produto.getCategoria().getDescricao());
        }

        return dto;
    }

    public static List<ProdutoListDTO> toListDTO(List<Produto> produtos) {
        if (produtos == null) {
            return null;
        }
        return produtos.stream()
                .map(ProdutoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static Produto toEntity(ProdutoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Produto produto = new Produto();

        if (dto.getModelo() != null) {
            produto.setModelo(dto.getModelo());
        }
        if (dto.getMarca() != null) {
            produto.setMarca(dto.getMarca());
        }
        if (dto.getTamanho() != null) {
            produto.setTamanho(dto.getTamanho());
        }
        if (dto.getCor() != null) {
            produto.setCor(dto.getCor());
        }

        if (dto.getPrecoCusto() != null) {
            produto.setPrecoCusto(dto.getPrecoCusto());
        }
        if (dto.getPrecoVenda() != null) {
            produto.setPrecoVenda(dto.getPrecoVenda());
        }

        return produto;
    }
}
package school.sptech.crud_proj_v1.mapper;

import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaResponseDto;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoResponseDTO;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoRequestDTO;
import school.sptech.crud_proj_v1.dto.ItensVenda.ItensVendaDTO;
import school.sptech.crud_proj_v1.entity.Produto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoMapper {

    private final CategoriaMapper categoriaMapper;

    public ProdutoMapper(CategoriaMapper categoriaMapper) {
        this.categoriaMapper = categoriaMapper;
    }

    public ItensVendaDTO toItensVendaDTO(Produto produto) {
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

    public ProdutoResponseDTO toResponseDTO(Produto produto) {
        if (produto == null) {
            return null;
        }

        ProdutoResponseDTO dto = new ProdutoResponseDTO();
        dto.setId(produto.getId());
        dto.setModelo(produto.getModelo());
        dto.setMarca(produto.getMarca());
        dto.setPrecoVenda(produto.getPrecoVenda());

        if (produto.getCategoria() != null) {
            CategoriaResponseDto categoriaDto = categoriaMapper.toResponseDto(produto.getCategoria());
            dto.setCategoria(categoriaDto);
        }

        return dto;
    }

    public List<ProdutoResponseDTO> produtoResponseDTOS(List<Produto> produtos) {
        if (produtos == null) {
            return null;
        }

        return produtos.stream()
                .map(produto -> toResponseDTO(produto))
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
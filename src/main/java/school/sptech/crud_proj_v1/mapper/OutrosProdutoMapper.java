package school.sptech.crud_proj_v1.mapper;

import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Produto.OutrosProdutoRequest;
import school.sptech.crud_proj_v1.dto.Produto.OutrosProdutoResponse;
import school.sptech.crud_proj_v1.entity.OutrosProduto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OutrosProdutoMapper {
    public OutrosProduto toEntity(OutrosProdutoRequest dto) {
        if (dto == null) {
            return null;
        }

        OutrosProduto entidade = new OutrosProduto();

        entidade.setQuantidade(dto.getQuantidade());
        entidade.setValorUnitario(dto.getValorUnitario());

        entidade.setDescricao(dto.getDescricao());
        entidade.setNome(dto.getNome());

        return entidade;
    }


    public OutrosProdutoResponse toResponse(OutrosProduto entidade) {
        if (entidade == null) return null;

        OutrosProdutoResponse response = new OutrosProdutoResponse();
        response.setId(entidade.getId());
        response.setIdCategoria(entidade.getCategoria().getId());
        response.setQuantidade(entidade.getQuantidade());
        response.setValorUnitario(entidade.getValorUnitario());
        response.setNome(entidade.getNome());
        response.setDescricao(entidade.getDescricao());

        // Adiciona categoriaPai e tipo
        if (entidade.getCategoria().getCategoriaPai() != null) {
            response.setCategoriaPai(entidade.getCategoria().getCategoriaPai().getDescricao());
            response.setTipo(entidade.getCategoria().getCategoriaPai().getDescricao().equalsIgnoreCase("Calçados") ? "calcado" : "outros");
        } else {
            response.setCategoriaPai(entidade.getCategoria().getDescricao());
            response.setTipo("outros");
        }

        return response;
    }

    public List<OutrosProdutoResponse> toResponseList(List<OutrosProduto> OutrosProdutos){
        if (OutrosProdutos == null) {
            return List.of();
        }
        return OutrosProdutos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void toUpdate(OutrosProdutoRequest dto, OutrosProduto entity) {
        entity.setQuantidade(dto.getQuantidade());
        entity.setValorUnitario(dto.getValorUnitario());

        entity.setNome(dto.getNome());
        entity.setDescricao(dto.getDescricao());
    }
}

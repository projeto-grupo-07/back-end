package school.sptech.crud_proj_v1.mapper;

import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Endereco.EnderecoRequestDto;
import school.sptech.crud_proj_v1.dto.Endereco.EnderecoResponseDto;
import school.sptech.crud_proj_v1.dto.Produto.CalcadoProdutoRequest;
import school.sptech.crud_proj_v1.dto.Produto.CalcadoProdutoResponse;
import school.sptech.crud_proj_v1.entity.CalcadoProduto;
import school.sptech.crud_proj_v1.entity.Endereco;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CalcadoProdutoMapper {
    public CalcadoProduto toEntity(CalcadoProdutoRequest dto) {
        if (dto == null) {
            return null;
        }

        CalcadoProduto entidade = new CalcadoProduto();

        entidade.setQuantidade(dto.getQuantidade());
        entidade.setValorUnitario(dto.getValorUnitario());

        entidade.setCor(dto.getCor());
        entidade.setModelo(dto.getModelo());
        entidade.setMarca(dto.getMarca());
        entidade.setNumero(dto.getNumero());

        return entidade;
    }


    public CalcadoProdutoResponse toResponse(CalcadoProduto entidade) {
        if (entidade == null) {
            return null;
        }

        CalcadoProdutoResponse response = new CalcadoProdutoResponse();

        response.setId(entidade.getId());
        response.setQuantidade(entidade.getQuantidade());
        response.setValorUnitario(entidade.getValorUnitario());

        if (entidade.getCategoria() != null) {
            response.setIdCategoria(entidade.getCategoria().getId());
        }


        response.setCor(entidade.getCor());
        response.setModelo(entidade.getModelo());
        response.setMarca(entidade.getMarca());
        response.setNumero(entidade.getNumero());

        return response;
    }

    public List<CalcadoProdutoResponse> toResponseList(List<CalcadoProduto> calcadoProdutos){
        if (calcadoProdutos == null) {
            return List.of();
        }
        return calcadoProdutos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void toUpdate(CalcadoProdutoRequest dto, CalcadoProduto entity) {
        entity.setQuantidade(dto.getQuantidade());
        entity.setValorUnitario(dto.getValorUnitario());

        entity.setModelo(dto.getModelo());
        entity.setMarca(dto.getMarca());
        entity.setCor(dto.getCor());
        entity.setNumero(dto.getNumero());
    }
}


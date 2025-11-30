package school.sptech.crud_proj_v1.mapper;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate; // <--- Importante
import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoResponse;
import school.sptech.crud_proj_v1.entity.CalcadoProduto;
import school.sptech.crud_proj_v1.entity.OutrosProduto;
import school.sptech.crud_proj_v1.entity.abstrato.Produto;

@Component
@RequiredArgsConstructor
public class ProdutoMapper {

    private final CalcadoProdutoMapper calcadoMapper;
    private final OutrosProdutoMapper outrosMapper;

    public ProdutoResponse toItensVendaDTO(Produto produto) {
        Object produtoReal = Hibernate.unproxy(produto);

        return switch (produtoReal) {
            case null -> null;
            case CalcadoProduto calcado -> calcadoMapper.toResponse(calcado);
            case OutrosProduto outros -> outrosMapper.toResponse(outros);

            default -> throw new IllegalArgumentException(
                    "Tipo de produto desconhecido: " + produtoReal.getClass().getName()
            );
        };
    }
}
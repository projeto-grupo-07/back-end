package school.sptech.crud_proj_v1.dto.Produto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.sptech.crud_proj_v1.entity.Categoria;

@Getter
@Setter
@NoArgsConstructor
public class CalcadoProdutoRequest {
    private Integer idCategoria;
    private Integer quantidade;
    private Double valorUnitario;

    private String marca;
    private String modelo;
    private Integer numero;
    private String cor;
}
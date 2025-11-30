package school.sptech.crud_proj_v1.dto.Produto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CalcadoProdutoResponse implements ProdutoResponse {
    private Integer id;
    private Integer idCategoria;
    private Integer quantidade;
    private Double valorUnitario;

    private String marca;
    private String modelo;
    private Integer numero;
    private String cor;

}

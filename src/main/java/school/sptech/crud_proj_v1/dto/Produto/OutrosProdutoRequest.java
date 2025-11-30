package school.sptech.crud_proj_v1.dto.Produto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OutrosProdutoRequest {
    private Integer idCategoria;
    private Integer quantidade;
    private Double valorUnitario;

    private String nome;
    private String descricao;

}

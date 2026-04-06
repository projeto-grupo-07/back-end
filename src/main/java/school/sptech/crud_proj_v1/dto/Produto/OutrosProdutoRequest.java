package school.sptech.crud_proj_v1.dto.Produto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OutrosProdutoRequest {
    @NotNull(message = "A categoria é obrigatória.")
    private Integer idCategoria;

    @NotNull(message = "A quantidade é obrigatória.")
    @Min(value = 0, message = "A quantidade não pode ser negativa.")
    private Integer quantidade;

    @NotNull(message = "O valor unitário é obrigatório.")
    @Positive(message = "O valor unitário deve ser maior que zero.")
    private Double valorUnitario;

    @NotNull(message = "O preço de custo é obrigatório.")
    @Positive(message = "O preço de custo deve ser maior que zero.")
    private Double precoCustoProduto;

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória.")
    private String descricao;

}

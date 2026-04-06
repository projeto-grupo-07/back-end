package school.sptech.crud_proj_v1.dto.Produto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.sptech.crud_proj_v1.entity.Categoria;

@Getter
@Setter
@NoArgsConstructor
public class CalcadoProdutoRequest {
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

    @NotBlank(message = "A marca é obrigatória.")
    private String marca;

    @NotBlank(message = "O modelo é obrigatório.")
    private String modelo;

    @NotNull(message = "O número é obrigatório.")
    @Positive(message = "O número deve ser maior que zero.")
    private Integer numero;

    @NotBlank(message = "A cor é obrigatória.")
    private String cor;
}
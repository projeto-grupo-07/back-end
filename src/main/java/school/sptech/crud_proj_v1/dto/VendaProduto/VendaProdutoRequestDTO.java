package school.sptech.crud_proj_v1.dto.VendaProduto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VendaProdutoRequestDTO {

    @NotNull()
    private Integer idProduto;

    @NotNull()
    @Min(value = 1)
    private Integer quantidadeVendaProduto;

    // ATENÇÃO: este campo é enviado pelo cliente mas ignorado pelo service.
    // O valor total é sempre recalculado no backend com base no valorUnitario do produto.
    // Pode ser removido futuramente ao alinhar com o front-end.
    @NotNull()
    @Min(value = 1)
    private Double valorTotalVendaProduto;

    @Min(value = 0, message = "O valor do desconto não pode ser negativo")
    private Double desconto;
}
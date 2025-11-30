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

    @NotNull()
    @Min(value = 1)
    private Double valorTotalVendaProduto;
}
package school.sptech.crud_proj_v1.dto.VendaProduto;


import lombok.Data;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoResponse;

@Data
public class VendaProdutoResponseDTO {

    private Integer id;
    private Integer quantidadeVendaProduto;
    private Double valorTotalVendaProduto;
    private ProdutoResponse produto;
}
package school.sptech.crud_proj_v1.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProdutoVendaItemRequestDTO {

    @NotNull()
    private Integer idProduto;

    @NotNull()
    @Min(value = 1)
    private Integer quantidade;

    @NotNull()
    @Min(value = 0)
    private BigDecimal precoVenda;

    public ProdutoVendaItemRequestDTO(Integer idProduto, Integer quantidade, BigDecimal precoVenda) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.precoVenda = precoVenda;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }
}
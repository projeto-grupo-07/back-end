package school.sptech.crud_proj_v1.dto.ItensVenda;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItensVendaRequestDTO {

    @NotNull()
    private Integer idProduto;

    @NotNull()
    @Min(value = 1)
    private Integer quantidade;

    @NotNull()
    @Min(value = 0)
    private Double precoVenda;

    public ItensVendaRequestDTO(Integer idProduto, Integer quantidade, Double precoVenda) {
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

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }
}
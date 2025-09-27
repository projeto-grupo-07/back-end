package school.sptech.crud_proj_v1.dto;

import java.math.BigDecimal;

public class ProdutosVendaResponseDTO {

    private Integer id;
    private Integer quantidade;
    private BigDecimal precoVenda;


    private ProdutoVendaItemDTO produto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public ProdutoVendaItemDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoVendaItemDTO produto) {
        this.produto = produto;
    }
}
package school.sptech.crud_proj_v1.dto.ProdutoVenda;

import java.math.BigDecimal;

public class ProdutosVendaResponseDTO {

    private Integer id;
    private Integer quantidade;
    private Double precoVenda;


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

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public ProdutoVendaItemDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoVendaItemDTO produto) {
        this.produto = produto;
    }
}
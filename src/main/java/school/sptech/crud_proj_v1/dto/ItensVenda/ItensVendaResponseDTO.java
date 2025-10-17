package school.sptech.crud_proj_v1.dto.ItensVenda;


public class ItensVendaResponseDTO {

    private Integer id;
    private Integer quantidade;
    private Double precoVenda;


    private ItensVendaDTO produto;

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

    public ItensVendaDTO getProduto() {
        return produto;
    }

    public void setProduto(ItensVendaDTO produto) {
        this.produto = produto;
    }
}
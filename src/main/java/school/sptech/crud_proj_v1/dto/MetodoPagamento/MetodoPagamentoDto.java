package school.sptech.crud_proj_v1.dto.MetodoPagamento;

public class MetodoPagamentoDto {
    private String metodo;
    private Integer qtdVendas;
    private Double valorTotal;
    private Double taxas; // Valor absoluto descontado pela operadora

    public MetodoPagamentoDto(String metodo, Integer qtdVendas, Double valorTotal, Double taxas) {
        this.metodo = metodo;
        this.qtdVendas = qtdVendas;
        this.valorTotal = valorTotal;
        this.taxas = taxas;
    }

    // Getters e Setters
    public String getMetodo() { return metodo; }
    public void setMetodo(String metodo) { this.metodo = metodo; }
    public Integer getQtdVendas() { return qtdVendas; }
    public void setQtdVendas(Integer qtdVendas) { this.qtdVendas = qtdVendas; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    public Double getTaxas() { return taxas; }
    public void setTaxas(Double taxas) { this.taxas = taxas; }

}

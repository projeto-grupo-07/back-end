package school.sptech.crud_proj_v1.dto.Venda;

import school.sptech.crud_proj_v1.dto.ItensVenda.ItensVendaResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public class VendaResponseDTO {

    private Integer id;

    private Integer idVendedor;
    private Double valorTotal;
    private String formaPagamento;
    private LocalDateTime dataHora;

    private List<ItensVendaResponseDTO> itensDaVenda;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public List<ItensVendaResponseDTO> getItensDaVenda() {
        return itensDaVenda;
    }

    public void setItensDaVenda(List<ItensVendaResponseDTO> itensDaVenda) {
        this.itensDaVenda = itensDaVenda;
    }
}
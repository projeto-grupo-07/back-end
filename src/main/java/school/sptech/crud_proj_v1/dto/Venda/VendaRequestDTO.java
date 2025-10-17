package school.sptech.crud_proj_v1.dto.Venda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import school.sptech.crud_proj_v1.dto.ItensVenda.ItensVendaRequestDTO;

import java.util.List;

public class VendaRequestDTO {

    //não tem id pq é request
    private Integer idVendedor;
    @NotBlank()
    private String formaPagamento;

    @NotNull()
    @NotEmpty()
    private List<ItensVendaRequestDTO> itensVenda;

    public VendaRequestDTO(Integer idVendedor, String formaPagamento, List<ItensVendaRequestDTO> itensVenda) {
        this.idVendedor = idVendedor;
        this.formaPagamento = formaPagamento;
        this.itensVenda = itensVenda;
    }

    public VendaRequestDTO() {}

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ItensVendaRequestDTO> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItensVendaRequestDTO> itensVenda) {
        this.itensVenda = itensVenda;
    }
}

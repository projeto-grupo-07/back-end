package school.sptech.crud_proj_v1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import school.sptech.crud_proj_v1.dto.*;

import java.util.List;

public class VendaRequestDTO {

    //não tem id pq é request

    @NotNull()
    private Integer idVendedor;

    @NotNull()
    private Integer idCliente;

    @NotBlank()
    private String formaPagamento;

    @NotNull()
    @NotEmpty()
    private List<ProdutoVendaItemRequestDTO> produtos;

    public VendaRequestDTO(Integer idVendedor, Integer idCliente, String formaPagamento, List<ProdutoVendaItemRequestDTO> produtos) {
        this.idVendedor = idVendedor;
        this.idCliente = idCliente;
        this.formaPagamento = formaPagamento;
        this.produtos = produtos;
    }

    public Integer getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Integer idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ProdutoVendaItemRequestDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoVendaItemRequestDTO> produtos) {
        this.produtos = produtos;
    }
}

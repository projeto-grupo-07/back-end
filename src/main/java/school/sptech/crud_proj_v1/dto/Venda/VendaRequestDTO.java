package school.sptech.crud_proj_v1.dto.Venda;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import school.sptech.crud_proj_v1.dto.ItensVenda.ItensVendaRequestDTO;
import school.sptech.crud_proj_v1.enumeration.FormaDePagamento;

import java.util.List;

@Schema(description = "DTO utilizado para cadastro e atualização de vendas")
public class VendaRequestDTO {

    //não tem id pq é request
    @Schema(description = "Esse campo representa a foreign key do funcionário que fez a venda", example = "1")
    private Integer idVendedor;
    @NotNull()
    @Schema(example = "PIX", description = "Esse campo representa qual foi a forma de pagamento da venda")
    private FormaDePagamento formaPagamento;

    @NotNull()
    @NotEmpty()
    private List<ItensVendaRequestDTO> itensVenda;

    public VendaRequestDTO(Integer idVendedor, FormaDePagamento formaPagamento, List<ItensVendaRequestDTO> itensVenda) {
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

    public FormaDePagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaDePagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ItensVendaRequestDTO> getItensVenda() {
        return itensVenda;
    }

    public void setItensVenda(List<ItensVendaRequestDTO> itensVenda) {
        this.itensVenda = itensVenda;
    }
}

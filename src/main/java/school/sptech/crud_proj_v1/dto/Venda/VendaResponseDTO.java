package school.sptech.crud_proj_v1.dto.Venda;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.crud_proj_v1.dto.ItensVenda.ItensVendaResponseDTO;
import school.sptech.crud_proj_v1.enumeration.FormaDePagamento;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "DTO utilizado para cadastro e atualização de vendas")
public class VendaResponseDTO {

    @Schema(example = "1", description = "Esse campo representa o identificador único dos vendas")
    private Integer id;
    @Schema(example = "1", description = "Esse campo representa a chave estrangeira do funcionário que fez a venda")
    private Integer idVendedor;
    @Schema(example = "239.90", description = "Esse campo representa o valor final da venda a ser pago ")
    private Double valorTotal;
    @Schema(example = "PIX", description = "Esse campo representa qual foi a forma de pagamento da venda")
    private FormaDePagamento formaPagamento;
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

    public FormaDePagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaDePagamento formaPagamento) {
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
package school.sptech.crud_proj_v1.dto.Venda;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.crud_proj_v1.dto.VendaProduto.VendaProdutoResponseDTO;
import school.sptech.crud_proj_v1.enumeration.FormaDePagamento;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "DTO utilizado para retorno de dados das vendas")
public class VendaResponseDTO {

    @Schema(example = "1", description = "Esse campo representa o identificador único da venda")
    private Integer id;

    @Schema(example = "1", description = "Esse campo representa a chave estrangeira do funcionário que fez a venda")
    private Integer idVendedor;

    @Schema(example = "João Silva", description = "Nome do funcionário que realizou a venda")
    private String funcionarioNome;

    @Schema(example = "239.90", description = "Esse campo representa o valor final da venda a ser pago")
    private Double valorTotalDaVenda;

    @Schema(example = "PIX", description = "Esse campo representa qual foi a forma de pagamento da venda")
    private FormaDePagamento formaPagamento;

    private LocalDateTime dataHora;

    // NOVOS CAMPOS DE COMISSÃO
    @Schema(example = "0.10", description = "Percentual de comissão que o vendedor tinha no momento exato desta venda")
    private Double percentualComissaoAplicado;

    @Schema(example = "23.99", description = "Valor em reais (R$) que o vendedor ganhou de comissão nesta venda")
    private Double valorComissao;

    private List<VendaProdutoResponseDTO> itensDaVenda;

    // GETTERS E SETTERS
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

    public String getFuncionarioNome() {
        return funcionarioNome;
    }

    public void setFuncionarioNome(String funcionarioNome) {
        this.funcionarioNome = funcionarioNome;
    }

    public Double getValorTotalDaVenda() {
        return valorTotalDaVenda;
    }

    public void setValorTotalDaVenda(Double valorTotalDaVenda) {
        this.valorTotalDaVenda = valorTotalDaVenda;
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

    public Double getPercentualComissaoAplicado() {
        return percentualComissaoAplicado;
    }

    public void setPercentualComissaoAplicado(Double percentualComissaoAplicado) {
        this.percentualComissaoAplicado = percentualComissaoAplicado;
    }

    public Double getValorComissao() {
        return valorComissao;
    }

    public void setValorComissao(Double valorComissao) {
        this.valorComissao = valorComissao;
    }

    public List<VendaProdutoResponseDTO> getItensDaVenda() {
        return itensDaVenda;
    }

    public void setItensDaVenda(List<VendaProdutoResponseDTO> itensDaVenda) {
        this.itensDaVenda = itensDaVenda;
    }
}
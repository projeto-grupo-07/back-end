    package school.sptech.crud_proj_v1.entity;

    import io.swagger.v3.oas.annotations.media.Schema;
    import jakarta.persistence.*;
    import school.sptech.crud_proj_v1.enumeration.FormaDePagamento;

    import java.time.LocalDateTime;
    import java.util.List;

    @Entity
    public class Venda {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Schema(example = "1", description = "Esse campo representa o identificador único dos vendas. Ele se auto incrementa")
        private Integer id;
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "FK_VENDEDOR")
        @Schema(description = "Esse campo é um objeto da classe Funcionario. Representa o funcionário que fez a venda")
        private Funcionario funcionario;
        @Column(name = "FORMA_PAGAMENTO")
        @Enumerated(EnumType.STRING)
        @Schema(example = "PIX", description = "Esse campo representa qual foi a forma de pagamento da venda (DEBITO, CREDITO, PIX)")
        private FormaDePagamento formaDePagamento;
        @Column(name = "VALOR_TOTAL")
        @Schema(example = "239.90", description = "Esse campo representa o valor final da venda a ser pago ")
        private Double totalVenda;
        @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
        private List<ItensVenda> itens;
        private LocalDateTime dataHora;
        @OneToOne(mappedBy = "venda", cascade = CascadeType.ALL)
        private Comissao comissao;


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Funcionario getFuncionario() {
            return funcionario;
        }

        public void setFuncionario(Funcionario funcionario) {
            this.funcionario = funcionario;
        }

        public List<ItensVenda> getItens() {
            return itens;
        }

        public void setItens(List<ItensVenda> itens) {
            this.itens = itens;
        }

        public Double getTotalVenda() {
            return totalVenda;
        }

        public void setTotalVenda(Double totalVenda) {
            this.totalVenda = totalVenda;
        }

        public FormaDePagamento getFormaDePagamento() {
            return formaDePagamento;
        }

        public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
            this.formaDePagamento = formaDePagamento;
        }

        public LocalDateTime getDataHora() {
            return dataHora;
        }

        public void setDataHora(LocalDateTime dataHora) {
            this.dataHora = dataHora;
        }
    }

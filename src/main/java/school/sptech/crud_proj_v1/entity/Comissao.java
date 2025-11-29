package school.sptech.crud_proj_v1.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Comissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComissao;

    private Double valorComissao;

    private LocalDateTime dataVenda;

    @OneToOne
    @JoinColumn(name = "fkVenda")
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "fkFuncionario")
    private Funcionario funcionario;

    public Integer getIdComissao() {
        return idComissao;
    }

    public void setIdComissao(Integer idComissao) {
        this.idComissao = idComissao;
    }

    public Double getValorComissao() {
        return valorComissao;
    }

    public void setValorComissao(Double valorComissao) {
        this.valorComissao = valorComissao;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Venda getIdVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario idFuncionario) {
        this.funcionario = idFuncionario;
    }
}





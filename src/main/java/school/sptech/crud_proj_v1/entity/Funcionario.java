package school.sptech.crud_proj_v1.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", description = "Esse campo representa o identificador único dos funcionários. Ele se auto incrementa")
    private Integer id;

    @NotBlank
    @Size(min = 2, max = 50)
    @Schema(example = "Alexandre Lima", description = "Esse campo representa o nome do funcionário")
    private String nome;

    @CPF
    @NotBlank
    @Schema(example = "734.698.110-32", description = "Esse campo representa o CPF do funcionário, é importante que siga o padrão estabelecido: XXX.XXX.XXX-XX")
    private String cpf;

    @Positive
    @Schema(example = "2025.50", description = "Esse campo representa o salário do funcionário, é importante que siga o padrão estabelecido: XXXX.XX")
    private Double salario;

    @NotNull
    @Email
    @Schema(example = "alexandre.lima@gmail.com", description = "Esse campo representa o email do funcionário, é importante que siga o padrão estabelecido: XXXXXXX@XXXXXXX.com")
    private String email;

    @Schema(example = "0.10", description = "Esse campo representa o percentual da comissão que o funcionário recebe para realizar o cálculo acima de cada venda, é importante que siga o padrão estabelecido: 0.XX")
    private Double comissao;

    @Schema(description = "Esse campo representa a senha do funcionário")
    private String senha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Double getComissao() {
        return comissao;
    }

    public void setComissao(Double comissao) {
        this.comissao = comissao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

package school.sptech.crud_proj_v1.dto.Funcionario;


import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

public class FuncionarioRequestDto {
    @NotBlank
    @Size(min = 2, max = 50)
    private String nome;

    @CPF
    @NotBlank
    private String cpf;

    @Positive
    private Double salario;

    @NotNull
    @Email
    private String email;

    @NotBlank
    private String senha;

    @NotNull
    private Double comissao;

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

    public Double getComissao() {
        return comissao;
    }

    public void setComissao(Double comissao) {
        this.comissao = comissao;
    }
}

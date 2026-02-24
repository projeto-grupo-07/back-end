package school.sptech.crud_proj_v1.dto.Funcionario;


import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;
import school.sptech.crud_proj_v1.dto.Perfil.PerfilResponseDto;

public class FuncionarioResponseDto {
    private Integer id;
    private String nome;
    private String cpf;
    private Double salario;
    private String email;
    private Double comissao;
    private PerfilResponseDto perfil;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getComissao() {
        return comissao;
    }

    public void setComissao(Double comissao) {
        this.comissao = comissao;
    }

    public PerfilResponseDto getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilResponseDto perfil) {
        this.perfil = perfil;
    }
}

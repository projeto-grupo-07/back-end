package school.sptech.crud_proj_v1.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
public class Funcionario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nome;

    @CPF
    private String cpf;

    @Positive
    private Double salario;

    @NotNull
    @Email
    private String email;

    private Double comissao;

    private String senha;


    @ManyToOne
    @JoinColumn(name = "perfil_id") // Cria a FK no banco
    private Perfil perfil;

    private Boolean ativo = true;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.perfil == null) return List.of();
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.perfil.getNome()));
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public Double getSalario() { return salario; }
    public void setSalario(Double salario) { this.salario = salario; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Double getComissao() { return comissao; }
    public void setComissao(Double comissao) { this.comissao = comissao; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Perfil getPerfil() { return perfil; }
    public void setPerfil(Perfil perfil) { this.perfil = perfil; }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override public String getPassword() { return this.senha; }
    @Override public String getUsername() { return this.email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
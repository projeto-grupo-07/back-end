package school.sptech.crud_proj_v1.dto.Funcionario;

import school.sptech.crud_proj_v1.dto.Tela.TelaDto;
import java.util.List;

public class FuncionarioTokenDto {
    private Integer id;
    private String email;
    private String nome;
    private String token;
    private String perfil;

    private List<TelaDto> menu;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    public List<TelaDto> getMenu() { return menu; }
    public void setMenu(List<TelaDto> menu) { this.menu = menu; }
}
package school.sptech.crud_proj_v1.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String nome;

    private String descricao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "perfil_tela",
            joinColumns = @JoinColumn(name = "perfil_id"),
            inverseJoinColumns = @JoinColumn(name = "tela_id")
    )
    private List<Tela> telas;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public List<Tela> getTelas() { return telas; }
    public void setTelas(List<Tela> telas) { this.telas = telas; }
}
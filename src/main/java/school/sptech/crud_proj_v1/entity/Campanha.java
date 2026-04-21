package school.sptech.crud_proj_v1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import school.sptech.crud_proj_v1.enumeration.StatusCampanha;

import java.util.List;

@Entity
@Data
public class Campanha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nome;

    @NotBlank
    private String assunto;

    @Column(columnDefinition = "TEXT")
    private String corpoTexto;

    @ManyToMany
    @JoinTable(
            name = "campanha_cliente",
            joinColumns = @JoinColumn(name = "campanha_id"),
            inverseJoinColumns = @JoinColumn(name = "cliente_id")
    )
    private List<Cliente> clientes;

    private StatusCampanha status;
}

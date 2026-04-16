package school.sptech.crud_proj_v1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "1", description = "Esse campo representa um identificador único da categoria. Ele se auto incrementa")
    private Integer id;

    @NotBlank
    @Size(max = 45)
    @Schema(example = "Calçados", description = "Esse campo representa a descrição do produto da loja, se ele é um 'Calçado' ou 'Outro'")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "fk_pai")
    @JsonBackReference
    private Categoria categoriaPai;

    @OneToMany(mappedBy = "categoriaPai")
    @JsonManagedReference
    private List<Categoria> subcategorias;


}
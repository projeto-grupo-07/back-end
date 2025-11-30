package school.sptech.crud_proj_v1.entity;

import school.sptech.crud_proj_v1.entity.abstrato.Produto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("CALCADO")
@Getter
@Setter
@NoArgsConstructor
public class CalcadoProduto extends Produto {
    private String marca;
    private String modelo;
    private Integer numero;
    private String cor;
}

package school.sptech.crud_proj_v1.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.sptech.crud_proj_v1.entity.abstrato.Produto;

@Entity
@DiscriminatorValue("OUTROS")
@Getter
@Setter
@NoArgsConstructor
public class OutrosProduto extends Produto {
    private String nome;
    private String descricao;
}

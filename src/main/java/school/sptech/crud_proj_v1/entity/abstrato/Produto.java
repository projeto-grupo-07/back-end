package school.sptech.crud_proj_v1.entity.abstrato;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DiscriminatorFormula;
import school.sptech.crud_proj_v1.entity.Categoria;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula("""
    COALESCE(
        (
            SELECT CASE 
                WHEN c.fk_pai = 1 OR c.id = 1 THEN 'CALCADO' 
                ELSE 'OUTROS'
            END
            FROM categoria c 
            WHERE c.id = fk_categoria 
        ),
    'OUTROS')
""")
public abstract class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer quantidade;
    private Double valorUnitario;
    @ManyToOne
    @JoinColumn(name = "fk_categoria")
    private Categoria categoria;
}
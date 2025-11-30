package school.sptech.crud_proj_v1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.sptech.crud_proj_v1.entity.abstrato.Produto;

@Getter
@Setter
@Entity
@Table(name = "ITENS_VENDA")
@NoArgsConstructor
public class VendaProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // Foreign keys
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_venda")
    private Venda venda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_produto")
    private Produto produto;

    private Integer quantidadeVendaProduto;
    private Double valorTotalVendaProduto;
}
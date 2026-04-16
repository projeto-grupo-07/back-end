package school.sptech.crud_proj_v1.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Comissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComissao;

    private Double valorComissao;

    private LocalDateTime dataVenda;

    @OneToOne
    @JoinColumn(name = "fkVenda")
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "fkFuncionario")
    private Funcionario funcionario;


}





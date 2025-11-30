package school.sptech.crud_proj_v1.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "ID do Produto", example = "1", description = "Esse campo representa o identificador único dos produtos. Ele se auto incrementa")
    private Integer id;

    @ManyToOne
    @Schema(description = "Esse campo é um objeto da classe Categoria. Representa a categoria do produto")
    private Categoria categoria;

    @Schema(example = "123.99", description = "Esse campo representa o valor que o produto custa")
    private Double precoCusto;

    @Schema(example = "122.99", description = "Esse campo representa o valor que o produto foi vendido")
    private Double precoVenda;

    @Schema(example = "Nike", description = "Esse campo representa a marca do produto")
    private String marca;

    @Schema(example = "Court Vision Low", description = "Esse campo representa o modelo do produto")
    private String modelo;

    @Schema(example = "40", description = "Esse campo representa o tamanho do produto")
    private String tamanho;

    @Schema(example = "Preto", description = "Esse campo representa a cor do produto")
    private String cor;

    @Schema(example = "50", description = "Quantidade disponível em estoque")
    private Integer quantidadeEstoque; // <-- novo campo

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<ItensVenda> vendas;


}

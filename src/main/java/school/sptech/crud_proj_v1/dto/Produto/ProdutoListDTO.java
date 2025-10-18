package school.sptech.crud_proj_v1.dto.Produto;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.crud_proj_v1.entity.Categoria;
import school.sptech.crud_proj_v1.entity.Produto;

@Schema(description = "DTO utilizado para exibir as informações dos produtos")
public class ProdutoListDTO {

    @Schema(name = "ID do Produto", example = "1", description = "Esse campo representa o identificador único dos produtos. Ele se auto incrementa")
    private Integer id;
    @Schema(example = "Court Vision Low", description = "Esse campo representa o modelo do produto")
    private String modelo;
    @Schema(example = "Nike", description = "Esse campo representa a marca do produto")
    private String marca;
    @Schema(example = "122.99", description = "Esse campo representa o valor que o produto foi vendido")
    private Double precoVenda;
    @Schema(description = "Esse campo é um objeto da classe Categoria. Representa a categoria do produto")
    private String nomeCategoria;


    public ProdutoListDTO() {
    }

    public ProdutoListDTO(Produto produto) {
        this.id = produto.getId();
        this.modelo = produto.getModelo();
        this.marca = produto.getMarca();
        this.precoVenda = produto.getPrecoVenda();

        if (produto.getCategoria() != null) {
            this.nomeCategoria = produto.getCategoria().getDescricao();
        } else {
            this.nomeCategoria = null;
        }
    }

    public ProdutoListDTO(Integer id, String modelo, String marca, Double precoVenda, Categoria categoria) {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}
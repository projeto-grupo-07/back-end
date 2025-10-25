package school.sptech.crud_proj_v1.dto.Produto;

import io.swagger.v3.oas.annotations.media.Schema;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaResponseDto;
import school.sptech.crud_proj_v1.entity.Categoria;
import school.sptech.crud_proj_v1.entity.Produto;

@Schema(description = "DTO utilizado para exibir as informações dos produtos")
public class ProdutoResponseDTO {

    @Schema(name = "ID do Produto", example = "1", description = "Esse campo representa o identificador único dos produtos. Ele se auto incrementa")
    private Integer id;
    @Schema(example = "Court Vision Low", description = "Esse campo representa o modelo do produto")
    private String modelo;
    @Schema(example = "Nike", description = "Esse campo representa a marca do produto")
    private String marca;
    @Schema(example = "122.99", description = "Esse campo representa o valor que o produto foi vendido")
    private Double precoVenda;
    @Schema(description = "Esse campo é um Response Dto da classe Categoria. Representa a categoria do produto como resposta")
    private CategoriaResponseDto categoria;

    public ProdutoResponseDTO(Integer id, String modelo, String marca, Double precoVenda, CategoriaResponseDto categoria) {
        this.id = id;
        this.modelo = modelo;
        this.marca = marca;
        this.precoVenda = precoVenda;
        this.categoria = categoria;
    }

    public ProdutoResponseDTO() {
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

    public CategoriaResponseDto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaResponseDto categoria) {
        this.categoria = categoria;
    }
}
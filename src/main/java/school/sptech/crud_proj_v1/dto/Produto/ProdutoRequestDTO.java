package school.sptech.crud_proj_v1.dto.Produto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO utilizado para cadastro e atualização de produtos")
public class ProdutoRequestDTO {
    @Size(min = 3, max = 50, message = "O modelo deve ter entre 3 e 50 caracteres.")
    @Schema(example = "Court Vision Low", description = "Esse campo representa o modelo do produto")
    private String modelo;

    @Size(min = 2, max = 50, message = "A marca deve ter entre 2 e 50 caracteres.")
    @Schema(example = "Nike", description = "Esse campo representa a marca do produto")
    private String marca;

    @Schema(example = "40", description = "Esse campo representa o tamanho do produto")
    private String tamanho;

    @Schema(example = "Preto", description = "Esse campo representa a cor do produto")
    private String cor;

    @NotNull(message = "O preço de custo é obrigatório.")
    @Positive(message = "O preço de custo deve ser um valor positivo.")
    @Schema(example = "123.99", description = "Esse campo representa o valor que o produto custa")
    private Double precoCusto;

    @NotNull(message = "O preço de venda é obrigatório.")
    @Positive(message = "O preço de venda deve ser um valor positivo.")
    @Schema(example = "122.99", description = "Esse campo representa o valor que o produto foi vendido")
    private Double precoVenda;


    @Schema(example = "1", description = "Esse campo representa o id da categoria do produto")
    private Integer categoriaId;

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

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(Double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }
}

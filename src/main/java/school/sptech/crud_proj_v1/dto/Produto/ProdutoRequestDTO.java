package school.sptech.crud_proj_v1.dto.Produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProdutoRequestDTO {
    @Size(min = 3, max = 50, message = "O modelo deve ter entre 3 e 50 caracteres.")
    private String modelo;

    @Size(min = 2, max = 50, message = "A marca deve ter entre 2 e 50 caracteres.")
    private String marca;

    private String tamanho;

    private String cor;

    @NotNull(message = "O preço de custo é obrigatório.")
    @Positive(message = "O preço de custo deve ser um valor positivo.")
    private Double precoCusto;

    @NotNull(message = "O preço de venda é obrigatório.")
    @Positive(message = "O preço de venda deve ser um valor positivo.")
    private Double precoVenda;


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

package school.sptech.crud_proj_v1.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "ID do Produto", example = "1", description = "Esse campo representa o identificador único dos produtos. Ele se auto incrementa")
    private Integer id;
//    private String nome;
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
    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<ItensVenda> vendas;

    public Produto(){}

    public Produto(Integer id, Categoria categoria, Double precoCusto, Double precoVenda, String marca, String modelo, String tamanho, String cor) {
        this.id = id;
//        this.nome = nome;
        this.categoria = categoria;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.marca = marca;
        this.modelo = modelo;
        this.tamanho = tamanho;
        this.cor = cor;
        this.vendas = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

//    public String getNome() {
//        return nome;
//    }
//
//    public void setNome(String nome) {
//        this.nome = nome;
//    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
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

    public List<ItensVenda> getVendas() {
        return vendas;
    }

    public void setVendas(List<ItensVenda> vendas) {
        this.vendas = vendas;
    }
}

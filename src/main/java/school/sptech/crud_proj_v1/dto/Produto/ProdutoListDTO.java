package school.sptech.crud_proj_v1.dto.Produto;

import school.sptech.crud_proj_v1.entity.Produto;

public class ProdutoListDTO {

    private Integer id;
    private String modelo;
    private String marca;
    private Double precoVenda;
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
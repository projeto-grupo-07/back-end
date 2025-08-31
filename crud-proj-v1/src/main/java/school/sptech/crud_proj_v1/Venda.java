package school.sptech.crud_proj_v1;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeVendedor;
//    @ManyToMany
//    private List<Produto> produtoList;
    private Double totalVenda;
    private LocalDate dataVenda;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeVendedor() {
        return nomeVendedor;
    }

    public void setNomeVendedor(String nomeVendedor) {
        this.nomeVendedor = nomeVendedor;
    }

//    public List<Produto> getProdutoList() {
//        return produtoList;
//    }
//
//    public void setProdutoList(List<Produto> produtoList) {
//        this.produtoList = produtoList;
//    }

    public Double getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(Double totalVenda) {
        this.totalVenda = totalVenda;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }
}

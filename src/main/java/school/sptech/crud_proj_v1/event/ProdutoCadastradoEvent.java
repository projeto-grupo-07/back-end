package school.sptech.crud_proj_v1.event;

import school.sptech.crud_proj_v1.entity.Produto;

public class ProdutoCadastradoEvent {
    private final Produto produto;

    public ProdutoCadastradoEvent(Produto produto) {
        this.produto = produto;
    }

    public Produto getProduto() {
        return produto;
    }
}

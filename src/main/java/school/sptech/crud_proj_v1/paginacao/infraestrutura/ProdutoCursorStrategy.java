package school.sptech.crud_proj_v1.paginacao.infraestrutura;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoResponse;
import school.sptech.crud_proj_v1.entity.CalcadoProduto;
import school.sptech.crud_proj_v1.entity.OutrosProduto;
import school.sptech.crud_proj_v1.entity.abstrato.Produto;
import school.sptech.crud_proj_v1.mapper.CalcadoProdutoMapper;
import school.sptech.crud_proj_v1.mapper.OutrosProdutoMapper;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginaCursorProduto;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginacaoStrategy;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component("produtoCursorStrategy")
@RequiredArgsConstructor
public class ProdutoCursorStrategy implements PaginacaoStrategy<PaginaCursorProduto> {

    private final ProdutoRepository produtoRepository;
    private final CalcadoProdutoMapper calcadoMapper;
    private final OutrosProdutoMapper outrosMapper;

    @Override
    public PaginaCursorProduto paginar(Map<String, Object> parametros) {
        int cursor  = (int) parametros.get("cursor");
        int tamanho = (int) parametros.get("tamanho");

        long inicio = System.currentTimeMillis();
        List<Produto> produtos = produtoRepository.findByIdGreaterThanAndAtivoTrueOrderByIdAsc(cursor, tamanho);
        long tempo = System.currentTimeMillis() - inicio;

        List<ProdutoResponse> conteudo = produtos.stream()
                .map(produto -> {
                    if (produto instanceof CalcadoProduto calcado) {
                        return (ProdutoResponse) calcadoMapper.toResponse(calcado);
                    } else if (produto instanceof OutrosProduto outros) {
                        return (ProdutoResponse) outrosMapper.toResponse(outros);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .toList();

        Integer proximoCursor = null;
        if (conteudo.size() == tamanho && !conteudo.isEmpty()) {
            proximoCursor = produtos.get(produtos.size() - 1).getId();
        }

        return new PaginaCursorProduto(conteudo, proximoCursor, tamanho, tempo);
    }
}
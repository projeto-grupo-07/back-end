package school.sptech.crud_proj_v1.paginacao.infraestrutura;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoResponse;
import school.sptech.crud_proj_v1.entity.CalcadoProduto;
import school.sptech.crud_proj_v1.entity.OutrosProduto;
import school.sptech.crud_proj_v1.entity.abstrato.Produto;
import school.sptech.crud_proj_v1.mapper.CalcadoProdutoMapper;
import school.sptech.crud_proj_v1.mapper.OutrosProdutoMapper;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginaOffsetProduto;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginacaoStrategy;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component("produtoOffsetStrategy")
@RequiredArgsConstructor
public class ProdutoOffsetStrategy implements PaginacaoStrategy<PaginaOffsetProduto> {

    private final ProdutoRepository produtoRepository;
    private final CalcadoProdutoMapper calcadoMapper;
    private final OutrosProdutoMapper outrosMapper;

    @Override
    public PaginaOffsetProduto paginar(Map<String, Object> parametros) {
        int pagina  = (int) parametros.get("pagina");
        int tamanho = (int) parametros.get("tamanho");

        PageRequest pageRequest = PageRequest.of(pagina, tamanho, Sort.by("id").ascending());

        long inicio = System.currentTimeMillis();
        Page<Produto> paginaResultado = produtoRepository.findAllByAtivoTrue(pageRequest);
        long tempo = System.currentTimeMillis() - inicio;

        List<ProdutoResponse> conteudo = paginaResultado.getContent().stream()
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

        return new PaginaOffsetProduto(
                conteudo,
                pagina,
                tamanho,
                paginaResultado.getTotalElements(),
                paginaResultado.getTotalPages(),
                tempo
        );
    }
}
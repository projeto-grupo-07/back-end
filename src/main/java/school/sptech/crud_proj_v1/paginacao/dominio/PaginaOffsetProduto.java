package school.sptech.crud_proj_v1.paginacao.dominio;

import school.sptech.crud_proj_v1.dto.Produto.ProdutoResponse;

import java.util.List;

public record PaginaOffsetProduto(
        List<ProdutoResponse> conteudo,
        int pagina,
        int tamanho,
        long total,
        long totalPaginas,
        long tempoConsultaMs
) {}
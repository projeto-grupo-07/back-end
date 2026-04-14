package school.sptech.crud_proj_v1.dto.paginacao;

import school.sptech.crud_proj_v1.dto.Produto.ProdutoResponse;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginaOffsetProduto;

import java.util.List;

public record PaginaOffsetProdutoResposta(
        List<ProdutoResponse> conteudo,
        int pagina,
        int tamanho,
        long total,
        long totalPaginas,
        long tempoConsultaMs
) {
    public static PaginaOffsetProdutoResposta de(PaginaOffsetProduto dominio) {
        return new PaginaOffsetProdutoResposta(
                dominio.conteudo(),
                dominio.pagina(),
                dominio.tamanho(),
                dominio.total(),
                dominio.totalPaginas(),
                dominio.tempoConsultaMs()
        );
    }
}
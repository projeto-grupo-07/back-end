package school.sptech.crud_proj_v1.paginacao.dominio;

import school.sptech.crud_proj_v1.dto.Produto.ProdutoResponse;

import java.util.List;

public record PaginaCursorProduto(
        List<ProdutoResponse> conteudo,
        Integer proximoCursor,
        int tamanho,
        long tempoConsultaMs
) {}
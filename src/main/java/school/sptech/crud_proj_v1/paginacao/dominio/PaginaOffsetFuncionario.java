package school.sptech.crud_proj_v1.paginacao.dominio;

import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioResponseDto;

import java.util.List;

public record PaginaOffsetFuncionario(
        List<FuncionarioResponseDto> conteudo,
        int pagina,
        int tamanho,
        long totalElementos,
        int totalPaginas,
        long tempoProcessamento
) {
}
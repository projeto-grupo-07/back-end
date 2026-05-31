package school.sptech.crud_proj_v1.dto.paginacao;

import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioResponseDto;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginaOffsetFuncionario;
import java.util.List;

public record PaginaOffsetFuncionarioResposta(
        List<FuncionarioResponseDto> conteudo,
        int pagina,
        int tamanho,
        long totalElementos,
        int totalPaginas
) {
    public static PaginaOffsetFuncionarioResposta de(PaginaOffsetFuncionario paginaOffset) {
        return new PaginaOffsetFuncionarioResposta(
                paginaOffset.conteudo(),
                paginaOffset.pagina(),
                paginaOffset.tamanho(),
                paginaOffset.totalElementos(),
                paginaOffset.totalPaginas()
        );
    }
}
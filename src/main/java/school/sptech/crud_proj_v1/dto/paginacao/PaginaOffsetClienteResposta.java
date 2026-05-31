package school.sptech.crud_proj_v1.dto.paginacao;

import school.sptech.crud_proj_v1.dto.Cliente.ClienteResponseDto;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginaOffsetCliente;
import java.util.List;

public record PaginaOffsetClienteResposta(
        List<ClienteResponseDto> conteudo,
        int pagina,
        int tamanho,
        long totalElementos,
        int totalPaginas
) {
    public static PaginaOffsetClienteResposta de(PaginaOffsetCliente paginaOffset) {
        return new PaginaOffsetClienteResposta(
                paginaOffset.conteudo(),
                paginaOffset.pagina(),
                paginaOffset.tamanho(),
                paginaOffset.totalElementos(),
                paginaOffset.totalPaginas()
        );
    }
}
package school.sptech.crud_proj_v1.paginacao.dominio;

import school.sptech.crud_proj_v1.dto.Cliente.ClienteResponseDto;
import java.util.List;

public record PaginaOffsetCliente(
        List<ClienteResponseDto> conteudo,
        int pagina,
        int tamanho,
        long totalElementos,
        int totalPaginas,
        long tempoProcessamento
) {
}
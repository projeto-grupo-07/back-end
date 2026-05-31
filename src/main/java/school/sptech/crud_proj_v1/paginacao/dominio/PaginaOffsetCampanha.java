package school.sptech.crud_proj_v1.paginacao.dominio;

import school.sptech.crud_proj_v1.dto.Campanha.CampanhaResponseDto;
import java.util.List;

public record PaginaOffsetCampanha(
        List<CampanhaResponseDto> conteudo,
        int pagina,
        int tamanho,
        long totalElementos,
        int totalPaginas,
        long tempoProcessamento
) {
}
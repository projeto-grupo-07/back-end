package school.sptech.crud_proj_v1.dto.paginacao;

import school.sptech.crud_proj_v1.dto.Campanha.CampanhaResponseDto;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginaOffsetCampanha;
import java.util.List;

public record PaginaOffsetCampanhaResposta(
        List<CampanhaResponseDto> conteudo,
        int pagina,
        int tamanho,
        long totalElementos,
        int totalPaginas
) {
    public static PaginaOffsetCampanhaResposta de(PaginaOffsetCampanha paginaOffset) {
        return new PaginaOffsetCampanhaResposta(
                paginaOffset.conteudo(),
                paginaOffset.pagina(),
                paginaOffset.tamanho(),
                paginaOffset.totalElementos(),
                paginaOffset.totalPaginas()
        );
    }
}
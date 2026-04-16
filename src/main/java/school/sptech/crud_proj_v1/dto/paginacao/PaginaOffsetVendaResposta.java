package school.sptech.crud_proj_v1.dto.paginacao;

import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginaOffsetVenda;

import java.util.List;

public record PaginaOffsetVendaResposta(
        List<VendaResponseDTO> conteudo,
        int pagina,
        int tamanho,
        long total,
        long totalPaginas,
        long tempoConsultaMs
) {
    public static PaginaOffsetVendaResposta de(PaginaOffsetVenda dominio) {
        return new PaginaOffsetVendaResposta(
                dominio.conteudo(),
                dominio.pagina(),
                dominio.tamanho(),
                dominio.total(),
                dominio.totalPaginas(),
                dominio.tempoConsultaMs()
        );
    }
}

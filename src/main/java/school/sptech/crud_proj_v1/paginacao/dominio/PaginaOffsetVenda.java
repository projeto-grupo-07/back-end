package school.sptech.crud_proj_v1.paginacao.dominio;

import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;

import java.util.List;

public record PaginaOffsetVenda(
        List<VendaResponseDTO> conteudo,
        int pagina,
        int tamanho,
        long total,
        long totalPaginas,
        long tempoConsultaMs
) {}

package school.sptech.crud_proj_v1.paginacao.dominio;

import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;

import java.util.List;

public record PaginaCursorVenda(
        List<VendaResponseDTO> conteudo,
        Integer proximoCursor,
        int tamanho,
        long tempoConsultaMs
) {}

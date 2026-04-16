package school.sptech.crud_proj_v1.dto.paginacao;

import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;
import school.sptech.crud_proj_v1.paginacao.dominio.PaginaCursorVenda;

import java.util.Base64;
import java.util.List;

public record


PaginaCursorVendaResposta(
        List<VendaResponseDTO> conteudo,
        String proximoCursor,
        int tamanho,
        long tempoConsultaMs
) {
    public static String codificarCursor(Integer id) {
        if (id == null) return null;
        return Base64.getEncoder().encodeToString(String.valueOf(id).getBytes());
    }

    public static int decodificarCursor(String cursorBase64) {
        if (cursorBase64 == null || cursorBase64.isBlank()) return 0;
        return Integer.parseInt(new String(Base64.getDecoder().decode(cursorBase64)));
    }

    public static PaginaCursorVendaResposta de(PaginaCursorVenda dominio) {
        return new PaginaCursorVendaResposta(
                dominio.conteudo(),
                codificarCursor(dominio.proximoCursor()),
                dominio.tamanho(),
                dominio.tempoConsultaMs()
        );
    }
}

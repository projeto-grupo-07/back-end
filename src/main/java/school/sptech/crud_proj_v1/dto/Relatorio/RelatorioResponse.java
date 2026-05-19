package school.sptech.crud_proj_v1.dto.Relatorio;

import java.util.List;

public record RelatorioResponse(
        String status,
        List<String> arquivos,
        String tempo_execucao
) {
}

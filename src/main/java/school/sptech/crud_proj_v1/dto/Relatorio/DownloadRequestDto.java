package school.sptech.crud_proj_v1.dto.Relatorio;

public record DownloadRequestDto(
        Integer mes,
        Integer ano,
        String jobId
) {
}

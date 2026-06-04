package school.sptech.crud_proj_v1.dto.Kpi;

public record KpiResumoResponse(
        Double faturamento,
        Double desconto,
        Integer totalVendas,
        Integer unidadesVendidas,
        Double ticketMedio
) {
}

package school.sptech.crud_proj_v1.projection;

import java.math.BigDecimal;

public interface GraficoTempoProjection {
    Integer getAno();
    Integer getMes();
    Integer getSemana();
    BigDecimal getFaturamentoTotal();
}

package school.sptech.crud_proj_v1.projection;

import java.math.BigDecimal;

public interface DesempenhoFuncionarioProjection {
    String getVendedor();
    Integer getTotalVendas();
    BigDecimal getTotalFaturado();
    BigDecimal getComissaoTotal();
}

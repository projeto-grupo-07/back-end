package school.sptech.crud_proj_v1.projection;

import java.math.BigDecimal;

public interface MetodoPagamentoProjection {
    String getMetodo();
    Integer getQtdVendas();
    BigDecimal getValorTotal();
}

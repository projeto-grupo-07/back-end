package school.sptech.crud_proj_v1.paginacao.dominio;

import java.util.Map;

public interface PaginacaoStrategy<R> {
    R paginar(Map<String, Object> parametros);
}
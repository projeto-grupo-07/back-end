package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.crud_proj_v1.entity.PagamentoComissao;

import java.util.List;

public interface PagamentoComissaoRepository extends JpaRepository<PagamentoComissao, Integer> {

    // 1. Busca todo o histórico de pagamentos de um funcionário (Do mais recente para o mais antigo)
    List<PagamentoComissao> findByVendedorIdOrderByDataPagamentoDesc(Integer idVendedor);

    // 2. A MÁGICA: Soma cada centavo que a loja já pagou de comissão para esse funcionário na vida
    @Query("SELECT SUM(p.valor) FROM PagamentoComissao p WHERE p.vendedor.id = :idVendedor")
    Double buscarTotalJaPagoPorVendedor(@Param("idVendedor") Integer idVendedor);
}
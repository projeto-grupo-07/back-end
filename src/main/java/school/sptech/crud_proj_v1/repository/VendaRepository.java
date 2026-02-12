package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.enumeration.FormaDePagamento;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Integer> {
    // aqui vai conter as queries personalizadas

    List<Venda> findByFuncionarioNomeContainingIgnoreCase(String valor);
    List<Venda> findByFormaDePagamento(FormaDePagamento formaDePagamento);

    //kpi da dashboard
    @Query(value = "SELECT * FROM faturamento_dia_atual", nativeQuery = true)
    Double buscarFaturamentoDia();

    @Query(value = "SELECT * FROM faturamento_semana_atual", nativeQuery = true)
    Double buscarFaturamentoSemana();

    @Query(value = "SELECT * FROM faturamento_mes_atual", nativeQuery = true)
    Double buscarFaturamentoMes();
}

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

    //faturamento
    @Query(value = "SELECT * FROM faturamento_dia_atual", nativeQuery = true)
    Double buscarFaturamentoDia();

    @Query(value = "SELECT * FROM faturamento_semana_atual", nativeQuery = true)
    Double buscarFaturamentoSemana();

    @Query(value = "SELECT * FROM faturamento_mes_atual", nativeQuery = true)
    Double buscarFaturamentoMes();

    //total de vendas
    @Query(value = "SELECT * FROM total_vendas_diarias", nativeQuery = true)
    Integer contarVendasDiarias();

    @Query(value = "SELECT * FROM total_vendas_semanais", nativeQuery = true)
    Integer contarVendasSemanais();

    @Query(value = "SELECT * FROM total_vendas_mensais", nativeQuery = true)
    Integer contarVendasMensais();

    // ticket médio
    @Query(value = "SELECT * FROM ticket_medio_diario", nativeQuery = true)
    Double contarTicketMedioDiario();

    @Query(value = "SELECT * FROM ticket_medio_semanal", nativeQuery = true)
    Double contarTicketMedioSemanal();

    @Query(value = "SELECT * FROM ticket_medio_mensal", nativeQuery = true)
    Double contarTicketMedioMensal();
}

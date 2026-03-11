package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.enumeration.FormaDePagamento;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Integer> {

    List<Venda> findByFuncionarioNomeContainingIgnoreCase(String valor);
    List<Venda> findByFormaDePagamento(FormaDePagamento formaDePagamento);

    // --- KPI de Faturamento Geral (Views Nativas) ---
    @Query(value = "SELECT * FROM faturamento_dia_atual", nativeQuery = true)
    Double buscarFaturamentoDia();

    @Query(value = "SELECT * FROM faturamento_semana_atual", nativeQuery = true)
    Double buscarFaturamentoSemana();

    @Query(value = "SELECT * FROM faturamento_mes_atual", nativeQuery = true)
    Double buscarFaturamentoMes();

    // --- KPI de Volume de Vendas ---
    @Query(value = "SELECT * FROM total_vendas_diarias", nativeQuery = true)
    Integer contarVendasDiarias();

    @Query(value = "SELECT * FROM total_vendas_semanais", nativeQuery = true)
    Integer contarVendasSemanais();

    @Query(value = "SELECT * FROM total_vendas_mensais", nativeQuery = true)
    Integer contarVendasMensais();

    // --- KPI de Ticket Médio ---
    @Query(value = "SELECT * FROM ticket_medio_diario", nativeQuery = true)
    Double contarTicketMedioDiario();

    @Query(value = "SELECT * FROM ticket_medio_semanal", nativeQuery = true)
    Double contarTicketMedioSemanal();

    @Query(value = "SELECT * FROM ticket_medio_mensal", nativeQuery = true)
    Double contarTicketMedioMensal();

    // --- MÉTODOS POR VENDEDOR (AJUSTADOS COM 'totalVenda') ---

    @Query("SELECT SUM(v.totalVenda) FROM Venda v WHERE v.funcionario.id = :idFuncionario")
    Double buscarFaturamentoTotalPorVendedor(Integer idFuncionario);

    @Query("SELECT SUM(v.totalVenda * f.comissao) FROM Venda v JOIN v.funcionario f WHERE f.id = :idFuncionario")
    Double buscarComissaoTotalPorVendedor(Integer idFuncionario);

    @Query(value = "SELECT SUM(valor_total) FROM venda WHERE fk_vendedor = :idFuncionario " +
            "AND YEAR(data_hora) = YEAR(CURDATE()) " +
            "AND MONTH(data_hora) = MONTH(CURDATE())", nativeQuery = true)
    Double buscarFaturamentoMensalPorVendedor(Integer idFuncionario);

    @Query("SELECT COUNT(v) FROM Venda v WHERE v.funcionario.id = :idFuncionario")
    Integer contarQtdVendasPorVendedor(Integer idFuncionario);
}
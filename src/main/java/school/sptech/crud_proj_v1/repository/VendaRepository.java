package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.enumeration.FormaDePagamento;
import school.sptech.crud_proj_v1.projection.*; // <-- Importação crucial

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Integer> {

    List<Venda> findByFuncionarioNomeContainingIgnoreCase(String valor);
    List<Venda> findByFormaDePagamento(FormaDePagamento formaDePagamento);

    // ========================================================================
    // --- KPI de Faturamento Geral (SQL Nativo Compatível com H2 e MySQL) ---
    // ========================================================================

    @Query(value = "SELECT COALESCE(SUM(valor_total), 0.0) FROM venda WHERE CAST(data_hora AS DATE) = CURRENT_DATE", nativeQuery = true)
    Double buscarFaturamentoDia();

    @Query(value = "SELECT COALESCE(SUM(valor_total), 0.0) FROM venda WHERE EXTRACT(YEAR FROM data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(WEEK FROM data_hora) = EXTRACT(WEEK FROM CURRENT_DATE)", nativeQuery = true)
    Double buscarFaturamentoSemana();

    @Query(value = "SELECT COALESCE(SUM(valor_total), 0.0) FROM venda WHERE EXTRACT(YEAR FROM data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM data_hora) = EXTRACT(MONTH FROM CURRENT_DATE)", nativeQuery = true)
    Double buscarFaturamentoMes();

    // ========================================================================
    // --- KPI de Volume de Vendas ---
    // ========================================================================

    @Query(value = "SELECT COUNT(*) FROM venda WHERE CAST(data_hora AS DATE) = CURRENT_DATE", nativeQuery = true)
    Integer contarVendasDiarias();

    @Query(value = "SELECT COUNT(*) FROM venda WHERE EXTRACT(YEAR FROM data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(WEEK FROM data_hora) = EXTRACT(WEEK FROM CURRENT_DATE)", nativeQuery = true)
    Integer contarVendasSemanais();

    @Query(value = "SELECT COUNT(*) FROM venda WHERE EXTRACT(YEAR FROM data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM data_hora) = EXTRACT(MONTH FROM CURRENT_DATE)", nativeQuery = true)
    Integer contarVendasMensais();

    // ========================================================================
    // --- KPI de Ticket Médio ---
    // ========================================================================

    @Query(value = "SELECT COALESCE(SUM(valor_total) / NULLIF(COUNT(*), 0), 0.0) FROM venda WHERE CAST(data_hora AS DATE) = CURRENT_DATE", nativeQuery = true)
    Double contarTicketMedioDiario();

    @Query(value = "SELECT COALESCE(SUM(valor_total) / NULLIF(COUNT(*), 0), 0.0) FROM venda WHERE EXTRACT(YEAR FROM data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(WEEK FROM data_hora) = EXTRACT(WEEK FROM CURRENT_DATE)", nativeQuery = true)
    Double contarTicketMedioSemanal();

    @Query(value = "SELECT COALESCE(SUM(valor_total) / NULLIF(COUNT(*), 0), 0.0) FROM venda WHERE EXTRACT(YEAR FROM data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM data_hora) = EXTRACT(MONTH FROM CURRENT_DATE)", nativeQuery = true)
    Double contarTicketMedioMensal();

    // ========================================================================
    // --- MÉTODOS POR VENDEDOR ---
    // ========================================================================

    @Query("SELECT SUM(v.totalVenda) FROM Venda v WHERE v.funcionario.id = :idFuncionario")
    Double buscarFaturamentoTotalPorVendedor(Integer idFuncionario);

    @Query("SELECT SUM(v.valorComissao) FROM Venda v WHERE v.funcionario.id = :idFuncionario")
    Double buscarComissaoTotalPorVendedor(Integer idFuncionario);

    @Query(value = "SELECT COALESCE(SUM(valor_total), 0.0) FROM venda WHERE fk_vendedor = :idFuncionario " +
            "AND EXTRACT(YEAR FROM data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "AND EXTRACT(MONTH FROM data_hora) = EXTRACT(MONTH FROM CURRENT_DATE)", nativeQuery = true)
    Double buscarFaturamentoMensalPorVendedor(Integer idFuncionario);

    @Query("SELECT COUNT(v) FROM Venda v WHERE v.funcionario.id = :idFuncionario")
    Integer contarQtdVendasPorVendedor(Integer idFuncionario);

    // ========================================================================
    // --- NOVOS MÉTODOS (COM PROJECTIONS) PARA A DASHBOARD ---
    // ========================================================================

    // 1. TOTAL EM DESCONTO
    @Query(value = "SELECT COALESCE(ROUND(SUM(iv.valor_desconto), 2), 0.0) FROM itens_venda iv JOIN venda v ON iv.fk_venda = v.id WHERE CAST(v.data_hora AS DATE) = CURRENT_DATE", nativeQuery = true)
    Double buscarTotalDescontoDia();

    @Query(value = "SELECT COALESCE(ROUND(SUM(iv.valor_desconto), 2), 0.0) FROM itens_venda iv JOIN venda v ON iv.fk_venda = v.id WHERE EXTRACT(YEAR FROM v.data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(WEEK FROM v.data_hora) = EXTRACT(WEEK FROM CURRENT_DATE)", nativeQuery = true)
    Double buscarTotalDescontoSemana();

    @Query(value = "SELECT COALESCE(ROUND(SUM(iv.valor_desconto), 2), 0.0) FROM itens_venda iv JOIN venda v ON iv.fk_venda = v.id WHERE EXTRACT(YEAR FROM v.data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM v.data_hora) = EXTRACT(MONTH FROM CURRENT_DATE)", nativeQuery = true)
    Double buscarTotalDescontoMes();

    // 2. QUANTIDADE TOTAL DE VENDAS (UNIDADES DE PRODUTOS VENDIDOS)
    @Query(value = "SELECT COALESCE(SUM(iv.quantidade_venda_produto), 0) FROM itens_venda iv JOIN venda v ON iv.fk_venda = v.id WHERE CAST(v.data_hora AS DATE) = CURRENT_DATE", nativeQuery = true)
    Integer buscarQuantidadeUnidadesDia();

    @Query(value = "SELECT COALESCE(SUM(iv.quantidade_venda_produto), 0) FROM itens_venda iv JOIN venda v ON iv.fk_venda = v.id WHERE EXTRACT(YEAR FROM v.data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(WEEK FROM v.data_hora) = EXTRACT(WEEK FROM CURRENT_DATE)", nativeQuery = true)
    Integer buscarQuantidadeUnidadesSemana();

    @Query(value = "SELECT COALESCE(SUM(iv.quantidade_venda_produto), 0) FROM itens_venda iv JOIN venda v ON iv.fk_venda = v.id WHERE EXTRACT(YEAR FROM v.data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM v.data_hora) = EXTRACT(MONTH FROM CURRENT_DATE)", nativeQuery = true)
    Integer buscarQuantidadeUnidadesMes();

    // 3. GRÁFICO FATURAMENTO AO LONGO DO TEMPO
    @Query(value = "SELECT EXTRACT(YEAR FROM data_hora) AS ano, EXTRACT(MONTH FROM data_hora) AS mes, COALESCE(ROUND(SUM(valor_total),2), 0.0) AS faturamentoTotal FROM venda WHERE data_hora >= CURRENT_DATE - INTERVAL '6' MONTH GROUP BY EXTRACT(YEAR FROM data_hora), EXTRACT(MONTH FROM data_hora) ORDER BY ano, mes", nativeQuery = true)
    List<GraficoTempoProjection> buscarFaturamentoTempoMensal();

    @Query(value = "SELECT EXTRACT(YEAR FROM data_hora) AS ano, EXTRACT(WEEK FROM data_hora) AS semana, COALESCE(ROUND(SUM(valor_total),2), 0.0) AS faturamentoTotal FROM venda WHERE data_hora >= CURRENT_DATE - INTERVAL '6' WEEK GROUP BY EXTRACT(YEAR FROM data_hora), EXTRACT(WEEK FROM data_hora) ORDER BY ano, semana", nativeQuery = true)
    List<GraficoTempoProjection> buscarFaturamentoTempoSemanal();

    // 4. GRÁFICO DIA MAIS MOVIMENTADO
    @Query(value = "SELECT d.dia_semana AS diaSemana, COALESCE(SUM(v.valor_total), 0.0) AS faturamento " +
            "FROM (SELECT 1 AS ordem, 'Domingo' AS dia_semana UNION SELECT 2, 'Segunda' UNION SELECT 3, 'Terça' UNION SELECT 4, 'Quarta' UNION SELECT 5, 'Quinta' UNION SELECT 6, 'Sexta' UNION SELECT 7, 'Sábado') d " +
            "LEFT JOIN venda v ON DAY_OF_WEEK(v.data_hora) = d.ordem AND EXTRACT(YEAR FROM v.data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(WEEK FROM v.data_hora) = EXTRACT(WEEK FROM CURRENT_DATE) " +
            "GROUP BY d.ordem, d.dia_semana ORDER BY d.ordem", nativeQuery = true)
    List<DiaMovimentadoProjection> buscarDiaMaisMovimentadoSemanaAtual();

    // 5. PRODUTOS E MARCAS MAIS VENDIDAS (RANKING)
    @Query(value = "SELECT p.modelo AS nome, SUM(iv.quantidade_venda_produto) AS totalVendidas " +
            "FROM itens_venda iv JOIN produto p ON p.id = iv.fk_produto JOIN venda v ON v.id = iv.fk_venda " +
            "WHERE EXTRACT(MONTH FROM v.data_hora) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(YEAR FROM v.data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "GROUP BY p.id, p.modelo ORDER BY totalVendidas DESC LIMIT 5", nativeQuery = true)
    List<RankingVendasProjection> buscarProdutosMaisVendidosMes();

    @Query(value = "SELECT p.marca AS nome, SUM(iv.quantidade_venda_produto) AS totalVendidas " +
            "FROM itens_venda iv JOIN produto p ON p.id = iv.fk_produto JOIN venda v ON v.id = iv.fk_venda " +
            "WHERE EXTRACT(MONTH FROM v.data_hora) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(YEAR FROM v.data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "GROUP BY p.marca ORDER BY totalVendidas DESC LIMIT 5", nativeQuery = true)
    List<RankingVendasProjection> buscarMarcasMaisVendidasMes();

    // 6. DESEMPENHO FUNCIONÁRIOS (TABELA EQUIPE)
    @Query(value = "SELECT f.nome AS vendedor, COUNT(v.id) AS totalVendas, COALESCE(ROUND(SUM(v.valor_total),2), 0.0) AS totalFaturado, COALESCE(ROUND(SUM(v.valor_total) * f.comissao,2), 0.0) AS comissaoTotal " +
            "FROM venda v JOIN funcionario f ON f.id = v.fk_vendedor " +
            "WHERE EXTRACT(MONTH FROM v.data_hora) = EXTRACT(MONTH FROM CURRENT_DATE) AND EXTRACT(YEAR FROM v.data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "GROUP BY f.nome, f.comissao ORDER BY totalFaturado DESC", nativeQuery = true)
    List<DesempenhoFuncionarioProjection> buscarDesempenhoFuncionarioMes();

    @Query(value = "SELECT f.nome AS vendedor, COUNT(v.id) AS totalVendas, COALESCE(ROUND(SUM(v.valor_total),2), 0.0) AS totalFaturado, COALESCE(ROUND(SUM(v.valor_total) * f.comissao,2), 0.0) AS comissaoTotal " +
            "FROM venda v JOIN funcionario f ON f.id = v.fk_vendedor " +
            "WHERE EXTRACT(WEEK FROM v.data_hora) = EXTRACT(WEEK FROM CURRENT_DATE) AND EXTRACT(YEAR FROM v.data_hora) = EXTRACT(YEAR FROM CURRENT_DATE) " +
            "GROUP BY f.nome, f.comissao ORDER BY totalFaturado DESC", nativeQuery = true)
    List<DesempenhoFuncionarioProjection> buscarDesempenhoFuncionarioSemana();
}
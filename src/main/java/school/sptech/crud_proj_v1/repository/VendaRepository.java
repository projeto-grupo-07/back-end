package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.projection.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Integer> {

    List<Venda> findByFuncionarioNomeContainingIgnoreCase(String nome);

    List<Venda> findByFormaDePagamento(school.sptech.crud_proj_v1.enumeration.FormaDePagamento formaDePagamento);

    @Query("SELECT v FROM Venda v WHERE v.id < :cursor ORDER BY v.id DESC")
    List<Venda> findByIdLessThanOrderByIdDesc(@Param("cursor") int cursor, Pageable pageable);

    // ========================================================================
    // --- KPIs EXISTENTES (DADOS ESTÁTICOS) ---
    // ========================================================================

    @Query(value = "SELECT SUM(valor_total) FROM venda WHERE DATE(data_hora) = CURDATE()", nativeQuery = true)
    Double buscarFaturamentoDia();

    @Query(value = "SELECT SUM(valor_total) FROM venda WHERE YEARWEEK(data_hora, 1) = YEARWEEK(CURDATE(), 1)", nativeQuery = true)
    Double buscarFaturamentoSemana();

    @Query(value = "SELECT SUM(valor_total) FROM venda WHERE MONTH(data_hora) = MONTH(CURDATE()) AND YEAR(data_hora) = YEAR(CURDATE())", nativeQuery = true)
    Double buscarFaturamentoMes();

    @Query(value = "SELECT COUNT(id) FROM venda WHERE DATE(data_hora) = CURDATE()", nativeQuery = true)
    Integer contarVendasDiarias();

    @Query(value = "SELECT COUNT(id) FROM venda WHERE YEARWEEK(data_hora, 1) = YEARWEEK(CURDATE(), 1)", nativeQuery = true)
    Integer contarVendasSemanais();

    @Query(value = "SELECT COUNT(id) FROM venda WHERE MONTH(data_hora) = MONTH(CURDATE()) AND YEAR(data_hora) = YEAR(CURDATE())", nativeQuery = true)
    Integer contarVendasMensais();

    @Query(value = "SELECT AVG(valor_total) FROM venda WHERE DATE(data_hora) = CURDATE()", nativeQuery = true)
    Double contarTicketMedioDiario();

    @Query(value = "SELECT AVG(valor_total) FROM venda WHERE YEARWEEK(data_hora, 1) = YEARWEEK(CURDATE(), 1)", nativeQuery = true)
    Double contarTicketMedioSemanal();

    @Query(value = "SELECT AVG(valor_total) FROM venda WHERE MONTH(data_hora) = MONTH(CURDATE()) AND YEAR(data_hora) = YEAR(CURDATE())", nativeQuery = true)
    Double contarTicketMedioMensal();

    @Query(value = "SELECT SUM(valor_total) FROM venda WHERE fk_vendedor = :idFuncionario", nativeQuery = true)
    Double buscarFaturamentoTotalPorVendedor(@Param("idFuncionario") Integer idFuncionario);

    @Query(value = "SELECT SUM(valor_comissao) FROM venda WHERE fk_vendedor = :idFuncionario", nativeQuery = true)
    Double buscarComissaoTotalPorVendedor(@Param("idFuncionario") Integer idFuncionario);

    @Query(value = "SELECT COUNT(id) FROM venda WHERE fk_vendedor = :id", nativeQuery = true)
    Integer contarQtdVendasPorVendedor(@Param("id") Integer id);

    @Query(value = "SELECT SUM(valor_desconto) FROM itens_venda iv JOIN venda v ON v.id = iv.fk_venda WHERE DATE(v.data_hora) = CURDATE()", nativeQuery = true)
    Double buscarTotalDescontoDia();

    @Query(value = "SELECT SUM(valor_desconto) FROM itens_venda iv JOIN venda v ON v.id = iv.fk_venda WHERE YEARWEEK(v.data_hora, 1) = YEARWEEK(CURDATE(), 1)", nativeQuery = true)
    Double buscarTotalDescontoSemana();

    @Query(value = "SELECT SUM(valor_desconto) FROM itens_venda iv JOIN venda v ON v.id = iv.fk_venda WHERE MONTH(v.data_hora) = MONTH(CURDATE()) AND YEAR(v.data_hora) = YEAR(CURDATE())", nativeQuery = true)
    Double buscarTotalDescontoMes();

    @Query(value = "SELECT SUM(quantidade_venda_produto) FROM itens_venda iv JOIN venda v ON v.id = iv.fk_venda WHERE DATE(v.data_hora) = CURDATE()", nativeQuery = true)
    Integer buscarQuantidadeUnidadesDia();

    @Query(value = "SELECT SUM(quantidade_venda_produto) FROM itens_venda iv JOIN venda v ON v.id = iv.fk_venda WHERE YEARWEEK(v.data_hora, 1) = YEARWEEK(CURDATE(), 1)", nativeQuery = true)
    Integer buscarQuantidadeUnidadesSemana();

    @Query(value = "SELECT SUM(quantidade_venda_produto) FROM itens_venda iv JOIN venda v ON v.id = iv.fk_venda WHERE MONTH(v.data_hora) = MONTH(CURDATE()) AND YEAR(v.data_hora) = YEAR(CURDATE())", nativeQuery = true)
    Integer buscarQuantidadeUnidadesMes();

    // ========================================================================
    // --- VIEWS ESTÁTICAS DELEGADAS ---
    // ========================================================================
    @Query(value = "SELECT * FROM faturamento_ao_longo_mensal", nativeQuery = true)
    List<GraficoTempoProjection> buscarFaturamentoTempoMensal();

    @Query(value = "SELECT * FROM faturamento_ao_longo_semanal", nativeQuery = true)
    List<GraficoTempoProjection> buscarFaturamentoTempoSemanal();

    @Query(value = "SELECT * FROM dia_mais_movimentado_semana_atual", nativeQuery = true)
    List<DiaMovimentadoProjection> buscarDiaMaisMovimentadoSemanaAtual();

    @Query(value = "SELECT * FROM produtos_mais_vendidos_mes", nativeQuery = true)
    List<RankingVendasProjection> buscarProdutosMaisVendidosMes();

    @Query(value = "SELECT * FROM marcas_mais_vendidas_mes", nativeQuery = true)
    List<RankingVendasProjection> buscarMarcasMaisVendidasMes();

    @Query(value = "SELECT * FROM desempenho_funcionario_mes", nativeQuery = true)
    List<DesempenhoFuncionarioProjection> buscarDesempenhoFuncionarioMes();

    @Query(value = "SELECT * FROM desempenho_funcionario_semana", nativeQuery = true)
    List<DesempenhoFuncionarioProjection> buscarDesempenhoFuncionarioSemana();


    // ========================================================================
    // --- QUERIES DINÂMICAS: GRÁFICOS (COMPATÍVEL COM MYSQL 8.0+) ---
    // ========================================================================

    @Query(value = "SELECT DATE_FORMAT(data_hora, '%d/%m') AS periodo, SUM(valor_total) AS faturamento " +
            "FROM venda " +
            "WHERE data_hora >= :inicio AND data_hora <= :fim " +
            "GROUP BY DATE(data_hora), DATE_FORMAT(data_hora, '%d/%m') " +
            "ORDER BY DATE(data_hora)", nativeQuery = true)
    List<FaturamentoTempoProjection> buscarGraficoFaturamentoDiarioDinamico(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query(value = "SELECT DATE_FORMAT(data_hora, '%Y-%m') AS periodo, SUM(valor_total) AS faturamento " +
            "FROM venda " +
            "WHERE data_hora >= :inicio AND data_hora <= :fim " +
            "GROUP BY DATE_FORMAT(data_hora, '%Y-%m') " +
            "ORDER BY periodo", nativeQuery = true)
    List<FaturamentoTempoProjection> buscarGraficoFaturamentoMensalDinamico(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query(value = "SELECT " +
            "  CASE DAYOFWEEK(data_hora) " +
            "    WHEN 1 THEN 'Dom' WHEN 2 THEN 'Seg' WHEN 3 THEN 'Ter' " +
            "    WHEN 4 THEN 'Qua' WHEN 5 THEN 'Qui' WHEN 6 THEN 'Sex' WHEN 7 THEN 'Sáb' " +
            "  END AS diaSemana, " +
            "  SUM(valor_total) AS faturamento " +
            "FROM venda " +
            "WHERE data_hora >= :inicio AND data_hora <= :fim " +
            "GROUP BY DAYOFWEEK(data_hora), " +
            "  CASE DAYOFWEEK(data_hora) " +
            "    WHEN 1 THEN 'Dom' WHEN 2 THEN 'Seg' WHEN 3 THEN 'Ter' " +
            "    WHEN 4 THEN 'Qua' WHEN 5 THEN 'Qui' WHEN 6 THEN 'Sex' WHEN 7 THEN 'Sáb' " +
            "  END " +
            "ORDER BY DAYOFWEEK(data_hora)", nativeQuery = true)
    List<PicoDiaProjection> buscarGraficoPicoDiaDinamico(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);


    // ========================================================================
    // --- QUERIES DINÂMICAS: TABELAS ---
    // ========================================================================

    @Query(value = "SELECT p.modelo AS nome, SUM(iv.quantidade_venda_produto) AS totalVendidas " +
            "FROM itens_venda iv " +
            "JOIN produto p ON p.id = iv.fk_produto " +
            "JOIN venda v ON v.id = iv.fk_venda " +
            "WHERE v.data_hora >= :inicio AND v.data_hora <= :fim " +
            "GROUP BY p.id, p.modelo " +
            "ORDER BY totalVendidas DESC LIMIT 5", nativeQuery = true)
    List<RankingVendasProjection> buscarRankingProdutosDinamico(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query(value = "SELECT p.marca AS nome, SUM(iv.quantidade_venda_produto) AS totalVendidas " +
            "FROM itens_venda iv " +
            "JOIN produto p ON p.id = iv.fk_produto " +
            "JOIN venda v ON v.id = iv.fk_venda " +
            "WHERE v.data_hora >= :inicio AND v.data_hora <= :fim " +
            "GROUP BY p.marca " +
            "ORDER BY totalVendidas DESC LIMIT 5", nativeQuery = true)
    List<RankingVendasProjection> buscarRankingMarcasDinamico(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    @Query(value = "SELECT f.nome AS vendedor, COUNT(v.id) AS totalVendas, " +
            "ROUND(SUM(v.valor_total), 2) AS totalFaturado, " +
            "ROUND(SUM(v.valor_total) * f.comissao, 2) AS comissaoTotal " +
            "FROM venda v " +
            "JOIN funcionario f ON f.id = v.fk_vendedor " +
            "WHERE v.data_hora >= :inicio AND v.data_hora <= :fim " +
            "GROUP BY f.id, f.nome, f.comissao " +
            "ORDER BY totalFaturado DESC", nativeQuery = true)
    List<DesempenhoFuncionarioProjection> buscarDesempenhoEquipeDinamico(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);


    @Query(value = "SELECT MONTH(v.data_hora) as mes, p.modelo as categoria, SUM(iv.quantidade_venda_produto) as quantidade " +
            "FROM itens_venda iv " +
            "JOIN produto p ON p.id = iv.fk_produto " +
            "JOIN venda v ON v.id = iv.fk_venda " +
            "WHERE YEAR(v.data_hora) = :ano " +
            "GROUP BY MONTH(v.data_hora), p.modelo " +
            "ORDER BY MONTH(v.data_hora)", nativeQuery = true)
    List<SazonalidadeProjection> buscarMapaSazonalidade(@Param("ano") Integer ano);

    // ========================================================================
    // --- QUERIES DINÂMICAS: DASHBOARD ESTRATÉGICA ---
    // ========================================================================

    /**
     * 1. Ranking de Pagamentos (Frequência e Valor Total)
     */
    @Query(value = "SELECT v.forma_pagamento AS metodo, " +
            "COUNT(v.id) AS qtdVendas, " +
            "SUM(v.valor_total) AS valorTotal " +
            "FROM venda v " +
            "WHERE v.data_hora >= :inicio AND v.data_hora <= :fim " +
            "GROUP BY v.forma_pagamento " +
            "ORDER BY valorTotal DESC", nativeQuery = true)
    List<MetodoPagamentoProjection> buscarDesempenhoPagamentosDinamico(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    /**
     * 2. Produtos Mais Rentáveis (Maior Lucro Líquido)
     */
    @Query(value = "SELECT p.modelo AS nome, " +
            "SUM(iv.quantidade_venda_produto) AS vendas, " +
            "SUM((p.valor_unitario - p.preco_custo) * iv.quantidade_venda_produto) AS lucro " +
            "FROM itens_venda iv " +
            "JOIN produto p ON p.id = iv.fk_produto " +
            "JOIN venda v ON v.id = iv.fk_venda " +
            "WHERE v.data_hora >= :inicio AND v.data_hora <= :fim " +
            "GROUP BY p.id, p.modelo " +
            "ORDER BY lucro DESC LIMIT 5", nativeQuery = true)
    List<ProdutoRentavelProjection> buscarProdutosMaisRentaveisDinamico(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

    /**
     * 3. Margem de Lucro por Categoria
     * Retorna o ID da categoria (fk_categoria) e a margem percentual média.
     */
    @Query(value = "SELECT p.fk_categoria AS categoria, " +
            "ROUND((SUM((p.valor_unitario - p.preco_custo) * iv.quantidade_venda_produto) / SUM(p.valor_unitario * iv.quantidade_venda_produto)) * 100, 2) AS margem " +
            "FROM itens_venda iv " +
            "JOIN produto p ON p.id = iv.fk_produto " +
            "JOIN venda v ON v.id = iv.fk_venda " +
            "WHERE v.data_hora >= :inicio AND v.data_hora <= :fim " +
            "GROUP BY p.fk_categoria " +
            "ORDER BY margem DESC", nativeQuery = true)
    List<MargemCategoriaProjection> buscarMargemPorCategoriaDinamico(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

}
package school.sptech.crud_proj_v1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.repository.VendaRepository;
import school.sptech.crud_proj_v1.projection.*; // Importante para as listas

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KpiService {

    private final VendaRepository vendaRepository;

    // ========================================================================
    // --- KPIs EXISTENTES ---
    // ========================================================================

    public Double buscarFaturamentoDia(){
        return vendaRepository.buscarFaturamentoDia();
    }

    public Double buscarFaturamentoSemana(){
        return vendaRepository.buscarFaturamentoSemana();
    }

    public Double buscarFaturamentoMes(){
        return vendaRepository.buscarFaturamentoMes();
    }

    public Integer buscarTotalVendasDiarias() {
        Integer qtd = vendaRepository.contarVendasDiarias();
        return (qtd != null) ? qtd : 0;
    }

    public Integer buscarTotalVendasSemanais() {
        Integer qtd = vendaRepository.contarVendasSemanais();
        return (qtd != null) ? qtd : 0;
    }

    public Integer buscarTotalVendasMensais() {
        Integer qtd = vendaRepository.contarVendasMensais();
        return (qtd != null) ? qtd : 0;
    }

    public Double contarTicketMedioMensal(){
        return vendaRepository.contarTicketMedioMensal();
    }

    public Double contarTicketMedioSemanal(){
        return vendaRepository.contarTicketMedioSemanal();
    }

    public Double contarTicketMedioDiario(){
        return vendaRepository.contarTicketMedioDiario();
    }

    public Double buscarFaturamentoTotalPorVendedor(Integer idFuncionario) {
        Double faturamento = vendaRepository.buscarFaturamentoTotalPorVendedor(idFuncionario);
        return Optional.ofNullable(faturamento).orElse(0.0);
    }

    public Double buscarComissaoTotalPorVendedor(Integer idFuncionario) {
        Double comissao = vendaRepository.buscarComissaoTotalPorVendedor(idFuncionario);
        return Optional.ofNullable(comissao).orElse(0.0);
    }

    public Integer buscarQtdVendasPorVendedor(Integer id) {
        Integer qtd = vendaRepository.contarQtdVendasPorVendedor(id);
        return (qtd != null) ? qtd : 0;
    }

    // ========================================================================
    // --- NOVOS MÉTODOS: DESCONTOS E UNIDADES (CARDS) ---
    // ========================================================================

    public Double buscarTotalDescontoDia() {
        return vendaRepository.buscarTotalDescontoDia();
    }

    public Double buscarTotalDescontoSemana() {
        return vendaRepository.buscarTotalDescontoSemana();
    }

    public Double buscarTotalDescontoMes() {
        return vendaRepository.buscarTotalDescontoMes();
    }

    public Integer buscarQuantidadeUnidadesDia() {
        return vendaRepository.buscarQuantidadeUnidadesDia();
    }

    public Integer buscarQuantidadeUnidadesSemana() {
        return vendaRepository.buscarQuantidadeUnidadesSemana();
    }

    public Integer buscarQuantidadeUnidadesMes() {
        return vendaRepository.buscarQuantidadeUnidadesMes();
    }

    // ========================================================================
    // --- DADOS ESTÁTICOS (MANTIDOS POR SEGURANÇA) ---
    // ========================================================================

    public List<GraficoTempoProjection> buscarFaturamentoTempoMensal() {
        return vendaRepository.buscarFaturamentoTempoMensal();
    }

    public List<GraficoTempoProjection> buscarFaturamentoTempoSemanal() {
        return vendaRepository.buscarFaturamentoTempoSemanal();
    }

    public List<DiaMovimentadoProjection> buscarDiaMaisMovimentadoSemanaAtual() {
        return vendaRepository.buscarDiaMaisMovimentadoSemanaAtual();
    }

    public List<RankingVendasProjection> buscarProdutosMaisVendidosMes() {
        return vendaRepository.buscarProdutosMaisVendidosMes();
    }

    public List<RankingVendasProjection> buscarMarcasMaisVendidasMes() {
        return vendaRepository.buscarMarcasMaisVendidasMes();
    }

    public List<DesempenhoFuncionarioProjection> buscarDesempenhoFuncionarioMes() {
        return vendaRepository.buscarDesempenhoFuncionarioMes();
    }

    public List<DesempenhoFuncionarioProjection> buscarDesempenhoFuncionarioSemana() {
        return vendaRepository.buscarDesempenhoFuncionarioSemana();
    }

    // ========================================================================
    // --- LÓGICA DINÂMICA (DASHBOARD) ---
    // ========================================================================

    // Helper para converter o texto do React em datas reais
    private LocalDateTime[] calcularPeriodo(String tipo, LocalDateTime inicio, LocalDateTime fim) {
        LocalDateTime dataInicio;
        LocalDateTime dataFim = LocalDateTime.now(); // default

        if (tipo == null) tipo = "Este Mês";

        switch (tipo) {
            case "Hoje":
                dataInicio = LocalDate.now().atStartOfDay();
                dataFim = LocalDate.now().atTime(LocalTime.MAX);
                break;
            case "Esta Semana":
                dataInicio = LocalDate.now().with(java.time.DayOfWeek.MONDAY).atStartOfDay();
                dataFim = LocalDate.now().with(java.time.DayOfWeek.SUNDAY).atTime(LocalTime.MAX);
                break;
            case "Este Mês":
                dataInicio = LocalDate.now().withDayOfMonth(1).atStartOfDay();
                dataFim = LocalDate.now().with(java.time.temporal.TemporalAdjusters.lastDayOfMonth()).atTime(LocalTime.MAX);
                break;
            case "Este Semestre":
                dataInicio = LocalDate.now().minusMonths(6).withDayOfMonth(1).atStartOfDay();
                break;
            case "Personalizado":
                dataInicio = inicio != null ? inicio.toLocalDate().atStartOfDay() : LocalDate.now().withDayOfMonth(1).atStartOfDay();
                dataFim = fim != null ? fim.toLocalDate().atTime(LocalTime.MAX) : LocalDate.now().atTime(LocalTime.MAX);
                break;
            default:
                dataInicio = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        }
        return new LocalDateTime[]{dataInicio, dataFim};
    }

    // --- Gráficos Dinâmicos ---
    public List<FaturamentoTempoProjection> buscarGraficoFaturamentoDinamico(String tipo, LocalDateTime inicio, LocalDateTime fim) {
        LocalDateTime[] datas = calcularPeriodo(tipo, inicio, fim);
        long dias = java.time.temporal.ChronoUnit.DAYS.between(datas[0], datas[1]);

        // MÁGICA: Se o filtro for mais de 2 meses, agrupa por Mês. Senão, mostra dia a dia.
        if (dias > 60) {
            return vendaRepository.buscarGraficoFaturamentoMensalDinamico(datas[0], datas[1]);
        } else {
            return vendaRepository.buscarGraficoFaturamentoDiarioDinamico(datas[0], datas[1]);
        }
    }

    public List<PicoDiaProjection> buscarGraficoPicoDiaDinamico(String tipo, LocalDateTime inicio, LocalDateTime fim) {
        LocalDateTime[] datas = calcularPeriodo(tipo, inicio, fim);
        return vendaRepository.buscarGraficoPicoDiaDinamico(datas[0], datas[1]);
    }

    // --- Tabelas Dinâmicas (MÉTODOS QUE ESTAVAM FALTANDO) ---
    public List<RankingVendasProjection> buscarRankingProdutosDinamico(String tipo, LocalDateTime LocalDateInicio, LocalDateTime LocalDateFim) {
        LocalDateTime[] datas = calcularPeriodo(tipo, LocalDateInicio, LocalDateFim);
        return vendaRepository.buscarRankingProdutosDinamico(datas[0], datas[1]);
    }

    public List<RankingVendasProjection> buscarRankingMarcasDinamico(String tipo, LocalDateTime LocalDateInicio, LocalDateTime LocalDateFim) {
        LocalDateTime[] datas = calcularPeriodo(tipo, LocalDateInicio, LocalDateFim);
        return vendaRepository.buscarRankingMarcasDinamico(datas[0], datas[1]);
    }

    public List<DesempenhoFuncionarioProjection> buscarDesempenhoEquipeDinamico(String tipo, LocalDateTime LocalDateInicio, LocalDateTime LocalDateFim) {
        LocalDateTime[] datas = calcularPeriodo(tipo, LocalDateInicio, LocalDateFim);
        return vendaRepository.buscarDesempenhoEquipeDinamico(datas[0], datas[1]);
    }

    public List<SazonalidadeProjection> buscarMapaSazonalidade(Integer ano) {
        if (ano == null) {
            ano = java.time.LocalDate.now().getYear();
        }
        return vendaRepository.buscarMapaSazonalidade(ano);
    }
}
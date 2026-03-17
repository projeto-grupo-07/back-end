package school.sptech.crud_proj_v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.crud_proj_v1.service.KpiService;
import school.sptech.crud_proj_v1.projection.*; // Traz as interfaces para o Controller

import java.util.List;

@RestController
@RequestMapping("/kpis")
@RequiredArgsConstructor
public class KpiController {

    private final KpiService kpiService;

    // ========================================================================
    // --- KPIs EXISTENTES (Faturamento, Vendas, Ticket e Vendedor Único) ---
    // ========================================================================

    @GetMapping("/faturamento-diario")
    public ResponseEntity<Double> getFaturamentoDiario(){
        return ResponseEntity.status(200).body(kpiService.buscarFaturamentoDia());
    }

    @GetMapping("/faturamento-semanal")
    public ResponseEntity<Double> getFaturamentoSemanal(){
        return ResponseEntity.status(200).body(kpiService.buscarFaturamentoSemana());
    }

    @GetMapping("/faturamento-mensal")
    public ResponseEntity<Double> getFaturamentoMensal(){
        return ResponseEntity.status(200).body(kpiService.buscarFaturamentoMes());
    }

    @GetMapping("/total-vendas-diario")
    public ResponseEntity<Integer> getTotalVendasDiario() {
        return ResponseEntity.status(200).body(kpiService.buscarTotalVendasDiarias());
    }

    @GetMapping("/total-vendas-semanal")
    public ResponseEntity<Integer> getTotalVendasSemanal() {
        return ResponseEntity.status(200).body(kpiService.buscarTotalVendasSemanais());
    }

    @GetMapping("/total-vendas-mensal")
    public ResponseEntity<Integer> getTotalVendasMensal() {
        return ResponseEntity.status(200).body(kpiService.buscarTotalVendasMensais());
    }

    @GetMapping("/ticket-medio-diario")
    public ResponseEntity<Double> getTicketMedioDiario(){
        return ResponseEntity.status(200).body(kpiService.contarTicketMedioDiario());
    }

    @GetMapping("/ticket-medio-semanal")
    public ResponseEntity<Double> getTicketMedioSemanal(){
        return ResponseEntity.status(200).body(kpiService.contarTicketMedioSemanal());
    }

    @GetMapping("/ticket-medio-mensal")
    public ResponseEntity<Double> getTicketMedioMensal(){
        return ResponseEntity.status(200).body(kpiService.contarTicketMedioMensal());
    }

    @GetMapping("/vendedor/{id}/faturamento")
    public ResponseEntity<Double> getFaturamentoPorVendedor(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(kpiService.buscarFaturamentoTotalPorVendedor(id));
    }

    @GetMapping("/vendedor/{id}/comissao")
    public ResponseEntity<Double> getComissaoPorVendedor(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(kpiService.buscarComissaoTotalPorVendedor(id));
    }

    @GetMapping("/vendedor/{id}/quantidade")
    public ResponseEntity<Integer> getQuantidadeVendasPorVendedor(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(kpiService.buscarQtdVendasPorVendedor(id));
    }

    // ========================================================================
    // --- NOVOS ENDPOINTS: DESCONTOS (CARDS ESQUERDOS) ---
    // ========================================================================

    @GetMapping("/desconto-diario")
    public ResponseEntity<Double> getDescontoDiario() {
        return ResponseEntity.status(200).body(kpiService.buscarTotalDescontoDia());
    }

    @GetMapping("/desconto-semanal")
    public ResponseEntity<Double> getDescontoSemanal() {
        return ResponseEntity.status(200).body(kpiService.buscarTotalDescontoSemana());
    }

    @GetMapping("/desconto-mensal")
    public ResponseEntity<Double> getDescontoMensal() {
        return ResponseEntity.status(200).body(kpiService.buscarTotalDescontoMes());
    }

    // ========================================================================
    // --- NOVOS ENDPOINTS: UNIDADES VENDIDAS (CARDS ESQUERDOS) ---
    // ========================================================================

    @GetMapping("/unidades-diario")
    public ResponseEntity<Integer> getUnidadesDiario() {
        return ResponseEntity.status(200).body(kpiService.buscarQuantidadeUnidadesDia());
    }

    @GetMapping("/unidades-semanal")
    public ResponseEntity<Integer> getUnidadesSemanal() {
        return ResponseEntity.status(200).body(kpiService.buscarQuantidadeUnidadesSemana());
    }

    @GetMapping("/unidades-mensal")
    public ResponseEntity<Integer> getUnidadesMensal() {
        return ResponseEntity.status(200).body(kpiService.buscarQuantidadeUnidadesMes());
    }

    // ========================================================================
    // --- NOVOS ENDPOINTS: GRÁFICOS E TABELAS (RETORNAM LISTAS) ---
    // ========================================================================

    @GetMapping("/grafico-faturamento-mensal")
    public ResponseEntity<List<GraficoTempoProjection>> getGraficoFaturamentoMensal() {
        return ResponseEntity.status(200).body(kpiService.buscarFaturamentoTempoMensal());
    }

    @GetMapping("/grafico-faturamento-semanal")
    public ResponseEntity<List<GraficoTempoProjection>> getGraficoFaturamentoSemanal() {
        return ResponseEntity.status(200).body(kpiService.buscarFaturamentoTempoSemanal());
    }

    @GetMapping("/grafico-pico-dia")
    public ResponseEntity<List<DiaMovimentadoProjection>> getGraficoPicoDia() {
        return ResponseEntity.status(200).body(kpiService.buscarDiaMaisMovimentadoSemanaAtual());
    }

    @GetMapping("/ranking-produtos-mes")
    public ResponseEntity<List<RankingVendasProjection>> getRankingProdutosMes() {
        return ResponseEntity.status(200).body(kpiService.buscarProdutosMaisVendidosMes());
    }

    @GetMapping("/ranking-marcas-mes")
    public ResponseEntity<List<RankingVendasProjection>> getRankingMarcasMes() {
        return ResponseEntity.status(200).body(kpiService.buscarMarcasMaisVendidasMes());
    }

    @GetMapping("/desempenho-equipe-mes")
    public ResponseEntity<List<DesempenhoFuncionarioProjection>> getDesempenhoEquipeMes() {
        return ResponseEntity.status(200).body(kpiService.buscarDesempenhoFuncionarioMes());
    }

    @GetMapping("/desempenho-equipe-semana")
    public ResponseEntity<List<DesempenhoFuncionarioProjection>> getDesempenhoEquipeSemana() {
        return ResponseEntity.status(200).body(kpiService.buscarDesempenhoFuncionarioSemana());
    }
}
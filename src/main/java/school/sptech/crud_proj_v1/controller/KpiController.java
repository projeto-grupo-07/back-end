package school.sptech.crud_proj_v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.service.KpiService;
import school.sptech.crud_proj_v1.projection.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/kpis")
@RequiredArgsConstructor
public class KpiController {

    private final KpiService kpiService;

    // ========================================================================
    // --- MÉTODOS UNIFICADOS (CARDS DA ESQUERDA) ---
    // ========================================================================

    @GetMapping("/faturamento")
    public ResponseEntity<Double> getFaturamento(@RequestParam(required = false, defaultValue = "Este Mês") String tipo) {
        return ResponseEntity.ok(kpiService.getFaturamento(tipo));
    }

    @GetMapping("/total-vendas")
    public ResponseEntity<Integer> getTotalVendas(@RequestParam(required = false, defaultValue = "Este Mês") String tipo) {
        return ResponseEntity.ok(kpiService.getTotalVendas(tipo));
    }

    @GetMapping("/ticket-medio")
    public ResponseEntity<Double> getTicketMedio(@RequestParam(required = false, defaultValue = "Este Mês") String tipo) {
        return ResponseEntity.ok(kpiService.getTicketMedio(tipo));
    }

    @GetMapping("/total-descontos")
    public ResponseEntity<Double> getTotalDescontos(@RequestParam(required = false, defaultValue = "Este Mês") String tipo) {
        return ResponseEntity.ok(kpiService.getTotalDescontos(tipo));
    }

    @GetMapping("/total-unidades")
    public ResponseEntity<Integer> getTotalUnidades(@RequestParam(required = false, defaultValue = "Este Mês") String tipo) {
        return ResponseEntity.ok(kpiService.getTotalUnidades(tipo));
    }

    // ========================================================================
    // --- RENDIMENTO INDIVIDUAL (VENDEDOR) ---
    // ========================================================================

    @GetMapping("/vendedor/{id}/faturamento")
    public ResponseEntity<Double> getFaturamentoPorVendedor(@PathVariable Integer id) {
        return ResponseEntity.ok(kpiService.buscarFaturamentoTotalPorVendedor(id));
    }

    @GetMapping("/vendedor/{id}/comissao")
    public ResponseEntity<Double> getComissaoPorVendedor(@PathVariable Integer id) {
        return ResponseEntity.ok(kpiService.buscarComissaoTotalPorVendedor(id));
    }

    @GetMapping("/vendedor/{id}/quantidade")
    public ResponseEntity<Integer> getQuantidadeVendasPorVendedor(@PathVariable Integer id) {
        return ResponseEntity.ok(kpiService.buscarQtdVendasPorVendedor(id));
    }

    // ========================================================================
    // --- MÉTODOS DINÂMICOS (GRÁFICOS E TABELAS DA DIREITA) ---
    // ========================================================================

    @GetMapping("/grafico-faturamento")
    public ResponseEntity<List<FaturamentoTempoProjection>> getGraficoFaturamento(
            @RequestParam(required = false, defaultValue = "Este Mês") String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(kpiService.getGraficoFaturamento(tipo, inicio, fim));
    }

    @GetMapping("/grafico-pico-dia")
    public ResponseEntity<List<PicoDiaProjection>> getGraficoPicoDia(
            @RequestParam(required = false, defaultValue = "Este Mês") String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(kpiService.getGraficoPicoDia(tipo, inicio, fim));
    }


    @GetMapping("/ranking-produtos")
    public ResponseEntity<List<RankingVendasProjection>> getRankingProdutos(
            @RequestParam(required = false, defaultValue = "Este Mês") String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(kpiService.getRankingProdutos(tipo, inicio, fim));
    }

    @GetMapping("/ranking-marcas")
    public ResponseEntity<List<RankingVendasProjection>> getRankingMarcas(
            @RequestParam(required = false, defaultValue = "Este Mês") String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(kpiService.getRankingMarcas(tipo, inicio, fim));
    }

    @GetMapping("/desempenho-equipe")
    public ResponseEntity<List<DesempenhoFuncionarioProjection>> getDesempenhoEquipe(
            @RequestParam(required = false, defaultValue = "Este Mês") String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(kpiService.getDesempenhoEquipe(tipo, inicio, fim));
    }

    @GetMapping("/sazonalidade")
    public ResponseEntity<List<SazonalidadeProjection>> getMapaSazonalidade(
            @RequestParam(required = false) Integer ano) {
        return ResponseEntity.ok(kpiService.buscarMapaSazonalidade(ano));
    }



    // ========================================================================
    // --- ENDPOINTS: DASHBOARD ESTRATÉGICA ---
    // ========================================================================

    @GetMapping("/estrategico/pagamentos")
    public ResponseEntity<List<MetodoPagamentoProjection>> getDesempenhoPagamentos(
            @RequestParam(required = false, defaultValue = "Este Mês") String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(kpiService.getDesempenhoPagamentos(tipo, inicio, fim));
    }

    @GetMapping("/estrategico/produtos-rentaveis")
    public ResponseEntity<List<ProdutoRentavelProjection>> getProdutosRentaveis(
            @RequestParam(required = false, defaultValue = "Este Mês") String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(kpiService.getProdutosRentaveis(tipo, inicio, fim));
    }

    @GetMapping("/estrategico/margem-categoria")
    public ResponseEntity<List<MargemCategoriaProjection>> getMargemCategoria(
            @RequestParam(required = false, defaultValue = "Este Mês") String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(kpiService.getMargemCategoria(tipo, inicio, fim));
    }
}
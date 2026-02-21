package school.sptech.crud_proj_v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.crud_proj_v1.service.KpiService;

@RestController
@RequestMapping("/kpis")
@RequiredArgsConstructor
public class KpiController {
    private final KpiService kpiService;

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
}

package school.sptech.crud_proj_v1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.repository.VendaRepository;
import school.sptech.crud_proj_v1.projection.*;

import java.time.*;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KpiService {

    private final VendaRepository vendaRepository;

    // ========================================================================
    // --- LÓGICA DE DATAS (O CÉREBRO) ---
    // ========================================================================
    private LocalDateTime[] calcularPeriodo(String tipo, LocalDateTime inicio, LocalDateTime fim) {
        if ("Personalizado".equals(tipo)) {
            return new LocalDateTime[]{
                    inicio != null ? inicio : LocalDate.now().atStartOfDay(),
                    fim != null ? fim : LocalDateTime.now()
            };
        }

        LocalDateTime dataInicio;
        LocalDateTime dataFim = LocalDateTime.now(); // Fim padrão é agora

        if (tipo == null) tipo = "Este Mês";

        switch (tipo) {
            case "Hoje":
                dataInicio = LocalDate.now().atStartOfDay();
                dataFim = LocalDate.now().atTime(LocalTime.MAX);
                break;
            case "Esta Semana":
                dataInicio = LocalDate.now().with(java.time.DayOfWeek.MONDAY).atStartOfDay();
                // dataFim continua sendo o .now() do topo
                break;
            case "Este Mês":
                dataInicio = LocalDate.now().withDayOfMonth(1).atStartOfDay();
                break;
            case "Este Semestre":
                dataInicio = LocalDate.of(LocalDate.now().getYear(), 1, 1).atStartOfDay();
                break;
            default:
                dataInicio = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        }

        System.out.println("DEBUG SQL: Tipo=" + tipo + " | Inicio=" + dataInicio + " | Fim=" + dataFim);
        return new LocalDateTime[]{dataInicio, dataFim};
    }

    // ========================================================================
    // --- MÉTODOS DE KPIS (SUBSTITUEM OS ANTIGOS) ---
    // ========================================================================
    public Double getFaturamento(String tipo) {
        LocalDateTime[] d = calcularPeriodo(tipo, null, null);
        return Optional.ofNullable(vendaRepository.somarFaturamento(d[0], d[1])).orElse(0.0);
    }

    public Integer getTotalVendas(String tipo) {
        LocalDateTime[] d = calcularPeriodo(tipo, null, null);
        return Optional.ofNullable(vendaRepository.contarVendas(d[0], d[1])).orElse(0);
    }

    public Double getTicketMedio(String tipo) {
        LocalDateTime[] d = calcularPeriodo(tipo, null, null);
        return Optional.ofNullable(vendaRepository.calcularTicketMedio(d[0], d[1])).orElse(0.0);
    }

    public Double getTotalDescontos(String tipo) {
        LocalDateTime[] d = calcularPeriodo(tipo, null, null);
        return Optional.ofNullable(vendaRepository.somarDescontos(d[0], d[1])).orElse(0.0);
    }

    public Integer getTotalUnidades(String tipo) {
        LocalDateTime[] d = calcularPeriodo(tipo, null, null);
        return Optional.ofNullable(vendaRepository.somarUnidades(d[0], d[1])).orElse(0);
    }

    // ========================================================================
    // --- MÉTODOS DE GRÁFICOS E TABELAS (DINÂMICOS) ---
    // ========================================================================
    public List<FaturamentoTempoProjection> getGraficoFaturamento(String tipo, LocalDateTime inicio, LocalDateTime fim) {
        LocalDateTime[] d = calcularPeriodo(tipo, inicio, fim);
        return vendaRepository.buscarGraficoFaturamentoDiarioDinamico(d[0], d[1]);
    }

    public List<PicoDiaProjection> getGraficoPicoDia(String tipo, LocalDateTime inicio, LocalDateTime fim) {
        LocalDateTime[] d = calcularPeriodo(tipo, inicio, fim);
        return vendaRepository.buscarGraficoPicoDiaDinamico(d[0], d[1]);
    }


    public List<RankingVendasProjection> getRankingProdutos(String tipo, LocalDateTime inicio, LocalDateTime fim) {
        LocalDateTime[] d = calcularPeriodo(tipo, inicio, fim);
        return vendaRepository.buscarRankingProdutosDinamico(d[0], d[1]);
    }

    public List<RankingVendasProjection> getRankingMarcas(String tipo, LocalDateTime inicio, LocalDateTime fim) {
        LocalDateTime[] d = calcularPeriodo(tipo, inicio, fim);
        return vendaRepository.buscarRankingMarcasDinamico(d[0], d[1]);
    }

    public List<DesempenhoFuncionarioProjection> getDesempenhoEquipe(String tipo, LocalDateTime inicio, LocalDateTime fim) {
        LocalDateTime[] d = calcularPeriodo(tipo, inicio, fim);
        return vendaRepository.buscarDesempenhoEquipeDinamico(d[0], d[1]);
    }

    public List<SazonalidadeProjection> buscarMapaSazonalidade(Integer ano) {
        return vendaRepository.buscarMapaSazonalidade(ano != null ? ano : LocalDate.now().getYear());
    }



    // --- MANTEMOS OS DE VENDEDOR PORQUE SÃO ESPECÍFICOS ---
    public Double buscarFaturamentoTotalPorVendedor(Integer id) { return Optional.ofNullable(vendaRepository.buscarFaturamentoTotalPorVendedor(id)).orElse(0.0); }
    public Double buscarComissaoTotalPorVendedor(Integer id) { return Optional.ofNullable(vendaRepository.buscarComissaoTotalPorVendedor(id)).orElse(0.0); }
    public Integer buscarQtdVendasPorVendedor(Integer id) { return Optional.ofNullable(vendaRepository.contarQtdVendasPorVendedor(id)).orElse(0); }

    // ========================================================================
    // --- MÉTODOS DA DASHBOARD ESTRATÉGICA ---
    // ========================================================================

    public List<MetodoPagamentoProjection> getDesempenhoPagamentos(String tipo, LocalDateTime inicio, LocalDateTime fim) {
        LocalDateTime[] d = calcularPeriodo(tipo, inicio, fim);
        return vendaRepository.buscarDesempenhoPagamentosDinamico(d[0], d[1]);
    }

    public List<ProdutoRentavelProjection> getProdutosRentaveis(String tipo, LocalDateTime inicio, LocalDateTime fim) {
        LocalDateTime[] d = calcularPeriodo(tipo, inicio, fim);
        return vendaRepository.buscarProdutosMaisRentaveisDinamico(d[0], d[1]);
    }

    public List<MargemCategoriaProjection> getMargemCategoria(String tipo, LocalDateTime inicio, LocalDateTime fim) {
        LocalDateTime[] d = calcularPeriodo(tipo, inicio, fim);
        return vendaRepository.buscarMargemPorCategoriaDinamico(d[0], d[1]);
    }
}
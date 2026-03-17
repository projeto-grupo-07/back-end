package school.sptech.crud_proj_v1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.repository.VendaRepository;
import school.sptech.crud_proj_v1.projection.*; // Importante para as listas

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
    // --- NOVOS MÉTODOS: GRÁFICOS E TABELAS (LISTAS) ---
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
}
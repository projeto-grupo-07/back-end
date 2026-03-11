package school.sptech.crud_proj_v1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.repository.VendaRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KpiService {
    private final VendaRepository vendaRepository;

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
    
}

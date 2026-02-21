package school.sptech.crud_proj_v1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.repository.VendaRepository;

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

    public Integer contarTicketMedioMensal(){
        return vendaRepository.contarTicketMedioMensal();
    }

    public Integer contarTicketMedioSemanal(){
        return vendaRepository.contarTicketMedioSemanal();
    }

    public Integer contarTicketMedioDiario(){
        return vendaRepository.contarTicketMedioDiario();
    }
}

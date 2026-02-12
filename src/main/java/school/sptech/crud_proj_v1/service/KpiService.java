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
}

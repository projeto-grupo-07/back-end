package school.sptech.crud_proj_v1.service;

import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.entity.Comissao;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.repository.ComissaoRepository;

@Service
public class ComissaoService {

private final ComissaoRepository comissaoRepository;

    public ComissaoService(ComissaoRepository comissaoRepository) {
        this.comissaoRepository = comissaoRepository;
    }

    public Comissao calcularComissao(Venda venda){
        Comissao vendaComissao = new Comissao();
        vendaComissao.setVenda(venda);
        vendaComissao.setFuncionario(venda.getFuncionario());
        vendaComissao.setDataVenda(venda.getDataHora());
        vendaComissao.setValorComissao(venda.getTotalVenda() * venda.getFuncionario().getComissao());

        return comissaoRepository.save(vendaComissao);
    }

}

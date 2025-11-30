package school.sptech.crud_proj_v1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.entity.Comissao;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.repository.ComissaoRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
@RequiredArgsConstructor
public class ComissaoService {

private final ComissaoRepository comissaoRepository;

    private Double arredondar(Double valor) {
        if (valor == null) return 0.0;
        return BigDecimal.valueOf(valor)
                .setScale(2, RoundingMode.HALF_EVEN)
                .doubleValue();
    }

    public Comissao calcularComissao(Venda venda){

        Comissao comissaoExistente = comissaoRepository.findByVendaId(venda.getId()).orElse(null);

        Double novoValor = arredondar(venda.getTotalVenda() * venda.getFuncionario().getComissao());

        if (comissaoExistente != null) {

            comissaoExistente.setValorComissao(novoValor);
            return comissaoRepository.save(comissaoExistente);
        }



        Comissao vendaComissao = new Comissao();
        vendaComissao.setVenda(venda);
        vendaComissao.setFuncionario(venda.getFuncionario());
        vendaComissao.setDataVenda(venda.getDataHora());
        vendaComissao.setValorComissao(arredondar(venda.getTotalVenda() * venda.getFuncionario().getComissao()));

        return comissaoRepository.save(vendaComissao);
    }

}

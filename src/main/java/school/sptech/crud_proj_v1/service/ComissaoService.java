package school.sptech.crud_proj_v1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.repository.VendaRepository; // <-- IMPORT NECESSÁRIO
// import school.sptech.crud_proj_v1.repository.ComissaoRepository; // Pode apagar se não usar mais

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class ComissaoService {

    // private final ComissaoRepository comissaoRepository; // Pode apagar se não for usar a tabela separada
    private final VendaRepository vendaRepository; // <-- ADICIONADO PARA O SAVE FUNCIONAR

    private Double arredondar(Double valor) {
        if (valor == null) return 0.0;
        return BigDecimal.valueOf(valor)
                .setScale(2, RoundingMode.HALF_EVEN)
                .doubleValue();
    }

    public void calcularComissao(Venda venda) {
        // 1. Puxa a porcentagem que foi "congelada" na venda
        Double porcentagem = venda.getPercentualComissaoAplicado();

        // Segurança extra: se vier nulo por algum motivo, assume 0
        if (porcentagem == null) {
            porcentagem = 0.0;
        }

        // 2. Calcula o valor em R$ com base no total da venda
        Double valorEmReais = venda.getTotalVenda() * porcentagem;

        // 3. Salva o valor final na própria venda USANDO O SEU MÉTODO DE ARREDONDAR!
        venda.setValorComissao(arredondar(valorEmReais));

        // 4. Atualiza a venda no banco
        vendaRepository.save(venda);
    }
}
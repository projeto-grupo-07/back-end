package school.sptech.crud_proj_v1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crud_proj_v1.dto.PagamentoComissao.NovoPagamentoDTO;
import school.sptech.crud_proj_v1.dto.PagamentoComissao.ResumoComissaoDTO;
import school.sptech.crud_proj_v1.entity.Funcionario;
import school.sptech.crud_proj_v1.entity.PagamentoComissao;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;
import school.sptech.crud_proj_v1.repository.PagamentoComissaoRepository;
import school.sptech.crud_proj_v1.repository.VendaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PagamentoComissaoService {

    private final PagamentoComissaoRepository pagamentoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final VendaRepository vendaRepository;

    // 1. Registrar um novo pagamento
    public PagamentoComissao registrarPagamento(Integer idFuncionario, NovoPagamentoDTO dto) {
        Funcionario vendedor = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado"));

        if (dto.valor() == null || dto.valor() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor do pagamento deve ser maior que zero.");
        }

        PagamentoComissao pagamento = new PagamentoComissao();
        pagamento.setVendedor(vendedor);
        pagamento.setValor(dto.valor());
        pagamento.setObservacao(dto.observacao());
        pagamento.setDataPagamento(LocalDateTime.now()); // Registra o exato momento do clique

        return pagamentoRepository.save(pagamento);
    }

    // 2. Buscar o Histórico (Extrato)
    public List<PagamentoComissao> buscarHistorico(Integer idFuncionario) {
        if (!funcionarioRepository.existsById(idFuncionario)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado");
        }
        return pagamentoRepository.findByVendedorIdOrderByDataPagamentoDesc(idFuncionario);
    }

    // 3. A Matemática: Calcular o Saldo Pendente
    public ResumoComissaoDTO obterResumo(Integer idFuncionario) {
        if (!funcionarioRepository.existsById(idFuncionario)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado");
        }

        // Busca o total que ele já ganhou vendendo (Se null, vira 0.0)
        Double totalGanho = Optional.ofNullable(vendaRepository.buscarComissaoTotalPorVendedor(idFuncionario)).orElse(0.0);

        // Busca o total que o dono já pagou pra ele (Se null, vira 0.0)
        Double totalPago = Optional.ofNullable(pagamentoRepository.buscarTotalJaPagoPorVendedor(idFuncionario)).orElse(0.0);

        // A mágica:
        Double saldoPendente = totalGanho - totalPago;

        return new ResumoComissaoDTO(totalGanho, totalPago, saldoPendente);
    }
}
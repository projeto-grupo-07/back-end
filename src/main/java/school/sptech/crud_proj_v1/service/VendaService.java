package school.sptech.crud_proj_v1.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.dto.VendaProduto.VendaProdutoRequestDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaRequestDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;

import school.sptech.crud_proj_v1.entity.Funcionario;
import school.sptech.crud_proj_v1.entity.abstrato.Produto;
import school.sptech.crud_proj_v1.entity.VendaProduto;
import school.sptech.crud_proj_v1.entity.Venda;

import school.sptech.crud_proj_v1.enumeration.FormaDePagamento;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.mapper.VendaMapper;
import school.sptech.crud_proj_v1.projection.*;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;
import school.sptech.crud_proj_v1.repository.VendaRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ProdutoRepository produtoRepository;
    private final VendaMapper vendaMapper;
    private final ComissaoService comissaoService;
    private final ProdutoService produtoService;



    @Transactional
    public VendaResponseDTO cadastrar(VendaRequestDTO dto) {
        Venda novaVenda = vendaMapper.toEntity(dto);

        Funcionario funcionario = funcionarioRepository.findById(dto.getIdVendedor())
                .orElseThrow(() -> new EntidadeNotFoundException("Vendedor não encontrado"));
        novaVenda.setFuncionario(funcionario);

        novaVenda.setDataHora(LocalDateTime.now());
        novaVenda.setPercentualComissaoAplicado(funcionario.getComissao());

        List<VendaProdutoRequestDTO> itensDto = dto.getItensVenda();
        if (itensDto == null || itensDto.isEmpty()) {
            throw new IllegalArgumentException("A venda deve ter pelo menos um item.");
        }
        Double valorTotal = 0.0;
        List<VendaProduto> novosItensDeVenda = new ArrayList<>();

        for (VendaProdutoRequestDTO itemDto : itensDto) {
            Produto produto = produtoRepository.findById(itemDto.getIdProduto())
                    .orElseThrow(() -> new EntidadeNotFoundException("Produto do id " + itemDto.getIdProduto() + " não encontrado"));

            VendaProduto itemVenda = new VendaProduto();
            itemVenda.setProduto(produto);
            itemVenda.setQuantidadeVendaProduto(itemDto.getQuantidadeVendaProduto());
            itemVenda.setVenda(novaVenda);

            // 1. Extrai o desconto (Se vier nulo, assume 0.0)
            Double descontoAplicado = itemDto.getDesconto() != null ? itemDto.getDesconto() : 0.0;

            // 2. Calcula o subtotal bruto
            Double subtotalBruto = produto.getValorUnitario() * itemDto.getQuantidadeVendaProduto();

            // 3. Valida se o desconto faz sentido
            if (descontoAplicado > subtotalBruto) {
                throw new IllegalArgumentException("O desconto de R$ " + descontoAplicado + " excede o valor do item.");
            }

            // 4. Aplica o desconto no valor final
            Double valorFinalItem = subtotalBruto - descontoAplicado;

            // 5. INJETA NA ENTIDADE PARA O HIBERNATE SALVAR
            itemVenda.setDesconto(descontoAplicado);
            itemVenda.setValorTotalVendaProduto(valorFinalItem);

            novosItensDeVenda.add(itemVenda);

            // Soma no total da venda geral
            valorTotal += itemVenda.getValorTotalVendaProduto();
        }

        novaVenda.setItens(novosItensDeVenda);
        novaVenda.setTotalVenda(arredondar(valorTotal));

        Venda vendaSalva = vendaRepository.save(novaVenda);

        for (VendaProduto item : vendaSalva.getItens()) {
            produtoService.diminuirEstoque(item.getProduto().getId(), item.getQuantidadeVendaProduto());
        }

        comissaoService.calcularComissao(vendaSalva);

        return vendaMapper.toVendaResponseDTO(vendaSalva);
    }

    public VendaResponseDTO buscarPorId(Integer id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Venda não encontrada."));
        return vendaMapper.toVendaResponseDTO(venda);
    }

    public List<VendaResponseDTO> listar() {
        List<Venda> vendas = vendaRepository.findAll();
        return vendaMapper.toVendaResponseDTO(vendas);
    }

    public List<VendaResponseDTO> buscarPorNomeVendedor(String nome) {
        List<Venda> vendas = vendaRepository.findByFuncionarioNomeContainingIgnoreCase(nome);
        return vendaMapper.toVendaResponseDTO(vendas);
    }

    public List<VendaResponseDTO> buscarPorFormaPagamento(FormaDePagamento formaDePagamento){
        List<Venda> vendas = vendaRepository.findByFormaDePagamento(formaDePagamento);
        return vendaMapper.toVendaResponseDTO(vendas);
    }

    @Transactional
    public VendaResponseDTO atualizarPorId(Integer id, VendaRequestDTO dto) {
        // 1. Busca a venda existente
        Venda vendaParaAtualizar = vendaRepository.findById(id)                                                                         .orElseThrow(() -> new EntidadeNotFoundException("Venda com ID não encontrada: " + id));
        // 2. Valida o novo funcionário/vendedor
        Funcionario funcionario = funcionarioRepository.findById(dto.getIdVendedor())
                .orElseThrow(() -> new EntidadeNotFoundException("Vendedor não encontrado"));

        // 3. Atualiza os dados básicos
        vendaParaAtualizar.setFuncionario(funcionario);
        vendaParaAtualizar.setFormaDePagamento(dto.getFormaPagamento());
        vendaParaAtualizar.setPercentualComissaoAplicado(funcionario.getComissao());
        vendaParaAtualizar.setDataHora(LocalDateTime.now());

        // 4. MAPEIA QUANTIDADES ANTIGAS POR PRODUTO (ANTES DE LIMPAR)
        java.util.Map<Integer, Integer> qtdAntigaPorProduto = new java.util.HashMap<>();
        for (VendaProduto itemAntigo : vendaParaAtualizar.getItens()) {
            qtdAntigaPorProduto.merge(itemAntigo.getProduto().getId(), itemAntigo.getQuantidadeVendaProduto(),
                    Integer::sum);
        }

        // 5. LIMPEZA DOS ITENS ANTIGOS
        vendaParaAtualizar.getItens().clear();

        // 6. VALIDAÇÃO DE ITENS NOVOS
        List<VendaProdutoRequestDTO> itensDto = dto.getItensVenda();
        if (itensDto == null || itensDto.isEmpty()) {
            throw new IllegalArgumentException("A venda deve ter pelo menos um item.");
        }

        // 7. MAPEIA QUANTIDADES NOVAS POR PRODUTO E VALIDA DESCONTOS
        java.util.Map<Integer, Integer> qtdNovaPorProduto = new java.util.HashMap<>();
        Double valorTotal = 0.0;

        for (VendaProdutoRequestDTO itemDto : itensDto) {
            Produto produto = produtoRepository.findById(itemDto.getIdProduto())
                    .orElseThrow(() -> new EntidadeNotFoundException("Produto ID não encontrado: " +
                            itemDto.getIdProduto()));

            int qtd = itemDto.getQuantidadeVendaProduto();
            qtdNovaPorProduto.merge(itemDto.getIdProduto(), qtd, Integer::sum);

            Double descontoAplicado = itemDto.getDesconto() != null ? itemDto.getDesconto() : 0.0;
            Double subtotalBruto = produto.getValorUnitario() * itemDto.getQuantidadeVendaProduto();

            if (descontoAplicado > subtotalBruto) {
                throw new IllegalArgumentException("O desconto de R$ " + descontoAplicado + " excede o valor do item.");
            }

            Double valorFinalItem = subtotalBruto - descontoAplicado;
            valorTotal += valorFinalItem;
        }

        // 8. AJUSTE DE ESTOQUE (o que saiu ou reduziu volta, o que aumentou sai)
        for (java.util.Map.Entry<Integer, Integer> entry : qtdAntigaPorProduto.entrySet()) {
            int idProduto = entry.getKey();
            int qtdAntiga = entry.getValue();
            int qtdNova = qtdNovaPorProduto.getOrDefault(idProduto, 0);
            int diferenca = qtdAntiga - qtdNova;

            if (diferenca > 0) {
                produtoRepository.findById(idProduto).ifPresent(p -> {
                    p.setQuantidade(p.getQuantidade() + diferenca);
                    produtoRepository.save(p);
                });
            } else if (diferenca < 0) {
                produtoRepository.findById(idProduto).ifPresent(p -> {
                    int falta = Math.abs(diferenca);
                    if (p.getQuantidade() < falta) {
                        throw new IllegalArgumentException("Estoque insuficiente para o produto ID: " + idProduto);
                    }
                    p.setQuantidade(p.getQuantidade() - falta);
                    produtoRepository.save(p);
                });
            }
        }

        // 9. CRIAÇÃO DOS NOVOS ITENS
        for (VendaProdutoRequestDTO itemDto : itensDto) {
            Produto produto = produtoRepository.findById(itemDto.getIdProduto())
                    .orElseThrow(() -> new EntidadeNotFoundException("Produto ID não encontrado: " +
                            itemDto.getIdProduto()));

            VendaProduto itemVenda = new VendaProduto();
            itemVenda.setProduto(produto);
            itemVenda.setQuantidadeVendaProduto(itemDto.getQuantidadeVendaProduto());

            Double descontoAplicado = itemDto.getDesconto() != null ? itemDto.getDesconto() : 0.0;
            Double subtotalBruto = produto.getValorUnitario() * itemDto.getQuantidadeVendaProduto();
            Double valorFinalItem = subtotalBruto - descontoAplicado;

            itemVenda.setDesconto(descontoAplicado);
            itemVenda.setValorTotalVendaProduto(valorFinalItem);
            itemVenda.setVenda(vendaParaAtualizar);

            vendaParaAtualizar.getItens().add(itemVenda);
        }

        // 10. Atualiza o total
        vendaParaAtualizar.setTotalVenda(valorTotal);

        // 11. Persiste
        Venda vendaSalva = vendaRepository.save(vendaParaAtualizar);

        // 12. Atualiza comissão
        comissaoService.calcularComissao(vendaSalva);

        return vendaMapper.toVendaResponseDTO(vendaSalva);
    }

    public void deletarVendaPorId(Integer id) {
        if (!vendaRepository.existsById(id))
            throw new EntidadeNotFoundException("Venda com ID " + id + " não encontrada.");

        vendaRepository.deleteById(id);
    }

    public Double calcularTotal() {
        List<Venda> vendas = vendaRepository.findAll();

        return vendas.stream()
                .mapToDouble(Venda::getTotalVenda)
                .sum();
    }

    public List<MetodoPagamentoProjection> buscarDesempenhoPagamentos(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.buscarDesempenhoPagamentosDinamico(inicio, fim);
    }

    public List<ProdutoRentavelProjection> buscarProdutosRentaveis(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.buscarProdutosMaisRentaveisDinamico(inicio, fim);
    }

    public List<MargemCategoriaProjection> buscarMargemCategoria(LocalDateTime inicio, LocalDateTime fim) {
        return vendaRepository.buscarMargemPorCategoriaDinamico(inicio, fim);
    }

    private double arredondar(Double valor) {
        if (valor == null) return 0.0;
        return Double.valueOf(valor);

    }

}

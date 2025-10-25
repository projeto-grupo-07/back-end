package school.sptech.crud_proj_v1.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.dto.ItensVenda.ItensVendaRequestDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaRequestDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;
import school.sptech.crud_proj_v1.entity.Funcionario;
import school.sptech.crud_proj_v1.entity.Produto;
import school.sptech.crud_proj_v1.entity.ItensVenda;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.mapper.VendaMapper;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;
import school.sptech.crud_proj_v1.repository.VendaRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ProdutoRepository produtoRepository;
    private final VendaMapper vendaMapper;

    public VendaService(
            VendaRepository vendaRepository,
            FuncionarioRepository funcionarioRepository,
            ProdutoRepository produtoRepository, VendaMapper vendaMapper
    ) {
        this.vendaRepository = vendaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.produtoRepository = produtoRepository;
        this.vendaMapper = vendaMapper;
    }

    @Transactional
    public VendaResponseDTO cadastrar(VendaRequestDTO dto) {
        Venda novaVenda = vendaMapper.toEntity(dto);

        Funcionario funcionario = funcionarioRepository.findById(dto.getIdVendedor())
                .orElseThrow(() -> new EntidadeNotFoundException("Vendedor não encontrado"));
        novaVenda.setFuncionario(funcionario);

        novaVenda.setDataHora(LocalDateTime.now());

        List<ItensVendaRequestDTO> itensDto = dto.getItensVenda();
        if (itensDto == null || itensDto.isEmpty()) {
            throw new IllegalArgumentException("A venda deve ter pelo menos um item.");
        }

        Double valorTotal = 0.0;
        List<ItensVenda> novosItensDeVenda = new ArrayList<>();

        for (ItensVendaRequestDTO itemDto : itensDto) {
            Produto produto = produtoRepository.findById(itemDto.getIdProduto())
                    .orElseThrow(() -> new EntidadeNotFoundException("Produto do id " + itemDto.getIdProduto() + " não encontrado"));

            ItensVenda itemVenda = new ItensVenda();
            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(itemDto.getQuantidade());
            itemVenda.setPrecoVenda(produto.getPrecoVenda());
            itemVenda.setVenda(novaVenda);

            novosItensDeVenda.add(itemVenda);

            valorTotal += (itemVenda.getPrecoVenda() * itemVenda.getQuantidade());
        }

        novaVenda.setItens(novosItensDeVenda);
        novaVenda.setTotalVenda(valorTotal);

        Venda vendaSalva = vendaRepository.save(novaVenda);

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

    public List<VendaResponseDTO> buscarPorFormaPagamento(String formPgto){
        List<Venda> vendas = vendaRepository.findByformaDePagamentoContainingIgnoreCase(formPgto);
        return vendaMapper.toVendaResponseDTO(vendas);
    }

    public VendaResponseDTO atualizarPorId(Integer id, VendaRequestDTO dto) {
        Venda vendaParaAtualizar = vendaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Venda com ID não encontrada: " + id));

        Funcionario funcionario = funcionarioRepository.findById(dto.getIdVendedor())
                .orElseThrow(() -> new EntidadeNotFoundException("Vendedor não encontrado"));

        vendaParaAtualizar.setFuncionario(funcionario);
        vendaParaAtualizar.setFormaDePagamento(dto.getFormaPagamento());
        vendaParaAtualizar.setDataHora(LocalDateTime.now());

        vendaParaAtualizar.getItens().clear();

        List<ItensVendaRequestDTO> itensDto = dto.getItensVenda();
        if (itensDto == null || itensDto.isEmpty()) {
            throw new IllegalArgumentException("A venda deve ter pelo menos um item.");
        }

        Double valorTotal = 0.0;
        List<ItensVenda> novosItensDeVenda = new ArrayList<>();

        for (ItensVendaRequestDTO itemDto : itensDto) {
            Produto produto = produtoRepository.findById(itemDto.getIdProduto())
                    .orElseThrow(() -> new EntidadeNotFoundException("Produto do id não encontrado: " + itemDto.getIdProduto()));

            ItensVenda itemVenda = new ItensVenda();
            itemVenda.setProduto(produto);
            itemVenda.setQuantidade(itemDto.getQuantidade());
            itemVenda.setPrecoVenda(produto.getPrecoVenda());
            itemVenda.setVenda(vendaParaAtualizar);
            novosItensDeVenda.add(itemVenda);
            valorTotal += (itemVenda.getPrecoVenda() * itemVenda.getQuantidade());
        }

        vendaParaAtualizar.setItens(novosItensDeVenda);
        vendaParaAtualizar.setTotalVenda(valorTotal);

        Venda vendaSalva = vendaRepository.save(vendaParaAtualizar);

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
}

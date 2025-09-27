package school.sptech.crud_proj_v1.mapper;

import school.sptech.crud_proj_v1.dto.ProdutosVendaResponseDTO;
import school.sptech.crud_proj_v1.dto.VendaRequestDTO;
import school.sptech.crud_proj_v1.dto.*;
import school.sptech.crud_proj_v1.dto.VendaResponseDTO;
import school.sptech.crud_proj_v1.entity.*;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.repository.ClienteRepository;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class VendaRequestMapper {

    // declarando os repositorios para pegar as fk's
    public static Venda toEntity(
            VendaRequestDTO dto,
            FuncionarioRepository funcionarioRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository
    ){
        if (dto == null) {
            return null;
        }

        Venda venda = new Venda();

        // Buscando funcionário e cliente
        Funcionario funcionario = funcionarioRepository.findById(dto.getIdVendedor())
                .orElseThrow(() -> new EntidadeNotFoundException("vendedor não encontrado"));

        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new EntidadeNotFoundException("cliente não encontrado"));

        // *** CORREÇÃO: SETANDO OS OBJETOS NA VENDA ***
        venda.setVendedor(funcionario);
        venda.setCliente(cliente);

        venda.setFormaPagamento(dto.getFormaPagamento());
        venda.setDataHora(LocalDateTime.now());

        BigDecimal valorTotal = mapProdutosVenda(dto.getProdutos(), venda, produtoRepository);
        venda.setValorTotal(valorTotal);

        return venda;
    }

    private static BigDecimal mapProdutosVenda(
            List<ProdutoVendaItemRequestDTO> itensDto,
            Venda venda,
            ProdutoRepository produtoRepository
    ){
        List<ProdutosVenda> itensVenda = itensDto.stream()
                .map(itemDto ->{
                    Produto produto = produtoRepository.findById(itemDto.getIdProduto())
                            .orElseThrow(() -> new EntidadeNotFoundException("Produto do id " + itemDto.getIdProduto() + " não encontrado"));
                    ProdutosVenda itemVenda = new ProdutosVenda();
                    itemVenda.setProduto(produto);
                    itemVenda.setQuantidade(itemDto.getQuantidade());
                    itemVenda.setPrecoVenda(itemDto.getPrecoVenda());

                    itemVenda.setVenda(venda);

                    return itemVenda;
                }).toList();
        venda.setProdutos(itensVenda);

        BigDecimal total = BigDecimal.ZERO;

        for (ProdutosVenda i : itensVenda){
            total = total.add(i.getPrecoVenda().multiply(new BigDecimal(i.getQuantidade())));
        }
        return total;
    }
}
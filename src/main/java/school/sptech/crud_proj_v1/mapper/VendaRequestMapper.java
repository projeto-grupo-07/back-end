package school.sptech.crud_proj_v1.mapper;

import school.sptech.crud_proj_v1.dto.ProdutoVenda.ProdutoVendaItemRequestDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaRequestDTO;
import school.sptech.crud_proj_v1.entity.*;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VendaRequestMapper {

    // declarando os repositorios para pegar as fk's
    public static Venda toEntity(
            VendaRequestDTO dto,
            FuncionarioRepository funcionarioRepository,
            ProdutoRepository produtoRepository
    ){
        if (dto == null) {
            return null;
        }

        Venda venda = new Venda();

        // Buscando funcionário e cliente
        Funcionario funcionario = funcionarioRepository.findById(dto.getIdVendedor())
                .orElseThrow(() -> new EntidadeNotFoundException("vendedor não encontrado"));


        // *** CORREÇÃO: SETANDO OS OBJETOS NA VENDA ***
        venda.setFuncionario(funcionario);

        venda.setFormaDePagamento(dto.getFormaPagamento());
        venda.setDataHora(LocalDateTime.now());

        Double valorTotal = mapProdutosVenda(dto.getProdutos(), venda, produtoRepository);
        venda.setTotalVenda(valorTotal);

        return venda;
    }

    private static Double mapProdutosVenda(
            List<ProdutoVendaItemRequestDTO> itensDto,
            Venda venda,
            ProdutoRepository produtoRepository
    ){
        List<ProdutoVenda> itensVenda = itensDto.stream()
                .map(itemDto ->{
                    Produto produto = produtoRepository.findById(itemDto.getIdProduto())
                            .orElseThrow(() -> new EntidadeNotFoundException("Produto do id " + itemDto.getIdProduto() + " não encontrado"));
                    ProdutoVenda itemVenda = new ProdutoVenda();
                    itemVenda.setProduto(produto);
                    itemVenda.setQuantidade(itemDto.getQuantidade());
                    itemVenda.setPrecoVenda(itemDto.getPrecoVenda());

                    itemVenda.setVenda(venda);

                    return itemVenda;
                }).toList();
        venda.setItens(itensVenda);

        Double total = 0.0;


        for (ProdutoVenda i : itensVenda){
            total += i.getPrecoVenda();
        }
        return total;
    }
}
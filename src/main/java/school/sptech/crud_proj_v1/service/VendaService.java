package school.sptech.crud_proj_v1.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.dto.Venda.VendaRequestDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;
import school.sptech.crud_proj_v1.entity.ProdutoVenda;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.mapper.VendaMapper;
import school.sptech.crud_proj_v1.mapper.VendaRequestMapper;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;
import school.sptech.crud_proj_v1.repository.VendaRepository;
import school.sptech.crud_proj_v1.repository.ProdutosVendaRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutosVendaRepository produtosVendaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ProdutoRepository produtoRepository;

    public VendaService(
            VendaRepository vendaRepository,
            ProdutosVendaRepository produtosVendaRepository,
            FuncionarioRepository funcionarioRepository,
            ProdutoRepository produtoRepository
    ) {
        this.vendaRepository = vendaRepository;
        this.produtosVendaRepository = produtosVendaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.produtoRepository = produtoRepository;
    }

    public VendaResponseDTO cadastrarVenda(VendaRequestDTO vendaDto) {
        Venda novaVenda = VendaRequestMapper.toEntity(
                vendaDto,
                funcionarioRepository,
                produtoRepository
        );



        List<ProdutoVenda> itensVenda = novaVenda.getItens();
        novaVenda.setItens(null);

        Venda vendaSalva = vendaRepository.save(novaVenda);


        for (ProdutoVenda item : itensVenda) {
            item.setVenda(vendaSalva);
            produtosVendaRepository.save(item);
        }


        Optional<Venda> opVenda = vendaRepository.findById(vendaSalva.getId());
        if (opVenda.isPresent()) {
            return VendaMapper.toVendaResponseDTO((opVenda.get()));
        }

        throw new EntidadeNotFoundException("Venda não encontrada");

    }

    public VendaResponseDTO buscarVendaPorId(Integer id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Venda não encontrada."));
        return VendaMapper.toVendaResponseDTO(venda);
    }

    public List<VendaResponseDTO> listarTodasVendas() {
        List<Venda> vendas = vendaRepository.findAll();

        // DEBUG - adicione isso temporariamente
        for (Venda venda : vendas) {
            System.out.println("=== DEBUG Venda ID: " + venda.getId() + " ===");
            System.out.println("Vendedor objeto: " + venda.getFuncionario());

            if (venda.getFuncionario() != null) {
                System.out.println("ID Vendedor: " + venda.getFuncionario().getId());
            } else {
                System.out.println("VENDEDOR É NULL!");
            }


            System.out.println("========================");
        }

        return VendaMapper.toVendaResponseDTO(vendas);
    }

    public List<VendaResponseDTO> buscarPorNomeVendedor(String nome) {
        List<Venda> vendas = vendaRepository.findByFuncionarioNomeContainingIgnoreCase(nome);
        return VendaMapper.toVendaResponseDTO(vendas);
    }

    public List<VendaResponseDTO> buscarPorFormaPagamento(String formPgto){
        List<Venda> vendas = vendaRepository.findByformaDePagamentoContainingIgnoreCase(formPgto);
        return VendaMapper.toVendaResponseDTO(vendas);
    }
}

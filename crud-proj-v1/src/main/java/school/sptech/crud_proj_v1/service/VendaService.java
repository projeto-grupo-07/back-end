package school.sptech.crud_proj_v1.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.dto.VendaRequestDTO;
import school.sptech.crud_proj_v1.dto.VendaResponseDTO;
import school.sptech.crud_proj_v1.entity.ProdutosVenda;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.mapper.VendaMapper;
import school.sptech.crud_proj_v1.mapper.VendaRequestMapper;
import school.sptech.crud_proj_v1.repository.ClienteRepository;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;
import school.sptech.crud_proj_v1.repository.VendaRepository;
import school.sptech.crud_proj_v1.repository.ProdutosVendaRepository;

import java.util.List;

@Service
@Transactional
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutosVendaRepository produtosVendaRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;

    public VendaService(
            VendaRepository vendaRepository,
            ProdutosVendaRepository produtosVendaRepository,
            FuncionarioRepository funcionarioRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository
    ) {
        this.vendaRepository = vendaRepository;
        this.produtosVendaRepository = produtosVendaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
    }

    public Venda cadastrarVenda(VendaRequestDTO vendaDto) {
        Venda novaVenda = VendaRequestMapper.toEntity(
                vendaDto,
                funcionarioRepository,
                clienteRepository,
                produtoRepository
        );

        List<ProdutosVenda> itensVenda = novaVenda.getProdutos();
        novaVenda.setProdutos(null);

        Venda vendaSalva = vendaRepository.save(novaVenda);

        for (ProdutosVenda item : itensVenda) {
            item.setVenda(vendaSalva);
            produtosVendaRepository.save(item);
        }

        return vendaRepository.findById(vendaSalva.getId())
                .orElseThrow(() -> new RuntimeException("Venda salva, mas falha ao recarregar."));
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
            System.out.println("Vendedor objeto: " + venda.getVendedor());
            System.out.println("Cliente objeto: " + venda.getCliente());

            if (venda.getVendedor() != null) {
                System.out.println("ID Vendedor: " + venda.getVendedor().getId());
            } else {
                System.out.println("VENDEDOR É NULL!");
            }

            if (venda.getCliente() != null) {
                System.out.println("ID Cliente: " + venda.getCliente().getId());
            } else {
                System.out.println("CLIENTE É NULL!");
            }
            System.out.println("========================");
        }

        return VendaMapper.toVendaResponseDTO(vendas);
    }

    public List<VendaResponseDTO> buscarPorNomeVendedor(String nome) {
        List<Venda> vendas = vendaRepository.findByVendedor_NomeContainingIgnoreCase(nome);
        return VendaMapper.toVendaResponseDTO(vendas);
    }
}

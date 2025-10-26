package school.sptech.crud_proj_v1.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoResponseDTO;
import school.sptech.crud_proj_v1.dto.Produto.ProdutoRequestDTO;
import school.sptech.crud_proj_v1.entity.Categoria;
import school.sptech.crud_proj_v1.entity.Produto;
import school.sptech.crud_proj_v1.event.ProdutoCadastradoEvent;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.mapper.ProdutoMapper;
import school.sptech.crud_proj_v1.repository.CategoriaRepository;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ProdutoMapper produtoMapper;
    private final FuncionarioService funcionarioService;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository, ApplicationEventPublisher eventPublisher, ProdutoMapper produtoMapper, FuncionarioService funcionarioService) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.eventPublisher = eventPublisher;
        this.produtoMapper = produtoMapper;
        this.funcionarioService = funcionarioService;
    }


    public List<ProdutoResponseDTO> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();

        return produtoMapper.produtoResponseDTOS(produtos);
    }

    public ProdutoResponseDTO criar(ProdutoRequestDTO novoProdutoDTO) {
        Produto novoProduto = produtoMapper.toEntity(novoProdutoDTO);

        if (novoProdutoDTO.getCategoriaId() != null) {
            Categoria categoriaEncontrada = categoriaRepository
                    .findById(novoProdutoDTO.getCategoriaId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Categoria com o ID informado não foi encontrada.")
                    );
            novoProduto.setCategoria(categoriaEncontrada);
        }

        var evento = new ProdutoCadastradoEvent(novoProduto);
        eventPublisher.publishEvent(evento);
        funcionarioService.handleProdutoCadastrado(evento);

        Produto produtoSalvo = produtoRepository.save(novoProduto);
        System.out.println("Produto " + produtoSalvo.getModelo() + " salvo!");
        return produtoMapper.toResponseDTO(produtoSalvo);
    }

    public ProdutoResponseDTO atualizarPorId(Integer id, ProdutoRequestDTO dto) {
        Produto produtoParaAtualizar = produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Produto não encontrado pelo ID: " + id));

        Categoria novaCategoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new EntidadeNotFoundException("Categoria não encontrado pelo ID: " + dto.getCategoriaId()));

        produtoParaAtualizar.setModelo(dto.getModelo());
        produtoParaAtualizar.setMarca(dto.getMarca());
        produtoParaAtualizar.setTamanho(dto.getTamanho());
        produtoParaAtualizar.setCor(dto.getCor());
        produtoParaAtualizar.setPrecoCusto(dto.getPrecoCusto());
        produtoParaAtualizar.setPrecoVenda(dto.getPrecoVenda());

        produtoParaAtualizar.setCategoria(novaCategoria);

        Produto produtoSalvo = produtoRepository.save(produtoParaAtualizar);

        return produtoMapper.toResponseDTO(produtoSalvo);
    }

    public List<ProdutoResponseDTO> buscarProdutoPorModelo(String modelo){
        return produtoMapper.produtoResponseDTOS(produtoRepository.findByModeloContainingIgnoreCase(modelo));
    }

    public List<ProdutoResponseDTO> buscarProdutoPorMarca(String marca){
        return produtoMapper.produtoResponseDTOS(produtoRepository.findByMarcaContainingIgnoreCase(marca));
    }

    public List<ProdutoResponseDTO> buscarProdutoPorCategoria(String categoria){
        return produtoMapper.produtoResponseDTOS(produtoRepository.findByCategoriaDescricaoContainingIgnoreCase(categoria));
    }

    public List<ProdutoResponseDTO> buscarProdutoPorCategoriaOrdenadoPorPrecoDesc(String categoria) {
        return produtoMapper.produtoResponseDTOS(produtoRepository.findByCategoriaDescricaoContainingIgnoreCaseOrderByPrecoVendaDesc(categoria));
    }

    public void deletarPorId(Integer id) {
        if (!produtoRepository.existsById(id)) {
            throw new EntidadeNotFoundException("Produto não encontrado pelo ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
}

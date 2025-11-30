package school.sptech.crud_proj_v1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crud_proj_v1.dto.Produto.*;
import school.sptech.crud_proj_v1.entity.CalcadoProduto;
import school.sptech.crud_proj_v1.entity.Categoria;
import school.sptech.crud_proj_v1.entity.OutrosProduto;
import school.sptech.crud_proj_v1.entity.abstrato.Produto;
import school.sptech.crud_proj_v1.event.ProdutoCadastradoEvent;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.mapper.CalcadoProdutoMapper;
import school.sptech.crud_proj_v1.mapper.OutrosProdutoMapper;
import school.sptech.crud_proj_v1.repository.CategoriaRepository;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final FuncionarioService funcionarioService;
    private final CalcadoProdutoMapper calcadoMapper;
    private final OutrosProdutoMapper outrosMapper;

    private void configurarCategoria(Produto produto, Integer categoriaId) {
        if (categoriaId != null) {
            Categoria categoriaEncontrada = categoriaRepository.findById(categoriaId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Categoria não encontrada com ID: " + categoriaId));

            produto.setCategoria(categoriaEncontrada);
        }
    }

    public CalcadoProdutoResponse cadastrarCalcado(CalcadoProdutoRequest dto) {
        CalcadoProduto novoCalcado = calcadoMapper.toEntity(dto);
        configurarCategoria(novoCalcado, dto.getIdCategoria());

        var evento = new ProdutoCadastradoEvent(novoCalcado);
        eventPublisher.publishEvent(evento);
        funcionarioService.handleProdutoCadastrado(evento);

        CalcadoProduto salvo = produtoRepository.save(novoCalcado);

        System.out.println("Calçado " + salvo.getModelo() + " salvo!");

        return calcadoMapper.toResponse(salvo);
    }

    public OutrosProdutoResponse cadastrarOutros(OutrosProdutoRequest dto) {
        OutrosProduto novoOutros = outrosMapper.toEntity(dto);

        configurarCategoria(novoOutros, dto.getIdCategoria());

        var evento = new ProdutoCadastradoEvent(novoOutros);
        eventPublisher.publishEvent(evento);
        funcionarioService.handleProdutoCadastrado(evento);

        OutrosProduto salvo = produtoRepository.save(novoOutros);
        System.out.println("Produto Outros " + salvo.getNome() + " salvo!");

        return outrosMapper.toResponse(salvo);
    }

    public List<ProdutoResponse> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream()
                .map(produto -> {
                    if (produto instanceof CalcadoProduto) {
                        return calcadoMapper.toResponse((CalcadoProduto) produto);
                    } else if (produto instanceof OutrosProduto) {
                        return outrosMapper.toResponse((OutrosProduto) produto);
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }


    public List<CalcadoProdutoResponse> listarApenasCalcados() {
        return calcadoMapper.toResponseList(produtoRepository.findAllCalcados());
    }

    public List<OutrosProdutoResponse> listarApenasOutros() {
        return outrosMapper.toResponseList(produtoRepository.findAllOutros());
    }

    public List<ProdutoResponse> listarProdutosOrdenadoPorMaiorQuantidade() {
        List<Produto> produtosOrdenados = produtoRepository.findAllByOrderByQuantidadeDesc();

        return produtosOrdenados.stream()
                .map(produto -> {
                    if (produto instanceof CalcadoProduto calcado) {
                        return calcadoMapper.toResponse(calcado);
                    } else if (produto instanceof OutrosProduto outros) {
                        return outrosMapper.toResponse(outros);
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    public List<ProdutoResponse> listarProdutosOrdenadoPorMenorQuantidade() {
        List<Produto> produtosOrdenados = produtoRepository.findAllByOrderByQuantidadeAsc();

        return produtosOrdenados.stream()
                .map(produto -> {
                    if (produto instanceof CalcadoProduto calcado) {
                        return calcadoMapper.toResponse(calcado);
                    } else if (produto instanceof OutrosProduto outros) {
                        return outrosMapper.toResponse(outros);
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    public ProdutoResponse buscarProdutoPorId(Integer id){
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Produto com id não encontrado: " + id));

        if (produto instanceof CalcadoProduto calcado) {
            return calcadoMapper.toResponse(calcado);
        } else if (produto instanceof OutrosProduto outros) {
            return outrosMapper.toResponse(outros);
        }

        throw new EntidadeNotFoundException("Tipo desconhecido.");
    }

    public CalcadoProdutoResponse atualizarCalcado(Integer id, CalcadoProdutoRequest dto) {

        Produto produtoGenérico = produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Produto não encontrado pelo ID: " + id));

        if (produtoGenérico instanceof CalcadoProduto calcado) {
            calcadoMapper.toUpdate(dto, calcado);

            if (dto.getIdCategoria() != null) {
                configurarCategoria(calcado, dto.getIdCategoria());
            }

            CalcadoProduto salvo = produtoRepository.save(calcado);
            return calcadoMapper.toResponse(salvo);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Este ID pertence a um produto do tipo 'Outros', use o endpoint correto.");
        }
    }

    public OutrosProdutoResponse atualizarOutros(Integer id, OutrosProdutoRequest dto) {
        Produto produtoGenérico = produtoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Produto não encontrado pelo ID: " + id));

        if (produtoGenérico instanceof OutrosProduto outros) {

            outrosMapper.toUpdate(dto, outros);

            if (dto.getIdCategoria() != null) {
                configurarCategoria(outros, dto.getIdCategoria());
            }

            OutrosProduto salvo = produtoRepository.save(outros);
            return outrosMapper.toResponse(salvo);

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Este ID pertence a um produto do tipo 'Calçado', use o endpoint correto.");
        }
    }

    public void deletarPorId(Integer id) {
        if (!produtoRepository.existsById(id)) {
            throw new EntidadeNotFoundException("Produto não encontrado pelo ID: " + id);
        }
        produtoRepository.deleteById(id);
    }


    public List<ProdutoResponse> buscarProdutoPorCategoria(String categoria) {
        List<Produto> produtos = produtoRepository.findByCategoriaDescricaoContainingIgnoreCase(categoria);

        return produtos.stream()
                .map(produto -> {
                    if (produto instanceof CalcadoProduto calcado) {
                        return calcadoMapper.toResponse(calcado);
                    }
                    else if (produto instanceof OutrosProduto outros) {
                        return outrosMapper.toResponse(outros);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<ProdutoResponse> buscarProdutoPorCategoriaOrdenadoPorPrecoDesc(String categoria) {
        List<Produto> produtos = produtoRepository.findByCategoriaDescricaoContainingIgnoreCaseOrderByValorUnitarioDesc(categoria);
        return produtos.stream()
                .map(produto -> {
                    if (produto instanceof CalcadoProduto calcado) {
                        return calcadoMapper.toResponse(calcado);
                    }
                    else if (produto instanceof OutrosProduto outros) {
                        return outrosMapper.toResponse(outros);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public CalcadoProdutoResponse buscarCalcadoPorModelo(String modelo){
        return calcadoMapper.toResponse(produtoRepository.findByModeloContainingIgnoreCase(modelo));
    }

    public List<CalcadoProdutoResponse> buscarCalcadoPorMarca(String marca){
        return calcadoMapper.toResponseList(produtoRepository.findByMarcaContainingIgnoreCase(marca));
    }

    public List<CalcadoProdutoResponse> buscarCalcadoPorNumero(Integer numero){
        return calcadoMapper.toResponseList(produtoRepository.findByNumero(numero));
    }

    public List<CalcadoProdutoResponse> buscarCalcadoPorCor(String cor){
        return calcadoMapper.toResponseList(produtoRepository.findByCorContainingIgnoreCase(cor));
    }

    public OutrosProdutoResponse buscarOutrosPorNome(String nome) {
        return outrosMapper.toResponse(produtoRepository.findByNomeContainingIgnoreCase(nome));
    }

    public List<OutrosProdutoResponse> buscarOutrosPorDescricao(String descricao) {
        return outrosMapper.toResponseList(produtoRepository.findByDescricaoContainingIgnoreCase(descricao));
    }

    public void diminuirEstoque(Integer idProduto, Integer quantidadeVendida){
        Produto produto = produtoRepository.findById(idProduto)
                .orElseThrow(() -> new  EntidadeNotFoundException("Produto não encontrado pelo ID: " + idProduto));

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidadeVendida);
        produtoRepository.save(produto);
    }
}

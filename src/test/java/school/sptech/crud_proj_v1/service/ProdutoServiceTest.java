package school.sptech.crud_proj_v1.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private CategoriaRepository categoriaRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private FuncionarioService funcionarioService;
    @Mock
    private CalcadoProdutoMapper calcadoMapper;
    @Mock
    private OutrosProdutoMapper outrosMapper;

    @InjectMocks
    private ProdutoService service;

    @Test
    @DisplayName("CadastrarCalçado: deve salvar com sucesso")
    void cadastrarCalcadoComSucesso() {
        CalcadoProdutoRequest dto = new CalcadoProdutoRequest();
        dto.setIdCategoria(1);

        CalcadoProduto calcado = new CalcadoProduto();
        calcado.setModelo("Nike Air");

        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setDescricao("Esportes");

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(calcadoMapper.toEntity(dto)).thenReturn(calcado);
        when(produtoRepository.save(calcado)).thenReturn(calcado);
        when(calcadoMapper.toResponse(calcado)).thenReturn(new CalcadoProdutoResponse());

        CalcadoProdutoResponse resultado = service.cadastrarCalcado(dto);

        assertNotNull(resultado);
        verify(produtoRepository, times(1)).save(calcado);
        verify(eventPublisher, times(1)).publishEvent(any(ProdutoCadastradoEvent.class));
        verify(funcionarioService, times(1)).handleProdutoCadastrado(any(ProdutoCadastradoEvent.class));
    }

    @Test
    @DisplayName("CadastrarOutros: deve salvar com sucesso")
    void cadastrarOutrosComSucesso() {
        OutrosProdutoRequest dto = new OutrosProdutoRequest();
        dto.setIdCategoria(1);

        OutrosProduto outros = new OutrosProduto();
        outros.setNome("Caderno");

        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setDescricao("Papelaria");

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoria));
        when(outrosMapper.toEntity(dto)).thenReturn(outros);
        when(produtoRepository.save(outros)).thenReturn(outros);
        when(outrosMapper.toResponse(outros)).thenReturn(new OutrosProdutoResponse());

        OutrosProdutoResponse resultado = service.cadastrarOutros(dto);

        assertNotNull(resultado);
        verify(produtoRepository, times(1)).save(outros);
        verify(eventPublisher, times(1)).publishEvent(any(ProdutoCadastradoEvent.class));
        verify(funcionarioService, times(1)).handleProdutoCadastrado(any(ProdutoCadastradoEvent.class));
    }

    @Test
    @DisplayName("ListarTodos: deve retornar lista com produtos")
    void listarTodosComSucesso() {
        CalcadoProduto calcado = new CalcadoProduto();
        OutrosProduto outros = new OutrosProduto();

        when(produtoRepository.findAll()).thenReturn(List.of(calcado, outros));
        when(calcadoMapper.toResponse(calcado)).thenReturn(new CalcadoProdutoResponse());
        when(outrosMapper.toResponse(outros)).thenReturn(new OutrosProdutoResponse());

        List<ProdutoResponse> resultado = service.listarTodos();

        assertEquals(2, resultado.size());
    }

    @Test
    @DisplayName("BuscarPorId: deve lançar exceção quando não encontrado")
    void buscarPorIdNaoEncontrado() {
        when(produtoRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntidadeNotFoundException.class, () -> service.buscarProdutoPorId(1));
    }

    @Test
    @DisplayName("AtualizarCalcado: deve lançar exceção quando produto não for calçado")
    void atualizarCalcadoComProdutoErrado() {
        OutrosProduto outros = new OutrosProduto();
        when(produtoRepository.findById(1)).thenReturn(Optional.of(outros));

        CalcadoProdutoRequest dto = new CalcadoProdutoRequest();

        assertThrows(ResponseStatusException.class, () -> service.atualizarCalcado(1, dto));
    }

    @Test
    @DisplayName("AtualizarOutros: deve lançar exceção quando produto não for 'outros'")
    void atualizarOutrosComProdutoErrado() {
        CalcadoProduto calcado = new CalcadoProduto();
        when(produtoRepository.findById(1)).thenReturn(Optional.of(calcado));

        OutrosProdutoRequest dto = new OutrosProdutoRequest();

        assertThrows(ResponseStatusException.class, () -> service.atualizarOutros(1, dto));
    }

    @Test
    @DisplayName("Deletar: deve lançar exceção quando produto não existir")
    void deletarNaoEncontrado() {
        when(produtoRepository.existsById(1)).thenReturn(false);
        assertThrows(EntidadeNotFoundException.class, () -> service.deletarPorId(1));
        verify(produtoRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("DiminuirEstoque: deve atualizar quantidade corretamente")
    void diminuirEstoqueComSucesso() {
        CalcadoProduto calcado = new CalcadoProduto();
        calcado.setId(1);
        calcado.setQuantidade(10);

        when(produtoRepository.findById(1)).thenReturn(Optional.of(calcado));
        when(produtoRepository.save(calcado)).thenReturn(calcado);

        service.diminuirEstoque(1, 3);

        assertEquals(7, calcado.getQuantidade());
        verify(produtoRepository, times(1)).save(calcado);
    }
}

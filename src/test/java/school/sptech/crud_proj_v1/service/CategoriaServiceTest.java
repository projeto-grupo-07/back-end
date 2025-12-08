package school.sptech.crud_proj_v1.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaPaiRequestDto;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaRequestDto;
import school.sptech.crud_proj_v1.entity.Categoria;
import school.sptech.crud_proj_v1.exception.EntidadeConflitoException;
import school.sptech.crud_proj_v1.repository.CategoriaRepository;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private CategoriaService service;

    @Test
    @DisplayName("CadastrarPai: deve salvar categoria pai com sucesso")
    void cadastrarPaiComSucesso() {
        CategoriaPaiRequestDto dto = new CategoriaPaiRequestDto();
        dto.setDescricao("Eletrônicos");

        when(categoriaRepository.existsByDescricao("Eletrônicos")).thenReturn(false);

        Categoria categoria = new Categoria();
        categoria.setDescricao("Eletrônicos");

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        assertDoesNotThrow(() -> service.cadastrarPai(dto));
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    @DisplayName("CadastrarPai: deve lançar exceção quando descrição já existir")
    void cadastrarPaiConflito() {
        CategoriaPaiRequestDto dto = new CategoriaPaiRequestDto();
        dto.setDescricao("Eletrônicos");

        when(categoriaRepository.existsByDescricao("Eletrônicos")).thenReturn(true);

        assertThrows(EntidadeConflitoException.class, () -> service.cadastrarPai(dto));
        verify(categoriaRepository, never()).save(any());
    }

    @Test
    @DisplayName("CadastrarFilho: deve salvar categoria filha com sucesso")
    void cadastrarFilhoComSucesso() {
        CategoriaRequestDto dto = new CategoriaRequestDto();
        dto.setDescricao("Celulares");
        dto.setCategoriaPaiId(1);

        Categoria pai = new Categoria();
        pai.setId(1);
        pai.setDescricao("Eletrônicos");

        when(categoriaRepository.existsByDescricao("Celulares")).thenReturn(false);
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(pai));

        Categoria filho = new Categoria();
        filho.setDescricao("Celulares");
        filho.setCategoriaPai(pai);

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(filho);

        assertDoesNotThrow(() -> service.cadastrarFilho(dto));
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    @DisplayName("CadastrarFilho: deve lançar exceção quando categoria pai não existir")
    void cadastrarFilhoPaiNaoEncontrado() {
        CategoriaRequestDto dto = new CategoriaRequestDto();
        dto.setDescricao("Celulares");
        dto.setCategoriaPaiId(99);

        when(categoriaRepository.existsByDescricao("Celulares")).thenReturn(false);
        when(categoriaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.cadastrarFilho(dto));
    }

    @Test
    @DisplayName("ListarPaiPorId: deve retornar null quando não existir")
    void listarPaiPorIdNaoEncontrado() {
        when(categoriaRepository.findById(1)).thenReturn(Optional.empty());
        assertNull(service.listarPaiPorId(1));
    }

    @Test
    @DisplayName("Deletar: deve lançar exceção quando categoria não existir")
    void deletarNaoEncontrada() {
        when(categoriaRepository.existsById(1)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> service.deletarPorId(1));
        verify(categoriaRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deletar: deve lançar exceção quando houver produtos associados")
    void deletarComProdutosAssociados() {
        when(categoriaRepository.existsById(1)).thenReturn(true);
        when(produtoRepository.existsByCategoriaId(1)).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> service.deletarPorId(1));
        verify(categoriaRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deletar: deve deletar com sucesso quando não houver produtos associados")
    void deletarComSucesso() {
        when(categoriaRepository.existsById(1)).thenReturn(true);
        when(produtoRepository.existsByCategoriaId(1)).thenReturn(false);

        service.deletarPorId(1);

        verify(categoriaRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("AtualizarFilho: deve lançar exceção quando id não existir")
    void atualizarFilhoNaoEncontrado() {
        CategoriaRequestDto dto = new CategoriaRequestDto();
        dto.setCategoriaPaiId(1);

        when(categoriaRepository.existsById(1)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> service.atualizarTotalFilho(1, dto));
    }

    @Test
    @DisplayName("AtualizarPai: deve lançar exceção quando id não existir")
    void atualizarPaiNaoEncontrado() {
        CategoriaPaiRequestDto dto = new CategoriaPaiRequestDto();
        dto.setDescricao("Eletrônicos");

        when(categoriaRepository.existsById(1)).thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> service.atualizarTotalPai(1, dto));
    }
}

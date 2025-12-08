package school.sptech.crud_proj_v1.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.crud_proj_v1.dto.VendaProduto.VendaProdutoRequestDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaRequestDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;
import school.sptech.crud_proj_v1.entity.Funcionario;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.entity.VendaProduto;
import school.sptech.crud_proj_v1.entity.abstrato.Produto;
import school.sptech.crud_proj_v1.enumeration.FormaDePagamento;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.mapper.VendaMapper;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;
import school.sptech.crud_proj_v1.repository.ProdutoRepository;
import school.sptech.crud_proj_v1.repository.VendaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;
    @Mock
    private FuncionarioRepository funcionarioRepository;
    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private VendaMapper vendaMapper;
    @Mock
    private ComissaoService comissaoService;
    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private VendaService service;

    @Test
    @DisplayName("Cadastrar: deve lançar exceção quando vendedor não existir")
    void cadastrarVendedorNaoEncontrado() {
        VendaRequestDTO dto = new VendaRequestDTO();
        dto.setIdVendedor(1);
        dto.setItensVenda(List.of(new VendaProdutoRequestDTO()));

        when(funcionarioRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNotFoundException.class, () -> service.cadastrar(dto));
    }

    @Test
    @DisplayName("Cadastrar: deve lançar exceção quando não houver itens")
    void cadastrarSemItens() {
        VendaRequestDTO dto = new VendaRequestDTO();
        dto.setIdVendedor(1);
        dto.setItensVenda(List.of()); // lista vazia

        Funcionario funcionario = new Funcionario();
        funcionario.setId(1);

        when(funcionarioRepository.findById(1)).thenReturn(Optional.of(funcionario));
        when(vendaMapper.toEntity(dto)).thenReturn(new Venda());

        assertThrows(IllegalArgumentException.class, () -> service.cadastrar(dto));
    }

    @Test
    @DisplayName("BuscarPorId: deve lançar exceção quando venda não existir")
    void buscarPorIdNaoEncontrada() {
        when(vendaRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntidadeNotFoundException.class, () -> service.buscarPorId(1));
    }

    @Test
    @DisplayName("Deletar: deve lançar exceção quando venda não existir")
    void deletarNaoEncontrada() {
        when(vendaRepository.existsById(1)).thenReturn(false);
        assertThrows(EntidadeNotFoundException.class, () -> service.deletarVendaPorId(1));
        verify(vendaRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("CalcularTotal: deve somar corretamente os valores das vendas")
    void calcularTotalComSucesso() {
        Venda v1 = new Venda();
        v1.setTotalVenda(100.0);
        Venda v2 = new Venda();
        v2.setTotalVenda(200.0);

        when(vendaRepository.findAll()).thenReturn(List.of(v1, v2));

        Double resultado = service.calcularTotal();

        assertEquals(300.0, resultado);
    }

    @Test
    @DisplayName("Atualizar: deve lançar exceção quando venda não existir")
    void atualizarVendaNaoEncontrada() {
        VendaRequestDTO dto = new VendaRequestDTO();
        dto.setIdVendedor(1);
        dto.setItensVenda(List.of(new VendaProdutoRequestDTO()));

        when(vendaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNotFoundException.class, () -> service.atualizarPorId(1, dto));
    }

    @Test
    @DisplayName("Atualizar: deve lançar exceção quando vendedor não existir")
    void atualizarVendedorNaoEncontrado() {
        VendaRequestDTO dto = new VendaRequestDTO();
        dto.setIdVendedor(1);
        dto.setItensVenda(List.of(new VendaProdutoRequestDTO()));

        Venda venda = new Venda();
        when(vendaRepository.findById(1)).thenReturn(Optional.of(venda));
        when(funcionarioRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntidadeNotFoundException.class, () -> service.atualizarPorId(1, dto));
    }
}

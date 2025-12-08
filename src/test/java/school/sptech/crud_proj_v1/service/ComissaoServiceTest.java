package school.sptech.crud_proj_v1.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.crud_proj_v1.entity.Comissao;
import school.sptech.crud_proj_v1.entity.Funcionario;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.repository.ComissaoRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComissaoServiceTest {

    @Mock
    private ComissaoRepository repository;

    @InjectMocks
    private ComissaoService service;

    @Test
    @DisplayName("CalcularComissao: deve criar nova comissão quando não existir")
    void calcularComissaoDeveCriarNova() {
        // Arrange
        Funcionario funcionario = new Funcionario();
        funcionario.setComissao(0.1); // 10%

        Venda venda = new Venda();
        venda.setId(1);
        venda.setFuncionario(funcionario);
        venda.setTotalVenda(100.0);
        venda.setDataHora(LocalDateTime.now());

        when(repository.findByVendaId(1)).thenReturn(Optional.empty());

        Comissao comissaoSalva = new Comissao();
        comissaoSalva.setValorComissao(10.0);
        when(repository.save(any(Comissao.class))).thenReturn(comissaoSalva);

        // Act
        Comissao resultado = service.calcularComissao(venda);

        // Assert
        assertNotNull(resultado);
        assertEquals(10.0, resultado.getValorComissao());
        verify(repository, times(1)).save(any(Comissao.class));
    }

    @Test
    @DisplayName("CalcularComissao: deve atualizar comissão existente")
    void calcularComissaoDeveAtualizarExistente() {
        // Arrange
        Funcionario funcionario = new Funcionario();
        funcionario.setComissao(0.2); // 20%

        Venda venda = new Venda();
        venda.setId(1);
        venda.setFuncionario(funcionario);
        venda.setTotalVenda(200.0);
        venda.setDataHora(LocalDateTime.now());

        Comissao comissaoExistente = new Comissao();
        comissaoExistente.setValorComissao(30.0);

        when(repository.findByVendaId(1)).thenReturn(Optional.of(comissaoExistente));
        when(repository.save(comissaoExistente)).thenReturn(comissaoExistente);

        // Act
        Comissao resultado = service.calcularComissao(venda);

        // Assert
        assertNotNull(resultado);
        assertEquals(40.0, resultado.getValorComissao()); // 200 * 0.2 = 40
        verify(repository, times(1)).save(comissaoExistente);
    }

    @Test
    @DisplayName("Arredondar: deve retornar 0.0 quando valor for null")
    void arredondarDeveRetornarZeroQuandoNull() throws Exception {
        // Usando reflexão para acessar método privado
        var method = ComissaoService.class.getDeclaredMethod("arredondar", Double.class);
        method.setAccessible(true);

        Double resultado = (Double) method.invoke(service, (Double) null);

        assertEquals(0.0, resultado);
    }

    @Test
    @DisplayName("Arredondar: deve arredondar para 2 casas decimais")
    void arredondarDeveArredondarCorretamente() throws Exception {
        var method = ComissaoService.class.getDeclaredMethod("arredondar", Double.class);
        method.setAccessible(true);

        Double resultado = (Double) method.invoke(service, 10.567);

        assertEquals(10.57, resultado);
    }
}

package school.sptech.crud_proj_v1.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crud_proj_v1.config.GerenciadorTokenJwt;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioRequestDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioResponseDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioTokenDto;
import school.sptech.crud_proj_v1.entity.Funcionario;
import school.sptech.crud_proj_v1.entity.abstrato.Produto;
import school.sptech.crud_proj_v1.event.ProdutoCadastradoEvent;
import school.sptech.crud_proj_v1.exception.EntidadeConflitoException;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.mapper.FuncionarioMapper;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @Mock
    private FuncionarioRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private FuncionarioService service;

    @Test
    @DisplayName("Listar: deve retornar lista vazia")
    void listarDeveRetornarListaVazia() {
        when(repository.findAll()).thenReturn(List.of());
        List<FuncionarioResponseDto> resultado = service.listar();
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Cadastrar: deve salvar funcionário com sucesso quando CPF não existir")
    void cadastrarDeveSalvarComSucesso() {
        FuncionarioRequestDto dto = new FuncionarioRequestDto();
        dto.setCpf("123");
        dto.setSenha("senha");

        Funcionario funcionario = FuncionarioMapper.toEntity(dto);
        funcionario.setId(1);

        when(repository.existsByCpf("123")).thenReturn(false);
        when(passwordEncoder.encode("senha")).thenReturn("senhaCriptografada");
        when(repository.save(any(Funcionario.class))).thenReturn(funcionario);

        FuncionarioResponseDto resultado = service.cadastrarFuncionario(dto);

        assertNotNull(resultado);
        assertEquals("123", resultado.getCpf());
        verify(repository, times(1)).save(any(Funcionario.class));
    }

    @Test
    @DisplayName("Cadastrar: deve lançar exceção quando CPF já existir")
    void cadastrarDeveLancarConflito() {
        FuncionarioRequestDto dto = new FuncionarioRequestDto();
        dto.setCpf("123");

        when(repository.existsByCpf("123")).thenReturn(true);

        assertThrows(EntidadeConflitoException.class, () -> service.cadastrarFuncionario(dto));
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Autenticar: deve retornar token quando credenciais válidas")
    void autenticarDeveRetornarToken() {
        Funcionario funcionario = new Funcionario();
        funcionario.setEmail("email@test.com");
        funcionario.setSenha("senha");

        Authentication authMock = mock(Authentication.class);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authMock);
        when(repository.findByEmail("email@test.com")).thenReturn(Optional.of(funcionario));
        when(gerenciadorTokenJwt.generateToken(authMock)).thenReturn("token123");

        FuncionarioTokenDto resultado = service.autenticar(funcionario);

        assertEquals("token123", resultado.getToken());
    }

    @Test
    @DisplayName("Autenticar: deve lançar exceção quando email não existir")
    void autenticarDeveFalharEmailInexistente() {
        Funcionario funcionario = new Funcionario();
        funcionario.setEmail("email@test.com");
        funcionario.setSenha("senha");

        Authentication authMock = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authMock);
        when(repository.findByEmail("email@test.com")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.autenticar(funcionario));
    }

    @Test
    @DisplayName("BuscarPorId: deve retornar funcionário quando id existir")
    void buscarPorIdComSucesso() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1);

        when(repository.findById(1)).thenReturn(Optional.of(funcionario));

        FuncionarioResponseDto resultado = service.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    @DisplayName("BuscarPorId: deve lançar exceção quando id não existir")
    void buscarPorIdFalha() {
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntidadeNotFoundException.class, () -> service.buscarPorId(1));
    }

    @Test
    @DisplayName("Atualizar: deve atualizar funcionário quando id existir")
    void atualizarComSucesso() {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1);

        FuncionarioRequestDto dto = new FuncionarioRequestDto();
        dto.setNome("Novo Nome");

        when(repository.findById(1)).thenReturn(Optional.of(funcionario));
        when(repository.save(funcionario)).thenReturn(funcionario);

        FuncionarioResponseDto resultado = service.atualizarPorId(1, dto);

        assertNotNull(resultado);
        verify(repository, times(1)).save(funcionario);
    }

    @Test
    @DisplayName("Atualizar: deve lançar exceção quando id não existir")
    void atualizarFalha() {
        FuncionarioRequestDto dto = new FuncionarioRequestDto();
        when(repository.findById(1)).thenReturn(Optional.empty());
        assertThrows(EntidadeNotFoundException.class, () -> service.atualizarPorId(1, dto));
    }

    @Test
    @DisplayName("Deletar: deve deletar quando id existir")
    void deletarComSucesso() {
        when(repository.existsById(1)).thenReturn(true);
        service.deletarPorId(1);
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Deletar: deve lançar exceção quando id não existir")
    void deletarFalha() {
        when(repository.existsById(1)).thenReturn(false);
        assertThrows(EntidadeNotFoundException.class, () -> service.deletarPorId(1));
        verify(repository, never()).deleteById(any());
    }

    @Test
    @DisplayName("HandleProdutoCadastrado: deve chamar enviarNotificacao para todos funcionários")
    void handleProdutoCadastradoDeveNotificar() {
        Produto produto = mock(Produto.class);
        when(produto.getId()).thenReturn(99);

        Funcionario f1 = new Funcionario();
        f1.setEmail("a@test.com");
        Funcionario f2 = new Funcionario();
        f2.setEmail("b@test.com");

        when(repository.findAll()).thenReturn(List.of(f1, f2));

        ProdutoCadastradoEvent event = new ProdutoCadastradoEvent(produto);
        service.handleProdutoCadastrado(event);

        verify(repository, times(1)).findAll();
    }
}

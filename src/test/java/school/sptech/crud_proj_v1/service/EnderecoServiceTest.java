package school.sptech.crud_proj_v1.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crud_proj_v1.dto.Endereco.EnderecoRequestDto;
import school.sptech.crud_proj_v1.dto.Endereco.EnderecoResponseDto;
import school.sptech.crud_proj_v1.entity.Endereco;
import school.sptech.crud_proj_v1.mapper.EnderecoMapper;
import school.sptech.crud_proj_v1.repository.EmpresaRepository;
import school.sptech.crud_proj_v1.repository.EnderecoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private EnderecoMapper enderecoMapper;
    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private EnderecoService service;

    @Test
    @DisplayName("Cadastrar: deve salvar endereço com sucesso")
    void cadastrarComSucesso() {
        EnderecoRequestDto dto = new EnderecoRequestDto();
        dto.setCep("12345-678");
        dto.setEstado("SP");

        Endereco endereco = new Endereco();
        endereco.setId(1);

        when(enderecoMapper.toEntity(dto)).thenReturn(endereco);
        when(enderecoRepository.save(endereco)).thenReturn(endereco);
        when(enderecoMapper.toResponseDto(endereco)).thenReturn(new EnderecoResponseDto());

        EnderecoResponseDto resultado = service.cadastrar(dto);

        assertNotNull(resultado);
        verify(enderecoRepository, times(1)).save(endereco);
    }

    @Test
    @DisplayName("ListarTodos: deve retornar lista vazia quando não houver endereços")
    void listarTodosVazio() {
        when(enderecoRepository.findAll()).thenReturn(List.of());
        List<EnderecoResponseDto> resultado = service.listarTodos();
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("ListarPorId: deve retornar endereço quando id existir")
    void listarPorIdComSucesso() {
        Endereco endereco = new Endereco();
        endereco.setId(1);

        when(enderecoRepository.findById(1)).thenReturn(Optional.of(endereco));
        when(enderecoMapper.toResponseDto(endereco)).thenReturn(new EnderecoResponseDto());

        EnderecoResponseDto resultado = service.listarPorId(1);

        assertNotNull(resultado);
    }

    @Test
    @DisplayName("ListarPorId: deve lançar exceção quando id não existir")
    void listarPorIdNaoEncontrado() {
        when(enderecoRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.listarPorId(1));
    }

    @Test
    @DisplayName("AtualizarTotal: deve salvar endereço quando id existir")
    void atualizarTotalComSucesso() {
        EnderecoRequestDto dto = new EnderecoRequestDto();
        dto.setCep("12345-678");

        Endereco endereco = new Endereco();
        endereco.setId(1);

        when(enderecoRepository.existsById(1)).thenReturn(true);
        when(enderecoMapper.toEntity(dto)).thenReturn(endereco);
        when(enderecoRepository.save(endereco)).thenReturn(endereco);
        when(enderecoMapper.toResponseDto(endereco)).thenReturn(new EnderecoResponseDto());

        EnderecoResponseDto resultado = service.atualizarTotal(1, dto);

        assertNotNull(resultado);
        verify(enderecoRepository, times(1)).save(endereco);
    }

    @Test
    @DisplayName("AtualizarTotal: deve lançar exceção quando id não existir")
    void atualizarTotalNaoEncontrado() {
        EnderecoRequestDto dto = new EnderecoRequestDto();
        when(enderecoRepository.existsById(1)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> service.atualizarTotal(1, dto));
    }

    @Test
    @DisplayName("AtualizarParcial: deve atualizar apenas campos informados")
    void atualizarParcialComSucesso() {
        EnderecoRequestDto dto = new EnderecoRequestDto();
        dto.setCidade("São Paulo");

        Endereco endereco = new Endereco();
        endereco.setId(1);
        endereco.setCidade("Antiga");

        when(enderecoRepository.findById(1)).thenReturn(Optional.of(endereco));
        when(enderecoRepository.save(endereco)).thenReturn(endereco);
        when(enderecoMapper.toResponseDto(endereco)).thenReturn(new EnderecoResponseDto());

        EnderecoResponseDto resultado = service.atualizarParcial(1, dto);

        assertNotNull(resultado);
        assertEquals("São Paulo", endereco.getCidade());
        verify(enderecoRepository, times(1)).save(endereco);
    }

    @Test
    @DisplayName("AtualizarParcial: deve lançar exceção quando id não existir")
    void atualizarParcialNaoEncontrado() {
        EnderecoRequestDto dto = new EnderecoRequestDto();
        when(enderecoRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.atualizarParcial(1, dto));
    }

    @Test
    @DisplayName("Deletar: deve deletar endereço quando id existir e não associado a empresa")
    void deletarComSucesso() {
        when(enderecoRepository.existsById(1)).thenReturn(true);
        when(empresaRepository.existsByEnderecoId(1)).thenReturn(false);

        service.deletarPorId(1);

        verify(enderecoRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Deletar: deve lançar exceção quando id não existir")
    void deletarNaoEncontrado() {
        when(enderecoRepository.existsById(1)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> service.deletarPorId(1));
        verify(enderecoRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("Deletar: deve lançar exceção quando endereço estiver associado a empresa")
    void deletarAssociadoEmpresa() {
        when(enderecoRepository.existsById(1)).thenReturn(true);
        when(empresaRepository.existsByEnderecoId(1)).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> service.deletarPorId(1));
        verify(enderecoRepository, never()).deleteById(any());
    }
}

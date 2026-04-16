package school.sptech.crud_proj_v1.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crud_proj_v1.dto.Empresa.EmpresaRequestDto;
import school.sptech.crud_proj_v1.dto.Empresa.EmpresaResponseDto;
import school.sptech.crud_proj_v1.entity.Empresa;
import school.sptech.crud_proj_v1.entity.Endereco;
import school.sptech.crud_proj_v1.mapper.EmpresaMapper;
import school.sptech.crud_proj_v1.repository.EmpresaRepository;
import school.sptech.crud_proj_v1.repository.EnderecoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;
    @Mock
    private EnderecoRepository enderecoRepository;
    @Mock
    private EmpresaMapper empresaMapper;

    @InjectMocks
    private EmpresaService service;

    @Test
    @DisplayName("Cadastrar: deve salvar empresa com sucesso quando endereço existir")
    void cadastrarComSucesso() {
        EmpresaRequestDto dto = new EmpresaRequestDto();
        dto.setRazaoSocial("Tech Ltda");
        dto.setCnpj("123456789");
        dto.setResponsavel("Kaio");
        dto.setFkEndereco(1);

        Endereco endereco = new Endereco();
        endereco.setId(1);

        Empresa empresa = new Empresa();
        empresa.setRazaoSocial("Tech Ltda");
        empresa.setEndereco(endereco);

        when(enderecoRepository.findById(1)).thenReturn(Optional.of(endereco));
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa);
        when(empresaMapper.toResponseDto(empresa)).thenReturn(new EmpresaResponseDto());

        EmpresaResponseDto resultado = service.cadastrar(dto);

        assertNotNull(resultado);
        verify(empresaRepository, times(1)).save(any(Empresa.class));
    }

    @Test
    @DisplayName("Cadastrar: deve lançar exceção quando endereço não existir")
    void cadastrarEnderecoNaoEncontrado() {
        EmpresaRequestDto dto = new EmpresaRequestDto();
        dto.setFkEndereco(99);

        when(enderecoRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.cadastrar(dto));
        verify(empresaRepository, never()).save(any());
    }

    @Test
    @DisplayName("ListarTodos: deve retornar lista vazia quando não houver empresas")
    void listarTodosVazio() {
        when(empresaRepository.findAll()).thenReturn(List.of());
        List<EmpresaResponseDto> resultado = service.listarTodos();
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("ListarPorId: deve retornar empresa quando id existir")
    void listarPorIdComSucesso() {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(1);

        when(empresaRepository.findById(1)).thenReturn(Optional.of(empresa));
        when(empresaMapper.toResponseDto(empresa)).thenReturn(new EmpresaResponseDto());

        EmpresaResponseDto resultado = service.listarPorId(1);

        assertNotNull(resultado);
    }

    @Test
    @DisplayName("ListarPorId: deve lançar exceção quando id não existir")
    void listarPorIdNaoEncontrado() {
        when(empresaRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.listarPorId(1));
    }

    @Test
    @DisplayName("Atualizar: deve salvar empresa quando id existir")
    void atualizarComSucesso() {
        EmpresaRequestDto dto = new EmpresaRequestDto();
        dto.setFkEndereco(1);

        Endereco endereco = new Endereco();
        endereco.setId(1);

        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(1);

        when(empresaRepository.existsById(1)).thenReturn(true);
        when(enderecoRepository.findById(1)).thenReturn(Optional.of(endereco));
        when(empresaRepository.save(any(Empresa.class))).thenReturn(empresa);
        when(empresaMapper.toResponseDto(empresa)).thenReturn(new EmpresaResponseDto());

        EmpresaResponseDto resultado = service.atualizar(1, dto);

        assertNotNull(resultado);
        verify(empresaRepository, times(1)).save(any(Empresa.class));
    }

    @Test
    @DisplayName("Atualizar: deve lançar exceção quando id não existir")
    void atualizarNaoEncontrado() {
        EmpresaRequestDto dto = new EmpresaRequestDto();
        when(empresaRepository.existsById(1)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> service.atualizar(1, dto));
    }

    @Test
    @DisplayName("Deletar: deve deletar empresa quando id existir")
    void deletarComSucesso() {
        when(empresaRepository.existsById(1)).thenReturn(true);
        service.deletarPorId(1);
        verify(empresaRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Deletar: deve lançar exceção quando id não existir")
    void deletarNaoEncontrado() {
        when(empresaRepository.existsById(1)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> service.deletarPorId(1));
        verify(empresaRepository, never()).deleteById(any());
    }
}

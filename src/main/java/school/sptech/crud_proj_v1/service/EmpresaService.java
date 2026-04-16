package school.sptech.crud_proj_v1.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crud_proj_v1.dto.Empresa.EmpresaRequestDto;
import school.sptech.crud_proj_v1.dto.Empresa.EmpresaResponseDto;
import school.sptech.crud_proj_v1.entity.Empresa;
import school.sptech.crud_proj_v1.entity.Endereco;
import school.sptech.crud_proj_v1.mapper.EmpresaMapper;
import school.sptech.crud_proj_v1.repository.EmpresaRepository;
import school.sptech.crud_proj_v1.repository.EnderecoRepository;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EnderecoRepository enderecoRepository;
    private final EmpresaMapper empresaMapper;

    public EmpresaService(EmpresaRepository empresaRepository, EnderecoRepository enderecoRepository, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.enderecoRepository = enderecoRepository;
        this.empresaMapper = empresaMapper;
    }

    private Empresa toEntity(EmpresaRequestDto dto) {
        Endereco endereco = enderecoRepository.findById(dto.getFkEndereco())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço com o ID fornecido não encontrado."));

        Empresa empresa = new Empresa();
        empresa.setRazaoSocial(dto.getRazaoSocial());
        empresa.setCnpj(dto.getCnpj());
        empresa.setResponsavel(dto.getResponsavel());
        empresa.setEndereco(endereco);

        return empresa;
    }

    public EmpresaResponseDto cadastrar(EmpresaRequestDto dto) {
        Empresa empresa = toEntity(dto);
        Empresa empresaSalva = empresaRepository.save(empresa);
        return empresaMapper.toResponseDto(empresaSalva);
    }

    public List<EmpresaResponseDto> listarTodos() {
        return empresaRepository.findAll().stream()
                .map(empresaMapper::toResponseDto)
                .toList();
    }

    public EmpresaResponseDto listarPorId(Integer id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada."));
        return empresaMapper.toResponseDto(empresa);
    }

    public EmpresaResponseDto atualizar(Integer id, EmpresaRequestDto dto) {
        if (!empresaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada.");
        }
        Empresa empresaAtualizada = toEntity(dto);
        empresaAtualizada.setIdEmpresa(id);

        Empresa empresaSalva = empresaRepository.save(empresaAtualizada);
        return empresaMapper.toResponseDto(empresaSalva);
    }

    public void deletarPorId(Integer id) {
        if (!empresaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada.");
        }
        empresaRepository.deleteById(id);
    }
}
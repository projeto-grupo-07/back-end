package school.sptech.crud_proj_v1.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crud_proj_v1.dto.Endereco.EnderecoRequestDto;
import school.sptech.crud_proj_v1.dto.Endereco.EnderecoResponseDto;
import school.sptech.crud_proj_v1.entity.Endereco;
import school.sptech.crud_proj_v1.mapper.EnderecoMapper;
import school.sptech.crud_proj_v1.repository.EmpresaRepository;
import school.sptech.crud_proj_v1.repository.EnderecoRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;
    private final EmpresaRepository empresaRepository;

    public EnderecoService(EnderecoRepository enderecoRepository,
                           EnderecoMapper enderecoMapper,
                           EmpresaRepository empresaRepository) {

        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
        this.empresaRepository = empresaRepository;
    }


    public EnderecoResponseDto cadastrar(EnderecoRequestDto dto) {
        Endereco entidade = enderecoMapper.toEntity(dto);
        Endereco salvo = enderecoRepository.save(entidade);
        return enderecoMapper.toResponseDto(salvo);
    }

    public List<EnderecoResponseDto> listarTodos() {
        return enderecoRepository.findAll()
                .stream()
                .map(enderecoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public EnderecoResponseDto listarPorId(Integer id) {
        Endereco entidade = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereco não encontrado"));
        return enderecoMapper.toResponseDto(entidade);
    }

    public EnderecoResponseDto atualizarTotal(Integer id, EnderecoRequestDto dto) {
        if (!enderecoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereco não encontrado");
        }
        Endereco entidadeAtualizada = enderecoMapper.toEntity(dto);
        entidadeAtualizada.setId(id);
        Endereco salvo = enderecoRepository.save(entidadeAtualizada);
        return enderecoMapper.toResponseDto(salvo);
    }

    public EnderecoResponseDto atualizarParcial(Integer id, EnderecoRequestDto dto) {
        Endereco entidadeExistente = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereco não encontrado"));

        if (dto.getCep() != null) entidadeExistente.setCep(dto.getCep());
        if (dto.getEstado() != null) entidadeExistente.setEstado(dto.getEstado());
        if (dto.getCidade() != null) entidadeExistente.setCidade(dto.getCidade());
        if (dto.getBairro() != null) entidadeExistente.setBairro(dto.getBairro());
        if (dto.getLogradouro() != null) entidadeExistente.setLogradouro(dto.getLogradouro());
        if (dto.getNumero() != null) entidadeExistente.setNumero(dto.getNumero());
        if (dto.getComplemento() != null) entidadeExistente.setComplemento(dto.getComplemento());

        Endereco salvo = enderecoRepository.save(entidadeExistente);
        return enderecoMapper.toResponseDto(salvo);
    }

    public void deletarPorId(Integer id) {
        if (!enderecoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }

        if (empresaRepository.existsByEnderecoId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Endereço não pode ser deletado, pois está associado a uma empresa.");
        }

        enderecoRepository.deleteById(id);
    }
}

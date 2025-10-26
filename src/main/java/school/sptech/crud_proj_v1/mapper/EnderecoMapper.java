package school.sptech.crud_proj_v1.mapper;

import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Endereco.EnderecoRequestDto;
import school.sptech.crud_proj_v1.dto.Endereco.EnderecoResponseDto;
import school.sptech.crud_proj_v1.entity.Endereco;

@Component
public class EnderecoMapper {
    public Endereco toEntity(EnderecoRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Endereco entidade = new Endereco();

        entidade.setCep(dto.getCep());
        entidade.setEstado(dto.getEstado());
        entidade.setCidade(dto.getCidade());
        entidade.setBairro(dto.getBairro());
        entidade.setLogradouro(dto.getLogradouro());
        entidade.setNumero(dto.getNumero());
        entidade.setComplemento(dto.getComplemento());

        return entidade;
    }


    public EnderecoResponseDto toResponseDto(Endereco entidade) {
        if (entidade == null) {
            return null;
        }

        EnderecoResponseDto dto = new EnderecoResponseDto();

        dto.setId(entidade.getId());
        dto.setCep(entidade.getCep());
        dto.setEstado(entidade.getEstado());
        dto.setCidade(entidade.getCidade());
        dto.setBairro(entidade.getBairro());
        dto.setLogradouro(entidade.getLogradouro());
        dto.setNumero(entidade.getNumero());
        dto.setComplemento(entidade.getComplemento());

        return dto;
    }
}

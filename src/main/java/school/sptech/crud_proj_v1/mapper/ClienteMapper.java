package school.sptech.crud_proj_v1.mapper;

import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Cliente.ClienteRequestDto;
import school.sptech.crud_proj_v1.dto.Cliente.ClienteResponseDto;
import school.sptech.crud_proj_v1.dto.Endereco.EnderecoResponseDto;
import school.sptech.crud_proj_v1.entity.Cliente;
import school.sptech.crud_proj_v1.entity.Endereco;

@Component
public class ClienteMapper {

    public static Cliente toEntity(ClienteRequestDto dto){
        if (dto == null) return null;

        Cliente cliente = new Cliente();

        cliente.setNome(dto.getNome());
        cliente.setDtNasc(dto.getDtNasc());
        cliente.setEmail(dto.getEmail());
        cliente.setGenero(dto.getGenero());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCpf(dto.getCpf());

        return cliente;
    }

    public static ClienteResponseDto of(Cliente cliente){
        if (cliente == null) return null;

        ClienteResponseDto dto = new ClienteResponseDto();

        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setDtNasc(cliente.getDtNasc());
        dto.setEmail(cliente.getEmail());
        dto.setGenero(cliente.getGenero());
        dto.setTelefone(cliente.getTelefone());
        dto.setCpf(cliente.getCpf());
        dto.setDtCadastro(cliente.getDtCadastro());

        if(cliente.getEndereco() != null){
            Endereco endereco = cliente.getEndereco();

            EnderecoResponseDto enderecoDto = new EnderecoResponseDto();

            enderecoDto.setId(endereco.getId());
            enderecoDto.setCep(endereco.getCep());
            enderecoDto.setEstado(endereco.getEstado());
            enderecoDto.setCidade(endereco.getCidade());
            enderecoDto.setBairro(endereco.getBairro());
            enderecoDto.setLogradouro(endereco.getLogradouro());
            enderecoDto.setNumero(endereco.getNumero());
            enderecoDto.setComplemento(endereco.getComplemento());

            dto.setCep(enderecoDto.getCep());
            dto.setEstado(enderecoDto.getEstado());
            dto.setCidade(enderecoDto.getCidade());
            dto.setBairro(enderecoDto.getBairro());
            dto.setLogradouro(enderecoDto.getLogradouro());
            dto.setNumero(enderecoDto.getNumero());
            dto.setComplemento(enderecoDto.getComplemento());
        }

        return dto;
    }
}

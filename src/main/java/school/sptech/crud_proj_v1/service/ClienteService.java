package school.sptech.crud_proj_v1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.dto.Cliente.ClienteRequestDto;
import school.sptech.crud_proj_v1.dto.Cliente.ClienteResponseDto;
import school.sptech.crud_proj_v1.entity.Cliente;
import school.sptech.crud_proj_v1.entity.Endereco;
import school.sptech.crud_proj_v1.exception.EntidadeConflitoException;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.mapper.ClienteMapper;
import school.sptech.crud_proj_v1.repository.ClienteRepository;
import school.sptech.crud_proj_v1.repository.EnderecoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;

    public List<ClienteResponseDto> listar(){
        return clienteRepository.findAll()
                .stream()
                .map(ClienteMapper::of)
                .toList();
    }

    public ClienteResponseDto buscarPorId(Integer id){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Cliente com id não encontrado: " + id));
        return ClienteMapper.of(cliente);
    }

    public ClienteResponseDto cadastrar(ClienteRequestDto dto){
        if (clienteRepository.existsByCpf(dto.getCpf())){
            throw new EntidadeConflitoException("Já existe um cliente com este CPF cadastrado: " + dto.getCpf());
        }

        if(clienteRepository.existsByEmail(dto.getEmail())){
            throw new EntidadeConflitoException("Já existe um cliente com este email cadastrado: " + dto.getEmail());
        }

        // ao cadastrar o cliente, checa se o endereço dele existe, e se não existir, ele da um exception de entidade not found
        Endereco endereco = enderecoRepository.findById(dto.getIdEndereco())
                .orElseThrow(() -> new EntidadeNotFoundException("Endereço não encontrado: " + dto.getIdEndereco()));

        Cliente cliente = ClienteMapper.toEntity(dto);
        cliente.setEndereco(endereco);
        cliente.setDtCadastro(LocalDate.now());

        return ClienteMapper.of(clienteRepository.save(cliente));
    }

    public ClienteResponseDto atualizarPorId(Integer id, ClienteRequestDto dto){
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Cliente com id " + id + " não encontrado."));

        cliente.setNome(dto.getNome());
        cliente.setDtNasc(dto.getDtNasc());
        cliente.setEmail(dto.getEmail());
        cliente.setGenero(dto.getGenero());
        cliente.setTelefone(dto.getTelefone());
        cliente.setCpf(dto.getCpf());

        if(dto.getIdEndereco() != null){
            Endereco endereco = enderecoRepository.findById(dto.getIdEndereco())
                    .orElseThrow(() -> new EntidadeNotFoundException("Endereço não encontrado: " + dto.getIdEndereco()));

            cliente.setEndereco(endereco);
        }

        return ClienteMapper.of(clienteRepository.save(cliente));
    }

    public void deletarPorId(Integer id){
        if(!clienteRepository.existsById(id)){
            throw new EntidadeNotFoundException("Cliente com id " + id + "não encontrado.");
        }
        clienteRepository.deleteById(id);
    }

    public List<ClienteResponseDto> buscarPorNome(String nome){
        return clienteRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(ClienteMapper::of)
                .toList();
    }
}

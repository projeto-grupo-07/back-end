package school.sptech.crud_proj_v1.service;

import jakarta.transaction.Transactional;
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

    @Transactional
    public ClienteResponseDto cadastrar(ClienteRequestDto dto){
        if (clienteRepository.existsByCpf(dto.getCpf())){
            throw new EntidadeConflitoException("Já existe um cliente com este CPF cadastrado: " + dto.getCpf());
        }

        if(clienteRepository.existsByEmail(dto.getEmail())){
            throw new EntidadeConflitoException("Já existe um cliente com este email cadastrado: " + dto.getEmail());
        }

        Endereco endereco = new Endereco();
        endereco.setCep(dto.getCep());
        endereco.setEstado(dto.getEstado());
        endereco.setCidade(dto.getCidade());
        endereco.setBairro(dto.getBairro());
        endereco.setLogradouro(dto.getLogradouro());
        endereco.setNumero(dto.getNumero());
        endereco.setComplemento(dto.getComplemento());

        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        Cliente cliente = ClienteMapper.toEntity(dto);
        cliente.setEndereco(enderecoSalvo);
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


            Endereco endereco = enderecoRepository.findById(cliente.getEndereco().getId())
                    .orElseThrow(() -> new EntidadeNotFoundException("Endereço não encontrado: " + cliente.getEndereco().getId()));

            cliente.setEndereco(endereco);


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

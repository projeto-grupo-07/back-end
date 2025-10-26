package school.sptech.crud_proj_v1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.crud_proj_v1.config.GerenciadorTokenJwt;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioRequestDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioResponseDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioTokenDto;
import school.sptech.crud_proj_v1.entity.Funcionario;
import school.sptech.crud_proj_v1.entity.Produto;
import school.sptech.crud_proj_v1.event.ProdutoCadastradoEvent;
import school.sptech.crud_proj_v1.exception.EntidadeConflitoException;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.mapper.FuncionarioMapper;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;
    @Autowired
    private AuthenticationManager authenticationManager;

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;

    public FuncionarioService(FuncionarioRepository repository, FuncionarioMapper funcionarioMapper) {
        this.funcionarioRepository = repository;
        this.funcionarioMapper = funcionarioMapper;
    }

    public List<FuncionarioResponseDto> listar(){
        List<Funcionario> funcionariosEncontrados =  funcionarioRepository.findAll();
        return funcionariosEncontrados.stream().map(FuncionarioMapper::of).toList();
    }

    public FuncionarioResponseDto cadastrarFuncionario(FuncionarioRequestDto func){
        if (funcionarioRepository.existsByCpf(func.getCpf())){
            throw new EntidadeConflitoException("Conflito no cpf");
        } else {
            String senhaCriptograda = passwordEncoder.encode(func.getSenha());
            Funcionario funcionarioSalvo = FuncionarioMapper.toEntity(func);
            funcionarioSalvo.setSenha(senhaCriptograda);
            funcionarioRepository.save(funcionarioSalvo);
            return FuncionarioMapper.of(funcionarioSalvo);
        }
    }

    public FuncionarioTokenDto autenticar(Funcionario funcionario) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(funcionario.getEmail(), funcionario.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Funcionario funcionarioAutenticado = funcionarioRepository.findByEmail(funcionario.getEmail())
                .orElseThrow(
                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = gerenciadorTokenJwt.generateToken(authentication);
        return FuncionarioMapper.of(funcionarioAutenticado, token);
    }

    public FuncionarioResponseDto buscarPorId(Integer id){
        Funcionario funcionarioFound = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Funcionário com id não encontrado: " + id));
        return FuncionarioMapper.of(funcionarioFound);
    }

    public FuncionarioResponseDto atualizarPorId(Integer id, FuncionarioRequestDto dto) {

        Funcionario funcionarioParaAtualizar = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Funcionário com id não encontrado: " + id));

        funcionarioParaAtualizar.setNome(dto.getNome());
        funcionarioParaAtualizar.setCpf(dto.getCpf());
        funcionarioParaAtualizar.setEmail(dto.getEmail());
        funcionarioParaAtualizar.setSalario(dto.getSalario());
        funcionarioParaAtualizar.setComissao(dto.getComissao());

        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            funcionarioParaAtualizar.setSenha(passwordEncoder.encode(dto.getSenha()));
        }

        return FuncionarioMapper.of(funcionarioRepository.save(funcionarioParaAtualizar));
    }

    public void deletarPorId(Integer id){
        if (funcionarioRepository.existsById(id)){
            funcionarioRepository.deleteById(id);
            return;
        }
        throw new EntidadeNotFoundException("funcionario nao encontrado/existente");
    }

    public void handleProdutoCadastrado(ProdutoCadastradoEvent event){
        Produto produto = event.getProduto();
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        enviarNotificacao(funcionarios, produto);
    }

    private void enviarNotificacao(List<Funcionario> funcionarios, Produto produto){
        for (Funcionario f : funcionarios) {
            System.out.println("Enviando email para " + f.getEmail() + " sobre adição do produto: " + produto.getModelo());
        }
    }
}

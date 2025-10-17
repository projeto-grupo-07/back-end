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
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioResponseDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioTokenDto;
import school.sptech.crud_proj_v1.entity.Funcionario;
import school.sptech.crud_proj_v1.exception.EntidadeConflitoException;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.mapper.FuncionarioMapper;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FuncionarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;
    @Autowired
    private AuthenticationManager authenticationManager;

    private FuncionarioRepository repository;

    public FuncionarioService(FuncionarioRepository repository) {
        this.repository = repository;
    }

    public List<FuncionarioResponseDto> listar(){
        List<Funcionario> funcionariosEncontrados =  repository.findAll();
        return funcionariosEncontrados.stream().map(FuncionarioMapper::of).toList();
    }

    public Funcionario cadastrar(Funcionario func){
        if (repository.existsByCpf(func.getCpf())){
            throw new EntidadeConflitoException("Conflito no cpf");
        } else {
            String senhaCriptograda = passwordEncoder.encode(func.getSenha());
            func.setSenha(senhaCriptograda);
            repository.save(func);
            return func;
        }
    }

    public FuncionarioTokenDto autenticar(Funcionario funcionario) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(funcionario.getEmail(), funcionario.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Funcionario funcionarioAutenticado = repository.findByEmail(funcionario.getEmail())
                .orElseThrow(
                () -> new ResponseStatusException(404, "Email do usuário não cadastrado", null)
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = gerenciadorTokenJwt.generateToken(authentication);
        return FuncionarioMapper.of(funcionarioAutenticado, token);
    }

    public Funcionario buscarPorId(Integer id){
        Optional<Funcionario> opt = repository.findById(id);
        if (opt.isEmpty()){
            throw new EntidadeNotFoundException("Não encontrado");
        } else {
            return opt.get();
        }
    }

    public Funcionario atualizarPorId(Integer id, Funcionario func){
        func.setId(id);
        if (repository.existsById(id)){
            return repository.save(func);
        }
        throw new EntidadeNotFoundException("funcionario nao encontrado/existente");
    }

    public void deletarPorId(Integer id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return;
        }
        throw new EntidadeNotFoundException("funcionario nao encontrado/existente");
    }


}

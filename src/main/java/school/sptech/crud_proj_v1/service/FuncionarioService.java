package school.sptech.crud_proj_v1.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import school.sptech.crud_proj_v1.entity.Perfil;
import school.sptech.crud_proj_v1.entity.abstrato.Produto;
import school.sptech.crud_proj_v1.event.ProdutoCadastradoEvent;
import school.sptech.crud_proj_v1.exception.EntidadeConflitoException;
import school.sptech.crud_proj_v1.exception.EntidadeNotFoundException;
import school.sptech.crud_proj_v1.mapper.FuncionarioMapper;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;
import school.sptech.crud_proj_v1.repository.PerfilRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionarioService {


    private final PasswordEncoder passwordEncoder;

    private final GerenciadorTokenJwt gerenciadorTokenJwt;

    private final AuthenticationManager authenticationManager;

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;
    private final PerfilRepository perfilRepository;


    public List<FuncionarioResponseDto> listar(){
        List<Funcionario> funcionariosEncontrados =  funcionarioRepository.findAllByAtivoTrue();
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

    // No seu FuncionarioService.java

    public FuncionarioResponseDto atualizarPorId(Integer id, FuncionarioRequestDto dto) {
        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Funcionário não encontrado"));

        // Busca o perfil enviado no DTO
        Perfil perfil = perfilRepository.findById(dto.getIdPerfil())
                .orElseThrow(() -> new EntidadeNotFoundException("Perfil não encontrado"));

        // Atualiza os campos
        funcionarioExistente.setNome(dto.getNome());
        funcionarioExistente.setEmail(dto.getEmail());
        funcionarioExistente.setCpf(dto.getCpf()); // Agora o CPF virá no body do front
        funcionarioExistente.setSalario(dto.getSalario());
        funcionarioExistente.setComissao(dto.getComissao());
        funcionarioExistente.setPerfil(perfil); // Seta o novo perfil

        return FuncionarioMapper.of(funcionarioRepository.save(funcionarioExistente));
    }

    @Transactional
    public void deletarPorId(Integer id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Funcionário não encontrado/existente"));

        funcionario.setAtivo(false);

        funcionarioRepository.save(funcionario);
    }

    public void handleProdutoCadastrado(ProdutoCadastradoEvent event){
        Produto produto = event.getProduto();
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        enviarNotificacao(funcionarios, produto);
    }

    private void enviarNotificacao(List<Funcionario> funcionarios, Produto produto){
        for (Funcionario f : funcionarios) {
            System.out.println("Enviando email para " + f.getEmail() + " sobre adição do produto: " + produto.getId());
        }
    }
}

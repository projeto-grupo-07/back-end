package school.sptech.crud_proj_v1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
import school.sptech.crud_proj_v1.entity.TentativaLogin;
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
    private final PerfilRepository perfilRepository;
    private final FuncionarioMapper funcionarioMapper;
    private final TentativaLoginService tentativaLoginService;


    public List<FuncionarioResponseDto> listar(){
        List<Funcionario> funcionariosEncontrados = funcionarioRepository.findAllByAtivoTrue();
        return funcionariosEncontrados.stream().map(FuncionarioMapper::of).toList();
    }

    public FuncionarioResponseDto cadastrarFuncionario(FuncionarioRequestDto func) {
        if (funcionarioRepository.existsByCpf(func.getCpf())) {
            throw new EntidadeConflitoException("Já existe um funcionário com este CPF.");
        }
        if (funcionarioRepository.existsByEmail(func.getEmail())) {
            throw new EntidadeConflitoException("Já existe um funcionário com este e-mail.");
        }

        String senhaCriptografada = passwordEncoder.encode(func.getSenha());
        Funcionario funcionarioSalvo = FuncionarioMapper.toEntity(func);
        funcionarioSalvo.setSenha(senhaCriptografada);
        Perfil perfil = perfilRepository.findById(func.getIdPerfil())
                .orElseThrow(() -> new EntidadeNotFoundException("Perfil não encontrado"));
        funcionarioSalvo.setPerfil(perfil);
        funcionarioRepository.save(funcionarioSalvo);
        return FuncionarioMapper.of(funcionarioSalvo);
    }

    public FuncionarioTokenDto autenticar(Funcionario funcionario) {
        TentativaLogin tentativa = tentativaLoginService.getByEmail(funcionario.getEmail());

        if (tentativa.isBloqueado()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Usuário temporariamente bloqueado até " + tentativa.getDesbloqueio());
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(funcionario.getEmail(), funcionario.getSenha())
            );
            // Resetar tentativas em caso de sucesso
            tentativaLoginService.resetar(funcionario.getEmail());

            Funcionario funcionarioAutenticado = funcionarioRepository.findByEmail(funcionario.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email não cadastrado"));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = gerenciadorTokenJwt.generateToken(authentication);

            return FuncionarioMapper.of(funcionarioAutenticado, token);

        } catch (BadCredentialsException e) {
            // Registrar falha
            tentativaLoginService.registrarFalha(funcionario.getEmail());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }
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

    public void deletarPorId(Integer id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNotFoundException("Funcionário não encontrado: " + id));

        funcionario.setAtivo(false);
        funcionarioRepository.save(funcionario);
    }

    public void handleProdutoCadastrado(ProdutoCadastradoEvent event){
        Produto produto = event.getProduto();
        List<Funcionario> funcionarios = funcionarioRepository.findAllByAtivoTrue();
        enviarNotificacao(funcionarios, produto);
    }

    private void enviarNotificacao(List<Funcionario> funcionarios, Produto produto){
        for (Funcionario f : funcionarios) {
            System.out.println("Enviando email para " + f.getEmail() + " sobre adição do produto: " + produto.getId());
        }
    }
}

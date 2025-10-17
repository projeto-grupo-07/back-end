package school.sptech.crud_proj_v1.service;

import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioDetalhesDto;
import school.sptech.crud_proj_v1.entity.Funcionario;
import school.sptech.crud_proj_v1.repository.FuncionarioRepository;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<Funcionario> optFuncionario = funcionarioRepository.findByEmail(username);
        if (optFuncionario.isEmpty()) {
            throw new UsernameNotFoundException(String.format("usuario %s não encontrado", username));

        }
        return new FuncionarioDetalhesDto(optFuncionario.get());
    }
}

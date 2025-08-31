package school.sptech.crud_proj_v1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    Optional<Funcionario> findBycpfContainingIgnoreCase(String valor);
}

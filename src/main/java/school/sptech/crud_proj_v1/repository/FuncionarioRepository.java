package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crud_proj_v1.entity.Funcionario;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    Optional<Funcionario> findByEmail(String email);
    Boolean existsByCpf(String valor);
    Optional<Funcionario> findBycpfContainingIgnoreCase(String valor);
    List<Funcionario> findAllByAtivoTrue();

}

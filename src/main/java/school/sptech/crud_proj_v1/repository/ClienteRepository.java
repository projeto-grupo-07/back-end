package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import school.sptech.crud_proj_v1.entity.Cliente;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>, JpaSpecificationExecutor<Cliente> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
}

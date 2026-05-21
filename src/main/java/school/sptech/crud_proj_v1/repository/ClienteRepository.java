package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.crud_proj_v1.entity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>, JpaSpecificationExecutor<Cliente> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT c FROM Cliente c WHERE " +
            "(:nome IS NULL OR LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
            "(:genero IS NULL OR c.genero = :genero)")
    List<Cliente> filtrarClientes(@Param("nome") String nome, @Param("genero") Character genero);

    Optional<Cliente> findByCpf(String cpf);
}

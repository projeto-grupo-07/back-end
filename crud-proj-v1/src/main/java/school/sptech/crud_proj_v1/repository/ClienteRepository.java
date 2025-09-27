package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crud_proj_v1.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}

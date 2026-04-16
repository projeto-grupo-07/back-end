package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crud_proj_v1.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}

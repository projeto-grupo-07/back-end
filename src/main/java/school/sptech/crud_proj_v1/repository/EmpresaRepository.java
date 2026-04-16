package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crud_proj_v1.entity.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Integer> {

    boolean existsByEnderecoId(Integer enderecoId);
}
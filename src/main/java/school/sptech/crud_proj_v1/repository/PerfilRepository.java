package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crud_proj_v1.entity.Perfil;
import school.sptech.crud_proj_v1.entity.Venda;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
}

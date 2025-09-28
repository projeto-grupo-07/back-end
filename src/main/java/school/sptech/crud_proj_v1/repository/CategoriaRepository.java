package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crud_proj_v1.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}

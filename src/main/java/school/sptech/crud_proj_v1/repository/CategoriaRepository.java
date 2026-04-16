package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaResponseDto;
import school.sptech.crud_proj_v1.entity.Categoria;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByDescricaoContainingIgnoreCase(String descricao);

    List<Categoria> findByCategoriaPaiId(Integer id);

    boolean existsByDescricao(String descricao);

    List<Categoria> findByCategoriaPaiIsNull();

    List<Categoria> findByCategoriaPaiIsNotNull();
}

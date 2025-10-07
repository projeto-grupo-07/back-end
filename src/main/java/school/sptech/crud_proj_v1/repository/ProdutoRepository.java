package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crud_proj_v1.entity.Produto;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByCategoriaDescricaoContainingIgnoreCase(String descricao);
    List<Produto> findByModeloContainingIgnoreCase(String modelo);
}

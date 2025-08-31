package school.sptech.crud_proj_v1;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoReposiitory extends JpaRepository<Produto, Integer> {
    List<Produto> findByCategoriaContainingIgnoreCase(String valor);
}

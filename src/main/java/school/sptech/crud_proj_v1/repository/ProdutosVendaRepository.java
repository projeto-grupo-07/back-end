package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crud_proj_v1.entity.ProdutoVenda;

public interface ProdutosVendaRepository extends JpaRepository<ProdutoVenda, Integer> {
}

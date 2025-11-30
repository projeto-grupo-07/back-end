package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import school.sptech.crud_proj_v1.entity.CalcadoProduto;
import school.sptech.crud_proj_v1.entity.OutrosProduto;
import school.sptech.crud_proj_v1.entity.abstrato.Produto;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    CalcadoProduto findByModeloContainingIgnoreCase(String modelo);
    List<CalcadoProduto> findByMarcaContainingIgnoreCase(String marca);
    List<CalcadoProduto> findByNumero(Integer numero);
    List<CalcadoProduto> findByCorContainingIgnoreCase(String cor);

    OutrosProduto findByNomeContainingIgnoreCase(String nome);
    List<OutrosProduto> findByDescricaoContainingIgnoreCase(String descricao);

    @Query("SELECT c FROM CalcadoProduto c")
    List<CalcadoProduto> findAllCalcados();

    @Query("SELECT o FROM OutrosProduto o")
    List<OutrosProduto> findAllOutros();

    boolean existsByCategoriaId(Integer categoriaId);

    List<Produto> findAllByOrderByQuantidadeDesc();
    List<Produto> findAllByOrderByQuantidadeAsc();
    List<Produto> findByCategoriaDescricaoContainingIgnoreCase(String categoria);
    List<Produto> findByCategoriaDescricaoContainingIgnoreCaseOrderByValorUnitarioDesc(String categoria);

}

package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import school.sptech.crud_proj_v1.entity.CalcadoProduto;
import school.sptech.crud_proj_v1.entity.OutrosProduto;
import school.sptech.crud_proj_v1.entity.abstrato.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<CalcadoProduto> findByModeloContainingIgnoreCaseAndAtivoTrue(String modelo);
    List<CalcadoProduto> findByMarcaContainingIgnoreCaseAndAtivoTrue(String marca);
    List<CalcadoProduto> findByNumeroAndAtivoTrue(Integer numero);
    List<CalcadoProduto> findByCorContainingIgnoreCaseAndAtivoTrue(String cor);

    List<OutrosProduto> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome);
    List<OutrosProduto> findByDescricaoContainingIgnoreCaseAndAtivoTrue(String descricao);

    @Query("SELECT c FROM CalcadoProduto c WHERE c.ativo = true")
    List<CalcadoProduto> findAllCalcados();

    @Query("SELECT o FROM OutrosProduto o WHERE o.ativo = true")
    List<OutrosProduto> findAllOutros();

    boolean existsByCategoriaIdAndAtivoTrue(Integer categoriaId);

    List<Produto> findAllByAtivoTrueOrderByQuantidadeDesc();
    List<Produto> findAllByAtivoTrueOrderByQuantidadeAsc();
    List<Produto> findByCategoriaDescricaoContainingIgnoreCaseAndAtivoTrue(String categoria);
    List<Produto> findByCategoriaDescricaoContainingIgnoreCaseAndAtivoTrueOrderByValorUnitarioDesc(String categoria);
    List<Produto> findAllByAtivoTrue();

    @Modifying
    @Query("UPDATE Produto p SET p.ativo = false WHERE p.id = :id")
    void softDeleteById(Integer id);

    Optional<Produto> findByIdAndAtivoTrue(Integer id);
}

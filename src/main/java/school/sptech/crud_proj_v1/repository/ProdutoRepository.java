package school.sptech.crud_proj_v1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    // Paginação server-side — Offset (Spring Data Page)
    Page<Produto> findAllByAtivoTrue(Pageable pageable);

    // Paginação server-side — Cursor (WHERE id > :cursor LIMIT :tamanho)
    @Query("SELECT p FROM Produto p WHERE p.id > :cursor AND p.ativo = true ORDER BY p.id ASC")
    List<Produto> findByIdGreaterThanAndAtivoTrueOrderByIdAsc(@Param("cursor") int cursor, Pageable pageable);
}
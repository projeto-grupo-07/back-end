package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crud_proj_v1.entity.Venda;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Integer> {
    // aqui vai conter as queries personalizadas

    List<Venda> findByFuncionarioNomeContainingIgnoreCase(String valor);
    List<Venda> findByformaDePagamentoContainingIgnoreCase(String formPgto);
}

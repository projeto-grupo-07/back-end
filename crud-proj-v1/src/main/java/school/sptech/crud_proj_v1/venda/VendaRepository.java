package school.sptech.crud_proj_v1.venda;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Integer> {
    // aqui vai conter as queries personalizadas

    List<Venda> findBynomeVendedorContainingIgnoreCase(String valor);
}

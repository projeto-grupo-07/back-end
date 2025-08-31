package school.sptech.crud_proj_v1;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Integer> {
    // aqui vai conter as queries personalizadas
}

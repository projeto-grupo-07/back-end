package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crud_proj_v1.entity.VendaProduto;

public interface ItensVendaRepository extends JpaRepository<VendaProduto, Integer> {
}

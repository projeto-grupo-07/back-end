package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.crud_proj_v1.entity.Comissao;

import java.util.Optional;

public interface ComissaoRepository extends JpaRepository<Comissao, Integer> {

    Optional<Comissao> findByVendaId(Integer idVenda);

}

package school.sptech.crud_proj_v1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.crud_proj_v1.entity.Campanha;
import school.sptech.crud_proj_v1.enumeration.StatusCampanha;

import java.util.List;

public interface CampanhaRepository extends JpaRepository<Campanha, Integer> {
    @Query("SELECT c FROM Campanha c WHERE " +
            "(:assunto IS NULL OR LOWER(c.assunto) LIKE LOWER(CONCAT('%', :assunto, '%'))) AND " +
            "(:status IS NULL OR c.status = :status)")
    List<Campanha> filtrarCampanhas(@Param("assunto") String assunto, @Param("status") StatusCampanha status);
}

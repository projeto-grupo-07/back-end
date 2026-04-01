package school.sptech.crud_proj_v1.projection;

public interface RankingVendasProjection {
    String getNome(); // Serve tanto para Produto quanto para Marca
    Integer getTotalVendidas();
}

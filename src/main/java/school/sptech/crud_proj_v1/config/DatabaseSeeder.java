package school.sptech.crud_proj_v1.config; // Pode colocar no pacote de config ou no mesmo do Service

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.repository.VendaRepository;
import school.sptech.crud_proj_v1.service.SeedService;

// @Component
public class DatabaseSeeder implements CommandLineRunner {

    private final VendaRepository vendaRepository;
    private final SeedService seedService;

    public DatabaseSeeder(VendaRepository vendaRepository, SeedService seedService) {
        this.vendaRepository = vendaRepository;
        this.seedService = seedService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica se a tabela de vendas está vazia
        if (vendaRepository.count() == 0) {
            System.out.println("🌱 Tabela de Vendas vazia detectada! Iniciando o Motor de Seeding...");

            // Define o tamanho da massa de dados. 2500 vendas é um ótimo número para os gráficos ficarem densos!
            seedService.gerarMassaDeDados(2500);

            System.out.println("🚀 Tudo pronto! O banco está populado para a apresentação.");
        } else {
            System.out.println("📊 O banco já possui registros de vendas. O Seeding automático foi ignorado.");
        }
    }
}
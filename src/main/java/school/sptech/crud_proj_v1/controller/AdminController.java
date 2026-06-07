package school.sptech.crud_proj_v1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.service.SeedService;

@RestController
@RequestMapping("/admin/seed")
public class AdminController {

    private final SeedService seedService;

    public AdminController(SeedService seedService) {
        this.seedService = seedService;
    }

    @PostMapping
    public ResponseEntity<String> gerarMassa(@RequestParam(defaultValue = "1000") int qtd) {
        seedService.gerarMassaDeDados(qtd);
        return ResponseEntity.ok("✅ Sucesso! O Faker gerou " + qtd + " novas vendas no banco.");
    }
}
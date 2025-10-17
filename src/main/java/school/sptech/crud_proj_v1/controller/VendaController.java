package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Venda.VendaRequestDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.repository.VendaRepository;
import school.sptech.crud_proj_v1.service.VendaService;

import java.util.List;

@RestController
@RequestMapping("/vendas")
public class VendaController {
    private final VendaRepository repository;
    private final VendaService service;

    public VendaController(VendaRepository repository, VendaService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<VendaResponseDTO>> listarVendas(){
        List<VendaResponseDTO> all = service.listarTodasVendas();

        if (all.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(all);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<VendaResponseDTO> cadastrarVenda(@RequestBody VendaRequestDTO venda){
       VendaResponseDTO vendaCriada = service.cadastrarVenda(venda);
        return ResponseEntity.status(201).body(vendaCriada);


    }

    @GetMapping("/{nome}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<List<Venda>> buscarVendasPorNomeDoVendedor(@PathVariable String nome){
        List<Venda> vendasByNome = repository.findByFuncionarioNomeContainingIgnoreCase(nome);
        if (vendasByNome.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(vendasByNome);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Venda> atualizarVendaPorId(@PathVariable Integer id, @RequestBody Venda venda){
        venda.setId(id);
        if (repository.existsById(id)){
            repository.save(venda);
            return ResponseEntity.status(200).body(venda);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<Void> deletarVendaPorId(@PathVariable Integer id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }


}

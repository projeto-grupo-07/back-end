package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Venda.VendaRequestDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.mapper.VendaMapper;
import school.sptech.crud_proj_v1.repository.VendaRepository;
import school.sptech.crud_proj_v1.service.VendaService;

import java.util.List;

@Tag(name = "Venda")
@RestController
@RequestMapping("/vendas")
public class VendaController {
    private final VendaService service;

    public VendaController(VendaService service) {
        this.service = service;
    }

    //endpoint Augusto (2)
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método busca a venda pelo id")
    public ResponseEntity<VendaResponseDTO> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @GetMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todas as vendas cadastrados")
    public ResponseEntity<List<VendaResponseDTO>> listarVendas(){
        List<VendaResponseDTO> all = service.listar();
        if (all.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(all);
    }

    @GetMapping("/vendedor/{nome}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todas as vendas de um determinado vendedor através do seu nome")
    public ResponseEntity<List<VendaResponseDTO>> buscarVendasPorNomeDoVendedor(@PathVariable String nome){
        List<VendaResponseDTO> vendasByNome = service.buscarPorNomeVendedor(nome);
        if (vendasByNome.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(vendasByNome);
    }

    //endpoint Augusto
    @GetMapping("/forma-pagamento/{formaPgto}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todas as vendas através de uma determinada forma de papgamento")
    public ResponseEntity<List<VendaResponseDTO>> buscarPorFormaDePagamento(@PathVariable String formaPgto){
        List<VendaResponseDTO> vendasByFormaPagto = service.buscarPorFormaPagamento(formaPgto);
        if (vendasByFormaPagto.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(vendasByFormaPagto);
    }

    // Endpoint Kaio
    // Obs: Posteriormente fazer filtrando por data
    @GetMapping("/total")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método retorna o valor total de todas as vendas cadastradas")
    public ResponseEntity<Double> calcularTotalVendas() {
        Double total = service.calcularTotal();

        if(total == 0 || total == null){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(total);
    }

    @PostMapping
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método cadastra uma venda")
    public ResponseEntity<VendaResponseDTO> cadastrarVenda(@RequestBody VendaRequestDTO venda){
       VendaResponseDTO vendaCriada = service.cadastrar(venda);
        return ResponseEntity.status(201).body(vendaCriada);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método atualiza algum campo da venda pelo id")
    public ResponseEntity<VendaResponseDTO> atualizarVendaPorId(@PathVariable Integer id, @RequestBody @Valid VendaRequestDTO vendaRequest){
        VendaResponseDTO vendaAtualizada = service.atualizarPorId(id, vendaRequest);
        return ResponseEntity.status(200).body(vendaAtualizada);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método deleta uma venda pelo id")
    public ResponseEntity<Void> deletarVendaPorId(@PathVariable Integer id){
        service.deletarVendaPorId(id);
        return ResponseEntity.status(204).build();
    }




}

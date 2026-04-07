package school.sptech.crud_proj_v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.Venda.VendaRequestDTO;
import school.sptech.crud_proj_v1.dto.Venda.VendaResponseDTO;
import school.sptech.crud_proj_v1.entity.Venda;
import school.sptech.crud_proj_v1.enumeration.FormaDePagamento;
import school.sptech.crud_proj_v1.mapper.VendaMapper;
import school.sptech.crud_proj_v1.projection.MargemCategoriaProjection;
import school.sptech.crud_proj_v1.projection.MetodoPagamentoProjection;
import school.sptech.crud_proj_v1.projection.ProdutoRentavelProjection;
import school.sptech.crud_proj_v1.repository.VendaRepository;
import school.sptech.crud_proj_v1.service.VendaService;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Venda")
@RestController
@RequestMapping("/vendas")
@Slf4j
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
        log.info("Requisição para listar todas as vendas recebida");
        List<VendaResponseDTO> all = service.listar();
        if (all.isEmpty()){
            log.warn("Nenhuma venda encontrada na base de dados");
            return ResponseEntity.status(204).build();
        }
        log.info("Listagem finalizada. Total de registros: {}", all.size());
        return ResponseEntity.status(200).body(all);
    }

    @GetMapping("/vendedor/{nome}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todas as vendas de um determinado vendedor através do seu nome")
    public ResponseEntity<List<VendaResponseDTO>> buscarVendasPorNomeDoVendedor(@PathVariable String nome){
        log.info("Requisição para buscar vendas por nome do vendedor recebida");
        List<VendaResponseDTO> vendasByNome = service.buscarPorNomeVendedor(nome);
        if (vendasByNome.isEmpty()){
            log.warn("Nenhuma venda encontrada em nome do vendedor na base de dados");
            return ResponseEntity.status(204).build();
        }
        log.info("Busca de vendas por nome finalizada: {}", vendasByNome.size());
        return ResponseEntity.status(200).body(vendasByNome);
    }

    //endpoint Augusto
    @GetMapping("/forma-pagamento/{formaDePagamento}")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método lista todas as vendas através de uma determinada forma de papgamento")
    public ResponseEntity<List<VendaResponseDTO>> buscarPorFormaDePagamento(@PathVariable String formaDePagamento){
        log.info("Requisição para buscar vendas por forma de pagamento recebida");
        FormaDePagamento forma = FormaDePagamento.valueOf(formaDePagamento.toUpperCase());
        List<VendaResponseDTO> vendasByFormaPagto = service.buscarPorFormaPagamento(forma);
        if (vendasByFormaPagto.isEmpty()){
            log.warn("Nenhuma venda encontrada com a forma de pagamento solicitada na base de dados");
            return ResponseEntity.status(204).build();
        }
        log.info("Buscar de vendas por forma de pagamento finalizada: {}", vendasByFormaPagto.size());
        return ResponseEntity.status(200).body(vendasByFormaPagto);
    }

    // Endpoint Kaio
    // Obs: Posteriormente fazer filtrando por data
    @GetMapping("/total")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Esse método retorna o valor total de todas as vendas cadastradas")
    public ResponseEntity<Double> calcularTotalVendas() {
        log.info("Requisição para calcular total de vendas recebida");
        Double total = service.calcularTotal();
        if(total == null || total == 0){
            log.warn("Nenhuma venda encontrada no momento");
            return ResponseEntity.status(204).build();
        }
        log.info("Total de venda calculado com sucesso: {}", total);
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


    @SecurityRequirement(name = "Bearer")
    @GetMapping("/formas-pagamento")
    public ResponseEntity<FormaDePagamento[]> listarFormasDePagamento() {
        return ResponseEntity.ok(FormaDePagamento.values());
    }


    // ========================================================================
    // --- ENDPOINTS DASHBOARD ESTRATÉGICA ---
    // ========================================================================

    @GetMapping("/estrategico/pagamentos")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Retorna o desempenho por método de pagamento (Dashboard Estratégica)")
    public ResponseEntity<List<MetodoPagamentoProjection>> buscarDesempenhoPagamentos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        List<MetodoPagamentoProjection> result = service.buscarDesempenhoPagamentos(inicio, fim);
        if (result.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/estrategico/produtos-rentaveis")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Retorna os produtos mais rentáveis (Dashboard Estratégica)")
    public ResponseEntity<List<ProdutoRentavelProjection>> buscarProdutosRentaveis(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        List<ProdutoRentavelProjection> result = service.buscarProdutosRentaveis(inicio, fim);
        if (result.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/estrategico/margem-categoria")
    @SecurityRequirement(name = "Bearer")
    @Operation(summary = "Retorna a margem de lucro por categoria (Dashboard Estratégica)")
    public ResponseEntity<List<MargemCategoriaProjection>> buscarMargemCategoria(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        List<MargemCategoriaProjection> result = service.buscarMargemCategoria(inicio, fim);
        if (result.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(result);
    }



}

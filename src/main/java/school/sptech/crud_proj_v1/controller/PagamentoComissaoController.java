package school.sptech.crud_proj_v1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.crud_proj_v1.dto.PagamentoComissao.NovoPagamentoDTO;
import school.sptech.crud_proj_v1.dto.PagamentoComissao.ResumoComissaoDTO;
import school.sptech.crud_proj_v1.entity.PagamentoComissao;
import school.sptech.crud_proj_v1.service.PagamentoComissaoService;

import java.util.List;

@RestController
@RequestMapping("/comissoes")
@RequiredArgsConstructor
public class PagamentoComissaoController {

    private final PagamentoComissaoService comissaoService;

    @PostMapping("/{idFuncionario}/pagamentos")
    public ResponseEntity<PagamentoComissao> registrarPagamento(
            @PathVariable Integer idFuncionario,
            @RequestBody NovoPagamentoDTO dto) {

        PagamentoComissao novoPagamento = comissaoService.registrarPagamento(idFuncionario, dto);
        return ResponseEntity.status(201).body(novoPagamento);
    }

    @GetMapping("/{idFuncionario}/pagamentos")
    public ResponseEntity<List<PagamentoComissao>> buscarHistorico(@PathVariable Integer idFuncionario) {
        return ResponseEntity.ok(comissaoService.buscarHistorico(idFuncionario));
    }

    @GetMapping("/{idFuncionario}/resumo")
    public ResponseEntity<ResumoComissaoDTO> obterResumo(@PathVariable Integer idFuncionario) {
        return ResponseEntity.ok(comissaoService.obterResumo(idFuncionario));
    }
}
package school.sptech.crud_proj_v1.dto.Caixa;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CaixaResponseDTO {
    private Integer id;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataFechamento;
    private Double saldoInicial;
    private Double saldoFinal;
    private String status;
    private String nomeFuncionarioAbriu;
    private String nomeFuncionarioFechou;
}
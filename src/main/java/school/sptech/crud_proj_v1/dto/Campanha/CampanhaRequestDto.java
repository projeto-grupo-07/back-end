package school.sptech.crud_proj_v1.dto.Campanha;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CampanhaRequestDto {
    private String nome;
    private String assunto;
    private String corpoTexto;

    //filtros
    private Character genero;
    private String bairro;
    private String cidade;
    private String estado;
    private Integer mesAniversario;
    private Integer idade;
}

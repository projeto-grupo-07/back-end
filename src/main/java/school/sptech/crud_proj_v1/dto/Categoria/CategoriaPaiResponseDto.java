package school.sptech.crud_proj_v1.dto.Categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import school.sptech.crud_proj_v1.entity.Categoria;

import java.util.List;

@Data
public class CategoriaPaiResponseDto {

    @Schema(example = "1")
    private Integer id;

    @Schema(example = "Calçados")
    private String descricao;

    @Schema(example = "Sandalia, Chinelo....")
    private List<CategoriaResponseDto> subcategorias;

}

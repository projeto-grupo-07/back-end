package school.sptech.crud_proj_v1.dto.Categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoriaResponseDto {
//    @Schema(example = "1")
//    private Integer id;

    @Schema(example = "Calçados")
    private String descricao;

}

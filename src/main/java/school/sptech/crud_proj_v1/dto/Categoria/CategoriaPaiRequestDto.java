package school.sptech.crud_proj_v1.dto.Categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoriaPaiRequestDto {
    @Schema(example = "Calçados")
    @NotBlank(message = "O nome da categoria não pode ser vazio.")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
    private String descricao;
}

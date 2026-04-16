package school.sptech.crud_proj_v1.dto.Categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import school.sptech.crud_proj_v1.entity.Categoria;

@Data
public class CategoriaRequestDto {
    @Schema(example = "Calçados")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
    private String descricao;

    @Schema(example = "1", description = "ID da categoria pai")
    @NotNull(message = "O id da categoria pai é obrigatório.")
    private Integer categoriaPaiId;
}

package school.sptech.crud_proj_v1.dto.Categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaRequestDto {
    @Schema(example = "Calçados")
    @NotBlank(message = "O nome da categoria não pode ser vazio.")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
    private String descricao;

    public CategoriaRequestDto(String descricao) {
        this.descricao = descricao;
    }
    public CategoriaRequestDto() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

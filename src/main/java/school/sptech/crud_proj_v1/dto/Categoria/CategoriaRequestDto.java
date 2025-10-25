package school.sptech.crud_proj_v1.dto.Categoria;

import io.swagger.v3.oas.annotations.media.Schema;

public class CategoriaRequestDto {
    @Schema(example = "Calçados")
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

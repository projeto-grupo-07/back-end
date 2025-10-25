package school.sptech.crud_proj_v1.dto.Categoria;

import io.swagger.v3.oas.annotations.media.Schema;

public class CategoriaResponseDto {
    @Schema(example = "1")
    private Integer id;

    @Schema(example = "Calçados")
    private String descricao;

    public CategoriaResponseDto() {
    }

    public CategoriaResponseDto(Integer id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

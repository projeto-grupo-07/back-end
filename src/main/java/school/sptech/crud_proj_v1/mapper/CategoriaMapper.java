package school.sptech.crud_proj_v1.mapper;

import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaRequestDto;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaResponseDto;
import school.sptech.crud_proj_v1.entity.Categoria;

@Component
public class CategoriaMapper {

    public CategoriaResponseDto toResponseDto(Categoria entity) {

        if (entity == null) {
            return null;
        }

        CategoriaResponseDto dto = new CategoriaResponseDto();

        dto.setId(entity.getId());
        dto.setDescricao(entity.getDescricao());

        return dto;
    }

    public Categoria toEntity(CategoriaRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Categoria entity = new Categoria();
        entity.setDescricao(dto.getDescricao());

        return entity;
    }

}
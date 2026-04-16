package school.sptech.crud_proj_v1.mapper;

import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaPaiRequestDto;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaPaiResponseDto;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaRequestDto;
import school.sptech.crud_proj_v1.dto.Categoria.CategoriaResponseDto;
import school.sptech.crud_proj_v1.entity.Categoria;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoriaMapper {


    public static CategoriaResponseDto toResponseDto(Categoria entity) {

        if (entity == null) {
            return null;
        }

        CategoriaResponseDto dto = new CategoriaResponseDto();
       dto.setId(entity.getId());
        dto.setDescricao(entity.getDescricao());

        return dto;
    }

    public static List<CategoriaResponseDto> toResponseDto(List<Categoria> lista){
        if (lista == null){
            return null;
        }

        return lista.stream()
                .map(CategoriaMapper::toResponseDto)
                .toList();
    }

    public static Categoria toEntityFilho(CategoriaRequestDto dto, Categoria pai) {
        Categoria c = new Categoria();
        c.setDescricao(dto.getDescricao());
        c.setCategoriaPai(pai);
        return c;
    }

    public static Categoria toEntity(CategoriaPaiRequestDto dto){
        if (dto == null){
            return null;
        }

        Categoria entity = new Categoria();
        entity.setDescricao(dto.getDescricao());
        entity.setCategoriaPai(null);
        entity.setSubcategorias(new ArrayList<>());

        return entity;
    }

    public static CategoriaPaiResponseDto toResponseDtoPai(Categoria entity){
        if (entity == null) {
            return null;
        }

        CategoriaPaiResponseDto dto = new CategoriaPaiResponseDto();
        dto.setId(entity.getId());
        dto.setDescricao(entity.getDescricao());

        dto.setSubcategorias(
                entity.getSubcategorias()
                        .stream()
                        .map(CategoriaMapper::toResponseDto)
                        .toList()
        );

        return dto;
    }

    public static List<CategoriaPaiResponseDto> toResponseDtoPai(List<Categoria> lista){
        if (lista == null){
            return null;
        }

        return lista.stream()
                .map(CategoriaMapper::toResponseDtoPai)
                .toList();
    }




}
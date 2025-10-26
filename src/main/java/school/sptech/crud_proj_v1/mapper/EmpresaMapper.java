package school.sptech.crud_proj_v1.mapper;

import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Empresa.EmpresaResponseDto;
import school.sptech.crud_proj_v1.entity.Empresa;

@Component
public class EmpresaMapper {

    private final EnderecoMapper enderecoMapper;

    public EmpresaMapper(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    public EmpresaResponseDto toResponseDto(Empresa entity) {
        if (entity == null) {
            return null;
        }

        EmpresaResponseDto dto = new EmpresaResponseDto();
        dto.setIdEmpresa(entity.getIdEmpresa());
        dto.setRazaoSocial(entity.getRazaoSocial());
        dto.setCnpj(entity.getCnpj());
        dto.setResponsavel(entity.getResponsavel());

        dto.setEndereco(enderecoMapper.toResponseDto(entity.getEndereco()));

        return dto;
    }
}
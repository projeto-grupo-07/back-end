package school.sptech.crud_proj_v1.mapper;

import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Campanha.CampanhaRequestDto;
import school.sptech.crud_proj_v1.dto.Campanha.CampanhaResponseDto;
import school.sptech.crud_proj_v1.entity.Campanha;

import java.util.List;

@Component
public class CampanhaMapper {
    public CampanhaResponseDto toResponseDto(Campanha campanha, CampanhaRequestDto dto) {
        CampanhaResponseDto responseDto = new CampanhaResponseDto();
        responseDto.setId(campanha.getId());
        responseDto.setNome(campanha.getNome());
        responseDto.setAssunto(campanha.getAssunto());
        responseDto.setCorpoTexto(campanha.getCorpoTexto());
        responseDto.setGenero(dto.getGenero());
        responseDto.setBairro(dto.getBairro());
        responseDto.setCidade(dto.getCidade());
        responseDto.setEstado(dto.getEstado());
        responseDto.setStatus(campanha.getStatus().toString());
        responseDto.setMesAniversario(dto.getMesAniversario());
        return responseDto;
    }

    public List<CampanhaResponseDto> toResponseDto(List<Campanha> campanhas, CampanhaRequestDto dto) {
        return campanhas.stream()
                .map(campanha -> toResponseDto(campanha, dto))
                .toList();
    }

    public Campanha toEntity(CampanhaRequestDto dto) {
        Campanha campanha = new Campanha();
        campanha.setNome(dto.getNome());
        campanha.setAssunto(dto.getAssunto());
        campanha.setCorpoTexto(dto.getCorpoTexto());
        return campanha;
    }

    public CampanhaRequestDto toRequestDto(Campanha campanha) {
        CampanhaRequestDto requestDto = new CampanhaRequestDto();
        requestDto.setNome(campanha.getNome());
        requestDto.setAssunto(campanha.getAssunto());
        requestDto.setCorpoTexto(campanha.getCorpoTexto());
        if (campanha.getClientes() != null && !campanha.getClientes().isEmpty()) {
            var primeiro = campanha.getClientes().getFirst();
            if (primeiro != null) {
                requestDto.setGenero(primeiro.getGenero());
                if (primeiro.getEndereco() != null) {
                    requestDto.setBairro(primeiro.getEndereco().getBairro());
                    requestDto.setCidade(primeiro.getEndereco().getCidade());
                    requestDto.setEstado(primeiro.getEndereco().getEstado());
                }
                if (primeiro.getDtNasc() != null) {
                    requestDto.setMesAniversario(primeiro.getDtNasc().getMonthValue());
                }
            }
        }
        return requestDto;
    }
}

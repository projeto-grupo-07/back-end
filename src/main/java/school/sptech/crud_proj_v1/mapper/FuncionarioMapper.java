package school.sptech.crud_proj_v1.mapper;

import org.springframework.stereotype.Component;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioLoginDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioRequestDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioResponseDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioTokenDto;
import school.sptech.crud_proj_v1.dto.Tela.TelaDto;
import school.sptech.crud_proj_v1.entity.Funcionario;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FuncionarioMapper {

    public static Funcionario toEntity(FuncionarioRequestDto dto){
        if (dto == null) {
            return null;
        }

        Funcionario funcionario = new Funcionario();

        funcionario.setSalario(dto.getSalario());
        funcionario.setCpf(dto.getCpf());
        funcionario.setEmail(dto.getEmail());
        funcionario.setSenha(dto.getSenha());
        funcionario.setNome(dto.getNome());
        funcionario.setComissao(dto.getComissao());

        return funcionario;
    }

    public static Funcionario of(FuncionarioRequestDto funcionarioRequestDto) {
        Funcionario funcionario = new Funcionario();

        funcionario.setSalario(funcionarioRequestDto.getSalario());
        funcionario.setComissao(funcionarioRequestDto.getComissao());
        funcionario.setNome(funcionarioRequestDto.getNome());
        funcionario.setEmail(funcionarioRequestDto.getEmail());
        funcionario.setSenha(funcionarioRequestDto.getSenha());
        funcionario.setCpf(funcionarioRequestDto.getCpf());

        return funcionario;
    }

    public static Funcionario of(FuncionarioLoginDto funcionarioLoginDto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setEmail(funcionarioLoginDto.getEmail());
        funcionario.setSenha(funcionarioLoginDto.getSenha());
        return funcionario;
    }

    public static FuncionarioTokenDto of(Funcionario funcionario, String token) {
        FuncionarioTokenDto dto = new FuncionarioTokenDto();

        dto.setId(funcionario.getId());
        dto.setEmail(funcionario.getEmail());
        dto.setNome(funcionario.getNome());
        dto.setToken(token);

        if (funcionario.getPerfil() != null) {
            dto.setPerfil(funcionario.getPerfil().getNome());

            if (funcionario.getPerfil().getTelas() != null) {
                List<TelaDto> menu = funcionario.getPerfil().getTelas().stream()
                        .map(tela -> new TelaDto(
                                tela.getTitulo(),
                                tela.getPath(),
                                tela.getComponentKey(),
                                tela.getOrdem()
                        ))
                        .sorted(Comparator.comparingInt(TelaDto::getOrdem))
                        .collect(Collectors.toList());

                dto.setMenu(menu);
            }
        }

        return dto;
    }

    public static FuncionarioResponseDto of(Funcionario funcionario) {
        FuncionarioResponseDto funcionarioResponseDto  = new FuncionarioResponseDto();

        funcionarioResponseDto.setId(funcionario.getId());
        funcionarioResponseDto.setEmail(funcionario.getEmail());
        funcionarioResponseDto.setNome(funcionario.getNome());
        funcionarioResponseDto.setCpf(funcionario.getCpf());
        funcionarioResponseDto.setSalario(funcionario.getSalario());
        funcionarioResponseDto.setComissao(funcionario.getComissao());

        return funcionarioResponseDto;
    }
}

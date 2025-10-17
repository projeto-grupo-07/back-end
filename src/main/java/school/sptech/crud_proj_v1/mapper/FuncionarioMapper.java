package school.sptech.crud_proj_v1.mapper;

import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioLoginDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioRequestDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioResponseDto;
import school.sptech.crud_proj_v1.dto.Funcionario.FuncionarioTokenDto;
import school.sptech.crud_proj_v1.entity.Funcionario;

public class FuncionarioMapper {

    public static Funcionario of(FuncionarioRequestDto funcionarioRequestDto) {
        Funcionario funcionario = new Funcionario();

        funcionario.setNome(funcionarioRequestDto.getNome());
        funcionario.setEmail(funcionarioRequestDto.getEmail());
        funcionario.setSenha(funcionarioRequestDto.getSenha());

        return funcionario;
    }

    public static Funcionario of(FuncionarioLoginDto funcionarioLoginDto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setEmail(funcionarioLoginDto.getEmail());
        funcionario.setSenha(funcionarioLoginDto.getSenha());
        return funcionario;
    }

    public static FuncionarioTokenDto of(Funcionario funcionario, String token) {
        FuncionarioTokenDto funcionarioTokenDto = new FuncionarioTokenDto();

        funcionarioTokenDto.setId(funcionario.getId());
        funcionarioTokenDto.setEmail(funcionario.getEmail());
        funcionarioTokenDto.setNome(funcionario.getNome());
        funcionarioTokenDto.setToken(token);

        return funcionarioTokenDto;
    }

    public static FuncionarioResponseDto of (Funcionario funcionario) {
        FuncionarioResponseDto funcionarioResponseDto  = new FuncionarioResponseDto();

        funcionarioResponseDto.setId(funcionario.getId());
        funcionarioResponseDto.setEmail(funcionario.getEmail());
        funcionarioResponseDto.setNome(funcionario.getNome());

        return funcionarioResponseDto;
    }
}

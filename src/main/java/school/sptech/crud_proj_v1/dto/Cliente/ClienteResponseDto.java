package school.sptech.crud_proj_v1.dto.Cliente;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import school.sptech.crud_proj_v1.dto.Endereco.EnderecoResponseDto;

import java.time.LocalDate;

@Data
public class ClienteResponseDto {
    private Integer id;
    private String nome;
    private LocalDate dtNasc;
    private String email;
    private Character genero;
    private String telefone;
    private String cpf;
    private LocalDate dtCadastro;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String logradouro;
    private Integer numero;
    private String complemento;

}

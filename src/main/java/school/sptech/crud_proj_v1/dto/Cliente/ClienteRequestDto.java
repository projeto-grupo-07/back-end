package school.sptech.crud_proj_v1.dto.Cliente;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Data
public class ClienteRequestDto {

    @NotBlank
    private String nome;

    @NotNull
    private LocalDate dtNasc;

    @Email
    @NotNull
    private String email;

    @NotNull
    private Character genero;

    @NotBlank
    private String telefone;

    @CPF
    private String cpf;

    @NotBlank
    @Size(min = 9, max = 9, message = "O CEP deve ter 9 caracteres (ex: 00000-000)")
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "O CEP deve seguir o formato 00000-000")
    private String cep;

    @NotBlank
    @Size(min = 2, max = 2, message = "O Estado deve ter 2 caracteres (ex: SP)")
    private String estado;

    @NotBlank
    @Size(max = 45)
    private String cidade;

    @NotBlank
    @Size(max = 45)
    private String bairro;

    @NotBlank
    @Size(max = 45)
    private String logradouro;

    @NotNull(message = "O número é obrigatório")
    @Positive(message = "O número deve ser positivo")
    private Integer numero;

    @Size(max = 100)
    private String complemento;
}

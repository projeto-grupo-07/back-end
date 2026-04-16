package school.sptech.crud_proj_v1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*; // Importações corrigidas
import lombok.Data;

@Entity
@Data
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
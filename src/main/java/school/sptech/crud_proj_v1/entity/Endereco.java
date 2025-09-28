package school.sptech.crud_proj_v1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(min = 9,max = 9) //precisar ter hífen
    @Pattern(regexp = "^\\d{5}-?\\d{3}$")
    private String cep;

    @NotBlank
    @Size(min = 2, max = 2)
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

    @NotBlank
    @Size(max = 10)
    private Integer numero;

    @Size(max = 100)
    private String complemento;

    // como é classe so pra armazenar pro banco, não tem getter setter construtor etc
}

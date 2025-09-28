package school.sptech.crud_proj_v1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;

@Entity
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmpresa;

    @NotBlank()
    @Size(max = 45)
    private String razaoSocial;

    @NotBlank()
    @Size(min = 14, max = 18)

    @Pattern(regexp = "^(\\d{2}\\.?\\d{3}\\.?\\d{3}\\/?\\d{4}\\-?\\d{2})$")
    private String cnpj;

    @NotBlank()
    @Size(max = 45)
    private String responsavel;

    @OneToOne
    @JoinColumn(name = "fk_endereco")
    private Endereco endereco;

    // como é classe so pra armazenar pro banco, não tem getter setter construtor etc
}
package school.sptech.crud_proj_v1.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Entity
@Data
public class Cliente {
    // id, nome, dt nasc, email, genero, telefone, cpf, fk endereco, dt cadastro
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private LocalDate dtCadastro;
}

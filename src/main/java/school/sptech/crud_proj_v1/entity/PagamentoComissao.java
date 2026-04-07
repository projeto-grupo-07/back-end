package school.sptech.crud_proj_v1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "pagamento_comissao")
@Getter
@Setter
public class PagamentoComissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "fk_vendedor", nullable = false)
    private Funcionario vendedor;

    @Column(name = "data_pagamento", nullable = false)
    private LocalDateTime dataPagamento;

    @Column(nullable = false)
    private Double valor;

    @Column(length = 255)
    private String observacao;
}
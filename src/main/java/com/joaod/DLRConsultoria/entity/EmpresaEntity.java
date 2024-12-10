package com.joaod.DLRConsultoria.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "empresas")
public class EmpresaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String cnpj;

    private String CEP;

    private String email;

    private Date dataCadastro = new Date();

    private Date dataUltimoContrato;

    @OneToMany
    private List<ContratoEntity> contratosEmpresa;

    @ManyToOne
    private ConsultorEntity consultador;

    @OneToMany
    private List<ClientesEntity> clientes;

}

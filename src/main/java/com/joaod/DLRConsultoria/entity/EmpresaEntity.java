package com.joaod.DLRConsultoria.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "empresas")
@Data
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
    @JsonIgnore
    private List<ContratoEntity> contratosEmpresa;

    @ManyToOne
    @JoinColumn(name = "consultor_id")
    private ConsultorEntity consultor;

    @OneToMany
    @JsonIgnore
    private List<ClientesEntity> clientes;

}

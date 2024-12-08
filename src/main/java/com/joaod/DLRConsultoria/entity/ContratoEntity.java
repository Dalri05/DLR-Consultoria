package com.joaod.DLRConsultoria.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "contratos")
public class ContratoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Double valorContrato;

    private Date dataFechamentoContrato = new Date();

    @ManyToOne
    private EmpresaEntity empresa;

    @OneToOne
    private ConsultorEntity consultorResponsavel;


}

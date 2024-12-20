package com.joaod.DLRConsultoria.entity;

import com.joaod.DLRConsultoria.enums.TipoContratoEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "contratos")
@Data
public class ContratoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Double valorContrato;

    private Date dataFechamentoContrato = new Date();

    @ManyToOne
    private EmpresaEntity empresa;

    @ManyToOne
    private ConsultorEntity consultorResponsavel;

    @Enumerated(EnumType.STRING)
    private TipoContratoEnum tipoContrato;

    private Date dataAlteracao;

    @Column(unique = true, nullable = false)
    private String codigoUnicoContrato = UUID.randomUUID().toString();

}

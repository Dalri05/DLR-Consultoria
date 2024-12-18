package com.joaod.DLRConsultoria.entity;

import com.joaod.DLRConsultoria.enums.SituacaoEmailEnum;
import com.joaod.DLRConsultoria.enums.TipoEnvioEmailEnum;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "emails")
public class EmailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String destinatario;

    private Date dataEnvio;

    @Enumerated(EnumType.STRING)
    private SituacaoEmailEnum situacaoEmail;

    @Enumerated(EnumType.STRING)
    private TipoEnvioEmailEnum tipoEnvioEmail;

    private String corpo;

    private Date dataCadastro = new Date();
}

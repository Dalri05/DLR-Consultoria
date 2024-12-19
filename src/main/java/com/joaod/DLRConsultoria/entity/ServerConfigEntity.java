package com.joaod.DLRConsultoria.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "config")
@Data
public class ServerConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int flagEnviaEmailContratoConsultor = 0;
}

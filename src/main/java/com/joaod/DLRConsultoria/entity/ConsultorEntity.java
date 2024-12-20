package com.joaod.DLRConsultoria.entity;

import com.joaod.DLRConsultoria.enums.SituacaoConsultorEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "consultores")
public class ConsultorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String telefone;

    private Date dataCadastro = new Date();

    @Column(nullable = false, unique = true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private SituacaoConsultorEnum situacao;

    @OneToMany(mappedBy = "consultor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmpresaEntity> empresasResponsaveis = new ArrayList<>();

    private Date dataAlteracao;

    @OneToMany(mappedBy = "consultorResponsavel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContratoEntity> contratos = new ArrayList<>();

}


package com.joaod.DLRConsultoria.entity;

import com.joaod.DLRConsultoria.enums.SituacaoConsultorEnum;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
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

    @ManyToMany
    private List<EmpresaEntity> empresasResponsaveis;
}

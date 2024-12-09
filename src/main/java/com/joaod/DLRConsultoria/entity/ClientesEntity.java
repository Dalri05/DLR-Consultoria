package com.joaod.DLRConsultoria.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.repository.Query;

@Entity
@Table(name = "clientes")
@Data
public class ClientesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String usuario;

    private String senha;

    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    private String cpf;

    @ManyToOne
    private EmpresaEntity empresaResponsavel;
}

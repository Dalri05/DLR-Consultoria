package com.joaod.DLRConsultoria.repository;

import com.joaod.DLRConsultoria.entity.ClientesEntity;
import com.joaod.DLRConsultoria.entity.ConsultorEntity;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<ClientesEntity, Integer> {
    public ClientesEntity findByCpf(String cpf);

}

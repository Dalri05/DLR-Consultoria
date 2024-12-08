package com.joaod.DLRConsultoria.repository;

import com.joaod.DLRConsultoria.entity.ConsultorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultorRepository extends JpaRepository<ConsultorEntity, Integer> {

    public ConsultorEntity findByCpf(String cpf);
}

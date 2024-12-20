package com.joaod.DLRConsultoria.repository;

import com.joaod.DLRConsultoria.entity.ConsultorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultorRepository extends JpaRepository<ConsultorEntity, Integer> {

    public Optional<ConsultorEntity> findByCpf(String cpf);
}

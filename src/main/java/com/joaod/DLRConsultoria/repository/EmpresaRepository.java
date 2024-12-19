package com.joaod.DLRConsultoria.repository;

import com.joaod.DLRConsultoria.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Integer> {

    public Optional<EmpresaEntity> findByCnpj(String cnpj);

}

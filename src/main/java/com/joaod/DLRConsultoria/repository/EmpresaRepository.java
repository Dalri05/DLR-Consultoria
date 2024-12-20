package com.joaod.DLRConsultoria.repository;

import com.joaod.DLRConsultoria.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<EmpresaEntity, Integer> {

    @Query("SELECT e FROM EmpresaEntity e WHERE e.cnpj = :cnpj")
    public Optional<EmpresaEntity> findByCnpj(@Param("cnpj") String cnpjEmpresa);

}

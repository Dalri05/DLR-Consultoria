package com.joaod.DLRConsultoria.repository;

import com.joaod.DLRConsultoria.entity.ConsultorEntity;
import com.joaod.DLRConsultoria.entity.ContratoEntity;
import com.joaod.DLRConsultoria.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<ContratoEntity, Integer> {

    public List<ContratoEntity> findByEmpresaId(Integer empresaId);

    public List<ContratoEntity> findByConsultorResponsavelId(Integer consultorId);

    @Query("SELECT COUNT(c) FROM ContratoEntity c WHERE c.empresa.id = :empresaId")
    public int findQuantidadeContratoByEmpresaId(@Param("empresaId") int idEmpresa);

}

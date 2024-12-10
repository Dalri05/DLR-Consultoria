package com.joaod.DLRConsultoria.repository;

import com.joaod.DLRConsultoria.entity.ConsultorEntity;
import com.joaod.DLRConsultoria.entity.ContratoEntity;
import com.joaod.DLRConsultoria.entity.EmpresaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<ContratoEntity, Integer> {

    public List<ContratoEntity> findByEmpresaId(Integer empresaId);

    public List<ContratoEntity> findByConsultorResponsavelId(Integer consultorId);

}

package com.joaod.DLRConsultoria.service;

import com.joaod.DLRConsultoria.entity.ConsultorEntity;
import com.joaod.DLRConsultoria.entity.ContratoEntity;
import com.joaod.DLRConsultoria.entity.EmpresaEntity;
import com.joaod.DLRConsultoria.repository.ContratoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContratoService {
    @Autowired
    private ContratoRepository contratoRepository;

    public ResponseEntity editarContrato(Integer id, ContratoEntity novosDados) {
        Optional<ContratoEntity> contrato = contratoRepository.findById(id);

        if (contrato.isEmpty())
            return ResponseEntity.ofNullable("Contrato não encontrado");

        if (!novosDados.getEmpresa().equals(contrato.get().getEmpresa()))
            return ResponseEntity.badRequest().body("Não é possível trocar a empresa do contrato!");

        BeanUtils.copyProperties(novosDados, contrato.get(), "id");
        contrato.get().setDataAlteracao(new Date());

        contratoRepository.saveAndFlush(contrato.get());
        return ResponseEntity.ok("Contrato alterado com sucesso!");
    }


    public ResponseEntity deletarContrato(Integer id) {
        Optional<ContratoEntity> contrato = contratoRepository.findById(id);

        if (contrato.isEmpty())
            return ResponseEntity.ofNullable("Contrato não encontrado!");

        contratoRepository.deleteById(id);
        return ResponseEntity.ok("Contrato deletado com sucesso!");
    }

    public ResponseEntity criarContrato(ContratoEntity contrato) {
        if (Objects.isNull(contrato)) return ResponseEntity.ofNullable("Contrato inválido!");

        try {
            contratoRepository.save(contrato);
            return ResponseEntity.ok("Contrato criado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

    }

    public List<ContratoEntity> listarContratos() {
        return contratoRepository.findAll();
    }

    public List<ContratoEntity> listarContratosByEmpresa(Integer idEmpresa) {
        return contratoRepository.findByEmpresaId(idEmpresa);
    }

    public List<ContratoEntity> listarContratoByConsultor(Integer idconsultor) {
        return contratoRepository.findByConsultorResponsavelId(idconsultor);
    }

}

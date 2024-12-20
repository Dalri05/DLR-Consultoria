package com.joaod.DLRConsultoria.service;

import com.joaod.DLRConsultoria.entity.ContratoEntity;
import com.joaod.DLRConsultoria.entity.EmpresaEntity;
import com.joaod.DLRConsultoria.repository.EmpresaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;


    @Transactional
    public void salvarEmpresa(EmpresaEntity empresa) {
        String cnpjEmpresa = empresa.getCnpj();
        List<ContratoEntity> novosContratos = empresa.getContratosEmpresa();

        Optional<EmpresaEntity> empresaExistenteOpt = empresaRepository.findByCnpj(cnpjEmpresa);

        if (empresaExistenteOpt.isPresent()) {
            EmpresaEntity empresaExistente = empresaExistenteOpt.get();

            if (empresaExistente.getConsultor() == null) {
                empresaExistente.setConsultor(empresa.getConsultor());
            }

            if (novosContratos != null && !novosContratos.isEmpty()) {
                List<ContratoEntity> contratosAtualizados = new ArrayList<>(empresaExistente.getContratosEmpresa());
                contratosAtualizados.addAll(novosContratos);
                empresaExistente.setContratosEmpresa(contratosAtualizados);
            }

            empresaRepository.save(empresaExistente);
            return;
        } else {
            empresaRepository.save(empresa);
        }
    }


    public void atualizarContratoEmpresa(EmpresaEntity empresa, List<ContratoEntity> novosContratos){
        List<ContratoEntity> contratosExistentesDaEmpresa = empresa.getContratosEmpresa();

        for (ContratoEntity contratoNovo : novosContratos) {
            if (contratosExistentesDaEmpresa.contains(contratoNovo)) continue;

            contratosExistentesDaEmpresa.add(contratoNovo);
        }

    }

    public ResponseEntity<String> criarRegistroEmpresa(EmpresaEntity empresa) {
        if (isEmpresaValida(empresa)) {
            empresaRepository.save(empresa);
            return ResponseEntity.ok().body("Empresa cadastrada com sucesso");
        }
        return ResponseEntity.badRequest().body("Ocorreu um erro ao registrar a empresa!");
    }

    public boolean isEmpresaValida(EmpresaEntity empresa) {
        Optional<EmpresaEntity> empresaExistenteOpt = empresaRepository.findByCnpj(empresa.getCnpj());

        if (empresaExistenteOpt.isPresent()) return false;

        return true;
    }

    public Optional<EmpresaEntity> buscarEmpresaPorCnpj(String cnpj){
        return empresaRepository.findByCnpj(cnpj);
    }

    public List<EmpresaEntity> listarEmpresas(){
        return empresaRepository.findAll();
    }
}

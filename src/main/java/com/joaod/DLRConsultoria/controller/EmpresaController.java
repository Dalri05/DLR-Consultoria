package com.joaod.DLRConsultoria.controller;

import com.joaod.DLRConsultoria.entity.EmpresaEntity;
import com.joaod.DLRConsultoria.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/listar")
    private List<EmpresaEntity> listarEmpresas(){
        return empresaService.listarEmpresas();
    }

    @PostMapping("/criar")
    public ResponseEntity<String> criarEmpresa(@RequestBody EmpresaEntity empresa){
        return empresaService.criarRegistroEmpresa(empresa);
    }
}

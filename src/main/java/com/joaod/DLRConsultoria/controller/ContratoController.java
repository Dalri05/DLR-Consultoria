package com.joaod.DLRConsultoria.controller;

import com.joaod.DLRConsultoria.entity.ContratoEntity;
import com.joaod.DLRConsultoria.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contrato")
public class ContratoController {
    @Autowired
    ContratoService contratoService;

    @PutMapping("/editar/{idContrato}")
    private ResponseEntity editarDadosContrato(@PathVariable Integer idContrato, @RequestBody ContratoEntity contratoEntity){
        return contratoService.editarContrato(idContrato,contratoEntity);
    }

    @DeleteMapping("deletar/{idContrato}")
    private ResponseEntity deletarContrato(@PathVariable Integer idContrato) {
        return contratoService.deletarContrato(idContrato);
    }

    @PostMapping("/criar")
    private ResponseEntity criarContrato(@RequestBody ContratoEntity contratoEntity) {
        return contratoService.criarContrato(contratoEntity);
    }

    @GetMapping("/listar")
    private List<ContratoEntity> listarContratos() {
        return contratoService.listarContratos();
    }

    @GetMapping("/listar/{idEmpresa}")
    private List<ContratoEntity> listarContratosByEmpresa(@PathVariable Integer idEmpresa) {
        return contratoService.listarContratosByEmpresa(idEmpresa);
    }

    @GetMapping("/listar/{idConsultor}")
    private List<ContratoEntity> listarContratoByConsultor(@PathVariable Integer idConsultor) {
        return contratoService.listarContratoByConsultor(idConsultor);
    }

}

package com.joaod.DLRConsultoria.controller;

import com.joaod.DLRConsultoria.entity.ConsultorEntity;
import com.joaod.DLRConsultoria.service.ConsultorService;
import com.joaod.DLRConsultoria.service.email.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consultor")
public class ConsultorController {
    @Autowired
    private ConsultorService consultorService;

    @Autowired
    private EnvioEmailService envioEmailService;

    @PostMapping("/criar")
    private ResponseEntity criarConsultor(@RequestBody ConsultorEntity consultor) {
        try {
            String response = consultorService.criarConsultor(consultor);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/editar/{cpf}")
    private ResponseEntity editarCadastroConsultor(@PathVariable String cpf, @RequestBody ConsultorEntity consultor) {
        try {
            return consultorService.editarCadastroConsultor(cpf, consultor);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletar/{cpf}")
    private ResponseEntity deletarConsultor(@PathVariable String cpf) {
        try {
            return consultorService.deletarConsultorbyCpf(cpf);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listar/{cpf}")
    private Optional<ConsultorEntity> listarConsultorByCpf(@PathVariable String cpf) {
        return consultorService.listarConsultorByCpf(cpf);
    }

    @GetMapping("/listar")
    private List<ConsultorEntity> listarConsultores() {
        return consultorService.listarTodosConsultores();
    }

}

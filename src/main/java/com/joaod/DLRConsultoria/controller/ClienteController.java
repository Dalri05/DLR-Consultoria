package com.joaod.DLRConsultoria.controller;

import com.joaod.DLRConsultoria.entity.ClientesEntity;
import com.joaod.DLRConsultoria.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/listar")
    public List<ClientesEntity> listarClientes() {
        return clienteService.listarClientes();
    }

    @GetMapping("listar/{cpf}")
    public ClientesEntity listarClienteByCpf(@PathVariable String cpf){
        return clienteService.listarClienteByCpf(cpf);
    }

    @PostMapping("/criar")
    public ResponseEntity<ClientesEntity> inserirCliente(@RequestBody ClientesEntity cliente) {
        try {
            ClientesEntity clienteNovo = clienteService.criarClienteNovo(cliente);
            return ResponseEntity.ok(clienteNovo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("deletar/{cpf}")
    private ResponseEntity deletarClientebyCpf(@PathVariable String cpf){
        return clienteService.deletarClientebyCpf(cpf);
    }

    @PutMapping("/editar/{cpf}")
    private ResponseEntity atualizarCliente(@PathVariable String cpf, @RequestBody ClientesEntity novosDados) {
        return clienteService.editarDadosCliente(cpf, novosDados);
    }

}

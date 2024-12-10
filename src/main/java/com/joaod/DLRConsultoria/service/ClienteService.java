package com.joaod.DLRConsultoria.service;

import com.joaod.DLRConsultoria.entity.ClientesEntity;
import com.joaod.DLRConsultoria.repository.ClienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public ResponseEntity editarDadosCliente(String cpf, ClientesEntity novosDados) {

        if (cpf.equals(novosDados.getCpf())) {
            return ResponseEntity.badRequest().body("Não é possível alterar o CPF do cliente");
        }

        ClientesEntity cliente = new ClientesEntity();
        try {
            cliente = clienteRepository.findByCpf(cpf);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }

        if (Objects.isNull(cliente)) {
            return ResponseEntity.ofNullable("Cliente não encontrado");
        }

        BeanUtils.copyProperties(novosDados, cliente, "id", "cpf", "dataCadastro");
        cliente.setDataAlteracao(new Date());
        clienteRepository.save(cliente);

        return ResponseEntity.ok().body("Cliente alterado com sucesso");

    }

    public ClientesEntity criarClienteNovo(ClientesEntity cliente) {
        if (Objects.isNull(cliente)) return cliente;

        try {
            return clienteRepository.save(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }



    }

    public ResponseEntity deletarClientebyCpf(String cpf){
        ClientesEntity cliente = clienteRepository.findByCpf(cpf);

        if (Objects.isNull(cliente))
            return ResponseEntity.ofNullable("Cliente não encontrado");

        clienteRepository.delete(cliente);
        return ResponseEntity.ok("Cliente deletado com sucesso");
    }

    public ClientesEntity listarClienteByCpf(String cpf){
        return clienteRepository.findByCpf(cpf);
    }

    public List<ClientesEntity> listarClientes(){
        return clienteRepository.findAll();
    }
}

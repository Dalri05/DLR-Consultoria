package com.joaod.DLRConsultoria.service;

import com.joaod.DLRConsultoria.entity.ConsultorEntity;
import com.joaod.DLRConsultoria.repository.ConsultorRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ConsultorService {
    @Autowired
    ConsultorRepository consultorRepository;

    public String criarConsultor (ConsultorEntity consultor) {
        if (Objects.isNull(consultor)) return "Passe os dados para criar um consultor!";

        try {
            consultorRepository.save(consultor);
        } catch (Exception e) {
            e.printStackTrace();
            return "Não foi possivel salvar o consultor, tente novamente mais tarde!";
        }

        return "Consultor criado com sucesso!";
    }

    public ResponseEntity deletarConsultorbyCpf(String cpf){
        try {
            String resposta = null;
            ConsultorEntity consultor = consultorRepository.findByCpf(cpf);

            if (Objects.isNull(consultor)) {
                resposta = "Esse CPF não é cadastrado como um consultor!";
                return ResponseEntity.unprocessableEntity().body(resposta);
            }

            consultorRepository.delete(consultor);

            resposta = "Copnsultor deletado com sucesso!";
            return ResponseEntity.ok().body(resposta);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ResponseEntity editarCadastroConsultor(String cpf, ConsultorEntity novosDados) {
        try {
            ConsultorEntity consultor = consultorRepository.findByCpf(cpf);

            if (Objects.isNull(consultor))
                return ResponseEntity.unprocessableEntity().body("Consultor não encontrado, realize um cadastro!");

            if (!novosDados.getCpf().equals(cpf))
                return ResponseEntity.badRequest().body("Não é possivel trocar o cpf de um consultor");

            BeanUtils.copyProperties(novosDados, consultor, "id", "cpf", "dataCadastro");

            consultor.setDataAlteracao(new Date());
            consultorRepository.save(consultor);

            return ResponseEntity.ok().body("Consultor atualizado com sucesso!");

        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ConsultorEntity listarConsultorByCpf(String cpf) {
        return consultorRepository.findByCpf(cpf);
    }

    public List<ConsultorEntity> listarTodosConsultores() {
        return consultorRepository.findAll();
    }
}

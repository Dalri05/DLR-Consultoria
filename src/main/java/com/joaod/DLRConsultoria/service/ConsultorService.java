package com.joaod.DLRConsultoria.service;

import com.joaod.DLRConsultoria.entity.ConsultorEntity;
import com.joaod.DLRConsultoria.entity.EmpresaEntity;
import com.joaod.DLRConsultoria.repository.ConsultorRepository;
import com.joaod.DLRConsultoria.repository.EmpresaRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConsultorService {
    @Autowired
    private ConsultorRepository consultorRepository;
    @Autowired
    private EmpresaRepository empresaRepository;

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
            Optional<ConsultorEntity> consultor = consultorRepository.findByCpf(cpf);

            if (consultor.isEmpty()) {
                resposta = "Esse CPF não é cadastrado como um consultor!";
                return ResponseEntity.unprocessableEntity().body(resposta);
            }

            consultorRepository.delete(consultor.get());

            resposta = "Copnsultor deletado com sucesso!";
            return ResponseEntity.ok().body(resposta);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    public ResponseEntity editarCadastroConsultor(String cpf, ConsultorEntity novosDados) {
        try {
            Optional<ConsultorEntity> consultorOpt = consultorRepository.findByCpf(cpf);
            ConsultorEntity consultor = new ConsultorEntity();

            if (consultorOpt.isPresent()) {
                consultor = consultorOpt.get();
            }

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

    public void salvarConsultor(ConsultorEntity consultor) {
        Optional<ConsultorEntity> consultorExistenteOpt = consultorRepository.findByCpf(consultor.getCpf());
        ConsultorEntity consultorExistente = new ConsultorEntity();

        if (consultorExistenteOpt.isPresent()) {
            consultorExistente = consultorExistenteOpt.get();
        }

        if (Objects.nonNull(consultorExistente) && consultorExistente.getEmpresasResponsaveis() == null) {
            consultorExistente.setEmpresasResponsaveis(new ArrayList<>());
        }

        for (EmpresaEntity novaEmpresa : consultor.getEmpresasResponsaveis()) {
            if (!consultorExistente.getEmpresasResponsaveis().contains(novaEmpresa)) {
                consultorExistente.getEmpresasResponsaveis().add(novaEmpresa);
            }
        }

        consultorRepository.save(consultorExistente);
    }

    public Optional<ConsultorEntity> buscarConsultorPorCpf(String cpf) {
        return consultorRepository.findByCpf(cpf);
    }

    public Optional<ConsultorEntity> listarConsultorByCpf(String cpf) {
        return consultorRepository.findByCpf(cpf);
    }

    public List<ConsultorEntity> listarTodosConsultores() {
        return consultorRepository.findAll();
    }
}

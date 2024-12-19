package com.joaod.DLRConsultoria.service;

import com.joaod.DLRConsultoria.entity.ConsultorEntity;
import com.joaod.DLRConsultoria.entity.ContratoEntity;
import com.joaod.DLRConsultoria.entity.EmpresaEntity;
import com.joaod.DLRConsultoria.entity.ServerConfigEntity;
import com.joaod.DLRConsultoria.repository.ContratoRepository;
import com.joaod.DLRConsultoria.repository.EmpresaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContratoService {
    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private ConsultorService consultorService;

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

        EmpresaEntity empresaContrato = contrato.getEmpresa();
        ConsultorEntity consultorReponsavel = contrato.getConsultorResponsavel();

        try {
            validarAtualizarEmpresaConsultor(contrato);
            enviarEmailContrato(contrato);
            contratoRepository.save(contrato);
            return ResponseEntity.ok("Contrato criado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private void validarAtualizarEmpresaConsultor(ContratoEntity contrato) {
        EmpresaEntity empresaContrato = contrato.getEmpresa();
        ConsultorEntity consultorResponsavel = contrato.getConsultorResponsavel();

        if (empresaContrato == null || consultorResponsavel == null) {
            throw new IllegalArgumentException("Empresa ou Consultor não podem ser nulos.");
        }

        Optional<EmpresaEntity> empresaExistenteOpt = empresaService.buscarEmpresaPorCnpj(empresaContrato.getCnpj());
        if (!empresaExistenteOpt.isPresent()) {
            throw new IllegalArgumentException("A empresa com o CNPJ " + empresaContrato.getCnpj() + " não existe, criando o registro!");
        }

        EmpresaEntity empresaExistente = empresaExistenteOpt.get();

        if (!empresaExistente.getConsultador().equals(consultorResponsavel)) {
            empresaExistente.setConsultador(consultorResponsavel);
            empresaService.salvarEmpresa(empresaExistente);
        }

        if (consultorResponsavel.getEmpresasResponsaveis() == null) {
            consultorResponsavel.setEmpresasResponsaveis(new ArrayList<>());
        }
        if (!consultorResponsavel.getEmpresasResponsaveis().contains(empresaContrato)) {
            consultorResponsavel.getEmpresasResponsaveis().add(empresaContrato);
        }
        consultorService.salvarConsultor(consultorResponsavel);
    }


    private void enviarEmailContrato(ContratoEntity contrato) {
        try {
            EnvioEmailService envioEmailService = new EnvioEmailService();
            ConsultorEntity consultorResponsavel = contrato.getConsultorResponsavel();
            ServerConfigEntity config = new ServerConfigEntity();
            EmpresaEntity empresaContrato = contrato.getEmpresa();
            Integer numerosContratos = contratoRepository.findQuantidadeContratoByEmpresaId(empresaContrato.getId());

            String corpoEmail = MontarCorpoEmailService.montarCorpotEmailContrato(empresaContrato.getNome(), consultorResponsavel.getNome(), numerosContratos);
            List<String> listDestinatarios = new ArrayList<>();

            if (config.getFlagEnviaEmailContratoConsultor() == 1 && Objects.nonNull(consultorResponsavel))
                listDestinatarios.add(consultorResponsavel.getEmail());

            listDestinatarios.add(empresaContrato.getEmail());

            envioEmailService.enviarEmail(listDestinatarios, corpoEmail, "Novo contrato criado! ");
        } catch (Exception e) {
            e.printStackTrace();
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

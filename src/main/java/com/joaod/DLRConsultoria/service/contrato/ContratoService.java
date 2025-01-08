package com.joaod.DLRConsultoria.service.contrato;

import com.joaod.DLRConsultoria.entity.ConsultorEntity;
import com.joaod.DLRConsultoria.entity.ContratoEntity;
import com.joaod.DLRConsultoria.entity.EmpresaEntity;
import com.joaod.DLRConsultoria.entity.ServerConfigEntity;
import com.joaod.DLRConsultoria.repository.ConsultorRepository;
import com.joaod.DLRConsultoria.repository.ContratoRepository;
import com.joaod.DLRConsultoria.repository.EmpresaRepository;
import com.joaod.DLRConsultoria.service.ConsultorService;
import com.joaod.DLRConsultoria.service.EmpresaService;
import com.joaod.DLRConsultoria.service.email.EnvioEmailService;
import com.joaod.DLRConsultoria.service.email.MontarCorpoEmailService;
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
    @Autowired
    private EnvioEmailService envioEmailService;
    @Autowired
    private EmpresaRepository empresaRepository;
    @Autowired
    private ConsultorRepository consultorRepository;

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
        if (Objects.isNull(contrato)) {
            return ResponseEntity.badRequest().body("Contrato inválido!");
        }

        try {
            validarAtualizarEmpresaConsultor(contrato);

            contrato.setConsultorResponsavel(
                    consultorService.buscarConsultorPorCpf(contrato.getConsultorResponsavel().getCpf())
                            .orElseThrow(() -> new IllegalArgumentException("Consultor não encontrado!"))
            );
            contrato.setEmpresa(
                    empresaService.buscarEmpresaPorCnpj(contrato.getEmpresa().getCnpj())
                            .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada!"))
            );

            contratoRepository.save(contrato);
            enviarEmailContrato(contrato);

            return ResponseEntity.ok("Contrato criado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao criar contrato!");
        }
    }


    private void validarAtualizarEmpresaConsultor(ContratoEntity contrato) {
        EmpresaEntity empresaContrato = contrato.getEmpresa();
        ConsultorEntity consultorResponsavel = contrato.getConsultorResponsavel();

        if (empresaContrato == null || consultorResponsavel == null) {
            throw new IllegalArgumentException("Empresa ou Consultor não podem ser nulos.");
        }

        Optional<EmpresaEntity> empresaExistenteOpt = empresaService.buscarEmpresaPorCnpj(empresaContrato.getCnpj());
        EmpresaEntity empresaExistente = empresaExistenteOpt
                .orElseThrow(() -> new IllegalArgumentException("A empresa com o CNPJ " + empresaContrato.getCnpj() + " não existe!"));

        Optional<ConsultorEntity> consultorExistenteOpt = consultorService.buscarConsultorPorCpf(consultorResponsavel.getCpf());
        ConsultorEntity consultorExistente = consultorExistenteOpt
                .orElseThrow(() -> new IllegalArgumentException("O consultor com o CPF " + consultorResponsavel.getCpf() + " não existe!"));

        if (empresaExistente.getConsultor() == null || !empresaExistente.getConsultor().equals(consultorExistente)) {
            empresaExistente.setConsultor(consultorExistente);
            empresaService.salvarEmpresa(empresaExistente);
        }

        if (!consultorExistente.getEmpresasResponsaveis().contains(empresaExistente)) {
            consultorExistente.getEmpresasResponsaveis().add(empresaExistente);
            consultorService.salvarConsultor(consultorExistente);
        }
    }

    private void enviarEmailContrato(ContratoEntity contrato) {
        try {
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

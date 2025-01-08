package com.joaod.DLRConsultoria.service.contrato;

import com.joaod.DLRConsultoria.Model.ContratoCalculadoModel;
import com.joaod.DLRConsultoria.entity.ContratoEntity;
import com.joaod.DLRConsultoria.entity.EmpresaEntity;
import com.joaod.DLRConsultoria.repository.ContratoRepository;
import com.joaod.DLRConsultoria.utils.DoubleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CalcularContratoService {
    @Autowired
    private ContratoRepository contratoRepository;

    public ContratoCalculadoModel calcularContratos(EmpresaEntity empresa) {
        List<ContratoEntity> contratosAtuais = empresa.getContratosEmpresa();
        Map<String, Double> mapContratoValor = popularMapContratos(contratosAtuais);
        ContratoCalculadoModel contratoCalculado = new ContratoCalculadoModel();

        String codMaiorContrato = getMaiorContrato(mapContratoValor);

        contratoCalculado.setQuantidadeTotalContratos(mapContratoValor.size());
        contratoCalculado.setValorTotalContratos(calcularValorTotalContratos(mapContratoValor.values()));
        contratoCalculado.setContratoValorMaisAlto(codMaiorContrato);
        contratoCalculado.setMediaValoresContratos(calcularMediaValoresContratos(mapContratoValor.values()));

        return contratoCalculado;
    }

    private Double calcularValorTotalContratos(Collection<Double> valoresContratos) {
        Double valorTotalContratos = 0.0;

        for (Double valor : valoresContratos) {
            valorTotalContratos += valor;
        }

        return valorTotalContratos;
    }

    private Double calcularMediaValoresContratos(Collection<Double> valoresContratos) {
        Double valorTotalContratos = 0.0;

        for (Double valor : valoresContratos) {
            valorTotalContratos += valor;
        }
        return valorTotalContratos / valoresContratos.size();
    }

    private Map<String, Double> popularMapContratos(List<ContratoEntity> contratos) {
        Map<String, Double> mapContratoValor = new HashMap<>();
        Double valorContrato = 0D;
        String codigoContrato = null;

        for (ContratoEntity contrato : contratos) {
            if (mapContratoValor.containsKey(contrato.getCodigoUnicoContrato())) continue;

            valorContrato = DoubleUtils.getDoubleValue(contrato.getValorContrato(), 0D);
            codigoContrato = contrato.getCodigoUnicoContrato();

            mapContratoValor.put(codigoContrato, valorContrato);

        }

        return mapContratoValor;
    }

    private String getMaiorContrato(Map<String, Double> mapContratoValor) {
        String codMaiorContrato = "";

        for (String contrato : mapContratoValor.keySet()) {
            codMaiorContrato = contrato;

            if (mapContratoValor.get(contrato) > mapContratoValor.get(codMaiorContrato)) {
                codMaiorContrato = contrato;
            }
        }

        return codMaiorContrato;
    }
}

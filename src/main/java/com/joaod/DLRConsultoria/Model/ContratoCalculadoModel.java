package com.joaod.DLRConsultoria.Model;

import lombok.Data;

@Data
public class ContratoCalculadoModel {
    private int quantidadeTotalContratos;

    private Double mediaValoresContratos;

    private Double valorTotalContratos;

    private String contratoValorMaisAlto;
}

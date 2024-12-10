package com.joaod.DLRConsultoria.enums;

public enum SituacaoConsultorEnum {
    ATIVO(1),
    INATIVO(2);

    private int situacao;

    private SituacaoConsultorEnum(int situacao) {
        this.situacao = situacao;
    }

    public int getSituacao() {
        return situacao;
    }

}

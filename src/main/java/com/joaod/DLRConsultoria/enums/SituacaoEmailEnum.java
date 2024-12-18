package com.joaod.DLRConsultoria.enums;

public enum SituacaoEmailEnum {
    CANCELADO(0),
    ENVIADO(1),
    PENDENTE(2);

    private final int situacaoEnvioEmail;

    SituacaoEmailEnum(int situacaoEnvioEmail) {
        this.situacaoEnvioEmail = situacaoEnvioEmail;
    }

    public int getSituacaoEnvioEmail() {
        return situacaoEnvioEmail;
    }
}

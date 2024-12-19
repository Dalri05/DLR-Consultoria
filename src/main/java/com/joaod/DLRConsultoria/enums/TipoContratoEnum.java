package com.joaod.DLRConsultoria.enums;

public enum TipoContratoEnum {
        UNICO(2),
    RECORRENTE(1);

    private final int tipoContrato;

    TipoContratoEnum(int tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    public int getTipoContrato() {
        return tipoContrato;
    }
}

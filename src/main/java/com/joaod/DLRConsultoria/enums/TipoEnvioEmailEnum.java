package com.joaod.DLRConsultoria.enums;

public enum TipoEnvioEmailEnum {
    CLIENTE(0),
    CONSULTOR(1),
    EMPRESA(2);

    private int tipoEnvioEmail;

    TipoEnvioEmailEnum(int tipoEnvioEmail) {
        this.tipoEnvioEmail = tipoEnvioEmail;
    }

    public int getTipoEnvioEmail() {
        return tipoEnvioEmail;
    }
}

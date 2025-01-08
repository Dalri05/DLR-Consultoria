package com.joaod.DLRConsultoria.utils;

public class DoubleUtils {

    public static Double getDoubleValue(Number valor, Double valorPadrao) {
        try {
            if (valor == null) return valorPadrao;
            return valor.doubleValue();
        } catch (Exception e) {
            return valorPadrao;
        }
    }

}

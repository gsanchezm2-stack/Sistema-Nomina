package com.cipa.nomina.util;

import com.cipa.nomina.excepcion.DatoInvalidoException;
import java.math.BigDecimal;

/** Validaciones reutilizables. Todas lanzan {@link DatoInvalidoException} si el dato no es válido. */
public final class Validaciones {

    private Validaciones() { }

    public static BigDecimal noNegativo(BigDecimal valor, String nombre) {
        if (valor == null) {
            throw new DatoInvalidoException(nombre + " es obligatorio.");
        }
        if (valor.signum() < 0) {
            throw new DatoInvalidoException(nombre + " no puede ser negativo.");
        }
        return valor;
    }

    /** Un porcentaje se expresa como fracción: 0.05 = 5 %. */
    public static BigDecimal porcentaje(BigDecimal valor, String nombre) {
        noNegativo(valor, nombre);
        if (valor.compareTo(BigDecimal.ONE) > 0) {
            throw new DatoInvalidoException(nombre + " debe estar entre 0 y 1.");
        }
        return valor;
    }

    public static String texto(String valor, String nombre) {
        if (valor == null || valor.isBlank()) {
            throw new DatoInvalidoException(nombre + " no puede estar vacío.");
        }
        return valor.trim();
    }
}

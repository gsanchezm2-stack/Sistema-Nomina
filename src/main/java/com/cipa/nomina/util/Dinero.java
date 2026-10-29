package com.cipa.nomina.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Utilidades monetarias. Se usa BigDecimal para evitar errores de redondeo de double. */
public final class Dinero {

    private Dinero() { }

    /** Redondea a 2 decimales (mitad hacia arriba). */
    public static BigDecimal redondear(BigDecimal valor) {
        return valor.setScale(2, RoundingMode.HALF_UP);
    }
}

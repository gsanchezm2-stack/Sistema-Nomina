package com.cipa.nomina;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

/** Ayudas comunes para las pruebas. */
final class TestUtil {

    private TestUtil() { }

    /** Compara valores monetarios por valor numérico (ignora diferencias de escala). */
    static void assertDinero(String esperado, BigDecimal real) {
        assertEquals(0, new BigDecimal(esperado).compareTo(real),
                () -> "Se esperaba " + esperado + " pero fue " + real);
    }
}

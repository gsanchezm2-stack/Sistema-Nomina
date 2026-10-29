package com.cipa.nomina.config;

import java.math.BigDecimal;

/** Reglas de negocio parametrizables (evita "números mágicos" en el código). */
public final class Constantes {

    private Constantes() { }

    // Empleado asalariado
    public static final BigDecimal PORCENTAJE_BONO_ANTIGUEDAD = new BigDecimal("0.10"); // 10 % del salario
    public static final int ANIOS_BONO_ANTIGUEDAD = 5;                                  // "más de 5 años"

    // Empleado por horas
    public static final BigDecimal UMBRAL_HORAS_EXTRA = new BigDecimal("40"); // más de 40 h son extras
    public static final BigDecimal FACTOR_HORA_EXTRA = new BigDecimal("1.5"); // 1.5 x tarifa normal

    // Empleado por comisión
    public static final BigDecimal UMBRAL_VENTAS_BONO = new BigDecimal("20000000"); // ventas > $20.000.000
    public static final BigDecimal PORCENTAJE_BONO_VENTAS = new BigDecimal("0.03");  // 3 % sobre ventas

    // Deducciones obligatorias
    public static final BigDecimal PORCENTAJE_SEGURO_PENSION = new BigDecimal("0.04"); // 4 % del bruto
    /** El enunciado no indica la tasa de ARL: se asume riesgo clase I (0,522 %). Es configurable. */
    public static final BigDecimal PORCENTAJE_ARL = new BigDecimal("0.00522");

    // Beneficios adicionales
    public static final BigDecimal VALOR_BONO_ALIMENTACION = new BigDecimal("1000000"); // por mes
    public static final BigDecimal PORCENTAJE_FONDO_AHORRO = new BigDecimal("0.02");    // 2 % del salario
    public static final int ANIOS_MINIMOS_FONDO_AHORRO = 1;                              // "más de 1 año"
}

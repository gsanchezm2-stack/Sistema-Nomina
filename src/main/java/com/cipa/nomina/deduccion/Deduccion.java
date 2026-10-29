package com.cipa.nomina.deduccion;

import java.math.BigDecimal;

/**
 * Interfaz mínima de una deducción (Interface Segregation) y estrategia de cálculo (Strategy).
 * Para una deducción nueva basta con implementar esta interfaz y entregarla al servicio.
 */
public interface Deduccion {

    String nombre();

    BigDecimal calcular(BigDecimal salarioBruto);
}

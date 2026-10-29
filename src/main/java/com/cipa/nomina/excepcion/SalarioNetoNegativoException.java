package com.cipa.nomina.excepcion;

/** La liquidación daría un salario neto negativo (regla de negocio). */
public class SalarioNetoNegativoException extends NominaException {
    public SalarioNetoNegativoException(String mensaje) {
        super(mensaje);
    }
}

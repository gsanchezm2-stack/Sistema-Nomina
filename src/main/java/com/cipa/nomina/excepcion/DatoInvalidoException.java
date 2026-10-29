package com.cipa.nomina.excepcion;

/** Un dato de entrada incumple una validación (horas, ventas, salario...). */
public class DatoInvalidoException extends NominaException {
    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}

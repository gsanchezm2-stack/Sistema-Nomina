package com.cipa.nomina.excepcion;

/** Error base de la aplicación de nómina. */
public class NominaException extends RuntimeException {
    public NominaException(String mensaje) {
        super(mensaje);
    }
}

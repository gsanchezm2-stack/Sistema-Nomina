package com.cipa.nomina.empleado;

/** Tipos de empleado que maneja la empresa. */
public enum TipoEmpleado {
    ASALARIADO("Asalariado"),
    POR_HORAS("Por horas"),
    POR_COMISION("Por comisión"),
    TEMPORAL("Temporal");

    private final String etiqueta;

    TipoEmpleado(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}

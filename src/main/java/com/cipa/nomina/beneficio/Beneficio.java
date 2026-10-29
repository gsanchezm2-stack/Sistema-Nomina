package com.cipa.nomina.beneficio;

import com.cipa.nomina.empleado.Empleado;
import java.math.BigDecimal;
import java.time.LocalDate;

/** Beneficio adicional: decide si aplica a un empleado y cuánto vale (patrón Strategy). */
public interface Beneficio {

    String nombre();

    boolean aplica(Empleado empleado, LocalDate fechaCalculo);

    BigDecimal calcular(BigDecimal salarioBruto);

    /** true: se suma al neto del empleado. false: se deposita en otro lugar (p. ej. un fondo). */
    default boolean sePagaAlEmpleado() {
        return true;
    }
}

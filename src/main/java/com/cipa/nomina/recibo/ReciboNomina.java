package com.cipa.nomina.recibo;

import com.cipa.nomina.empleado.TipoEmpleado;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/** Resultado inmutable de una liquidación. */
public record ReciboNomina(
        String nombre,
        String documento,
        TipoEmpleado tipo,
        LocalDate fechaCalculo,
        Map<String, BigDecimal> devengado,
        BigDecimal salarioBruto,
        Map<String, BigDecimal> deducciones,
        Map<String, BigDecimal> beneficios,   // pagados al empleado (suman al neto)
        Map<String, BigDecimal> aportes,      // depositados en fondos (NO suman al neto)
        BigDecimal salarioNeto) {

    public BigDecimal totalDeducciones() {
        return deducciones.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal totalBeneficios() {
        return beneficios.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

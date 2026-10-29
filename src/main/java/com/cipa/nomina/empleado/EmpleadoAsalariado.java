package com.cipa.nomina.empleado;

import static com.cipa.nomina.config.Constantes.ANIOS_BONO_ANTIGUEDAD;
import static com.cipa.nomina.config.Constantes.PORCENTAJE_BONO_ANTIGUEDAD;

import com.cipa.nomina.util.Dinero;
import com.cipa.nomina.util.Validaciones;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/** Salario fijo mensual + bono del 10 % si lleva más de 5 años en la empresa. */
public class EmpleadoAsalariado extends Empleado {

    private final BigDecimal salarioMensual;

    public EmpleadoAsalariado(String nombre, String documento, LocalDate fechaIngreso,
                              BigDecimal salarioMensual) {
        super(nombre, documento, fechaIngreso);
        this.salarioMensual = Validaciones.noNegativo(salarioMensual, "El salario mensual");
    }

    @Override
    public TipoEmpleado getTipo() {
        return TipoEmpleado.ASALARIADO;
    }

    @Override
    public Map<String, BigDecimal> calcularDevengado(LocalDate fechaCalculo) {
        Map<String, BigDecimal> devengado = new LinkedHashMap<>();
        devengado.put("Salario mensual", Dinero.redondear(salarioMensual));
        if (superaAntiguedad(ANIOS_BONO_ANTIGUEDAD, fechaCalculo)) {
            devengado.put("Bono por antigüedad",
                    Dinero.redondear(salarioMensual.multiply(PORCENTAJE_BONO_ANTIGUEDAD)));
        }
        return devengado;
    }
}

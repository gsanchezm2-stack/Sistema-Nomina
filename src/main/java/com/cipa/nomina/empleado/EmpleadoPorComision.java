package com.cipa.nomina.empleado;

import static com.cipa.nomina.config.Constantes.PORCENTAJE_BONO_VENTAS;
import static com.cipa.nomina.config.Constantes.UMBRAL_VENTAS_BONO;

import com.cipa.nomina.util.Dinero;
import com.cipa.nomina.util.Validaciones;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/** Salario base + comisión sobre ventas; bono extra del 3 % si las ventas superan $20.000.000. */
public class EmpleadoPorComision extends Empleado {

    private final BigDecimal salarioBase;
    private final BigDecimal porcentajeComision;
    private final BigDecimal ventas;

    public EmpleadoPorComision(String nombre, String documento, LocalDate fechaIngreso,
                               BigDecimal salarioBase, BigDecimal porcentajeComision,
                               BigDecimal ventas) {
        super(nombre, documento, fechaIngreso);
        this.salarioBase = Validaciones.noNegativo(salarioBase, "El salario base");
        this.porcentajeComision = Validaciones.porcentaje(porcentajeComision, "El porcentaje de comisión");
        this.ventas = Validaciones.noNegativo(ventas, "Las ventas");
    }

    @Override
    public TipoEmpleado getTipo() {
        return TipoEmpleado.POR_COMISION;
    }

    @Override
    public Map<String, BigDecimal> calcularDevengado(LocalDate fechaCalculo) {
        Map<String, BigDecimal> devengado = new LinkedHashMap<>();
        devengado.put("Salario base", Dinero.redondear(salarioBase));
        devengado.put("Comisión por ventas", Dinero.redondear(ventas.multiply(porcentajeComision)));
        if (ventas.compareTo(UMBRAL_VENTAS_BONO) > 0) {
            devengado.put("Bono por ventas", Dinero.redondear(ventas.multiply(PORCENTAJE_BONO_VENTAS)));
        }
        return devengado;
    }
}

package com.cipa.nomina.empleado;

import com.cipa.nomina.excepcion.DatoInvalidoException;
import com.cipa.nomina.util.Dinero;
import com.cipa.nomina.util.Validaciones;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/** Salario fijo mensual con contrato a término definido. Sin bonos ni beneficios. */
public class EmpleadoTemporal extends Empleado {

    private final BigDecimal salarioMensual;
    private final LocalDate fechaFinContrato;

    public EmpleadoTemporal(String nombre, String documento, LocalDate fechaIngreso,
                            BigDecimal salarioMensual, LocalDate fechaFinContrato) {
        super(nombre, documento, fechaIngreso);
        if (fechaFinContrato == null || !fechaFinContrato.isAfter(fechaIngreso)) {
            throw new DatoInvalidoException("La fecha de fin del contrato debe ser posterior al ingreso.");
        }
        this.salarioMensual = Validaciones.noNegativo(salarioMensual, "El salario mensual");
        this.fechaFinContrato = fechaFinContrato;
    }

    @Override
    public TipoEmpleado getTipo() {
        return TipoEmpleado.TEMPORAL;
    }

    public boolean contratoVigente(LocalDate fechaCalculo) {
        return !fechaCalculo.isBefore(getFechaIngreso()) && !fechaCalculo.isAfter(fechaFinContrato);
    }

    @Override
    public Map<String, BigDecimal> calcularDevengado(LocalDate fechaCalculo) {
        Map<String, BigDecimal> devengado = new LinkedHashMap<>();
        devengado.put("Salario mensual", Dinero.redondear(salarioMensual));
        return devengado;
    }
}

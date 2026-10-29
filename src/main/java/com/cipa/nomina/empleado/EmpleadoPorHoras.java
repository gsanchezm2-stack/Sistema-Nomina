package com.cipa.nomina.empleado;

import static com.cipa.nomina.config.Constantes.FACTOR_HORA_EXTRA;
import static com.cipa.nomina.config.Constantes.UMBRAL_HORAS_EXTRA;

import com.cipa.nomina.util.Dinero;
import com.cipa.nomina.util.Validaciones;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/** Paga por hora trabajada; las horas sobre 40 se pagan a 1.5 x. No recibe bonos. */
public class EmpleadoPorHoras extends Empleado {

    private final BigDecimal tarifaHora;
    private final BigDecimal horasTrabajadas;
    private final boolean aceptaFondoAhorro;

    public EmpleadoPorHoras(String nombre, String documento, LocalDate fechaIngreso,
                            BigDecimal tarifaHora, BigDecimal horasTrabajadas,
                            boolean aceptaFondoAhorro) {
        super(nombre, documento, fechaIngreso);
        this.tarifaHora = Validaciones.noNegativo(tarifaHora, "La tarifa por hora");
        this.horasTrabajadas = Validaciones.noNegativo(horasTrabajadas, "Las horas trabajadas");
        this.aceptaFondoAhorro = aceptaFondoAhorro;
    }

    @Override
    public TipoEmpleado getTipo() {
        return TipoEmpleado.POR_HORAS;
    }

    @Override
    public boolean aceptaFondoAhorro() {
        return aceptaFondoAhorro;
    }

    @Override
    public Map<String, BigDecimal> calcularDevengado(LocalDate fechaCalculo) {
        BigDecimal horasOrdinarias = horasTrabajadas.min(UMBRAL_HORAS_EXTRA);
        BigDecimal horasExtra = horasTrabajadas.subtract(UMBRAL_HORAS_EXTRA).max(BigDecimal.ZERO);

        Map<String, BigDecimal> devengado = new LinkedHashMap<>();
        devengado.put("Horas ordinarias", Dinero.redondear(horasOrdinarias.multiply(tarifaHora)));
        devengado.put("Horas extra",
                Dinero.redondear(horasExtra.multiply(tarifaHora).multiply(FACTOR_HORA_EXTRA)));
        return devengado;
    }
}

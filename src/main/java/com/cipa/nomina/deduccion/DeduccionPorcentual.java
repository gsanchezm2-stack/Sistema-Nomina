package com.cipa.nomina.deduccion;

import com.cipa.nomina.util.Dinero;
import com.cipa.nomina.util.Validaciones;
import java.math.BigDecimal;

/** Deducción que es un porcentaje fijo del salario bruto. */
public class DeduccionPorcentual implements Deduccion {

    private final String nombre;
    private final BigDecimal porcentaje;

    public DeduccionPorcentual(String nombre, BigDecimal porcentaje) {
        this.nombre = nombre;
        this.porcentaje = Validaciones.porcentaje(porcentaje, "El porcentaje de " + nombre);
    }

    @Override
    public String nombre() {
        return nombre;
    }

    @Override
    public BigDecimal calcular(BigDecimal salarioBruto) {
        return Dinero.redondear(salarioBruto.multiply(porcentaje));
    }
}

package com.cipa.nomina.beneficio;

import com.cipa.nomina.config.Constantes;
import com.cipa.nomina.empleado.Empleado;
import com.cipa.nomina.empleado.TipoEmpleado;
import com.cipa.nomina.util.Dinero;
import com.cipa.nomina.util.Validaciones;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

/** $1.000.000/mes cubierto por la empresa para empleados permanentes (Asalariado y Comisión). */
public class BonoAlimentacion implements Beneficio {

    private final BigDecimal valor;
    private final Set<TipoEmpleado> tipos;

    public BonoAlimentacion() {
        this(Constantes.VALOR_BONO_ALIMENTACION,
                EnumSet.of(TipoEmpleado.ASALARIADO, TipoEmpleado.POR_COMISION));
    }

    public BonoAlimentacion(BigDecimal valor, Set<TipoEmpleado> tipos) {
        this.valor = Validaciones.noNegativo(valor, "El bono de alimentación");
        this.tipos = Set.copyOf(tipos);
    }

    @Override
    public String nombre() {
        return "Bono de alimentación";
    }

    @Override
    public boolean aplica(Empleado empleado, LocalDate fechaCalculo) {
        return tipos.contains(empleado.getTipo());
    }

    @Override
    public BigDecimal calcular(BigDecimal salarioBruto) {
        return Dinero.redondear(valor);
    }
}

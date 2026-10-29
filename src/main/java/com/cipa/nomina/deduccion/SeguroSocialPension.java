package com.cipa.nomina.deduccion;

import com.cipa.nomina.config.Constantes;
import java.math.BigDecimal;

/** Seguro social y pensión: 4 % del salario bruto. */
public class SeguroSocialPension extends DeduccionPorcentual {

    public SeguroSocialPension() {
        this(Constantes.PORCENTAJE_SEGURO_PENSION);
    }

    public SeguroSocialPension(BigDecimal porcentaje) {
        super("Seguro social y pensión", porcentaje);
    }
}

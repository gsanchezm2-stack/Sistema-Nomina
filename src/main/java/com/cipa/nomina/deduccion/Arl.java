package com.cipa.nomina.deduccion;

import com.cipa.nomina.config.Constantes;
import java.math.BigDecimal;

/** Aporte a riesgos laborales (ARL). La tasa es configurable. */
public class Arl extends DeduccionPorcentual {

    public Arl() {
        this(Constantes.PORCENTAJE_ARL);
    }

    public Arl(BigDecimal porcentaje) {
        super("ARL", porcentaje);
    }
}

package com.cipa.nomina;

import static com.cipa.nomina.TestUtil.assertDinero;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cipa.nomina.beneficio.BonoAlimentacion;
import com.cipa.nomina.beneficio.FondoAhorro;
import com.cipa.nomina.deduccion.Arl;
import com.cipa.nomina.deduccion.SeguroSocialPension;
import com.cipa.nomina.empleado.Empleado;
import com.cipa.nomina.empleado.EmpleadoAsalariado;
import com.cipa.nomina.empleado.EmpleadoPorComision;
import com.cipa.nomina.empleado.EmpleadoPorHoras;
import com.cipa.nomina.empleado.EmpleadoTemporal;
import com.cipa.nomina.excepcion.DatoInvalidoException;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/** Pruebas unitarias de deducciones y beneficios. */
class DeduccionesBeneficiosTest {

    private static final LocalDate HOY = LocalDate.of(2026, 10, 28);

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
    }

    @Nested
    @DisplayName("Deducciones")
    class Deducciones {

        @Test
        @DisplayName("seguro social y pensión es 4 % del bruto")
        void seguroYPension() {
            assertDinero("40000.00", new SeguroSocialPension().calcular(bd("1000000")));
        }

        @Test
        @DisplayName("ARL usa la tasa configurada")
        void arlConfigurable() {
            assertDinero("10000.00", new Arl(bd("0.01")).calcular(bd("1000000")));
        }

        @Test
        @DisplayName("porcentaje negativo es inválido")
        void porcentajeInvalido() {
            assertThrows(DatoInvalidoException.class, () -> new Arl(bd("-0.1")));
        }
    }

    @Nested
    @DisplayName("Bono de alimentación")
    class Alimentacion {

        private final BonoAlimentacion bono = new BonoAlimentacion();

        @Test
        @DisplayName("aplica a asalariado y comisión")
        void aplicaAPermanentes() {
            Empleado asalariado = new EmpleadoAsalariado("A", "1", LocalDate.of(2020, 1, 1), bd("1000"));
            Empleado comision = new EmpleadoPorComision("B", "2", LocalDate.of(2020, 1, 1),
                    bd("1000"), bd("0.1"), bd("0"));
            assertTrue(bono.aplica(asalariado, HOY));
            assertTrue(bono.aplica(comision, HOY));
        }

        @Test
        @DisplayName("no aplica a por horas ni temporal")
        void noAplicaAOtros() {
            Empleado horas = new EmpleadoPorHoras("C", "3", LocalDate.of(2020, 1, 1),
                    bd("1000"), bd("10"), false);
            Empleado temporal = new EmpleadoTemporal("D", "4", LocalDate.of(2026, 1, 1),
                    bd("1000"), LocalDate.of(2027, 1, 1));
            assertFalse(bono.aplica(horas, HOY));
            assertFalse(bono.aplica(temporal, HOY));
        }

        @Test
        @DisplayName("el valor es un millón")
        void valor() {
            assertDinero("1000000.00", bono.calcular(BigDecimal.ZERO));
        }
    }

    @Nested
    @DisplayName("Fondo de ahorro")
    class Fondo {

        private Empleado horas(LocalDate ingreso, boolean acepta) {
            return new EmpleadoPorHoras("L", "5", ingreso, bd("10000"), bd("40"), acepta);
        }

        @Test
        @DisplayName("aplica con más de 1 año y aceptación")
        void aplica() {
            assertTrue(new FondoAhorro().aplica(horas(LocalDate.of(2025, 1, 1), true), HOY));
        }

        @Test
        @DisplayName("no aplica si no acepta")
        void noAcepta() {
            assertFalse(new FondoAhorro().aplica(horas(LocalDate.of(2025, 1, 1), false), HOY));
        }

        @Test
        @DisplayName("no aplica con menos de 1 año")
        void pocaAntiguedad() {
            assertFalse(new FondoAhorro().aplica(horas(LocalDate.of(2026, 6, 1), true), HOY));
        }

        @Test
        @DisplayName("no aplica a otros tipos de empleado")
        void otroTipo() {
            Empleado asalariado = new EmpleadoAsalariado("A", "1", LocalDate.of(2010, 1, 1), bd("1000"));
            assertFalse(new FondoAhorro().aplica(asalariado, HOY));
        }

        @Test
        @DisplayName("el aporte es 2 % y no se paga al empleado")
        void aporte() {
            FondoAhorro fondo = new FondoAhorro();
            assertDinero("8000.00", fondo.calcular(bd("400000")));
            assertFalse(fondo.sePagaAlEmpleado());
        }
    }
}

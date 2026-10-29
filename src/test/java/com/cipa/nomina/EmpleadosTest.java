package com.cipa.nomina;

import static com.cipa.nomina.TestUtil.assertDinero;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cipa.nomina.empleado.EmpleadoAsalariado;
import com.cipa.nomina.empleado.EmpleadoPorComision;
import com.cipa.nomina.empleado.EmpleadoPorHoras;
import com.cipa.nomina.empleado.EmpleadoTemporal;
import com.cipa.nomina.excepcion.DatoInvalidoException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/** Pruebas unitarias de cada tipo de empleado (patrón Arrange-Act-Assert). */
class EmpleadosTest {

    private static final LocalDate HOY = LocalDate.of(2026, 10, 28);

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
    }

    @Nested
    @DisplayName("Empleado asalariado")
    class Asalariado {

        private EmpleadoAsalariado crear(LocalDate ingreso) {
            return new EmpleadoAsalariado("Ana", "1", ingreso, bd("3000000"));
        }

        @Test
        @DisplayName("con más de 5 años recibe bono del 10 %")
        void masDeCincoAniosRecibeBono() {
            Map<String, BigDecimal> dev = crear(LocalDate.of(2020, 1, 1)).calcularDevengado(HOY);
            assertDinero("300000.00", dev.get("Bono por antigüedad"));
        }

        @Test
        @DisplayName("con exactamente 5 años NO recibe bono")
        void exactamenteCincoAniosNoRecibeBono() {
            assertFalse(crear(LocalDate.of(2021, 10, 28)).calcularDevengado(HOY)
                    .containsKey("Bono por antigüedad"));
        }

        @Test
        @DisplayName("un día después de los 5 años sí recibe bono")
        void unDiaDespuesRecibeBono() {
            assertTrue(crear(LocalDate.of(2021, 10, 27)).calcularDevengado(HOY)
                    .containsKey("Bono por antigüedad"));
        }

        @Test
        @DisplayName("salario negativo es inválido")
        void salarioNegativo() {
            assertThrows(DatoInvalidoException.class,
                    () -> new EmpleadoAsalariado("Ana", "1", HOY, bd("-1")));
        }

        @Test
        @DisplayName("nombre vacío es inválido")
        void nombreVacio() {
            assertThrows(DatoInvalidoException.class,
                    () -> new EmpleadoAsalariado("  ", "1", HOY, bd("1000")));
        }
    }

    @Nested
    @DisplayName("Empleado por horas")
    class PorHoras {

        private EmpleadoPorHoras crear(String horas) {
            return new EmpleadoPorHoras("Luis", "2", LocalDate.of(2025, 1, 1),
                    bd("10000"), bd(horas), false);
        }

        @Test
        @DisplayName("hasta 40 horas se paga tarifa normal")
        void hastaCuarentaHoras() {
            Map<String, BigDecimal> dev = crear("40").calcularDevengado(HOY);
            assertDinero("400000.00", dev.get("Horas ordinarias"));
            assertDinero("0.00", dev.get("Horas extra"));
        }

        @Test
        @DisplayName("las horas extra se pagan a 1.5 x")
        void horasExtra() {
            assertDinero("75000.00", crear("45").calcularDevengado(HOY).get("Horas extra"));
        }

        @Test
        @DisplayName("no recibe bonos")
        void sinBonos() {
            assertTrue(crear("50").calcularDevengado(HOY).keySet()
                    .equals(Set.of("Horas ordinarias", "Horas extra")));
        }

        @Test
        @DisplayName("horas negativas son inválidas")
        void horasNegativas() {
            assertThrows(DatoInvalidoException.class, () -> crear("-1"));
        }
    }

    @Nested
    @DisplayName("Empleado por comisión")
    class PorComision {

        private EmpleadoPorComision crear(String ventas) {
            return new EmpleadoPorComision("Marta", "3", LocalDate.of(2022, 1, 1),
                    bd("2000000"), bd("0.05"), bd(ventas));
        }

        @Test
        @DisplayName("ventas bajo el umbral no tienen bono")
        void bajoUmbral() {
            Map<String, BigDecimal> dev = crear("10000000").calcularDevengado(HOY);
            assertDinero("500000.00", dev.get("Comisión por ventas"));
            assertFalse(dev.containsKey("Bono por ventas"));
        }

        @Test
        @DisplayName("ventas exactamente en $20.000.000 no tienen bono")
        void enElUmbral() {
            assertFalse(crear("20000000").calcularDevengado(HOY).containsKey("Bono por ventas"));
        }

        @Test
        @DisplayName("ventas sobre el umbral reciben bono del 3 %")
        void sobreUmbral() {
            assertDinero("900000.00", crear("30000000").calcularDevengado(HOY).get("Bono por ventas"));
        }

        @Test
        @DisplayName("ventas negativas son inválidas")
        void ventasNegativas() {
            assertThrows(DatoInvalidoException.class, () -> crear("-1"));
        }

        @Test
        @DisplayName("porcentaje de comisión mayor a 1 es inválido")
        void porcentajeInvalido() {
            assertThrows(DatoInvalidoException.class, () -> new EmpleadoPorComision(
                    "M", "3", HOY, bd("1000"), bd("5"), bd("0")));
        }
    }

    @Nested
    @DisplayName("Empleado temporal")
    class Temporal {

        @Test
        @DisplayName("solo devenga salario fijo")
        void soloSalarioFijo() {
            EmpleadoTemporal emp = new EmpleadoTemporal("Carlos", "4", LocalDate.of(2026, 9, 1),
                    bd("1500000"), LocalDate.of(2027, 2, 28));
            Map<String, BigDecimal> dev = emp.calcularDevengado(HOY);
            assertTrue(dev.size() == 1);
            assertDinero("1500000.00", dev.get("Salario mensual"));
        }

        @Test
        @DisplayName("el fin de contrato debe ser posterior al ingreso")
        void finAnteriorAlIngreso() {
            assertThrows(DatoInvalidoException.class, () -> new EmpleadoTemporal(
                    "C", "4", LocalDate.of(2026, 9, 1), bd("1000"), LocalDate.of(2026, 8, 1)));
        }

        @Test
        @DisplayName("contrato vigente según la fecha")
        void contratoVigente() {
            EmpleadoTemporal emp = new EmpleadoTemporal("C", "4", LocalDate.of(2026, 9, 1),
                    bd("1000"), LocalDate.of(2026, 12, 31));
            assertTrue(emp.contratoVigente(HOY));
            assertFalse(emp.contratoVigente(LocalDate.of(2027, 1, 1)));
        }
    }
}

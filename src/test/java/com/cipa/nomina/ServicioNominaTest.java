package com.cipa.nomina;

import static com.cipa.nomina.TestUtil.assertDinero;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cipa.nomina.deduccion.Deduccion;
import com.cipa.nomina.empleado.Empleado;
import com.cipa.nomina.empleado.EmpleadoAsalariado;
import com.cipa.nomina.empleado.EmpleadoPorComision;
import com.cipa.nomina.empleado.EmpleadoPorHoras;
import com.cipa.nomina.empleado.EmpleadoTemporal;
import com.cipa.nomina.excepcion.SalarioNetoNegativoException;
import com.cipa.nomina.recibo.ReciboNomina;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/** Pruebas del servicio: integran empleados + deducciones + beneficios. */
class ServicioNominaTest {

    private static final LocalDate HOY = LocalDate.of(2026, 10, 28);

    private ServicioNomina servicio;

    /** Doble de prueba: demuestra que el servicio acepta cualquier Deduccion (DIP/OCP). */
    private record DeduccionFijaFalsa(BigDecimal valor) implements Deduccion {
        @Override
        public String nombre() {
            return "Embargo";
        }

        @Override
        public BigDecimal calcular(BigDecimal salarioBruto) {
            return valor;
        }
    }

    private static BigDecimal bd(String v) {
        return new BigDecimal(v);
    }

    @BeforeEach
    void preparar() {
        servicio = ServicioNomina.conReglasPorDefecto();
    }

    @Test
    @DisplayName("asalariado con bono de antigüedad y alimentación")
    void asalariado() {
        Empleado emp = new EmpleadoAsalariado("Ana", "1", LocalDate.of(2020, 1, 1), bd("3000000"));
        ReciboNomina recibo = servicio.liquidar(emp, HOY);
        assertDinero("3300000.00", recibo.salarioBruto());
        assertDinero("149226.00", recibo.totalDeducciones()); // 132.000 + 17.226
        assertDinero("4150774.00", recibo.salarioNeto());
    }

    @Test
    @DisplayName("por horas con fondo de ahorro: el aporte no suma al neto")
    void porHorasConFondo() {
        Empleado emp = new EmpleadoPorHoras("Luis", "2", LocalDate.of(2024, 1, 1),
                bd("10000"), bd("40"), true);
        ReciboNomina recibo = servicio.liquidar(emp, HOY);
        assertDinero("8000.00", recibo.aportes().get("Fondo de ahorro"));
        assertDinero("381912.00", recibo.salarioNeto());
    }

    @Test
    @DisplayName("comisión con bono por ventas")
    void comision() {
        Empleado emp = new EmpleadoPorComision("Marta", "3", LocalDate.of(2022, 1, 1),
                bd("2000000"), bd("0.05"), bd("30000000"));
        ReciboNomina recibo = servicio.liquidar(emp, HOY);
        assertDinero("4400000.00", recibo.salarioBruto());
        assertDinero("5201032.00", recibo.salarioNeto());
    }

    @Test
    @DisplayName("temporal sin beneficios")
    void temporal() {
        Empleado emp = new EmpleadoTemporal("Carlos", "4", LocalDate.of(2026, 9, 1),
                bd("1500000"), LocalDate.of(2027, 2, 28));
        ReciboNomina recibo = servicio.liquidar(emp, HOY);
        assertTrue(recibo.beneficios().isEmpty());
        assertDinero("1432170.00", recibo.salarioNeto());
    }

    @Test
    @DisplayName("un salario neto negativo lanza SalarioNetoNegativoException")
    void netoNegativo() {
        ServicioNomina conEmbargo = new ServicioNomina(
                List.of(new DeduccionFijaFalsa(bd("2000000"))), List.of());
        Empleado emp = new EmpleadoTemporal("Carlos", "4", LocalDate.of(2026, 9, 1),
                bd("1500000"), LocalDate.of(2027, 2, 28));
        assertThrows(SalarioNetoNegativoException.class, () -> conEmbargo.liquidar(emp, HOY));
    }

    @Test
    @DisplayName("liquidarTodos devuelve un recibo por empleado")
    void liquidarTodos() {
        List<Empleado> empleados = List.of(
                new EmpleadoAsalariado("A", "1", LocalDate.of(2020, 1, 1), bd("1000000")),
                new EmpleadoTemporal("B", "2", LocalDate.of(2026, 9, 1), bd("1000000"),
                        LocalDate.of(2027, 1, 1)));
        assertEquals(2, servicio.liquidarTodos(empleados, HOY).size());
    }
}

# Sistema de Nómina (POO, Java) — CIPA · Actividad Unidad 3

Universidad de Cartagena · CTEV. Sistema de nómina en **Java 17** para una empresa con cuatro tipos de empleado, cada uno con su propia forma de calcular salario, beneficios y deducciones.

**Cómo ejecutar y tomar las capturas del documento → [INSTRUCCIONES_EJECUCION.md](INSTRUCCIONES_EJECUCION.md)** · Diagrama → [docs/DIAGRAMA_CLASES.md](docs/DIAGRAMA_CLASES.md) · Guion → [GUION_VIDEO.md](GUION_VIDEO.md)

```bash
mvn compile exec:java     # demo          (o ./ejecutar.sh / ejecutar.bat sin Maven)
mvn test                  # 34 pruebas JUnit 5 + cobertura JaCoCo
```

## Estructura (`src/main/java/com/cipa/nomina`)

```
config/      Constantes (reglas parametrizables, sin números mágicos)
excepcion/   NominaException, DatoInvalidoException, SalarioNetoNegativoException
util/        Dinero (redondeo BigDecimal), Validaciones
empleado/    Empleado (abstracta) + Asalariado, PorHoras, PorComision, Temporal, TipoEmpleado
deduccion/   Deduccion (interfaz) + DeduccionPorcentual, SeguroSocialPension, Arl
beneficio/   Beneficio (interfaz) + BonoAlimentacion, FondoAhorro
recibo/      ReciboNomina (record inmutable)
ServicioNomina.java   orquesta la liquidación
FormatoRecibo.java    formato de texto del recibo
Main.java             demostración
legacy/NominaV1.java  versión inicial procedural (solo evidencia de refactorización; no se compila)
```

## Principios SOLID aplicados

| Principio | Dónde se evidencia |
|---|---|
| **S** – Responsabilidad única | `ServicioNomina` solo liquida; `FormatoRecibo` solo formatea; `Validaciones` solo valida; cada empleado solo calcula su devengado. |
| **O** – Abierto/Cerrado | Un nuevo tipo de empleado, deducción o beneficio es una **nueva clase**; no se modifica código existente. |
| **L** – Sustitución de Liskov | Cualquier subclase de `Empleado` se usa en `ServicioNomina` sin saber cuál es (`calcularDevengado`). |
| **I** – Segregación de interfaces | `Deduccion` (2 métodos) y `Beneficio` (4) son interfaces pequeñas y específicas. |
| **D** – Inversión de dependencias | `ServicioNomina` recibe `List<Deduccion>` y `List<Beneficio>` por constructor y depende de abstracciones; las pruebas inyectan una deducción falsa. |

## Código limpio y refactorización

- **Antes** (`legacy/NominaV1.java`): un método con `if/else` por tipo, números mágicos, `double` para dinero, sin validaciones.
- **Después**: polimorfismo en lugar de condicionales, `BigDecimal`, constantes con nombre, validaciones y excepciones propias, métodos cortos, Javadoc y comentarios que explican el porqué.

## Metodología de desarrollo

**Scrum ligero + TDD + GitHub Flow**: reglas del enunciado → historias de usuario con criterios de aceptación; iteraciones cortas con tablero (Backlog → En progreso → Hecho); primero la prueba (incluidos casos límite) y luego el código mínimo, después refactorizar; rama `main` estable, ramas `feature/*`, *Pull Request* con revisión de otro integrante y commits `feat | test | refactor | docs | chore`.

## Supuestos (el enunciado no los especifica)

| Tema | Decisión |
|---|---|
| Tasa de ARL | 0,522 % (riesgo clase I). Configurable en `Constantes`. |
| Salario bruto | Todo el devengado (base, horas, comisiones y bonos del tipo). Bono de alimentación y fondo **no** son parte del bruto. |
| Bono de alimentación | Se suma al neto del empleado (la empresa lo cubre). |
| Fondo de ahorro (2 %) | Aporte de la empresa: aparece en el recibo, **no** modifica el neto. |
| "Más de N años" | Estrictamente mayor: con exactamente 5 años no hay bono. |
| 40 horas | Umbral por periodo de liquidación (mensual en este modelo). |

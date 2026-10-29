# Diagrama de clases

GitHub renderiza este diagrama automáticamente. Para exportarlo como **imagen PNG** (para el documento):
abre https://mermaid.live → pega el bloque de código → botón **Actions → PNG** → guarda en `capturas/diagrama_clases.png`.

```mermaid
classDiagram
    direction LR
    class Empleado {
        <<abstract>>
        -String nombre
        -String documento
        -LocalDate fechaIngreso
        +getTipo() TipoEmpleado
        +calcularDevengado(fecha) Map
        +aceptaFondoAhorro() boolean
        +superaAntiguedad(anios, fecha) boolean
    }
    Empleado <|-- EmpleadoAsalariado
    Empleado <|-- EmpleadoPorHoras
    Empleado <|-- EmpleadoPorComision
    Empleado <|-- EmpleadoTemporal

    class Deduccion {
        <<interface>>
        +nombre() String
        +calcular(salarioBruto) BigDecimal
    }
    Deduccion <|.. DeduccionPorcentual
    DeduccionPorcentual <|-- SeguroSocialPension
    DeduccionPorcentual <|-- Arl

    class Beneficio {
        <<interface>>
        +nombre() String
        +aplica(empleado, fecha) boolean
        +calcular(salarioBruto) BigDecimal
        +sePagaAlEmpleado() boolean
    }
    Beneficio <|.. BonoAlimentacion
    Beneficio <|.. FondoAhorro

    class ServicioNomina {
        -List~Deduccion~ deducciones
        -List~Beneficio~ beneficios
        +liquidar(empleado, fecha) ReciboNomina
        +liquidarTodos(empleados, fecha) List
    }
    ServicioNomina ..> Empleado
    ServicioNomina o-- Deduccion
    ServicioNomina o-- Beneficio
    ServicioNomina ..> ReciboNomina : crea

    class ReciboNomina {
        <<record>>
        salarioBruto
        salarioNeto
    }
```

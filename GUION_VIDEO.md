# Guion del video (YouTube) — Sistema de Nómina POO · CIPA

**Duración sugerida:** 10–12 min · **Integrantes:** 5 (se sugiere un bloque por persona; reasignen si prefieren).
**Antes de grabar:** abrir en pantalla el IDE, el repositorio en GitHub y una terminal en la carpeta del proyecto. Subir el video a YouTube como *No listado* o *Público* y pegar el enlace en la entrega.

---

## Bloque 1 · Introducción (0:00–1:00) — Integrante 1
**Pantalla:** diapositiva/portada con nombre de la actividad, universidad y nombres del grupo.
> "Buenas, somos el grupo CIPA de la Universidad de Cartagena. Presentamos la Actividad de la Unidad 3: un sistema de nómina con programación orientada a objetos, aplicando principios SOLID, código limpio, refactorización, pruebas unitarias y control de versiones en GitHub. Nuestro tutor es Vladimir Roa Pérez."

## Bloque 2 · El problema y las reglas de negocio (1:00–2:30) — Integrante 1
**Pantalla:** PDF del enunciado o tabla resumen.
> "La empresa maneja cuatro tipos de empleado y cada uno calcula distinto su pago:
> - **Asalariado:** salario fijo y bono del 10 % si lleva más de 5 años.
> - **Por horas:** paga por hora; sobre 40 horas se paga a 1,5 veces; sin bonos.
> - **Por comisión:** salario base más comisión; si vende más de 20 millones, bono del 3 % sobre ventas.
> - **Temporal:** salario fijo, contrato definido, sin bonos ni beneficios.
>
> Todos tienen deducciones de seguro social y pensión del 4 % y ARL. Los permanentes reciben bono de alimentación de un millón, y los de horas con más de un año pueden acceder al fondo de ahorro del 2 %. Además hay validaciones: sin neto negativo, sin horas negativas y sin ventas negativas."

## Bloque 3 · Metodología de desarrollo (2:30–4:00) — Integrante 2
**Pantalla:** sección "Metodología" del README + tablero de tareas (Trello/GitHub Projects).
> "Usamos **Scrum ligero combinado con TDD y GitHub Flow**. Convertimos las reglas en historias de usuario con criterios de aceptación, trabajamos en iteraciones cortas con un tablero Backlog–En progreso–Hecho y reuniones de seguimiento.
> Para cada regla primero escribimos la prueba, incluidos los casos límite, luego el código mínimo y después refactorizamos. En GitHub, `main` siempre estable, una rama por tarea, *pull request* con revisión de otro compañero y commits con convención feat, test, refactor, docs."

## Bloque 4 · Arquitectura (4:00–5:30) — Integrante 3
**Pantalla:** árbol de carpetas en el IDE y diagrama de clases (dibujarlo en draw.io: `Empleado` ← 4 subclases; `Deduccion` ← 2; `Beneficio` ← 2; `ServicioNomina` usa las abstracciones).
> "El paquete `nomina` separa responsabilidades: `empleado` con la clase abstracta `Empleado` y sus cuatro subclases; `deduccion` y `beneficio` como estrategias; `ServicioNomina` que orquesta; `recibo` que guarda el resultado de forma inmutable; y `presentacion` que solo formatea. Las reglas numéricas viven en `Constantes.java`."

## Bloque 5 · Principios SOLID con código (5:30–7:30) — Integrante 3 y 4
**Pantalla:** mostrar cada archivo mientras se explica.
> **S:** "`ServicioNomina` solo liquida; formatear está en `FormatoRecibo`, validar en `Validaciones`."
> **O:** "Si mañana hay un empleado 'Practicante', creamos una subclase de `Empleado`. No tocamos el servicio." *(mostrar `EmpleadoTemporal` como ejemplo de lo corta que es una subclase)*
> **L:** "El servicio llama `calcularDevengado` sobre cualquier `Empleado` sin saber cuál es."
> **I:** "`Deduccion` y `Beneficio` son interfaces pequeñas: nombre y uno o dos métodos."
> **D:** "`ServicioNomina` recibe por constructor las listas de deducciones y beneficios; depende de abstracciones. En las pruebas inyectamos una deducción falsa para forzar un neto negativo."

## Bloque 6 · Código limpio y refactorización (7:30–8:45) — Integrante 4
**Pantalla:** `legacy/NominaV1.java` al lado de `empleado/`; luego `git log`.
> "Nuestra primera versión era una sola función con `if/else` por tipo, números mágicos como 0.04 y 1000000, `double` para dinero y cero validaciones. Refactorizamos así: polimorfismo en lugar de condicionales, constantes con nombre, `BigDecimal` para dinero, excepciones propias, métodos cortos y Javadoc que explican el porqué. El commit de la versión 1 sigue en el historial como evidencia."

## Bloque 7 · Demostración (8:45–9:45) — Integrante 5
**Pantalla:** terminal.
```bash
mvn compile exec:java
```
> "Aquí liquidamos un empleado de cada tipo. La asalariada con más de 5 años tiene bruto de 3.300.000, deducciones de 149.226, bono de alimentación y neto de 4.150.774. El empleado por horas con 45 horas muestra las extras a 1,5 y el aporte al fondo de ahorro que no suma al neto. La de comisión supera los 20 millones y recibe el bono del 3 %. El temporal solo tiene salario y deducciones."

## Bloque 8 · Pruebas unitarias (9:45–10:45) — Integrante 5
**Pantalla:** terminal + `EmpleadosTest.java`.
```bash
mvn test
```
> "Tenemos 34 pruebas con JUnit 5, y el reporte de cobertura de JaCoCo. Cubrimos los casos límite: exactamente 5 años no da bono y un día más sí; ventas de exactamente 20 millones no dan bono; 40 horas no tienen extras. También probamos las validaciones: horas, ventas o salarios negativos, y el neto negativo lanza `SalarioNetoNegativoException`. Todas pasan."

## Bloque 9 · Control de versiones en GitHub (10:45–11:30) — Integrante 2
**Pantalla:** repositorio en GitHub → pestaña *Commits*, *Branches* y *Pull requests*.
> "Aquí está el repositorio con el historial: cada commit corresponde a una funcionalidad. Se ve la versión 1, la jerarquía de empleados, las estrategias, el servicio, las pruebas y la documentación. Cada integrante aportó mediante ramas y pull requests."

## Bloque 10 · Supuestos y cierre (11:30–12:00) — Integrante 1
**Pantalla:** tabla de supuestos del README.
> "Como el enunciado no lo especifica, asumimos ARL de 0,522 % configurable, que el fondo de ahorro es aporte de la empresa y no modifica el neto, y que 'más de N años' es estrictamente mayor. Todo está documentado y es fácil de cambiar en `Constantes.java`. Gracias por su atención."

---

## Lista de verificación antes de entregar
- [ ] Subir el proyecto a GitHub (ver comandos abajo) y que **cada integrante haga al menos un commit/PR** con su usuario.
- [ ] Generar el diagrama de clases (`docs/DIAGRAMA_CLASES.md`) y tomar las capturas del documento (`INSTRUCCIONES_EJECUCION.md`, sección 6).
- [ ] Grabar el video (pantalla + voz de los 5), subirlo a YouTube y copiar el enlace.
- [ ] Entregar: enlace de GitHub + enlace de YouTube.

```bash
# Subir a GitHub (crear antes un repositorio vacío en github.com)
git remote add origin https://github.com/<usuario>/sistema-nomina.git
git push -u origin main

# Flujo de trabajo para los demás integrantes
git checkout -b feature/<tarea>
git add . && git commit -m "feat: descripcion"
git push -u origin feature/<tarea>     # luego abrir Pull Request
```

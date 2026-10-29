# Cómo ejecutar el proyecto y obtener las capturas para el documento

## 1. Requisitos (instalar una sola vez)

| Herramienta | Versión | Para qué | Verificar |
|---|---|---|---|
| JDK | 17 o superior | compilar y ejecutar | `java -version` y `javac -version` |
| Maven | 3.8+ (opcional si usan IDE) | pruebas y reporte de cobertura | `mvn -v` |
| Git | cualquiera | control de versiones | `git --version` |
| IDE (recomendado) | IntelliJ IDEA Community, Eclipse o VS Code + *Extension Pack for Java* | capturas más vistosas | — |

## 2. Abrir el proyecto

- **IntelliJ:** *File → Open* → seleccionar la carpeta `nomina-java` (detecta el `pom.xml` solo).
- **Eclipse:** *File → Import → Existing Maven Projects*.
- **VS Code:** *File → Open Folder* → esperar a que cargue "Java Projects".

## 3. Ejecutar la demostración (3 formas, use la que tenga)

**A. Maven**
```bash
mvn compile exec:java
```

**B. Sin Maven (solo JDK)**
```bash
./ejecutar.sh        # Linux / macOS / Git Bash
ejecutar.bat         # Windows (CMD)
```

**C. Desde el IDE:** abrir `src/main/java/com/cipa/nomina/Main.java` → botón ▶ **Run** junto a `main`.

> **Tildes mal en Windows:** ejecute `chcp 65001` en la consola antes de correr el programa (el `.bat` ya lo hace), o use el IDE.

## 4. Ejecutar las pruebas unitarias

```bash
mvn test
```
Debe terminar con `Tests run: 34, Failures: 0, Errors: 0` y `BUILD SUCCESS`.
En el IDE: clic derecho sobre la carpeta `src/test/java` → **Run Tests** (verá el árbol verde con los nombres legibles).

## 5. Reporte de cobertura (imagen muy presentable)

```bash
mvn test
```
Abrir en el navegador `target/site/jacoco/index.html`. Muestra barras verdes/rojas por paquete y clase.

## 6. Capturas que debe tomar para el documento

Atajos: **Windows** `Win + Shift + S` · **macOS** `Cmd + Shift + 4` · **Linux** `Impr Pant`. Guarde cada imagen en la carpeta `capturas/` con el nombre indicado y ábrala con el documento (Word/Docs: *Insertar → Imagen*).

| # | Qué mostrar | Cómo obtenerla | Archivo sugerido |
|---|---|---|---|
| 1 | Estructura del proyecto | Panel *Project/Explorer* del IDE con los paquetes desplegados | `01_estructura.png` |
| 2 | Diagrama de clases | Ver `docs/DIAGRAMA_CLASES.md` (mermaid.live → PNG) | `02_diagrama_clases.png` |
| 3 | Salida del programa (4 recibos) | Paso 3; haga scroll y capture los 4 recibos | `03_ejecucion.png` |
| 4 | Pruebas unitarias en verde | Paso 4 (`mvn test` o panel de tests del IDE) | `04_pruebas.png` |
| 5 | Cobertura JaCoCo | Paso 5 | `05_cobertura.png` |
| 6 | Código con SOLID | `ServicioNomina.java` (inyección de `Deduccion`/`Beneficio`) y `EmpleadoTemporal.java` | `06_solid.png` |
| 7 | Antes y después de refactorizar | `legacy/NominaV1.java` vs `EmpleadoPorHoras.java` lado a lado | `07_refactor.png` |
| 8 | Historial de commits | `git log --oneline --graph` o pestaña *Commits* en GitHub | `08_commits.png` |
| 9 | Repositorio en GitHub | Página principal del repo con el README | `09_github.png` |
| 10 | Caso de error (validación) | Opcional: en `Main` cambie horas a `-5`, ejecute y capture la excepción; luego revierta | `10_validacion.png` |

**Consejos para que se vea presentable:** tema claro del IDE, fuente tamaño 14+, recorte solo la parte útil, y numere cada figura con pie de foto ("Figura 3. Liquidación de los cuatro tipos de empleado").

## 7. Subir a GitHub (evidencia de control de versiones)

```bash
# 1) Crear un repositorio VACÍO en github.com (sin README)
git remote add origin https://github.com/<usuario>/sistema-nomina.git
git push -u origin main
```
Cada integrante debe trabajar en su rama y abrir un Pull Request:
```bash
git checkout -b feature/<tarea>
git add . && git commit -m "feat: descripcion corta"
git push -u origin feature/<tarea>      # luego abrir Pull Request en GitHub
```

## 8. Estructura del documento sugerida (con las imágenes)

1. Portada e introducción · 2. Reglas de negocio · 3. Metodología (Scrum ligero + TDD + GitHub Flow) · 4. Diseño: Figura 1 estructura, Figura 2 diagrama de clases · 5. SOLID con código (Figura 6) · 6. Refactorización (Figura 7) · 7. Resultados: Figura 3 ejecución · 8. Pruebas: Figuras 4 y 5 · 9. Control de versiones: Figuras 8 y 9 · 10. Conclusiones y enlace al video de YouTube.

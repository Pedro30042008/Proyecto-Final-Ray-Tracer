# Declaración de uso de Inteligencia Artificial

**Actividad:** Proyecto final de Modelado y Programación: Ray Tracer  
**Integrantes:**
- Alan Gael Alvarez Hernández
- María Fernanda Miranda Sánchez
- Pedro Pablo Ruiz Hernández

**Fecha:** 27 de noviembre de 2026


> Esta declaración es obligatoria en toda tarea, práctica y en el proyecto final.
> **No da ni quita puntos**: es un reporte adicional que se revisa aparte y se
> devuelve con comentarios. **No se evalúa cuánta IA usaron**, sino si pueden
> dar cuenta de lo que entregaron.

---

## 0. ¿Se usó IA en esta actividad?

- [ ] **No.** Firmen la última sección y terminen. Con eso queda completa.
- [x] **Sí.** Contesten los cinco puntos.

---

## 1. Herramienta(s) utilizada(s)

 Herramienta | Versión o modelo, si lo saben |
|---|---|
| ChatGPT (OpenAI) | GPT-6 (en esta conversación) |


> Cuenta también el autocompletado del editor (Copilot y similares). Si sólo
> fue eso, basta con decirlo en un renglón y seguir al punto 4.

---

## 2. Objetivo del uso

Utilizamos ChatGPT **principalmente como apoyo para comprender la teoría y discutir decisiones de implementación.**

 Para qué la usamos | En qué parte del entregable |
|---|---|
| Comprender la geometría de la cámara: vectores de orientación, viewport, desplazamientos horizontales y verticales, y generación de un rayo por píxel. | `Camara.java` |
| Entender la iluminación ambiental y la iluminación difusa de Lambert, el producto punto entre la normal y la dirección de la luz y la suma de contribuciones. | `Renderizador.java` y `LuzPuntual.java` |
| Revisar código escrito durante el desarrollo, detectar casos límite y discutir cómo separar responsabilidades entre métodos. | `Renderizador.java`, especialmente `calcularColor()` y `calcularIluminacion()` |
| Entender la concurrencia por filas, los intervalos semiabiertos, la matriz compartida y los riesgos de modificar datos compartidos. | `Renderizador.java`, `RenderizadorConcurrente.java` y revisión de `Plano.java` |
| Comparar alternativas de organización del código y decidir en equipo dónde debían hacerse las validaciones. | Diseño del renderizado secuencial y concurrente |

---

## 3. Prompts principales

Textuales, tal como los escribieron. No los limpien ni los reescriban.

```
Ayúdame a entender la cámara mejor. Que es ai y bj, y porque para Pi,j se le suma el vector P?
```

```
No puedo solo pararme en una esquina y empezar a movernos desde ahí 1 unidad? Porque necesitaría calcular los vectores w, u y v cada vez?
```

```
Listo, entonces ya quedo lambert? No crees que deberia de ir en un metodo seperado? como que se mezcla todo en color y no me gusta mucho.
```

```
oye y estas validaciones es necesario que se hagan en renderizar filas? porque cada vez que se llame al metodo se estara comprobando si tiene la anchura no? no deberia hacerse solo una vez? Justo las validaciones irian en otro lado
```

```
me puedes dar las diferentes opciones de diseño para comentarle a mi amigo?
```

```
Hola, ¿conoces el curso de Ray Tracer In One Weekend? quiero que me expliques todo el contenido que viene en el mismo, si quieres por capítulos porfas.
```

```
Oye w mi idea es que recorra caracter por caracter el JSON y ya depues vamos extrayenfo.
```

```
w, es que hice esto (codigo de escritorPPM.java) pero me marca error por que los arreglos son null, pero antes de crearlo no lo son
```
---

## 4. Cómo se incorporó o se descartó lo obtenido

**Éste es el punto que más pesa.**

Qué produjo la herramienta | ¿Aceptado, modificado o descartado? | Por qué | Dónde quedó (archivo / commit) |
|---|---|---|---|
| Explicaciones de cómo obtener el centro de cada píxel a partir del centro del viewport o de su esquina superior izquierda. | **Aceptado como explicación; contrastado con nuestras fórmulas.** | Nos ayudó a entender que ambas representaciones pueden describir los mismos rayos y que los vectores `u`, `v` y `w` fijan la orientación de la cámara. | `Camara.java` |
| Explicación de Lambert y ejemplos incrementales para calcular las contribuciones de las luces. | **Aceptado y adaptado.** | Fuimos implementando y revisando por partes los cálculos de dirección hacia la luz, normalización, producto punto, iluminación ambiental y límite RGB. | `Renderizador.java`, `LuzPuntual.java` |
| Propuesta de extraer la iluminación de `calcularColor()` a un método separado. | **Aceptado.** | Consideramos que `calcularColor()` estaba acumulando responsabilidades y sería más difícil agregar sombras sin separar la iluminación. | `Renderizador.java`: `calcularIluminacion()` |
| Advertencia sobre el cambio de la normal almacenada en el plano durante las intersecciones. | **Revisado y corregido por el equipo.** | Modificar una normal compartida puede hacer que el resultado dependa del orden de ejecución de los hilos. Se optó por trabajar con una normal local a la intersección. | `Plano.java` |
| Propuesta inicial de comprobar el ancho de todas las filas dentro de cada llamada a `renderizarFilas()`. | **Descartado.** | Repetir una validación completa en cada trabajador no era necesario si la matriz se preparaba y verificaba antes de iniciar los hilos. | Decisión de diseño en `Renderizador.java` y `RenderizadorConcurrente.java` |
| Alternativas para coordinar la concurrencia y fragmentos de código para ajustar el renderizado por filas. | **Modificado y adoptado parcialmente.** | Elegimos separar la coordinación de los hilos del cálculo de píxeles; también solicitamos una corrección puntual del código conservando la lógica y los comentarios existentes. | `Renderizador.java`, `RenderizadorConcurrente.java` |

### 4.1 Si la usaron para el análisis: el contraste

Se les pidió intentarlo primero por su cuenta. Si lo hicieron así:

| | |
|---|---|
| ¿Qué encontró la herramienta que ustedes **no** habían notado? | Ayudó a identificar riesgos en los límites de los intervalos: `filaFin` es exclusivo, por lo que acceder a `pixeles[filaFin]` puede provocar un error cuando coincide con la altura. También señaló la importancia de que las normales utilizadas para Lambert sean unitarias y de no modificar la normal compartida de un plano durante una intersección. |
| ¿Qué **no** encontró, que en este curso sí consideramos un problema? | Inicialmente propuso comprobar todas las filas de la matriz dentro de cada llamada a `renderizarFilas()`. Cuestionamos esa duplicación porque varios trabajadores repetirían la misma comprobación; a partir de ello discutimos la encapsulación y decidimos validar la matriz desde el coordinador, antes de ejecutar los hilos. |

### 4.2 ¿Cómo verificaron que lo que incorporaron era correcto?

Compilamos el proyecto y ejecutamos escenas JSON para generar archivos PPM. Primero comprobamos que la cámara, las intersecciones y la escritura de la imagen permitieran obtener una escena con colores planos. Después generamos `salida_lambert.ppm` y contrastamos visualmente el resultado: las esferas mostraron variaciones de iluminación según la orientación de sus superficies y la posición de las luces, mientras que el fondo conservó su color. También revisamos las fórmulas de la cámara y de Lambert al implementar los cálculos.

---

## 5. Reflexión

### 5.1 ¿Qué limitación concreta observaron **en esta actividad**?

La herramienta proporcionó inicialmente una solución funcional que repetía la validación del ancho de todas las filas de la matriz en cada llamada al método de renderizado por filas. Aunque eso podía detectar matrices mal formadas, no consideraba suficientemente que los trabajadores compartirían una matriz preparada previamente. Tuvimos que cuestionar esa propuesta y discutir una alternativa que validara una sola vez sin dejar de controlar los datos de entrada.


### 5.2 ¿Qué harían distinto la próxima vez?

Definiríamos antes las responsabilidades y los contratos de cada clase, especialmente quién construye y valida los datos compartidos y quién administra los hilos. Seguiríamos usando la IA para aclarar conceptos y contrastar ideas propias, pero acompañaríamos cada modificación con pruebas específicas.

---

## Firma

> Declaramos que entendemos y podemos explicar todo lo que entregamos,
> independientemente de las herramientas que hayamos utilizado.

| Nombre | Número de cuenta |
|---|---|
|Alan Gael Alvarez Hernández | 323113340
|María Fernanda Miranda Sánchez | 420102320
|Pedro Pablo Ruiz Hernández | 323198507
|

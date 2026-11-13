
# Proyecto Final: Ray Tracer

Proyecto final de Modelado y Programación.

Facultad de Ciencias, UNAM.

## Descripción

Implementación de un Ray Tracer en Java capaz de generar
imágenes de escenas tridimensionales mediante el trazado
de rayos.

El programa permitirá cargar escenas desde archivos JSON,
calcular intersecciones, iluminación y sombras, y distribuir
el renderizado entre múltiples hilos.

Ejecución provisional

Durante la primera etapa, el programa permite recibir argumentos desde la terminal, leer un archivo JSON y validar su contenido.

Primero, compilar y ejecutar las pruebas:

mvn clean test

Después, generar el archivo con las rutas de las dependencias:

mvn dependency:build-classpath -Dmdep.outputFile=classpath.txt

Finalmente, ejecutar:

java -cp "target/classes:$(cat classpath.txt)" Main \
src/test/resources/escenas/prueba_dimensiones_IMPL-COMPLETAcorr.json \
salida.ppm \
--trabajadores 1
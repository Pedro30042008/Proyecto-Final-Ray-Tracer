
# Proyecto Final: Ray Tracer
V2

Proyecto final de Modelado y Programación.

Facultad de Ciencias, UNAM.

## Descripción

Implementación de un Ray Tracer en Java capaz de generar
imágenes de escenas tridimensionales mediante el trazado
de rayos.

El programa permitirá cargar escenas desde archivos JSON,
calcular intersecciones, iluminación y sombras, y distribuir
el renderizado entre múltiples hilos.

Compilacion y ejecucion
mvn clean package

java -jar raytracer.jar escena.json salida.ppm [--trabajadores N]


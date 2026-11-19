package io;

import matematicas.VectorR3;

/**
 * Genera una imagen de prueba para comprobar
 * visualmente el funcionamiento de EscritorPPM.
 */
public class PruebaVisualPPM {

    public static void main(String[] args) throws Exception {

        // Dimensiones de nuestra imagen.
        int ancho = 256;
        int alto = 256;

        // Creamos una matriz de 256 filas y 256 columnas.
        VectorR3[][] pixeles = new VectorR3[alto][ancho];

        // Recorremos los píxeles de la imagen.
        /*Queremos crear un degradado de colores
        rojo aumenta hacia la derecha y azul aumenta hacia abajo y sin el verde*/
        for (int fila = 0; fila < alto; fila++) {
            for (int columna = 0; columna < ancho; columna++) {

                /* Rojo aumenta de izquierda a derecha.
                Y por eso dividimos entre 255 para que en 0 no haya nada de rojo
                y en 255 haya todo rojo, normalizando*/
                double rojo = columna / 255.0;

                /*  Azul aumenta de arriba hacia abajo.
                Igual dividimos como en el rojo*/
                double azul = fila / 255.0;

                /*  Verde permanece en cero.
                Y creamos vector con los colores*/

                pixeles[fila][columna] =
                    new VectorR3(rojo, 0, azul);
            }
        }

        // Escribimos el archivo PPM P3.
        EscritorPPM.escribir("prueba_visual.ppm", pixeles);

        System.out.println("Imagen PPM generada correctamente.");
    }
}

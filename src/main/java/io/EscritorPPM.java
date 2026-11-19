package io;

import matematicas.VectorR3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Escribe imágenes en formato PPM P3.
 */
public class EscritorPPM {

    /**
     * Escribe una imagen a partir de una matriz de colores RGB.
     *
     * @param ruta Ruta del archivo de salida.
     * @param pixeles Colores organizados por fila y columna.
     */
    public static void escribir(String ruta, VectorR3[][] pixeles) throws IOException {

        /* Checamos que tengamos al menos una fila.*/
        if (pixeles == null || pixeles.length == 0) {
            throw new IllegalArgumentException("La imagen debe contener al menos una fila.");
        }
        /*Recuperamos el alto.*/
        int alto = pixeles.length;
        
        /*Revisamos que tengamos al menos una columna*/
        if (pixeles[0] == null || pixeles[0].length == 0) {
            throw new IllegalArgumentException("La imagen debe contener al menos una columna.");
        }

        /*Recuperamos el ancho */
        int ancho = pixeles[0].length;

        /*Debemos de checar (como extremo) que las filas tengan el mismo ancho
            y que los colores esten entre 0 y 1 para despues convertirlos*/
        for (VectorR3[] fila : pixeles) {
            /*Filas con mismo ancho */
            if (fila == null || fila.length != ancho) {
                throw new IllegalArgumentException("Todas las filas deben tener el mismo ancho.");
            }
            /*Pixeles validos != null */
            for (VectorR3 color : fila) {
                if (color == null) {
                    throw new IllegalArgumentException("Los píxeles no pueden ser null.");
                }

                /*Checamos que el color este entre 0 y 1 */
                boolean colorValido = comprobadorColorInvalido(color);
                if (colorValido) {
                    throw new IllegalArgumentException("Los colores deben estar entre 0 y 1.");
                }
            }
        }/*Terminamos comprobaciones */

        /*Abrimos el archivo para escribir.
         Path.of(ruta) = COnvierte el string con la ruta a un objeto path que es la ubicacion/direccion del archivo
         StandardCharsets.US_ASCII es que ocuparemos la codificacion estandar (letras sin acent, 0-9, etc)*/
        try (BufferedWriter escritor = Files.newBufferedWriter(Path.of(ruta), StandardCharsets.US_ASCII)) {

            /* Encabezado PPM.*/
            escritor.write("P3");
            escritor.newLine();
            /*Escribimos ancho y alto */
            escritor.write(ancho + " " + alto);
            escritor.newLine();
            /*Color maximo permitido por cada componente */
            escritor.write("255");
            escritor.newLine();

            /*Recorreremos la matriz y escribiremos fila por fila */
            for (int fila = 0; fila < alto; fila++) {
                for (int columna = 0; columna < ancho; columna++) {
                    /*Obtenemos color */
                    VectorR3 color = pixeles[fila][columna];
                    /*Obtenemos cada color con la componente, multiplicando por 255 para 
                        cambiar la escala a 0 - 256 */
                    int rojo = (int) Math.round(color.getX() * 255);
                    int verde = (int) Math.round(color.getY() * 255);
                    int azul = (int) Math.round(color.getZ() * 255);

                    /*Escribimos los colores del i-esimo pixel */
                    escritor.write(rojo + " " + verde + " " + azul);
                    /*Siguiente linea */
                    escritor.newLine();
                }//termina la columa i-esima
            }//Termina la fila, termina la matriz
        }//fin try
    }

    /** 
     * Comprueba que un color cumpla con estar entre 0 y 1.
     * 
     * @param color vector que almacena el color.
     * @return True -> Color invalido, False -> Colo valido
     */
    private static boolean comprobadorColorInvalido(VectorR3 color){
        /* Comprobamos cada componente*/
        return color.getX() < 0 || color.getX() > 1 || color.getY() < 0 || color.getY() > 1 || color.getZ() < 0 || color.getZ() > 1 ;
    }

}

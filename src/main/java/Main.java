
import java.io.IOException;

import escena.Escena;
import io.LectorEscenaJson;

/**
 * Punto de entrada del Ray Tracer.
 *
 * Recibe los argumentos de ejecución, valida su formato
 * y coordina la lectura de la escena.
 * !!NO TERMINADO!! CONSIDERANDO ORDEN DE EJEMPLO, java -jar raytracer.jar escena.json salida.ppm --trabajadores N
 */
public class Main {

    /**
     * Ejecuta el programa desde la terminal.
     * V = 1
     * @param cositas Argumentos de ejecución.
     */
    public static void main(String[] cositas) {

        /*  Verificamos que se reciban los cuatro argumentos.*/
        if (cositas.length != 4) {
            System.err.println("Uso: java -jar raytracer.jar escena.json salida.ppm --trabajadores N");
            return;
        }

        /*  Obtenemos rutas de entrada y salida.*/
        String rutaEntrada = cositas[0];
        String rutaSalida = cositas[1];

        /* Comprobamos la 3ra posicion sea de trabajadores.*/
        if (!cositas[2].equals("--trabajadores")) {
            System.err.println("Error: se esperaba la opción --trabajadores.");
            return;
        }

        /* Convertimos el num de trabajadores.*/
        int trabajadores;

        /*Intentamos pasar los trabajadores a enteros */
        try {
            trabajadores = Integer.parseInt(cositas[3]);
        } catch (NumberFormatException e) {
            System.err.println("Error: el número de trabajadores debe ser un entero.");
            return;
        }

        /*VAlidamos minimo un trabajador */
        if (trabajadores <= 0) {
            System.err.println("Error: debe indicarse al menos un trabajador.");
            return;
        }

        /*Intentamos leer escena */
        try {
            Escena escena = LectorEscenaJson.leer(rutaEntrada);

            System.out.println("Escena leída correctamente.");
            System.out.println("Dimensiones: " + escena.getAncho() + " x " + escena.getAlto());
            System.out.println("Archivo de salida: " + rutaSalida);
            System.out.println("Trabajadores: " + trabajadores);

            //RENDERIZADO Y ESCRIBITPPM//

        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error al leer la escena: " + e.getMessage());
        }

        
    }//FIN MAIN
}//FIN CLASS

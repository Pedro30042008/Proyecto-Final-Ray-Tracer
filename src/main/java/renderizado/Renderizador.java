
package renderizado;

import escena.Escena;
import camara.Camara;
import matematicas.Rayo;
import matematicas.VectorR3;
import geometria.Objeto3D;
import geometria.Interseccion;

/**
 * Calcula los colores de los píxeles de una escena
 * utilizando los rayos generados por la cámara.
 */
public class Renderizador {

    /* Escena que queremos renderizar. */
    private final Escena escena;

    /**
     * Construye un renderizador para una escena.
     *
     * @param escena escena que será renderizada.
     */
    public Renderizador(Escena escena) {

        /* Verificar que la escena no sea null. */
        if(escena == null){
            throw new IllegalArgumentException(
                    "La escena no puede estar vacía.");
        }

        /* Guardar la escena. */
        this.escena = escena;
    }

    /**
     * Calcula el color correspondiente a un rayo.
     *
     * @param rayo rayo que queremos evaluar.
     * @return color RGB correspondiente al rayo.
     */
    public VectorR3 calcularColor(Rayo rayo) {

        /* Validar que el rayo no sea null. */
        if (rayo == null) {
            throw new IllegalArgumentException(
                    "El rayo no puede ser null.");
        }

        /* Intersección más cercana encontrada hasta ahora. */
        Interseccion interseccionCercana = null;

        /* Objeto al que pertenece esa intersección. */
        Objeto3D objetoCercano = null;

        /* Menor distancia paramétrica encontrada. */
        double tMinimo = Double.POSITIVE_INFINITY;

        /* Buscar la intersección más cercana */
        for(Objeto3D objeto : escena.getObjetos()){

            /* Calcular si el rayo intersecta al objeto*/
            Interseccion interseccion = objeto.intersectar(rayo);

            /* Evaluar que la intersección no sea nula y que su T sea menor que tMin*/
            if(interseccion != null && interseccion.getT() < tMinimo){
                interseccionCercana = interseccion;
                objetoCercano = objeto;
                tMinimo = interseccion.getT();
            }
        }


        /* Si no hubo intersección, devolver el color de fondo. */
        if (interseccionCercana == null) {
            return escena.getFondo();
        }

        /* Si hubo intersección, devolver el color del objeto. */
        return objetoCercano.getColor();
    }

    /**
     * Calcula los colores de todos los píxeles de la imagen.
     *
     * @return matriz de colores RGB organizada por filas y columnas.
     */
    public VectorR3[][] renderizar() {

        /* Obtener la cámara de la escena. */
        Camara camara = escena.getCamara();

        /* Obtener las dimensiones de la imagen. */
        int ancho = escena.getAncho();
        int alto = escena.getAlto();

        /* Crear la matriz de píxeles. */
        VectorR3[][] pixeles = new VectorR3[alto][ancho];

        /* Recorrer todas las filas. */
        for(int fila = 0; fila < alto; fila++){
            /* Recorrer todas las columnas. */
            for(int columna = 0; columna < ancho; columna++){

                /* Generar el rayo correspondiente. */
                Rayo rayo = camara.generarRayo(fila, columna);

                /* Calcular el color del rayo. */
                VectorR3 color = calcularColor(rayo);

                /* Guardar el color en la matriz. */
                pixeles[fila][columna] = color;
            }
        }
        /* Devolver la matriz completa. */
        return pixeles;
    }
}

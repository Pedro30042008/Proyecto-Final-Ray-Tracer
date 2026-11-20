
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
     * @return matriz de colores organizada por filas y columnas.
     */
    public VectorR3[][] renderizar() {

        // PASO 6: Crear la matriz de píxeles.

        // PASO 7: Recorrer filas y columnas.

        // PASO 8: Generar un rayo para cada píxel.

        // PASO 9: Calcular y guardar su color.

        throw new UnsupportedOperationException(
                "Pendiente de implementar");
    }
}

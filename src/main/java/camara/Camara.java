package camara;

import matematicas.VectorR3;
import matematicas.Rayo;

/**
 * Representa una cámara virtual encargada de generar
 * los rayos que atraviesan los píxeles de la imagen.
 */
public class Camara {

    /* Datos de la cámara. */
    private final VectorR3 posicion;
    private final VectorR3 miraA;
    private final double fov;

    /* Dimensiones de la imagen en píxeles. */
    private final int ancho;
    private final int alto;

    /* Vectores que definen la orientación. */
    private final VectorR3 u; // Derecha
    private final VectorR3 v; // Arriba
    private final VectorR3 w; // Atrás

    /* Dimensiones del viewport. */
    private final double anchoViewport;
    private final double altoViewport;

    /**
     * Construye una cámara con su posición, orientación,
     * campo de visión y dimensiones de imagen.
     */
    public Camara(VectorR3 posicion, VectorR3 miraA,
                  double fov, int ancho, int alto) {

        /* Validamos que los vectores no sean nulos. */
        if (posicion == null || miraA == null) {
            throw new IllegalArgumentException(
                    "Los vectores posicion y miraA no pueden ser null.");
        }

        /* Validamos que la dirección no sea el vector 0.
        * D = miraA - posicion
        * */
        if (miraA.resta(posicion).esCero()) {
            throw new IllegalArgumentException(
                    "La cámara debe mirar hacia un punto distinto.");
        }
        /* Validamos que el FOV sea finito y que esté entre 0 y 180 grados */
        if (!Double.isFinite(fov) || fov <= 0 || fov >= 180) {
            throw new IllegalArgumentException(
                    "El FOV debe estar entre 0 y 180 grados.");
        }

        /* Validamos que las dimensiones sean mayores a 0 */
        if (ancho <= 0 || alto <= 0) {
            throw new IllegalArgumentException(
                    "Las dimensiones deben ser positivas.");
        }

        this.posicion = new VectorR3(posicion);
        this.miraA = new VectorR3(miraA);
        this.fov = fov;
        this.ancho = ancho;
        this.alto = alto;

        /* Calcular vector w: atrás de la cámara.*/
        this.w = posicion.resta(miraA).vectorUnitario();

        /* Calcular vector u: derecha de la cámara.*/

        /* Vector auxiliar para orientar la cámara. */
        VectorR3 upVector = new VectorR3(0, 1, 0);

        /* Calcular el producto cruz antes de normalizar. */
        VectorR3 derecha = upVector.productoCruz(w);

        /* Si los vectores son paralelos, elegimos otra referencia. */
        if (derecha.esCero()) {
            upVector = new VectorR3(0, 0, 1);
            derecha = upVector.productoCruz(w);
        }

        /* Normalizamos para obtener u. */
        this.u = derecha.vectorUnitario();

        /* Calculamos v. */
        this.v = w.productoCruz(u);

        /* Calculamos las dimensiones del viewport */

        /* Distancia de la cámara al viewport. */
        double distancia = 1.0;

        /* Convertimos el FOV de grados a radianes. */
        double angulo = Math.toRadians(fov);

        /* Calculamos las dimensiones del viewport */
        this.anchoViewport = 2 * distancia * Math.tan(angulo / 2);
        this.altoViewport = anchoViewport * ((double) alto / ancho);
    }

    /**
     * Genera el rayo que atraviesa el centro del píxel
     * indicado por su columna y fila.
     */
    public Rayo generarRayo(int columna, int fila) {
        // PASO 7: Validar los índices del píxel.

        // PASO 8: Encontrar el centro del viewport.

        // PASO 9: Calcular el desplazamiento horizontal.

        // PASO 10: Calcular el desplazamiento vertical.

        // PASO 11: Encontrar el centro del píxel.

        // PASO 12: Calcular la dirección del rayo.

        // PASO 13: Devolver el Rayo.

        throw new UnsupportedOperationException(
                "Pendiente de implementar");
    }
}

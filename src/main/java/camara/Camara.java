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

    /* Distancia de la cámara al viewport. */
    private static final double DISTANCIA_VIEWPORT = 1.0;

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

        /* Convertimos el FOV de grados a radianes. */
        double angulo = Math.toRadians(fov);

        /* Calculamos las dimensiones del viewport */
        this.anchoViewport = 2 * DISTANCIA_VIEWPORT * Math.tan(angulo / 2);
        this.altoViewport = anchoViewport * ((double) alto / ancho);
    }

    /**
     * Genera el rayo que atraviesa el centro del píxel
     * indicado por su columna y fila.
     */
    public Rayo generarRayo(int columna, int fila) {

        /* Validar que el pixel pertenezca a la imagen */

        if (columna < 0 || columna >= ancho ||
                fila < 0 || fila >= alto) {
            throw new IllegalArgumentException(
                    "El pixel está fuera de la imagen");
        }

        /* Calcular el centro del Viewport */
        VectorR3 centroViewport = posicion.resta(w.productoPorUnEscalar(DISTANCIA_VIEWPORT));

        /* Calcular el desplazamiento horizontal. */
        double desplazamientoHorizontal = (columna + 0.5 - ((double) ancho / 2))
                * (anchoViewport / ancho);

        /* Calcular el desplazamiento horizontal en R3. */
        VectorR3 horizontal = u.productoPorUnEscalar(desplazamientoHorizontal);

        /* Calcular el desplazamiento vertical. */
        double desplazamientoVertical = (-fila - 0.5 + ((double) alto / 2))
                * (altoViewport / alto);

        /* Calcular el desplazamiento vertical en R3. */
        VectorR3 vertical = v.productoPorUnEscalar(desplazamientoVertical);

        /* Calcular el centro del píxel. */
        VectorR3 centroPixel = centroViewport.suma(
                horizontal.suma(vertical)
        );

        /* Calcular la dirección del rayo. */
        VectorR3 direccion = centroPixel.resta(posicion);

        /* Devolver el Rayo. */
        return new Rayo(new VectorR3(posicion), direccion);
    }
}

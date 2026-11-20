package iluminacion;

import matematicas.VectorR3;

/**
 * Representa una fuente de luz puntual en una escena.
 */
public class LuzPuntual {

    /* Posición de la luz en el espacio tridimensional. */
    private final VectorR3 posicion;

    /* Intensidad de la fuente luminosa. */
    private final double intensidad;

    /**
     * Construye una luz puntual con su posición e intensidad.
     *
     * @param posicion posición de la luz en R3.
     * @param intensidad intensidad de la luz entre 0 y 1.
     * @throws IllegalArgumentException si la posición es nula
     *         o la intensidad no es finita o está fuera de [0, 1].
     */
    public LuzPuntual(VectorR3 posicion, double intensidad) {

        /* Validar que la posición no sea nula. */
        if (posicion == null) {
            throw new IllegalArgumentException(
                    "La posición de la luz no puede ser null.");
        }

       /* Validar que la intensidad sea finita y esté entre 0 y 1. */
        if (!Double.isFinite(intensidad) ||
                intensidad < 0 || intensidad > 1) {
            throw new IllegalArgumentException(
                    "La intensidad debe estar entre 0 y 1.");
        }

        /* Guardar los atributos. */
        this.posicion = new VectorR3(posicion);

        this.intensidad = intensidad;
    }

    /**
     * Devuelve la posición de la fuente luminosa.
     *
     * @return una copia del vector de posición.
     */
    public VectorR3 getPosicion() {
        return new VectorR3(posicion);
    }

    /**
     * Devuelve la intensidad de la fuente luminosa.
     *
     * @return intensidad de la luz.
     */
    public double getIntensidad() {
        return intensidad;
    }
}

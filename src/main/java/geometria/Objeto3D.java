package geometria;

import matematicas.Rayo;
import matematicas.VectorR3;

/**
 * Representa un objeto geométrico con un color uniforme.
 * Cada figura debe definir cómo intersectarse con un rayo.
 */
public abstract class Objeto3D {

    /* Color del objeto, con componentes RGB entre 0 y 1. */
    private final VectorR3 color;

    /**
     * Construye un objeto geométrico con un color uniforme.
     *
     * @param color componentes RGB del objeto.
     */
    protected Objeto3D(VectorR3 color) {
        if (color == null)
            throw new IllegalArgumentException(
                "El color no puede ser null.");

        // Cada componente RGB debe estar entre 0 y 1.
        if (!(color.getX() >= 0 && color.getX() <= 1
           && color.getY() >= 0 && color.getY() <= 1
           && color.getZ() >= 0 && color.getZ() <= 1))
            throw new IllegalArgumentException(
                "Las componentes RGB deben estar entre 0 y 1.");

        this.color = color;
    }

    /**
     * Devuelve el color uniforme del objeto.
     *
     * @return color del objeto.
     */
    public VectorR3 getColor() {
        return this.color;
    }

    /**
     * Calcula la primera intersección válida con un rayo.
     *
     * @param rayo rayo que se quiere intersectar.
     * @return intersección más cercana o null si no existe.
     */
    public abstract Interseccion intersectar(Rayo rayo);
}

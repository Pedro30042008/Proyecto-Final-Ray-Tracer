package geometria;

import matematicas.Rayo;
import matematicas.VectorR3;
import matematicas.Verificador;

/**
 * Representa un objeto geométrico con un color uniforme.
 * Cada figura debe definir cómo intersectarse con un rayo.
 */
public abstract class Objeto3D {

    /* Color del objeto, con componentes RGB entre 0 y 1. */
    protected VectorR3 color;

    /**
     * Construye un objeto geométrico con un color uniforme.
     *
     * @param color componentes RGB del objeto.
     */
    protected Objeto3D(VectorR3 color) {
	// Verificamos el color y lo asignamos.
        this.color = Verificador.verificarColor(color);
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
     * Modifica el color uniforme del objeto.
     *
     * @param color nuevo color del objeto.
     */
    public void setColor(VectorR3 color) {
	// Verificamos el color y lo asignamos.
        this.color = Verificador.verificarColor(color);
    }

    /**
     * Calcula la primera intersección válida con un rayo.
     *
     * @param rayo rayo que se quiere intersectar.
     * @return intersección más cercana o null si no existe.
     * @throws IllegalArgumentException si el rayo es null.
     */
    public abstract Interseccion intersectar(Rayo rayo);
}

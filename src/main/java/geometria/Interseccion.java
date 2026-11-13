package geometria;

import matematicas.VectorR3;

/**
 * Guarda los datos necesarios de los cálculos obtenidos de la intersección
 * de un rayo con una figura.
 */
public class Interseccion {

    /* Parámetro t donde se produce la intersección. */
    private final double t;
    /* Punto donde se presenta la intersección. */
    private final VectorR3 punto;
    /* Vector perpendicular a la superficie en el punto de choque. */
    private final VectorR3 normal;

    /**
     * Construye los datos que debe guardar la intersección.
     *
     * @param t parámetro donde ocurre la intersección con la figura.
     * @param punto vector que contiene el punto donde está la intersección.
     * @param normal vector perpendicular a la superficie en el choque.
     * @throws IllegalArgumentException si el parámetro t no es finito o
     *         es negativo, si los vectores son <code>null</code> o
     *         si el vector normal es cero.
     */
    public Interseccion(double t, VectorR3 punto, VectorR3 normal) {
	// Verificamos que el parámetro t sea finito y no negativo.
        if (!Double.isFinite(t) || t < 0)
            throw new IllegalArgumentException("El parámetro t debe ser finito y no negativo.");

        // Verificamos que los argumentos no sean nulos.
        if (punto == null || normal == null)
            throw new IllegalArgumentException("El punto y el vector normal no pueden ser null.");

        // Verificamos que el vector normal sea distinto del vector cero.
        if (normal.esCero())
            throw new IllegalArgumentException("El vector normal debe ser distinto del vector cero.");

        // Guardamos el parámetro t y los vectores; la normal queda unitaria.
	this.t = t;
        this.punto = punto;
        this.normal = normal.vectorUnitario();
    }

    /**
     * Devuelve el parámetro donde se produce la intersección rayo figura.
     *
     * @return parámetro t cuando ocurre la intersección.
     */
    public double getT() {
	// Devolvemos el parámetro t.
        return this.t;
    }

    /**
     * Devuelve el punto de la intersección.
     *
     * @return vector que contiene el punto donde se produce la intersección.
     */
    public VectorR3 getPunto() {
	// Devolvemos el punto.
        return this.punto;
    }

    /**
     * Devuelve el vector normal de la intersección rayo-figura.
     *
     * @return vector normal de la intersección rayo-figura.
     */
    public VectorR3 getNormal() {
	// Devolvemos el vector normal.
        return this.normal;
    }
}

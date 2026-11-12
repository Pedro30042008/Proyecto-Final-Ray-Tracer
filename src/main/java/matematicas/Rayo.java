package matematicas;

/**
 * Define la estructura de un rayo en el espacio tridimensional y la forma
 * de calcular los puntos de su trayectoria.
 *
 * Matemáticamente, se modela con la expresión R(t) = O + tD,
 * donde D es distinto del vector cero y no necesita tener norma uno.
 */
public class Rayo {

    /* Punto de origen del rayo. */
    private final VectorR3 origen;
    /* Vector que determina su dirección. */
    private final VectorR3 direccion;

    /**
     * Construye un rayo dado el origen y el vector de dirección.
     *
     * @param origen vector que representa el punto de origen.
     * @param direccion vector que determina la dirección del rayo.
     * @throws IllegalArgumentException si alguno de los argumentos es null
     *         o si la dirección es el vector cero.
     */
    public Rayo(VectorR3 origen, VectorR3 direccion) {
        // Verificamos que los argumentos no sean nulos.
        if (origen == null || direccion == null)
            throw new IllegalArgumentException("El origen y la dirección no pueden ser null.");

        // Verificamos que la dirección sea distinta del vector cero.
        if (direccion.esCero())
            throw new IllegalArgumentException("El vector de dirección debe ser distinto del vector cero.");

        // Guardamos los vectores; no pueden modificarse porque VectorR3 es inmutable.
        this.origen = origen;
        this.direccion = direccion;
    }

    /**
     * Devuelve el origen del rayo.
     *
     * @return vector que representa el punto de origen del rayo.
     */
    public VectorR3 getOrigen() {
        // Devolvemos el vector de origen.
        return this.origen;
    }

    /**
     * Devuelve la dirección del rayo.
     *
     * @return vector que determina la dirección del rayo.
     */
    public VectorR3 getDireccion() {
        // Devolvemos el vector de dirección.
        return this.direccion;
    }

    /**
     * Calcula un punto de la trayectoria mediante R(t) = O + tD.
     *
     * Para representar el rayo se consideran valores t mayores o iguales
     * a cero. El parámetro t solo es distancia si D tiene norma uno.
     *
     * @param t parámetro escalar de la trayectoria.
     * @return punto correspondiente al parámetro t.
     */
    public VectorR3 puntoEnT(double t) {
        // Calculamos el desplazamiento y lo sumamos al origen.
        return origen.suma(direccion.productoPorUnEscalar(t));
    }
}

package matematicas;

/**
 * Clase Rayo.
 *
 * Define la estructura de un rayo y la forma de calcular los puntos de
 * su trayectoria, de denota como:
 *
 * R(t) = O + tD, donde;
 *
 * 't' es parámetro libre que denota un punto específico del rayo.
 * 'O' es el punto donde se origina el rayo.
 * 'D' es un vector, distinto del vector nulo, que denota la dirección del rayo.
 */
public class Rayo {

    /* Punto de origen del rayo. */
    private VectorR3 origen;
    /* Vector que determina su dirección. */
    private VectorR3 direccion;

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
     * Modifica el origen del rayo.
     *
     * @param origen nuevo vector que representa el punto de origen del rayo.
     */
    public void setOrigen(VectorR3 origen) {
        // Modificamos el vector de origen.
        this.origen = origen;
    }

    /**
     * Modifica la dirección del rayo.
     *
     * @param direccion nuevo vector que determina la dirección del rayo.
     */
    public void setDireccion(VectorR3 direccion) {
        // Modificamos el vector de dirección.
        this.direccion = direccion;
    }
    
    /**
     * Calcula un punto de la trayectoria mediante R(t) = O + tD.
     *
     * Para representar el rayo se consideran valores t mayores o iguales
     * a cero.
     *
     * @param t parámetro escalar de la trayectoria.
     * @return punto resultante dado el parámetro t.
     * @throws IllegalArgumentException si t es negativo o no es un real finito.
     */
    public VectorR3 puntoEnT(double t) {
	// Verificamos que el parámetro t sea finito.
	if (!Double.isFinite(t))
	    // Lanzamos un error.
	    throw new IllegalArgumentException("El parámetro t no es valido al no ser un real finito: " + t);
	// Validamos el parámetro t para que no ea negativo.
	if (t < 0)
	    // Lanzamos la excepción correspondiente.
	    throw new IllegalArgumentException("El parámetro t es inválido al ser negativo:" + t);
        // Calculamos el desplazamiento y lo sumamos al origen.
        return origen.suma(direccion.productoPorUnEscalar(t));
    }
}

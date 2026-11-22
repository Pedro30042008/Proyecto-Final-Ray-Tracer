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
     * @throws IllegalArgumentException si alguno de los argumentos es
     *         <code>null</code> o si la dirección es el vector cero.
     */
    public Rayo(VectorR3 origen, VectorR3 direccion) {
        // Verificamos los datos y los guardamos.
        this.origen = Verificador.verificarNotNull(origen, "El origen no pueden ser null.");
        this.direccion = Verificador.verificarVectorCero(direccion, "La dirección no pueden ser null.", "El vector de dirección debe ser distinto del vector cero.");
    }

    /**
     * Devuelve el origen del rayo.
     *
     * @return vector que representa el punto de origen del rayo.
     */
    public VectorR3 getOrigen() {
        return this.origen;
    }

    /**
     * Devuelve la dirección del rayo.
     *
     * @return vector que determina la dirección del rayo.
     */
    public VectorR3 getDireccion() {
        return this.direccion;
    }

    /**
     * Modifica el origen del rayo.
     *
     * @param origen nuevo vector que representa el punto de origen del rayo.
     */
    public void setOrigen(VectorR3 origen) {
	// Verificamos el parámetro y lo asignamos.
        this.origen = Verificador.verificarNotNull(origen, "El origen no pueden ser null.");
    }

    /**
     * Modifica la dirección del rayo.
     *
     * @param direccion nuevo vector que determina la dirección del rayo.
     */
    public void setDireccion(VectorR3 direccion) {
	// Verificamos el parámetro y lo asignamos.
        this.direccion = Verificador.verificarVectorCero(direccion, "La dirección no pueden ser null.", "El vector de dirección debe ser distinto del vector cero.");
    }
    
    /**
     * Calcula un punto de la trayectoria mediante R(t) = O + tD.
     *
     * Para representar el rayo se consideran valores t mayores o iguales
     * a cero.
     *
     * @param t parámetro escalar de la trayectoria.
     * @return punto resultante dado el parámetro t.
     */
    public VectorR3 puntoEnT(double t) {
	// Validamos la t.
	double tValidada = Verificador.verificarNumero(t, "El parámetro t no es válido al no ser un real finito: " + t);
	
	// Validamos el parámetro t para que no sea negativo.
	if (tValidada < 0)
	    // Lanzamos la excepción correspondiente.
	    throw new IllegalArgumentException("El parámetro t es inválido al ser negativo:" + tValidada);
	
        // Calculamos el desplazamiento y lo sumamos al origen.
        return origen.suma(direccion.productoPorUnEscalar(tValidada));
    }
}

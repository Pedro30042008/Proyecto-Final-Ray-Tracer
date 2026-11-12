package matematicas;

/**
 * Define la estructura de un vector en el espacio tridimensional R3.
 * Sus componentes no pueden modificarse después de construirlo.
 */
public final class VectorR3 {

    /* Componente X del vector. */
    private final double x;
    /* Componente Y del vector. */
    private final double y;
    /* Componente Z del vector. */
    private final double z;

    /**
     * Construye un vector dadas tres componentes.
     *
     * @param x componente X del vector.
     * @param y componente Y del vector.
     * @param z componente Z del vector.
     */
    public VectorR3(double x, double y, double z) {
        // Asignamos las componentes, evitando ceros negativos.
        this.x = (x == 0.0) ? 0.0 : x;
        this.y = (y == 0.0) ? 0.0 : y;
        this.z = (z == 0.0) ? 0.0 : z;
    }

    /**
     * Devuelve la componente x del vector.
     *
     * @return componente X del vector.
     */
    public double getX() {
        // Devolvemos la componente X del vector.
        return this.x;
    }

    /**
     * Devuelve la componente y del vector.
     *
     * @return componente Y del vector.
     */
    public double getY() {
        // Devolvemos la componente Y del vector.
        return this.y;
    }

    /**
     * Devuelve la componente z del vector.
     *
     * @return componente Z del vector.
     */
    public double getZ() {
        // Devolvemos la componente Z del vector.
        return this.z;
    }

    /**
     * Verifica si el vector es el vector cero.
     *
     * @return <code>true</code> si todas las componentes son cero,
     *         <code>false</code> en otro caso.
     */
    public boolean esCero() {
        // Verificamos si las tres componentes del vector son cero.
        return this.x == 0 && this.y == 0 && this.z == 0;
    }

    /**
     * Suma el vector con otro y devuelve el nuevo vector resultante.
     * A + B = (a1 + b1, a2 + b2, a3 + b3)
     *
     * @param vector el otro vector involucrado en la suma.
     * @return el vector resultante de la suma entre vectores.
     */
    public VectorR3 suma(VectorR3 vector) {
        // Devolvemos el vector resultante de la suma.
        return new VectorR3(verificarDesbordamiento(this.x + vector.x, "suma", toString(), vector.toString()),
			    verificarDesbordamiento(this.y + vector.y, "suma", toString(), vector.toString()),
			    verificarDesbordamiento(this.z + vector.z, "suma", toString(), vector.toString()));
    }

    /**
     * Resta el vector con otro y devuelve el nuevo vector resultante.
     * A - B = (a1 - b1, a2 - b2, a3 - b3)
     *
     * @param vector el otro vector involucrado en la resta.
     * @return el vector resultante de la resta entre vectores.
     */
    public VectorR3 resta(VectorR3 vector) {
        // Devolvemos el vector resultante de la resta.
        return new VectorR3(verificarDesbordamiento(this.x - vector.x, "resta", toString(), vector.toString()),
			    verificarDesbordamiento(this.y - vector.y, "resta", toString(), vector.toString()),
			    verificarDesbordamiento(this.z - vector.z, "resta", toString(), vector.toString()));
    }

    /**
     * Realiza el producto del vector con un escalar y devuelve el
     * nuevo vector resultante.
     * α ⋅ B = (α ⋅ b1, α ⋅ b2, α ⋅ b3)
     *
     * @param escalar número real involucrado en el producto.
     * @return el vector resultante del producto por escalar.
     */
    public VectorR3 productoPorUnEscalar(double escalar) {
        // Devolvemos el vector resultante del producto por un escalar.
        return new VectorR3(verificarDesbordamiento(this.x * escalar, "producto por el escalar: " + escalar, toString(), null),
			    verificarDesbordamiento(this.y * escalar, "producto por el escalar: " + escalar, toString(), null),
			    verificarDesbordamiento(this.z * escalar, "producto por el escalar: " + escalar, toString(), null));
    }

    /**
     * Realiza el producto punto del vector con otro y devuelve
     * un número real.
     * A ⋅ B = a1 ⋅ b1 + a2 ⋅ b2 + a3 ⋅ b3
     *
     * @param vector el otro vector involucrado en el producto punto.
     * @return el número real resultante del producto punto.
     */
    public double productoPunto(VectorR3 vector) {
        // Devolvemos el número resultante del producto punto.
        return verificarDesbordamiento(this.x * vector.x + this.y * vector.y + this.z * vector.z, "producto punto", toString(), vector.toString());
    }

    /**
     * Devuelve el producto cruz del vector con otro.
     * A × B = (ay ⋅ bz − az ⋅ by, az ⋅ bx − ax ⋅ bz, ax ⋅ by − ay ⋅ bx)
     *
     * @param vector el otro vector involucrado en el producto cruz.
     * @return el vector resultante del producto cruz.
     */
    public VectorR3 productoCruz(VectorR3 vector) {
        // Devolvemos el vector resultante del producto cruz. 
        return new VectorR3(verificarDesbordamiento(this.y * vector.z - this.z * vector.y, "producto cruz", toString(), vector.toString()),
			    verificarDesbordamiento(this.z * vector.x - this.x * vector.z, "producto cruz", toString(), vector.toString()),
			    verificarDesbordamiento(this.x * vector.y - this.y * vector.x, "producto cruz", toString(), vector.toString()));
    }
    
    /**
     * Calcula la norma del vector.
     
     *
     * @return norma del vector.
     */
    public double norma() {
	// Calulamos la suma de cuadrados de la norma y verificamos el desbordamiento.
	double valor = verificarDesbordamiento(this.x * this.x + this.y * this.y + this.z * this.z, "norma", toString(), null);
        // Retornamos la norma del vector.
        return Math.sqrt(valor);
    }

    /**
     * Devuelve el vector normalizado.
     *
     * @return el vector unitario del vector.
     * @throws IllegalArgumentException si es el vector cero.
     */
    public VectorR3 vectorUnitario() {
        // Calculamos la norma del vector.
        double norma = norma();

        // Verificamos que la normalización esté definida.
        if (norma == 0)
            throw new IllegalArgumentException("El vector cero" + toString() + " no tiene vector unitario.");

        // Devolvemos un nuevo vector de norma uno.
        return productoPorUnEscalar(1 / norma);
    }

    /**
     * Método auxiliar para verificar desbordamientos.
     *
     * @param valor número a evaluar de una operación resultante.
     * @param operacion nombre de la operación que se realiza.
     * @param v1 vector uno involucrado en la operación.
     * @param v2 posible vector dos involucrado en la operación.
     * @return valor resultante finito.
     * @throws ArithmeticException si el valor resultante no es finito.
     */
    private double verificarDesbordamiento(double valor, String operacion, String v1, String v2){
	/* Si no es representable el valor resultante de la operación. */
	if (!Double.isFinite(valor)){
	    /* Verificamos si es una operación del vector o con otro. */
	    if (v2 == null)
		/* Lanzamos un error. */
		throw new ArithmeticException("El valor resultante de la operación" +
					      operacion +
					      "con el vector v: " + v1 +
					      ", resulto no finito.");
	    /* Lanzamos un error. */
	    throw new ArithmeticException("El valor resultante de la operación" +
					  operacion +
					  "con los vectores v1: " + v1 +
					  " y el vector v2: " + v2 +
					  ", resulto no finito.");
	}
	/* En caso contrario, retornamos el valor. */
	return valor;
    }

    /**
     * Método equals para comparar dos vectores.
     *
     * @param obj objeto con el cual compararemos.
     * @return <code>true</code> si son iguales componente a componente,
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object obj) {
        // Si se trata del mismo objeto.
        if (this == obj)
	    // Devolvemos true.
            return true;
        
        // Si es null o no se trata de la misma clase.
        if (obj == null || getClass() != obj.getClass()) {
	    // Devolvemos false.
            return false;
        }
        
        // Conversion a VectorR3 ed obj.
        VectorR3 otro = (VectorR3) obj;
        
        // Comparamos componente a componente y devolvemos el resultado.
        return Double.compare(this.x, otro.x) == 0 &&
               Double.compare(this.y, otro.y) == 0 &&
               Double.compare(this.z, otro.z) == 0;
    }
    
    /**
     * Produce una representación en cadena de texto del vector.
     *
     * @return String que representa al vector.
     */
    @Override
    public String toString() {
	/* Cadena de texto para guardar la representación del vector. */
	String vector = "(" + this.x + ", " + this.y + ", " + this.z + ")";
	/* Regresamos la representación del vector. */
	return vector;
    }
}

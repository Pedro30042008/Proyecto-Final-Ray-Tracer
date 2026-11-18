package matematicas;

/**
 * Clase VectorR3.
 *
 * Define la estructura de un vector en el espacio R3
 *
 * Este puede representar:
 *
 * Una posición en el espacio.
 * Vectores con magnitud, dirección y sentido.
 * Colores RGB.
 */
public class VectorR3 {

    /* Componente X del vector. */
    private double x;
    /* Componente Y del vector. */
    private double y;
    /* Componente Z del vector. */
    private double z;

    /**
     * Construye un vector dadas tres componentes.
     *
     * @param x componente X del vector.
     * @param y componente Y del vector.
     * @param z componente Z del vector.
     */
    public VectorR3(double x, double y, double z) {
	// Verificamos componente a componente y asignamos las componentes, evitando ceros negativos.
	this.x = Verificador.verificarNumero(x, "El valor otorgado para la componete x es inválido: " + x);
	this.y = Verificador.verificarNumero(y, "El valor otorgado para la componete y es inválido: " + y);
	this.z = Verificador.verificarNumero(z, "El valor otorgado para la componete z es inválido: " + x);
    }

    /**
     * Construye un vector a partir de otro.
     *
     * @param otro vector a copiar.
     */
    public VectorR3(VectorR3 otro) {
        // Asignamos las componentes al nuevo vector.
        this.x = otro.x;
        this.y = otro.y;
        this.z = otro.z;
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
     * Modifica la componente x del vector.
     *
     * @param x nueva componente X del vector.
     */
    public void setX(double x) {
	// Verificamos componente a componente y asignamos las componente, evitando cero negativo.
	this.x = Verificador.verificarNumero(x, "El valor otorgado para la componete x es inválido: " + x);
    }

    /**
     * Modifica la componente y del vector.
     *
     * @param y nueva componente Y del vector.
     */
    public void setY(double y) {
	// Verificamos componente a componente y asignamos las componente, evitando cero negativo.
	this.y = Verificador.verificarNumero(y, "El valor otorgado para la componete y es inválido: " + y);
    }

    /**
     * Modifica la componente z del vector.
     *
     * @param z nueva componente Z del vector.
     */
    public void setZ(double z) {
	// Verificamos componente a componente y asignamos las componente, evitando cero negativo.
	this.z = Verificador.verificarNumero(z, "El valor otorgado para la componete z es inválido: " + z);
    }
    
    /**
     * Verifica si es el vector cero.
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
        return new VectorR3(Verificador.verificarOperacion(this.x + vector.x, "suma", toString(), vector.toString()),
			    Verificador.verificarOperacion(this.y + vector.y, "suma", toString(), vector.toString()),
			    Verificador.verificarOperacion(this.z + vector.z, "suma", toString(), vector.toString()));
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
        return new VectorR3(Verificador.verificarOperacion(this.x - vector.x, "resta", toString(), vector.toString()),
			    Verificador.verificarOperacion(this.y - vector.y, "resta", toString(), vector.toString()),
			    Verificador.verificarOperacion(this.z - vector.z, "resta", toString(), vector.toString()));
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
        return new VectorR3(Verificador.verificarOperacion(this.x * escalar, "producto por el escalar: " + escalar, toString(), null),
			    Verificador.verificarOperacion(this.y * escalar, "producto por el escalar: " + escalar, toString(), null),
			    Verificador.verificarOperacion(this.z * escalar, "producto por el escalar: " + escalar, toString(), null));
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
        return Verificador.verificarOperacion(this.x * vector.x + this.y * vector.y + this.z * vector.z, "producto punto", toString(), vector.toString());
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
        return new VectorR3(Verificador.verificarOperacion(this.y * vector.z - this.z * vector.y, "producto cruz", toString(), vector.toString()),
			    Verificador.verificarOperacion(this.z * vector.x - this.x * vector.z, "producto cruz", toString(), vector.toString()),
			    Verificador.verificarOperacion(this.x * vector.y - this.y * vector.x, "producto cruz", toString(), vector.toString()));
    }
    
    /**
     * Calcula la norma del vector.
     *
     * @return norma del vector.
     */
    public double norma() {
	// Calculamos la suma de cuadrados de la norma y verificamos el desbordamiento.
	double valor = Verificador.verificarOperacion(this.x * this.x + this.y * this.y + this.z * this.z, "norma", toString(), null);
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
        
        // Convertimos a VectorR3 el obj.
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
	// Cadena de texto para guardar la representación del vector.
	String vector = "(" + this.x + ", " + this.y + ", " + this.z + ")";
	// Regresamos la representación del vector.
	return vector;
    }
}

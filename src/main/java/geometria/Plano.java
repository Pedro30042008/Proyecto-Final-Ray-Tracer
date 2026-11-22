package geometria;

import matematicas.VectorR3;
import matematicas.Rayo;
import matematicas.Verificador;

/**
 * Define la estructura de un plano y su comportamiento al intersectar rayos.
 */
public class Plano extends Objeto3D {

    /* Vector normal al plano. */
    private VectorR3 normal;
    /* Punto perteneciente al plano. */
    private VectorR3 punto;
    
    /**
     * Construye un plano dado un vector normal y un punto perteneciente
     * al plano.
     *
     * @param normal vector normal al plano.
     * @param punto perteneciente al plano.
     * @param color componentes RGB del plano.
     */
    public Plano(VectorR3 normal, VectorR3 punto, VectorR3 color) {
	// Constructor padre.
	super(color);
	// Verificamos los datos y los asignamos.
	this.normal = Verificador.verificarVectorCero(normal,
						      "El vector normal al plano debe ser distinto de null",
						      "El vector normal al plano debe ser distinto del vector cero.");
	this.punto = Verificador.verificarNotNull(punto, "El punto perteneciente al plano debe ser distinto de null.");
    }

    /**
     * Devuelve el punto perteneciente al plano.
     *
     * @return punto perteneciente al plano.
     */
    public VectorR3 getPunto() {
	return this.punto;
    }

    /**
     * Devuelve el vector normal al plano, siendo el mismo
     * para todos los puntos pertenecientes al plano.
     *
     * @return vector normal al plano.
     */
    public VectorR3 getNormal() {
	return this.normal;
    }

    /**
     * Modifica el punto perteneciente al plano.
     *
     * @param punto nuevo punto perteneciente al plano.
     */
    public void setPunto(VectorR3 punto) {
	// Verificamos el punto y lo asignamos.
	this.punto = Verificador.verificarNotNull(punto, "El punto perteneciente al plano debe ser distinto de null.");
    }

    /**
     * Modifica el vector normal al plano, siendo el mismo
     * para todos los puntos pertenecientes al plano.
     *
     * @param normal nuevo vector normal al plano.
     */
    public void setNormal(VectorR3 normal) {
	// Verificamos la normal y la asignamos.
        this.normal = Verificador.verificarVectorCero(normal,
						      "El vector normal al plano debe ser distinto de null",
						      "El vector normal al plano debe ser distinto del vector cero.");
    }
    
    /**
     * Calcula la intersección que tiene el plano con un rayo.
     *
     * @param rayo que puede tener o no una intersección válida con el plano.
     * @return la intersección del rayo con un punto del plano si este se
     *         encuentra en o delante del origen del rayo,
     *         <code>null</code> en otro caso.
     * @throws IllegalArgumentException si el rayo es <code>null</code>.
     */
    @Override
    public Interseccion intersectar(Rayo rayo) {
        // Verificamos que el rayo sea distinto de null.
	Verificador.verificarNotNull(rayo, "El rayo debe ser distinto de null.");
	
	// Obtenemos los datos del rayo.
	VectorR3 origen = rayo.getOrigen();
	VectorR3 direccion = rayo.getDireccion();
	
	// Verificamos el comportamiento del rayo con el plano.
        double denominador = direccion.productoPunto(this.normal);
	
	// Si el rayo no choca con el plano (es paralelo) o tiene una infinidad de intersecciones (contenido en el mismo).
	if (denominador == 0)
	    // Retornamos que no hay intersección inválida, pues el rayo "no puede observarlo".
	    return null;
	
	// Hacemos el cálculo de la intersección.
        double t = Verificador.verificarNumero(this.punto.resta(origen).productoPunto(this.normal) / denominador, "Operación para calcular t con desbordamiento.");

	// Verificamos que t pueda observarse.
	if (t <= 1e-6)
	    return null;
	
	// Obtenemos el punto de la intersección.
        VectorR3 pInterseccion = rayo.puntoEnT(t);

	// Normal resultante de la intersección rayo-plano.
	VectorR3 normalPlanoRayo = new VectorR3(this.normal);
	
	// Verificamos si toca desde la cara "frontal" o "posterior".
	if (direccion.productoPunto(this.normal) > 0)
	    normalPlanoRayo = this.normal.productoPorUnEscalar(-1);
	
	// Retornamos la intersección.
	return new Interseccion(t, pInterseccion, normalPlanoRayo);
    }

    /**
     * Produce una representación en cadena de texto del vector.
     *
     * @return String que representa al vector.
     */
    @Override
    public String toString() {
	// Cadena de texto para guardar la representación del plano.
	String plano = "{'tipo': Plano, 'punto': " + this.punto + ", 'normal': " + this.normal + ", 'color': " + this.color + "}";
	// Regresamos la representación del plano.
	return plano;
    }
}

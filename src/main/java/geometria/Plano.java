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
	
	// Verificamos que no sean null los argumentos.
	if (normal == null || punto == null)
	    throw new IllegalArgumentException("El vector normal y el punto deben ser distintos de null.");
	
	// Verificamos que la normal no sea el vector cero.
	if (normal.esCero())
	    throw new IllegalArgumentException("El vector normal al plano debe ser distinto de cero.");
	
	// Asignamos los datos.
	this.normal = normal;
	this.punto = punto;
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
	this.punto = punto;
    }

    /**
     * Modifica el vector normal al plano, siendo el mismo
     * para todos los puntos pertenecientes al plano.
     *
     * @param normal nuevo vector normal al plano.
     */
    public void setNormal(VectorR3 normal) {
        this.normal = normal;
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
	if (rayo == null)
	    throw new IllegalArgumentException("El rayo debe ser distinto de null.");
	
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
	if (t < 0)
	    return null;
	
	// Obtenemos el punto de la intersección.
        VectorR3 pInterseccion = rayo.puntoEnT(t);

	// Verificamos si toca desde la cara "frontal" o "posterior".
	if (direccion.productoPunto(this.normal) > 0)
	    this.normal = this.normal.productoPorUnEscalar(-1);
	
	// Retornamos la intersección.
	return new Interseccion(t, pInterseccion, this.normal);
    }
}

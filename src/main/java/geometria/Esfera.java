package geometria;

import matematicas.VectorR3;
import matematicas.Rayo;

/**
 * Define la estructura de una esfera y su comportamiento con los rayos.
 */
public class Esfera extends Objeto3D {

    /* Centro. */
    private final VectorR3 centro;
    /* Radio. */
    private final double radio;
    
    /**
     * Construye una esfera dado su centro, radio y color.
     *
     * @param centro vector que apunta al centro de la esfera.
     * @param radio valor del radio de la esfera.
     * @param color componentes RGB de la esfera.
     */
    public Esfera(VectorR3 centro, double radio, VectorR3 color) {
	// Invocamos el constructor padre dado un color.
	super(color);
	
	// Verificamos que el centro sea distinto de null.
	if (centro == null)
	    throw new IllegalArgumentException("El centro debe ser un punto bien definido.");
	
	// Verificamos que el radio esté bien definido.
	if (!Double.isFinite(radio) || radio <= 0)
	    throw new IllegalArgumentException("El radio debe ser un número positivo no infinito.");

	// Asignamos los datos de la esfera.
	this.centro = centro;
	this.radio = radio;
    }

    /**
     * Obtiene el radio de la esfera.
     *
     * @return radio de la esfera.
     */
    public double getRadio() {
	// Retornamos el radio de la figura.
	return this.radio;
    }

    /**
     * Obtiene el centro de la esfera.
     *
     * @return vector que contiene el centro de la esfera.
     */
    public VectorR3 getCentro() {
	// Retornamos el centro de la figura.
	return this.centro;
    }
    
    /**
     * Calcula la intersección que tiene la esfera con un rayo.
     *
     * @param rayo con el cual determinaremos su intersección con la esfera.
     * @return la intersección resultante de la esfera con el rayo,
     *          <code>null</code> si no existe.
     */
    @Override
    public Interseccion intersectar(Rayo rayo) {
	// Verificamos que el rayo sea distinto de null.
	if (rayo == null)
	    throw new IllegalArgumentException("El rayo debe ser distinto de null.");
	
	// Obtenemos los datos del rayo.
	VectorR3 origen = rayo.getOrigen();
	VectorR3 direccion = rayo.getDireccion();

	// Guardamos la diferencia del origen con el centro.
	VectorR3 auxDif = origen.resta(this.centro);
	
	// Hallamos a.
	double a = direccion.productoPunto(direccion);
	// Hallamos b.
	double b = auxDif.productoPunto(direccion) * 2;
	// Hallamos c.
	double c = auxDif.productoPunto(auxDif) - this.radio * this.radio;

	// Calculamos el discriminante.
	double discriminante = b * b - 4 * a * c;
	
	// Si la intersección no existe.
	if (discriminante < 0)
	    // Devolvemos null.
	    return null;

	// Guardamos la t menor.
	double tMenor = (-b - Math.sqrt(discriminante)) / (2 * a);
	
	// Guardamos la t mayor.
	double tMayor = (-b + Math.sqrt(discriminante)) / (2 * a);
	
	// Seleccionamos el parámetro de la primera intersección válida del rayo con la esfera.
	double t;
	
	// Si tMenor es positivo o cero.
	if (tMenor >= 0)
	    // La primera intersección es con tMenor.
	    t = tMenor;
	// Si tMayor es positivo o cero.
	else if (tMayor >= 0)
	    // La primera intersección es con tMayor.
	    t = tMayor;
	// Ambas soluciones están detrás del origen del rayo.
	else	    
	    return null;
	// Obtenemos el punto de intersección.
	VectorR3 punto = rayo.puntoEnT(t);
	
	// Devolvemos la intersección.
	return new Interseccion(t, punto, punto.resta(this.centro));
    }
}

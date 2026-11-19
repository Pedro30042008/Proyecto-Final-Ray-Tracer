package geometria;

import matematicas.VectorR3;
import matematicas.Rayo;
import matematicas.Verificador;

/**
 * Define la estructura de una esfera y su comportamiento con los rayos.
 */
public class Esfera extends Objeto3D {

    /* Centro. */
    private VectorR3 centro;
    /* Radio. */
    private double radio;
    
    /**
     * Construye una esfera dado su centro, radio y color.
     *
     * @param centro vector que apunta al centro de la esfera.
     * @param radio valor del radio de la esfera.
     * @param color componentes RGB de la esfera.
     * @throws IllegalArgumentException si el centro es <code>null</code>
     *         o si el radio no es un número real finito y mayor a 0.
     */
    public Esfera(VectorR3 centro, double radio, VectorR3 color) {
	// Invocamos el constructor padre dado un color.
	super(color);
	
	// Verificamos que el centro sea distinto de null.
	if (centro == null)
	    throw new IllegalArgumentException("El centro debe ser un punto bien definido.");

	// Validamos el rayo.
	double radioValidado = Verificador.verificarNumero(radio, "El radio debe ser un número finito real: " + radio);
	
	// Verificamos que el radio esté bien definido.
	if (radioValidado <= 0)
	    throw new IllegalArgumentException("El radio debe ser mayor a 0.");

	// Asignamos los datos de la esfera.
	this.centro = centro;
	this.radio = radioValidado;
    }

    /**
     * Obtiene el radio de la esfera.
     *
     * @return radio de la esfera.
     */
    public double getRadio() {
	return this.radio;
    }

    /**
     * Obtiene el centro de la esfera.
     *
     * @return vector que contiene el centro de la esfera.
     */
    public VectorR3 getCentro() {
	return this.centro;
    }

    /**
     * Modifica el radio de la esfera.
     *
     * @param radio nuevo radio de la esfera.
     */
    public void setRadio(double radio) {
        this.radio = radio;
    }

    /**
     * Modifica el centro de la esfera.
     *
     * @param centro nuevo punto donde se localiza el centro de la esfera.
     */
    public void setCentro(VectorR3 centro) {
        this.centro = centro;
    }
    
    /**
     * Calcula la intersección que tiene la esfera con un rayo.
     *
     * @param rayo con el cual determinaremos su intersección con la esfera.
     * @return la intersección resultante de la esfera con el rayo,
     *         <code>null</code> si no existe.
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

	// Guardamos la diferencia del origen con el centro.
	VectorR3 auxDif = origen.resta(this.centro);
	
	// Hallamos a.
	double a = direccion.productoPunto(direccion);
	
	// Hallamos b.
	double b = Verificador.verificarNumero(auxDif.productoPunto(direccion) * 2, "Operación para calcular b con desbordamiento.");
	
	// Hallamos c.
	double c = Verificador.verificarNumero(auxDif.productoPunto(auxDif) - this.radio * this.radio, "Operación para calcular c con desbordamiento.");
	
	// Calculamos el discriminante.
	double discriminante = Verificador.verificarNumero(b * b - 4 * a * c, "Operación para calcular discriminante con desbordamiento.");
	
	// Si la intersección no existe.
	if (discriminante < 0)
	    // Devolvemos null.
	    return null;

	// Guardamos la t menor.
	double tMenor = Verificador.verificarNumero((-b - Math.sqrt(discriminante)) / (2 * a), "Operación para calcular tMenor con desbordamiento.");
	
	// Guardamos la t mayor.
	double tMayor = Verificador.verificarNumero((-b + Math.sqrt(discriminante)) / (2 * a), "Operación para calcular tMayor con desbordamiento.");
	
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

	// Obtenemos el vector normal a la superficie en el punto de intersección.
	VectorR3 normal = punto.resta(this.centro);

	// Verificamos si toca desde la cara "frontal" o "posterior".
	if (direccion.productoPunto(normal) > 0)
	    normal = normal.productoPorUnEscalar(-1);
	    
	// Devolvemos la intersección.
	return new Interseccion(t, punto, normal);
    }
}

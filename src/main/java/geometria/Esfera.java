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
	// Asignamos los datos de la esfera.
	this.centro = Verificador.verificarNotNull(centro, "El centro de la esfera ddebe ser distinto de null.");
	// Verificamos el radio y lo asignamos.
        this.radio = Verificador.verificarPositivo(radio, "El radio debe ser un número finito.", "El radio debe ser positivo o cero.");
	// Verificamos que el radio sea mayor a 0.
	if (this.radio == 0)
	    throw new IllegalArgumentException("El radio debe ser mayor a 0.");
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
     * @throws IllegalArgumentException si el radio es cero.
     */
    public void setRadio(double radio) {
	// Verificamos el radio y lo asignamos.
        double r = Verificador.verificarPositivo(radio, "El radio debe ser un número finito.", "El radio debe ser positivo o cero.");
        // Verificamos que el radio sea mayor a 0.
	if (r == 0)
	    throw new IllegalArgumentException("El radio debe ser mayor a 0.");
	// Asignamos el radio.
	this.radio = r;
    }

    /**
     * Modifica el centro de la esfera.
     *
     * @param centro nuevo punto donde se localiza el centro de la esfera.
     */
    public void setCentro(VectorR3 centro) {
	// Verificamos el centro y lo asignamos.
        this.centro = Verificador.verificarNotNull(centro, "El centro de la esfera debe ser distinto de null.");
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
	Verificador.verificarNotNull(rayo, "El rayo debe ser distinto de null.");
	
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
	if (tMenor > 1e-6)
	    // La primera intersección es con tMenor.
	    t = tMenor;
	// Si tMayor es positivo o cero.
	else if (tMayor > 1e-6)
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

    /**
     * Produce una representación en cadena de texto de la esfera.
     *
     * @return cadena de texto que representa la esfera.
     */
    @Override
    public String toString() {
	// Cadena de texto para guardar la representación de la esfera.
	StringBuilder esfera = new StringBuilder();

	// Formato de la esfera
        esfera.append("Esfera\n");
	esfera.append("        Centro: "+ this.centro +"\n");
	esfera.append("        Radio: "+ this.radio +"\n");
	esfera.append("        Color: "+ this.color +"\n");

	// Regresamos la representación de la esfera.
	return esfera.toString();
    }
}

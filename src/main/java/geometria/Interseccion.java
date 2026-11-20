package geometria;

import matematicas.VectorR3;
import matematicas.Verificador;

/**
 * Guarda los datos necesarios de los cálculos obtenidos de la intersección
 * de un rayo con una figura. Si no existe o está bien definida, la
 * intersección es <code>null</code>.
 */
public class Interseccion {

    /* Parámetro t donde se produce la intersección. */
    private double t;
    /* Punto donde se presenta la intersección. */
    private VectorR3 punto;
    /* Vector perpendicular a la superficie en el punto de choque. */
    private VectorR3 normal;

    /**
     * Construye los datos que debe guardar la intersección.
     *
     * @param t parámetro donde ocurre la intersección con la figura.
     * @param punto vector que contiene el punto donde está la intersección.
     * @param normal vector perpendicular a la superficie en el choque.
     */
    public Interseccion(double t, VectorR3 punto, VectorR3 normal) {
        // Guardamos el parámetro t y los vectores; la normal queda unitaria.
	this.t = Verificador.verificarPositivo(t,
					      "El parámetro t debe ser finito.",
					      "El parámetro t debe ser no negativo.");
        this.punto = Verificador.verificarNotNull(punto,
						  "El punto debe ser distinto de null.");
        this.normal = Verificador.verificarVectorCero(normal,
						      "La normal perteneciente al plano debe ser distinto de null.",
						      "El vector normal del plano debe ser distinto del vector cero.").vectorUnitario();
    }

    /**
     * Devuelve el parámetro donde se produce la intersección rayo figura.
     *
     * @return parámetro t cuando ocurre la intersección.
     */
    public double getT() {
        return this.t;
    }

    /**
     * Devuelve el punto de la intersección.
     *
     * @return vector que contiene el punto donde se produce la intersección.
     */
    public VectorR3 getPunto() {
        return this.punto;
    }

    /**
     * Devuelve el vector normal de la intersección rayo-figura.
     *
     * @return vector normal de la intersección rayo-figura.
     */
    public VectorR3 getNormal() {
        return this.normal;
    }

    /**
     * Moifica el parámetro donde se produce la intersección rayo figura.
     *
     * @param t nuevo parámetro t cuando ocurre la intersección.
     */
    public void setT(double t) {
	// Verificamos que el parámetro t y lo asignamos.
	this.t = Verificador.verificarPositivo(t,
					      "El parámetro t debe ser finito.",
					      "El parámetro t debe ser no negativo.");
    }

    /**
     * Modifica el punto de la intersección.
     *
     * @param punto nuevo vector que contiene el punto donde se
     *        produce la intersección.
     */
    public void setPunto(VectorR3 punto) {
	// Verificamos que el punto y lo asignamos.
        this.punto = Verificador.verificarNotNull(punto,
						  "El punto debe ser distinto de null.");
    }

    /**
     * Modifica el vector normal de la intersección rayo-figura.
     *
     * @param normal nuevo vector normal a la supeerficie de la
     *        intersección rayo-figura.
     */
    public void setNormal(VectorR3 normal) {
	// Verificamos que la normal y la asignamos.
        this.normal = Verificador.verificarVectorCero(normal,
						      "La normal perteneciente al plano no pueden ser null.",
						      "El vector normal debe ser distinto del vector cero.").vectorUnitario();
    }
}

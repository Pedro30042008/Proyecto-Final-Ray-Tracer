package matematicas;

/**
 * Clase auxiliar para verificar 
 */
public class Verificador {

    /* Tolerancia para la comparación entre números. */
    public static final double TOLERANCIA = 1e-9;
    
    /**
     * Método auxiliar para verificar desbordamientos en operaciones
     * binarias o unarias.
     *
     * @param valor número a evaluar de una operación resultante.
     * @param operacion nombre de la operación que se realiza.
     * @param operando1 primer operando involucrado en la operación.
     * @param operando2 posible segundo operando involucrado en la operación.
     * @return valor real finito resultante.
     * @throws ArithmeticException si el valor resultante no es un real finito.
     */
    public static double verificarOperacion(double valor, String operacion, String operando1, String operando2){
	// Si no es representable el valor resultante de la operación.
	if (!Double.isFinite(valor)){
	    // Verificamos si es una operación del vector o con otro.
	    if (operando2 == null)
		// Lanzamos un error.
		throw new ArithmeticException("El valor resultante de la operación" +
					      operacion +
					      "con el operando: " + operando1 +
					      ", resulto no finito.");
	    // Lanzamos un error.
	    throw new ArithmeticException("El valor resultante de la operación" +
					  operacion +
					  "con el operando 1: " + operando1 +
					  " y el operando 2: " + operando2 +
					  ", resulto no finito.");
	}
	// En caso contrario, retornamos el valor.
	return valor;
    }

    /**
     * Metodo auxiliar para lanzar excepción con números inválidos
     * y evitar el cero negativo.
     *
     * @param numero real a validar.
     * @param mensaje texto que aparecera para indicar el error.
     * @return el número validado y sin cero negativo.
     * @throws IllegalArgumentException si el número dado no es finito real.
     */
    public static double verificarNumero(double numero, String mensaje) {
	// Verificamos que sea un número finito real.
	if (!Double.isFinite(numero))
	    // Lanzamos un error si no es finito real.
	    throw new IllegalArgumentException(mensaje);
	// Retornamos el número real evitando ceros negativos.
	return (numero == 0.0) ? 0.0 : numero;
    }

    /**
     * Metodo auxiliar para lanzar excepción cuando un número no es un
     * real finito positivo o cero.
     *
     * @param numero número a verificar si es positivo o cero.
     * @param mensaje1 mensaje lanzado si no es un número finito.
     * @param mensaje2 mensaje lanzado si es negativo.
     * @throws IllegalArgumentException si el número no es real finito
     *         finito positivo o cero.
     */
    public static double verificarPositivo(double numero, String mensaje1, String mensaje2) {
	// Verificamos que sea un real finito.
	double num = verificarNumero(numero, mensaje1);
	// Verificamos que no sea negativo.
	if (num < 0)
	    // Lanzamos un error si es negativo.
	    throw new IllegalArgumentException(mensaje2);
	// Retornamos el número real evitando ceros negativos.
	return num;
    }
    
    /**
     * Metodo auxiliar para lanzar excepción cuando un color es
     * inválido.
     *
     * @param color vector que contiene las componentes del color en RGB.
     * @param mensaje en caso de lanzar una excepcion con mensaje.
     * @throws IllegalArgumentException si el vector dado en alguna de
     *         sus componentes está fuera del rango [0, 1].
     */
    public static VectorR3 verificarColor(VectorR3 color) {
	// Verificamos que no sea nulo.
	verificarNotNull(color, "El color debe ser distinto de null.");
	// Cada componente RGB debe estar entre 0 y 1.
        if (!(color.getX() >= 0 && color.getX() <= 1
           && color.getY() >= 0 && color.getY() <= 1
           && color.getZ() >= 0 && color.getZ() <= 1))
            throw new IllegalArgumentException("Las componentes RGB deben estar entre 0 y 1.");
	// Retornamos el color validado.
	return color;
    }

    /**
     * Metodo auxiliar para lanzar excepción cuando un objeto es
     * <code>null</code>.
     *
     * @param objeto algún objeto a comprobar que sea distinto de null.
     * @param mensaje en caso de ser null lanzamos la excepción con mensaje.
     * @throws IllegalArgumentException si el vector dado es
     *         <code>null</code>.
     */
    public static <T> T verificarNotNull(T objeto, String mensaje) {	
	// Verificamos que sea destinto de null el objeto
	if (objeto == null)
	    // Lanzamos una excepción con mensaje.
	    throw new IllegalArgumentException(mensaje);
	// Retornamos el objeto distinto de null.
	return objeto;
    }

    /**
     * Metodo auxiliar para verificar que no sea el vector cero.
     *
     * @param vector vector a verificar que no sea el vector cero.
     * @param mensaje1 texto lanzado si es <code>null</code> el vector.
     * @param mensaje2 texto lanzado si es el vector cero.
     * @throws IllegalArgumentException si el vector dado es el vector cero.
     */
    public static VectorR3 verificarVectorCero(VectorR3 vector, String mensaje1, String mensaje2) {
	// Verificamos que sea distinto de null.
	verificarNotNull(vector, mensaje1);
	// Verificamos que el vector sea destinto del vector cero.
	if (vector.esCero())
	    // Lanzamos una excepción con mensaje.
	    throw new IllegalArgumentException(mensaje2);
	// Retornamos el vector distinto del vector cero.
	return vector;
    }
}

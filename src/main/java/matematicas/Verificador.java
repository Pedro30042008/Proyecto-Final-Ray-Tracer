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
}

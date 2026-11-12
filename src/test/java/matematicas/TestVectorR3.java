package matematicas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Pruebas unitarias para la clase {@link VectorR3}.
 *
 * Verifica el funcionamiento de las operaciones entre vectores
 * necesarias para los cálculos geométricos del Ray Tracer.
 */
public class TestVectorR3 {

    /* Tolerancia para comparar resultados aproximados. */
    private static final double TOLERANCIA = 1e-9;

    /* Vectores canónicos utilizados en las pruebas. */
    private VectorR3 e1;
    private VectorR3 e2;
    private VectorR3 e3;

    /**
     * Inicializa los vectores canónicos antes de cada prueba.
     */
    @BeforeEach
    public void setUp() {
        // Construimos los tres vectores de la base canónica.
        e1 = new VectorR3(1, 0, 0);
        e2 = new VectorR3(0, 1, 0);
        e3 = new VectorR3(0, 0, 1);
    }

    /**
     * Prueba unitaria para los getters de {@link VectorR3}.
     */
    @Test
    public void testGetters() {
        // Verificamos sus componentes.
        Assertions.assertEquals(1, e1.getX());
        Assertions.assertEquals(0, e1.getY());
        Assertions.assertEquals(0, e1.getZ());
    }
    
    /**
     * Prueba unitaria para el constructor de {@link VectorR3}.
     */
    @Test
    public void testConstructor() {
        // Construimos un vector de componentes conocidas.
        VectorR3 vector = new VectorR3(2, -3, 6);

        // Verificamos sus componentes.
        Assertions.assertEquals(2, vector.getX());
        Assertions.assertEquals(-3, vector.getY());
        Assertions.assertEquals(6, vector.getZ());

	// Construimos un vector con componentes negativas y positivas de cero.
        vector = new VectorR3(-0.0, 0.0, -0.0);

        // Verificamos que las componentes sean cero positivo.
        Assertions.assertEquals(0.0, vector.getX());
        Assertions.assertEquals(0.0, vector.getY());
        Assertions.assertEquals(0.0, vector.getZ());
    }

    /**
     * Prueba unitaria para el equals {@link VectorR3}.
     */
    @Test
    public void testEquals() {
	// Verificamos que el vector no sea igual a  null.
	Assertions.assertFalse(e1.equals(null));
	// Verificamos la igualdad del mismo vector.
        Assertions.assertTrue(e1.equals(e1));
	// Verificamos la igualdad con otro vector.
        VectorR3 otro = new VectorR3(1, 0, 0);
        Assertions.assertTrue(e1.equals(otro));
        Assertions.assertTrue(otro.equals(e1));
	// Verificamos la desigualdad con otro vector.
        Assertions.assertFalse(e1.equals(e2));
	// Verificamos la desigualdad con otras clases.
        Assertions.assertFalse(e1.equals("vector"));
    }

    
    /**
     * Prueba unitaria para {@link VectorR3#suma(VectorR3)}.
     */
    @Test
    public void testSuma() {
        // Construimos un vector con componentes positivas y negativas.
        VectorR3 vector = new VectorR3(8, 6, -4);

        // Realizamos la suma entre vectores.
        VectorR3 resultado = vector.suma(e2.suma(e3));

	// Construimos el vector esperado (8, 7, -3).
	VectorR3 esperado = new VectorR3(8, 7, -3);
	
        // Verificamos el vector resultante.
        Assertions.assertEquals(esperado, resultado);
    }

    /**
     * Prueba unitaria para {@link VectorR3#resta(VectorR3)}.
     */
    @Test
    public void testResta() {
        // Construimos un vector con componentes positivas y negativas.
        VectorR3 vector = new VectorR3(8, 6, -4);

        // Realizamos la resta entre vectores.
        VectorR3 resultado = vector.resta(e2.resta(e3));

	// Construimos el vector esperado (8, 5, -3).
	VectorR3 esperado = new VectorR3(8, 5, -3);
	
        // Verificamos el vector resultante.
        Assertions.assertEquals(esperado, resultado);
    }

    /**
     * Pruebas parametrizadas para {@link VectorR3#productoPorUnEscalar(double)}.
     *
     * Verifica el producto por un escalar y por cero.
     *
     * @param escalar número real utilizado en el producto.
     * @param x componente X esperada.
     * @param y componente Y esperada.
     * @param z componente Z esperada.
     */
    @ParameterizedTest
    @CsvSource({
        "-5, -5, 10, 15",
        "0, 0, 0, 0"
    })
    public void testProductoPorUnEscalar(double escalar,
                                   double x, double y, double z) {
        // Construimos el vector.
        VectorR3 vector = new VectorR3(1, -2, -3);

        // Realizamos el producto por el escalar.
        VectorR3 resultado = vector.productoPorUnEscalar(escalar);

	// Vector esperado.
	VectorR3 esperado = new VectorR3(x, y, z);

        // Verificamos el vector resultante.
        Assertions.assertEquals(esperado, resultado);
    }

    /**
     * Pruebas parametrizadas para {@link VectorR3#productoCruz(VectorR3)}.
     *
     * Verifica la orientación, el orden de los factores, los vectores
     * paralelos y un caso general.
     *
     * @param x1 componente X del primer vector.
     * @param y1 componente Y del primer vector.
     * @param z1 componente Z del primer vector.
     * @param x2 componente X del segundo vector.
     * @param y2 componente Y del segundo vector.
     * @param z2 componente Z del segundo vector.
     * @param xEsperada componente X esperada del resultado.
     * @param yEsperada componente Y esperada del resultado.
     * @param zEsperada componente Z esperada del resultado.
     */
    @ParameterizedTest
    @CsvSource({
        "1, 0, 0, 0, 1, 0, 0, 0, 1",
        "0, 1, 0, 1, 0, 0, 0, 0, -1",
        "1, 2, 3, 2, 4, 6, 0, 0, 0",
        "1, 2, 3, 4, 5, 6, -3, 6, -3"
    })
    public void testProductoCruz(double x1, double y1, double z1,
                                 double x2, double y2, double z2,
                                 double xEsperada, double yEsperada, double zEsperada) {
        // Preparamos los dos vectores.
        VectorR3 primero = new VectorR3(x1, y1, z1);
        VectorR3 segundo = new VectorR3(x2, y2, z2);

        // Calculamos el producto cruz.
        VectorR3 resultado = primero.productoCruz(segundo);

	// Construimos el vector esperado.
	VectorR3 esperado = new VectorR3(xEsperada, yEsperada, zEsperada);
	
        // Comparamos las tres componentes.
        Assertions.assertEquals(esperado, resultado);
    }

    /**
     * Pruebas parametrizadas para {@link VectorR3#productoPunto(VectorR3)}.
     *
     * Verifica el producto punto de vectores perpendiculares
     * y no perpendiculares.
     *
     * @param x1 componente X del primer vector.
     * @param y1 componente Y del primer vector.
     * @param z1 componente Z del primer vector.
     * @param x2 componente X del segundo vector.
     * @param y2 componente Y del segundo vector.
     * @param z2 componente Z del segundo vector.
     * @param esperado resultado esperado del producto punto.
     */
    @ParameterizedTest
    @CsvSource({
        "-2, -4, 6, -2, 4, -6, -48",
        "1, 0, 0, 0, 1, 0, 0"
    })
    public void testProductoPunto(double x1, double y1, double z1,
                                  double x2, double y2, double z2,
                                  double esperado) {
        // Construimos los vectores.
        VectorR3 a = new VectorR3(x1, y1, z1);
        VectorR3 b = new VectorR3(x2, y2, z2);

        // Realizamos el producto punto.
        double resultado = a.productoPunto(b);

        // Verificamos el resultado esperado.
        Assertions.assertEquals(esperado, resultado);
    }

    /**
     * Pruebas parametrizadas para {@link VectorR3#norma()}.
     *
     * Verifica la norma de un vector y del vector cero.
     *
     * @param x componente X del vector.
     * @param y componente Y del vector.
     * @param z componente Z del vector.
     * @param esperado norma esperada del vector.
     */
    @ParameterizedTest
    @CsvSource({
        "6, -3, 2, 7",
        "0, 0, 0, 0"
    })
    public void testNorma(double x, double y, double z, double esperado) {
        // Construimos el vector.
        VectorR3 vector = new VectorR3(x, y, z);

        // Calculamos la norma.
        double resultado = vector.norma();

        // Verificamos la norma esperada.
        Assertions.assertEquals(esperado, resultado);
    }

    /**
     * Prueba unitaria para {@link VectorR3#vectorUnitario()}.
     */
    @Test
    public void testVectorUnitario() {
        // Construimos un vector de norma 5.
        VectorR3 vector = new VectorR3(0, 3, 4);

        // Calculamos el vector unitario.
        VectorR3 resultado = vector.vectorUnitario();

        // Verificamos las componentes del vector unitario con tolerancia.
        Assertions.assertEquals(0, resultado.getX(), TOLERANCIA);
        Assertions.assertEquals(0.6, resultado.getY(), TOLERANCIA);
        Assertions.assertEquals(0.8, resultado.getZ(), TOLERANCIA);

        // Verificamos que la norma del resultado sea uno.
        Assertions.assertEquals(1, resultado.norma(), TOLERANCIA);

	// Construimos el vector cero.
        VectorR3 cero = e1.productoPorUnEscalar(0);

        // Verificamos que lance la excepción esperada.
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> cero.vectorUnitario()
        );
    }

    /**
     * Prueba unitaria para {@link VectorR3#esCero()}.
     */
    @Test
    public void testEsCero() {
        // Construimos el vector cero.
        VectorR3 cero = new VectorR3(-0.0, 0.0, 0.0);

        // Verificamos que solo el primer vector sea cero.
        Assertions.assertTrue(cero.esCero());
        Assertions.assertFalse(e1.esCero());
    }

    /**
     * Prueba unitaria para verificar los desbordamientos
     * en las operaciones de {@link VectorR3}.
     */
    @Test
    public void testDesbordamientos() {
	// Construimos vectores con componentes finitas grandes.
	VectorR3 grande = new VectorR3(Double.MAX_VALUE, 0, 0);
	VectorR3 negativo = new VectorR3(-Double.MAX_VALUE, 0, 0);
	VectorR3 doble = new VectorR3(2, 0, 0);
	VectorR3 vertical = new VectorR3(0, 2, 0);
	
	// Verificamos el desbordamiento en la suma.
	Assertions.assertThrows(
				ArithmeticException.class,
				() -> grande.suma(grande)
				);
	
	// Verificamos el desbordamiento en la resta.
	Assertions.assertThrows(
				ArithmeticException.class,
				() -> negativo.resta(grande)
				);
	
	// Verificamos el desbordamiento en el producto por escalar.
	Assertions.assertThrows(
				ArithmeticException.class,
				() -> grande.productoPorUnEscalar(2)
				);
	
	// Verificamos el desbordamiento en el producto punto.
	Assertions.assertThrows(
				ArithmeticException.class,
				() -> grande.productoPunto(doble)
				);
	
	// Verificamos el desbordamiento en el producto cruz.
	Assertions.assertThrows(
				ArithmeticException.class,
				() -> grande.productoCruz(vertical)
				);
	
	// Verificamos el desbordamiento en el cálculo de la norma.
	Assertions.assertThrows(
				ArithmeticException.class,
				() -> grande.norma()
				);
    }
    
    /**
     * Prueba unitaria para toString de {@link VectorR3}.
     */
    @Test
    public void testToString() {
        // Verificamos sus componentes.
        Assertions.assertEquals("(1.0, 0.0, 0.0)", e1.toString());
    }
}

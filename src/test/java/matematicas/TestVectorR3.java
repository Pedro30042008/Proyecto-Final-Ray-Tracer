package matematicas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Pruebas unitarias para la clase {@link VectorR3}.
 *
 * Verifica el funcionamiento de las operaciones entre vectores.
 */
public class TestVectorR3 {

    /* Tolerancia para comparar resultados aproximados. */
    private static final double TOLERANCIA = 1e-9;

    /* Vectores canónicos utilizados en las pruebas. */
    private VectorR3 e1;
    private VectorR3 e2;
    private VectorR3 e3;

    /* Vectores con componentes al límite permitido. */
    private VectorR3 limitePositivo;
    private VectorR3 limiteNegativo;

    /* Vector cero. */
    private VectorR3 cero;
    
    /**
     * Inicializa los vectores canónicos antes de cada prueba.
     */
    @BeforeEach
    public void setUp() {
        // Construimos los tres vectores de la base canónica.
        e1 = new VectorR3(1, 0, 0);
        e2 = new VectorR3(0, 1, 0);
        e3 = new VectorR3(0, 0, 1);
	// Construimos los vectores con componentes al límite permitido.
	limitePositivo = new VectorR3(Double.MAX_VALUE, 0, 0);
	limiteNegativo = new VectorR3(-Double.MAX_VALUE, 0, 0);
	// Construimos el vector cero.
	cero = new VectorR3(0, 0, 0);
    }

    /**
     * Prueba unitaria para el constructor de {@link VectorR3}.
     */
    @Test
    public void testConstructor() {
        // Construimos un vector de componentes conocidas.
        VectorR3 vector = new VectorR3(2, -3, 6);

        // Verificamos sus componentes.
        assertEquals(2, vector.getX());
        assertEquals(-3, vector.getY());
        assertEquals(6, vector.getZ());

	// Construimos un vector con componentes negativas y positivas de cero.
        vector = new VectorR3(-0.0, 0.0, -0.0);

        // Verificamos que las componentes sean cero positivo.
        assertEquals(0.0, vector.getX());
        assertEquals(0.0, vector.getY());
        assertEquals(0.0, vector.getZ());

	// Verificamos la invalidación de la componentes no definidas.
        assertThrows(
		     IllegalArgumentException.class,
		     () -> new VectorR3(Double.POSITIVE_INFINITY, 0, 0)
		     );
        assertThrows(
		     IllegalArgumentException.class,
		     () -> new VectorR3(0, Double.POSITIVE_INFINITY, 0)
		     );
        assertThrows(
		     IllegalArgumentException.class,
		     () -> new VectorR3(0, 0, Double.POSITIVE_INFINITY)
		     );
        assertThrows(
		     IllegalArgumentException.class,
		     () -> new VectorR3(Double.NaN, 0, 0)
		     );
        assertThrows(
		     IllegalArgumentException.class,
		     () -> new VectorR3(0, Double.NaN, 0)
		     );
        assertThrows(
		     IllegalArgumentException.class,
		     () -> new VectorR3(0, 0, Double.NaN)
		     );
    }
    
    /**
     * Prueba unitaria para los getters de {@link VectorR3}.
     */
    @Test
    public void testGetters() {
        // Verificamos sus componentes.
        assertEquals(1, e1.getX());
        assertEquals(0, e1.getY());
        assertEquals(0, e1.getZ());
    }

    /**
     * Prueba unitaria para los setters de {@link VectorR3}.
     */
    @Test
    public void testSetters() {
	// Modificamos al vector e1.
	e1.setX(-1);
	e1.setY(-1);
        e1.setZ(-1);
        // Verificamos sus componentes.
        assertEquals(-1, e1.getX());
        assertEquals(-1, e1.getY());
        assertEquals(-1, e1.getZ());
	// Modificamos al vector e1.
	e1.setX(-0.0);
	e1.setY(-0.0);
        e1.setZ(-0.0);
	// Verificamos que las componentes sean cero positivo.
        assertEquals(0.0, e1.getX());
        assertEquals(0.0, e1.getY());
        assertEquals(0.0, e1.getZ());
	// Verificamos la invalidación de la componentes no definidas.
        assertThrows(
		     IllegalArgumentException.class,
		     () -> e1.setX(Double.POSITIVE_INFINITY)
		     );
	assertThrows(
		     IllegalArgumentException.class,
		     () -> e1.setY(Double.POSITIVE_INFINITY)
		     );
	assertThrows(
		     IllegalArgumentException.class,
		     () -> e1.setZ(Double.POSITIVE_INFINITY)
		     );
	assertThrows(
		     IllegalArgumentException.class,
		     () -> e1.setX(Double.NaN)
		     );
	assertThrows(
		     IllegalArgumentException.class,
		     () -> e1.setY(Double.NaN)
		     );
        assertThrows(
		     IllegalArgumentException.class,
		     () -> e1.setZ(Double.NaN)
		     );
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
        assertEquals(esperado, resultado);
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
        assertEquals(esperado, resultado);
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
	"-5, -5, 10, 15", // Verificamos un caso en general.
	"0, 0, 0, 0" // Verificamos que el producto con el escalar cero de el vector nulo.
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
        assertEquals(esperado, resultado);
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
	"-2, -4, 6, -2, 4, -6, -48", // Producto punto con resultado negativo.
	"1, 0, 0, 1, 0, 0, 1", // Producto punto con resultado positivo.
	"1, 0, 0, 0, 1, 0, 0" // Producto punto con resultado cero.
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
        assertEquals(esperado, resultado);
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
	"1, 0, 0, 0, 1, 0, 0, 0, 1", // Verificamos que nos de el vector perpendicualar e3 a los canonicos e1 y e2.
	"0, 1, 0, 1, 0, 0, 0, 0, -1", // Verificamos que invertir el orden del producto cruz entre e1 y e2 nos de -e3.
	"1, 2, 3, 2, 4, 6, 0, 0, 0", // Verificamos que vectores paralelos den el vector cero.
	"1, 2, 3, 4, 5, 6, -3, 6, -3" // Verificamos el funcionamiento en general del producto cruz.
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
        assertEquals(esperado, resultado);
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
	"6, -3, 2, 7", // Verificamos un caso en general.
	"0, 0, 0, 0" // Verificamos que la norma del vector nulo sea cero.
    })
    public void testNorma(double x, double y, double z, double esperado) {
        // Construimos el vector.
        VectorR3 vector = new VectorR3(x, y, z);

        // Calculamos la norma.
        double resultado = vector.norma();

        // Verificamos la norma esperada.
        assertEquals(esperado, resultado);
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
        assertEquals(0, resultado.getX(), TOLERANCIA);
        assertEquals(0.6, resultado.getY(), TOLERANCIA);
        assertEquals(0.8, resultado.getZ(), TOLERANCIA);

        // Verificamos que la norma del resultado sea uno.
        assertEquals(1, resultado.norma(), TOLERANCIA);

        // Verificamos que lance la excepción esperada con el vector cero.
        assertThrows(
		     IllegalArgumentException.class,
		     () -> cero.vectorUnitario()
		     );
    }

    /**
     * Prueba unitaria para {@link VectorR3#esCero()}.
     */
    @Test
    public void testEsCero() {
        // Verificamos que solo el primer vector sea cero.
        assertTrue(cero.esCero());
        assertFalse(e1.esCero());
    }

    /**
     * Prueba unitaria para verificar los desbordamientos
     * en las operaciones de {@link VectorR3}.
     */
    @Test
    public void testDesbordamientos() {
	// Verificamos el desbordamiento en la suma.
        assertThrows(
		     ArithmeticException.class,
		     () -> limitePositivo.suma(limitePositivo)
		     );
	
	// Verificamos el desbordamiento en la resta.
        assertThrows(
		     ArithmeticException.class,
		     () -> limiteNegativo.resta(limitePositivo)
		     );
	
	// Verificamos el desbordamiento en el producto por escalar.
        assertThrows(
		     ArithmeticException.class,
		     () -> limitePositivo.productoPorUnEscalar(2)
		     );
	
	// Verificamos el desbordamiento en el producto punto.
        assertThrows(
		     ArithmeticException.class,
		     () -> limitePositivo.productoPunto(limitePositivo)
		     );

	// Vector que provoca desbordamiento del nuevo vector con el producto cruz.
	VectorR3 vectorGrande = new VectorR3(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE);
	
	// Verificamos el desbordamiento en el producto cruz.
        assertThrows(
		     ArithmeticException.class,
		     () -> limitePositivo.productoCruz(vectorGrande)
		     );
	
	// Verificamos el desbordamiento en el cálculo de la norma.
	assertThrows(
		     ArithmeticException.class,
		     () -> vectorGrande.norma()
		     );
    }

    /**
     * Prueba unitaria para el equals {@link VectorR3}.
     */
    @Test
    public void testEquals() {
	// Verificamos que el vector no sea igual a  null.
        assertFalse(e1.equals(null));
	
	// Verificamos la igualdad del mismo vector.
        assertTrue(e1.equals(e1));
	
	// Verificamos la igualdad con otro vector.
        VectorR3 otro = new VectorR3(1, 0, 0);
        assertTrue(e1.equals(otro));
        assertTrue(otro.equals(e1));
	
	// Verificamos la desigualdad con otro vector.
        assertFalse(e1.equals(e2));
	
	// Verificamos la desigualdad con otras clases.
        assertFalse(e1.equals("vector"));
    }
    
    /**
     * Prueba unitaria para toString de {@link VectorR3}.
     */
    @Test
    public void testToString() {
        // Verificamos sus componentes.
        assertEquals("(1.0, 0.0, 0.0)", e1.toString());
    }
}

package matematicas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Pruebas unitarias para la clase {@link Rayo}.
 *
 * Verifica el origen, la dirección y el cálculo de puntos
 * de un rayo.
 */
public class TestRayo {

    /* Rayo con origen y dirección válidas.*/
    private Rayo rayo;

    /**
     * Inicializa el rayo antes de cada prueba.
     */
    @BeforeEach
    public void setUp() {
        // Construimos el origen y dirección del rayo.
        VectorR3 origen = new VectorR3(1, 2, 3);
        VectorR3 direccion = new VectorR3(2, 0, -4);

        // Construimos el rayo con los datos anteriores.
        rayo = new Rayo(origen, direccion);
    }
    
    /**
     * Prueba unitaria para el constructor de {@link Rayo}.
     *
     * Verifica que conserve el origen y la dirección recibidos.
     */
    @Test
    public void testConstructor() {
        // Construimos un rayo.
        VectorR3 origen = new VectorR3(2, 2, 2);
        VectorR3 direccion = new VectorR3(0, 1, -1);
	Rayo rayo = new Rayo(origen, direccion);
	
        // Verificamos las componentes del origen y la dirección.
        assertEquals(origen, rayo.getOrigen());
        assertEquals(direccion, rayo.getDireccion());

        // Verificamos que el origen no pueda ser null.
        assertThrows(
		     IllegalArgumentException.class,
		     () -> new Rayo(null, direccion)
		     );

        // Verificamos que la dirección no pueda ser null.
        assertThrows(
		     IllegalArgumentException.class,
		     () -> new Rayo(origen, null)
		     );

        // Verificamos que la dirección no pueda ser el vector cero.
        assertThrows(
		     IllegalArgumentException.class,
		     () -> new Rayo(origen, new VectorR3(0, 0, 0))
		     );
    }
    
    /**
     * Prueba unitaria para los getters de {@link Rayo}.
     *
     * Verifica que obtenga el origen y la dirección correctamente.
     */
    @Test
    public void testGetters() {
        // Construimos los vectores esperados.
        VectorR3 origen = new VectorR3(1, 2, 3);
        VectorR3 direccion = new VectorR3(2, 0, -4);
	
        // Verificamos las componentes del origen y la dirección.
        assertEquals(origen, rayo.getOrigen());
        assertEquals(direccion, rayo.getDireccion());
    }

    /**
     * Prueba unitaria para los setters de {@link Rayo}.
     *
     * Verifica que modifique el origen y la dirección correctamente.
     */
    @Test
    public void testSetters() {
        // Construimos los nuevos vectores.
        VectorR3 origen = new VectorR3(3, 3, 3);
        VectorR3 direccion = new VectorR3(3, 3, 3);

	// Modificamos los vectores.
	rayo.setOrigen(origen);
	rayo.setDireccion(direccion);
	
        // Verificamos las componentes del origen y la dirección.
        assertEquals(origen, rayo.getOrigen());
        assertEquals(direccion, rayo.getDireccion());

	// Verificamos que el origen no pueda ser null.
        assertThrows(
		     IllegalArgumentException.class,
		     () -> rayo.setOrigen(null)
		     );

        // Verificamos que la dirección no pueda ser null.
        assertThrows(
		     IllegalArgumentException.class,
		     () -> rayo.setDireccion(null)
		     );

        // Verificamos que la dirección no pueda ser el vector cero.
        assertThrows(
		     IllegalArgumentException.class,
		     () -> rayo.setDireccion(new VectorR3(0, 0, 0))
		     );
    }
    
    /**
     * Pruebas parametrizadas para {@link Rayo#puntoEnT(double)}.
     *
     * Verifica que R(0) sea el origen y que se calcule O + tD
     * correctamente dado un rayo.
     *
     * @param t parámetro de la trayectoria.
     * @param x componente X esperada del punto.
     * @param y componente Y esperada del punto.
     * @param z componente Z esperada del punto.
     */
    @ParameterizedTest
    @CsvSource({
	"0, 1, 2, 3", // Verifica t = 0.
	"2, 5, 2, -5", // Verifica t > 0.
    })
    public void testPuntoEnT(double t, double x, double y, double z) {
        // Calculamos el punto correspondiente al parámetro t.
        VectorR3 resultado = rayo.puntoEnT(t);

	// Construimos el vector esperado.
	VectorR3 esperado = new VectorR3(x, y, z);

        // Verificamos el punto esperado de la trayectoria.
        assertEquals(esperado, resultado);
    }

    /**
     * Prueba unitaria para verificar t invalida en
     * {@link Rayo#puntoEnT(double)}.
     */
    public void testParametroTInvalido() {
	// Verifica que no se tomen valores infinitos.
        assertThrows(
		     IllegalArgumentException.class,
		     () -> rayo.puntoEnT(Double.POSITIVE_INFINITY)
		     );
	assertThrows(
		     IllegalArgumentException.class,
		     () -> rayo.puntoEnT(Double.NaN)
		     );
	// Verificamos que no considere t negativo.
        assertThrows(
		     IllegalArgumentException.class,
		     () -> rayo.puntoEnT(-2)
		     );
    }
}

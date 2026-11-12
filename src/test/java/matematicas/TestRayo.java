package matematicas;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Pruebas unitarias para la clase {@link Rayo}.
 *
 * Verifica el origen, la dirección y el cálculo de puntos
 * de la trayectoria mediante R(t) = O + tD.
 */
public class TestRayo {

    /* Rayo con dirección no unitaria utilizado en las pruebas. */
    private Rayo rayo;

    /**
     * Inicializa el rayo antes de cada prueba.
     */
    @BeforeEach
    public void setUp() {
        // Construimos el origen y una dirección de norma distinta de uno.
        VectorR3 origen = new VectorR3(1, 2, 3);
        VectorR3 direccion = new VectorR3(2, 0, -4);

        // Construimos el rayo.
        rayo = new Rayo(origen, direccion);
    }

    /**
     * Prueba unitaria para los getters de {@link Rayo}.
     *
     * Verifica que obtenga el origen y la dirección correctos.
     */
    @Test
    public void testGetters() {
        // Construimos los vectores esperados.
        VectorR3 origen = new VectorR3(1, 2, 3);
        VectorR3 direccion = new VectorR3(2, 0, -4);
	
        // Verificamos las componentes del origen y la dirección.
	Assertions.assertEquals(origen, rayo.getOrigen());
	Assertions.assertEquals(direccion, rayo.getDireccion());
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
	Assertions.assertEquals(origen, rayo.getOrigen());
	Assertions.assertEquals(direccion, rayo.getDireccion());

        // Verificamos que el origen no pueda ser null.
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new Rayo(null, direccion)
        );

        // Verificamos que la dirección no pueda ser null.
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new Rayo(origen, null)
        );

        // Verificamos que la dirección no pueda ser el vector cero.
        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new Rayo(origen, new VectorR3(0, 0, 0))
        );
    }
    
    /**
     * Pruebas parametrizadas para {@link Rayo#puntoEnT(double)}.
     *
     * Verifica que R(0) sea el origen y que se calcule O + tD
     * utilizando una dirección no unitaria.
     *
     * @param t parámetro de la trayectoria.
     * @param x componente X esperada del punto.
     * @param y componente Y esperada del punto.
     * @param z componente Z esperada del punto.
     */
    @ParameterizedTest
    @CsvSource({
        "0, 1, 2, 3",
        "2, 5, 2, -5",
        "5, 11, 2, -17"
    })
    public void testPuntoEnT(double t, double x, double y, double z) {
        // Calculamos el punto correspondiente al parámetro t.
        VectorR3 resultado = rayo.puntoEnT(t);

	// Construimos el vector esperado.
	VectorR3 esperado = new VectorR3(x, y, z);

        // Verificamos el punto esperado de la trayectoria.
        Assertions.assertEquals(esperado, resultado);
    }
}

package geometria;

import matematicas.Rayo;
import matematicas.VectorR3;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Pruebas unitarias para la clase {@link Esfera}.
 *
 * Verifica la construcción de una esfera y sus intersecciones con rayos
 * mediante ejemplos geométricos con resultados conocidos.
 */
public class TestEsfera {

    /* Esfera centrada sobre el eje Z, con radio 2. */
    private Esfera esfera;

    /**
     * Construye la esfera utilizada en las pruebas de intersección.
     */
    @BeforeEach
    public void setUp() {
        // Preparamos una esfera con centro (0, 0, -5) y radio 2.
        VectorR3 centro = new VectorR3(0, 0, -5);
        VectorR3 color = new VectorR3(0.9, 0.3, 0.2);
        esfera = new Esfera(centro, 2, color);
    }

    /**
     * Verifica que la esfera se construya con datos válidos
     * y rechace los argumentos inválidos.
     */
    @Test
    public void testConstructor() {
	// Preparamos valores válidos para centro y color.
        VectorR3 centro = new VectorR3(1, 1, 1);
        VectorR3 color = new VectorR3(0, 0, 1);

	// Construimos una esfera válida.
	Esfera esferaValida = new Esfera(centro, 2, color);

	// Validamos la esfera.
	Assertions.assertEquals(centro, esferaValida.getCentro());
	Assertions.assertEquals(2, esferaValida.getRadio());
	Assertions.assertEquals(color, esferaValida.getColor());
	
        // Un radio cero no representa una esfera válida.
        Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Esfera(centro, 0, color));

	// Un radio negativo no representa una esfera válida.
        Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Esfera(centro, -1, color));
	
	// No se puede construir una esfera con centro null.
        Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Esfera(null, 2, new VectorR3(1, 0, 0)));

	// No se puede construir una esfera con color null.
        Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Esfera(new VectorR3(0, 0, -5), 2, null));
	
        // El componente rojo no puede superar el valor 1.
        VectorR3 colorInvalido = new VectorR3(1.2, 0.3, 0.2);
	
        // Una esfera no debe aceptar ese color.
        Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Esfera(new VectorR3(0, 0, -5), 2, colorInvalido));
    }

    /**
     * Verifica los getters de la clase.
     */
    @Test
    public void testGetters() {
	// Preparamos valores de la esfera.
	VectorR3 centro = new VectorR3(0, 0, -5);
        VectorR3 color = new VectorR3(0.9, 0.3, 0.2);
	
	// Validamos la esfera.
	Assertions.assertEquals(centro, esfera.getCentro());
	Assertions.assertEquals(2, esfera.getRadio());
	Assertions.assertEquals(color, esfera.getColor());
    }

    /**
     * Verifica que {@link Esfera#intersectar(Rayo)}
     * rechace un rayo nulo.
     */
    @Test
    public void testIntersectarRayoNulo() {
	// No se puede calcular una intersección si no existe un rayo.
        Assertions.assertThrows(IllegalArgumentException.class,
				() -> esfera.intersectar(null));
    }
    
    /**
     * Pruebas parametrizadas para {@link Esfera#intersectar(Rayo)}.
     *
     * Verifica que se obtenga la primera intersección válida de un rayo con la esfera,
     * cuyo parámetro t sea no negativo. Para ello, comprueba el parámetro t, el punto de
     * intersección y el vector normal a la superficie.
     * 
     * @param xOrigen componente X del origen del Rayo.
     * @param yOrigen componente Y del origen del Rayo.
     * @param zOrigen componente Z del origen del Rayo.
     * @param xDireccion componente X de la dirección del Rayo.
     * @param yDireccion componente Y de la dirección del Rayo.
     * @param zDireccion componente Z de la dirección del Rayo.
     * @param xPunto componente X del punto de choque.
     * @param yPunto componente Y del punto de choque.
     * @param zPunto componente Z del punto de choque.
     * @param xNormal componente X del vector normal a la superficie.
     * @param yNormal componente Y del vector normal a la superficie.
     * @param zNormal componente Z del vector normal a la superficie.
     * @param t parámetro donde ocurre la intersección del rayo con la
     *        esfera.
     */
    @ParameterizedTest
    @CsvSource({
	    "0, 0, 0, 0, 0, -1, 0, 0, -3, 0, 0, 1, 3", // Verifica que se tome la primera intersección de un rayo frontal con la esfera.
	    "0, 0, -3, 0, 0, -1, 0, 0, -3, 0, 0, 1, 0", // Verifica que se tome la primera intersección de un rayo que apunta al interior de la esfera.
	    "0, 0, -3, 0, 0, 1, 0, 0, -3, 0, 0, 1, 0", // Verifica que se tome la primera intersección de un rayo que apunta al exterior de la esfera.
	    "0, 2, 0, 0, 0, -1, 0, 2, -5, 0, 1, 0, 5", // Verifica la intersección tangente, cuyo discriminante es cero y t es distinto de 0.
	    "0, 0, -5, 0, 0, 1, 0, 0, -3, 0, 0, 1, 2", // Verifica que se tome la intersección válida de un rayo originado al interior de la esfera.
	    "0, 0, 0, 0, 0, -2, 0, 0, -3, 0, 0, 1, 1.5", // Verifica que la intersección funcione con una dirección no unitaria.
	    "4, 0, -5, -1, 0, 0, 2, 0, -5, 1, 0, 0, 2" // Verifica la intersección del rayo en otra dirección distinta al eje Z.
    })
    public void testConInterseccionValida(double xOrigen, double yOrigen, double zOrigen,
				    double xDireccion, double yDireccion, double zDireccion,
				    double xPunto, double yPunto, double zPunto,
				    double xNormal, double yNormal, double zNormal,
				    double t) {
        // Definimos el rayo que choca con la esfera.
        Rayo rayo = new Rayo(new VectorR3(xOrigen, yOrigen, zOrigen), new VectorR3(xDireccion, yDireccion, zDireccion));

        // Buscamos la primera intersección.
        Interseccion resultado = esfera.intersectar(rayo);

        // Construimos el punto de intersección esperado.
	VectorR3 puntoEsperado = new VectorR3(xPunto, yPunto, zPunto);

	// Construimos el vector normal de la superficie en el punto del choque.
	VectorR3 normalEsperado = new VectorR3(xNormal, yNormal, zNormal);
	
	// Verificamos que haya una intersección distinta de null.
        Assertions.assertNotNull(resultado);

	// Comparamos si el parámetro t es el esperado.
        Assertions.assertEquals(t, resultado.getT());

	// Comparamos si obtenemos el punto de la intersección.
	Assertions.assertEquals(puntoEsperado, resultado.getPunto());

	// Comparamos si obtenemos el vector normal esperado.
	Assertions.assertEquals(normalEsperado, resultado.getNormal());
    }
    
    /**
     * Pruebas parametrizadas para {@link Esfera#intersectar(Rayo)}.
     *
     * Verifica que el método devuelva <code>null</code> cuando el rayo
     * no intersecta la esfera o cuando las intersecciones se encuentran detrás
     * del origen del rayo.
     *
     * @param xOrigen componente X del origen del Rayo.
     * @param yOrigen componente Y del origen del Rayo.
     * @param zOrigen componente Z del origen del Rayo.
     * @param xDireccion componente X de la dirección del Rayo.
     * @param yDireccion componente Y de la dirección del Rayo.
     * @param zDireccion componente Z de la dirección del Rayo.
     */
    @ParameterizedTest
    @CsvSource({
	    "0, 0, 0, 0, 1, 0", // Verifica que se descarten las intersecciones cuando el rayo pasa lejos de la esfera.
	    "0, 0, 0, 0, 0, 1", // Verifica que se descarten las intersecciones detrás del origen del rayo en dos puntos.
	    "0, 2, 0, 0, 0, 1" // Verifica que se descarte la intersección detrás del origen del rayo en un punto.
    })
    public void testSinInterseccionValida(double xOrigen, double yOrigen, double zOrigen, double xDireccion, double yDireccion, double zDireccion) {
        // Definimos el rayo que no choca con la esfera.
        Rayo rayo = new Rayo(new VectorR3(xOrigen, yOrigen, zOrigen), new VectorR3(xDireccion, yDireccion, zDireccion));

        // Verificamos que no exista una intersección válida con el rayo.
        Assertions.assertNull(esfera.intersectar(rayo));
    }
}

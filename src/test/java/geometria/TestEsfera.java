package geometria;

import matematicas.Rayo;
import matematicas.VectorR3;
import matematicas.Verificador;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Pruebas unitarias para la clase {@link Esfera}.
 *
 * Verifica la construcción de una esfera y sus intersecciones con rayos.
 */
public class TestEsfera {

    /* Esfera. */
    private Esfera esfera;

    /**
     * Construye una esfera conocida.
     */
    @BeforeEach
    public void setUp() {
	// Definimos una esfera con radio 1 y centro en (1, 1, 1).
        VectorR3 centro = new VectorR3(1, 1, 1);
        VectorR3 color = new VectorR3(1, 1, 1);
	double radio = 1;
        esfera = new Esfera(centro, radio, color);
    }

    /**
     * Verifica la cosntrucción de una esfera válida.
     */
    @Test
    public void testConstructor() {
	// Datos para una esfera con radio 2 y centro en (2, 3, -4).
        VectorR3 centro = new VectorR3(2, 3, -4);
        VectorR3 color = new VectorR3(0, 0, 0);
	double radio = 2;
	
	// Construimos una esfera válida.
	Esfera esferaValida = new Esfera(centro, 2, color);

	// Validamos la esfera con los datos esperados.
	assertEquals(centro, esferaValida.getCentro());
	assertEquals(radio, esferaValida.getRadio());
	assertEquals(color, esferaValida.getColor());

	// Definimos datos de entrada inválidos.

        // Esfera con radio cero no está bien definida.
        assertThrows(IllegalArgumentException.class,
		     () -> new Esfera(centro, 0, color));
	
	// Esfera con radio negativo no está bien definida.
        assertThrows(IllegalArgumentException.class,
		     () -> new Esfera(centro, -1, color));
	
	// Esfera con centro null no está bien definida.
        assertThrows(IllegalArgumentException.class,
		     () -> new Esfera(null, radio, color));

	// Esfera con color null no está bien definida.
        assertThrows(IllegalArgumentException.class,
		     () -> new Esfera(centro, radio, null));

	// Esfera con color fuera de rango [0-1] no está bien definida.
	VectorR3 colorInvalido = new VectorR3(1.2, 0.3, 0.2);

        assertThrows(IllegalArgumentException.class,
		     () -> new Esfera(centro, radio, colorInvalido));
    }

    /**
     * Verifica los getters de la clase.
     */
    @Test
    public void testGetters() {
	// Datos para una esfera con radio 1 y centro en (1, 1, 1).
	VectorR3 centro = new VectorR3(1, 1, 1);
        VectorR3 color = new VectorR3(1, 1, 1);
	double radio = 1;
	
	// Validamos los getters de la esfera construida al inicio.
	assertEquals(centro, esfera.getCentro());
	assertEquals(radio, esfera.getRadio());
	assertEquals(color, esfera.getColor());
    }

    /**
     * Verifica los setters de la clase.
     */
    @Test
    public void testSetters() {
	// Datos para una esfera con radio 1 y centro en (1, 1, 1).
	VectorR3 centro = new VectorR3(5, 5, 5);
        VectorR3 color = new VectorR3(0, 0, 0);
	double radio = 2;

	// Modificamos los datos.
	esfera.setCentro(centro);
	esfera.setRadio(radio);
	esfera.setColor(color);
	
	// Validamos los setters de la esfera construida al inicio.
	assertEquals(centro, esfera.getCentro());
	assertEquals(radio, esfera.getRadio());
	assertEquals(color, esfera.getColor());
    }
    
    /**
     * Verifica que {@link Esfera#intersectar(Rayo)}
     * rechace un rayo null.
     */
    @Test
    public void testIntersectarRayoNulo() {
	// No se puede calcular una intersección si no existe un rayo.
        assertThrows(IllegalArgumentException.class,
		     () -> esfera.intersectar(null));
    }
    
    /**
     * Pruebas parametrizadas para {@link Esfera#intersectar(Rayo)}.
     *
     * Verifica que se obtenga la primera intersección válida de un rayo
     * con la esfera, cuyo parámetro t sea no negativo. Para ello,
     * comprueba el parámetro t, el punto de intersección y el vector
     * normal a la superficie.
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
	    // Rayo con 'dos intersecciones' con la esfera. (Origen del rayo adentro de la esfera)
	    "1, 1, 1, 0.5, 0.5, 0.70710678118654752440, 1.5, 1.5, 1.70710678118654752440, -0.5, -0.5, -0.70710678118654752440, 1",
	    // Rayo con dos intersecciones con la esfera. (Origen del rayo afuera de la esfera)
	    "-3, -3, -4.65685424949238019520, 0.5, 0.5, 0.70710678118654752440, 0.5, 0.5, 0.29289321881345247560, -0.5, -0.5, -0.70710678118654752440, 7",
	    // Rayo con una intersección con la esfera. (Origen del rayo es parte de la esfera)
	    "0, 1, 1, 0, 2, 2, 0, 1, 1, -1, 0, 0, 0",
	    // Rayo con una intersección con la esfera. (Origen del rayo afuera de la esfera)
	    "0, 0, 0, 0, 2, 2, 0, 1, 1, -1, 0, 0, 0.5"
    })
    public void testConInterseccionValida(double xOrigen, double yOrigen, double zOrigen,
					  double xDireccion, double yDireccion, double zDireccion,
					  double xPunto, double yPunto, double zPunto,
					  double xNormal, double yNormal, double zNormal,
					  double t) {
	// Datos del rayo.
	VectorR3 origen = new VectorR3(xOrigen, yOrigen, zOrigen);
	VectorR3 direccion = new VectorR3(xDireccion, yDireccion, zDireccion);
	
        // Definimos el rayo.
        Rayo rayo = new Rayo(origen, direccion);

        // Buscamos la primera intersección.
        Interseccion resultado = esfera.intersectar(rayo);

        // Construimos el punto de intersección esperado.
	VectorR3 puntoEsperado = new VectorR3(xPunto, yPunto, zPunto);

	// Construimos el vector normal de la superficie en el punto del choque.
	VectorR3 normalEsperado = new VectorR3(xNormal, yNormal, zNormal);

	// Verificamos la intersección del rayo con el plano.
	verificarInterseccion(resultado, t, puntoEsperado, normalEsperado);
    }
    
    /**
     * Pruebas parametrizadas para {@link Esfera#intersectar(Rayo)}.
     *
     * Verifica que el método devuelva </code>null</code> cuando el rayo
     * no intersecta la esfera o cuando las intersecciones se encuentran
     * detrás del origen del rayo.
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
	    // Rayo no toca a la esfera.
	    "0, 0, 5, 1, 0, 0",
	    // Rayo con 'una interseccion' detrás del origen del rayo.
	    "10, 1, 0, 1, 0, 0",
	    // Rayo con 'dos intersecciones' detrás del origen del rayo.
	    "10, 1, 1, 1, 0, 0"
    })
    public void testSinInterseccionValida(double xOrigen, double yOrigen, double zOrigen, double xDireccion, double yDireccion, double zDireccion) {
	// Datos del rayo.
	VectorR3 origen = new VectorR3(xOrigen, yOrigen, zOrigen);
	VectorR3 direccion = new VectorR3(xDireccion, yDireccion, zDireccion);
	
        // Definimos el rayo.
        Rayo rayo = new Rayo(origen, direccion);

        // Verificamos que no exista una intersección válida de la esfera con el rayo.
        assertNull(esfera.intersectar(rayo));
    }
    
    /**
     * Método auxiliar para verificar la intersección existente que debe
     * presentar un rayo y un plano, dando tolerancia a los valores
     * decimales.
     *
     * @param interseccion resultado obtenido de la intersección.
     * @param t parámetro t esperado de la intersección.
     * @param punto ubicación del punto esperado de la intersección.
     * @param normal vector normal esperado de la intersección.
     */
    private void verificarInterseccion(Interseccion interseccion, double t, VectorR3 punto, VectorR3 normal) {
	// Verificamos que haya una intersección distinta de null.
        assertNotNull(interseccion);

	// Comparamos si el parámetro t es el esperado.
        assertEquals(t, interseccion.getT(), Verificador.TOLERANCIA);

	// Construimos el punto de intersección esperado.
	VectorR3 puntoObtenido = interseccion.getPunto();

	// Comparamos si obtenemos el punto de la intersección.
	verificarVectoresTolerancia(punto, puntoObtenido);

	// Construimos el punto de intersección esperado.
	VectorR3 normalObtenida = interseccion.getNormal();

	// Comparamos si obtenemos la normal esperada de la intersección.
	verificarVectoresTolerancia(normal, normalObtenida);
    }

    /**
     * Método auxiliar para comparar vectores con tolerancia.
     *
     * @param v1 primer vector a comparar.
     * @param v2 segundo vector a comparar.
     */
    private void verificarVectoresTolerancia(VectorR3 v1, VectorR3 v2) {
	assertEquals(v1.getX(), v2.getX(), Verificador.TOLERANCIA);
	assertEquals(v1.getY(), v2.getY(), Verificador.TOLERANCIA);
	assertEquals(v1.getZ(), v2.getZ(), Verificador.TOLERANCIA);
    }
}

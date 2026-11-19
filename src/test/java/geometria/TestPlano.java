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
 * Pruebas unitarias para la clase {@link Plano}.
 *
 * Verifica la construcción de un plano y sus intersecciones con rayos
 * mediante ejemplos geométricos con resultados conocidos.
 */
public class TestPlano {
    
    /* Plano inclinado. */
    private Plano plano;

    /**
     * Construye el plano utilizado en las pruebas.
     */
    @BeforeEach
    public void setUp() {
        // Preparamos los datos necesarios para el plano.
        VectorR3 normal = new VectorR3(1, 1, 1);
        VectorR3 punto = new VectorR3(0, 0, 0);
        VectorR3 color = new VectorR3(1, 1, 1);

	// Creamos un plano nuevo.
        plano = new Plano(normal, punto, color);
    }

    /**
     * Verifica que el plano se construya con datos válidos
     * y rechace los argumentos inválidos.
     */
    @Test
    public void testConstructor(){
	// Preparamos valores válidos para la normal, el punto y color.
        VectorR3 normal = new VectorR3(1, 0, 1);
	VectorR3 punto = new VectorR3(0, 1, 0);
        VectorR3 color = new VectorR3(1, 1, 1);

	// Construimos un plano válido.
        Plano planoValido = new Plano(normal, punto, color);

	// Validamos el plano.
	assertEquals(normal, planoValido.getNormal());
	assertEquals(punto, planoValido.getPunto());
	assertEquals(color, planoValido.getColor());
	
	// Objeto3D valida el color recibido mediante super(color).
        assertThrows(IllegalArgumentException.class,
				() -> new Plano(normal, punto, null));

	// La normal del plano no puede ser null.
        assertThrows(IllegalArgumentException.class,
				() -> new Plano(null, punto, color));
	
	// El plano debe contener un punto bien definido.
        assertThrows(IllegalArgumentException.class,
				() -> new Plano(normal, null, color));
	
	// No se puede definir la orientación del plano con la normal cero.
        assertThrows(IllegalArgumentException.class,
				() -> new Plano(new VectorR3(0, 0, 0), punto, color));
    }

    /**
     * Verifica los getters de la clase.
     */
    @Test
    public void testGetters() {
	// Preparamos valores del plano.
        VectorR3 normal = new VectorR3(1, 1, 1);
        VectorR3 punto = new VectorR3(0, 0, 0);
        VectorR3 color = new VectorR3(1, 1, 1);
	
	// Validamos el plano.
	assertEquals(normal, plano.getNormal());
	assertEquals(punto, plano.getPunto());
	assertEquals(color, plano.getColor());
    }

    /**
     * Verifica los setters de la clase.
     */
    @Test
    public void testSetters() {
	// Preparamos valores del plano.
        VectorR3 normal = new VectorR3(2, 2, 2);
        VectorR3 punto = new VectorR3(2, 2, 2);
        VectorR3 color = new VectorR3(0, 0, 0);

	// Modificamos los datos.
	plano.setNormal(normal);
	plano.setPunto(punto);
	plano.setColor(color);
	
	// Validamos el plano.
	assertEquals(normal, plano.getNormal());
	assertEquals(punto, plano.getPunto());
	assertEquals(color, plano.getColor());
    }
    
    /**
     * Verifica que {@link Plano#intersectar(Rayo)}
     * rechace un rayo nulo.
     */
    @Test
    public void testIntersectarRayoNulo() {
        // El cálculo requiere un Rayo distinto de null.
        assertThrows(IllegalArgumentException.class,
		     () -> plano.intersectar(null));
    }

    /**
     * Pruebas parametrizadas para {@link Plano#intersectar(Rayo)}.
     *
     * Verifica que se obtenga la intersección válida de un rayo
     * con el plano, cuyo parámetro t sea no negativo. Para ello,
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
     * @param t parámetro donde ocurre la intersección del rayo con el
     *        plano.
     */
    @ParameterizedTest
    @CsvSource({
	    // Rayo paralelo a la normal del plano con dirección contraria de este último. (Cara frontal)
	    "8, 20, 20, -8, -8, -8, -8, 4, 4, 1, 1, 1, 2",
	    // Rayo paralelo a la normal del plano con la misma dirección de este último. (Cara posterior)
	    "-8, -20, -20, 8, 8, 8, 8, -4, -4, -1, -1, -1, 2",
	    // Rayo no paralelo a la normal del plano. (Cara Frontal)
	    "0, 12, 12, 0, -24, -24, 0, 0, 0, 1, 1, 1, 0.5",
	    // Rayo no paralelo a la normal del plano. (Cara Posterior)
	    "0, -12, -12, 0, 24, 24, 0, 0, 0, -1, -1, -1, 0.5",
	    // Rayo con intersección en t = 0. (Primer valor del parámetro t válido)
	    "8, -4, -4, 1, 1, 1, 8, -4, -4, -1, -1, -1, 0",
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
        Interseccion resultado = plano.intersectar(rayo);

        // Construimos el punto de intersección esperado.
	VectorR3 puntoEsperado = new VectorR3(xPunto, yPunto, zPunto);

	// Construimos el vector normal de la superficie en el punto del choque.
	VectorR3 normalEsperado = new VectorR3(xNormal, yNormal, zNormal);

	// Verificamos la intersección del rayo con el plano.
	verificarInterseccion(resultado, t, puntoEsperado, normalEsperado.vectorUnitario());
    }
    
    /**
     * Pruebas parametrizadas para {@link Plano#intersectar(Rayo)}.
     *
     * Verifica que el método devuelva <code>null</code> cuando el rayo
     * no intersecta al plano, se tengan infinitas intersecciones o
     * esta se encuentra detrás del origen del rayo.
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
	    // Rayo paralelo al plano sin intersectarlo. (Sin tocar el plano)
	    "-7, 5, 5, 8, -4, -4",
	    // Rayo con intersecciones infinitas. (Contenido en el plano)
	    "8, -4, -4, 16, -8, -8",
	    // Rayo con 'intersección' detrás del origen del mismo. (Intersección que no se ve)
	    "0, 12, 12, 0, 24, 24"
    })
    public void testSinInterseccionValida(double xOrigen, double yOrigen, double zOrigen,
					  double xDireccion, double yDireccion, double zDireccion) {
        // Datos del rayo.
	VectorR3 origen = new VectorR3(xOrigen, yOrigen, zOrigen);
	VectorR3 direccion = new VectorR3(xDireccion, yDireccion, zDireccion);
	
        // Definimos el rayo.
        Rayo rayo = new Rayo(origen, direccion);

        // Verificamos que no exista una intersección válida del rayo con el plano.
        assertNull(plano.intersectar(rayo));
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

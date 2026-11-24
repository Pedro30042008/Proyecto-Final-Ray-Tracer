package renderizado;

import camara.Camara;
import escena.Escena;
import geometria.Esfera;
import geometria.Interseccion;
import geometria.Objeto3D;
import geometria.Plano;
import iluminacion.LuzPuntual;
import io.EscritorPPM;
import matematicas.Rayo;
import matematicas.VectorR3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas del renderizado concurrente frente al secuencial.
 * Los tamaños son pequeños para que las pruebas sean rápidas.
 */
public class RenderizadorConcurrenteTest {

    @TempDir
    Path directorioTemporal;

    /**
     * Helper
     * 
     * Construye una escena con esfera, plano, iluminación y fondo.
     */
    private Escena crearEscena(int ancho, int alto) {
        /*Creamos camara */
        Camara camara = new Camara(new VectorR3(0, 1, 3),new VectorR3(0, 0, -3), 60, ancho, alto);
        /*Creamos esfera y plano */
        List<Objeto3D> objetos = List.of(
            new Esfera(new VectorR3(0, 0, -3), 1,new VectorR3(0.9, 0.2, 0.1)),
            new Plano(new VectorR3(0, 1, 0),new VectorR3(0, -1, 0),new VectorR3(0.5, 0.5, 0.5)));
        /*Creamos una luz */
        List<LuzPuntual> luces = List.of(new LuzPuntual(new VectorR3(3, 4, -1), 0.7));
        /*Con todo creamos la escena */
        return new Escena(ancho, alto, camara,new VectorR3(0.1, 0.2, 0.3),0.3, luces, objetos);
    }

    /**
     * Parametrixado para debeCoincidirConElRenderizadoSecuencial
     * Compara cada componente RGB con el renderizado secuencial.
     * Incluye repartos exactos e inexactos, y más trabajadores que filas.
     */
    @ParameterizedTest
    @CsvSource({
        "11, 7, 1",
        "11, 7, 2",
        "11, 7, 3",
        "11, 7, 4",
        "11, 7, 8",
        "5, 2, 8",
        "1, 1, 8"
    })

    /**
     * Test
     * Prueba parametrizada que verifica que produzca el mismo resultado
     * tanto concurrente como secuencial, comparando componentes RGB.
     * 
     * Seguiremos el modelo Arrange–Act–Assert.
     * @param ancho Ancho de la escena.
     * @param alto  Alto de la aescena.
     * @param trabajadores  Trabajadores para renderizado concurrente.
     * @throws InterruptedException
     */
    public void debeCoincidirConElRenderizadoSecuencial(int ancho, int alto, int trabajadores) throws InterruptedException {

        // Arrange
        /*Preparamos escena */
        Escena escena = crearEscena(ancho, alto);
        /* Renderizamos secuencialmente*/
        VectorR3[][] esperado = new Renderizador(escena).renderizar();

        // Act
        /*Renderizamos concurrente */
        VectorR3[][] obtenido = new RenderizadorConcurrente(escena).renderizar(trabajadores);

        // Assert
        /*Checamos que tengan mismas dimension y que todos los pixeles
        se calcularon */
        assertEquals(alto, obtenido.length);
        /*Recorremos para checar pixeles */
        for (int fila = 0; fila < alto; fila++) {
            /*Que no sea null */
            assertNotNull(obtenido[fila]);
            /*Mismo ancho en la fila i-esima */
            assertEquals(ancho, obtenido[fila].length);
            /*Recorremos columnas */
            for (int columna = 0; columna < ancho; columna++) {
                /*Guardamos uno concurrente y otro secuencial */
                VectorR3 actual = obtenido[fila][columna]; //concurrente
                VectorR3 referencia = esperado[fila][columna]; //secuencial
                /*CHecamos que no sea null */
                assertNotNull(actual,"Píxel sin calcular: (" + fila + ", " + columna + ")");
                /*Comprobamos que ambos renderizados generen los mismos colores */
                assertEquals(referencia.getX(), actual.getX(), 1e-12);
                assertEquals(referencia.getY(), actual.getY(), 1e-12);
                assertEquals(referencia.getZ(), actual.getZ(), 1e-12);
            }
        }
    }

    
    @ParameterizedTest
    /*Ejecutamos 2 veces el test, una vez con 0 trabajadores y luego con -1 */
    @ValueSource(ints = {0, -1})

    /**
     * Test
     * Prueba unitaria parametrizada para que renderizadorConcurrente 
     * rechace trabajadores
     * 
     * Seguiremos el modelo Arrange–Act–Assert.
     * @param trabajadores num de trabjadores invalidos.
     */
    public void debeRechazarTrabajadoresInvalidos(int trabajadores) {
        //Arrange
        /*Creamos escena */
        Escena escena = crearEscena(5, 3);
        /*Construimos un renderizadorConcurrente */
        RenderizadorConcurrente concurrente = new RenderizadorConcurrente(escena);
        /*Lo mandamos a chambear con trabajadores que no deberian de pasar. */
        //Act y Assert
        assertThrows(IllegalArgumentException.class,
                () -> concurrente.renderizar(trabajadores));
    }

    
    
    
    /**
     * Test
     * Prueba unitaria que verifica que genera el mismo PPM
     * con 1 o con 4 trabajdores
     * 
     * Seguiremos el modelo Arrange–Act–Assert.
     * @throws InterruptedException 
     * @throws IOException
     */
    @Test
    public void debeGenerarElMismoPPM() throws InterruptedException, IOException {
        //Arrange
        /*Preparamos escena y un renderizador concurrente */
        Escena escena = crearEscena(11, 7);
        RenderizadorConcurrente concurrente = new RenderizadorConcurrente(escena);
        /*Renderizamos secuencial, 1 trabjador */
        VectorR3[][] uno = concurrente.renderizar(1);
        /*Renderizamos concurrente, 4 trabajadores */
        VectorR3[][] cuatro = concurrente.renderizar(4);
        /*Construye rutas temporales*/
        Path rutaUno = directorioTemporal.resolve("uno.ppm"); //Secuencial
        Path rutaCuatro = directorioTemporal.resolve("cuatro.ppm"); //Concurrente
        /*Escribimos e las rutas temporales y con las matrices correspondientes. */
        //Act
        EscritorPPM.escribir(rutaUno.toString(), uno);
        EscritorPPM.escribir(rutaCuatro.toString(), cuatro);
        //Assert
        /*Verificamos que tengan lo mismo */
        assertArrayEquals(Files.readAllBytes(rutaUno),Files.readAllBytes(rutaCuatro));
    }

    /**
     * Test
     * Prueba unictaria que demuestra que si hay un error no se queda perdido
     * sino que se guarda.
     * 
     * Seguiremos el modelo Arrange–Act–Assert.
    */
    @Test
    public void debePropagarErroresDeLosTrabajadores() {
        
        /*Ancho y alto de camara */
        int ancho = 5; 
        int alto = 3;
        //Arrange
        /*Creamos camara */
        Camara camara = new Camara(new VectorR3(0, 0, 0), new VectorR3(0, 0, -1),60, ancho, alto);
        /* Creamos un Objeto 3D de pruba sin crear otra clase, 
        para sobreescribir el metodo intersectar para que siempre nos regrese una excepcion 
        y asi el hilo la guarde.*/
        Objeto3D objetoQueFalla = new Objeto3D(new VectorR3(1, 1, 1)) {
            @Override
            /*Sobrescribimos  */
            public Interseccion intersectar(Rayo rayo) {
                /*Siempre lanzamos excepction */
                throw new IllegalArgumentException("Fallo intencional");
            }
        };
        /*Creamos la escena con el objeto que falla */
        Escena escena = new Escena(ancho, alto, camara,new VectorR3(0, 0, 0), 0.2,List.of(), List.of(objetoQueFalla));
        //Act
        /*Mandamos a llamar con el override de intersectar
        para verificar que pase el error y que lo guarde */
        IllegalStateException error = assertThrows(IllegalStateException.class,
                () -> new RenderizadorConcurrente(escena).renderizar(3));
        //Assert
        /*CHecamos que paso el error */    
        assertNotNull(error.getCause());
        /*Checamos que sea el error que esperamos */
        assertEquals("Fallo intencional", error.getCause().getMessage());
    }
}

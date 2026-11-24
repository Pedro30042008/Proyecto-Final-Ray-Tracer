package io;

/*Clases del proyecto */
import matematicas.VectorR3;
/*Bibliotecas para los test */
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/*Bibliotecas de excepcion y para archivos/rutas */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


/**
 * Pruebas unitarias para la clase {@link EscritorPPM}.
 *
 * Comprueba la escritura de imágenes PPM P3,
 * la conversión de colores y las entradas inválidas.
 */
public class EscritorPPMTest {

    // JUnit crea un directorio temporal para cada prueba.
    @TempDir
    Path directorioTemporal;//ruta

    /**
     * Test
     * 
     * Comprueba que se escriba una imagen PPM P3
     * con dimensiones y colores conocidos.
     * 
     * Seguiremos el modelo Arrange–Act–Assert.
     */
    @Test
    public void debeEscribirImagenPPM() throws IOException {

        // Arrange
        /*Preparamos pixeles con colores conocidos
            de 2 filas 3 columnas */
        VectorR3[][] pixeles = { {
                /*fila 0 con 3 columnas*/
                new VectorR3(1, 0, 0), //[0][0]
                new VectorR3(0, 1, 0), //[0][1]
                new VectorR3(0, 0, 1)  //[0][2]
            },
            {
                /*Fila 1 con 3 columnas */
                new VectorR3(1, 1, 0), //[1][0]
                new VectorR3(1, 1, 1), //[1][1]
                new VectorR3(0, 0, 0) //[1][2]
            }
        };
        /*Como ya tenemos la ruta /.../../imagen.ppm aqui vamos a dejaremos
            la basura de los test*/
        Path salida = directorioTemporal.resolve("imagen.ppm");

        // Act
        /*Llamamos para que escriba */
        EscritorPPM.escribir(salida.toString(), pixeles);

        /*Abrimos el archivo que creamos y lo guardamos en contenido */
        String contenido = Files.readString(salida);

        // Assert
        /*Eliminamos todos los espacios/saltos de linea con trim
            y con \\s+ significa que cualquier caracter en blanco " " \n etc
            y con + que uno o mas seguidos caracteres es decir que
            divide la cadena en elementos utilizando uno o más
            espacios en blanco como separadores.*/
        String[] valores = contenido.trim().split("\\s+");

        /*Creamos lo que esperamos  */
        String[] esperado = {
            /*Aqui comprobamos que lo que escribimos en valores
                lo tengamos como esperado */
            "P3", "3", "2", "255",
            "255", "0", "0",
            "0", "255", "0",
            "0", "0", "255",
            "255", "255", "0",
            "255", "255", "255",
            "0", "0", "0"
        };
        /*Comprobamos */
        assertArrayEquals(esperado, valores);
    }

    /**
     * Test
     * Comprueba la conversión y el redondeo
     * de componentes RGB.
     * 
     * Seguiremos el modelo Arrange–Act–Assert.
     */
    @Test
    public void debeRedondearComponentes() throws IOException {

        // Arrange
        /*Creamos una matriz de 1x1 */
        VectorR3[][] pixeles = {
            {new VectorR3(0.5, 0.25, 0.75)}
        };

        /*Como ya tenemos la ruta /.../../redondeo.ppm aqui vamos a dejaremos
            la basura de los test*/
        Path salida = directorioTemporal.resolve("redondeo.ppm");

        // Act
        /*Llamamos a escribir */
        EscritorPPM.escribir(salida.toString(), pixeles);

        /*Abrimos el archivo que creamos y lo guardamos en contenido */
        String contenido = Files.readString(salida);

        // Assert
        /*LE quitamos espacios en blanco y otras cosas */
        String[] valores = contenido.trim().split("\\s+");
        /*Creamos esperado*/
        String[] esperado = {
            "P3", "1", "1", "255",
            "128", "64", "191" 
            /*Es decir que esperamos que 
            0.5 = 128 
            0.25 = 64
            0.75 = 191*/
        };
        /*Comprobamos */
        assertArrayEquals(esperado, valores);
    }

    /**
     * Test
     * Comprueba que se rechacen matrices nulas
     * o sin filas.
     * Seguiremos el modelo Arrange–Act–Assert.
     */
    @Test
    public void debeRechazarMatrizNulaOVacia() {

        // Arrange
        /*Preparamos matriz nula y vacia */
        VectorR3[][] matrizNula = null;
        VectorR3[][] matrizVacia = {};

        /*Como ya tenemos la ruta /.../../invalida.ppm aqui vamos a dejaremos
            la basura de los test*/
        Path salida = directorioTemporal.resolve("invalida.ppm");

        // Act y Assert
        /*Guardamos en excepcion1 y 2, lo que debemos de tener para despues comprobar*/
        IllegalArgumentException excepcion1 = assertThrows(IllegalArgumentException.class,
                () -> EscritorPPM.escribir(salida.toString(), matrizNula));

        IllegalArgumentException excepcion2 = assertThrows(IllegalArgumentException.class,
            () -> EscritorPPM.escribir(salida.toString(), matrizVacia));

        /*Comprobamos que fallo donde queriamos */
        assertTrue(excepcion1.getMessage().contains("al menos una fila") && excepcion2.getMessage().contains("al menos una fila"));
        /*NO debio de haber creado salida por que debio fallar */
        assertFalse(Files.exists(salida));
    }

    /**
     * Test
     * Comprueba que las filas tengan el mismo ancho
     * y que exista al menos una columna.
     * Seguiremos el modelo Arrange–Act–Assert.
     */
    @Test
    public void debeRechazarFilaInvalida() {

        // Arrange
        /*Preparamos vector rojo */
        VectorR3 rojo = new VectorR3(1, 0, 0);
        /*Preparamos matriz con 1 fila pero 0 columnas */
        VectorR3[][] sinColumnas = {{}};
        /*Preparamos matriz con una fila nula */
        VectorR3[][] filaNula = {
            {rojo},
            null
        };
        /* Preparamos matriz con columnas irregulares
            fila 0 tiene 1 columna, fila 1 tiene 2 columnas*/
        VectorR3[][] irregular = {
            {rojo},
            {rojo, rojo}
        };
        /*Como ya tenemos la ruta /.../../filas.ppm aqui vamos a dejaremos
            la basura de los test*/
        Path salida = directorioTemporal.resolve("filas.ppm");

        // Act y Assert
        IllegalArgumentException excepcion1 = assertThrows(IllegalArgumentException.class,
            () -> EscritorPPM.escribir(salida.toString(), sinColumnas));

        IllegalArgumentException excepcion2 = assertThrows(IllegalArgumentException.class,
            () -> EscritorPPM.escribir(salida.toString(), filaNula));

        IllegalArgumentException excepcion3 = assertThrows(IllegalArgumentException.class,
            () -> EscritorPPM.escribir(salida.toString(), irregular));

        /*Comprobamos que fallo donde queriamos */
        assertTrue(excepcion1.getMessage().contains("al menos una columna"));

        assertTrue(excepcion2.getMessage().contains("mismo ancho"));

        assertTrue(excepcion3.getMessage().contains("mismo ancho"));

        /*NO debio de haber creado salida por que debio fallar */
        assertFalse(Files.exists(salida));
    }

    /**
     * Test
     * Comprueba que no se acepten píxeles nulos.
     * 
     * Seguiremos el modelo Arrange–Act–Assert.
     */
    @Test
    public void debeRechazarPixelNulo() {

        // Arrange
        /*Preparamos matriz de una fila con 2 columnas */
        VectorR3[][] pixeles = {
            {new VectorR3(1, 0, 0), null}
        };

        /*Como ya tenemos la ruta /.../../pixel_nulo.ppm aqui vamos a dejaremos
            la basura de los test*/
        Path salida = directorioTemporal.resolve("pixel_nulo.ppm");

        // Act y Assert
        IllegalArgumentException excepcion1 = assertThrows(IllegalArgumentException.class,
            () -> EscritorPPM.escribir(salida.toString(), pixeles));
        /*Comprobamos */
        assertTrue(excepcion1.getMessage().contains("píxeles no pueden ser null"));
        /*NO debio de haber creado salida por que debio fallar */
        assertFalse(Files.exists(salida));
    }

    /**
     * Parametrizado para debeRechazarColorFueraDeRango
     * Comprueba que se rechacen componentes RGB
     * fuera del intervalo [0,1].
     */
    @ParameterizedTest
    @CsvSource({
        "-0.1, 0.5, 0.5",
        "1.1, 0.5, 0.5",
        "0.5, -0.1, 0.5",
        "0.5, 1.1, 0.5",
        "0.5, 0.5, -0.1",
        "0.5, 0.5, 1.1"
    })

    /**
     * Test
     * Prueba parametrizada que verifica que se 
     * rechacen pixeles fuera del intervalo [0,1]
     * 
     * Seguiremos el modelo Arrange–Act–Assert.
     * @param rojo
     * @param verde
     * @param azul
     */
    public void debeRechazarColorFueraDeRango(double rojo, double verde, double azul) {

        // Arrange
        /*Preparamos un matriz de 1x1 vector con los vector de la parametrizacion */
        VectorR3[][] pixeles = {
            {new VectorR3(rojo, verde, azul)}
        };
        /*Como ya tenemos la ruta /.../../color.ppm aqui vamos a dejaremos
            la basura de los test*/
        Path salida = directorioTemporal.resolve("color.ppm");

        // Act y Assert
        IllegalArgumentException excepcion1 = assertThrows(IllegalArgumentException.class,
            () -> EscritorPPM.escribir(salida.toString(), pixeles));
        
        /*Comprobamos que fallo donde devia */
        assertTrue(excepcion1.getMessage().contains("deben estar entre 0 y 1"));

        /*NO debio de haber creado salida por que debio fallar */
        assertFalse(Files.exists(salida));
    }
}


package io;

/*
El static es para no tener que escribir el bombre de la clase padre
y unicamente puedes llamar el nombre a secas.
Aparte de importar todos los metodos de la clase.
 */
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException; //Exepcion de entrada y salida de archivos.

import org.junit.jupiter.api.Test; // importamos JUnit 5 (Jupiter)

import escena.Escena; //Importamos la clase 


public class LectorEscenaJsonTest {
    
    /**
     * Test
     * Prueba unitaria para leer el archivo y extraer correctamente los tamanios 
     * de la escena.
     * Seguiremos el modelo Arrange–Act–Assert.
     * @throws IOException
     */
    @Test
    public void debeLeerDimensionesDeEscenaCompleta() throws IOException {

        /* Arrange
        Por comodidad, los archivos para tester se encontraran en esta ruta
        despues implementaremos en terminal
        */
        String ruta = "src/test/resources/escenas/prueba_dimensiones_IMPL-COMPLETAcorr.json";

        /* Act
        Llamamos a Lector con objeto nuevo escena.
         */
        Escena escena = LectorEscenaJson.leer(ruta);

        /* Assert
        Comprobamos
         */
        assertEquals(400, escena.getAncho());
        assertEquals(225, escena.getAlto());
    }

    /**
     * Test
     * Prueba unitaria que verifica que el lector de escenas rechaza
     * un archivo JSON con dimensiones incorrectas o negativas.
     * Seguiremos el modelo Arrange–Act–Assert.
     */
    @Test
    void debeRechazarDimensionesNegativas() {

        // Arrange
        String ruta = "src/test/resources/escenas/prueba_dimensiones_inc.json";

        /* Act y Assert
        Ocupamos assertThrowa para verificar que algo falle,
        es decir que una operacion lance la excepcion que determinamos.
        */ 


        assertThrows(
            /*
            IllegalArgumentException.class -> Excepcion que esperamos que ocurra
            ()) -> LectorEscenaJson.leer(ruta) -> Lambda que ejecuta el metodo
                  leer con la ruta especificada.
             */
            IllegalArgumentException.class, () -> LectorEscenaJson.leer(ruta)
        );
    }
    /**
     * Test
     * Prueba unitaria que verifica que el lector rechaza un archivo JSON
     * que existe y es sintácticamente válido, pero al cual le falta un campo
     * obligatorio.
     * Seguiremos el modelo Arrange–Act–Assert.
     */
    @Test
    void debeRechazarJsonSinImagen() {

        // Arrange
        String ruta = "src/test/resources/escenas/prueba_sin_imagen_inc.json";

        // Act y Assert
        assertThrows(
            IllegalArgumentException.class,
            () -> LectorEscenaJson.leer(ruta)
        );
    }

    /**
     * Test
     * Prueba unitaria que verifica el comportamiento del sistema cuando
     * se intenta leer una ruta de archivo que no existe.
     * Seguiremos el modelo Arrange–Act–Assert.
     */
    @Test
    void debeRechazarArchivoInexistente() {

        // Arrange
        String ruta = "src/test/resources/escenas/no_existe.json";

        // Act y Assert
        assertThrows(
            IOException.class,
            () -> LectorEscenaJson.leer(ruta)
        );
    }

    /**
     * Prueba unitaria que verifica que el lector rechaza un archivo JSON
     * con un valor FOV inválido o fuera de rango.
     * Seguiremos el modelo Arrange–Act–Assert.
     */
    @Test
    void debeRechazarFovInvalido() {
        //Arrange
        String ruta = "src/test/resources/escenas/prueba_dimensiones_IMPL-COMPLETA_incFov.json";

        // Act y Assert
        assertThrows(
            IllegalArgumentException.class,
            () -> LectorEscenaJson.leer(ruta)
        );
    }


    /**
     * Prueba unitaria que verifica que el lector rechaza un archivo JSON
     * que contiene una intensidad lumínica negativa.
     * Seguiremos el modelo Arrange–Act–Assert.
     */
    @Test
    void debeRechazarIntensidadNegativa() {
        //Arrange
        String ruta = "src/test/resources/escenas/prueba_dimensiones_IMPL-COMPLETA_incINT.json";

        // Act y Assert
        assertThrows(
            IllegalArgumentException.class,
            () -> LectorEscenaJson.leer(ruta)
        );
    }


    /**
     * Prueba unitaria que verifica que el lector rechaza un archivo JSON
     * que incluye un tipo de objeto desconocido.
     * Seguiremos el modelo Arrange–Act–Assert.
     */
    @Test
    void debeRechazarObjetoDesconocido() {
        //Arrange
        String ruta = "src/test/resources/escenas/prueba_dimensiones_IMPL-COMPLETA_incOBJ.json";

        // Act y Assert
        assertThrows(
            IllegalArgumentException.class,
            () -> LectorEscenaJson.leer(ruta)
        );
    }
}
//IMPLEMENTAR COMO SABEMOS QUE EFECTIVAMENTE EN ESA PARTE VALIO MADRE

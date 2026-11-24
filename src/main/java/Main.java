import io.LectorArgsTerminal;
import io.LectorEscenaJson;
import io.EscritorPPM;

import renderizado.RenderizadorConcurrente;
import renderizado.Renderizador;
import matematicas.VectorR3;
import escena.Escena;

import java.io.IOException;

/**
 * MAIN
 * Recibe los argumentos de ejecución, crea un LectorArgsTerminal
 * para validarlos y coordina el trabajo del Ray Tracer.
 */
public class Main {

    /**
    * Ejecuta el programa desde la terminal.
    *
    * Recibe las rutas del JSON de entrada y del PPM de salida.
    * Opcionalmente, permite indicar el número de trabajadores.
    * V2.1
    * @param cositas argumentos de ejecución.
    */
    public static void main(String[] cositas) {
	/* Construimos un lector de argumentos en terminal. */
	LectorArgsTerminal lector = new LectorArgsTerminal(cositas);

	/* Obtenemos los datos leídos por el lector. */
	String archivoJSON = lector.getArchivoJSON();
	String archivoPPM = lector.getArchivoPPM();
	int trabajadores = lector.getNumTrabajadores();

	/* Intentamos leer el archivoJON y escribirlo en archivoPPM. */
	try {
            /* Leemos y construimos la escena desde el archivo JSON. */
            Escena escena = LectorEscenaJson.leer(archivoJSON);

	    /* Verificamos que se haya leido la escena correctamente. */
	    System.out.println(escena);
	    
            /* Creamos el renderizador concurrente. */
            RenderizadorConcurrente renderizador = new RenderizadorConcurrente(escena);

            /* Renderizamos utilizando el número solicitado de trabajadores. */
            VectorR3[][] pixeles = renderizador.renderizar(trabajadores);
            
            /* Escribimos los colores en un archivo PPM P3. */
            EscritorPPM.escribir(archivoPPM, pixeles);
	    
	    /* Operación exitosa.*/
            System.out.println("Imagen generada correctamente: " + archivoPPM);

	/* Problemas en la lectura o escritura de archivos. */
        } catch (IOException e) {
	    /* Notificamos el problema y teerminamos. */
	    System.err.println("Error: " + e.getMessage());
	    System.exit(1);
	/* Ocurrio un error con el trabajo de los hilos. */
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("El renderizado fue interrumpido.");
        }

    }//FIN MAIN
    
}//FIN CLASS

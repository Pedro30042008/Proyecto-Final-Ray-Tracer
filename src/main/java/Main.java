import java.io.IOException;

import escena.Escena;
import io.LectorEscenaJson;

/**
 * MAIN
 * Recibe los argumentos de ejecución, valida su formato
 * y coordina la lectura de la escena.
 * 
 */
public class Main {

    /**
    * Ejecuta el programa desde la terminal.
    *
    * Recibe las rutas del JSON de entrada y del PPM de salida.
    * Opcionalmente, permite indicar el número de trabajadores.
    * V2
    * @param cositas argumentos de ejecución.
    */
    public static void main(String[] cositas) {

        /*  Verificamos que se reciban minimo 2 argumentos (.json y .ppm).
            y maximo 4 (todos)
            */
        if (cositas.length < 2 || cositas.length > 4) {
            System.err.println("Uso: java -jar raytracer.jar escena.json salida.ppm [--trabajadores N]");
            return;
        }

        /*  Obtenemos rutas de entrada y salida.*/
        String rutaEntrada = "";
        String rutaSalida = "";
        /*Por default tendremo 1 trabjador, se cambiara si el usuario los especifica 
            probaremos max 8*/
        int trabajadores = 1 ;
        boolean trabajadoresIndicados = false;
        /*Recorremos el arreglo cositas para obtener los argumentos */
        try {
            
            for(int i = 0; i < cositas.length ; i++){
                String actual = cositas [i];

                if(actual == null || actual.isEmpty()){
                    throw new IllegalArgumentException("No puede haber argumentos null o vacios");
                }else if (actual.endsWith(".json")){
                    if (!rutaEntrada.isEmpty()) {
                        throw new IllegalArgumentException("Se proporcionó más de una ruta JSON.");
                    }
                    rutaEntrada = cositas[i];
                } else if (actual.endsWith(".ppm")){
                    if (!rutaSalida.isEmpty()) {
                        throw new IllegalArgumentException("Se proporcionó más de una ruta PPM.");
                    }
                    rutaSalida = cositas[i];
                }else if (actual.equals("--trabajadores")){
                    if (trabajadoresIndicados) {
                        throw new IllegalArgumentException("La opción --trabajadores no puede repetirse.");
                    }
                    if (i + 1 >= cositas.length) {
                        throw new IllegalArgumentException("Debe especificarse un número después de --trabajadores.");
                    }
                    trabajadores = Integer.parseInt(cositas[i+1]);
                    trabajadoresIndicados = true;
                    i++;
                }else {
                    throw new IllegalArgumentException("Argumento no reconocido: " + actual);
                }
            }

            if (rutaEntrada.equals("")){
                throw new IllegalArgumentException("Sin ruta de entrada.");
            }
            if (rutaSalida.equals("")){
                throw new IllegalArgumentException("Sin ruta de salida.");
            }
            if (trabajadores < 1 || trabajadores > 8){
                throw new IllegalArgumentException("Los trabjadores deben de estar entre 1 y 8");
            }

            Escena escena = LectorEscenaJson.leer(rutaEntrada);

            System.out.println(escena);

        } catch (NumberFormatException e) {
            System.err.println("El número de trabajadores debe ser un entero.");
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }

        
    }//FIN MAIN
}//FIN CLASS

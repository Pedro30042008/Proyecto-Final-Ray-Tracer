
package io;

import escena.Escena;

//Biblioteca para leer JSON
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;

//Biblioteca para leer y excepcion
import java.io.FileReader;
import java.io.IOException;

/**
 * Lee archivos JSON y construye objetos de tipo Escena.
 */
public class LectorEscenaJson {

    /**
     * Lee las dimensiones de una escena desde un archivo JSON.
     *
     * @param ruta Ruta del archivo JSON.
     * @return Escena con las dimensiones recuperadas.
     * @throws IOException si no puede leerse el archivo.
     * @throws IllegalArgumentException si los datos son inválidos.
     */
    public static Escena leer(String ruta) throws IOException {
        //Ocuparemos un try-with-resources, para evitar cosumir recursos del sisitema
        try (FileReader archivo = new FileReader(ruta)) {
            
            //Leemos el archivo y lo analiza caractrer a caracter asia extraemos las propiedades de la Escena.
            JsonElement raiz = JsonParser.parseReader(archivo);

            
            /*
            Preguntamos si el JSON (archvo) empieza con  {...}
            Y como solo lo parseamos, asi podemos asegurarnos que es un objeto y no caer en el caso de null.
             */
            if (!raiz.isJsonObject()) {
                throw new IllegalArgumentException(
                    "La raíz del JSON debe ser un objeto."
                );
            }
            
            /*
            Como ya nos aseguramos que es un objeto.
            Lo convertimos a un objeto json, para asi poder obtener datos por nombre
             */
            JsonObject json = raiz.getAsJsonObject();

            /*
            Primero preguntamos si tiene "imagen" si NO tiene imgen -> True y lanza excepcion
            Si la primera evaluacion fue false, no entra al if y sabemos que SI tiene "imagen"
            Accedemos a ella, pero debemos de comprobar que sea un objeto y no null.
             */
            if (!json.has("imagen") || !json.get("imagen").isJsonObject()) {
                throw new IllegalArgumentException(
                    "Falta el objeto 'imagen'."
                );
            }
            /*
            Ya sabemos que si tenemos imagen y es un objeto, podemos convertirlo
            a un objeto y acceder a su prop.
             */
            JsonObject imagen = json.getAsJsonObject("imagen");

            //Obtenemos ancho y alto
            int ancho = leerEnteroPositivo(imagen, "ancho");
            int alto = leerEnteroPositivo(imagen, "alto");

            //Regresamos una escena nueva.
            return new Escena(ancho, alto);

            //Este catch va a cachar 2 cualquiera de los 2 errores
        } catch (JsonParseException | IllegalStateException e) {
            /*
            Aparte pasaremos el mensaje del error y con la "e" la informacion
            que proporciona Gson.
            */
            throw new IllegalArgumentException(
                "El archivo no contiene un JSON válido.", e // <- Mensaje amigable + reporte tecnico
            );
        }
    }

    
    /**
     * Recuperamos un entero positico desde un obj JSON.
     * 
     * @param objeto El objeto JSON
     * @param campo El campo del cual queremos recuperar el entero
     * @return Entero correspondiente al campo
     */
    private static int leerEnteroPositivo(JsonObject objeto, String campo) {
        /*
        Hacemos una referencia llamado valor (es generico) y del objeto pedimos 
        el campo
         */
        JsonElement valor = objeto.get(campo);
        /*
        Si el campo no esta, nos lo regresan como null, entonces debemos de verificar
        NO null, que sea primitivo (texto,numero o bool) por ultimo obtenemos
        como primitivo y despues vemos si es numero.
         */
        if (valor == null || !valor.isJsonPrimitive() || !valor.getAsJsonPrimitive().isNumber()) {
            throw new IllegalArgumentException(
                "El campo '" + campo + "' debe ser numérico."
            );
        }

        int numero;

        try {
            //Intentamos pasar a entero
            numero = Integer.parseInt(valor.getAsString());

            //Cacha error de conversion.
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                "El campo '" + campo + "' debe ser un entero.", e
            );
        }

        /*
        Por ultimo checamos que el numero sea positivo y diferente de 0,
        pues no tiene mucho sentido hacer escena de alguna dimension 0 xd.
         */
        if (numero <= 0) {
            throw new IllegalArgumentException(
                "El campo '" + campo + "' debe ser positivo."
            );
        }

        return numero;
    }
}

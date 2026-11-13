
package io;

import escena.Escena;

//Biblioteca para leer JSON
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;
import com.google.gson.JsonArray;

//Biblioteca para leer y excepcion
import java.io.FileReader;
import java.io.IOException;

/**
 * Lee archivos JSON y construye objetos de tipo Escena.
 */
public class LectorEscenaJson {

    //---LECTURA JSON---//
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

            //!!TEMPORAL PARA PROBAR CAMARA COLOR LUZ Y OBJETOS!!)//
            // Validar los demás componentes de la escena.
            leerCamara(json);
            leerColor(json, "fondo");

            double luzAmbiental = leerDouble(json, "luz_ambiental");

            if (luzAmbiental < 0 || luzAmbiental > 1) {
                throw new IllegalArgumentException("La luz ambiental debe estar entre 0 y 1.");
            }

            leerLuces(json);
            leerObjetos(json);
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
     * Lee y valida los objetos tridimensionales de una escena.
     *
     * @param json Objeto JSON de la escena.
     * @return Arreglo JSON de objetos validados.
     * @throws IllegalArgumentException si algún objeto es inválido.
     */
    private static JsonArray leerObjetos(JsonObject json) {
        /*
        Hacemos una referencia llamando "elemento" (es generico) y del objeto pedimos 
        el campo "objetos"
         */
        JsonElement elemento = json.get("objetos");

        //Revisamos que no sea null y que sea un array
        if (elemento == null || !elemento.isJsonArray()) {
            throw new IllegalArgumentException("El campo 'objetos' debe ser un arreglo.");
        }
        //Una vez verificado lo pedimos como array.
        JsonArray objetos = elemento.getAsJsonArray();

        /*
        Aqui tambien tendremos array dentro de un array
        Recorreremos los elementos y los analizaremos uno a uno
         */
        for (int i = 0; i < objetos.size(); i++) {
            /*
            Pedimos el objeto i
             */
            JsonElement elementoObjeto = objetos.get(i);

            //Revisamos que sea un objeto
            if (!elementoObjeto.isJsonObject()) {
                throw new IllegalArgumentException("El objeto " + i + " debe ser un objeto JSON.");
            }

            //Como ya sabaemos que es un obj, lo pedimos asi
            JsonObject objeto = elementoObjeto.getAsJsonObject();
            /*
            Pedimos el elemento "tipo"
             */
            JsonElement tipoElemento = objeto.get("tipo");
            /*Revisamos que no sea null, que sea primitivo y que sea un string */
            if (tipoElemento == null || !tipoElemento.isJsonPrimitive() || !tipoElemento.getAsJsonPrimitive().isString()) {
                throw new IllegalArgumentException("El objeto " + i + " debe tener un tipo válido.");
            }
            //Una vez verificado, lo pedimos como string
            String tipo = tipoElemento.getAsString();

            /*!!!!CAMBIAR CUNADO TENGAS LAS FIGURAS Y CONSTRUSCTORES
            DE MOMENTO SERA GENERICO
             */
            /*
            Una vez que ya tenemos el tipo de objeto
            entraremos al switch para analisar.
             */
            switch (tipo) {
                //SI es esfera
                case "esfera":
                    //COn LeerTerna buscaremos y comprobaremos centro
                    leerTerna(objeto, "centro");
                    //Buscaremos radio y obtenemos como double
                    double radio = leerDouble(objeto, "radio");
                    //Si el radio es menor o igual a 0, es absurdo
                    if (radio <= 0) {
                        throw new IllegalArgumentException("El radio de la esfera " + i + " debe ser positivo.");
                    }
                    //Leeremos el color y verificaremos
                    leerColor(objeto, "color");
                    break;
                //Si es plano
                case "plano":
                    //Leeremos su punto perteneciente al plano
                    leerTerna(objeto, "punto");
                    //Leeremos su normal
                    double[] normal = leerTerna(objeto, "normal");
                    /*Checamos que la normal no sea el vector 0, pues de ser
                    asi tendriamos un plano sin orientacion y como depues debemos
                    de sacar longitud caemos en division entre 0.
                     */
                    if (normal[0] == 0 && normal[1] == 0 && normal[2] == 0) {
                        throw new IllegalArgumentException( "La normal del plano " + i + " no puede ser el vector cero.");
                    }
                    //Leemos color
                    leerColor(objeto, "color");
                    break;

                //POr si no encontramos el objeto que corresponde    
                default:
                    throw new IllegalArgumentException("Tipo de objeto desconocido: " + tipo);
            }
        }

        return objetos;
    }

    /**
     * Lee y valida la información de la cámara.
     *
     * @param json Objeto JSON principal de la escena.
     * @return Objeto JSON correspondiente a la cámara validada.
     * @throws IllegalArgumentException si los datos son inválidos.
     */
    private static JsonObject leerCamara(JsonObject json) {
        
        /*
        Hacemos una referencia llamando "elemento" (es generico) y del objeto pedimos 
        el campo
         */
        JsonElement elemento = json.get("camara");

        //Revisamos que no sea null y que sea un objeto.
        if (elemento == null || !elemento.isJsonObject()) {
            throw new IllegalArgumentException(
                "Falta el objeto 'camara' o tiene un formato inválido.");
        }

        /*Ya vimos que no es null y que es un objeto,
        lo pedimos como objeto.
        */
        JsonObject camara = elemento.getAsJsonObject();

        /*
        Llamamos a leerTern para que lea los arreglos correspondientes.
         */
        double[] posicion = leerTerna(camara, "posicion");
        double[] miraA = leerTerna(camara, "mira_a");
        /*
        Llamamos a LeerDouble para que indetifique el fov
        y revise que sea valido
         */
        double fov = leerDouble(camara, "fov");

        /*
        Comprobamos que 0<fov<180
        pues si fov = 180 seria vista de pescado xd.
         */
        if (fov <= 0 || fov >= 180) {
            throw new IllegalArgumentException("El campo 'fov' debe estar entre 0 y 180 grados.");
        }

        /*
        Debemos de revisar que la posicion y la mira no 
        sean el mismo punto, pues seria absurdo 
        mirar justo donde estas.
         */
        boolean mismaPosicion = true;

        /*
        Recorremos las posiciones y verificamos que no sea iguales.
        Y solo basta con que una sea diferente.
         */
        for (int i = 0; i < 3; i++) {
            if (posicion[i] != miraA[i]) {
                mismaPosicion = false;
                break;
            }
        }
        //Comprobacion.
        if (mismaPosicion) {
            throw new IllegalArgumentException(
                "La cámara no puede mirar hacia su propia posición.");
        }

        return camara;
    }
    
    /**
     * Lee y valida las luces puntuales de una escena.
     * 
     * Para este metodo debemos de considerar que
     * recorremos arrays dentro de arrays.
     * @param json Objeto JSON  de la escena.
     * @return Arreglo JSON de luces validadas.
     * @throws IllegalArgumentException si las luces son inválidas.
     */
    private static JsonArray leerLuces(JsonObject json) {

        /*
        Hacemos una referencia llamando "elemento" (es generico) y del json pedimos 
        el campo
         */
        JsonElement elemento = json.get("luces");

        /*
        Comprobamos que sea no nullo y que sea aun array.
         */
        if (elemento == null || !elemento.isJsonArray()) {
            throw new IllegalArgumentException(
                "El campo 'luces' debe ser un arreglo.");
        }
        //Lo pedimos como array.
        JsonArray luces = elemento.getAsJsonArray();

        /*Debemos de ver que minimo tenemos una luz */
        if (luces.size() == 0) {
            throw new IllegalArgumentException(
                "La escena debe contener al menos una luz.");
        }
        /*
        Recorreremos el array principa, y analizaremos
        cada luz independientemente.
         */
        for (int i = 0; i < luces.size(); i++) {
             /*
            Hacemos una referencia llamando "elementoLuz" (es generico) y del
            array principal tomamos el i.
             */
            JsonElement elementoLuz = luces.get(i);

            /*Vemos que sea un objeto*/
            if (!elementoLuz.isJsonObject()) {
                throw new IllegalArgumentException(
                    "La luz " + i + " debe ser un objeto JSON.");
            }
            
            /*!!!
            AQUI DEBEMOS DE IMPLEMENTAR LUZ PUNTUAL
            !!!1*/
            /*
            Obtenemos y creamos luz puntual.
             */
            JsonObject luz = elementoLuz.getAsJsonObject();

            /*
            Despues con leerTerna vamos a buscar el array "posicion"
            y validarlo
            */
            leerTerna(luz, "posicion");
            /*Ahora checamos que la intensidad sea valida */
            double intensidad = leerDouble(luz, "intensidad");

            /*
            leerDouble nos da un real el cual puede ser negativo
            pero no podemos tener una luz negativa.
             */
            if (intensidad < 0) {
                throw new IllegalArgumentException(
                    "La intensidad de la luz " + i + " no puede ser negativa.");
            }
        }
        //!!!!DEBEMOS DE REGRESAR LUCES PUNTUAL
        return luces;
    }

    /**
     * Lee y valida un color RGB desde un objeto JSON.
     *
     * @param objeto Objeto JSON que tiene el color.
     * @param campo Nombre del campo paraa leer.
     * @return Arreglo con las componentes RGB.
     * @throws IllegalArgumentException si el color es inválido.
     */
    private static double[] leerColor(JsonObject objeto, String campo) {
        //Llamamos al metodo encargado de parsear.
        double[] color = leerTerna(objeto, campo);

        /*
        Como un color debe de cumplir que 
                (r,g,b) ∈ [0,1]^3 
        vamos a recorrer los numeros que ya parseamos
        y verificar que estan en este rango
         */
        for (int i = 0; i < color.length; i++) {
            /*
            Menor a 0 y mayor a 1 son los casos donde 
            seria un color invalido.
            */
            if (color[i] < 0 || color[i] > 1) {
                throw new IllegalArgumentException("Las componentes de '" + campo+ "' deben estar entre 0 y 1.");
            }
        }

        return color;
    }




    //---Auxiliares para leer JSON---//

    /**
     * Recuperamos un entero positico desde un obj JSON.
     * 
     * @param objeto El objeto JSON
     * @param campo El campo del cual queremos recuperar el entero
     * @return Entero correspondiente al campo
     */
    private static int leerEnteroPositivo(JsonObject objeto, String campo) {
        /*
        Hacemos una referencia llamando "valor" (es generico) y del objeto pedimos 
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

    /**
     * Lee un número real finito desde un campo JSON.
     *
     * @param objeto Objeto JSON que tiene el campo.
     * @param campo Nombre del campo.
     * @return Valor numérico del campo.
     * @throws IllegalArgumentException si el campo es inválido.
     */
    private static double leerDouble(JsonObject objeto, String campo) {
        /*
        Hacemos una referencia llamando "elemento" (es generico) y del objeto pedimos 
        el campo
        */
        JsonElement elemento = objeto.get(campo);
        /*
        Revisamos que no sea null, que sea primitivo y que sea un num.
         */
        if (elemento == null || !elemento.isJsonPrimitive() || !elemento.getAsJsonPrimitive().isNumber()) {
            throw new IllegalArgumentException(
                "El campo '" + campo + "' debe ser numérico."
            );
        }
        //Como ya comprobamos lo pedimos como double
        double valor = elemento.getAsDouble();

        //Revisamos que sea finito
        if (!Double.isFinite(valor)) {
            throw new IllegalArgumentException(
                "El campo '" + campo + "' debe ser finito."
            );
        }

        return valor;
    }


    /**
     * Lee un arreglo JSON de exactamente tres números finitos.
     *
     * @param objeto Objeto JSON que tiene el campo.
     * @param campo Nombre del campo para leer.
     * @return Arreglo con las tres componentes numéricas.
     * @throws IllegalArgumentException si el campo es inválido.
     */
    private static double[] leerTerna(JsonObject objeto, String campo) {
        /*
        Hacemos una referencia llamando "elemento" (es generico) y del objeto pedimos 
        el campo
         */
        JsonElement elemento = objeto.get(campo);
        /*
        Revisamos que no sea null y que sea un array
         */
        if (elemento == null || !elemento.isJsonArray()) {
            throw new IllegalArgumentException(
                "El campo '" + campo + "' debe ser un arreglo.");
        }

        /*
        Ya vimos que si es un arreglo, lo instanciamos como "arreglo"
         */
        JsonArray arreglo = elemento.getAsJsonArray();

        /*
        Para todos nuestros casos deben de ser de 3 elementos
        tanto para terna como para RGB y los otros.
         */
        if (arreglo.size() != 3) {
            throw new IllegalArgumentException(
                "El campo '" + campo + "' debe tener tres componentes."
            );
        }
        /*
        Lo guarderemos en un arreglo (despues sera vectorR3)
         */
        double[] componentes = new double[3];

        /*
        Como JsonArray no nos dice que tiene, es decir que es un 
        "array generico" debemos de ver que no tenga otra cosa que 
        no sea un INT o DOUBLE.
         */
        for (int i = 0; i < 3; i++) {
            /*
            Recorremos elemento por elemento, recuperando un elemento
            y obteniendolo como generico.
            */
            JsonElement componente = arreglo.get(i);

            /*
            Comprobamos que sea primitivo, y que sea un num.
             */
            if (!componente.isJsonPrimitive() || !componente.getAsJsonPrimitive().isNumber()) {
                throw new IllegalArgumentException(
                    "Las componentes de '" + campo + "' deben ser numéricas."
                );
            }
            /*
            Como ya sabemos que efectivamente es un numero,
            lo pedimos como tal.
            */
            double numero = componente.getAsDouble();

            //Debemos de ver que no sea infinito o NaN.
            if (!Double.isFinite(numero)) {
                throw new IllegalArgumentException(
                    "Las componentes de '" + campo + "' deben ser finitas."
                );
            }
            //guardamos componentes
            componentes[i] = numero;
            /*Asi lo hacemos con los 3 elementos del arreglo O(n) */
        }

        return componentes;
    }



}

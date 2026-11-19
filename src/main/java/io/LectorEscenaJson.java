
package io;

//Clases con las que trabajamos
import escena.Escena;
import matematicas.VectorR3;
import geometria.Objeto3D;
import geometria.Esfera;
import geometria.Plano;
import camara.Camara;

//Biblioteca para leer JSON
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonParseException;
import com.google.gson.JsonArray;
//Biblioteca para leer y excepcion
import java.io.FileReader;
import java.io.IOException;
/*Biblioteca para listas */
import java.util.ArrayList;
import java.util.List;

/**
 * Lee archivos JSON y construye objetos de tipo Escena.
 */
public class LectorEscenaJson {

    //---LECTURA JSON---//
    /**
     * Metodo pricipal para la lectura de un JSON.
     * Lee y busca cada parametro para crear una escena.
     *
     * @param ruta Ruta del archivo JSON.
     * @return Escena con los atributos extraidos.
     * @throws IOException si no puede leerse el archivo.
     * @throws IllegalArgumentException si los datos son inválidos.
     */
    public static Escena leer(String ruta) throws IOException {
        //Ocuparemos un try-with-resources, para evitar cosumir recursos del sisitema
        try (FileReader archivo = new FileReader(ruta)) {
            
            /*Leemos el archivo y lo analiza caractrer a caracter a si extraemos las propiedades de la Escena.*/
            JsonElement raiz = JsonParser.parseReader(archivo);

            
            /*Preguntamos si el JSON (archivo) empieza con  {...}
                Y como solo lo parseamos, asi podemos asegurarnos que es un objeto y no caer en el caso de null.*/
            if (!raiz.isJsonObject()) {
                throw new IllegalArgumentException("La raíz del JSON debe ser un objeto.");
                }
            
            /*Como ya nos aseguramos que es un objeto.
                Lo convertimos a un objeto json, para asi poder obtener datos por nombre*/
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
            a un objeto y acceder a sus props.
             */
            JsonObject imagen = json.getAsJsonObject("imagen");

            /*Llamamos a leerEnterosPositivo para recuperar ancho y alto */
            int ancho = leerEnteroPositivo(imagen, "ancho");
            int alto = leerEnteroPositivo(imagen, "alto");

            /* Llamamos a leerCamara para recuperar una camara.
                Pasandole ancho y alto apra que nos devuelva un obj camara.*/
            Camara camara = leerCamara(json, ancho, alto);       
            /*Llamamos a leercolor que nos regresara un vector con el color 
                entre 0 y 1 correspondiente al color del fondo. */
            VectorR3 fondo = leerColor(json, "fondo");
            /*Llamamos a leerDouble para recuperar la intensidad de la luz ambiental */
            double luzAmbiental = leerDouble(json, "luz_ambiental");

            /*!? Validacion correcta? podemos tener luz ambiental negativa o mayor a 1 que saturaria los colores
                manteniendolos en 0 y 1, maximo llegaria a 1? */
            if (luzAmbiental < 0 || luzAmbiental > 1) {
                throw new IllegalArgumentException("La luz ambiental debe estar entre 0 y 1.");
            }
            /*!!-- TEMPORAL JSONOBJECT NO DEBE DE PARECER COMO ATRIBUTO DE ESCENA
                Unicamente fue para validar funcionamiento */
            List<JsonObject> luces = leerLuces(json);     
            /*Llamamos a LeerObjetos para recupear la lsita de los objtos que tendra la escena */   
            List<Objeto3D> objetos = leerObjetos(json);

            /*Regresamos una escena validad con todas sus propiedades */
            return new Escena(ancho,alto,camara,fondo,luzAmbiental,luces,objetos);

            //Este catch va a cachar 2 cualquiera de los 2 errores
        } catch (JsonParseException | IllegalStateException e) {
            /*Aparte pasaremos el mensaje del error y con la "e" la informacion que proporciona Gson.*/
            throw new IllegalArgumentException(
                "El archivo no contiene un JSON válido.", e // Mensaje linde + reporte tecnico
                );
        }
    }

    /**
     * Lee y valida un vector.
     * @param objeto  OBjeto JSON que tiene el campo.
     * @param campo Nombre del campo.
     * @return Vector validado.
     */
    private static VectorR3 leerVector(JsonObject objeto, String campo) {
        /* Hacemos una referencia llamando "elemento" (es generico) y del objeto pedimos 
        el campo*/
        JsonElement elemento = objeto.get(campo);

        /*Checamos que no sea null y que sea un array */
        if (elemento == null || !elemento.isJsonArray()) {
            throw new IllegalArgumentException("El campo '" + campo + "' debe ser un arreglo.");
        }
        /*Una vez checado, lo pedimos como array */
        JsonArray arreglo = elemento.getAsJsonArray();

        /*Todos los vectores que manejaremos deberan de ser de tamanio 3 */
        if (arreglo.size() != 3) {
            throw new IllegalArgumentException("El campo '" + campo + "' debe tener tres componentes.");
        }
        /*Creamos un arreglo para las componentes 
            para despues contruir el vector */
        double[] componentes = new double[3];

        /*Recorremos el arreglo componente a componente */
        for (int i = 0; i < 3; i++) {
            /*Pedimos la primera componente */
            JsonElement componente = arreglo.get(i);

            /*Revisamos que al componente sea un primitivo y que sea un num */
            if (!componente.isJsonPrimitive()|| !componente.getAsJsonPrimitive().isNumber()) {
                throw new IllegalArgumentException("Las componentes de '" + campo + "' deben ser numéricas.");
            }
            /*Declaramos la varible numero que representara una componente */ 
            double numero;

            try {
                /*Lo pedimos como double */
                numero = componente.getAsDouble();
                
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El campo '" + campo + "' debe ser un real.", e);
            }

            /*Revisamos que sea finito */
            if (!Double.isFinite(numero)) {
                throw new IllegalArgumentException("Las componentes de '" + campo + "' deben ser finitas.");
            }
            /*Agregamos a nuestro arreglo de componentes */
            componentes[i] = numero;
        }
        /*Regresamo un vector con todas las componentes*/
        return new VectorR3(componentes[0], componentes[1], componentes[2]);
    }

    /**
     * Lee y valida los objetos tridimensionales de una escena.
     * 
     * !!-- CON POSIBILIDAD DE AGREGAR MAS OBJETOS
     * 
     * @param json Objeto JSON de la escena.
     * @return Lista de objetos tridimensionales construidos y validados.
     * @throws IllegalArgumentException si algún objeto es inválido.
     */
    private static List<Objeto3D> leerObjetos(JsonObject json) {

        /*Hacemos una referencia llamando "elemento" (es generico) y del objeto pedimos el campo "objetos"*/
       JsonElement elemento = json.get("objetos");
       
       /*Revisamos que no sea null y que sea un array*/
       if (elemento == null || !elemento.isJsonArray()) {
           throw new IllegalArgumentException("El campo 'objetos' debe ser un arreglo.");
        }

        /*Una vez verificado lo pedimos como array.*/
        JsonArray objetosArray = elemento.getAsJsonArray();
        /*Creamos nuestra lista que almcenara nuestros objetos */
        List<Objeto3D> objetos = new ArrayList<>();
        
        /*Recorreremos los elementos y los analizaremos uno a uno*/
        for (int i = 0; i < objetosArray.size(); i++) {

            /*Pedimos el objeto i*/
            JsonElement elementoObjeto = objetosArray.get(i);

            //Revisamos que sea un objeto
            if (!elementoObjeto.isJsonObject()) {
                throw new IllegalArgumentException("El objeto " + i + " debe ser un objeto JSON.");
            }

            /*Como ya sabaemos que es un obj, lo pedimos asi*/
            JsonObject objeto = elementoObjeto.getAsJsonObject();

            /*Pedimos el elemento "tipo"*/
            JsonElement tipoElemento = objeto.get("tipo");

            /*Revisamos que no sea null, que sea primitivo y que sea un string */
            if (tipoElemento == null || !tipoElemento.isJsonPrimitive() || !tipoElemento.getAsJsonPrimitive().isString()) {
                throw new IllegalArgumentException("El objeto " + i + " debe tener un tipo válido.");
            }

            /*Una vez verificado, lo pedimos como string*/
            String tipo = tipoElemento.getAsString();

            /*Una vez que ya tenemos el tipo de objeto,
                entraremos al switch para analisar que objeto es y asi
                pedir sus propiedades.*/
            switch (tipo) {
                case "esfera":
                    //Con LeerVector nos dara el centro
                    VectorR3 centro = leerVector(objeto, "centro");
                    //Buscaremos radio y obtenemos como double
                    double radio = leerDouble(objeto, "radio");

                    /*No aceptaremos radio menor o igual a 0 */
                    if (radio <= 0) {
                        throw new IllegalArgumentException("El radio de la esfera " + i + " debe ser positivo.");
                    }
                    //Leeremos el color y verificaremos
                    VectorR3 colorEsfera = leerColor(objeto, "color");
                    /*Una vez verificado todo, lo agregamos a nuestra listita de objetos */
                    objetos.add(new Esfera(centro, radio, colorEsfera));
                    break;
                
                case "plano":
                    //Leeremos su punto perteneciente al plano
                    VectorR3 punto = leerVector(objeto, "punto");
                    //Leeremos su normal
                    VectorR3 normal = leerVector(objeto, "normal");
                    /*Checamos que la normal no sea el vector 0, pues de ser
                    asi tendriamos un plano sin orientacion y como depues debemos
                    de sacar longitud caemos en division entre 0.
                     */
                    if (normal.getX() == 0 && normal.getY() == 0 && normal.getZ() == 0) {
                        throw new IllegalArgumentException("La normal del plano " + i + " no puede ser el vector cero.");
                    }
                    //Leemos color
                    VectorR3 colorPlano = leerColor(objeto, "color");
                    /*Una vez comprobado todo agregaremos, a nuestra listita de objetos */
                    objetos.add(new Plano(normal, punto, colorPlano));
                    break;

                /*Por si nos pasan otro objetos que no reconocemos*/
                default:
                    throw new IllegalArgumentException("Tipo de objeto desconocido: " + tipo);
            }
        }
        /*Regresamos la lista ya con todos los objetos validados. */
        return objetos;
    }

    /**
     * Lee y valida la información de la cámara.
     *
     * @param json Objeto JSON principal de la escena.
     * @return Camara que tendra la escena.
     * @throws IllegalArgumentException si los datos son inválidos.
     */
    private static Camara leerCamara(JsonObject json, int ancho, int alto) {
        
        /*Hacemos una referencia llamando "elemento" (es generico)
         y del objeto pedimos el campo
         */
        JsonElement elemento = json.get("camara");

        //Revisamos que no sea null y que sea un objeto.
        if (elemento == null || !elemento.isJsonObject()) {
            throw new IllegalArgumentException("Falta el objeto 'camara' o tiene un formato inválido.");
        }

        /*Ya vimos que no es null y que es un objeto,
        lo pedimos como objeto.*/
        JsonObject camara = elemento.getAsJsonObject();

        /*
        Llamamos a leerVector para que lea posicion y miraA
         */
        VectorR3 posicion = leerVector(camara, "posicion");
        VectorR3 miraA = leerVector(camara, "mira_a");

        /*
        Llamamos a LeerDouble para que indetifique el fov
        y revise que sea valido
         */
        double fov = leerDouble(camara, "fov");

        /*
        Comprobamos que 0<fov<180
        pues si fov = 180 al calcular el viewport caemos en divicion entre 0.
         */
        if (fov <= 0 || fov >= 180) {
            throw new IllegalArgumentException("El campo 'fov' debe estar entre 0 y 180 grados.");
        }

        /*
        Debemos de revisar que la posicion y la mira no sean el mismo punto, pues seria raro 
            mirar justo donde estas.*/
        if (posicion.getX() == miraA.getX() && posicion.getY() == miraA.getY() && posicion.getZ() == miraA.getZ()) {
            throw new IllegalArgumentException(
                "La cámara no puede mirar hacia su propia posición.");
        }
        /*Regresamos la camara ya validad. */
        return new Camara(posicion, miraA, fov, ancho, alto);
    }
    
    /**
     * !!--TEMPORAL no debemos de tener JsonObject
     * de momento es para test y funcionamiento.
     * 
     * Lee y valida las luces puntuales de una escena.
     * 
     * @param json Objeto JSON  de la escena.
     * @return DEBEMOS DE REGRESAR LISTA DE LUCES NO DE JSONOBJECT
     * @throws IllegalArgumentException si las luces son inválidas.
     */
    private static List<JsonObject> leerLuces(JsonObject json) {

        /*Hacemos una referencia llamando "elemento" (es generico) y del json pedimos 
        el campo*/
        JsonElement elemento = json.get("luces");

        /*
        Comprobamos que sea no nulL y que sea aun array.
         */
        if (elemento == null || !elemento.isJsonArray()) {
            throw new IllegalArgumentException("El campo 'luces' debe ser un arreglo.");
        }

        //Lo pedimos como array.
        JsonArray luces = elemento.getAsJsonArray();
        /*Creamos nuestra lista de luces */
        /*CAMBIAR A LUCESPUNTUALES.CLASS */
        List<JsonObject> lucesTotales = new ArrayList<>();

        /*Debemos de ver que minimo tenemos una luz */
        /* !? podemos tener 0 luces?*/
        if (luces.size() == 0) {
            throw new IllegalArgumentException("La escena debe contener al menos una luz.");
        }
        /*
        Recorreremos el array y vamos a ver cada luz independientemente.
         */
        for (int i = 0; i < luces.size(); i++) {

             /* Hacemos una referencia llamando "elementoLuz" (es generico) y del 
                array principal tomamos el i.
             */
            JsonElement elementoLuz = luces.get(i);

            /*Vemos que sea un objeto*/
            if (!elementoLuz.isJsonObject()) {
                throw new IllegalArgumentException("La luz " + i + " debe ser un objeto JSON.");
            }
            
            /*!!
            AQUI DEBEMOS DE IMPLEMENTAR LUZ PUNTUAL
            !!*/

            /*
            Obtenemos y creamos luz puntual.
             */
            JsonObject luz = elementoLuz.getAsJsonObject();

            /*Despues con leerVector buscamos posicion*/
            VectorR3 posicion = leerVector(luz, "posicion");
            /*Buscamos y checamos que la intensidad sea valida */
            double intensidad = leerDouble(luz, "intensidad");
            
            /*Comprobamos que la intensidad sea valida*/
            /* !? Podemos tener intensidad negativa y topa hasta? */
           if (intensidad < 0) {
               throw new IllegalArgumentException("La intensidad de la luz " + i + " no puede ser negativa.");
                }
            
            /*Una vez validad agrgamos la luz a nuestra lsita */
            lucesTotales.add(luz);
        }
        //!!!!DEBEMOS DE REGRESAR LUCES PUNTUAL
        return lucesTotales;
    }

    /**
     * Lee y valida un color RGB desde un objeto JSON.
     *
     * @param objeto Objeto JSON que contiene el color.
     * @param campo Nombre del campo que se vamos a leer.
     * @return VectorR3 con las componentes RGB.
     * @throws IllegalArgumentException si el color es inválido.
     */
    private static VectorR3 leerColor(JsonObject objeto, String campo) {

        // Leemos y validamos los componentes.
        VectorR3 color = leerVector(objeto, campo);

        /*
         Como un color debe cumplir:
         (r,g,b) ∈ [0,1]^3 comprobamos que cada componente esté en ese intervalo.
         */
        double[] componentes = {color.getX(), color.getY(), color.getZ()};
        
        for (double componente : componentes) {
            if (componente < 0 || componente > 1) {
                throw new IllegalArgumentException("Las componentes de '" + campo+ "' deben estar entre 0 y 1.");
            }
        }
        /*Regresamos el vector que representara un color */
        return color;
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
            throw new IllegalArgumentException("El campo '" + campo + "' debe ser numérico.");
        }
        //Declaramos numero que regresaremos
        int numero;

        try {
            //Intentamos pasar a entero
            numero = Integer.parseInt(valor.getAsString());

            //Cacha error de conversion.
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo '" + campo + "' debe ser un entero.", e);
        }

        /*
        Por ultimo checamos que el numero sea positivo y diferente de 0,
        pues no tiene mucho sentido hacer escena de alguna dimension 0 xd.
         */
        if (numero <= 0) {
            throw new IllegalArgumentException("El campo '" + campo + "' debe ser positivo.");
        }
        /*Numero extraido correctamente */
        return numero;
    }

    /**
     * Lee un número real finito desde un campo JSON.
     *
     * @param objeto Objeto JSON que tiene el campo.
     * @param campo Nombre del campo.
     * @return Valor numérico del campo.
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
            throw new IllegalArgumentException("El campo '" + campo + "' debe ser numérico.");
        }
        //Como ya comprobamos lo pedimos como double
        double valor;

        try {
            //Intentamos pasar a double
            valor = Double.parseDouble(elemento.getAsString());

            //Cacha error de conversion.
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El campo '" + campo + "' debe ser un real.", e);
        }
        //Revisamos que sea finito
        if (!Double.isFinite(valor)) {
            throw new IllegalArgumentException("El campo '" + campo + "' debe ser finito.");
        }

        return valor;
    }
}

package escena;
/*Clase lista para objetos3D y luces. */
import java.util.List;
/*!!TEMPORAL HASTA OBTENER LA CLASE LUCES PUNTUALES */
import com.google.gson.JsonObject;

/*Clases que necesita escena como atributis */
import camara.Camara;
import geometria.Objeto3D;
import matematicas.VectorR3;

/**
 * Representa la información de una escena que será renderizada.
 *
 * Por ahora, almacena únicamente las dimensiones de la imagen.
 */
public class Escena {
    /*Ancho y alto de la escena*/
    private final int ancho;
    private final int alto;

    /*Camara de la escena */
    private final Camara camara;
    /*Vector que representa el color del fondo */
    private final VectorR3 fondo;
    /*Intensidad de la luz ambiental*/
    private final double luzAmbiental;
    /*Lista de luces puntuaales y de objetos */
    private final List<JsonObject> luces; // !!!Temporal!!!
    private final List<Objeto3D> objetos;

    
    /**
     * Constructor de una escena.
     * 
     * @param ancho Ancho de la escena.
     * @param alto  Alto de la escena.
     * @param camara Camara de la escena.
     * @param fondo Vector que representara el color del fondo.
     * @param luzAmbiental  Intensidad de la luz ambiental de la escena.
     * @param luces Lista de luces puntuales.
     * @param objetos  Lista de los objetos en la escena.
     */
    public Escena(int ancho, int alto, Camara camara, VectorR3 fondo, double luzAmbiental,List<JsonObject> luces, List<Objeto3D> objetos) {
        
        /*No crearemos escenas con ancho o alto, negativos o 0 */
        if (ancho <= 0 || alto <= 0) {
            throw new IllegalArgumentException("Las dimensiones de la imagen deben ser positivas.");
        }

        this.ancho = ancho;
        this.alto = alto;
        this.camara = camara;
        this.fondo = fondo;
        this.luzAmbiental = luzAmbiental;
        this.luces = List.copyOf(luces);
        this.objetos = List.copyOf(objetos);
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    @Override
    public String toString() {
        return "Escena {\n"
                + "  Dimensiones: " + ancho + " x " + alto + "\n"
                + "  Camara: " + camara + "\n"
                + "  Fondo: " + fondo + "\n"
                + "  Luz ambiental: " + luzAmbiental + "\n"
                + "  Luces puntuales: " + luces + "\n"
                + "  Objetos: " + objetos + "\n"
                + "}";
    }

}

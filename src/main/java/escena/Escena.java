package escena;
/*Clase lista para objetos3D y luces. */
import java.util.List;

/*Clases que necesita escena como atributis */
import camara.Camara;
import geometria.Objeto3D;
import matematicas.VectorR3;
import iluminacion.LuzPuntual;

/**
 * Representa la información de una escena tridimensional.
 *
 * Almacena las dimensiones de la imagen, la cámara,
 * el color de fondo, la iluminación ambiental,
 * las luces puntuales y los objetos geométricos.
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
    private final List<LuzPuntual> luces; 
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
   public Escena(int ancho, int alto, Camara camara, VectorR3 fondo, double luzAmbiental,List<LuzPuntual> luces, List<Objeto3D> objetos) {
       
       /*Comprobaciones basicas para evitar una mala construccion de una escena*/
       if (ancho <= 0 || alto <= 0) {
           throw new IllegalArgumentException("Las dimensiones de la imagen deben ser positivas.");
        }
        if (camara == null) {
            throw new IllegalArgumentException("La cámara no puede ser null.");
        }

        if (fondo == null) {
            throw new IllegalArgumentException("El fondo no puede ser null.");
        }

        if (!Double.isFinite(luzAmbiental) || luzAmbiental < 0 || luzAmbiental > 1) {
            throw new IllegalArgumentException("La luz ambiental debe estar entre 0 y 1.");
        }

        if (luces == null || objetos == null) {
            throw new IllegalArgumentException("Las listas de luces y objetos no pueden ser null.");
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
    
    /*Getters */    
    public Camara getCamara() {
        return camara;
    }
    
    public VectorR3 getFondo() {
        return fondo;
    }
    
    public double getLuzAmbiental() {
        return luzAmbiental;
    }
    
    public List<Objeto3D> getObjetos() {
        return objetos;
    }
    public List<LuzPuntual> getLucesPuntuales(){
        return  luces;
    }
}

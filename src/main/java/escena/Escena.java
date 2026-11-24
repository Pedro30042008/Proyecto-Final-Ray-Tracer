package escena;
/*Clase lista para objetos3D y luces. */
import java.util.List;

/*Clases que necesita escena como atributis */
import camara.Camara;
import geometria.Objeto3D;
import matematicas.VectorR3;
import iluminacion.LuzPuntual;
import geometria.Esfera;
import geometria.Plano;

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
    
    public static String representacionLuces(List<LuzPuntual> luces){

        if(luces == null){
            throw new IllegalArgumentException("Luces no puden ser null");
        }
        if(luces.isEmpty())
            return "[]";

        StringBuilder sb = new StringBuilder();
        

        int i = 1;
        for(LuzPuntual luzActual : luces){

            if(luzActual == null){
             continue;   
            }

            sb.append("     "+i+". ");
            sb.append("Posición: "+luzActual.getPosicion()+"\n");
            sb.append("        Intensidad: "+luzActual.getIntensidad()+"\n");
            i++;
        }
        
        return sb.toString();

    }

    public static String representacionObjetos(List<Objeto3D> objetos){

        if(objetos == null){
            throw new IllegalArgumentException("Objetos no puden ser null");
        }
        if(objetos.isEmpty())
            return "[]";

        StringBuilder sb = new StringBuilder();
        

        int i = 1;
        for(Objeto3D objetoActual : objetos){

            if(objetoActual == null){
             continue;   
            }

            if(objetoActual instanceof Esfera){
                Esfera esfera = (Esfera) objetoActual;
                sb.append("     "+i+". Esfera\n");
                sb.append("        Centro: "+esfera.getCentro()+"\n");
                sb.append("        Radio: "+esfera.getRadio()+"\n");
                sb.append("        Color: "+esfera.getColor()+"\n");
            }

            if(objetoActual instanceof Plano){
                Plano plano = (Plano) objetoActual;
                sb.append("     "+i+". Plano\n");
                sb.append("        Normal: "+plano.getNormal()+"\n");
                sb.append("        Punto: "+plano.getPunto()+"\n");
                sb.append("        Color: "+plano.getColor()+"\n");
            }
            
            i++;
        }
        
        return sb.toString();

    }


    @Override
    public String toString() {
        return "Escena {\n"
        + "  Dimensiones: " + ancho + " x " + alto + "\n"
        + "  Camara: " + camara + "\n"
        + "  Fondo: " + fondo + "\n"
        + "  Luz ambiental:" + luzAmbiental + "\n"
        + "  Luces puntuales:[\n" + representacionLuces(luces) + "        ]\n"
        + "  Objetos:[\n" + representacionObjetos(objetos) + "        ]\n"
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

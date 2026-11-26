package escena;

/* Clase lista para objetos3D y luces. */
import java.util.List;

/* Clases que necesita escena como atributos. */
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
       
       /* Comprobaciones basicas para evitar una mala construccion de una escena. */
       if (ancho <= 0 || alto <= 0)
           throw new IllegalArgumentException("Las dimensiones de la imagen deben ser positivas.");
       
        if (camara == null)
            throw new IllegalArgumentException("La cámara no puede ser null.");

        if (fondo == null)
            throw new IllegalArgumentException("El fondo no puede ser null.");

        if (!Double.isFinite(luzAmbiental) || luzAmbiental < 0 || luzAmbiental > 1)
            throw new IllegalArgumentException("La luz ambiental debe estar entre 0 y 1.");

        if (luces == null || objetos == null)
            throw new IllegalArgumentException("Las listas de luces y objetos no pueden ser null.");

	/* Asignación de datos verificados. */
        this.ancho = ancho;
        this.alto = alto;
        this.camara = camara;
        this.fondo = fondo;
        this.luzAmbiental = luzAmbiental;
        this.luces = List.copyOf(luces);
        this.objetos = List.copyOf(objetos);
    }

    /**
     * Método para obtener el ancho de la imagen de la escena.
     *
     * @return el ancho de la imagen de la escena.
     */
    public int getAncho() {
        return this.ancho;
    }

    /**
     * Método para obtener el alto de la imagen de la escena.
     *
     * @return el alto de la imagen de la escena.
     */
    public int getAlto() {
        return this.alto;
    }

    /**
     * Método para obtener la cámara de la escena.
     *
     * @return la cámara de la escena.
     */
    public Camara getCamara() {
        return this.camara;
    }

    /**
     * Método para obtener el color del fondo de la imagen.
     *
     * @return el color del fondo de la imagen.
     */
    public VectorR3 getFondo() {
        return new VectorR3(fondo);
    }

    /**
     * Método para obtener la intensidad de la luz ambiental en la escena.
     *
     * @return la intensidad de la luz ambiental en la escena.
     */
    public double getLuzAmbiental() {
        return this.luzAmbiental;
    }

    /**
     * Método para obtener la lista de objetos en la escena.
     *
     * @return lista de objetos en la escena.
     */
    public List<Objeto3D> getObjetos() {
        return this.objetos;
    }

    /**
     * Método para obtener la lista de luces puntuales en la escena.
     *
     * @return lista de luces puntales en la escena.
     */
    public List<LuzPuntual> getLucesPuntuales(){
        return this.luces;
    }
    
    /**
     * Devuelve una representación en cadena de texto para la lista de
     * luces obtenida del archivo JSON.
     *
     * @param luces lista de luces putuales.
     * @return representación en cadena de texto de la lista de luces.
     * @throws IllegalArgumentException si la lista es <code>null</code>.
     */
    public static String representacionLuces(List<LuzPuntual> luces){
	/* Si la lista de luces no es distinta de null. */
        if(luces == null)
	    /* Lanzamos la excepción correspondiente. */
            throw new IllegalArgumentException("Luces no puden ser null");
	
	/* Si la lista de luces esta vacía. */
        if(luces.isEmpty())
	    /* Regresamos la lista vacía. */
            return "[]";

	/* Cadena de texto para guardar la lista de luces. */
        StringBuilder sb = new StringBuilder();
        
        /* Contador de luces. */
        int i = 1;

	/* Recorremos la lista de luces. */
        for(LuzPuntual luzActual : luces){

	    /* Si una luz es null. */
            if(luzActual == null)
		/* Ignoramos esta luz.*/
		continue;

	    /* Agregamos a la cadena de texto la i-esima luz. */
            sb.append("     " + i + ". " + luzActual);
	    
	    /* Aumentamos el contador de luces. */
            i++;
        }

	/* Regresamos la representación de texto para la lista de luces. */
        return sb.toString();
    }

    /**
     * Devuelve una representación en cadena de texto para la lista de
     * objetos obtenida del archivo JSON.
     *
     * @param objetos lista de objetos.
     * @return representación en cadena de texto de la lista de objetos.
     * @throws IllegalArgumentException si la lista es <code>null</code>.
     */
    public static String representacionObjetos(List<Objeto3D> objetos){
	/* Si la lista de objetos no es distinta de null. */
        if(objetos == null)
	    /* Lanzamos la excepción correspondiente. */
            throw new IllegalArgumentException("Objetos no puden ser null");
	
	/* Si la lista está vacía. */
        if(objetos.isEmpty())
	    /* Regresamos la lista vacía. */
            return "[]";

	/* Cadena de texto para guardar la lista de objetos. */
        StringBuilder sb = new StringBuilder();
        
	/* Contador de objetos. */
        int i = 1;

	/* Recorremos la lista de objetos. */
        for(Objeto3D objetoActual : objetos){

	    /* Si un objeto es null. */
            if(objetoActual == null)
		/* Ignoramos ese objeto. */
		continue;   

	    /* Agregamos a la cadena de texto el i-esimo objeto. */
	    sb.append("     "+ i + ". " + objetoActual);

	    /* Aumentamos el contador de objetos. */
            i++;
        }

	/* Regresamos la representación de texto para la lista de objetos. */
        return sb.toString();
    }

    /**
     * Devuelve la representación en cadena de texto de la escena.
     *
     * @return cadena de texto que representa la escena.
     */
    @Override
    public String toString() {
	/* Retornamos la representación en cadena de texto de la escena. */
        return "Escena {\n"
	    + "  Dimensiones: " + ancho + " x " + alto + "\n"
	    + "  Camara: \n" + camara + "\n"
	    + "  Fondo: " + fondo + "\n"
	    + "  Luz ambiental: " + luzAmbiental + "\n"
	    + "  Luces puntuales:[\n" + representacionLuces(luces) + "        ]\n"
	    + "  Objetos:[\n" + representacionObjetos(objetos) + "        ]\n"
	    + "}";
    }    
}

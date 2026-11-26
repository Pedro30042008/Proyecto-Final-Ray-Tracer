package camara;

import matematicas.VectorR3;
import matematicas.Rayo;
import matematicas.Verificador;

/**
 * Representa una cámara virtual encargada de generar
 * los rayos que atraviesan los píxeles de la imagen.
 */
public class Camara {

    /* Datos de la cámara. */

    /* Punto de ubicación de la cámara. */
    private VectorR3 posicion;
    /* Punto donde mirá la cámara y esta su centro. */
    private VectorR3 miraA;
    /* Ángulo de visión que abarca la cámara horizontalmente. */
    private double fov;
    private double fovGrados;

    /* Distancia de la cámara al viewport. */
    private double distanciaViewport = 1.0;

    /* Dimensiones de la imagen en píxeles. */
    private int anchoImagen;
    private int altoImagen;

    /* Vectores que definen la orientación. */

    /* Dirección hacia la derecha de la cámara. */
    private VectorR3 u;
    /* Dirección hacia arriba de la cámara. */
    private VectorR3 v;
    /* Dirección hacia al frente de la cámara. */
    private VectorR3 w;

    /* Dimensiones del viewport. */
    private double anchoViewport;
    private double altoViewport;

    /* Vectores de desplazamiento para el Viewport. */
    private VectorR3 uViewport;
    private VectorR3 vViewport;

    /* Punto donde esta el centro del Viewport.  */
    private VectorR3 centroViewport;
    
    /* Punto inicial donde se origina el primer rayo.  */
    private VectorR3 centroPrimerPixel;
    
    
    /**
     * Construye una cámara con su posición, orientación, campo de visión
     * y dimensiones de imagen.
     *
     * @param posicion punto donde se localia la cámara.
     * @param miraA punto donde mira el centro de la cámara.
     * @param fov ángulo de visión horizontal de la cámara.
     * @param ancho número de pixeles que indican el ancho de la imagen.
     * @param alto número de pixeles que indican el alto de la imagen.
     */
    public Camara(VectorR3 posicion, VectorR3 miraA, double fov,
		  int anchoImagen, int altoImagen) {
	/* Verificamos que el punto de posición de la cámara no sea nulo. */
	Verificador.verificarNotNull(posicion,
				     "La posición de la cámara debe ser distinto de null.");
	/* Verificamos que el punto de miraA de la cámara no sea nulo. */
	Verificador.verificarNotNull(miraA,
				     "La miraA de la cámara debe ser distinto de null.");
	/* Verificamos que la miraA sea distinto de la posición de la cámara. */
	if (miraA.resta(posicion).esCero())
            throw new IllegalArgumentException(
                    "La cámara debe mirar hacia un punto distinto.");

        /* Validamos que el FOV sea finito y sea mayor a 0 y menor a 180 grados. */
        if (!Double.isFinite(fov) || fov <= 0 || fov >= 180)
            throw new IllegalArgumentException(
                    "El FOV debe ser mayor a 0 y menor a 180 grados.");

        /* Validamos que las dimensiones de la imagen sean mayores a cero. */
        if (anchoImagen <= 0 || altoImagen <= 0)
            throw new IllegalArgumentException(
                    "Las dimensiones de la imagen deben ser mayores a cero.");

	/* Asignamos los datos de la cámara. */ 
        this.posicion = new VectorR3(posicion);
        this.miraA = new VectorR3(miraA);
	this.fovGrados = fov;
        this.fov = Math.toRadians(fov); // Convertimos el FOV de grados a radianes.
	
	/* Asignamos las dimensiones de la imagen. */
        this.anchoImagen = anchoImagen;
        this.altoImagen = altoImagen;

	/* Calculamos los atributos de la cámara y el Viewport. */
	inicializarCamara();
    }

    /**
     * Método axiliar para inicializar la cámara.
     */
    private void inicializarCamara() {
	/* Calculamos los vectores de dirección u, v, w de la cámara. */
	
	/* Calculamos vector w: El frente de la cámara. */
        this.w = this.miraA.resta(this.posicion).vectorUnitario();
	
	/* Calcular vector u: La derecha de la cámara.*/

        /* Vector auxiliar para orientar la cámara respecto al mundo tridimensional. */
        VectorR3 upVector = new VectorR3(0, 1, 0);

        /* Calculamos el producto cruz para obtener un vector perpendicular que apunte
	   a la derecha de la cámara.
	   Nota: Si el producto cruz es UpVector x w el vector apunta hacia la izquierda. */
        VectorR3 derecha = this.w.productoCruz(upVector);

        /* Si los vectores son paralelos, elegimos otra referencia para upVector. */
        if (derecha.esCero()) {
	    /* Tomamos al canónico en dirección a z. */
            upVector = new VectorR3(0, 0, 1);
	    /* Construimos el vector que apunta a la derecha con la nueva referencia.
	       Nota: Si el producto cruz es UpVector x w el vector apunta hacia la izquierda. */
            derecha = this.w.productoCruz(upVector);
        }

        /* Normalizamos el vector unitario u de la cámara. */
        this.u = derecha.vectorUnitario();
	
	/* Calculamos vector v: Hacia arriba de la cámara.
	   Nota: Si el producto cruz es w x u el vector apunta hacia abajo.
	         Al hacer el producto cruz de dos vectores perpendiculares unitarios
	         el resultante queda unitario. */
        this.v = this.u.productoCruz(this.w);

        /* Calculamos las dimensiones del viewport. */
	
	/* Calculamos la mitad del ancho del Viewport con trigonometría y lo duplicamos
	   para asiganarlo al ancho proporcional del Viewport. */
        this.anchoViewport = 2 * distanciaViewport * Math.tan(this.fov / 2);
	/* Mediante semejanza calculamos el alto del Viewport. */
        this.altoViewport = this.anchoViewport * ((double) this.altoImagen / this.anchoImagen);

	/* Calculamos los vectores de desplazamiento del Viewport. */

	/* Partimos el ancho del Viewport dado el ancho de la imagen en pixeles.
	   Esto con el objetivo de medir el ancho de un "pixel" dentro del Viewport.
	   
	   Con el producto escalar ajustamos la nueva magnitud del vector uViewport
	   a partir del vector unitario u de la cámara.

	   Desplazamiento de izquierda a derecha.
	*/
	this.uViewport = this.u.productoPorUnEscalar((double) this.anchoViewport / this.anchoImagen);
	
	/* Partimos el alto del Viewport dado el alto de la imagen en pixeles.
	   Esto con el objetivo de medir la altura de un "pixel" dentro del Viewport.
	   
	   Con el producto escalar ajustamos la nueva magnitud del vector vViewport
	   a partir del vector unitario v de la cámara.

	   Desplazamientos de arriba a abajo.
	*/
	this.vViewport = this.v.productoPorUnEscalar((double) this.altoViewport / -this.altoImagen);

	/* Localizamos el centro del Viewport, desplazando tantas veces como sea
	   necesario el vector del frente de la cámara partiendo de la
	   ubicación de la cámara. */
        this.centroViewport = this.posicion.suma(this.w.productoPorUnEscalar(distanciaViewport));
	
	/* Localizamos la esquina superior izquierda del Viewport. */
	
	/* Del centro, avanzamos a la izquierda la mitad del ancho del Viewport
	   con el vector u de la cámara. */
	VectorR3 desplazamientoX = this.u.productoPorUnEscalar((double) this.anchoViewport / 2);
	/* Del centro, subimos la mitad del ancho del Viewport
	   con el vector v de la cámara. */
	VectorR3 desplazamientoY = this.v.productoPorUnEscalar((double) this.altoViewport / 2);
	
	/* Asignamos la equina superior izquierda. */
	VectorR3 esquinaViewport = this.centroViewport.resta(desplazamientoX).suma(desplazamientoY);

	/* Lozalizamos la ubicación del centro del primer pixel. */

	/* Avanzamos la mitad del ancho de un pixel. */
	desplazamientoX = this.uViewport.productoPorUnEscalar(0.5);
	/* Avanzamos la mitad del alto de un pixel. */
	desplazamientoY = this.vViewport.productoPorUnEscalar(0.5);

	/* A partir de la esquina superior izquierda localizamos el primer centro del primer pixel. */
	this.centroPrimerPixel = esquinaViewport.suma(desplazamientoX).suma(desplazamientoY);
    }

    /**
     * Genera el rayo que atraviesa el centro del píxel
     * indicado por su fila y columna.
     *
     * @param fila índice de la fila del píxel, comenzando en cero.
     * @param columna índice de la columna del píxel, comenzando en cero.
     * @return el rayo que sale de la cámara y atraviesa el centro del píxel.
     * @throws IllegalArgumentException si el píxel está fuera de la imagen.
     */
    public Rayo generarRayo(int fila, int columna) {
        /* Validamos que el pixel pertenezca a la imagen */
        if (columna < 0 || columna >= this.anchoImagen || fila < 0 || fila >= this.altoImagen)
            throw new IllegalArgumentException(
                    "El pixel está fuera de la imagen");
	/* Calculamos el centro del pixel con coordenadas (fila, columna).*/

	/* Desplazamiento necesario en X. */
	VectorR3 desplazamientoX = this.uViewport.productoPorUnEscalar(columna);
	/* Desplazamiento necesario en Y. */
	VectorR3 desplazamientoY = this.vViewport.productoPorUnEscalar(fila);

	/* Calculamos el centro del pixel (fila, columna) a partir del centro del primer pixel.*/
	VectorR3 centroPixel = this.centroPrimerPixel.suma(desplazamientoX).suma(desplazamientoY);
	
        /* Calcular la dirección del rayo. */
        VectorR3 direccion = centroPixel.resta(this.posicion);

        /* Devolver el Rayo. */
        return new Rayo(new VectorR3(this.posicion), direccion);
    }

    /**
     * Produce una representación en cadena de texto de la cámara.
     *
     * @return cadena dde texto que representa la cámara.
     */
    @Override
    public String toString() {
	// Cadena de texto para guardar la representación de la cámara.
	StringBuilder camara = new StringBuilder();

	// Formato de la cámara.
        camara.append("        Posición: "+ this.posicion +"\n");
        camara.append("        Mira_a: "+ this.miraA +"\n");
        camara.append("        FOV: "+ this.fovGrados +"°");

	// Regresamos la representación de la camara.
	return camara.toString();
    }
}

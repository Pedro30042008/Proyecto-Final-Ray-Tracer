package renderizado;

import escena.Escena;
import camara.Camara;
import iluminacion.LuzPuntual;
import matematicas.Rayo;
import matematicas.VectorR3;
import geometria.Objeto3D;
import geometria.Interseccion;

/**
 * Calcula los colores de los píxeles de una escena
 * utilizando los rayos generados por la cámara.
 */
public class Renderizador {

    /* Escena que queremos renderizar. */
    private final Escena escena;

    /**
     * Construye un renderizador para una escena.
     *
     * @param escena escena que será renderizada.
     */
    public Renderizador(Escena escena) {

        /* Verificar que la escena no sea null. */
        if(escena == null){
            throw new IllegalArgumentException(
                    "La escena no puede estar vacía.");
        }

        /* Guardar la escena. */
        this.escena = escena;
    }

    /**
     * Calcula el color correspondiente a un rayo.
     *
     * @param rayo rayo que queremos evaluar.
     * @return color RGB correspondiente al rayo.
     */
    public VectorR3 calcularColor(Rayo rayo) {

        /* Validar que el rayo no sea null. */
        if (rayo == null) {
            throw new IllegalArgumentException(
                    "El rayo no puede ser null.");
        }

        /* Intersección más cercana encontrada hasta ahora. */
        Interseccion interseccionCercana = null;

        /* Objeto al que pertenece esa intersección. */
        Objeto3D objetoCercano = null;

        /* Menor distancia paramétrica encontrada. */
        double tMinimo = Double.POSITIVE_INFINITY;

        /* Buscar la intersección más cercana */
        for(Objeto3D objeto : escena.getObjetos()){

            /* Calcular si el rayo intersecta al objeto*/
            Interseccion interseccion = objeto.intersectar(rayo);

            /* Evaluar que la intersección no sea nula y que su T sea menor que tMin*/
            if(interseccion != null && interseccion.getT() < tMinimo){
                interseccionCercana = interseccion;
                objetoCercano = objeto;
                tMinimo = interseccion.getT();
            }
        }

        /* Si no hubo intersección, devolver el color de fondo. */
        if (interseccionCercana == null) {
            return escena.getFondo();
        }

        /*Si hubo intersección, calculamos la ilumniación usando lambert
        * Devolvemos el color con la iluminación */
        return calcularIluminacion(objetoCercano, interseccionCercana);
    }


    /**
     * Calcula el color de una superficie considerando
     * iluminación ambiental y difusa de Lambert.
     *
     * @param objeto objeto intersectado.
     * @param interseccion intersección del rayo con el objeto.
     * @return color RGB limitado al intervalo [0, 1].
     */
    private VectorR3 calcularIluminacion(
            Objeto3D objeto, Interseccion interseccion) {
	
        /* Obtener el color original del objeto. */
        VectorR3 colorObjeto = objeto.getColor();

        /* Obtener la intensidad ambiental. */
        double ambiental = escena.getLuzAmbiental();

        /* Obtener el punto y la normal de la superficie. */
        VectorR3 punto = interseccion.getPunto();
        VectorR3 normal = interseccion.getNormal();

        /* Comenzar con la contribución de la luz ambiental. */
        double iluminacionTotal = ambiental;

        /* Recorrer las luces puntuales. */
        for (LuzPuntual luz : escena.getLucesPuntuales()) {

            /* Obtener la posición de la luz. */
            VectorR3 posicionLuz = luz.getPosicion();

            /* Calcular el vector desde el punto hacia la luz. */
            VectorR3 direccionLuz = posicionLuz.resta(punto);

            /* Ignorar esta luz si coincide con el punto de intersección. */
            if (direccionLuz.esCero()) {
                continue;
            }

	    /* Verificamos si debe haber sombra. */
	    if (esSombra(new Rayo(punto, direccionLuz))) {
		/* No afectamos a la suma. */
		continue;
	    }

	    /* Obtener el vector unitario que apunta hacia la luz. */
            VectorR3 L = direccionLuz.vectorUnitario();
	    
            /* Calcular el producto punto entre la normal y la luz. */
            double productoPuntoNL = normal.productoPunto(L);

            /* Evitar contribuciones negativas. */
            double factorDifuso = Math.max(0, productoPuntoNL);

            /* Calcular el color con iluminación ambiental y difusa
             * Contribución = I*max{0, N.L}*/
            double contribucion =  luz.getIntensidad() * factorDifuso;

            /* Acumular la contribución de esta luz. */
            iluminacionTotal = iluminacionTotal + contribucion;
        }

        /* Aplicar la iluminación al color del objeto. */
        VectorR3 colorFinal =
                colorObjeto.productoPorUnEscalar(iluminacionTotal);

        /* Limitar cada componente RGB. */
        double rojo = Math.min(1, Math.max(0, colorFinal.getX()));
        double verde = Math.min(1, Math.max(0, colorFinal.getY()));
        double azul = Math.min(1, Math.max(0, colorFinal.getZ()));

        return new VectorR3(rojo, verde, azul);
    }


    /**
     * Método auxiliar para verificar si el objeto tiene sombra con una luz.
     *
     * @param rayoSombra rayo que va del punto de intereccion a la luz, donde
     *                   si t es igual a 1, intersectamos la luz.
     * @return <code>true</code> si t se encuentra en el intervalo (0 - 1),
     *         <code>false</code> en otro caso.
     */
    private boolean esSombra(Rayo rayoSombra){
	// Si hay algun obstaculo entre la figura y la luz.
	for (Objeto3D objeto : escena.getObjetos()) {
	    // Referenciamos la intersección a guardar si hay un obstáculo entre la figura y la luz.
	    Interseccion interseccion = objeto.intersectar(rayoSombra);
	    // Si hubo una intersección que estuviera entre la luz y el punto de intersección.
	    if (interseccion != null && interseccion.getT() < 1 - 1e-6)
		// Devolvemos true.
		return true;
	}
	// Devolvemos false al no encontrar intersecciones con otras figuras.
	return false;
    }
    
    /**
     * Calcula los colores de todos los píxeles de la imagen.
     *
     * @return matriz de colores RGB organizada por filas y columnas.
     */
    public VectorR3[][] renderizar() {

        /* Obtener la cámara de la escena. */
        Camara camara = escena.getCamara();

        /* Obtener las dimensiones de la imagen. */
        int ancho = escena.getAncho();
        int alto = escena.getAlto();

        /* Crear la matriz de píxeles. */
        VectorR3[][] pixeles = new VectorR3[alto][ancho];

        /* Recorrer todas las filas. */
        for(int fila = 0; fila < alto; fila++){
            /* Recorrer todas las columnas. */
            for(int columna = 0; columna < ancho; columna++){

                /* Generar el rayo correspondiente. */
                Rayo rayo = camara.generarRayo(fila, columna);

                /* Calcular el color del rayo. */
                VectorR3 color = calcularColor(rayo);

                /* Guardar el color en la matriz. */
                pixeles[fila][columna] = color;
            }
        }
        /* Devolver la matriz completa. */
        return pixeles;
    }
}

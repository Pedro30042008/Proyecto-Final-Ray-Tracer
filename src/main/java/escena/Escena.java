
package escena;

/**
 * Representa la información de una escena que será renderizada.
 *
 * Por ahora, almacena únicamente las dimensiones de la imagen.
 */
public class Escena {

    private final int ancho;
    private final int alto;

    /**
     * Construye una escena con las dimensiones indicadas.
     *
     * @param ancho Ancho de la imagen en píxeles.
     * @param alto Alto de la imagen en píxeles.
     * @throws IllegalArgumentException si alguna dimensión no es positiva.
     */
    public Escena(int ancho, int alto) {
        if (ancho <= 0 || alto <= 0) {// Para no crear escenas con dimensiones negativas o ceros.
            throw new IllegalArgumentException(
                "Las dimensiones de la imagen deben ser positivas."
            );
        }

        this.ancho = ancho;
        this.alto = alto;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }
}

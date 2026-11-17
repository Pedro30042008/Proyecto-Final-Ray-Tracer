
public class Camara {
    private final VectorR3 posicion;
    private final VectorR3 miraA;
    private final double fov;
    private final int ancho;
    private final int alto;

    public Camara(VectorR3 posicion, VectorR3 miraA,
                  double fov, int ancho, int alto) {
        this.posicion = posicion;
        this.miraA = miraA;
        this.fov = fov;
        this.ancho = ancho;
        this.alto = alto;
    }

    public Rayo generarRayo(int columna, int fila) {
        // 1. Calcular los vectores de la cámara
        // 2. Calcular el viewport
        // 3. Encontrar el centro del píxel
        // 4. Calcular la dirección
        // 5. Devolver el rayo
        throw new UnsupportedOperationException(
                "Pendiente de implementar");
    }
}

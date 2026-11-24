package renderizado;

import escena.Escena;
import matematicas.VectorR3;

/**
 * Coordina el renderizado de una escena utilizando trabajadores.
 */
public class RenderizadorConcurrente {

    /* Escena que se va a renderizar. */
    private final Escena escena;

    /* Objeto que sabe calcular los colores de los píxeles. */
    private final Renderizador renderizador;

    /**
     * Construye el coordinador del renderizado.
     *
     * @param escena escena que será renderizada.
     */
    public RenderizadorConcurrente(Escena escena) {

        if (escena == null) {
            throw new IllegalArgumentException(
                "La escena no puede ser null."
            );
        }

        this.escena = escena;
        this.renderizador = new Renderizador(escena);
    }


    /**
     * Renderiza una escena repartiendo las filas entre trabajadores.
     *
     * @param trabajadores cantidad de trabajadores solicitados.
     * @return matriz con los colores de los píxeles.
     */
    public VectorR3[][] renderizar(int trabajadores) throws InterruptedException {

        /* Comprobamos la cantidad de trabajadores. */
        if (trabajadores <= 0) {
            throw new IllegalArgumentException("Debe existir al menos un trabajador.");
        }

        /* Recuperamos las dimensiones de la escena. */
        int alto = escena.getAlto();
        int ancho = escena.getAncho();

        /* Creamos la matriz compartida de píxeles. */
        VectorR3[][] pixeles = new VectorR3[alto][ancho];
 
        /* Evitamos tener más trabajadores que filas. */
        int trabajadoresEfectivos = Math.min(trabajadores, alto);

        /* Arreglos para guardar los hilos y sus posibles errores. */
        Thread[] hilos = new Thread[trabajadoresEfectivos];
        RuntimeException[] errores = new RuntimeException[trabajadoresEfectivos];

        /* Calculamos qué filas corresponden a cada trabajador. */
        for (int i = 0; i < trabajadoresEfectivos; i++) {
            /*Fila real en la que inicia un trabajador */
            int filaInicio = (int) ((long) i * alto / trabajadoresEfectivos); 
            /*
            FilaFin-1 fila real en la que termina, por que renderizarFilas lo
            mantiene en <filasFin, o sea que FilaFin realmente te da donde inicia
            el otro trabjador sig.
            */
            int filaFin = (int) ((long) (i + 1) * alto / trabajadoresEfectivos); 
            /* Guardamos el índice del trabajador actual. */
            int indiceTrabajador = i;

            /* Definimos la tarea que realizará el hilo. */
            hilos[i] = new Thread(() -> {
                try {
                    renderizador.renderizarFilas(filaInicio, filaFin, pixeles);
                } catch (RuntimeException e) {
                    errores[indiceTrabajador] = e;
                }
            });
            /* Iniciamos el hilo. */
            hilos[i].start();

        }

        /* Esperamos a que terminen todos los chambeadores. */
        for (Thread hilo : hilos) {
            hilo.join();
        }

        /* Comprobamos si algún trabajador falló. */
        for (RuntimeException error : errores) {
            if (error != null) {
                throw new IllegalStateException("Ocurrió un error durante el renderizado.",error);
            }
        }

        /* Todos terminaron correctamente. */
        return pixeles;
        }

}

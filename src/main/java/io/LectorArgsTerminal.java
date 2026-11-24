package io;

/**
 * Lee los argumentos recibidos en la terminal y válida si los datos
 * son correctos para el programa Ray Tracer.
 */
public class LectorArgsTerminal {

    /* Bandera --trabajadores. */
    private boolean trabajadores;
    
    /* Número de trabajadores. */
    private int numTrabajadores;
    /* Nombre del archivo JSON. */
    private String archivoJSON;
    /* Nombre del archivo PPM. */
    private String archivoPPM;
    
    /**
     * Construye un LectorArgsTerminal dado un arreglo de Strings.
     *
     * @param args arreglo de strings que contiene los argumentos de la
     *        terminal.
     */
    public LectorArgsTerminal(String[] args){
	/* Asignamos el número de trabajadores por default. */
	this.numTrabajadores = 1;
	/* Leemos los argumentos dados. */
	leerArgs(args);
	/* Verificamos los datos obtenidos. */
	verificarDatos();
    }

    /**
     * Devuelve el número de trabajadores leído.
     *
     * @return número de trabajadores.
     */
    public int getNumTrabajadores() {
	return this.numTrabajadores;
    }

    /**
     * Devuelve nombre del archivoJSON leído.
     *
     * @return nombre del archivoJSON.
     */
    public String getArchivoJSON() {
	return this.archivoJSON;
    }

    /**
     * Devuelve nombre del archivoPPM leído.
     *
     * @return nombre del archivoPPM.
     */
    public String getArchivoPPM() {
	return this.archivoPPM;
    }
    
    /**
     * Lee los argumentos de la terminal.
     *
     * @param args arreglo que contiene los argumentos de la terminal.
     */
    private void leerArgs(String[] args) {
	/* Si no tenemos argumentos en la terminal. */
	if (args.length == 0)
	    throw new IllegalArgumentException("Uso: java -jar raytracer.jar escena.json salida.ppm [--trabajadores N]");

	/* Intentamos leer los argumntos y validarlos. */
	try {
	    /* Recorremos los argumentos de la terminal. */
	    for(int i = 0; i < args.length ; i++){
		/* Obtenemos un argumento de la terminal. */
		String actual = args[i];
		
		/* Verificamos que no se tengan argumentos null o vacíos. */
		if(actual == null || actual.isEmpty())
		    throw new IllegalArgumentException("No puede haber argumentos null o vacios.");
		
		/* Verificamos los casos posibles para los argumentos de terminal.*/
		switch (actual) {
		    /* Bandera: --trabajadores */
	            case "--trabajadores":
			/* Verificamos la bandera trabajadores. */
			verificarTrabajadores(args, i);
			/* Avanzamos en uno la la lectura de argumentos. */
			i++;
			break;

		    /* Verificamos si es un archivo o es un argumento inválido. */
		    default:
			/* Verificamos en caso de ser un archivo JSON. */
			if (verificarEsArchivo(actual, ".json", this.archivoJSON)){
			    this.archivoJSON = actual;
			    continue;
			}
			    
			/* Verificamos en caso de ser un archivo PPM. */
			if (verificarEsArchivo(actual, ".ppm", this.archivoPPM)){
			    this.archivoPPM = actual;
			    continue;
			}
			
			/* Argumento inválido, lanzamos error en el uso del programa. */
			throw new IllegalArgumentException("Uso: java -jar raytracer.jar escena.json salida.ppm [--trabajadores N]");
		}
	    }
	    /* Verificamos que los datos sean correctos. */
	    verificarDatos();
	/* Error en la conversión del número de trabajadores. */
	} catch (NumberFormatException e) {
	    System.err.println("El número de trabajadores debe ser un entero.");
	/* Error en algún argumento en la terminal. */
	} catch (IllegalArgumentException e) {
	    /* Notificamos el problema y terminamos. */
	    System.err.println("Error: " + e.getMessage());
	    System.exit(1);
	}
    }
	
    /**
     * Verifica los archivos de entrada y salida existan.
     *
     * @throws IllegalArgumentException si no se encontraron los
     *         archivos de entrada y salida.
     */
    public void verificarDatos() {
	/* Si no tenemos un archivoJSON. */
	if (this.archivoJSON == null)
	    /* Lanzamos excepcion al no tener el JSON. */
	    throw new IllegalArgumentException("No se encontró el archivo JSON.");
	/* Si no tenemos un archivoPPM. */
	if (this.archivoPPM == null)
	    /* Lanzamos excepcion al no tener el PPM. */
	    throw new IllegalArgumentException("No se encontró el archivo PPM.");
    }
    
    /**
     * Verifica si un argumento de terminal se identifica como archivo y
     * verifica que sólo se pase un archivo del tipo a verificar.
     *
     * @param nombreArchivo argumento de terminal que podría ser un archivo.
     * @param extensionArchivo extensión esperada del archivo.
     * @param archivoAsignado variable donde se guardara el nombre del archivo.
     * @return <code>true</code> si el archivo con la extensión esperada se
     *         encotró y fue asignado;
     *         <code>false</code> en otro caso.
     * @throws IllegalArgumentException si el archivo ya le fue asignado un nombre,
     *         indicando que nos pasaron más de un archivo con la misma extensión.
     */
    private boolean verificarEsArchivo(String nombreArchivo, String extensionArchivo, String archivoAsignado) {
	/* Verificamos si tiene la extension Esperada. */
	if (nombreArchivo.endsWith(extensionArchivo)){
	    /* Verificamos que no se asigne más de un archivo. */
	    if (archivoAsignado != null)
		throw new IllegalArgumentException("Se proporcionó más de un archivo con extensión: " + extensionArchivo);
	    /* Se encontró el archivo con la extensión esperada. */
	    return true;
	}
	/* No se encontró archivo con la extensión esperada. */
	return false;
    }

    /**
     * Verifica la bandera --trabajadores con su respectivo parámetro.
     *
     * @param args lista de argumentos.
     * @param i indice actual donde se encuentra la bandera --trabajadores.
     * @throws IllegalArgumentException si la bandera esta repetida o si el
     *         parámetro de la bandera es incorecto.
     */
    private void verificarTrabajadores(String[] args, int i) {
	/* Si la bandera trabajadores ya fue leída. */
	if (this.trabajadores) 
	    /* Lanzamos error al repetirse la bandera. */
	    throw new IllegalArgumentException("La bandera trabajadores no debe repetirse.");
	
	/* Si la bandera no tiene parámetro. */
	if (i + 1 >= args.length)
	    /* Lanzamos error al repetirse la bandera. */
	    throw new IllegalArgumentException("La bandera trabajadores debe ir acompañada de un número.");

	/* Obtenemos el parámetro de la bandera trabajadores. */
	String paramTrabajadores = args[i+1];

	/* Tratamos de hacer la conversión y asignar el parámetro. */
	int numTrabajadores = Integer.parseInt(paramTrabajadores);

	/* Si el número de trabajadores en negativo. */
	if (numTrabajadores < 0)
	    /* Lanzamos excepcion al no haber cantidad negativa de trabajadores. */
	    throw new IllegalArgumentException("El número de trabajadores debe ser un número entero positivo.");
	/* Si el número de trabajadores es cero.  */
	if (numTrabajadores == 0)
	    /* Lanzamos excepcion al no poder trabajar con cero trabajadores. */
	    throw new IllegalArgumentException("El número de trabajadores debe ser mayor a cero.");

	/* Asignamos el número de trabajadores. */
	this.numTrabajadores = numTrabajadores;
	
	/* Indicamos que ya leímos la bandera --trabajadores. */
	this.trabajadores = true;
    }
}

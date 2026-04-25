package fase2edd.util;

import java.nio.file.Paths;

public class Rutas {
    // Devuelve la ruta completa hacia un archivo dentro de la carpeta "archivos" del proyecto
    public static String obtenerRutaArchivo(String nombreArchivo) {
        String dirProyecto = System.getProperty("user.dir");
        return Paths.get(dirProyecto, "archivos", nombreArchivo).toString();
    }

    // Devuelve la ruta base del proyecto
    public static String obtenerRutaBase() {
        return System.getProperty("user.dir");
    }
}
package fase2edd.util;

import java.nio.file.Paths;

public class Rutas {
    
    public static String obtenerRutaArchivo(String nombreArchivo) {
        String dirProyecto = System.getProperty("user.dir");
        return Paths.get(dirProyecto, "archivos", nombreArchivo).toString();
    }

   
    public static String obtenerRutaBase() {
        return System.getProperty("user.dir");
    }
}
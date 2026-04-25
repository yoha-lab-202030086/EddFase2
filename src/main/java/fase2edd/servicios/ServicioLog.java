package fase2edd.servicios;


public class ServicioLog {
    private String rutaLog;
    private java.io.FileWriter fw;
    private boolean abierto;

    public ServicioLog(String nombreArchivo) {
        this.rutaLog = nombreArchivo;
        this.abierto = false;
    }

    public void abrir() {
        try {
            fw = new java.io.FileWriter(rutaLog, false);
            fw.write("INICIO DE LOG - Errores y eventos\n");
            fw.write("===================================\n");
            abierto = true;
        } catch (Exception e) {
            System.err.println("No se pudo abrir el archivo de log: " + e.getMessage());
        }
    }

    public void cerrar() {
        if (abierto) {
            try {
                fw.write("FIN DE LOG\n");
                fw.close();
            } catch (Exception e) {
                System.err.println("Error al cerrar log: " + e.getMessage());
            }
            abierto = false;
        }
    }

    public void logError(String mensaje) {
        log("ERROR", mensaje);
    }

    public void logEvento(String mensaje) {
        log("INFO", mensaje);
    }

    public void logDuplicado(String codigoBarra, String nombre) {
        log("DUPLICADO", "Código duplicado: " + codigoBarra + " - " + nombre);
    }

    public void logProductoMalFormado(String linea) {
        log("MAL_FORMADO", "Línea omitida: " + linea);
    }

    public void log(String tipo, String mensaje) {
        if (!abierto) return;
        try {
            fw.write("[" + tipo + "] " + mensaje + "\n");
            fw.flush();
        } catch (Exception e) {
            System.err.println("Error escribiendo en log: " + e.getMessage());
        }
    }
}
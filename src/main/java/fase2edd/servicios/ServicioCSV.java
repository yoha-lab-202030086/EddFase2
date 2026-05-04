package fase2edd.servicios;

import fase2edd.control.ControladorGlobal;
import fase2edd.model.Conexion;
import fase2edd.model.Producto;
import fase2edd.model.ResultadoOperacion;
import fase2edd.util.Validaciones;

public class ServicioCSV {

    private ServicioLog logger;
    private ControladorGlobal controlador;

    public ServicioCSV(ControladorGlobal controlador, ServicioLog logger) {
        this.controlador = controlador;
        this.logger = logger;
    }

    /**
     * Formato: SucursalID,Nombre, CodigoBarra, Categoria, FechaCaducidad,
     * Marca, Precio, Stock
     */
    public int cargarProductos(String rutaArchivo) {

    logger.logEvento("Cargando productos desde: " + rutaArchivo);

    int cargados = 0;

    boolean dentroComentarioBloque = false;

    try (java.io.BufferedReader br = new java.io.BufferedReader(
            new java.io.FileReader(rutaArchivo))) {

        String linea;

        int numLinea = 0;

        while ((linea = br.readLine()) != null) {

            numLinea++;

            linea = linea.trim();

            // Ignorar vacías
            if (linea.isEmpty()) {
                continue;
            }

            // Omitir cabecera
            if (numLinea == 1) {
                continue;
            }

            // Comentarios tipo bloque /*
            if (linea.startsWith("/*")) {
                dentroComentarioBloque = true;
                continue;
            }

            if (dentroComentarioBloque) {

                if (linea.endsWith("*/")) {
                    dentroComentarioBloque = false;
                }

                continue;
            }

            // Comentarios simples --
            if (linea.startsWith("--")) {
                continue;
            }

            try {

                // Parser CSV ROBUSTO
                java.util.List<String> partes = parseCSVLine(linea);

                if (partes.size() != 8) {

                    logger.logProductoMalFormado(
                            "Línea "
                            + numLinea
                            + ": cantidad inválida de columnas -> "
                            + linea);

                    continue;
                }

                // Campos
                String idSucursalStr = partes.get(0);
                String nombre = partes.get(1);
                String codigoBarra = partes.get(2);
                String categoria = partes.get(3);
                String fechaCaducidad = partes.get(4);
                String marca = partes.get(5);
                String precioStr = partes.get(6);
                String stockStr = partes.get(7);

                // =========================
                // PARSEO SEGURO
                // =========================

                int idSucursal;

                try {

                    idSucursal = Integer.parseInt(idSucursalStr);

                } catch (Exception e) {

                    logger.logError(
                            "ID sucursal inválido línea "
                            + numLinea
                            + ": "
                            + idSucursalStr);

                    continue;
                }

                double precio;

                try {

                    precio = Double.parseDouble(precioStr);

                } catch (Exception e) {

                    logger.logError(
                            "Precio inválido línea "
                            + numLinea
                            + ": "
                            + precioStr);

                    continue;
                }

                int stock;

                try {

                    stock = Integer.parseInt(stockStr);

                } catch (Exception e) {

                    logger.logError(
                            "Stock inválido línea "
                            + numLinea
                            + ": "
                            + stockStr);

                    continue;
                }

                // =========================
                // VALIDACIONES
                // =========================

                if (!Validaciones.esIdSucursalValido(idSucursal)) {

                    logger.logError(
                            "ID sucursal no válido línea "
                            + numLinea);

                    continue;
                }

                if (!Validaciones.esStringValido(nombre)) {

                    logger.logError(
                            "Nombre inválido línea "
                            + numLinea);

                    continue;
                }

                if (!Validaciones.esCodigoBarraValido(codigoBarra)) {

                    logger.logError(
                            "Código de barras inválido línea "
                            + numLinea);

                    continue;
                }

                if (!Validaciones.esStringValido(categoria)) {

                    logger.logError(
                            "Categoría inválida línea "
                            + numLinea);

                    continue;
                }

                if (!Validaciones.esFechaValida(fechaCaducidad)) {

                    logger.logError(
                            "Fecha inválida línea "
                            + numLinea
                            + ": "
                            + fechaCaducidad);

                    continue;
                }

                if (!Validaciones.esStringValido(marca)) {

                    logger.logError(
                            "Marca inválida línea "
                            + numLinea);

                    continue;
                }

                if (!Validaciones.esPrecioValido(precio)) {

                    logger.logError(
                            "Precio inválido línea "
                            + numLinea);

                    continue;
                }

                if (!Validaciones.esStockValido(stock)) {

                    logger.logError(
                            "Stock inválido línea "
                            + numLinea);

                    continue;
                }

                // =========================
                // CREAR PRODUCTO
                // =========================

                Producto p = new Producto(
                        nombre,
                        codigoBarra,
                        categoria,
                        fechaCaducidad,
                        marca,
                        precio,
                        stock
                );

                controlador.setSucursalActiva(idSucursal);

                ResultadoOperacion res = controlador.agregarProducto(p);

                if (res.isExitoso()) {

                    cargados++;

                } else {

                    logger.logError(
                            "No se pudo insertar producto línea "
                            + numLinea
                            + ": "
                            + res.getMensaje());
                }

            } catch (Exception e) {

                logger.logError(
                        "Error inesperado línea "
                        + numLinea
                        + ": "
                        + e.getMessage());
            }
        }

    } catch (Exception e) {

        logger.logError(
                "Error general leyendo productos: "
                + e.getMessage());
    }

    logger.logEvento("Productos cargados correctamente: " + cargados);

    return cargados;
}

  /**
 * Formato:
 * ID, Nombre, Ubicación, t_ingreso, t_traspaso, t_despacho
 */
public int cargarSucursales(String rutaArchivo) {

    logger.logEvento("Cargando sucursales desde: " + rutaArchivo);

    int cargadas = 0;

    boolean dentroComentarioBloque = false;

    try (java.io.BufferedReader br = new java.io.BufferedReader(
            new java.io.FileReader(rutaArchivo))) {

        String linea;
        int numLinea = 0;

        while ((linea = br.readLine()) != null) {

            numLinea++;

            linea = linea.trim();

            // Ignorar vacías
            if (linea.isEmpty()) {
                continue;
            }

            // Ignorar cabecera
            if (numLinea == 1) {
                continue;
            }

            // Comentario tipo bloque /*
            if (linea.startsWith("/*")) {
                dentroComentarioBloque = true;
                continue;
            }

            if (dentroComentarioBloque) {

                if (linea.endsWith("*/")) {
                    dentroComentarioBloque = false;
                }

                continue;
            }

            // Comentarios simples
            if (linea.startsWith("--")) {
                continue;
            }

            try {

                java.util.List<String> partes = parseCSVLine(linea);

                if (partes.size() != 6) {

                    logger.logError(
                            "Sucursal mal formada línea "
                            + numLinea
                            + ": cantidad inválida de columnas -> "
                            + linea);

                    continue;
                }

                String idStr = partes.get(0);
                String nombre = partes.get(1);
                String ubicacion = partes.get(2);
                String ingresoStr = partes.get(3);
                String traspasoStr = partes.get(4);
                String despachoStr = partes.get(5);

                int id;
                double tIngreso;
                double tTraspaso;
                double tDespacho;

                try {
                    id = Integer.parseInt(idStr);
                } catch (Exception e) {

                    logger.logError(
                            "ID inválido en línea "
                            + numLinea
                            + ": "
                            + idStr);

                    continue;
                }

                try {
                    tIngreso = Double.parseDouble(ingresoStr);
                } catch (Exception e) {

                    logger.logError(
                            "t_ingreso inválido en línea "
                            + numLinea
                            + ": "
                            + ingresoStr);

                    continue;
                }

                try {
                    tTraspaso = Double.parseDouble(traspasoStr);
                } catch (Exception e) {

                    logger.logError(
                            "t_traspaso inválido en línea "
                            + numLinea
                            + ": "
                            + traspasoStr);

                    continue;
                }

                try {
                    tDespacho = Double.parseDouble(despachoStr);
                } catch (Exception e) {

                    logger.logError(
                            "t_despacho inválido en línea "
                            + numLinea
                            + ": "
                            + despachoStr);

                    continue;
                }

                // Validaciones
                if (!Validaciones.esIdSucursalValido(id)) {

                    logger.logError(
                            "ID de sucursal inválido línea "
                            + numLinea);

                    continue;
                }

                if (!Validaciones.esStringValido(nombre)) {

                    logger.logError(
                            "Nombre inválido línea "
                            + numLinea);

                    continue;
                }

                if (!Validaciones.esStringValido(ubicacion)) {

                    logger.logError(
                            "Ubicación inválida línea "
                            + numLinea);

                    continue;
                }

                if (tIngreso < 0 || tTraspaso < 0 || tDespacho < 0) {

                    logger.logError(
                            "Tiempos negativos línea "
                            + numLinea);

                    continue;
                }

                boolean ok = controlador.crearSucursal(
                        id,
                        nombre,
                        ubicacion,
                        tIngreso,
                        tTraspaso,
                        tDespacho
                );

                if (ok) {

                    cargadas++;

                } else {

                    logger.logError(
                            "No se pudo crear sucursal línea "
                            + numLinea
                            + " (posible ID duplicado)");
                }

            } catch (Exception e) {

                logger.logError(
                        "Error inesperado línea "
                        + numLinea
                        + ": "
                        + e.getMessage());
            }
        }

    } catch (Exception e) {

        logger.logError(
                "Error general leyendo sucursales: "
                + e.getMessage());
    }

    logger.logEvento("Sucursales cargadas correctamente: " + cargadas);

    return cargadas;
}

   public int cargarConexiones(String rutaArchivo) {

    logger.logEvento("Cargando conexiones desde: " + rutaArchivo);

    int cargadas = 0;

    boolean dentroComentarioBloque = false;

    try (java.io.BufferedReader br = new java.io.BufferedReader(
            new java.io.FileReader(rutaArchivo))) {

        String linea;

        int numLinea = 0;

        while ((linea = br.readLine()) != null) {

            numLinea++;

            linea = linea.trim();

            // Vacías
            if (linea.isEmpty()) {
                continue;
            }

            // Cabecera
            if (numLinea == 1) {
                continue;
            }

            // Comentario bloque
            if (linea.startsWith("/*")) {
                dentroComentarioBloque = true;
                continue;
            }

            if (dentroComentarioBloque) {

                if (linea.endsWith("*/")) {
                    dentroComentarioBloque = false;
                }

                continue;
            }

            // Comentarios simples
            if (linea.startsWith("--")) {
                continue;
            }

            try {

                java.util.List<String> partes = parseCSVLine(linea);

                if (partes.size() != 4) {

                    logger.logError(
                            "Conexión mal formada línea "
                            + numLinea
                            + ": columnas inválidas -> "
                            + linea);

                    continue;
                }

                String origenStr = partes.get(0);
                String destinoStr = partes.get(1);
                String tiempoStr = partes.get(2);
                String costoStr = partes.get(3);

                int origen;
                int destino;

                double tiempo;
                double costo;

                try {
                    origen = Integer.parseInt(origenStr);
                } catch (Exception e) {

                    logger.logError(
                            "Origen inválido línea "
                            + numLinea
                            + ": "
                            + origenStr);

                    continue;
                }

                try {
                    destino = Integer.parseInt(destinoStr);
                } catch (Exception e) {

                    logger.logError(
                            "Destino inválido línea "
                            + numLinea
                            + ": "
                            + destinoStr);

                    continue;
                }

                try {
                    tiempo = Double.parseDouble(tiempoStr);
                } catch (Exception e) {

                    logger.logError(
                            "Tiempo inválido línea "
                            + numLinea
                            + ": "
                            + tiempoStr);

                    continue;
                }

                try {
                    costo = Double.parseDouble(costoStr);
                } catch (Exception e) {

                    logger.logError(
                            "Costo inválido línea "
                            + numLinea
                            + ": "
                            + costoStr);

                    continue;
                }

                // Validaciones
                if (origen <= 0 || destino <= 0) {

                    logger.logError(
                            "IDs inválidos línea "
                            + numLinea);

                    continue;
                }

                if (origen == destino) {

                    logger.logError(
                            "No se permite conexión consigo misma línea "
                            + numLinea);

                    continue;
                }

                if (tiempo < 0 || costo < 0) {

                    logger.logError(
                            "Tiempo/costo negativos línea "
                            + numLinea);

                    continue;
                }

                Conexion conexion = new Conexion(
                        origen,
                        destino,
                        tiempo,
                        costo,
                        true
                );

                boolean ok = controlador.agregarConexion(conexion);

                if (ok) {

                    cargadas++;

                } else {

                    logger.logError(
                            "No se pudo agregar conexión línea "
                            + numLinea);
                }

            } catch (Exception e) {

                logger.logError(
                        "Error inesperado línea "
                        + numLinea
                        + ": "
                        + e.getMessage());
            }
        }

    } catch (Exception e) {

        logger.logError(
                "Error general leyendo conexiones: "
                + e.getMessage());
    }

    logger.logEvento("Conexiones cargadas correctamente: " + cargadas);

    return cargadas;
}
   
   
    //Metodo auxiliar para parsear bien el csv
    private java.util.List<String> parseCSVLine(String linea) {

        java.util.List<String> campos = new java.util.ArrayList<>();

        if (linea == null || linea.trim().isEmpty()) {
            return campos;
        }

        StringBuilder actual = new StringBuilder();

        boolean dentroComillas = false;

        for (int i = 0; i < linea.length(); i++) {

            char c = linea.charAt(i);

            // Manejo de comillas
            if (c == '"') {

                // Comillas escapadas ""
                if (dentroComillas
                        && i + 1 < linea.length()
                        && linea.charAt(i + 1) == '"') {

                    actual.append('"');
                    i++;

                } else {
                    dentroComillas = !dentroComillas;
                }

            } // Separador CSV real
            else if (c == ',' && !dentroComillas) {

                campos.add(actual.toString().trim());
                actual.setLength(0);

            } else {
                actual.append(c);
            }
        }

        campos.add(actual.toString().trim());

        return campos;
    }
}

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
     * Formato: SucursalID,Nombre, CodigoBarra, Categoria, FechaCaducidad, Marca, Precio, Stock
     * 
     */
    public int cargarProductos(String rutaArchivo) {
        logger.logEvento("Cargando productos desde: " + rutaArchivo);
        int cargados = 0;
        java.io.BufferedReader br = null;
        try {
            br = new java.io.BufferedReader(new java.io.FileReader(rutaArchivo));
            String linea;
            int numLinea = 0;
            while ((linea = br.readLine()) != null) {
                numLinea++;

                // Omitir la cabecera
                if (numLinea == 1) {
                    continue;
                }

                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length < 8) {
                    logger.logProductoMalFormado("Línea " + numLinea + ": " + linea);
                    continue;
                }
                // Parsear campos
                String idSucursalStr = partes[0].trim();
                String nombre = partes[1].trim();
                String codigoBarra = partes[2].trim();
                String categoria = partes[3].trim();
                String fechaCaducidad = partes[4].trim();
                String marca = partes[5].trim();
                String precioStr = partes[6].trim();
                String stockStr = partes[7].trim();

               
                if (!Validaciones.esIdSucursalValido(Integer.parseInt(idSucursalStr))) {
                    logger.logError("ID de sucursal inválido en línea " + numLinea + ": " + idSucursalStr);
                    continue;
                }
                if (!Validaciones.esStringValido(nombre) || !Validaciones.esCodigoBarraValido(codigoBarra)
                        || !Validaciones.esStringValido(categoria) || !Validaciones.esFechaValida(fechaCaducidad)
                        || !Validaciones.esStringValido(marca)) {
                    logger.logProductoMalFormado("Línea " + numLinea + ": " + linea + " - campos inválidos");
                    continue;
                }

                double precio;
                int stock;
                try {
                    precio = Double.parseDouble(precioStr);
                    stock = Integer.parseInt(stockStr);
                } catch (NumberFormatException e) {
                    logger.logProductoMalFormado("Línea " + numLinea + ": precio o stock no numérico");
                    continue;
                }
                if (!Validaciones.esPrecioValido(precio) || !Validaciones.esStockValido(stock)) {
                    logger.logProductoMalFormado("Línea " + numLinea + ": precio o stock negativos");
                    continue;
                }

                // Crear producto
                Producto p = new Producto(nombre, codigoBarra, categoria, fechaCaducidad, marca, precio, stock);
                int idSuc = Integer.parseInt(idSucursalStr);
                // Establecer la sucursal activa y agregar producto
                controlador.setSucursalActiva(idSuc);
                ResultadoOperacion res = controlador.agregarProducto(p);
                if (res.isExitoso()) {
                    cargados++;
                } else {
                    logger.logError("No se pudo insertar producto en línea " + numLinea + ": " + res.getMensaje());
                }
            }
        } catch (Exception e) {
            logger.logError("Error al leer archivo de productos: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ex) {
                }
            }
        }
        logger.logEvento("Productos cargados: " + cargados);
        return cargados;
    }

    /**
     * Formato: ID, Nombre, Ubicación, t_ingreso,t_traspaso, t_despacho
     * 
     */
    public int cargarSucursales(String rutaArchivo) {
        logger.logEvento("Cargando sucursales desde: " + rutaArchivo);
        int cargadas = 0;
        java.io.BufferedReader br = null;
        try {
            br = new java.io.BufferedReader(new java.io.FileReader(rutaArchivo));
            String linea;
            int numLinea = 0;
            while ((linea = br.readLine()) != null) {
                numLinea++;

                // Salta la primera línea (la cabecera)
                if (numLinea == 1) {
                    continue;
                }

                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length < 6) {
                    logger.logProductoMalFormado("Línea " + numLinea + " sucursal: " + linea);
                    continue;
                }
                int id = Integer.parseInt(partes[0].trim());
                String nombre = partes[1].trim();
                String ubicacion = partes[2].trim();
                double tIngreso = Double.parseDouble(partes[3].trim());
                double tTraspaso = Double.parseDouble(partes[4].trim());
                double tDespacho = Double.parseDouble(partes[5].trim());

                if (!Validaciones.esIdSucursalValido(id) || !Validaciones.esStringValido(nombre)
                        || !Validaciones.esStringValido(ubicacion)) {
                    logger.logError("Sucursal mal formada en línea " + numLinea);
                    continue;
                }
                boolean ok = controlador.crearSucursal(id, nombre, ubicacion, tIngreso, tTraspaso, tDespacho);
                if (ok) {
                    cargadas++;
                } else {
                    logger.logError("No se pudo crear sucursal en línea " + numLinea + ", posible ID duplicado");
                }
            }
        } catch (Exception e) {
            logger.logError("Error al leer archivo de sucursales: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ex) {
                }
            }
        }
        logger.logEvento("Sucursales cargadas: " + cargadas);
        return cargadas;
    }

    
    public int cargarConexiones(String rutaArchivo) {
        logger.logEvento("Cargando conexiones desde: " + rutaArchivo);
        int cargadas = 0;
        java.io.BufferedReader br = null;
        try {
            br = new java.io.BufferedReader(new java.io.FileReader(rutaArchivo));
            String linea;
            int numLinea = 0;
            while ((linea = br.readLine()) != null) {
                numLinea++;

                // Omitir la cabecera
                if (numLinea == 1) {
                    continue;
                }

                if (linea.trim().isEmpty()) {
                    continue;
                }
                String[] partes = linea.split(",");
                if (partes.length < 4) {
                    logger.logProductoMalFormado("Línea " + numLinea + " conexión: " + linea);
                    continue;
                }
                int origen = Integer.parseInt(partes[0].trim());
                int destino = Integer.parseInt(partes[1].trim());
                double tiempo = Double.parseDouble(partes[2].trim());
                double costo = Double.parseDouble(partes[3].trim());

                // Asumimos bidireccional por defecto? Según enunciado puede ser bidireccional o unidireccional.
                // Aquí lo pondremos bidireccional por simplicidad.
                Conexion conexion = new Conexion(origen, destino, tiempo, costo, true);
                boolean ok = controlador.agregarConexion(conexion);
                if (ok) {
                    cargadas++;
                } else {
                    logger.logError("Error al agregar conexión en línea " + numLinea);
                }
            }
        } catch (Exception e) {
            logger.logError("Error al leer archivo de conexiones: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ex) {
                }
            }
        }
        logger.logEvento("Conexiones cargadas: " + cargadas);
        return cargadas;
    }
}

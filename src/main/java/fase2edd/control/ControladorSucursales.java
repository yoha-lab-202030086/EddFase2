package fase2edd.control;

import fase2edd.estructuras.grafo.Grafo;
import fase2edd.model.Conexion;
import fase2edd.model.Sucursal;

public class ControladorSucursales {

    private Sucursal[] sucursales;
    private int cantidad;
    private int capacidad;
    private Grafo grafo;

    public ControladorSucursales() {
        capacidad = 8;
        sucursales = new Sucursal[capacidad];
        cantidad = 0;
        grafo = new Grafo();
    }

    public boolean crearSucursal(int id, String nombre, String ubicacion,
            double tIngreso, double tTraspaso, double intervalo) {
        if (buscarPorId(id) != null) {
            return false;
        }
        if (cantidad == capacidad) {
            capacidad *= 2;
            Sucursal[] nuevo = new Sucursal[capacidad];
            for (int i = 0; i < cantidad; i++) {
                nuevo[i] = sucursales[i];
            }
            sucursales = nuevo;
        }
        Sucursal s = new Sucursal(id, nombre, ubicacion, tIngreso, tTraspaso, intervalo);
        sucursales[cantidad++] = s;
        grafo.agregarNodo(id);
        return true;
    }

    public boolean modificarSucursal(int id, String nombre, String ubicacion,
            double tIngreso, double tTraspaso, double tDespacho) {
        Sucursal s = buscarPorId(id);
        if (s == null) {
            return false;
        }
        s.setNombre(nombre);
        s.setUbicacion(ubicacion);
        s.setTiempoIngreso(tIngreso);
        s.setTiempoTraspaso(tTraspaso);
        s.setIntervaloDespacho(tDespacho);
        return true;
    }

    public boolean eliminarSucursal(int id) {
        for (int i = 0; i < cantidad; i++) {
            if (sucursales[i].getId() == id) {
                // Mover las restantes una posición hacia atrás
                for (int j = i; j < cantidad - 1; j++) {
                    sucursales[j] = sucursales[j + 1];
                }
                sucursales[cantidad - 1] = null;
                cantidad--;
                return true;
            }
        }
        return false;
    }

    public boolean agregarConexion(Conexion c) {
        Sucursal origen = buscarPorId(c.getIdOrigen());
        Sucursal destino = buscarPorId(c.getIdDestino());
        if (origen == null || destino == null) {
            return false;
        }

        grafo.agregarArista(c.getIdOrigen(), c.getIdDestino(), c.getTiempo(), c.getCosto());
        if (c.isBidireccional()) {
            grafo.agregarArista(c.getIdDestino(), c.getIdOrigen(), c.getTiempo(), c.getCosto());
        }
        return true;
    }

    public Sucursal buscarPorId(int id) {
        for (int i = 0; i < cantidad; i++) {
            if (sucursales[i].getId() == id) {
                return sucursales[i];
            }
        }
        return null;
    }

    public Sucursal[] getSucursales() {
        Sucursal[] resultado = new Sucursal[cantidad];
        for (int i = 0; i < cantidad; i++) {
            resultado[i] = sucursales[i];
        }
        return resultado;
    }

    public Grafo getGrafo() {
        return grafo;
    }
}

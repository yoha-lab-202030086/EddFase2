package fase2edd.control;

import fase2edd.estructuras.listas.ListaEnlazada;
import fase2edd.inventario.Inventario;
import fase2edd.model.Producto;
import fase2edd.model.ResultadoOperacion;



public class ControladorInventario {
    private Inventario inventario;

    public ControladorInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public ResultadoOperacion agregarProducto(Producto p) {
        return inventario.insertarProducto(p);
    }

    public ResultadoOperacion eliminarProducto(String codigoBarra) {
        return inventario.eliminarProducto(codigoBarra);
    }

    public Producto buscarPorCodigo(String codigo) {
        return inventario.buscarPorCodigo(codigo);
    }

    public Producto buscarPorNombre(String nombre) {
        return inventario.buscarPorNombre(nombre);
    }

    public ListaEnlazada buscarPorCategoria(String categoria) {
        return inventario.buscarPorCategoria(categoria);
    }

    public ListaEnlazada buscarPorRangoFechas(String inicio, String fin) {
        return inventario.buscarPorRangoFechas(inicio, fin);
    }

    public Producto[] listarOrdenadoPorNombre() {
        return inventario.listarPorNombre();
    }

    public Producto deshacerUltimo() {
        return inventario.deshacerUltimaInsercion();
    }

    public Inventario getInventario() {
        return inventario;
    }
}
package fase2edd.control;

// Punto central que une todos los controladores
import fase2edd.estructuras.listas.ListaEnlazada;
import fase2edd.inventario.Inventario;
import fase2edd.model.Conexion;
import fase2edd.model.Producto;
import fase2edd.model.ResultadoOperacion;

public class ControladorGlobal {

    private ControladorInventario inventarioCtrl;
    private ControladorSucursales sucursalesCtrl;
    private ControladorTransferencias transferenciasCtrl;

    public ControladorGlobal() {
        sucursalesCtrl = new ControladorSucursales();
        transferenciasCtrl = new ControladorTransferencias(sucursalesCtrl);
        // inicia y conecta sscursales y transferencias
        inventarioCtrl = null; // se asignará cuando se seleccione una sucursal
    }

    public Inventario getInventarioActivo() {
        if (inventarioCtrl == null) {
            return null;
        }
        return inventarioCtrl.getInventario();
    }

    public boolean prepararTransferencia(Producto p, int idOrigen, int idDestino, int criterio) {
        return transferenciasCtrl.prepararTransferencia(p, idOrigen, idDestino, criterio);
    }

    public boolean modificarSucursal(int id, String nombre, String ubicacion,
            double tIngreso, double tTraspaso, double intervalo) {
        return sucursalesCtrl.modificarSucursal(id, nombre, ubicacion, tIngreso, tTraspaso, intervalo);
    }

    public boolean eliminarSucursal(int id) {
        return sucursalesCtrl.eliminarSucursal(id);
    }

    // Asignar el controlador de inventario de la sucursal activa
    public void setSucursalActiva(int idSucursal) {
        var s = sucursalesCtrl.buscarPorId(idSucursal);
        if (s != null) {
            inventarioCtrl = new ControladorInventario(s.getInventario());
        }
    }

    public ResultadoOperacion agregarProducto(Producto p) {
        if (inventarioCtrl == null) {
            return new ResultadoOperacion(false, "No hay sucursal activa");
        }
        return inventarioCtrl.agregarProducto(p);
    }

    public ResultadoOperacion eliminarProducto(String codigo) {
        if (inventarioCtrl == null) {
            return new ResultadoOperacion(false, "No hay sucursal activa");
        }
        return inventarioCtrl.eliminarProducto(codigo);
    }

    public Producto buscarPorNombre(String nombre) {
        if (inventarioCtrl == null) {
            return null;
        }
        return inventarioCtrl.buscarPorNombre(nombre);
    }

    public Producto buscarPorCodigo(String codigo) {
        if (inventarioCtrl == null) {
            return null;
        }
        return inventarioCtrl.buscarPorCodigo(codigo);
    }

    public ListaEnlazada buscarPorCategoria(String cat) {
        if (inventarioCtrl == null) {
            return null;
        }
        return inventarioCtrl.buscarPorCategoria(cat);
    }

    public ListaEnlazada buscarPorRangoFechas(String ini, String fin) {
        if (inventarioCtrl == null) {
            return null;
        }
        return inventarioCtrl.buscarPorRangoFechas(ini, fin);
    }

    public Producto[] listarPorNombre() {
        if (inventarioCtrl == null) {
            return new Producto[0];
        }
        return inventarioCtrl.listarOrdenadoPorNombre();
    }

    public Producto deshacerUltimo() {
        if (inventarioCtrl == null) {
            return null;
        }
        return inventarioCtrl.deshacerUltimo();
    }

    public boolean crearSucursal(int id, String nombre, String ubicacion,
            double tIng, double tTras, double interv) {
        return sucursalesCtrl.crearSucursal(id, nombre, ubicacion, tIng, tTras, interv);
    }

    public boolean agregarConexion(Conexion c) {
        return sucursalesCtrl.agregarConexion(c);
    }
    //
    //    public int[] transferirProducto(Producto p, int idOrigen, int idDestino, int criterio) {
    //        return transferenciasCtrl.transferirProducto(p, idOrigen, idDestino, criterio);
    //    }

    public ControladorSucursales getCtrlSucursales() {
        return sucursalesCtrl;
    }

    public ControladorTransferencias getCtrlTransferencias() {
        return transferenciasCtrl;
    }

    public boolean iniciarTransferenciaAutomatica(Producto p, int idOrigen, int idDestino, int criterio) {
        return transferenciasCtrl.iniciarTransferenciaAutomatica(p, idOrigen, idDestino, criterio);
    }

    public void detenerSimulacion() {
        transferenciasCtrl.detenerSimulacion();
    }
}

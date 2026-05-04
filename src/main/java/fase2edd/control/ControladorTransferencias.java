package fase2edd.control;

import fase2edd.estructuras.grafo.Grafo;
import fase2edd.model.Producto;
import fase2edd.model.Sucursal;
import fase2edd.simulacion.EstadoProducto;
import fase2edd.simulacion.SimuladorDespacho;

public class ControladorTransferencias {

    private ControladorSucursales ctrlSucursales;
    private SimuladorDespacho simulador;

    public ControladorTransferencias(ControladorSucursales ctrlSucursales) {
        this.ctrlSucursales = ctrlSucursales;
        this.simulador = new SimuladorDespacho();
    }

    // Transfiere un producto desde origen a destino usando el criterio (0 = tiempo, 1 = costo)
    // Devuelve la ruta como arreglo de ids de sucursales o null si no es posible
//    public int[] transferirProducto(Producto p, int idOrigen, int idDestino, int criterio) {
//        Sucursal origen = ctrlSucursales.buscarPorId(idOrigen);
//        Sucursal destino = ctrlSucursales.buscarPorId(idDestino);
//        if (origen == null || destino == null) return null;
//
//        Grafo grafo = ctrlSucursales.getGrafo();
//        int[] ruta = grafo.rutaMasCorta(idOrigen, idDestino, criterio);
//        if (ruta == null || ruta.length == 0) return null;
//
//        // Cambiar estado del producto
//        p.setEstado(EstadoProducto.EN_TRANSITO);
//
//        // Colocar en la cola de ingreso de la primera sucursal
//        origen.getColaIngreso().encolar(p);
//
//        // Iniciar simulación de despacho a través de la ruta
//        simulador.simularRuta(ruta, ctrlSucursales, p);
//
//        return ruta;
//    }
    public boolean prepararTransferencia(Producto p, int idOrigen, int idDestino, int criterio) {
        Grafo grafo = ctrlSucursales.getGrafo();
        int[] ruta = grafo.rutaMasCorta(idOrigen, idDestino, criterio);
        if (ruta == null || ruta.length == 0) {
            return false;
        }

        p.setEstado(EstadoProducto.EN_TRANSITO);
        simulador.prepararTransferencia(p, ruta, ctrlSucursales);
        return true;
    }

    public boolean procesarSiguientePaso() {
        return simulador.procesarUnPaso(ctrlSucursales);
    }

    public SimuladorDespacho getSimulador() {
        return simulador;
    }

}

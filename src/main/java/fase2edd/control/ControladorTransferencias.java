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

    // Inicia transferencia automática con hilo
public boolean iniciarTransferenciaAutomatica(Producto p, int idOrigen, int idDestino, int criterio) {
    Grafo grafo = ctrlSucursales.getGrafo();
    int[] ruta = grafo.rutaMasCorta(idOrigen, idDestino, criterio);
    if (ruta == null || ruta.length == 0) return false;
    p.setEstado(EstadoProducto.EN_TRANSITO);
    simulador.iniciarTransferencia(p, ruta, ctrlSucursales);
    return true;
}

// Prepara transferencia manual (sin hilo)
public boolean prepararTransferencia(Producto p, int idOrigen, int idDestino, int criterio) {
    Grafo grafo = ctrlSucursales.getGrafo();
    int[] ruta = grafo.rutaMasCorta(idOrigen, idDestino, criterio);
    if (ruta == null || ruta.length == 0) return false;
    p.setEstado(EstadoProducto.EN_TRANSITO);
    // Colocar directamente en ingreso del origen
    Sucursal origen = ctrlSucursales.buscarPorId(ruta[0]);
    if (origen != null) {
        origen.getColaIngreso().encolar(p);
    }
    // Configurar simulador para modo manual
    simulador.prepararTransferencia(p, ruta, ctrlSucursales);
    return true;
}

   public boolean procesarSiguientePaso() {
    return simulador.procesarUnPaso(ctrlSucursales);
}

public void detenerSimulacion() {
    simulador.detener();
}

public SimuladorDespacho getSimulador() {
    return simulador;
}

}

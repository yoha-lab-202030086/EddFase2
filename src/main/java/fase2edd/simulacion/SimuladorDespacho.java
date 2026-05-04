package fase2edd.simulacion;

import fase2edd.control.ControladorSucursales;
import fase2edd.model.Producto;
import fase2edd.model.Sucursal;
import fase2edd.model.Producto;
import fase2edd.model.Sucursal;


public class SimuladorDespacho {
    // Estado de la simulación actual
    private int[] rutaActual;
    private int indiceActual;      // índice en la ruta (sucursal actual)
    private Producto productoActual;
    private String ultimoMensaje;
    private boolean finalizada;

    public SimuladorDespacho() {
        finalizada = true;
        ultimoMensaje = "";
    }

    /**
     * Prepara una nueva transferencia paso a paso.
     * Coloca el producto en la cola de ingreso de la primera sucursal.
     */
    public void prepararTransferencia(Producto p, int[] ruta, ControladorSucursales ctrl) {
        this.rutaActual = ruta;
        this.indiceActual = 0;
        this.productoActual = p;
        this.finalizada = false;

        // Colocar en cola de ingreso de la primera sucursal
        Sucursal origen = ctrl.buscarPorId(ruta[0]);
        if (origen != null) {
            origen.getColaIngreso().encolar(p);
            productoActual.setEstado(EstadoProducto.EN_TRANSITO);
            ultimoMensaje = "Producto " + p.getNombre() + " encolado en ingreso de " + origen.getNombre();
        }
    }

    /**
     * Procesa un solo paso de la simulación.
     * @param ctrl Controlador de sucursales para obtener los objetos Sucursal.
     * @return true si la simulación continúa, false si ya terminó.
     */
    public boolean procesarUnPaso(ControladorSucursales ctrl) {
        if (finalizada || rutaActual == null) {
            ultimoMensaje = "No hay transferencia en curso.";
            return false;
        }

        Sucursal actual = ctrl.buscarPorId(rutaActual[indiceActual]);

        // Caso 1: Estamos en la última sucursal (destino final)
        if (indiceActual == rutaActual.length - 1) {
            // Sacar de cola de ingreso (si está allí) y finalizar
            if (!actual.getColaIngreso().estaVacia()) {
                Producto p = actual.getColaIngreso().desencolar();
                p.setEstado(EstadoProducto.DISPONIBLE);
                actual.getInventario().insertarProducto(p);
                ultimoMensaje = "Producto " + p.getNombre() + " recibido en destino final " + actual.getNombre();
            }
            finalizada = true;
            return false;
        }

        // Sucursal intermedia
        Sucursal siguiente = ctrl.buscarPorId(rutaActual[indiceActual + 1]);

        // 1. Si hay producto en ingreso, moverlo a preparación de traspaso
        if (!actual.getColaIngreso().estaVacia()) {
            Producto p = actual.getColaIngreso().desencolar();
            actual.getColaTraspaso().encolar(p);
            ultimoMensaje = "Producto " + p.getNombre() + " pasa a preparación en " + actual.getNombre();
            return true;
        }

        // 2. Si hay producto en preparación, moverlo a salida
        if (!actual.getColaTraspaso().estaVacia()) {
            Producto p = actual.getColaTraspaso().desencolar();
            actual.getColaDespacho().encolar(p);
            ultimoMensaje = "Producto " + p.getNombre() + " pasa a cola de salida en " + actual.getNombre();
            return true;
        }

        // 3. Si hay producto en salida, enviarlo a la siguiente sucursal (ingreso)
        if (!actual.getColaDespacho().estaVacia()) {
            Producto p = actual.getColaDespacho().desencolar();
            siguiente.getColaIngreso().encolar(p);
            indiceActual++; // avanzamos a la siguiente sucursal
            ultimoMensaje = "Producto " + p.getNombre() + " enviado de " + actual.getNombre() + " a " + siguiente.getNombre();
            return true;
        }

        // Si no hay nada en ninguna cola, la transferencia se estancó
        ultimoMensaje = "Sin productos en colas de " + actual.getNombre();
        return false;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public Producto getProductoActual() {
        return productoActual;
    }
    
    public int[] getRuta() {
    return rutaActual;
}
}
package fase2edd.simulacion;

import fase2edd.control.ControladorSucursales;
import fase2edd.model.Producto;
import fase2edd.model.Sucursal;
import fase2edd.model.Producto;
import fase2edd.model.Sucursal;
import javax.swing.SwingUtilities;

public class SimuladorDespacho {

    private int[] rutaActual;
    private int indiceActual;
    private Producto productoActual;
    private String ultimoMensaje;
    private boolean finalizada;
    private boolean enEjecucion;
    private Thread hiloSimulacion;
    private ControladorSucursales ctrlSucursales;

  
    private Runnable alActualizar;

    public SimuladorDespacho() {
        finalizada = true;
        enEjecucion = false;
        ultimoMensaje = "";
    }

    
    public void setAlActualizar(Runnable callback) {
        this.alActualizar = callback;
    }

    private void notificarGUI() {
        if (alActualizar != null) {
            SwingUtilities.invokeLater(alActualizar);
        }
    }

    public int[] getRuta() {
        return rutaActual;
    }

    public int getIndiceActual() {
        return indiceActual;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public boolean isEnEjecucion() {
        return enEjecucion;
    }

    public Producto getProductoActual() {
        return productoActual;
    }

    
    public void iniciarTransferencia(Producto p, int[] ruta, ControladorSucursales ctrl) {
        if (enEjecucion) {
            ultimoMensaje = "Ya hay una transferencia en curso.";
            return;
        }
        this.rutaActual = ruta;
        this.indiceActual = 0;
        this.productoActual = p;
        this.finalizada = false;
        this.enEjecucion = true;
        this.ctrlSucursales = ctrl;

        // Colocar en cola de ingreso de la primera sucursal
        Sucursal origen = ctrl.buscarPorId(ruta[0]);
        if (origen != null) {
            origen.getColaIngreso().encolar(p);
            productoActual.setEstado(EstadoProducto.EN_TRANSITO);
            ultimoMensaje = "Producto " + p.getNombre() + " encolado en ingreso de " + origen.getNombre();
        }

        notificarGUI();

        // Lanzar hilo que procesará los pasos automáticamente
        hiloSimulacion = new Thread(() -> ejecutarSimulacion());
        hiloSimulacion.start();
    }

    
    private void ejecutarSimulacion() {
        while (!finalizada && enEjecucion) {
            // Pequeña pausa entre pasos para que se vea en la GUI
            try {
                Thread.sleep(500); // medio segundo entre pasos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            boolean continuar = procesarUnPasoInterno();
            notificarGUI();

            if (!continuar) {
                break;
            }
        }
        enEjecucion = false;
        notificarGUI();
    }

    private boolean procesarUnPasoInterno() {
        if (finalizada || rutaActual == null) {
            ultimoMensaje = "No hay transferencia en curso.";
            return false;
        }

        Sucursal actual = ctrlSucursales.buscarPorId(rutaActual[indiceActual]);
        if (actual == null) {
            ultimoMensaje = "Error: sucursal no encontrada.";
            finalizada = true;
            return false;
        }

       
        if (indiceActual == rutaActual.length - 1) {
            if (!actual.getColaIngreso().estaVacia()) {
                // Simular tiempo de ingreso del destino
                dormir((long) (actual.getTiempoIngreso() * 1000));
                Producto p = actual.getColaIngreso().desencolar();
                p.setEstado(EstadoProducto.DISPONIBLE);
                actual.getInventario().insertarProducto(p);
                ultimoMensaje = "Producto " + p.getNombre() + " recibido en destino final " + actual.getNombre();
            }
            finalizada = true;
            productoActual.setEstado(EstadoProducto.DISPONIBLE);
            return false;
        }

        Sucursal siguiente = ctrlSucursales.buscarPorId(rutaActual[indiceActual + 1]);
        if (siguiente == null) {
            ultimoMensaje = "Error: sucursal siguiente no encontrada.";
            finalizada = true;
            return false;
        }

        // Movimiento 1: Ingreso, Preparación (con tiempo de ingreso)
        if (!actual.getColaIngreso().estaVacia()) {
            dormir((long) (actual.getTiempoIngreso() * 500));
            Producto p = actual.getColaIngreso().desencolar();
            actual.getColaTraspaso().encolar(p);
            ultimoMensaje = "Producto " + p.getNombre() + " pasa a preparacion en " + actual.getNombre();
            return true;
        }

        // Movimiento 2: Preparación, Salida (con tiempo de traspaso)
        if (!actual.getColaTraspaso().estaVacia()) {
            dormir((long) (actual.getTiempoTraspaso() * 500));
            Producto p = actual.getColaTraspaso().desencolar();
            actual.getColaDespacho().encolar(p);
            ultimoMensaje = "Producto " + p.getNombre() + " pasa a cola de salida en " + actual.getNombre();
            return true;
        }

        // Movimiento 3: Salida , Siguiente sucursal (con intervalo de despacho)
        if (!actual.getColaDespacho().estaVacia()) {
            dormir((long) (actual.getIntervaloDespacho() * 500));
            Producto p = actual.getColaDespacho().desencolar();
            siguiente.getColaIngreso().encolar(p);
            indiceActual++;
            ultimoMensaje = "Producto " + p.getNombre() + " enviado de " + actual.getNombre() + " a " + siguiente.getNombre();
            return true;
        }

        ultimoMensaje = "Sin productos en colas de " + actual.getNombre();
        return false;
    }


    public boolean procesarUnPaso(ControladorSucursales ctrl) {
        this.ctrlSucursales = ctrl;
        boolean resultado = procesarUnPasoInterno();
        notificarGUI();
        return resultado;
    }

    private void dormir(long milis) {
        try {
            if (milis > 0) {
                Thread.sleep(milis);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void detener() {
        enEjecucion = false;
        if (hiloSimulacion != null) {
            hiloSimulacion.interrupt();
        }
        ultimoMensaje = "Simulacion detenida por el usuario.";
        notificarGUI();
    }

    public void prepararTransferencia(Producto p, int[] ruta, ControladorSucursales ctrl) {
        this.rutaActual = ruta;
        this.indiceActual = 0;
        this.productoActual = p;
        this.finalizada = false;
        this.enEjecucion = false; // manual
        this.ctrlSucursales = ctrl;

        ultimoMensaje = "Producto " + p.getNombre() + " listo para iniciar transferencia manual.";
    }
}

package fase2edd.simulacion;

import fase2edd.control.ControladorSucursales;
import fase2edd.estructuras.cola.Cola;
import fase2edd.model.Producto;
import fase2edd.model.Sucursal;


public class SimuladorDespacho {
    // Variable para seguimiento visual (opcional)
    private String ultimoMensaje;

    public SimuladorDespacho() {
        ultimoMensaje = "";
    }

    /**
     * Simula el movimiento de un producto a través de la ruta calculada.
     * La ruta es un arreglo de ids de sucursales, del origen al destino.
     * El producto ya fue encolado en la cola de ingreso de la primera sucursal.
     */
    public void simularRuta(int[] ruta, ControladorSucursales ctrl, Producto producto) {
        if (ruta == null || ruta.length < 2) return;

        // Para cada sucursal intermedia (origen y las intermedias antes del destino)
        for (int i = 0; i < ruta.length - 1; i++) {
            Sucursal actual = ctrl.buscarPorId(ruta[i]);
            Sucursal siguiente = ctrl.buscarPorId(ruta[i + 1]);
            if (actual == null || siguiente == null) continue;

            // Procesar cola de ingreso de la sucursal actual
            // (el producto ingresó al inicio o por la transferencia previa)
            // En una simulación real se manejarían tiempos, aquí encolamos y desencolamos lógicamente.
            Cola ingreso = actual.getColaIngreso();
            Cola traspaso = actual.getColaTraspaso();
            Cola despacho = actual.getColaDespacho();

            // Si es la sucursal origen, el producto ya está en la cola de ingreso
            // Si es una sucursal intermedia, también lo encolamos al "recibirlo" (simulado)
            if (i == 0) {
                // Ya se puso en ingreso antes, solo procesamos
            } else {
                ingreso.encolar(producto);
            }

            // Tiempo de ingreso: desencolar y pasar a preparación de traspaso (si no es destino final)
            while (!ingreso.estaVacia()) {
                Producto p = ingreso.desencolar();
                if (siguiente.getId() == ruta[ruta.length - 1]) {
                    // Es el último salto: el producto queda en destino
                    p.setEstado(EstadoProducto.DISPONIBLE);
                    // Se agrega al inventario de la sucursal destino (siguiente)
                    siguiente.getInventario().insertarProducto(p);
                    ultimoMensaje = "Producto " + p.getNombre() + " llegó al destino " + siguiente.getNombre();
                    return;
                } else {
                    // Sucursal intermedia: preparar traspaso
                    p.setEstado(EstadoProducto.EN_TRANSITO);
                    traspaso.encolar(p);
                }
            }

            // Tiempo de preparación: pasar de traspaso a despacho
            while (!traspaso.estaVacia()) {
                Producto p = traspaso.desencolar();
                despacho.encolar(p);
            }

            // Envío: se respeta el intervalo de despacho (simulado como desencolar uno por paso)
            if (!despacho.estaVacia()) {
                Producto p = despacho.desencolar();
                // El producto viaja a la siguiente sucursal
                // Lo ponemos en la cola de ingreso de la siguiente (para el próximo ciclo)
                siguiente.getColaIngreso().encolar(p);
                ultimoMensaje = "Producto " + p.getNombre() + " enviado de " + actual.getNombre() + " a " + siguiente.getNombre();
            }
        }
    }

    /**
     * Permite procesar un solo paso de la simulación (para interfaz paso a paso).
     * Retorna true si aún hay productos en movimiento.
     */
    public boolean procesarUnEnvio(ControladorSucursales ctrl) {
        // Recorrer todas las sucursales buscando productos en cola de despacho
        Sucursal[] todas = ctrl.getSucursales();
        boolean hayMovimiento = false;
        for (int i = 0; i < todas.length; i++) {
            Sucursal s = todas[i];
            if (s == null) continue;
            // Si hay productos en cola de traspaso, los movemos a despacho
            if (!s.getColaTraspaso().estaVacia()) {
                Producto p = s.getColaTraspaso().desencolar();
                s.getColaDespacho().encolar(p);
                hayMovimiento = true;
            }
            // Si hay productos en despacho, enviar uno al destino (según conexiones)
            if (!s.getColaDespacho().estaVacia()) {
                Producto p = s.getColaDespacho().verFrente(); // no desencolar aún
                // Necesitamos saber el destino de este producto; la lógica de transferencia
                // guarda la ruta, pero aquí simplificamos: asumimos que el siguiente destino
                // está indicado en alguna estructura externa. 
                // Para evitar complejidad, procesaremos solo si el controlador de transferencias
                // tiene un destino pendiente. Esta función es básica para demostración.
                hayMovimiento = true;
                break; // por ahora solo señala que hay algo pendiente
            }
        }
        return hayMovimiento;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }
}
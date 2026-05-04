package fase2edd.vista;

import fase2edd.control.ControladorGlobal;
import fase2edd.model.Producto;
import fase2edd.model.Sucursal;

import fase2edd.estructuras.cola.Cola;
import fase2edd.simulacion.SimuladorDespacho;
import javax.swing.*;

public class PanelTransferenciasHelper {

    private ControladorGlobal controlador;
    private JComboBox<String> cmbSucursalOrigenTransferencia;
    private JComboBox<String> cmbSucursalDestinoTransferencia;
    private JComboBox<String> cmbProductoTransferencia;
    private JRadioButton rbnTransferenciaTiempo;
    private JRadioButton rbnTransferenciaCosto;
    private JButton btnIniciarTransferencia;
    private JButton btnProcesarPaso;
    private JTextArea txtColaIngreso;
    private JTextArea txtColaPreparacion;
    private JTextArea txtColaSalida;
    private JLabel lblEstadoProducto;
    private JLabel lblETA;
    private JLabel lblUltimoEvento;
    private JLabel output; // barra de estado principal
    private int[] rutaActual;

    public PanelTransferenciasHelper(ControladorGlobal controlador,
            JComboBox<String> cmbSucursalOrigenTransferencia,
            JComboBox<String> cmbSucursalDestinoTransferencia,
            JComboBox<String> cmbProductoTransferencia,
            JRadioButton rbnTransferenciaTiempo,
            JRadioButton rbnTransferenciaCosto,
            JButton btnIniciarTransferencia,
            JButton btnProcesarPaso,
            JTextArea txtColaIngreso,
            JTextArea txtColaPreparacion,
            JTextArea txtColaSalida,
            JLabel lblEstadoProducto,
            JLabel lblETA,
            JLabel lblUltimoEvento,
            JLabel output) {
        this.controlador = controlador;
        this.cmbSucursalOrigenTransferencia = cmbSucursalOrigenTransferencia;
        this.cmbSucursalDestinoTransferencia = cmbSucursalDestinoTransferencia;
        this.cmbProductoTransferencia = cmbProductoTransferencia;
        this.rbnTransferenciaTiempo = rbnTransferenciaTiempo;
        this.rbnTransferenciaCosto = rbnTransferenciaCosto;
        this.btnIniciarTransferencia = btnIniciarTransferencia;
        this.btnProcesarPaso = btnProcesarPaso;
        this.txtColaIngreso = txtColaIngreso;
        this.txtColaPreparacion = txtColaPreparacion;
        this.txtColaSalida = txtColaSalida;
        this.lblEstadoProducto = lblEstadoProducto;
        this.lblETA = lblETA;
        this.lblUltimoEvento = lblUltimoEvento;
        this.output = output;

        // Agrupar radio buttons de criterio
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbnTransferenciaTiempo);
        grupo.add(rbnTransferenciaCosto);
        rbnTransferenciaTiempo.setSelected(true);

        // Configurar áreas de texto como solo lectura
        txtColaIngreso.setEditable(false);
        txtColaPreparacion.setEditable(false);
        txtColaSalida.setEditable(false);

        // Evento al cambiar sucursal origen: actualizar combo de productos
        cmbSucursalOrigenTransferencia.addActionListener(e -> cargarProductosOrigen());
        // Evento al cambiar sucursal origen/destino: actualizar visualización de colas (de la origen)
        cmbSucursalOrigenTransferencia.addActionListener(e -> actualizarColasVisual());
        cmbSucursalDestinoTransferencia.addActionListener(e -> actualizarColasVisual());
    }

    // Llena los combos de sucursales
    public void actualizarCombos() {
        cmbSucursalOrigenTransferencia.removeAllItems();
        cmbSucursalDestinoTransferencia.removeAllItems();
        Sucursal[] sucursales = controlador.getCtrlSucursales().getSucursales();
        for (Sucursal s : sucursales) {
            String item = s.getId() + " - " + s.getNombre();
            cmbSucursalOrigenTransferencia.addItem(item);
            cmbSucursalDestinoTransferencia.addItem(item);
        }
        if (sucursales.length > 0) {
            cmbSucursalOrigenTransferencia.setSelectedIndex(0);
            cmbSucursalDestinoTransferencia.setSelectedIndex(0);
        }
        cargarProductosOrigen();
        actualizarColasVisual();
    }

    // Carga los productos de la sucursal origen en cmbProductoTransferencia
    private void cargarProductosOrigen() {
        cmbProductoTransferencia.removeAllItems();
        String selOrigen = (String) cmbSucursalOrigenTransferencia.getSelectedItem();
        if (selOrigen == null) {
            return;
        }
        int idOrigen = Integer.parseInt(selOrigen.split(" - ")[0]);
        controlador.setSucursalActiva(idOrigen);
        Producto[] productos = controlador.listarPorNombre();
        if (productos != null) {
            for (Producto p : productos) {
                cmbProductoTransferencia.addItem(p.getCodigoBarra() + " - " + p.getNombre());
            }
        }
        if (cmbProductoTransferencia.getItemCount() > 0) {
            cmbProductoTransferencia.setSelectedIndex(0);
        }
    }

    public void iniciarTransferenciaAutomatica() {
        String selOrigen = (String) cmbSucursalOrigenTransferencia.getSelectedItem();
        String selDestino = (String) cmbSucursalDestinoTransferencia.getSelectedItem();
        String selProd = (String) cmbProductoTransferencia.getSelectedItem();

        if (selOrigen == null || selDestino == null || selProd == null) {
            output.setText("Seleccione sucursales y producto.");
            return;
        }

        int idOrigen = Integer.parseInt(selOrigen.split(" - ")[0]);
        int idDestino = Integer.parseInt(selDestino.split(" - ")[0]);

        if (idOrigen == idDestino) {
            output.setText("Origen y destino no pueden ser iguales.");
            return;
        }

        String codigo = selProd.split(" - ")[0];
        int criterio = rbnTransferenciaTiempo.isSelected() ? 0 : 1;

        controlador.setSucursalActiva(idOrigen);
        Producto p = controlador.buscarPorCodigo(codigo);
        if (p == null) {
            output.setText("Producto no encontrado en sucursal origen.");
            return;
        }

        // Registrar callback para refrescar GUI automáticamente
        SimuladorDespacho sim = controlador.getCtrlTransferencias().getSimulador();
        sim.setAlActualizar(() -> {
            actualizarColasVisual();
            lblUltimoEvento.setText("Evento: " + sim.getUltimoMensaje());
            if (sim.isFinalizada()) {
                lblEstadoProducto.setText("Estado: DISPONIBLE (finalizada)");
                lblETA.setText("Transferencia completada.");
            } else {
                lblEstadoProducto.setText("Estado: EN_TRANSITO");
                lblETA.setText("Ruta restante: " + arrayToStringRestante(sim.getRuta(), sim.getIndiceActual()));
            }
        });

        boolean ok = controlador.iniciarTransferenciaAutomatica(p, idOrigen, idDestino, criterio);
        if (!ok) {
            output.setText("No se pudo calcular ruta.");
            return;
        }

        rutaActual = sim.getRuta();
        lblEstadoProducto.setText("Estado: " + p.getEstado().name());
        lblUltimoEvento.setText("Iniciada: " + sim.getUltimoMensaje());
        lblETA.setText("Ruta: " + arrayToString(rutaActual));
        output.setText("Transferencia automatica iniciada (hilo en ejecucion).");
        actualizarColasVisual();
    }

    // Muestra las colas de la sucursal origen en los JTextArea
    private void actualizarColasVisual() {
        txtColaIngreso.setText("");
        txtColaPreparacion.setText("");
        txtColaSalida.setText("");
        String selOrigen = (String) cmbSucursalOrigenTransferencia.getSelectedItem();
        if (selOrigen == null) {
            return;
        }
        int idOrigen = Integer.parseInt(selOrigen.split(" - ")[0]);
        Sucursal s = controlador.getCtrlSucursales().buscarPorId(idOrigen);
        if (s == null) {
            return;
        }

        txtColaIngreso.setText(colaToString(s.getColaIngreso()));
        txtColaPreparacion.setText(colaToString(s.getColaTraspaso()));
        txtColaSalida.setText(colaToString(s.getColaDespacho()));
    }

    public void detenerSimulacion() {
        controlador.detenerSimulacion();
        output.setText("Simulación detenida.");
        actualizarColasVisual();
    }

    private String colaToString(Cola cola) {
        Producto[] arr = cola.verTodos();
        if (arr.length == 0) {
            return "(vacía)";
        }
        StringBuilder sb = new StringBuilder();
        for (Producto p : arr) {
            sb.append(p.getCodigoBarra()).append(" - ").append(p.getNombre()).append("\n");
        }
        return sb.toString();
    }

    // Iniciar transferencia del producto seleccionado
    public void iniciarTransferencia() {
        String selOrigen = (String) cmbSucursalOrigenTransferencia.getSelectedItem();
        String selDestino = (String) cmbSucursalDestinoTransferencia.getSelectedItem();
        String selProd = (String) cmbProductoTransferencia.getSelectedItem();

        if (selOrigen == null || selDestino == null || selProd == null) {
            output.setText("Seleccione sucursales y producto.");
            return;
        }

        int idOrigen = Integer.parseInt(selOrigen.split(" - ")[0]);
        int idDestino = Integer.parseInt(selDestino.split(" - ")[0]);

        if (idOrigen == idDestino) {
            output.setText("Origen y destino no pueden ser iguales.");
            return;
        }

        String codigo = selProd.split(" - ")[0];
        int criterio = rbnTransferenciaTiempo.isSelected() ? 0 : 1;

        controlador.setSucursalActiva(idOrigen);
        Producto p = controlador.buscarPorCodigo(codigo);
        if (p == null) {
            output.setText("Producto no encontrado en sucursal origen.");
            return;
        }

        boolean ok = controlador.prepararTransferencia(p, idOrigen, idDestino, criterio);
        if (!ok) {
            output.setText("No se pudo calcular ruta.");
            return;
        }

        SimuladorDespacho sim = controlador.getCtrlTransferencias().getSimulador();
        rutaActual = sim.getRuta();
        lblEstadoProducto.setText("Estado: " + p.getEstado().name());
        lblUltimoEvento.setText("Iniciada: " + sim.getUltimoMensaje());
        lblETA.setText("Ruta: " + arrayToString(rutaActual));
        output.setText("Transferencia iniciada paso a paso.");

        actualizarColasVisual();
    }

    private String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                sb.append(" -> ");
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    // Procesar siguiente paso de la simulación
    public void procesarSiguientePaso() {
        SimuladorDespacho sim = controlador.getCtrlTransferencias().getSimulador();
        boolean continuar = controlador.getCtrlTransferencias().procesarSiguientePaso();

        lblUltimoEvento.setText("Evento: " + sim.getUltimoMensaje());

        if (sim.isFinalizada()) {
            lblEstadoProducto.setText("Estado: DISPONIBLE (finalizada)");
            lblETA.setText("Transferencia completada.");
        } else {
            lblEstadoProducto.setText("Estado: EN_TRANSITO");
            // Mostrar ruta restante
            int[] rutaCompleta = sim.getRuta();
            int idx = sim.getIndiceActual();
            if (rutaCompleta != null && idx < rutaCompleta.length) {
                String rutaRestante = arrayToStringRestante(rutaCompleta, idx);
                lblETA.setText("Ruta restante: " + rutaRestante);
            }
        }

        actualizarColasVisual();

        if (!continuar) {
            output.setText("Simulación finalizada.");
        } else {
            output.setText("Paso procesado.");
        }
    }

    private String arrayToStringRestante(int[] arr, int inicio) {
        StringBuilder sb = new StringBuilder();
        for (int i = inicio; i < arr.length; i++) {
            if (i > inicio) {
                sb.append(" -> ");
            }
            sb.append(arr[i]);
        }
        return sb.toString();
    }
}

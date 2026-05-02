package fase2edd.vista;

import fase2edd.control.ControladorGlobal;
import fase2edd.estructuras.grafo.Grafo;
import fase2edd.model.Sucursal;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class PanelRedSucursalesHelper {

    private ControladorGlobal controlador;
    private JComboBox<String> cmbOrigenConexion;
    private JComboBox<String> cmbDestinoConexion;
    private JTextField txtTiempoConexion;
    private JTextField txtCostoConexion;
    private JCheckBox chkBidireccional;
    private JButton btnAgregarConexion;
    private JPanel pnlGrafoDibujo;       // Panel donde se dibujará el grafo

    private JComboBox<String> cmbOrigenRuta;
    private JComboBox<String> cmbDestinoRuta;
    private JRadioButton rbnRutaTiempo;
    private JRadioButton rbnRutaCosto;
    private JButton btnCalcularRuta;
    private JLabel lblRutaResultado;
    private JLabel output;               // barra de estado

    public PanelRedSucursalesHelper(ControladorGlobal controlador,
                                    JComboBox<String> cmbOrigenConexion,
                                    JComboBox<String> cmbDestinoConexion,
                                    JTextField txtTiempoConexion,
                                    JTextField txtCostoConexion,
                                    JCheckBox chkBidireccional,
                                    JButton btnAgregarConexion,
                                    JPanel pnlGrafoDibujo,
                                    JComboBox<String> cmbOrigenRuta,
                                    JComboBox<String> cmbDestinoRuta,
                                    JRadioButton rbnRutaTiempo,
                                    JRadioButton rbnRutaCosto,
                                    JButton btnCalcularRuta,
                                    JLabel lblRutaResultado,
                                    JLabel output) {
        this.controlador = controlador;
        // Conexiones
        this.cmbOrigenConexion = cmbOrigenConexion;
        this.cmbDestinoConexion = cmbDestinoConexion;
        this.txtTiempoConexion = txtTiempoConexion;
        this.txtCostoConexion = txtCostoConexion;
        this.chkBidireccional = chkBidireccional;
        this.btnAgregarConexion = btnAgregarConexion;
        this.pnlGrafoDibujo = pnlGrafoDibujo;
        // Rutas
        this.cmbOrigenRuta = cmbOrigenRuta;
        this.cmbDestinoRuta = cmbDestinoRuta;
        this.rbnRutaTiempo = rbnRutaTiempo;
        this.rbnRutaCosto = rbnRutaCosto;
        this.btnCalcularRuta = btnCalcularRuta;
        this.lblRutaResultado = lblRutaResultado;
        this.output = output;

        // Agrupar radio buttons de criterio
        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbnRutaTiempo);
        grupo.add(rbnRutaCosto);
        rbnRutaTiempo.setSelected(true);

        // Configurar panel de dibujo con layout para agregar scroll
        pnlGrafoDibujo.setLayout(new BorderLayout());
        dibujarGrafo();  // intento inicial (vacío)
    }

    // Llena los combos con las sucursales existentes
    public void actualizarCombos() {
        cmbOrigenConexion.removeAllItems();
        cmbDestinoConexion.removeAllItems();
        cmbOrigenRuta.removeAllItems();
        cmbDestinoRuta.removeAllItems();

        Sucursal[] sucursales = controlador.getCtrlSucursales().getSucursales();
        for (Sucursal s : sucursales) {
            String item = s.getId() + " - " + s.getNombre();
            cmbOrigenConexion.addItem(item);
            cmbDestinoConexion.addItem(item);
            cmbOrigenRuta.addItem(item);
            cmbDestinoRuta.addItem(item);
        }
        if (sucursales.length > 0) {
            cmbOrigenConexion.setSelectedIndex(0);
            cmbDestinoConexion.setSelectedIndex(0);
            cmbOrigenRuta.setSelectedIndex(0);
            cmbDestinoRuta.setSelectedIndex(0);
        }
    }

    // Agrega una conexión usando los valores de los campos
    public void agregarConexion() {
        String selOrigen = (String) cmbOrigenConexion.getSelectedItem();
        String selDestino = (String) cmbDestinoConexion.getSelectedItem();
        if (selOrigen == null || selDestino == null) {
            output.setText("Seleccione sucursales válidas.");
            return;
        }
        int idOrigen = Integer.parseInt(selOrigen.split(" - ")[0]);
        int idDestino = Integer.parseInt(selDestino.split(" - ")[0]);
        if (idOrigen == idDestino) {
            output.setText("Origen y destino no pueden ser iguales.");
            return;
        }
        String tStr = txtTiempoConexion.getText().trim();
        String cStr = txtCostoConexion.getText().trim();
        if (tStr.isEmpty() || cStr.isEmpty()) {
            output.setText("Ingrese tiempo y costo.");
            return;
        }
        double tiempo, costo;
        try {
            tiempo = Double.parseDouble(tStr);
            costo = Double.parseDouble(cStr);
        } catch (NumberFormatException e) {
            output.setText("Tiempo y costo deben ser numéricos.");
            return;
        }
        boolean bidireccional = chkBidireccional.isSelected();

        fase2edd.model.Conexion conexion = new fase2edd.model.Conexion(idOrigen, idDestino, tiempo, costo, bidireccional);
        boolean ok = controlador.agregarConexion(conexion);
        if (ok) {
            output.setText("Conexión agregada.");
            dibujarGrafo();
            // Limpiar campos
            txtTiempoConexion.setText("");
            txtCostoConexion.setText("");
            chkBidireccional.setSelected(false);
        } else {
            output.setText("Error al agregar conexión.");
        }
    }
    
public void dibujarGrafo() {
    pnlGrafoDibujo.removeAll();
    pnlGrafoDibujo.setLayout(new BorderLayout());

    Grafo grafo = controlador.getCtrlSucursales().getGrafo();
    StringBuilder dot = new StringBuilder("digraph GrafoSucursales {\n");
    dot.append("rankdir=LR;\n");
    dot.append("node [shape=ellipse, fontsize=10];\n");

    Sucursal[] sucursales = controlador.getCtrlSucursales().getSucursales();
    for (Sucursal s : sucursales) {
        dot.append("\"").append(s.getId()).append("\" [label=\"")
           .append(s.getNombre()).append("\"];\n");
    }

    fase2edd.estructuras.grafo.NodoGrafo[] nodos = grafo.getNodos();
    for (fase2edd.estructuras.grafo.NodoGrafo nodo : nodos) {
        int origen = nodo.getIdSucursal();
        fase2edd.estructuras.grafo.Arista[] aristas = nodo.getAristas();
        for (fase2edd.estructuras.grafo.Arista a : aristas) {
            dot.append("\"").append(origen).append("\" -> \"")
               .append(a.getDestino()).append("\" [label=\"T:")
               .append(a.getTiempo()).append(", C:")
               .append(a.getCosto()).append("\"];\n");
        }
    }
    dot.append("}");

    try {
        MutableGraph g = new Parser().read(dot.toString());
        BufferedImage bufImg = Graphviz.fromGraph(g)
                .render(Format.PNG)
                .toImage();

        JLabel label = new JLabel(new ImageIcon(bufImg));

        JScrollPane scroll = new JScrollPane(label);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getHorizontalScrollBar().setUnitIncrement(16);

        // 🔑 Forzar que el scroll NO pida más espacio del que tiene el panel
        scroll.setPreferredSize(pnlGrafoDibujo.getSize());
        scroll.setMaximumSize(pnlGrafoDibujo.getSize());

        pnlGrafoDibujo.add(scroll, BorderLayout.CENTER);

    } catch (Exception e) {
        pnlGrafoDibujo.add(
            new JLabel("Error: " + e.getMessage()),
            BorderLayout.CENTER
        );
    }

    pnlGrafoDibujo.revalidate();
    pnlGrafoDibujo.repaint();
}

    // Calcula ruta más corta y muestra el resultado en lblRutaResultado
    public void calcularRuta() {
        String selOrigen = (String) cmbOrigenRuta.getSelectedItem();
        String selDestino = (String) cmbDestinoRuta.getSelectedItem();
        if (selOrigen == null || selDestino == null) {
            output.setText("Seleccione sucursales.");
            return;
        }
        int idOrigen = Integer.parseInt(selOrigen.split(" - ")[0]);
        int idDestino = Integer.parseInt(selDestino.split(" - ")[0]);
        int criterio = rbnRutaTiempo.isSelected() ? 0 : 1;

        Grafo grafo = controlador.getCtrlSucursales().getGrafo();
        int[] ruta = grafo.rutaMasCorta(idOrigen, idDestino, criterio);
        if (ruta == null || ruta.length == 0) {
            lblRutaResultado.setText("No hay ruta disponible.");
            return;
        }

        // Construir cadena de ruta y peso total
        StringBuilder sb = new StringBuilder("Ruta: ");
        double pesoTotal = 0;
        for (int i = 0; i < ruta.length; i++) {
            sb.append(ruta[i]);
            if (i < ruta.length - 1) {
                sb.append(" -> ");
                pesoTotal += grafo.obtenerPesoArista(ruta[i], ruta[i + 1], criterio);
            }
        }
        sb.append(" | ");
        sb.append(criterio == 0 ? "Tiempo total: " : "Costo total: ");
        sb.append(String.format("%.2f", pesoTotal));
        lblRutaResultado.setText(sb.toString());
        output.setText("Ruta calculada.");
    }
}
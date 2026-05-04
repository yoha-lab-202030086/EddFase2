package fase2edd.vista;

import fase2edd.control.ControladorGlobal;
import fase2edd.inventario.Inventario;
import fase2edd.servicios.ServicioMedicion;
import javax.swing.*;

public class PanelMedicionRendimiento {

    private ControladorGlobal controlador;
    private ServicioMedicion medicion;
    private JLabel output;

    public PanelMedicionRendimiento(ControladorGlobal controlador, JLabel output) {
        this.controlador = controlador;
        this.medicion = new ServicioMedicion();
        this.output = output;
    }

 
    public void mostrarDialogoMedicion() {
        String[] opciones = {
            "Buscar por NOMBRE (Lista vs AVL)",
            "Buscar por CÓDIGO (Lista vs Hash)",
            "Buscar por CATEGORÍA (Lista vs B+)",
            "Buscar por RANGO FECHAS (Lista vs B)"
        };

        String elegido = (String) JOptionPane.showInputDialog(
            null,
            "Seleccione el tipo de medición a realizar:",
            "Medición de Rendimiento",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        if (elegido == null) return;

      
        String valor = JOptionPane.showInputDialog(null, "Ingrese el valor de búsqueda:");
        if (valor == null || valor.trim().isEmpty()) {
            output.setText("Medición cancelada.");
            return;
        }

        
        Inventario inv = controlador.getInventarioActivo();
        if (inv == null) {
            output.setText("Error: No hay sucursal activa.");
            return;
        }

       
        String tipo = "";
        switch (elegido) {
            case "Buscar por NOMBRE (Lista vs AVL)":
                medicion.compararBusquedaPorNombre(inv, valor.trim());
                tipo = "Nombre";
                break;
            case "Buscar por CÓDIGO (Lista vs Hash)":
                medicion.compararBusquedaPorCodigo(inv, valor.trim());
                tipo = "Código";
                break;
            case "Buscar por CATEGORÍA (Lista vs B+)":
                medicion.compararBusquedaPorCategoria(inv, valor.trim());
                tipo = "Categoría";
                break;
            case "Buscar por RANGO FECHAS (Lista vs B)":
                String valor2 = JOptionPane.showInputDialog(null, "Ingrese fecha fin (AAAA-MM-DD):");
                if (valor2 == null || valor2.trim().isEmpty()) {
                    output.setText("Medición cancelada.");
                    return;
                }
                medicion.compararBusquedaPorRangoFechas(inv, valor.trim(), valor2.trim());
                tipo = "Rango fechas";
                break;
        }

       
        String resumen = medicion.obtenerResumen(tipo);
        JTextArea textArea = new JTextArea(resumen);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setPreferredSize(new java.awt.Dimension(400, 200));
        JOptionPane.showMessageDialog(null, scroll, "Resultados de Medición", JOptionPane.INFORMATION_MESSAGE);
        output.setText("Medición completada.");
    }

    public ServicioMedicion getServicioMedicion() {
        return medicion;
    }
}
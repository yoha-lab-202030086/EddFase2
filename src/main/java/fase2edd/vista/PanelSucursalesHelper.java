package fase2edd.vista;

import fase2edd.control.ControladorGlobal;
import fase2edd.model.Sucursal;
import fase2edd.model.ResultadoOperacion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PanelSucursalesHelper {

    private ControladorGlobal controlador;
    private JTextField txtIdSucursal;
    private JTextField txtNombreSucursal;
    private JTextField txtUbicacion;
    private JTextField txtTiempoIngreso;
    private JTextField txtTiempoTraspaso;
    private JTextField txtTiempoDespacho;
    private JTable tblSucursales;
    private JLabel output;  // Para mostrar mensajes en la barra de estado

    public PanelSucursalesHelper(ControladorGlobal controlador,
                                 JTextField txtIdSucursal,
                                 JTextField txtNombreSucursal,
                                 JTextField txtUbicacion,
                                 JTextField txtTiempoIngreso,
                                 JTextField txtTiempoTraspaso,
                                 JTextField txtTiempoDespacho,
                                 JTable tblSucursales,
                                 JLabel output) {
        this.controlador = controlador;
        this.txtIdSucursal = txtIdSucursal;
        this.txtNombreSucursal = txtNombreSucursal;
        this.txtUbicacion = txtUbicacion;
        this.txtTiempoIngreso = txtTiempoIngreso;
        this.txtTiempoTraspaso = txtTiempoTraspaso;
        this.txtTiempoDespacho = txtTiempoDespacho;
        this.tblSucursales = tblSucursales;
        this.output = output;

        // Configurar tabla
        String[] columnas = {"ID", "Nombre", "Ubicación", "T. Ingreso", "T. Traspaso", "T. Despacho"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        tblSucursales.setModel(modelo);
        tblSucursales.getSelectionModel().addListSelectionListener(e -> cargarDatosEnFormulario());
    }

    // Cargar la tabla con los datos del controlador
    public void actualizarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblSucursales.getModel();
        modelo.setRowCount(0);
        Sucursal[] sucursales = controlador.getCtrlSucursales().getSucursales();
        for (Sucursal s : sucursales) {
            modelo.addRow(new Object[]{
                s.getId(),
                s.getNombre(),
                s.getUbicacion(),
                s.getTiempoIngreso(),
                s.getTiempoTraspaso(),
                s.getIntervaloDespacho()
            });
        }
    }

    // Al seleccionar una fila de la tabla, cargar datos en el formulario
    private void cargarDatosEnFormulario() {
        int fila = tblSucursales.getSelectedRow();
        if (fila == -1) return;
        DefaultTableModel modelo = (DefaultTableModel) tblSucursales.getModel();
        txtIdSucursal.setText(modelo.getValueAt(fila, 0).toString());
        txtNombreSucursal.setText(modelo.getValueAt(fila, 1).toString());
        txtUbicacion.setText(modelo.getValueAt(fila, 2).toString());
        txtTiempoIngreso.setText(modelo.getValueAt(fila, 3).toString());
        txtTiempoTraspaso.setText(modelo.getValueAt(fila, 4).toString());
        txtTiempoDespacho.setText(modelo.getValueAt(fila, 5).toString());
    }

    // Validar campos del formulario
    private boolean validarCampos() {
        if (txtIdSucursal.getText().trim().isEmpty() ||
            txtNombreSucursal.getText().trim().isEmpty() ||
            txtUbicacion.getText().trim().isEmpty() ||
            txtTiempoIngreso.getText().trim().isEmpty() ||
            txtTiempoTraspaso.getText().trim().isEmpty() ||
            txtTiempoDespacho.getText().trim().isEmpty()) {
            output.setText("Error: Todos los campos son obligatorios.");
            return false;
        }
        try {
            int id = Integer.parseInt(txtIdSucursal.getText().trim());
            double ti = Double.parseDouble(txtTiempoIngreso.getText().trim());
            double tt = Double.parseDouble(txtTiempoTraspaso.getText().trim());
            double td = Double.parseDouble(txtTiempoDespacho.getText().trim());
            if (id <= 0 || ti < 0 || tt < 0 || td < 0) {
                output.setText("Error: Valores negativos o ID inválido.");
                return false;
            }
        } catch (NumberFormatException e) {
            output.setText("Error: Formato numérico inválido.");
            return false;
        }
        return true;
    }

    // Limpiar formulario
    private void limpiarFormulario() {
        txtIdSucursal.setText("");
        txtNombreSucursal.setText("");
        txtUbicacion.setText("");
        txtTiempoIngreso.setText("");
        txtTiempoTraspaso.setText("");
        txtTiempoDespacho.setText("");
        tblSucursales.clearSelection();
    }

    // ========== ACCIONES DE BOTONES ==========

    public void crearSucursal() {
        if (!validarCampos()) return;
        int id = Integer.parseInt(txtIdSucursal.getText().trim());
        String nombre = txtNombreSucursal.getText().trim();
        String ubicacion = txtUbicacion.getText().trim();
        double tIngreso = Double.parseDouble(txtTiempoIngreso.getText().trim());
        double tTraspaso = Double.parseDouble(txtTiempoTraspaso.getText().trim());
        double tDespacho = Double.parseDouble(txtTiempoDespacho.getText().trim());

        boolean ok = controlador.crearSucursal(id, nombre, ubicacion, tIngreso, tTraspaso, tDespacho);
        if (ok) {
            output.setText("Sucursal creada correctamente.");
            limpiarFormulario();
            actualizarTabla();
        } else {
            output.setText("Error: No se pudo crear la sucursal. ID duplicado.");
        }
    }

    public void modificarSucursal() {
        if (!validarCampos()) return;
        int id = Integer.parseInt(txtIdSucursal.getText().trim());
        String nombre = txtNombreSucursal.getText().trim();
        String ubicacion = txtUbicacion.getText().trim();
        double tIngreso = Double.parseDouble(txtTiempoIngreso.getText().trim());
        double tTraspaso = Double.parseDouble(txtTiempoTraspaso.getText().trim());
        double tDespacho = Double.parseDouble(txtTiempoDespacho.getText().trim());

        boolean ok = controlador.modificarSucursal(id, nombre, ubicacion, tIngreso, tTraspaso, tDespacho);
        if (ok) {
            output.setText("Sucursal modificada correctamente.");
            limpiarFormulario();
            actualizarTabla();
        } else {
            output.setText("Error: No se encontró la sucursal con ID " + id);
        }
    }

    public void eliminarSucursal() {
        String idStr = txtIdSucursal.getText().trim();
        if (idStr.isEmpty()) {
            output.setText("Seleccione una sucursal en la tabla o ingrese un ID.");
            return;
        }
        int id = Integer.parseInt(idStr);
        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Eliminar la sucursal ID " + id + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        boolean ok = controlador.eliminarSucursal(id);
        if (ok) {
            output.setText("Sucursal eliminada.");
            limpiarFormulario();
            actualizarTabla();
        } else {
            output.setText("Error: No se encontró la sucursal.");
        }
    }

    public void limpiarSucursal() {
        limpiarFormulario();
        output.setText("Formulario limpiado.");
    }
}
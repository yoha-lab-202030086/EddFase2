package fase2edd.vista;

import fase2edd.control.ControladorGlobal;
import fase2edd.estructuras.listas.ListaEnlazada;
import fase2edd.inventario.Inventario;
import fase2edd.servicios.ServicioCSV;
import fase2edd.servicios.ServicioLog;
import fase2edd.model.Producto;
import fase2edd.model.ResultadoOperacion;
import fase2edd.model.Sucursal;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class VentanaPrincipal extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName());

    private ControladorGlobal controlador;
    private ServicioLog logg;
    private ServicioCSV servicioCSV;
    private PanelSucursalesHelper panelSucursalesHelper;
    private PanelRedSucursalesHelper panelRedHelper;
    JLabel lblRutaResultado;
    private PanelTransferenciasHelper panelTransferenciasHelper;

    public VentanaPrincipal() {
        initComponents();
        controlador = new ControladorGlobal();
        jTabbedPane3.addChangeListener(e -> {
            if (jTabbedPane3.getSelectedIndex() == 2) { // índice de Red de Sucursales
                panelRedHelper.actualizarCombos();
                panelRedHelper.dibujarGrafo();
            }
            if (jTabbedPane3.getSelectedIndex() == 3) {
                panelTransferenciasHelper.actualizarCombos();
            }
        });

        panelTransferenciasHelper = new PanelTransferenciasHelper(
                controlador,
                cmbSucursalOrigenTransferencia,
                cmbSucursalDestinoTransferencia,
                cmbProductoTransferencia,
                rbnTransferenciaTiempo,
                rbnTransferenciaCosto,
                btnIniciarTransferencia,
                btnProcesarPaso,
                txtColaIngreso,
                txtColaPreparacion,
                txtColaSalida,
                lblEstadoProducto,
                lblETA,
                lblUltimoEvento,
                output
        );
        panelTransferenciasHelper.actualizarCombos(); // inicial
        lblRutaResultado = new JLabel();
        
        panelSucursalesHelper = new PanelSucursalesHelper(
                controlador,
                txtIdSucursal,
                txtNombreSucursal,
                txtUbicacion,
                txtTiempoIngreso,
                txtTiempoTraspaso,
                txtTiempoDespacho,
                tblSucursales,
                output
        );
        panelSucursalesHelper.actualizarTabla();

        panelRedHelper = new PanelRedSucursalesHelper(
                controlador,
                cmbOrigenConexion, cmbDestinoConexion, txtTiempoConexion, txtCostoConexion,
                chkBidireccional, btnAgregarConexion, pnlGrafoDibujo,
                cmbOrigenRuta, cmbDestinoRuta, rbnRutaTiempo, rbnRutaCosto,
                btnCalcularRuta, lblRutaResultado, output
        );
        panelRedHelper.actualizarCombos(); // inicial
        panelRedHelper.dibujarGrafo();

        csvConexiones.setEnabled(false);
        csvProducto.setEnabled(false);
        rangos.setVisible(false);
        //controlador = new ControladorGlobal();
        logg = new ServicioLog("errores.log");
        logg.abrir();
        servicioCSV = new ServicioCSV(controlador, logg);
        actualizarComboSucursales();
        refrescarTabla();
        output.setText("Sistema iniciado. Cargue datos desde Archivo → Cargar CSV.");

    }

    public void actualizarComboSucursales() {
        jComboBox1.removeAllItems();
        Sucursal[] sucursales = controlador.getCtrlSucursales().getSucursales();
        for (Sucursal s : sucursales) {
            jComboBox1.addItem(s.getId() + " - " + s.getNombre());
        }
    }

    public void refrescarTabla() {
        javax.swing.table.DefaultTableModel modelo
                = (javax.swing.table.DefaultTableModel) tablaProductos.getModel();
        modelo.setRowCount(0);

        // Obtener sucursal activa del combo
        String seleccion = (String) jComboBox1.getSelectedItem();
        if (seleccion == null) {
            sucursalSeleccionado.setText("Ninguna");
            return;
        }
        int idSucursal = Integer.parseInt(seleccion.split(" - ")[0]);
        controlador.setSucursalActiva(idSucursal);
        sucursalSeleccionado.setText(seleccion);
        sucursal.setText(seleccion);

        Producto[] productos = controlador.listarPorNombre();
        if (productos != null) {
            for (Producto p : productos) {
                modelo.addRow(new Object[]{
                    p.getNombre(), p.getCodigoBarra(), p.getCategoria(),
                    p.getFechaCaducidad(), p.getMarca(), p.getPrecio(), p.getStock()
                });
            }
        }
    }

    // Muestra un mensaje en el label output (barra de estado)
    private void mostrarMensaje(String texto) {
        output.setText(texto);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        sucursalSeleccionado = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        icodigo = new javax.swing.JTextField();
        inombre1 = new javax.swing.JTextField();
        icateg = new javax.swing.JTextField();
        ifecha = new javax.swing.JTextField();
        imarca = new javax.swing.JTextField();
        iprecio = new javax.swing.JTextField();
        istock = new javax.swing.JTextField();
        deshacer = new javax.swing.JButton();
        agregar = new javax.swing.JButton();
        busqueda = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        val = new javax.swing.JLabel();
        valor = new javax.swing.JTextField();
        buscar = new javax.swing.JButton();
        limpiar = new javax.swing.JButton();
        rangos = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        desde = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        hasta = new javax.swing.JTextField();
        rnombre = new javax.swing.JRadioButton();
        rcodigo = new javax.swing.JRadioButton();
        rcateg = new javax.swing.JRadioButton();
        rRango = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        sucursal = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        elimiarSelect = new javax.swing.JButton();
        verAvl = new javax.swing.JButton();
        verHash = new javax.swing.JButton();
        verB = new javax.swing.JButton();
        verBplus = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        btnCrearSucursal = new javax.swing.JButton();
        btnModificarSucursal = new javax.swing.JButton();
        btnEliminarSucursal = new javax.swing.JButton();
        btnLimpiarSucursal = new javax.swing.JButton();
        txtIdSucursal = new javax.swing.JTextField();
        txtUbicacion = new javax.swing.JTextField();
        txtTiempoTraspaso = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtNombreSucursal = new javax.swing.JTextField();
        txtTiempoIngreso = new javax.swing.JTextField();
        txtTiempoDespacho = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSucursales = new javax.swing.JTable();
        btnActualizarTabla = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        chkBidireccional = new javax.swing.JCheckBox();
        btnAgregarConexion = new javax.swing.JButton();
        cmbDestinoConexion = new javax.swing.JComboBox<>();
        cmbOrigenConexion = new javax.swing.JComboBox<>();
        txtTiempoConexion = new javax.swing.JTextField();
        txtCostoConexion = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        cmbOrigenRuta = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        rbnRutaTiempo = new javax.swing.JRadioButton();
        rbnRutaCosto = new javax.swing.JRadioButton();
        btnCalcularRuta = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        cmbDestinoRuta = new javax.swing.JComboBox<>();
        pnlGrafoDibujo = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        cmbSucursalDestinoTransferencia = new javax.swing.JComboBox<>();
        cmbProductoTransferencia = new javax.swing.JComboBox<>();
        cmbSucursalOrigenTransferencia = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        rbnTransferenciaTiempo = new javax.swing.JRadioButton();
        rbnTransferenciaCosto = new javax.swing.JRadioButton();
        btnIniciarTransferencia = new javax.swing.JButton();
        btnProcesarPaso = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtColaIngreso = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtColaPreparacion = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtColaSalida = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lblETA = new javax.swing.JLabel();
        lblUltimoEvento = new javax.swing.JLabel();
        lblEstadoProducto = new javax.swing.JLabel();
        output = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        csvSucursal = new javax.swing.JMenuItem();
        csvConexiones = new javax.swing.JMenuItem();
        csvProducto = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        avl = new javax.swing.JMenuItem();
        btree = new javax.swing.JMenuItem();
        btreeplus = new javax.swing.JMenuItem();
        hash = new javax.swing.JMenuItem();
        grafo = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel1.setText("Sucursal");

        jComboBox1.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", " " }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

        sucursalSeleccionado.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        sucursalSeleccionado.setText("aaaa");

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel3.setText("Sucursal Actual:");

        jLabel2.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel2.setText("Formulario");

        jLabel4.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel4.setText("Nombre:");

        jLabel5.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel5.setText("Categoria:");

        jLabel6.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel6.setText("Codigo:");

        jLabel7.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel7.setText("Fecha:");

        jLabel8.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel8.setText("Marca:");

        jLabel9.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel9.setText("Precio:");

        jLabel10.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel10.setText("Stock:");

        deshacer.setText("Deshacer");
        deshacer.addActionListener(this::deshacerActionPerformed);

        agregar.setText("Agregar");
        agregar.addActionListener(this::agregarActionPerformed);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel2))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(inombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(icodigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(icateg, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ifecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(imarca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(iprecio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(istock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(agregar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(deshacer)
                                .addGap(30, 30, 30)))))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(inombre1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(icodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(icateg, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(ifecha, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(imarca, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(iprecio, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(istock, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(agregar)
                    .addComponent(deshacer))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel11.setText("Busqueda y resultados");

        val.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        val.setText("Valor:");

        buscar.setText("Buscar");
        buscar.addActionListener(this::buscarActionPerformed);

        limpiar.setText("Limpiar");
        limpiar.addActionListener(this::limpiarActionPerformed);

        jLabel15.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel15.setText("Desde:");

        desde.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        desde.setText("YY-MM-DD");

        jLabel16.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel16.setText("Hasta:");

        hasta.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        hasta.setText("YY-MM-DD");

        javax.swing.GroupLayout rangosLayout = new javax.swing.GroupLayout(rangos);
        rangos.setLayout(rangosLayout);
        rangosLayout.setHorizontalGroup(
            rangosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rangosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desde, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(hasta, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        rangosLayout.setVerticalGroup(
            rangosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rangosLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(rangosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(desde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(hasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        buttonGroup1.add(rnombre);
        rnombre.setText("Nombre");
        rnombre.addActionListener(this::rnombreActionPerformed);

        buttonGroup1.add(rcodigo);
        rcodigo.setText("Codigo");
        rcodigo.addActionListener(this::rcodigoActionPerformed);

        buttonGroup1.add(rcateg);
        rcateg.setText("Categoria");
        rcateg.addActionListener(this::rcategActionPerformed);

        buttonGroup1.add(rRango);
        rRango.setText("Rango de Fechas");
        rRango.addActionListener(this::rRangoActionPerformed);

        javax.swing.GroupLayout busquedaLayout = new javax.swing.GroupLayout(busqueda);
        busqueda.setLayout(busquedaLayout);
        busquedaLayout.setHorizontalGroup(
            busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(busquedaLayout.createSequentialGroup()
                .addGroup(busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, busquedaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(rangos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, busquedaLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, busquedaLayout.createSequentialGroup()
                                .addComponent(val)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(busquedaLayout.createSequentialGroup()
                                        .addComponent(valor, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(106, 106, 106))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, busquedaLayout.createSequentialGroup()
                                        .addGroup(busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rnombre)
                                            .addComponent(rcodigo))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rRango)
                                            .addComponent(rcateg))
                                        .addGap(9, 9, 9))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, busquedaLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(70, 70, 70)))))
                .addContainerGap())
            .addGroup(busquedaLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(buscar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(limpiar)
                .addGap(57, 57, 57))
        );
        busquedaLayout.setVerticalGroup(
            busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(busquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rnombre)
                    .addComponent(rcateg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rcodigo)
                    .addComponent(rRango))
                .addGap(16, 16, 16)
                .addGroup(busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(val)
                    .addComponent(valor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rangos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(busquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscar)
                    .addComponent(limpiar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addComponent(busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(busqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel13.setText("Productos en sucursal: ");

        sucursal.setFont(new java.awt.Font("Liberation Sans", 1, 21)); // NOI18N
        sucursal.setText("aaaa");

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "CodigoBarra", "Categoria", "FechaCaducidad", "Marca", "Precio", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaProductos);

        elimiarSelect.setText("Eliminar Seleccionado");
        elimiarSelect.addActionListener(this::elimiarSelectActionPerformed);

        verAvl.setText("Ver AVL");
        verAvl.addActionListener(this::verAvlActionPerformed);

        verHash.setText("Ver Hash");
        verHash.addActionListener(this::verHashActionPerformed);

        verB.setText("Ver B");
        verB.addActionListener(this::verBActionPerformed);

        verBplus.setText("Ver B+");
        verBplus.addActionListener(this::verBplusActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(153, 153, 153)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(33, 33, 33)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sucursalSeleccionado)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(sucursal, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 902, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(elimiarSelect)
                        .addGap(64, 64, 64)
                        .addComponent(verAvl)
                        .addGap(58, 58, 58)
                        .addComponent(verHash)
                        .addGap(67, 67, 67)
                        .addComponent(verB)
                        .addGap(83, 83, 83)
                        .addComponent(verBplus)))
                .addGap(0, 132, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel3)
                        .addComponent(sucursalSeleccionado))
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(sucursal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(elimiarSelect)
                    .addComponent(verAvl)
                    .addComponent(verHash)
                    .addComponent(verB)
                    .addComponent(verBplus))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Productos", jPanel1);

        jLabel12.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel12.setText("ID:");

        jLabel14.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel14.setText("Ubicacion:");

        jLabel17.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel17.setText("T. traspaso:");

        btnCrearSucursal.setText("Crear");
        btnCrearSucursal.addActionListener(this::btnCrearSucursalActionPerformed);

        btnModificarSucursal.setText("Modificar");
        btnModificarSucursal.addActionListener(this::btnModificarSucursalActionPerformed);

        btnEliminarSucursal.setText("Eliminar");
        btnEliminarSucursal.addActionListener(this::btnEliminarSucursalActionPerformed);

        btnLimpiarSucursal.setText("Limpiar");
        btnLimpiarSucursal.addActionListener(this::btnLimpiarSucursalActionPerformed);

        txtIdSucursal.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        txtUbicacion.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        txtTiempoTraspaso.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel18.setText("T. despacho:");

        jLabel19.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel19.setText("Nombre:");

        jLabel20.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel20.setText("Tiempo Ingreso:");

        txtNombreSucursal.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        txtTiempoIngreso.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        txtTiempoDespacho.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N

        tblSucursales.setFont(new java.awt.Font("Liberation Sans", 0, 17)); // NOI18N
        tblSucursales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Ubicacion", "T. Ingreso", "T. Traspaso", "T. Despacho"
            }
        ));
        jScrollPane2.setViewportView(tblSucursales);

        btnActualizarTabla.setText("Actualizar Lista");
        btnActualizarTabla.addActionListener(this::btnActualizarTablaActionPerformed);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnCrearSucursal)
                            .addComponent(jLabel17)
                            .addComponent(jLabel14)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtIdSucursal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                .addComponent(txtUbicacion, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtTiempoTraspaso, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(btnModificarSucursal))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(204, 204, 204)
                                .addComponent(jLabel18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEliminarSucursal)
                                .addGap(98, 98, 98)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtNombreSucursal)
                        .addComponent(txtTiempoIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                        .addComponent(txtTiempoDespacho))
                    .addComponent(btnLimpiarSucursal))
                .addGap(144, 144, 144))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnActualizarTabla)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 927, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(138, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtIdSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNombreSucursal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(6, 6, 6)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(txtUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel20)
                    .addComponent(txtTiempoIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel17)
                        .addComponent(txtTiempoTraspaso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18))
                    .addComponent(txtTiempoDespacho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrearSucursal)
                    .addComponent(btnModificarSucursal)
                    .addComponent(btnEliminarSucursal)
                    .addComponent(btnLimpiarSucursal))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnActualizarTabla)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Sucursales", jPanel2);

        jLabel21.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        jLabel21.setText("Conexiones");

        jLabel22.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel22.setText("Origen:");

        jLabel23.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel23.setText("Destino:");

        jLabel24.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel24.setText("Tiempo:");

        jLabel25.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel25.setText("Costo:");

        chkBidireccional.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        chkBidireccional.setText("Bidireccional");

        btnAgregarConexion.setText("Agregar Conexion");
        btnAgregarConexion.addActionListener(this::btnAgregarConexionActionPerformed);

        cmbDestinoConexion.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        cmbDestinoConexion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbOrigenConexion.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        cmbOrigenConexion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtTiempoConexion.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N

        txtCostoConexion.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel28.setText("Origen:");

        cmbOrigenRuta.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        cmbOrigenRuta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel29.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        jLabel29.setText("Destino:");

        buttonGroup2.add(rbnRutaTiempo);
        rbnRutaTiempo.setText("Tiempo");
        rbnRutaTiempo.addActionListener(this::rbnRutaTiempoActionPerformed);

        buttonGroup2.add(rbnRutaCosto);
        rbnRutaCosto.setText("Costo");

        btnCalcularRuta.setText("Calcular Ruta");
        btnCalcularRuta.addActionListener(this::btnCalcularRutaActionPerformed);

        jLabel27.setText("Ruta");

        cmbDestinoRuta.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
        cmbDestinoRuta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        pnlGrafoDibujo.setBackground(new java.awt.Color(153, 255, 255));

        javax.swing.GroupLayout pnlGrafoDibujoLayout = new javax.swing.GroupLayout(pnlGrafoDibujo);
        pnlGrafoDibujo.setLayout(pnlGrafoDibujoLayout);
        pnlGrafoDibujoLayout.setHorizontalGroup(
            pnlGrafoDibujoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
        );
        pnlGrafoDibujoLayout.setVerticalGroup(
            pnlGrafoDibujoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 638, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(rbnRutaTiempo)
                        .addGap(62, 62, 62)
                        .addComponent(rbnRutaCosto)
                        .addGap(166, 166, 166))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAgregarConexion)
                            .addComponent(chkBidireccional)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel25)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel24))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTiempoConexion, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCostoConexion, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbOrigenConexion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbDestinoConexion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addComponent(jLabel21))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(127, 127, 127)
                                .addComponent(jLabel27))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(btnCalcularRuta))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel28)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cmbOrigenRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel29)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cmbDestinoRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(pnlGrafoDibujo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel21)
                        .addGap(26, 26, 26)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbOrigenConexion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbDestinoConexion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTiempoConexion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtCostoConexion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(chkBidireccional)
                                .addGap(18, 18, 18)
                                .addComponent(btnAgregarConexion)))
                        .addGap(21, 21, 21)
                        .addComponent(jLabel27)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(cmbOrigenRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel29)
                            .addComponent(cmbDestinoRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbnRutaTiempo)
                            .addComponent(rbnRutaCosto))
                        .addGap(18, 18, 18)
                        .addComponent(btnCalcularRuta))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlGrafoDibujo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(56, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Red de Sucursales", jPanel3);

        jLabel26.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel26.setText("Prodcuto:");

        jLabel30.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel30.setText("Origen:");

        jLabel31.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel31.setText("Destino:");

        cmbSucursalDestinoTransferencia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbProductoTransferencia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbSucursalOrigenTransferencia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel32.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        jLabel32.setText("Criterio:");

        buttonGroup3.add(rbnTransferenciaTiempo);
        rbnTransferenciaTiempo.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        rbnTransferenciaTiempo.setText("Tiempo");

        buttonGroup3.add(rbnTransferenciaCosto);
        rbnTransferenciaCosto.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        rbnTransferenciaCosto.setText("Costo");

        btnIniciarTransferencia.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        btnIniciarTransferencia.setText("Iniciar Transferencia");
        btnIniciarTransferencia.addActionListener(this::btnIniciarTransferenciaActionPerformed);

        btnProcesarPaso.setFont(new java.awt.Font("Liberation Sans", 1, 16)); // NOI18N
        btnProcesarPaso.setText("Procesar Siguiente Paso");
        btnProcesarPaso.addActionListener(this::btnProcesarPasoActionPerformed);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(rbnTransferenciaTiempo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rbnTransferenciaCosto)
                        .addGap(189, 189, 189))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(cmbProductoTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbSucursalOrigenTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)))
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbSucursalDestinoTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(btnIniciarTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnProcesarPaso, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(414, 414, 414))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(cmbSucursalDestinoTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbProductoTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbSucursalOrigenTransferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(rbnTransferenciaTiempo)
                    .addComponent(rbnTransferenciaCosto))
                .addGap(62, 62, 62)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIniciarTransferencia)
                    .addComponent(btnProcesarPaso))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        jLabel33.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel33.setText("Cola de Ingreso");

        jLabel34.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel34.setText("Cola preparación");

        jLabel35.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel35.setText("Cola salida");

        txtColaIngreso.setColumns(20);
        txtColaIngreso.setRows(5);
        jScrollPane3.setViewportView(txtColaIngreso);

        txtColaPreparacion.setColumns(20);
        txtColaPreparacion.setRows(5);
        jScrollPane4.setViewportView(txtColaPreparacion);

        txtColaSalida.setColumns(20);
        txtColaSalida.setRows(5);
        jScrollPane5.setViewportView(txtColaSalida);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(63, 63, 63)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(48, 48, 48))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel34)
                    .addComponent(jLabel35))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel36.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel36.setText("ETA:");

        jLabel37.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel37.setText("Estado Actual:");

        jLabel38.setFont(new java.awt.Font("Liberation Sans", 1, 20)); // NOI18N
        jLabel38.setText("Último evento:");

        lblETA.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        lblETA.setText("Estado Actual:");

        lblUltimoEvento.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        lblUltimoEvento.setText("Estado Actual:");

        lblEstadoProducto.setFont(new java.awt.Font("Liberation Sans", 1, 18)); // NOI18N
        lblEstadoProducto.setText("Estado Actual:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel36)
                        .addGap(35, 35, 35)
                        .addComponent(lblETA, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel38)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUltimoEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(217, 217, 217)
                        .addComponent(lblEstadoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addComponent(jLabel37)
                    .addContainerGap(918, Short.MAX_VALUE)))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(lblEstadoProducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(jLabel38)
                    .addComponent(lblETA)
                    .addComponent(lblUltimoEvento))
                .addGap(38, 38, 38))
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel7Layout.createSequentialGroup()
                    .addGap(38, 38, 38)
                    .addComponent(jLabel37)
                    .addContainerGap(129, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane3.addTab("Transferencias", jPanel4);

        jButton1.setText("limpiar");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jMenu1.setText("Archivo");

        csvSucursal.setText("Cargar sucursales CSV");
        csvSucursal.addActionListener(this::csvSucursalActionPerformed);
        jMenu1.add(csvSucursal);

        csvConexiones.setText("Cargar conexiones CSV");
        csvConexiones.addActionListener(this::csvConexionesActionPerformed);
        jMenu1.add(csvConexiones);

        csvProducto.setText("Cargar productos CSV");
        csvProducto.addActionListener(this::csvProductoActionPerformed);
        jMenu1.add(csvProducto);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Visualizacion");

        avl.setText("AVL");
        jMenu2.add(avl);

        btree.setText("Árbol B");
        btree.addActionListener(this::btreeActionPerformed);
        jMenu2.add(btree);

        btreeplus.setText("Árbol B+");
        jMenu2.add(btreeplus);

        hash.setText("Hash");
        jMenu2.add(hash);

        grafo.setText("Grafo");
        jMenu2.add(grafo);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(output, javax.swing.GroupLayout.PREFERRED_SIZE, 974, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(75, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(output, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void csvSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csvSucursalActionPerformed
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        javax.swing.filechooser.FileNameExtensionFilter filtro
                = new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");

        fc.setFileFilter(filtro);

        fc.setAcceptAllFileFilterUsed(false);
        fc.setDialogTitle("Seleccionar archivo de sucursales");
        int resultado = fc.showOpenDialog(this);
        if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
            String ruta = fc.getSelectedFile().getAbsolutePath();
            int cargadas = servicioCSV.cargarSucursales(ruta);
            mostrarMensaje("Sucursales cargadas: " + cargadas);
            actualizarComboSucursales();
            refrescarTabla();

            if (cargadas > 0) {
                csvSucursal.setEnabled(false);
                csvConexiones.setEnabled(true);
            }
        }
    }//GEN-LAST:event_csvSucursalActionPerformed

    private void btreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btreeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btreeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void rRangoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rRangoActionPerformed
        if (rRango.isSelected()) {
            rangos.setVisible(true);
            valor.setVisible(false);
            val.setVisible(false);
        } else {
            rangos.setVisible(false);
        }

        busqueda.revalidate();
        busqueda.repaint();
    }//GEN-LAST:event_rRangoActionPerformed

    private void rnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rnombreActionPerformed
        rangos.setVisible(false);
    }//GEN-LAST:event_rnombreActionPerformed

    private void rcategActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rcategActionPerformed
        rangos.setVisible(false);

    }//GEN-LAST:event_rcategActionPerformed

    private void rcodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rcodigoActionPerformed
        rangos.setVisible(false);

    }//GEN-LAST:event_rcodigoActionPerformed

    private void csvConexionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csvConexionesActionPerformed
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        javax.swing.filechooser.FileNameExtensionFilter filtro
                = new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
        fc.setFileFilter(filtro);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setDialogTitle("Seleccionar archivo de conexiones");
        int resultado = fc.showOpenDialog(this);
        if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
            String ruta = fc.getSelectedFile().getAbsolutePath();
            int cargadas = servicioCSV.cargarConexiones(ruta);
            mostrarMensaje("Conexiones cargadas: " + cargadas);

            if (cargadas > 0) {
                csvConexiones.setEnabled(false);
                csvProducto.setEnabled(true);
            }
        }
    }//GEN-LAST:event_csvConexionesActionPerformed

    private void csvProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_csvProductoActionPerformed
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        javax.swing.filechooser.FileNameExtensionFilter filtro
                = new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
        fc.setFileFilter(filtro);
        fc.setAcceptAllFileFilterUsed(false);
        fc.setDialogTitle("Seleccionar archivo de productos");
        int resultado = fc.showOpenDialog(this);
        if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
            String ruta = fc.getSelectedFile().getAbsolutePath();
            int cargados = servicioCSV.cargarProductos(ruta);
            mostrarMensaje("Productos cargados: " + cargados);
            actualizarComboSucursales();
            refrescarTabla();

            if (cargados > 0) {
                csvProducto.setEnabled(false);
            }
        }
    }//GEN-LAST:event_csvProductoActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        refrescarTabla();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarActionPerformed
        // Validar sucursal activa
        if (jComboBox1.getSelectedItem() == null) {
            mostrarMensaje("Error: No hay sucursal seleccionada.");
            return;
        }

        // Leer campos
        String nombre = inombre1.getText().trim();
        String codigo = icodigo.getText().trim();
        String categoria = icateg.getText().trim();
        String fecha = ifecha.getText().trim();
        String marca = imarca.getText().trim();
        String precioStr = iprecio.getText().trim();
        String stockStr = istock.getText().trim();

        // Validaciones básicas
        if (nombre.isEmpty() || codigo.isEmpty() || categoria.isEmpty()
                || fecha.isEmpty() || marca.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty()) {
            mostrarMensaje("Error: Todos los campos son obligatorios.");
            return;
        }

        double precio;
        int stock;
        try {
            precio = Double.parseDouble(precioStr);
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: Precio o stock no numéricos.");
            return;
        }

        if (precio < 0 || stock < 0) {
            mostrarMensaje("Error: Precio y stock deben ser positivos.");
            return;
        }

        // Crear producto
        Producto p = new Producto(nombre, codigo, categoria, fecha, marca, precio, stock);

        // Agregar usando controlador
        ResultadoOperacion res = controlador.agregarProducto(p);
        mostrarMensaje(res.getMensaje());

        if (res.isExitoso()) {
            // Limpiar formulario
            inombre1.setText("");
            icodigo.setText("");
            icateg.setText("");
            ifecha.setText("");
            imarca.setText("");
            iprecio.setText("");
            istock.setText("");
            refrescarTabla();
        }
    }//GEN-LAST:event_agregarActionPerformed

    private void deshacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deshacerActionPerformed
        Producto eliminado = controlador.deshacerUltimo();
        if (eliminado != null) {
            mostrarMensaje("Deshecha inserción de: " + eliminado.getNombre());
            refrescarTabla();
        } else {
            mostrarMensaje("No hay operación para deshacer.");
        }
    }//GEN-LAST:event_deshacerActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        if (jComboBox1.getSelectedItem() == null) {
            mostrarMensaje("Error: No hay sucursal seleccionada.");
            return;
        }

        javax.swing.table.DefaultTableModel modelo
                = (javax.swing.table.DefaultTableModel) tablaProductos.getModel();
        modelo.setRowCount(0);

        // Criterio seleccionado
        if (rnombre.isSelected()) {
            String nombre = valor.getText().trim();
            if (nombre.isEmpty()) {
                mostrarMensaje("Ingrese un nombre para buscar.");
                return;
            }
            Producto p = controlador.buscarPorNombre(nombre);
            if (p != null) {
                agregarProductoATabla(modelo, p);
            }
            mostrarMensaje(p != null ? "Encontrado." : "No encontrado.");

        } else if (rcodigo.isSelected()) {
            String codigo = valor.getText().trim();
            if (codigo.isEmpty()) {
                mostrarMensaje("Ingrese un código para buscar.");
                return;
            }
            Producto p = controlador.buscarPorCodigo(codigo);
            if (p != null) {
                agregarProductoATabla(modelo, p);
            }
            mostrarMensaje(p != null ? "Encontrado." : "No encontrado.");

        } else if (rcateg.isSelected()) {
            String categoria = valor.getText().trim();
            if (categoria.isEmpty()) {
                mostrarMensaje("Ingrese una categoría.");
                return;
            }
            ListaEnlazada lista = controlador.buscarPorCategoria(categoria);
            if (lista != null) {
                Producto[] arr = lista.listar();
                for (Producto p : arr) {
                    agregarProductoATabla(modelo, p);
                }
            }
            mostrarMensaje("Resultados: " + modelo.getRowCount());

        } else if (rRango.isSelected()) {
            String ini = desde.getText().trim();
            String fin = hasta.getText().trim();
            if (ini.isEmpty() || fin.isEmpty()) {
                mostrarMensaje("Ingrese ambas fechas.");
                return;
            }
            ListaEnlazada lista = controlador.buscarPorRangoFechas(ini, fin);
            if (lista != null) {
                Producto[] arr = lista.listar();
                for (Producto p : arr) {
                    agregarProductoATabla(modelo, p);
                }
            }
            mostrarMensaje("Resultados: " + modelo.getRowCount());
        }
    }//GEN-LAST:event_buscarActionPerformed

    private void limpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpiarActionPerformed
        valor.setText("");
        desde.setText("");
        hasta.setText("");
        refrescarTabla(); // vuelve a mostrar todos los productos
        mostrarMensaje("Búsqueda limpiada.");
    }//GEN-LAST:event_limpiarActionPerformed

    private void elimiarSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elimiarSelectActionPerformed
        int fila = tablaProductos.getSelectedRow();
        if (fila == -1) {
            mostrarMensaje("Seleccione un producto en la tabla.");
            return;
        }
        String codigo = (String) tablaProductos.getValueAt(fila, 1); // columna 1: código
        ResultadoOperacion res = controlador.eliminarProducto(codigo);
        mostrarMensaje(res.getMensaje());
        refrescarTabla();
    }//GEN-LAST:event_elimiarSelectActionPerformed

    private void verAvlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verAvlActionPerformed
        if (jComboBox1.getSelectedItem() == null) {
            mostrarMensaje("Seleccione una sucursal.");
            return;
        }
        int idSuc = Integer.parseInt(((String) jComboBox1.getSelectedItem()).split(" - ")[0]);
        controlador.setSucursalActiva(idSuc);
        Inventario inv = controlador.getInventarioActivo();
        if (inv != null) {
            VisualizadorGraphviz.mostrarAVL(inv.getAvl(), this);
        }
    }//GEN-LAST:event_verAvlActionPerformed

    private void verHashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verHashActionPerformed
        if (jComboBox1.getSelectedItem() == null) {
            return;
        }
        int idSuc = Integer.parseInt(((String) jComboBox1.getSelectedItem()).split(" - ")[0]);
        controlador.setSucursalActiva(idSuc);
        Inventario inv = controlador.getInventarioActivo();
        if (inv != null) {
            VisualizadorGraphviz.mostrarHash(inv.getHash(), this);
        }
    }//GEN-LAST:event_verHashActionPerformed

    private void verBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verBActionPerformed
        if (jComboBox1.getSelectedItem() == null) {
            return;
        }
        int idSuc = Integer.parseInt(((String) jComboBox1.getSelectedItem()).split(" - ")[0]);
        controlador.setSucursalActiva(idSuc);
        Inventario inv = controlador.getInventarioActivo();
        if (inv != null) {
            VisualizadorGraphviz.mostrarB(inv.getArbolB(), this);
        }
    }//GEN-LAST:event_verBActionPerformed

    private void verBplusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verBplusActionPerformed
        if (jComboBox1.getSelectedItem() == null) {
            return;
        }
        int idSuc = Integer.parseInt(((String) jComboBox1.getSelectedItem()).split(" - ")[0]);
        controlador.setSucursalActiva(idSuc);
        Inventario inv = controlador.getInventarioActivo();
        if (inv != null) {
            VisualizadorGraphviz.mostrarBPlus(inv.getArbolBPlus(), this);
        }
    }//GEN-LAST:event_verBplusActionPerformed

    private void btnCrearSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearSucursalActionPerformed
        panelSucursalesHelper.crearSucursal();
        actualizarComboSucursales(); // Para que el combo de Productos se refresque
    }//GEN-LAST:event_btnCrearSucursalActionPerformed

    private void btnActualizarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTablaActionPerformed
        panelSucursalesHelper.actualizarTabla();

    }//GEN-LAST:event_btnActualizarTablaActionPerformed

    private void btnModificarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarSucursalActionPerformed
        panelSucursalesHelper.modificarSucursal();
    }//GEN-LAST:event_btnModificarSucursalActionPerformed

    private void btnEliminarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarSucursalActionPerformed
        panelSucursalesHelper.eliminarSucursal();
        actualizarComboSucursales();
    }//GEN-LAST:event_btnEliminarSucursalActionPerformed

    private void btnLimpiarSucursalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarSucursalActionPerformed
        panelSucursalesHelper.limpiarSucursal();
    }//GEN-LAST:event_btnLimpiarSucursalActionPerformed

    private void btnCalcularRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularRutaActionPerformed
        panelRedHelper.calcularRuta();

        ///lblRutaResultado.setText("HOlaaaaaaa");

        JOptionPane.showMessageDialog(null, lblRutaResultado, "Ruta:", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnCalcularRutaActionPerformed

    private void rbnRutaTiempoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbnRutaTiempoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbnRutaTiempoActionPerformed

    private void btnAgregarConexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarConexionActionPerformed
        panelRedHelper.agregarConexion();
    }//GEN-LAST:event_btnAgregarConexionActionPerformed

    private void btnIniciarTransferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarTransferenciaActionPerformed
      panelTransferenciasHelper.iniciarTransferencia();
    }//GEN-LAST:event_btnIniciarTransferenciaActionPerformed

    private void btnProcesarPasoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProcesarPasoActionPerformed
        panelTransferenciasHelper.procesarSiguientePaso();
    }//GEN-LAST:event_btnProcesarPasoActionPerformed

    private void agregarProductoATabla(javax.swing.table.DefaultTableModel modelo, Producto p) {
        modelo.addRow(new Object[]{
            p.getNombre(), p.getCodigoBarra(), p.getCategoria(),
            p.getFechaCaducidad(), p.getMarca(), p.getPrecio(), p.getStock()
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregar;
    private javax.swing.JMenuItem avl;
    private javax.swing.JButton btnActualizarTabla;
    private javax.swing.JButton btnAgregarConexion;
    private javax.swing.JButton btnCalcularRuta;
    private javax.swing.JButton btnCrearSucursal;
    private javax.swing.JButton btnEliminarSucursal;
    private javax.swing.JButton btnIniciarTransferencia;
    private javax.swing.JButton btnLimpiarSucursal;
    private javax.swing.JButton btnModificarSucursal;
    private javax.swing.JButton btnProcesarPaso;
    private javax.swing.JMenuItem btree;
    private javax.swing.JMenuItem btreeplus;
    private javax.swing.JButton buscar;
    private javax.swing.JPanel busqueda;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JCheckBox chkBidireccional;
    private javax.swing.JComboBox<String> cmbDestinoConexion;
    private javax.swing.JComboBox<String> cmbDestinoRuta;
    private javax.swing.JComboBox<String> cmbOrigenConexion;
    private javax.swing.JComboBox<String> cmbOrigenRuta;
    private javax.swing.JComboBox<String> cmbProductoTransferencia;
    private javax.swing.JComboBox<String> cmbSucursalDestinoTransferencia;
    private javax.swing.JComboBox<String> cmbSucursalOrigenTransferencia;
    private javax.swing.JMenuItem csvConexiones;
    private javax.swing.JMenuItem csvProducto;
    private javax.swing.JMenuItem csvSucursal;
    private javax.swing.JTextField desde;
    private javax.swing.JButton deshacer;
    private javax.swing.JButton elimiarSelect;
    private javax.swing.JMenuItem grafo;
    private javax.swing.JMenuItem hash;
    private javax.swing.JTextField hasta;
    private javax.swing.JTextField icateg;
    private javax.swing.JTextField icodigo;
    private javax.swing.JTextField ifecha;
    private javax.swing.JTextField imarca;
    private javax.swing.JTextField inombre1;
    private javax.swing.JTextField iprecio;
    private javax.swing.JTextField istock;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JLabel lblETA;
    private javax.swing.JLabel lblEstadoProducto;
    private javax.swing.JLabel lblUltimoEvento;
    private javax.swing.JButton limpiar;
    private javax.swing.JLabel output;
    private javax.swing.JPanel pnlGrafoDibujo;
    private javax.swing.JRadioButton rRango;
    private javax.swing.JPanel rangos;
    private javax.swing.JRadioButton rbnRutaCosto;
    private javax.swing.JRadioButton rbnRutaTiempo;
    private javax.swing.JRadioButton rbnTransferenciaCosto;
    private javax.swing.JRadioButton rbnTransferenciaTiempo;
    private javax.swing.JRadioButton rcateg;
    private javax.swing.JRadioButton rcodigo;
    private javax.swing.JRadioButton rnombre;
    private javax.swing.JLabel sucursal;
    private javax.swing.JLabel sucursalSeleccionado;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTable tblSucursales;
    private javax.swing.JTextArea txtColaIngreso;
    private javax.swing.JTextArea txtColaPreparacion;
    private javax.swing.JTextArea txtColaSalida;
    private javax.swing.JTextField txtCostoConexion;
    private javax.swing.JTextField txtIdSucursal;
    private javax.swing.JTextField txtNombreSucursal;
    private javax.swing.JTextField txtTiempoConexion;
    private javax.swing.JTextField txtTiempoDespacho;
    private javax.swing.JTextField txtTiempoIngreso;
    private javax.swing.JTextField txtTiempoTraspaso;
    private javax.swing.JTextField txtUbicacion;
    private javax.swing.JLabel val;
    private javax.swing.JTextField valor;
    private javax.swing.JButton verAvl;
    private javax.swing.JButton verB;
    private javax.swing.JButton verBplus;
    private javax.swing.JButton verHash;
    // End of variables declaration//GEN-END:variables
}

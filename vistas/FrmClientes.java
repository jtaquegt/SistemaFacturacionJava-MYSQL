package vistas;

import dao.ClienteDAO;
import models.Cliente;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmClientes extends javax.swing.JFrame {
    ClienteDAO clienteDAO = new ClienteDAO();
    DefaultTableModel modeloTabla = new DefaultTableModel();
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmClientes.class.getName());

    /**
     * Creates new form FrmClientes
     */
    public FrmClientes() {
        initComponents();
        setLocationRelativeTo(null);
        cargarTablaClientes();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblNit = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        txtNit = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnReporte = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("GESTION DE CLIENTES");

        lblNit.setText("NIT:");
        lblNombre.setText("Nombre:");
        lblDireccion.setText("Dirección:");

        jButton1.setText("ATRAS");
        jButton1.addActionListener(evt -> {
            new FrmPrincipal().setVisible(true);
            this.dispose();
        });

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(evt -> guardarCliente());

        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(evt -> modificarCliente());

        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(evt -> buscarCliente());

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(evt -> eliminarCliente());

        btnReporte.setText("REPORTES");
        btnReporte.addActionListener(evt -> {
            new FrmReportes().setVisible(true);
            this.dispose();
        });

        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addActionListener(evt -> limpiarCampos());

        tblClientes.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "NIT", "Nombre", "Dirección"}
        ) {
            boolean[] canEdit = new boolean [] {false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tblClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblClientes);
        if (tblClientes.getColumnModel().getColumnCount() > 0) {
            tblClientes.getColumnModel().getColumn(0).setMinWidth(0);
            tblClientes.getColumnModel().getColumn(0).setMaxWidth(0); // Oculta ID
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNit)
                            .addComponent(lblNombre)
                            .addComponent(lblDireccion))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNit)
                            .addComponent(txtNombre)
                            .addComponent(txtDireccion))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificar)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnReporte)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar)))
                .addContainerGap())
            .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.CENTER)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNit)
                    .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDireccion)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnModificar)
                    .addComponent(btnBuscar)
                    .addComponent(btnEliminar)
                    .addComponent(btnReporte)
                    .addComponent(btnLimpiar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1)
        );

        pack();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new FrmClientes().setVisible(true));
    }

    private void guardarCliente() {
        String nit = txtNit.getText().trim();
        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();

        if (nit.isEmpty() || nombre.isEmpty() || direccion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente existente = clienteDAO.buscarPorNit(nit);
        if (existente != null) {
            JOptionPane.showMessageDialog(this, "El NIT ingresado ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente nuevo = new Cliente();
        nuevo.setNit(nit);
        nuevo.setNombre(nombre);
        nuevo.setDireccion(direccion);

        if (clienteDAO.agregarCliente(nuevo)) {
            JOptionPane.showMessageDialog(this, "Cliente agregado correctamente.");
            limpiarCampos();
            cargarTablaClientes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarCliente() {
        String nit = txtNit.getText().trim();
        String nombre = txtNombre.getText().trim();
        Cliente c = null;

        if (!nit.isEmpty()) {
            c = clienteDAO.buscarPorNit(nit);
        } else if (!nombre.isEmpty()) {
            c = clienteDAO.buscarPorNombre(nombre);
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese NIT o Nombre para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (c != null) {
            txtNit.setText(c.getNit());
            txtNombre.setText(c.getNombre());
            txtDireccion.setText(c.getDireccion());
            JOptionPane.showMessageDialog(this, "Cliente encontrado.");
        } else {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void modificarCliente() {
        int fila = tblClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCliente = (int) tblClientes.getValueAt(fila, 0);
        Cliente c = clienteDAO.buscarPorId(idCliente);

        String nit = txtNit.getText().trim();
        String nombre = txtNombre.getText().trim();
        String direccion = txtDireccion.getText().trim();

        Cliente existente = clienteDAO.buscarPorNit(nit);
        if (existente != null && existente.getIdCliente() != idCliente) {
            JOptionPane.showMessageDialog(this, "El NIT ya pertenece a otro cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        c.setNit(nit);
        c.setNombre(nombre);
        c.setDireccion(direccion);

        if (clienteDAO.actualizarCliente(c)) {
            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
            limpiarCampos();
            cargarTablaClientes();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar cliente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente() {
        int fila = tblClientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCliente = (int) tblClientes.getValueAt(fila, 0);
        Cliente c = clienteDAO.buscarPorId(idCliente);

        int confirm = JOptionPane.showConfirmDialog(this, "¿Desea eliminar el cliente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (clienteDAO.eliminarCliente(idCliente)) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                limpiarCampos();
                cargarTablaClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        txtNit.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        tblClientes.clearSelection();
        txtNit.requestFocus();
    }

    private void cargarTablaClientes() {
        modeloTabla = new DefaultTableModel(new String[]{"ID", "NIT", "Nombre", "Dirección"}, 0);
        List<Cliente> lista = clienteDAO.listarClientes();
        for (Cliente c : lista) {
            Object[] fila = {c.getIdCliente(), c.getNit(), c.getNombre(), c.getDireccion()};
            modeloTabla.addRow(fila);
        }
        tblClientes.setModel(modeloTabla);
        tblClientes.getColumnModel().getColumn(0).setMinWidth(0);
        tblClientes.getColumnModel().getColumn(0).setMaxWidth(0);
    }

    // Variables declaration
    private javax.swing.JButton btnBuscar, btnEliminar, btnGuardar, btnLimpiar, btnModificar, btnReporte, jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDireccion, lblNit, lblNombre, lblTitulo;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtDireccion, txtNit, txtNombre;
}

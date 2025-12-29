package vistas;

import dao.ProveedorDAO;
import models.Proveedor;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrmProveedores extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(FrmProveedores.class.getName());

    public FrmProveedores() {
        initComponents();
        setLocationRelativeTo(null);
        this.setResizable(false);
        cargarTablaProveedores();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblNit = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        txtNit = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        btnAtras = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProveedores = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("GESTIÓN DE PROVEEDORES");

        lblNit.setText("NIT:");
        lblNombre.setText("Nombre:");
        lblDireccion.setText("Dirección:");
        lblTelefono.setText("Teléfono:");

        btnAtras.setText("ATRÁS");
        btnAtras.addActionListener(evt -> btnAtrasActionPerformed());

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(evt -> btnGuardarActionPerformed());

        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(evt -> btnModificarActionPerformed());

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(evt -> btnEliminarActionPerformed());

        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addActionListener(evt -> limpiarCampos());

        btnReportes.setText("REPORTES");
        btnReportes.addActionListener(evt -> btnReportesActionPerformed());

        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(evt -> btnBuscarActionPerformed());

        tablaProveedores.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "NIT", "Nombre", "Dirección", "Teléfono"}
        ));
        jScrollPane1.setViewportView(tablaProveedores);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        // Layout simplificado (para no extender demasiado)
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(lblNit)
                                                        .addComponent(lblNombre)
                                                        .addComponent(lblDireccion)
                                                        .addComponent(lblTelefono))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txtNit)
                                                        .addComponent(txtNombre)
                                                        .addComponent(txtDireccion)
                                                        .addComponent(txtTelefono)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnGuardar)
                                                .addGap(10, 10, 10)
                                                .addComponent(btnBuscar)
                                                .addGap(10, 10, 10)
                                                .addComponent(btnModificar)
                                                .addGap(10, 10, 10)
                                                .addComponent(btnEliminar)
                                                .addGap(10, 10, 10)
                                                .addComponent(btnLimpiar)
                                                .addGap(10, 10, 10)
                                                .addComponent(btnReportes)
                                                .addGap(10, 10, 10)
                                                .addComponent(btnAtras)))
                                .addContainerGap())
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(10)
                                .addComponent(lblTitulo)
                                .addGap(10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNit)
                                        .addComponent(txtNit))
                                .addGap(5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblNombre)
                                        .addComponent(txtNombre))
                                .addGap(5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblDireccion)
                                        .addComponent(txtDireccion))
                                .addGap(5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblTelefono)
                                        .addComponent(txtTelefono))
                                .addGap(10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnGuardar)
                                        .addComponent(btnBuscar)
                                        .addComponent(btnModificar)
                                        .addComponent(btnEliminar)
                                        .addComponent(btnLimpiar)
                                        .addComponent(btnReportes)
                                        .addComponent(btnAtras))
                                .addGap(10)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10))
        );

        pack();
    }

    // --- ACCIONES DE BOTONES ---
    private void btnGuardarActionPerformed() {
        try {
            if (txtNit.getText().isEmpty() || txtNombre.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "NIT y Nombre son obligatorios.");
                return;
            }

            Proveedor p = new Proveedor();
            p.setNit(txtNit.getText().trim());
            p.setNombre(txtNombre.getText().trim());
            p.setDireccion(txtDireccion.getText().trim());
            p.setTelefono(txtTelefono.getText().trim());

            ProveedorDAO dao = new ProveedorDAO();
            if (dao.guardarProveedor(p)) {
                JOptionPane.showMessageDialog(this, "Proveedor guardado correctamente.");
                limpiarCampos();
                cargarTablaProveedores();
            } else {
                JOptionPane.showMessageDialog(this, "Error: NIT o nombre ya existen.");
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error guardando proveedor", ex);
            JOptionPane.showMessageDialog(this, "Error inesperado al guardar proveedor.");
        }
    }

    private void btnModificarActionPerformed() {
        try {
            int fila = tablaProveedores.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un proveedor de la tabla.");
                return;
            }

            Proveedor p = new Proveedor();
            p.setIdProveedor((int) tablaProveedores.getValueAt(fila, 0));
            p.setNit(txtNit.getText().trim());
            p.setNombre(txtNombre.getText().trim());
            p.setDireccion(txtDireccion.getText().trim());
            p.setTelefono(txtTelefono.getText().trim());

            ProveedorDAO dao = new ProveedorDAO();
            if (dao.modificarProveedor(p)) {
                JOptionPane.showMessageDialog(this, "Proveedor modificado correctamente.");
                limpiarCampos();
                cargarTablaProveedores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al modificar proveedor.");
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error modificando proveedor", ex);
            JOptionPane.showMessageDialog(this, "Error inesperado al modificar proveedor.");
        }
    }

    private void btnEliminarActionPerformed() {
        try {
            int fila = tablaProveedores.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un proveedor de la tabla.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Deseas eliminar este proveedor?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                int idProveedor = (int) tablaProveedores.getValueAt(fila, 0);
                ProveedorDAO dao = new ProveedorDAO();
                if (dao.eliminarProveedor(idProveedor)) {
                    JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente.");
                    limpiarCampos();
                    cargarTablaProveedores();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar proveedor.");
                }
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error eliminando proveedor", ex);
            JOptionPane.showMessageDialog(this, "Error inesperado al eliminar proveedor.");
        }
    }

    private void btnBuscarActionPerformed() {
        try {
            String nit = txtNit.getText().trim();
            String nombre = txtNombre.getText().trim();
            if (nit.isEmpty() && nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa NIT o Nombre para buscar.");
                return;
            }

            ProveedorDAO dao = new ProveedorDAO();
            Proveedor p = (!nit.isEmpty()) ? dao.buscarPorNit(nit) : dao.buscarPorNombre(nombre);

            if (p != null) {
                txtNit.setText(p.getNit());
                txtNombre.setText(p.getNombre());
                txtDireccion.setText(p.getDireccion());
                txtTelefono.setText(p.getTelefono());

                // Seleccionar en tabla
                for (int i = 0; i < tablaProveedores.getRowCount(); i++) {
                    if ((int) tablaProveedores.getValueAt(i, 0) == p.getIdProveedor()) {
                        tablaProveedores.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Proveedor no encontrado.");
                limpiarCampos();
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error buscando proveedor", ex);
            JOptionPane.showMessageDialog(this, "Error inesperado al buscar proveedor.");
        }
    }

    private void btnAtrasActionPerformed() {
        new FrmPrincipal().setVisible(true);
        this.dispose();
    }

    private void btnReportesActionPerformed() {
        new FrmReportes().setVisible(true);
        this.dispose();
    }

    // --- MÉTODOS AUXILIARES ---
    private void limpiarCampos() {
        txtNit.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
    }

    private void cargarTablaProveedores() {
        try {
            DefaultTableModel modelo = new DefaultTableModel(
                    new Object[]{"ID", "NIT", "Nombre", "Dirección", "Teléfono"}, 0
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            ProveedorDAO dao = new ProveedorDAO();
            List<Proveedor> lista = dao.listarProveedores();

            for (Proveedor p : lista) {
                modelo.addRow(new Object[]{
                        p.getIdProveedor(),
                        p.getNit(),
                        p.getNombre(),
                        p.getDireccion(),
                        p.getTelefono()
                });
            }

            tablaProveedores.setModel(modelo);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error cargando tabla proveedores", ex);
        }
    }

    // --- VARIABLES ---
    private javax.swing.JButton btnAtras, btnBuscar, btnEliminar, btnGuardar, btnLimpiar, btnModificar, btnReportes;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDireccion, lblNit, lblNombre, lblTelefono, lblTitulo;
    private javax.swing.JTable tablaProveedores;
    private javax.swing.JTextField txtDireccion, txtNit, txtNombre, txtTelefono;
}

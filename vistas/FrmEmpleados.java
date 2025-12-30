package vistas;

import dao.EmpleadoDAO;
import models.Empleado;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmEmpleados extends javax.swing.JFrame {
    EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    DefaultTableModel modeloTabla = new DefaultTableModel();
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmEmpleados.class.getName());

    public FrmEmpleados() {
        initComponents();
        setLocationRelativeTo(null);
        cargarTablaEmpleados();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblPuesto = new javax.swing.JLabel();
        lblSalario = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        btnAtras = new javax.swing.JButton();
        txtPuesto = new javax.swing.JTextField();
        txtSalario = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblEmpleados = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("GESTIÓN DE EMPLEADOS");

        lblCodigo.setText("Código:");
        lblNombre.setText("Nombre:");
        lblPuesto.setText("Puesto:");
        lblSalario.setText("Salario:");

        btnAtras.setText("ATRAS");
        btnAtras.addActionListener(evt -> {
            new FrmPrincipal().setVisible(true);
            this.dispose();
        });

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(evt -> guardarEmpleado());

        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(evt -> modificarEmpleado());

        btnEliminar.setText("ELIMINAR");
        btnEliminar.addActionListener(evt -> eliminarEmpleado());

        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(evt -> buscarEmpleado());

        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addActionListener(evt -> limpiarCampos());

        btnReportes.setText("REPORTES");
        btnReportes.addActionListener(evt -> {
            new FrmReportes().setVisible(true);
            this.dispose();
        });

        tblEmpleados.setModel(new DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Código", "Nombre", "Puesto", "Salario"}
        ) {
            boolean[] canEdit = {false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tblEmpleados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblEmpleados);
        if (tblEmpleados.getColumnModel().getColumnCount() > 0) {
            tblEmpleados.getColumnModel().getColumn(0).setMinWidth(0);
            tblEmpleados.getColumnModel().getColumn(0).setMaxWidth(0); // Oculta ID
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
                            .addComponent(lblCodigo)
                            .addComponent(lblNombre)
                            .addComponent(lblPuesto)
                            .addComponent(lblSalario))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCodigo)
                            .addComponent(txtNombre)
                            .addComponent(txtPuesto)
                            .addComponent(txtSalario))
                        .addGap(18, 18, 18)
                        .addComponent(btnAtras))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificar)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnReportes)
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
                    .addComponent(lblCodigo)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAtras))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPuesto)
                    .addComponent(txtPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSalario)
                    .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnModificar)
                    .addComponent(btnBuscar)
                    .addComponent(btnEliminar)
                    .addComponent(btnReportes)
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

        java.awt.EventQueue.invokeLater(() -> new FrmEmpleados().setVisible(true));
    }

    private void guardarEmpleado() {
        if (!validarCampos()) return;

        String codigo = txtCodigo.getText().trim();
        String nombre = txtNombre.getText().trim();
        String puesto = txtPuesto.getText().trim();
        double salario;

        try {
            salario = Double.parseDouble(txtSalario.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El salario debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (empleadoDAO.buscarPorCodigo(codigo) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe un empleado con este código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Empleado emp = new Empleado(codigo, nombre, puesto, salario);
        if (empleadoDAO.agregarEmpleado(emp)) {
            JOptionPane.showMessageDialog(this, "Empleado agregado correctamente.");
            limpiarCampos();
            cargarTablaEmpleados();
        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar empleado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarEmpleado() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del empleado a buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Empleado e = empleadoDAO.buscarPorCodigo(codigo);
        if (e != null) {
            txtNombre.setText(e.getNombre());
            txtPuesto.setText(e.getPuesto());
            txtSalario.setText(String.valueOf(e.getSalario()));
            JOptionPane.showMessageDialog(this, "Empleado encontrado.");
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró ningún empleado con ese código.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void modificarEmpleado() {
        if (!validarCampos()) return;

        String codigo = txtCodigo.getText().trim();
        Empleado e = empleadoDAO.buscarPorCodigo(codigo);
        if (e == null) {
            JOptionPane.showMessageDialog(this, "No existe un empleado con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        e.setNombre(txtNombre.getText().trim());
        e.setPuesto(txtPuesto.getText().trim());
        try {
            e.setSalario(Double.parseDouble(txtSalario.getText().trim()));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El salario debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (empleadoDAO.actualizarEmpleado(e)) {
            JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente.");
            limpiarCampos();
            cargarTablaEmpleados();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar empleado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarEmpleado() {
        int fila = tblEmpleados.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codigo = (String) tblEmpleados.getValueAt(fila, 1);
        Empleado e = empleadoDAO.buscarPorCodigo(codigo);
        if (e == null) {
            JOptionPane.showMessageDialog(this, "Empleado no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Desea eliminar este empleado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (empleadoDAO.eliminarEmpleado(e.getIdEmpleado())) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente.");
                limpiarCampos();
                cargarTablaEmpleados();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar empleado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPuesto.setText("");
        txtSalario.setText("");
        txtCodigo.requestFocus();
        tblEmpleados.clearSelection();
    }

    private void cargarTablaEmpleados() {
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Código", "Nombre", "Puesto", "Salario"}, 0);
        List<Empleado> lista = empleadoDAO.listarEmpleados();
        for (Empleado e : lista) {
            Object[] fila = {e.getIdEmpleado(), e.getCodigo(), e.getNombre(), e.getPuesto(), e.getSalario()};
            modeloTabla.addRow(fila);
        }
        tblEmpleados.setModel(modeloTabla);
        tblEmpleados.getColumnModel().getColumn(0).setMinWidth(0);
        tblEmpleados.getColumnModel().getColumn(0).setMaxWidth(0);
    }

    private boolean validarCampos() {
        if (txtCodigo.getText().trim().isEmpty() ||
            txtNombre.getText().trim().isEmpty() ||
            txtPuesto.getText().trim().isEmpty() ||
            txtSalario.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe llenar todos los campos.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    // Variables declaration
    private javax.swing.JButton btnAtras, btnBuscar, btnEliminar, btnGuardar, btnLimpiar, btnModificar, btnReportes;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo, lblNombre, lblPuesto, lblSalario, lblTitulo;
    private javax.swing.JTable tblEmpleados;
    private javax.swing.JTextField txtCodigo, txtNombre, txtPuesto, txtSalario;
}

package vistas;

import dao.ProveedorDAO;
import models.Proveedor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Ventana de gestión de proveedores.
 * Funcionalidad: Permite crear, leer, actualizar y eliminar proveedores.
 */
public class FrmProveedores extends JFrame {

    public FrmProveedores() {
        initComponents();  // Inicialización de componentes (NetBeans GUI)
        setLocationRelativeTo(null);
        setResizable(false);
        cargarTablaProveedores();  // Carga inicial de la tabla de proveedores
        setTitle("Gestión de Proveedores");
    }

    /**
     * Eventos y funcionalidades programadas manualmente
     */

    // Guardar nuevo proveedor
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        Proveedor p = new Proveedor();
        p.setNit(txtNit.getText());
        p.setNombre(txtNombre.getText());
        p.setDireccion(txtDireccion.getText());
        p.setTelefono(txtTelefono.getText());

        ProveedorDAO dao = new ProveedorDAO();
        if (dao.guardarProveedor(p)) {
            JOptionPane.showMessageDialog(this, "Proveedor guardado correctamente.");
            limpiarCampos();
            cargarTablaProveedores();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar proveedor. Verifica si el NIT o el nombre ya existen.");
        }
    }

    // Modificar proveedor seleccionado
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {
        int filaSeleccionada = tablaProveedores.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un proveedor de la tabla.");
            return;
        }

        Proveedor p = new Proveedor();
        p.setIdProveedor((int) tablaProveedores.getValueAt(filaSeleccionada, 0));
        p.setNit(txtNit.getText());
        p.setNombre(txtNombre.getText());
        p.setDireccion(txtDireccion.getText());
        p.setTelefono(txtTelefono.getText());

        ProveedorDAO dao = new ProveedorDAO();
        if (dao.modificarProveedor(p)) {
            JOptionPane.showMessageDialog(this, "Proveedor modificado correctamente.");
            limpiarCampos();
            cargarTablaProveedores();
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar proveedor. Verifica los datos.");
        }
    }

    // Eliminar proveedor seleccionado
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        int filaSeleccionada = tablaProveedores.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un proveedor de la tabla.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este proveedor?", 
                                                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int idProveedor = (int) tablaProveedores.getValueAt(filaSeleccionada, 0);
            ProveedorDAO dao = new ProveedorDAO();
            if (dao.eliminarProveedor(idProveedor)) {
                JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente.");
                limpiarCampos();
                cargarTablaProveedores();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar proveedor.");
            }
        }
    }

    // Buscar proveedor por NIT o nombre
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        String nit = txtNit.getText().trim();
        String nombre = txtNombre.getText().trim();

        ProveedorDAO dao = new ProveedorDAO();
        Proveedor p = null;

        if (!nit.isEmpty()) {
            p = dao.buscarPorNit(nit);
        } else if (!nombre.isEmpty()) {
            p = dao.buscarPorNombre(nombre);
        } else {
            JOptionPane.showMessageDialog(this, "Ingresa un NIT o nombre para buscar.");
            return;
        }

        if (p != null) {
            txtNit.setText(p.getNit());
            txtNombre.setText(p.getNombre());
            txtDireccion.setText(p.getDireccion());
            txtTelefono.setText(p.getTelefono());

            // Seleccionar la fila correspondiente en la tabla
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
    }

    // Limpiar campos del formulario
    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {
        limpiarCampos();
    }

    // Regresar al menú principal
    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {
        new FrmPrincipal().setVisible(true);
        this.dispose();
    }

    // Abrir ventana de reportes
    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {
        new FrmReportes().setVisible(true);
        this.dispose();
    }

    /**
     * Métodos auxiliares
     */
    private void limpiarCampos() {
        txtNit.setText("");
        txtNombre.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
    }

    private void cargarTablaProveedores() {
        DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "NIT", "Nombre", "Dirección", "Teléfono"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        ProveedorDAO dao = new ProveedorDAO();
        List<Proveedor> lista = dao.listarProveedores();

        for (Proveedor p : lista) {
            modelo.addRow(new Object[]{p.getIdProveedor(), p.getNit(), p.getNombre(), p.getDireccion(), p.getTelefono()});
        }

        tablaProveedores.setModel(modelo);
    }

    /**
     * Método principal para iniciar la ventana
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new FrmProveedores().setVisible(true));
    }
}

package vistas;

import javax.swing.*;

/**
 * Ventana principal del sistema.
 * Funcionalidad: Menú para acceder a los módulos del sistema.
 */
public class FrmPrincipal extends JFrame {

    public FrmPrincipal() {
        initComponents();         // Inicialización de componentes (NetBeans GUI)
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Tienda - Menú Principal");
    }

    /**
     * Fragmentos de código importantes: eventos de botones
     * para abrir las ventanas de cada módulo.
     */

    // Abrir módulo Artículos
    private void btnArticulosActionPerformed(java.awt.event.ActionEvent evt) {
        new FrmArticulos().setVisible(true);
        this.dispose();  // Cierra la ventana principal
    }

    // Abrir módulo Proveedores
    private void btnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {
        new FrmProveedores().setVisible(true);
        this.dispose();
    }

    // Abrir módulo Clientes
    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {
        new FrmClientes().setVisible(true);
        this.dispose();
    }

    // Abrir módulo Empleados
    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {
        new FrmEmpleados().setVisible(true);
        this.dispose();
    }

    // Abrir módulo Facturación
    private void btnFacturacionActionPerformed(java.awt.event.ActionEvent evt) {
        new FrmFacturacion().setVisible(true);
        this.dispose();
    }

    // Abrir módulo Reportes
    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {
        new FrmReportes().setVisible(true);
        this.dispose();
    }

    /**
     * Método principal para iniciar la aplicación
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new FrmPrincipal().setVisible(true));
    }
}

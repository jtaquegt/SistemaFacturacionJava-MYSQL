package vistas;

import dao.ArticuloDAO;
import models.Articulo;
import models.ProveedorItem;
import config.ConexionBD;
import javax.swing.*;
import java.util.List;

/**
 * Ventana de gestión de artículos.
 * Funcionalidad: Permite crear, leer, actualizar y eliminar artículos.
 * Cada artículo está vinculado a un proveedor.
 */
public class FrmArticulos extends JFrame {

    public FrmArticulos() {
        initComponents();  // Inicialización de componentes (NetBeans GUI)
        cargarProveedores();  // Carga proveedores desde la base de datos
        listarArticulos();    // Lista todos los artículos en la tabla
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Gestión de Artículos");
    }

    /**
     * Eventos y funcionalidades programadas manualmente
     */

    // Guardar nuevo artículo
    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        ArticuloDAO dao = new ArticuloDAO();
        Articulo articulo = new Articulo();

        // Validaciones de campos vacíos y formato numérico
        // Validación de duplicados por código y nombre
        // Asignación de datos al modelo
        // Guardado mediante DAO y actualización de la tabla
    }

    // Buscar artículo por código o nombre
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        ArticuloDAO dao = new ArticuloDAO();
        Articulo articulo = null;

        // Búsqueda condicional por código o nombre
        // Cargar datos encontrados en campos de formulario y seleccionar proveedor
    }

    // Modificar artículo existente
    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {
        ArticuloDAO dao = new ArticuloDAO();
        Articulo articulo = new Articulo();

        // Validación de campos y formato
        // Actualización de datos mediante DAO
        // Refresco de tabla y limpieza de campos
    }

    // Eliminar artículo
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        ArticuloDAO dao = new ArticuloDAO();

        // Validación de identificación (código o nombre)
        // Confirmación de eliminación
        // Eliminación mediante DAO y refresco de tabla
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
        // Limpia todos los campos del formulario
    }

    private void listarArticulos() {
        // Obtiene los artículos desde la base de datos y los carga en la tabla
    }

    private void cargarProveedores() {
        // Obtiene los proveedores desde la base de datos y los agrega al ComboBox
    }

    /**
     * Método principal para iniciar la ventana
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new FrmArticulos().setVisible(true));
    }
}

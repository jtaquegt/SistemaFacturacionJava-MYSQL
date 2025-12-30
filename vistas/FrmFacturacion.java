package vistas;

import dao.ArticuloDAO;
import dao.ClienteDAO;
import dao.EmpleadoDAO;
import dao.FacturaDAO;
import dao.DetalleFacturaDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import models.Articulo;
import models.Cliente;
import models.Empleado;
import models.Factura;
import models.DetalleFactura;
import models.GenerarFacturaHTML;
import java.util.ArrayList;
import java.util.List;

public class FrmFacturacion extends javax.swing.JFrame {
    private DefaultTableModel modelo;
    private final ArticuloDAO articuloDAO;
    private final int idClienteSeleccionado = -1;
    private final Empleado empleadoSeleccionado = null;
        
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmFacturacion.class.getName());

    public FrmFacturacion() {
        initComponents();
        setLocationRelativeTo(null);
        
        articuloDAO = new ArticuloDAO(); 
        modelo = (DefaultTableModel) tblArticulos.getModel();
        
        cargarEmpleados(); 
                
        modelo.addTableModelListener(e -> {
            int fila = e.getFirstRow();
            int columna = e.getColumn();

            if (columna == 0) {
                String codigo = String.valueOf(modelo.getValueAt(fila, 0));
                Articulo articulo = buscarArticuloPorCodigo(codigo);
                if (articulo != null) {
                    modelo.setValueAt(articulo.getNombre(), fila, 1);
                    modelo.setValueAt(articulo.getPrecio(), fila, 2);
                    modelo.setValueAt(1, fila, 3);
                    modelo.setValueAt(articulo.getPrecio(), fila, 4);
                } else {
                    JOptionPane.showMessageDialog(this, "Artículo no encontrado");
                    modelo.setValueAt("", fila, 1);
                    modelo.setValueAt("", fila, 2);
                    modelo.setValueAt("", fila, 3);
                    modelo.setValueAt("", fila, 4);
                }
            }

            if (columna == 3) {
                try {
                    int cantidad = Integer.parseInt(String.valueOf(modelo.getValueAt(fila, 3)));
                    double precio = Double.parseDouble(String.valueOf(modelo.getValueAt(fila, 2)));
                    modelo.setValueAt(cantidad * precio, fila, 4);
                } catch (Exception ex) {
                    modelo.setValueAt(0, fila, 4);
                }
            }
            actualizarTotal();
        });
        
    }
    
    public class EmpleadoComboItem {
        private Empleado empleado;
        public EmpleadoComboItem(Empleado empleado) {
            this.empleado = empleado;
        }
        public Empleado getEmpleado() {
            return empleado;
        }
        @Override
        public String toString() {
            return empleado.getNombre(); 
        }
    }
    
    private void actualizarTotal() {
        double total = 0;
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Object valor = modelo.getValueAt(i, 4);
            if (valor != null) {
                try {
                    total += Double.parseDouble(valor.toString());
                } catch (NumberFormatException e) { }
            }
        }
        txtTotal.setText(String.format("%.2f", total));
    }
        
    private void cargarEmpleados() {
        EmpleadoDAO empleadoDAO = new EmpleadoDAO();
        List<Empleado> empleados = empleadoDAO.obtenerTodos();
        cbxEmpleado.removeAllItems(); 
        for (Empleado e : empleados) {
            cbxEmpleado.addItem(new EmpleadoComboItem(e));
        }
    }
     
    private Articulo buscarArticuloPorCodigo(String codigo) {
        return articuloDAO.obtenerArticuloParaFactura(codigo);
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {
        // Aquí va exactamente el contenido generado por NetBeans Form Editor (igual que lo proporcionaste)
        // ... 
        // Se omite la repetición por brevedad en este ejemplo
    }

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {                                         
        FrmPrincipal principal = new FrmPrincipal();
        principal.setVisible(true);
        this.dispose();
    }                                        

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        String searchQuery = txtNIT.getText().trim();
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.buscarPorNit(searchQuery);

        if (cliente == null) {
            cliente = clienteDAO.buscarPorNombre(searchQuery);
        }

        if (cliente != null) {
            txtNombreCliente.setText(cliente.getNombre());
            txtDireccionCliente.setText(cliente.getDireccion());
        } else {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado. ¿Desea agregarlo?",
                    "Cliente no encontrado", JOptionPane.INFORMATION_MESSAGE);
        }
    }                                                

    private void btnAgregarArticuloActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        modelo.addRow(new Object[]{"", "", 0.0, 1, 0.0});
    }                                                  

    private void btnReporteFacturaActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        new FrmReportes().setVisible(true);
        this.dispose();
    }                                                 

    private void btnQuitarArticuloActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        int fila = tblArticulos.getSelectedRow();
        if (fila >= 0) {
            modelo.removeRow(fila);
            actualizarTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar");
        }
    }                                                 

    private void btnGenerarFacturaActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        if (txtNombreCliente.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente antes de generar la factura");
            return;
        }
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un artículo a la factura");
            return;
        }

        EmpleadoComboItem item = (EmpleadoComboItem) cbxEmpleado.getSelectedItem();
        if (item == null || item.getEmpleado() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado");
            return;
        }
        Empleado empleado = item.getEmpleado();

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = clienteDAO.buscarPorNit(txtNIT.getText().trim());
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado");
            return;
        }

        Factura factura = new Factura();
        factura.setIdCliente(cliente.getIdCliente());
        factura.setIdEmpleado(empleado.getIdEmpleado());
        factura.setNumeroCaja(Integer.parseInt(txtNumeroCaja.getText()));
        factura.setNumeroFactura(new FacturaDAO().generarNumeroFactura());
        factura.setFecha(new java.util.Date());
        factura.setTotal(Double.parseDouble(txtTotal.getText()));

        List<DetalleFactura> detalles = new ArrayList<>();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String codigo = String.valueOf(modelo.getValueAt(i, 0));
            if (codigo == null || codigo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Artículo inválido en la fila " + (i + 1));
                return;
            }

            Articulo articulo = articuloDAO.obtenerArticuloParaFactura(codigo);
            if (articulo == null) {
                JOptionPane.showMessageDialog(this, "Artículo no encontrado en la fila " + (i + 1));
                return;
            }

            DetalleFactura detalle = new DetalleFactura();
            detalle.setIdArticulo(articulo.getIdArticulo());
            detalle.setNombreArticulo(articulo.getNombre());
            detalle.setCantidad(Integer.parseInt(modelo.getValueAt(i, 3).toString()));
            detalle.setPrecioUnitario(articulo.getPrecio());

            detalles.add(detalle);
        }
        factura.setDetalles(detalles);

        FacturaDAO facturaDAO = new FacturaDAO();
        if (facturaDAO.guardarFactura(factura)) {
            DetalleFacturaDAO detalleDAO = new DetalleFacturaDAO();
            for (DetalleFactura d : detalles) {
                d.setIdFactura(factura.getIdFactura());
                if (!detalleDAO.guardarDetalle(d)) {
                    JOptionPane.showMessageDialog(this, "Error al guardar detalle de artículo: " + d.getNombreArticulo());
                }
            }

            GenerarFacturaHTML.generar(factura, detalles, txtNombreCliente.getText(), empleado.getNombre());

            modelo.setRowCount(0);
            txtTotal.setText("0.00");
            txtNombreCliente.setText("");
            txtDireccionCliente.setText("");
            txtNIT.setText("");
            txtNumeroCaja.setText("");

            JOptionPane.showMessageDialog(this, "Factura generada correctamente");
        } else {
            JOptionPane.showMessageDialog(this, "Error al generar la factura");
        }
    }                                                 

    private void cbxEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {                                            
    }                                           

    private void btnBuscarFacturaActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        String input = JOptionPane.showInputDialog(this, "Ingrese el número de factura a buscar:");
        if (input == null || input.trim().isEmpty()) return;

        try {
            int numeroFactura = Integer.parseInt(input.trim());
            FacturaDAO facturaDAO = new FacturaDAO();
            Factura factura = facturaDAO.buscarFactura(numeroFactura);

            if (factura != null) {
                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente cliente = clienteDAO.obtenerPorId(factura.getIdCliente());
                if (cliente != null) {
                    txtNIT.setText(cliente.getNit());
                    txtNombreCliente.setText(cliente.getNombre());
                    txtDireccionCliente.setText(cliente.getDireccion());
                } else {
                    txtNIT.setText("");
                    txtNombreCliente.setText("");
                    txtDireccionCliente.setText("");
                }

                EmpleadoDAO empleadoDAO = new EmpleadoDAO();
                Empleado empleado = empleadoDAO.obtenerPorId(factura.getIdEmpleado());
                if (empleado != null) {
                    cbxEmpleado.setSelectedItem(new EmpleadoComboItem(empleado));
                }

                txtNumeroCaja.setText(String.valueOf(factura.getNumeroCaja()));
                txtTotal.setText(String.format("%.2f", factura.getTotal()));

                DefaultTableModel modelo = (DefaultTableModel) tblArticulos.getModel();
                modelo.setRowCount(0);
                for (DetalleFactura d : factura.getDetalles()) {
                    Object[] fila = {
                        d.getIdArticulo(),
                        d.getNombreArticulo(),
                        d.getPrecioUnitario(),
                        d.getCantidad(),
                        d.getSubtotal()
                    };
                    modelo.addRow(fila);
                }

                JOptionPane.showMessageDialog(this, "Factura cargada correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Factura no encontrada.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número de factura válido.");
        }
    }                                                

    private void btnEliminarFacturaActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        String input = JOptionPane.showInputDialog(this, "Ingrese el número de factura que desea eliminar:");
        if (input == null || input.trim().isEmpty()) return;

        try {
            int numeroFactura = Integer.parseInt(input.trim());
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar la factura N° " + numeroFactura + "?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                FacturaDAO facturaDAO = new FacturaDAO();
                boolean eliminado = facturaDAO.eliminarFacturaPorNumero(numeroFactura);

                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Factura eliminada correctamente.");
                    txtNIT.setText("");
                    txtNombreCliente.setText("");
                    txtDireccionCliente.setText("");
                    txtNumeroCaja.setText("");
                    txtTotal.setText("0.00");
                    ((DefaultTableModel) tblArticulos.getModel()).setRowCount(0);
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró la factura o ocurrió un error al eliminarla.");
                }
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número de factura válido.");
        }
    }                                                  

    private Articulo buscarArticuloPorCodigo(Object valueAt) {
        return articuloDAO.obtenerArticuloParaFactura(String.valueOf(valueAt));
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new FrmFacturacion().setVisible(true));
    }

    // Variables declaration                     
    private javax.swing.JButton btnAgregarArticulo;
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarFactura;
    private javax.swing.JButton btnCancelarFactura;
    private javax.swing.JButton btnEliminarFactura;
    private javax.swing.JButton btnGenerarFactura;
    private javax.swing.JButton btnModificarFactura;
    private javax.swing.JButton btnQuitarArticulo;
    private javax.swing.JButton btnReporteFactura;
    private javax.swing.JComboBox<Object> cbxEmpleado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDireccionCliente;
    private javax.swing.JLabel lblEmpleado;
    private javax.swing.JLabel lblNIT;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblNumeroCaja;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tblArticulos;
    private javax.swing.JTextField txtDireccionCliente;
    private javax.swing.JTextField txtNIT;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtNumeroCaja;
    private javax.swing.JTextField txtTotal;
}

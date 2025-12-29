package vistas;

import dao.ProveedorDAO;
import models.Proveedor;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class FrmReportes extends JFrame {

    public FrmReportes() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void initComponents() {
        lblTitulo = new JLabel("REPORTES DE PROVEEDORES", SwingConstants.CENTER);
        btnGenerar = new JButton("GENERAR REPORTE");
        btnAtras = new JButton("ATRÁS");

        btnGenerar.addActionListener(evt -> generarReporte());
        btnAtras.addActionListener(evt -> volverPrincipal());

        setLayout(new BorderLayout());
        add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGenerar);
        panelBotones.add(btnAtras);
        add(panelBotones, BorderLayout.CENTER);

        setSize(400, 150);
    }

    private void generarReporte() {
        try {
            ProveedorDAO dao = new ProveedorDAO();
            List<Proveedor> lista = dao.listarProveedores();

            if (lista.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay proveedores para generar el reporte.");
                return;
            }

            String fileName = "Reporte_Proveedores.html";
            try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
                pw.println("<html><head><title>Reporte de Proveedores</title></head><body>");
                pw.println("<h2>Reporte de Proveedores</h2>");
                pw.println("<table border='1'>");
                pw.println("<tr><th>ID</th><th>NIT</th><th>Nombre</th><th>Dirección</th><th>Teléfono</th></tr>");

                for (Proveedor p : lista) {
                    pw.printf("<tr><td>%d</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>%n",
                            p.getIdProveedor(), p.getNit(), p.getNombre(), p.getDireccion(), p.getTelefono());
                }

                pw.println("</table></body></html>");
            }

            JOptionPane.showMessageDialog(this, "Reporte generado: " + fileName);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generando reporte.");
            ex.printStackTrace();
        }
    }

    private void volverPrincipal() {
        new FrmPrincipal().setVisible(true);
        this.dispose();
    }

    // --- VARIABLES ---
    private JLabel lblTitulo;
    private JButton btnGenerar, btnAtras;
}

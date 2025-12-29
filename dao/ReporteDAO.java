package dao;

import config.ConexionBD;
import java.io.*;
import java.sql.*;

public class ReporteDAO {

    private void generarReporte(String titulo, String sql, String archivo) {
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
             PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            pw.println("<html><head><meta charset='UTF-8'><title>" + titulo + "</title></head><body>");
            pw.println("<h1>" + titulo + "</h1>");
            pw.println("<table border='1' cellspacing='0' cellpadding='5'><tr>");

            for (int i = 1; i <= columnas; i++) pw.println("<th>" + meta.getColumnName(i) + "</th>");
            pw.println("</tr>");

            while (rs.next()) {
                pw.println("<tr>");
                for (int i = 1; i <= columnas; i++) pw.println("<td>" + rs.getString(i) + "</td>");
                pw.println("</tr>");
            }

            pw.println("</table></body></html>");
            System.out.println("Reporte generado: " + archivo);

        } catch (Exception e) {
            System.out.println("Error al generar reporte " + titulo + ": " + e.getMessage());
        }
    }

    public void reporteArticulos() {
        generarReporte("Reporte de Artículos", "SELECT * FROM articulos", "Reporte_Articulos.html");
    }

    public void reporteProveedores() {
        generarReporte("Reporte de Proveedores", "SELECT * FROM proveedores", "Reporte_Proveedores.html");
    }

    public void reporteClientes() {
        generarReporte("Reporte de Clientes", "SELECT * FROM clientes", "Reporte_Clientes.html");
    }

    public void reporteEmpleados() {
        generarReporte("Reporte de Empleados", "SELECT * FROM empleados", "Reporte_Empleados.html");
    }

    public void reporteFacturas() {
        generarReporte("Reporte de Facturas", "SELECT * FROM facturas", "Reporte_Facturas.html");
    }
}

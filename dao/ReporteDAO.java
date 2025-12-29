package dao;

import config.ConexionBD;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.*;

/**
 * DAO para generar reportes HTML a partir de consultas SQL.
 * Permite crear reportes para artículos, proveedores, clientes, empleados y facturas.
 */
public class ReporteDAO {

    /**
     * Genera un reporte HTML a partir de un SQL y lo guarda en un archivo.
     * @param titulo Título del reporte
     * @param sql Consulta SQL
     * @param archivo Nombre del archivo HTML a generar
     */
    private void generarReporte(String titulo, String sql, String archivo) {
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql);
             PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnas = meta.getColumnCount();

            pw.println("<html><head><meta charset='UTF-8'><title>" + titulo + "</title>");
            pw.println("<style>table { border-collapse: collapse; width: 100%; }");
            pw.println("th, td { border: 1px solid #999; padding: 8px; text-align: center; }");
            pw.println("th { background-color: #f2f2f2; }</style></head><body>");

            pw.println("<h1>" + titulo + "</h1>");
            pw.println("<table><tr>");

            // Encabezados
            for (int i = 1; i <= columnas; i++) {
                pw.println("<th>" + meta.getColumnName(i) + "</th>");
            }
            pw.println("</tr>");

            // Filas de datos
            while (rs.next()) {
                pw.println("<tr>");
                for (int i = 1; i <= columnas; i++) {
                    pw.println("<td>" + rs.getString(i) + "</td>");
                }
                pw.println("</tr>");
            }

            pw.println("</table></body></html>");
            System.out.println("Reporte generado: " + archivo);

        } catch (Exception e) {
            System.err.println("Error al generar reporte '" + titulo + "': " + e.getMessage());
        }
    }

    /** Reporte de artículos */
    public void reporteArticulos() {
        generarReporte("Reporte de Artículos", "SELECT * FROM articulos", "Reporte_Articulos.html");
    }

    /** Reporte de proveedores */
    public void reporteProveedores() {
        generarReporte("Reporte de Proveedores", "SELECT * FROM proveedores", "Reporte_Proveedores.html");
    }

    /** Reporte de clientes */
    public void reporteClientes() {
        generarReporte("Reporte de Clientes", "SELECT * FROM clientes", "Reporte_Clientes.html");
    }

    /** Reporte de empleados */
    public void reporteEmpleados() {
        generarReporte("Reporte de Empleados", "SELECT * FROM empleados", "Reporte_Empleados.html");
    }

    /** Reporte de facturas */
    public void reporteFacturas() {
        generarReporte("Reporte de Facturas", "SELECT * FROM facturas", "Reporte_Facturas.html");
    }
}

package models;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;
import java.awt.Desktop;
import java.io.File;

/**
 * Clase responsable de generar facturas en formato HTML.
 * Permite crear un archivo HTML de la factura y abrirlo automáticamente en el navegador.
 */
public class GenerarFacturaHTML {

    /**
     * Genera un archivo HTML con la información de la factura y detalles.
     *
     * @param factura        Objeto Factura con información general
     * @param detalles       Lista de detalles de la factura (productos)
     * @param nombreCliente  Nombre del cliente
     * @param nombreEmpleado Nombre del empleado / vendedor
     */
    public static void generar(Factura factura,
                               List<DetalleFactura> detalles,
                               String nombreCliente,
                               String nombreEmpleado) {
        // Ruta del archivo HTML
        String ruta = "D:/progra 2/Proyecto Progra2/Facturas/factura_" 
                       + factura.getNumeroFactura() + ".html";

        // Try-with-resources asegura que FileWriter se cierre automáticamente
        try (FileWriter archivo = new FileWriter(ruta)) {

            // Estructura básica HTML y estilos
            archivo.write("<html>");
            archivo.write("<head>");
            archivo.write("<title>Factura #" + factura.getNumeroFactura() + "</title>");
            archivo.write("<style>");
            archivo.write("body { font-family: Arial; margin: 30px; }");
            archivo.write("h1, h2, h3 { color: #2E86C1; }");
            archivo.write("table { border-collapse: collapse; width: 100%; }");
            archivo.write("th, td { border: 1px solid #999; padding: 8px; text-align: center; }");
            archivo.write("th { background-color: #f2f2f2; }");
            archivo.write("</style>");
            archivo.write("</head>");
            archivo.write("<body>");

            archivo.write("<h1>Supertienda MAS</h1>");
            archivo.write("<h2>Factura #" + factura.getNumeroFactura() + "</h2>");
            archivo.write("<p><b>Fecha:</b> " + LocalDateTime.now() + "</p>");
            archivo.write("<p><b>Cliente:</b> " + nombreCliente + "</p>");
            archivo.write("<p><b>Vendedor:</b> " + nombreEmpleado + "</p>");
            archivo.write("<p><b>Caja:</b> " + factura.getNumeroCaja() + "</p>");
            archivo.write("<hr>");

            // Tabla de productos
            archivo.write("<table>");
            archivo.write("<tr><th>Artículo</th><th>Cantidad</th><th>Precio Unitario</th><th>Subtotal</th></tr>");
            for (DetalleFactura d : detalles) {
                archivo.write("<tr>");
                archivo.write("<td>" + d.getNombreArticulo() + "</td>");
                archivo.write("<td>" + d.getCantidad() + "</td>");
                archivo.write("<td>Q" + d.getPrecioUnitario() + "</td>");
                archivo.write("<td>Q" + d.getSubtotal() + "</td>");
                archivo.write("</tr>");
            }
            archivo.write("</table>");

            archivo.write("<h3>Total: Q" + factura.getTotal() + "</h3>");
            archivo.write("<hr>");
            archivo.write("<p>Gracias por su compra en <b>Supertienda MAS</b></p>");

            archivo.write("</body></html>");

            // Abrir automáticamente el archivo HTML en el navegador si es posible
            File file = new File(ruta);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(file.toURI());
            }

            System.out.println("Factura HTML generada correctamente: " + ruta);

        } catch (IOException e) {
            System.err.println("Error al generar la factura HTML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

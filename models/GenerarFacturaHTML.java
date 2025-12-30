package models;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase utilitaria para generar facturas en formato HTML.
 * Recibe una factura, sus detalles, el nombre del cliente y del empleado,
 * y genera un archivo HTML con formato visual amigable.
 */
public class GenerarFacturaHTML {

    /**
     * Genera un archivo HTML de la factura especificada.
     *
     * @param factura        Objeto Factura que contiene la información general
     * @param detalles       Lista de DetalleFactura con los artículos de la factura
     * @param nombreCliente  Nombre del cliente
     * @param nombreEmpleado Nombre del vendedor o empleado que atendió la venta
     */
    public static void generar(Factura factura,
                               List<DetalleFactura> detalles,
                               String nombreCliente,
                               String nombreEmpleado) {
        try {
            // Ruta donde se guardará el archivo HTML
            String ruta = "D:/progra 2/Proyecto Progra2/Facturas/factura_" + factura.getNumeroFactura() + ".html";
            FileWriter archivo = new FileWriter(ruta);

            // Formateador de fecha
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String fechaActual = LocalDateTime.now().format(formatter);

            // Inicio del documento HTML
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

            // Encabezado de la factura
            archivo.write("<h1>Supertienda MAS</h1>");
            archivo.write("<h2>Factura #" + factura.getNumeroFactura() + "</h2>");
            archivo.write("<p><b>Fecha:</b> " + fechaActual + "</p>");
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
                archivo.write("<td>Q" + String.format("%.2f", d.getPrecioUnitario()) + "</td>");
                archivo.write("<td>Q" + String.format("%.2f", d.getSubtotal()) + "</td>");
                archivo.write("</tr>");
            }
            archivo.write("</table>");

            // Total
            archivo.write("<h3>Total: Q" + String.format("%.2f", factura.getTotal()) + "</h3>");
            archivo.write("<hr>");
            archivo.write("<p>Gracias por su compra en <b>Supertienda MAS</b></p>");

            archivo.write("</body></html>");
            archivo.close();

            // Abrir automáticamente el archivo en el navegador si es soportado
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().browse(new java.io.File(ruta).toURI());
            }

            System.out.println("Factura HTML generada correctamente: " + ruta);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

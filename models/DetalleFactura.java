package models;

/**
 * Representa un detalle de factura, es decir, un artículo específico incluido en una factura.
 * Contiene información sobre el artículo, cantidad, precio unitario y subtotal calculado.
 */
public class DetalleFactura {

    // Identificador único del detalle
    private int idDetalle;

    // Identificador de la factura a la que pertenece este detalle
    private int idFactura;

    // Identificador del artículo
    private int idArticulo;

    // Nombre del artículo (para mostrar en interfaces o reportes)
    private String nombreArticulo;

    // Cantidad del artículo en la factura
    private int cantidad;

    // Precio unitario del artículo
    private double precioUnitario;

    // Subtotal calculado automáticamente
    private double subtotal;

    // Constructor vacío
    public DetalleFactura() {}

    /**
     * Constructor sin ID, usado al crear un detalle antes de guardar en la base de datos.
     *
     * @param idArticulo ID del artículo
     * @param nombreArticulo Nombre del artículo
     * @param cantidad Cantidad comprada
     * @param precioUnitario Precio por unidad
     */
    public DetalleFactura(int idArticulo, String nombreArticulo, int cantidad, double precioUnitario) {
        this.idArticulo = idArticulo;
        this.nombreArticulo = nombreArticulo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    /**
     * Constructor completo con ID, usado al obtener datos de la base de datos.
     *
     * @param idDetalle ID del detalle
     * @param idFactura ID de la factura
     * @param idArticulo ID del artículo
     * @param nombreArticulo Nombre del artículo
     * @param cantidad Cantidad comprada
     * @param precioUnitario Precio por unidad
     */
    public DetalleFactura(int idDetalle, int idFactura, int idArticulo, String nombreArticulo,
                          int cantidad, double precioUnitario) {
        this(idArticulo, nombreArticulo, cantidad, precioUnitario);
        this.idDetalle = idDetalle;
        this.idFactura = idFactura;
    }

    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(int idDetalle) { this.idDetalle = idDetalle; }

    public int getIdFactura() { return idFactura; }
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }

    public int getIdArticulo() { return idArticulo; }
    public void setIdArticulo(int idArticulo) { this.idArticulo = idArticulo; }

    public String getNombreArticulo() { return nombreArticulo; }
    public void setNombreArticulo(String nombreArticulo) { this.nombreArticulo = nombreArticulo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad; 
        calcularSubtotal(); // Actualiza subtotal automáticamente
    }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { 
        this.precioUnitario = precioUnitario; 
        calcularSubtotal(); // Actualiza subtotal automáticamente
    }

    public double getSubtotal() { return subtotal; }

    // Calcula el subtotal automáticamente como cantidad * precioUnitario
    private void calcularSubtotal() { this.subtotal = this.cantidad * this.precioUnitario; }

    @Override
    public String toString() {
        return String.format(
            "DetalleFactura{idDetalle=%d, idArticulo=%d, nombreArticulo='%s', cantidad=%d, precioUnitario=%.2f, subtotal=%.2f}",
            idDetalle, idArticulo, nombreArticulo, cantidad, precioUnitario, subtotal
        );
    }
}

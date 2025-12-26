package models;

public class DetalleFactura {

    private int idDetalle;
    private int idFactura;
    private int idArticulo;
    private String nombreArticulo;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    // Constructor vacío
    public DetalleFactura() {}

    // Constructor sin ID
    public DetalleFactura(int idArticulo, String nombreArticulo, int cantidad, double precioUnitario) {
        this.idArticulo = idArticulo;
        this.nombreArticulo = nombreArticulo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    // Constructor completo
    public DetalleFactura(int idDetalle, int idFactura, int idArticulo, String nombreArticulo,
                          int cantidad, double precioUnitario) {
        this(idArticulo, nombreArticulo, cantidad, precioUnitario);
        this.idDetalle = idDetalle;
        this.idFactura = idFactura;
    }

    // Getters y Setters
    public int getIdDetalle() { return idDetalle; }
    public void setIdDetalle(final int idDetalle) { this.idDetalle = idDetalle; }

    public int getIdFactura() { return idFactura; }
    public void setIdFactura(final int idFactura) { this.idFactura = idFactura; }

    public int getIdArticulo() { return idArticulo; }
    public void setIdArticulo(final int idArticulo) { this.idArticulo = idArticulo; }

    public String getNombreArticulo() { return nombreArticulo; }
    public void setNombreArticulo(final String nombreArticulo) { this.nombreArticulo = nombreArticulo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(final int cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(final double precioUnitario) {
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public double getSubtotal() { return subtotal; }

    // Cálculo interno del subtotal
    private void calcularSubtotal() {
        this.subtotal = this.cantidad * this.precioUnitario;
    }

    @Override
    public String toString() {
        return String.format(
            "DetalleFactura{idDetalle=%d, idArticulo=%d, nombreArticulo='%s', cantidad=%d, precioUnitario=%.2f, subtotal=%.2f}",
            idDetalle, idArticulo, nombreArticulo, cantidad, precioUnitario, subtotal
        );
    }
}

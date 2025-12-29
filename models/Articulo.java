package models;

public class Articulo {

    private int idArticulo;
    private String codigo;
    private int idProveedor;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private double precio;

    public Articulo() {}

    public Articulo(String codigo, int idProveedor, String nombre, String descripcion, int cantidad, double precio) {
        this.codigo = codigo;
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public Articulo(int idArticulo, String codigo, int idProveedor, String nombre, String descripcion, int cantidad, double precio) {
        this(codigo, idProveedor, nombre, descripcion, cantidad, precio);
        this.idArticulo = idArticulo;
    }

    public int getIdArticulo() { return idArticulo; }
    public void setIdArticulo(int idArticulo) { this.idArticulo = idArticulo; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() {
        return String.format(
            "Articulo{idArticulo=%d, codigo='%s', idProveedor=%d, nombre='%s', descripcion='%s', cantidad=%d, precio=%.2f}",
            idArticulo, codigo, idProveedor, nombre, descripcion, cantidad, precio
        );
    }
}

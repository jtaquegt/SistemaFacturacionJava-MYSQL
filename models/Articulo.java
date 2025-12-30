package models;

/**
 * Clase que representa un artículo en el sistema.
 * Contiene información básica del artículo, como código, proveedor,
 * nombre, descripción, cantidad en stock y precio.
 * Se utiliza principalmente en operaciones de inventario y facturación.
 */
public class Articulo {

    // Identificador único del artículo
    private int idArticulo;

    // Código único del artículo, usado para búsquedas y referencias
    private String codigo;

    // ID del proveedor asociado al artículo
    private int idProveedor;

    // Nombre descriptivo del artículo
    private String nombre;

    // Descripción adicional del artículo
    private String descripcion;

    // Cantidad disponible en stock
    private int cantidad;

    // Precio unitario del artículo
    private double precio;

    /**
     * Constructor vacío para frameworks o librerías que requieran inicialización por defecto.
     */
    public Articulo() {}

    /**
     * Constructor para crear un artículo sin ID (por ejemplo, antes de persistir en BD).
     *
     * @param codigo      Código único del artículo
     * @param idProveedor ID del proveedor
     * @param nombre      Nombre del artículo
     * @param descripcion Descripción del artículo
     * @param cantidad    Cantidad en stock
     * @param precio      Precio unitario
     */
    public Articulo(String codigo, int idProveedor, String nombre, String descripcion, int cantidad, double precio) {
        this.codigo = codigo;
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    /**
     * Constructor completo incluyendo ID (usado al recuperar de la base de datos).
     *
     * @param idArticulo  ID del artículo
     * @param codigo      Código único del artículo
     * @param idProveedor ID del proveedor
     * @param nombre      Nombre del artículo
     * @param descripcion Descripción del artículo
     * @param cantidad    Cantidad en stock
     * @param precio      Precio unitario
     */
    public Articulo(int idArticulo, String codigo, int idProveedor, String nombre, String descripcion, int cantidad, double precio) {
        this(codigo, idProveedor, nombre, descripcion, cantidad, precio);
        this.idArticulo = idArticulo;
    }

    // Getters y setters
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

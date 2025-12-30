package models;

/**
 * Clase que representa un proveedor en el sistema.
 * Contiene información básica como NIT, nombre, dirección y teléfono.
 * Se utiliza principalmente en la gestión de inventario y facturación.
 */
public class Proveedor {

    // Identificador único del proveedor
    private int idProveedor;

    // Número de identificación tributaria del proveedor
    private String nit;

    // Nombre del proveedor
    private String nombre;

    // Dirección física del proveedor
    private String direccion;

    // Teléfono de contacto del proveedor
    private String telefono;

    /**
     * Constructor vacío para frameworks o librerías que requieran inicialización por defecto.
     */
    public Proveedor() {}

    /**
     * Constructor sin ID, usado al crear un nuevo proveedor antes de persistir en la BD.
     *
     * @param nit       Número de identificación tributaria
     * @param nombre    Nombre del proveedor
     * @param direccion Dirección del proveedor
     * @param telefono  Teléfono de contacto
     */
    public Proveedor(String nit, String nombre, String direccion, String telefono) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    /**
     * Constructor completo incluyendo ID, usado al recuperar de la base de datos.
     *
     * @param idProveedor ID del proveedor
     * @param nit         Número de identificación tributaria
     * @param nombre      Nombre del proveedor
     * @param direccion   Dirección del proveedor
     * @param telefono    Teléfono de contacto
     */
    public Proveedor(int idProveedor, String nit, String nombre, String direccion, String telefono) {
        this(nit, nombre, direccion, telefono);
        this.idProveedor = idProveedor;
    }

    // Getters y setters
    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return String.format(
            "Proveedor{idProveedor=%d, nit='%s', nombre='%s', direccion='%s', telefono='%s'}",
            idProveedor, nit, nombre, direccion, telefono
        );
    }
}

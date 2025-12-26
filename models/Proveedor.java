package models;

public class Proveedor {

    private int idProveedor;
    private String nit;
    private String nombre;
    private String direccion;
    private String telefono;

    // Constructor vacío
    public Proveedor() {}

    // Constructor sin ID (útil para inserts)
    public Proveedor(String nit, String nombre, String direccion, String telefono) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Constructor completo
    public Proveedor(int idProveedor, String nit, String nombre, String direccion, String telefono) {
        this(nit, nombre, direccion, telefono);
        this.idProveedor = idProveedor;
    }

    // Getters y Setters
    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(final int idProveedor) { this.idProveedor = idProveedor; }

    public String getNit() { return nit; }
    public void setNit(final String nit) { this.nit = nit; }

    public String getNombre() { return nombre; }
    public void setNombre(final String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(final String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(final String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return String.format(
            "Proveedor{idProveedor=%d, nit='%s', nombre='%s', direccion='%s', telefono='%s'}",
            idProveedor, nit, nombre, direccion, telefono
        );
    }
}

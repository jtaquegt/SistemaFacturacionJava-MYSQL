package models;

public class Proveedor {

    private int idProveedor;
    private String nit;
    private String nombre;
    private String direccion;
    private String telefono;

    public Proveedor() {}

    public Proveedor(String nit, String nombre, String direccion, String telefono) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public Proveedor(int idProveedor, String nit, String nombre, String direccion, String telefono) {
        this(nit, nombre, direccion, telefono);
        this.idProveedor = idProveedor;
    }

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

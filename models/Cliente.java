package models;

public class Cliente {

    private int idCliente;
    private String nit;
    private String nombre;
    private String direccion;

    public Cliente() {}

    public Cliente(String nit, String nombre, String direccion) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public Cliente(int idCliente, String nit, String nombre, String direccion) {
        this(nit, nombre, direccion);
        this.idCliente = idCliente;
    }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public String toString() {
        return String.format(
            "Cliente{idCliente=%d, nit='%s', nombre='%s', direccion='%s'}",
            idCliente, nit, nombre, direccion
        );
    }
}

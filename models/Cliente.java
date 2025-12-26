package models;

public class Cliente {

    // Atributos
    private int idCliente;
    private String nit;
    private String nombre;
    private String direccion;

    // Constructor vacío
    public Cliente() {}

    // Constructor sin ID (útil para inserciones)
    public Cliente(String nit, String nombre, String direccion) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    // Constructor completo
    public Cliente(int idCliente, String nit, String nombre, String direccion) {
        this(nit, nombre, direccion);
        this.idCliente = idCliente;
    }

    // Getters y Setters
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(final int idCliente) { this.idCliente = idCliente; }

    public String getNit() { return nit; }
    public void setNit(final String nit) { this.nit = nit; }

    public String getNombre() { return nombre; }
    public void setNombre(final String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(final String direccion) { this.direccion = direccion; }

    @Override
    public String toString() {
        return String.format(
            "Cliente{idCliente=%d, nit='%s', nombre='%s', direccion='%s'}",
            idCliente, nit, nombre, direccion
        );
    }
}

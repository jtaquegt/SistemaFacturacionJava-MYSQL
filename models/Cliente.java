package models;

/**
 * Clase que representa un cliente en el sistema.
 * Contiene información básica como NIT, nombre y dirección.
 * Se utiliza en la gestión de ventas y facturación.
 */
public class Cliente {

    // Identificador único del cliente
    private int idCliente;

    // Número de identificación tributaria del cliente
    private String nit;

    // Nombre completo del cliente
    private String nombre;

    // Dirección física del cliente
    private String direccion;

    /**
     * Constructor vacío para frameworks o librerías que requieran inicialización por defecto.
     */
    public Cliente() {}

    /**
     * Constructor sin ID, usado al crear un nuevo cliente antes de persistir en la BD.
     *
     * @param nit       Número de identificación tributaria
     * @param nombre    Nombre del cliente
     * @param direccion Dirección del cliente
     */
    public Cliente(String nit, String nombre, String direccion) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    /**
     * Constructor completo incluyendo ID, usado al recuperar de la base de datos.
     *
     * @param idCliente ID del cliente
     * @param nit       Número de identificación tributaria
     * @param nombre    Nombre del cliente
     * @param direccion Dirección del cliente
     */
    public Cliente(int idCliente, String nit, String nombre, String direccion) {
        this(nit, nombre, direccion);
        this.idCliente = idCliente;
    }

    // Getters y setters
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

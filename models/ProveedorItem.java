package models;

/**
 * Clase que representa un ítem de proveedor para ser usado en UI (ej. ComboBox).
 * Contiene el ID y el nombre del proveedor.
 */
public class ProveedorItem {

    private final int idProveedor;
    private final String nombre;

    /**
     * Constructor que inicializa el id y nombre del proveedor.
     * @param idProveedor Identificador único del proveedor
     * @param nombre Nombre del proveedor
     */
    public ProveedorItem(int idProveedor, String nombre) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    @Override
    public String toString() {
        // Esto es lo que se mostrará en el JComboBox (u otro componente UI)
        return nombre;
    }
}


package models;

/**
 * Clase auxiliar que representa un proveedor simplificado para ser usado en listas,
 * combos o selecciones en la interfaz gráfica.
 * Contiene únicamente el ID y el nombre, sobrescribiendo toString para mostrar el nombre.
 */
public class ProveedorItem {

    // Identificador único del proveedor
    private int idProveedor;

    // Nombre del proveedor
    private String nombre;

    /**
     * Constructor que inicializa el proveedor con ID y nombre.
     *
     * @param idProveedor ID del proveedor
     * @param nombre      Nombre del proveedor
     */
    public ProveedorItem(int idProveedor, String nombre) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
    }

    // Getter del ID del proveedor
    public int getIdProveedor() { return idProveedor; }

    /**
     * Se sobrescribe el método toString para que al mostrar el objeto en un JComboBox
     * o lista, se muestre únicamente el nombre del proveedor.
     *
     * @return Nombre del proveedor
     */
    @Override
    public String toString() {
        return nombre;
    }
}


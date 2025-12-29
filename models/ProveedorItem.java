package models;

public class ProveedorItem {

    private int idProveedor;
    private String nombre;

    public ProveedorItem(int idProveedor, String nombre) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
    }

    public int getIdProveedor() { return idProveedor; }

    @Override
    public String toString() {
        return nombre;
    }
}

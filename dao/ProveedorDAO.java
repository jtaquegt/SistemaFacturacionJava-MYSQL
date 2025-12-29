package dao;

import config.ConexionBD;
import models.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * DAO para gestionar proveedores en la base de datos.
 * Incluye métodos para crear, modificar, eliminar y buscar proveedores.
 */
public class ProveedorDAO {

    private final Connection conn;

    public ProveedorDAO() {
        conn = ConexionBD.getConexion();
    }

    /**
     * Guarda un nuevo proveedor.
     * @param p Objeto Proveedor
     * @return true si la operación fue exitosa
     */
    public boolean guardarProveedor(Proveedor p) {
        String sql = "INSERT INTO proveedores (nit, nombre, direccion, telefono) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNit());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getTelefono());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error guardando proveedor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Modifica un proveedor existente.
     * @param p Objeto Proveedor con datos actualizados
     * @return true si la operación fue exitosa
     */
    public boolean modificarProveedor(Proveedor p) {
        String sql = "UPDATE proveedores SET nit = ?, nombre = ?, direccion = ?, telefono = ? WHERE id_proveedor = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNit());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getTelefono());
            ps.setInt(5, p.getIdProveedor());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error modificando proveedor: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un proveedor por ID.
     * Maneja constraint de clave foránea si tiene artículos asociados.
     * @param id ID del proveedor
     * @return true si se eliminó correctamente
     */
    public boolean eliminarProveedor(int id) {
        String sql = "DELETE FROM proveedores WHERE id_proveedor = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            if (e.getMessage().contains("foreign key constraint")) {
                JOptionPane.showMessageDialog(null,
                        "No se puede eliminar el proveedor porque tiene artículos asociados.",
                        "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                System.err.println("Error eliminando proveedor: " + e.getMessage());
            }
            return false;
        }
    }

    /**
     * Busca un proveedor por su NIT.
     * @param nit NIT del proveedor
     * @return Objeto Proveedor o null si no se encontró
     */
    public Proveedor buscarPorNit(String nit) {
        String sql = "SELECT * FROM proveedores WHERE nit = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nit);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirProveedor(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error buscando proveedor: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca un proveedor por su nombre.
     * @param nombre Nombre del proveedor
     * @return Objeto Proveedor o null si no se encontró
     */
    public Proveedor buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM proveedores WHERE nombre = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return construirProveedor(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error buscando proveedor por nombre: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todos los proveedores.
     * @return Lista de objetos Proveedor
     */
    public List<Proveedor> listarProveedores() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(construirProveedor(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error listando proveedores: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Construye un objeto Proveedor a partir de un ResultSet.
     * @param rs ResultSet con datos de proveedor
     * @return Objeto Proveedor
     * @throws SQLException si ocurre un error de acceso
     */
    private Proveedor construirProveedor(ResultSet rs) throws SQLException {
        Proveedor p = new Proveedor();
        p.setIdProveedor(rs.getInt("id_proveedor"));
        p.setNit(rs.getString("nit"));
        p.setNombre(rs.getString("nombre"));
        p.setDireccion(rs.getString("direccion"));
        p.setTelefono(rs.getString("telefono"));
        return p;
    }
}

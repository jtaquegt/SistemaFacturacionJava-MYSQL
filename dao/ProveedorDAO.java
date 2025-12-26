package dao;

import config.ConexionBD;
import models.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProveedorDAO {

    private final Connection conn;

    public ProveedorDAO() {
        conn = ConexionBD.getConexion();
    }

    // Guardar proveedor
    public boolean guardarProveedor(Proveedor p) {
        String sql = "INSERT INTO proveedores (nit, nombre, direccion, telefono) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNit());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getTelefono());
            int resultado = ps.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            System.out.println("Error guardando proveedor: " + e.getMessage());
            return false;
        }
    }

    // Modificar proveedor
    public boolean modificarProveedor(Proveedor p) {
        String sql = "UPDATE proveedores SET nit = ?, nombre = ?, direccion = ?, telefono = ? WHERE id_proveedor = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNit());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getTelefono());
            ps.setInt(5, p.getIdProveedor());
            int resultado = ps.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            System.out.println("Error modificando proveedor: " + e.getMessage());
            return false;
        }
    }

    // Eliminar proveedor
    public boolean eliminarProveedor(int id) {
        String sql = "DELETE FROM proveedores WHERE id_proveedor = ?";
    try (Connection con = ConexionBD.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, id);
        int resultado = ps.executeUpdate();
        return resultado > 0;        
    }catch (SQLException e) {
        if (e.getMessage().contains("foreign key constraint")) {
            JOptionPane.showMessageDialog(null,
                "No se puede eliminar el proveedor porque tiene artículos asociados.",
                "Error", JOptionPane.WARNING_MESSAGE);
        } else {
            System.out.println("Error eliminando proveedor: " + e.getMessage());
        }
        return false;
    }
    }

    // Buscar proveedor por NIT
    public Proveedor buscarPorNit(String nit) {
        String sql = "SELECT * FROM proveedores WHERE nit = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nit);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return construirProveedor(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error buscando proveedor: " + e.getMessage());
        }
        return null;
    }

    // Buscar proveedor por nombre
    public Proveedor buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM proveedores WHERE nombre = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return construirProveedor(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error buscando proveedor por nombre: " + e.getMessage());
        }
        return null;
    }
    
    // Listar todos los proveedores
    public List<Proveedor> listarProveedores() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT * FROM proveedores";
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                lista.add(construirProveedor(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error listando proveedores: " + e.getMessage());
        }
        return lista;
    }

    // Método privado para construir un objeto Proveedor a partir del ResultSet
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

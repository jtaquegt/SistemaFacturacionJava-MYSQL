package dao;

import config.ConexionBD;
import models.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Crear nuevo cliente
    public boolean agregarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nit, nombre, direccion) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNit());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getDireccion());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al agregar cliente: " + e.getMessage());
            return false;
        }
    }

    // Eliminar cliente
    public boolean eliminarCliente(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    // Actualizar cliente
    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nit=?, nombre=?, direccion=? WHERE id_cliente=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNit());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getDireccion());
            ps.setInt(4, cliente.getIdCliente());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // Buscar por NIT
    public Cliente buscarPorNit(String nit) {
        String sql = "SELECT * FROM clientes WHERE nit=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nit);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return construirCliente(rs);

        } catch (SQLException e) {
            System.out.println("Error al buscar cliente por NIT: " + e.getMessage());
        }
        return null;
    }

    // Buscar por nombre (parcial)
    public Cliente buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM clientes WHERE nombre LIKE ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return construirCliente(rs);

        } catch (SQLException e) {
            System.out.println("Error al buscar cliente por nombre: " + e.getMessage());
        }
        return null;
    }

    // Listar todos los clientes
    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) lista.add(construirCliente(rs));

        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    // Obtener por ID
    public Cliente obtenerPorId(int idCliente) {
        String sql = "SELECT * FROM clientes WHERE id_cliente=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return construirCliente(rs);

        } catch (SQLException e) {
            System.out.println("Error al obtener cliente por ID: " + e.getMessage());
        }
        return null;
    }

    // Helper
    private Cliente construirCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setNit(rs.getString("nit"));
        c.setNombre(rs.getString("nombre"));
        c.setDireccion(rs.getString("direccion"));
        return c;
    }
}

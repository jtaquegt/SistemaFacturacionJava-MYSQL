package dao;

import config.ConexionBD;
import models.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la gestión de clientes en la base de datos.
 * Permite agregar, eliminar, actualizar, buscar y listar clientes.
 */
public class ClienteDAO {

    /**
     * Agrega un nuevo cliente a la base de datos.
     * @param cliente Cliente a agregar
     * @return true si se insertó correctamente, false en caso de error
     */
    public boolean agregarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nit, nombre, direccion) VALUES (?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNit());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getDireccion());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al agregar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un cliente de la base de datos por ID.
     * @param idCliente ID del cliente a eliminar
     * @return true si se eliminó correctamente, false en caso de error
     */
    public boolean eliminarCliente(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza los datos de un cliente existente.
     * @param cliente Cliente con los datos actualizados
     * @return true si se actualizó correctamente, false en caso de error
     */
    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nit = ?, nombre = ?, direccion = ? WHERE id_cliente = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNit());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getDireccion());
            ps.setInt(4, cliente.getIdCliente());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca un cliente por su NIT.
     * @param nit NIT del cliente
     * @return Cliente encontrado o null si no existe
     */
    public Cliente buscarPorNit(String nit) {
        String sql = "SELECT * FROM clientes WHERE nit = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nit);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por NIT: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca un cliente por nombre (parcial).
     * @param nombre Nombre del cliente
     * @return Primer cliente encontrado o null si no existe
     */
    public Cliente buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM clientes WHERE nombre LIKE ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por nombre: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todos los clientes en la base de datos.
     * @return Lista de clientes
     */
    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearCliente(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene un cliente por su ID.
     * @param idCliente ID del cliente
     * @return Cliente encontrado o null si no existe
     */
    public Cliente obtenerPorId(int idCliente) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener cliente por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Mapea un ResultSet a un objeto Cliente.
     * @param rs ResultSet con los datos del cliente
     * @return Cliente mapeado
     * @throws SQLException en caso de error de lectura
     */
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setNit(rs.getString("nit"));
        c.setNombre(rs.getString("nombre"));
        c.setDireccion(rs.getString("direccion"));
        return c;
    }
}

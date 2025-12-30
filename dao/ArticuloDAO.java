package dao;

import config.ConexionBD;
import models.Articulo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para la entidad Articulo.
 * Proporciona métodos para CRUD y gestión de stock.
 */
public class ArticuloDAO {

    /**
     * Inserta un nuevo artículo en la base de datos.
     *
     * @param articulo Objeto Articulo a agregar.
     * @return true si se insertó correctamente, false en caso de error.
     */
    public boolean agregar(Articulo articulo) {
        String sql = "INSERT INTO articulos (codigo, id_proveedor, nombre, descripcion, cantidad, precio) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, articulo.getCodigo());
            ps.setInt(2, articulo.getIdProveedor());
            ps.setString(3, articulo.getNombre());
            ps.setString(4, articulo.getDescripcion());
            ps.setInt(5, articulo.getCantidad());
            ps.setDouble(6, articulo.getPrecio());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al agregar artículo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza un artículo existente.
     */
    public boolean actualizar(Articulo articulo) {
        String sql = "UPDATE articulos SET id_proveedor=?, nombre=?, descripcion=?, cantidad=?, precio=? WHERE codigo=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, articulo.getIdProveedor());
            ps.setString(2, articulo.getNombre());
            ps.setString(3, articulo.getDescripcion());
            ps.setInt(4, articulo.getCantidad());
            ps.setDouble(5, articulo.getPrecio());
            ps.setString(6, articulo.getCodigo());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al actualizar artículo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un artículo por su ID.
     */
    public boolean eliminarPorId(int idArticulo) {
        String sql = "DELETE FROM articulos WHERE id_articulo=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idArticulo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar artículo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un artículo por su código.
     */
    public boolean eliminarPorCodigo(String codigo) {
        String sql = "DELETE FROM articulos WHERE codigo=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar artículo por código: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un artículo por su nombre.
     */
    public boolean eliminarPorNombre(String nombre) {
        String sql = "DELETE FROM articulos WHERE nombre=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar artículo por nombre: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca un artículo por su código.
     */
    public Articulo buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM articulos WHERE codigo=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return construirArticulo(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar artículo: " + e.getMessage());
        }
        return null;
    }

    /**
     * Busca un artículo por su nombre.
     */
    public Articulo buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM articulos WHERE nombre=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return construirArticulo(rs);
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar artículo por nombre: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todos los artículos de la base de datos.
     */
    public List<Articulo> listarTodos() {
        List<Articulo> lista = new ArrayList<>();
        String sql = "SELECT * FROM articulos";
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) lista.add(construirArticulo(rs));

        } catch (SQLException e) {
            System.err.println("Error al listar artículos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Descuenta cantidad del stock de un artículo.
     */
    public boolean descontarStock(int idArticulo, int cantidad) {
        String sql = "UPDATE articulos SET cantidad = cantidad - ? WHERE id_articulo=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cantidad);
            ps.setInt(2, idArticulo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al descontar stock: " + e.getMessage());
            return false;
        }
    }

    /**
     * Aumenta cantidad del stock de un artículo.
     */
    public boolean aumentarStock(int idArticulo, int cantidad) {
        String sql = "UPDATE articulos SET cantidad = cantidad + ? WHERE id_articulo=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cantidad);
            ps.setInt(2, idArticulo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al aumentar stock: " + e.getMessage());
            return false;
        }
    }

    /**
     * Construye un objeto Articulo a partir de un ResultSet.
     */
    private Articulo construirArticulo(ResultSet rs) throws SQLException {
        Articulo a = new Articulo();
        a.setIdArticulo(rs.getInt("id_articulo"));
        a.setCodigo(rs.getString("codigo"));
        a.setIdProveedor(rs.getInt("id_proveedor"));
        a.setNombre(rs.getString("nombre"));
        a.setDescripcion(rs.getString("descripcion"));
        a.setCantidad(rs.getInt("cantidad"));
        a.setPrecio(rs.getDouble("precio"));
        return a;
    }

    /**
     * Verifica si existe un proveedor por su ID.
     */
    public boolean existeProveedor(int idProveedor) {
        String sql = "SELECT id_proveedor FROM proveedores WHERE id_proveedor=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProveedor);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Error al validar proveedor: " + e.getMessage());
            return false;
        }
    }
}

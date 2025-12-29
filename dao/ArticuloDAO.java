package dao;

import config.ConexionBD;
import models.Articulo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDAO {

    // Insertar nuevo artículo
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
            System.out.println("Error al agregar artículo: " + e.getMessage());
            return false;
        }
    }

    // Actualizar artículo
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
            System.out.println("Error al actualizar artículo: " + e.getMessage());
            return false;
        }
    }

    // Eliminar artículo
    public boolean eliminarPorId(int idArticulo) {
        String sql = "DELETE FROM articulos WHERE id_articulo=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idArticulo);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar artículo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPorCodigo(String codigo) {
        String sql = "DELETE FROM articulos WHERE codigo=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar artículo por código: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarPorNombre(String nombre) {
        String sql = "DELETE FROM articulos WHERE nombre=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar artículo por nombre: " + e.getMessage());
            return false;
        }
    }

    // Buscar artículo
    public Articulo buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM articulos WHERE codigo=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return construirArticulo(rs);

        } catch (SQLException e) {
            System.out.println("Error al buscar artículo: " + e.getMessage());
        }
        return null;
    }

    public Articulo buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM articulos WHERE nombre=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return construirArticulo(rs);

        } catch (SQLException e) {
            System.out.println("Error al buscar artículo por nombre: " + e.getMessage());
        }
        return null;
    }

    // Listar todos
    public List<Articulo> listarTodos() {
        List<Articulo> lista = new ArrayList<>();
        String sql = "SELECT * FROM articulos";
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) lista.add(construirArticulo(rs));

        } catch (SQLException e) {
            System.out.println("Error al listar artículos: " + e.getMessage());
        }
        return lista;
    }

    // Stock
    public boolean descontarStock(int idArticulo, int cantidad) {
        String sql = "UPDATE articulos SET cantidad = cantidad - ? WHERE id_articulo=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idArticulo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al descontar stock: " + e.getMessage());
            return false;
        }
    }

    public boolean aumentarStock(int idArticulo, int cantidad) {
        String sql = "UPDATE articulos SET cantidad = cantidad + ? WHERE id_articulo=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idArticulo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al aumentar stock: " + e.getMessage());
            return false;
        }
    }

    // Helper
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

    public boolean existeProveedor(int idProveedor) {
        String sql = "SELECT id_proveedor FROM proveedores WHERE id_proveedor=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProveedor);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error al validar proveedor: " + e.getMessage());
            return false;
        }
    }
}

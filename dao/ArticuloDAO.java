package dao;

import config.ConexionBD;
import models.Articulo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la gestión de artículos en la base de datos.
 * Incluye métodos para crear, eliminar, actualizar y buscar artículos.
 */
public class ArticuloDAO {

    /**
     * Agrega un nuevo artículo a la base de datos.
     * @param articulo Objeto Articulo con todos los datos
     * @return true si la inserción fue exitosa
     */
    public boolean agregarArticulo(Articulo articulo) {
        String sql = "INSERT INTO articulos (codigo, id_proveedor, nombre, descripcion, cantidad, precio) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, articulo.getCodigo());
            ps.setInt(2, articulo.getIdProveedor());
            ps.setString(3, articulo.getNombre());
            ps.setString(4, articulo.getDescripcion());
            ps.setInt(5, articulo.getCantidad());
            ps.setDouble(6, articulo.getPrecio());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar artículo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un artículo por su ID.
     * @param idArticulo ID del artículo a eliminar
     * @return true si se eliminó al menos una fila
     */
    public boolean eliminarArticulo(int idArticulo) {
        String sql = "DELETE FROM articulos WHERE id_articulo = ?";
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
     * @param codigo Código del artículo
     * @return true si se eliminó al menos una fila
     */
    public boolean eliminarPorCodigo(String codigo) {
        String sql = "DELETE FROM articulos WHERE codigo = ?";
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
     * Actualiza los datos de un artículo.
     * @param articulo Articulo con datos actualizados
     * @return true si se actualizó al menos una fila
     */
    public boolean actualizarArticulo(Articulo articulo) {
        String sql = "UPDATE articulos SET id_proveedor=?, nombre=?, descripcion=?, cantidad=?, precio=? "
                   + "WHERE codigo=?";
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
     * Busca un artículo por su código completo.
     * @param codigo Código del artículo
     * @return Objeto Articulo o null si no se encontró
     */
    public Articulo buscarArticuloPorCodigo(String codigo) {
        String sql = "SELECT * FROM articulos WHERE codigo = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar artículo por código: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todos los artículos de la base de datos.
     * @return Lista de Articulo
     */
    public List<Articulo> listarArticulos() {
        List<Articulo> lista = new ArrayList<>();
        String sql = "SELECT * FROM articulos";

        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Articulo a = new Articulo();
                a.setIdArticulo(rs.getInt("id_articulo"));
                a.setCodigo(rs.getString("codigo"));
                a.setIdProveedor(rs.getInt("id_proveedor"));
                a.setNombre(rs.getString("nombre"));
                a.setDescripcion(rs.getString("descripcion"));
                a.setCantidad(rs.getInt("cantidad"));
                a.setPrecio(rs.getDouble("precio"));
                lista.add(a);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar artículos: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Verifica si existe un proveedor por su ID.
     * @param idProveedor ID del proveedor
     * @return true si existe
     */
    public boolean existeProveedor(int idProveedor) {
        String sql = "SELECT id_proveedor FROM proveedores WHERE id_proveedor = ?";
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

    /**
     * Descuenta stock de un artículo.
     * @param idArticulo ID del artículo
     * @param cantidad Cantidad a descontar
     * @return true si se actualizó correctamente
     */
    public boolean descontarStock(int idArticulo, int cantidad) {
        String sql = "UPDATE articulos SET cantidad = cantidad - ? WHERE id_articulo = ?";
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
     * Aumenta el stock de un artículo.
     * @param idArticulo ID del artículo
     * @param cantidad Cantidad a sumar
     * @return true si se actualizó correctamente
     */
    public boolean aumentarStock(int idArticulo, int cantidad) {
        String sql = "UPDATE articulos SET cantidad = cantidad + ? WHERE id_articulo = ?";
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
}

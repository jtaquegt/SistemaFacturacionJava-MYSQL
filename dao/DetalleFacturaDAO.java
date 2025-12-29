package dao;

import config.ConexionBD;
import models.DetalleFactura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la gestión de los detalles de factura.
 * Permite guardar detalles, reintegrar stock y listar detalles de una factura.
 */
public class DetalleFacturaDAO {

    private final ArticuloDAO articuloDAO = new ArticuloDAO();

    /**
     * Guarda un detalle de factura y descuenta el stock del artículo.
     * @param detalle DetalleFactura a guardar
     * @return true si se guardó correctamente, false en caso de error
     */
    public boolean guardarDetalle(DetalleFactura detalle) {
        String sql = "INSERT INTO detalle_factura(id_factura, id_articulo, cantidad, precio_unitario, subtotal) VALUES(?,?,?,?,?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, detalle.getIdFactura());
            ps.setInt(2, detalle.getIdArticulo());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setDouble(5, detalle.getSubtotal());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                articuloDAO.descontarStock(detalle.getIdArticulo(), detalle.getCantidad());
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar detalle: " + e.getMessage());
        }
        return false;
    }

    /**
     * Reintegra el stock de los artículos de una factura y elimina los detalles.
     * @param idFactura ID de la factura
     * @return true si se reintegró correctamente, false en caso de error
     */
    public boolean reintegrarStock(int idFactura) {
        String sql = "SELECT id_articulo, cantidad FROM detalle_factura WHERE id_factura=?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFactura);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idArticulo = rs.getInt("id_articulo");
                    int cantidad = rs.getInt("cantidad");
                    articuloDAO.aumentarStock(idArticulo, cantidad);
                }
            }

            // Eliminar detalles de la factura
            String sqlDel = "DELETE FROM detalle_factura WHERE id_factura=?";
            try (PreparedStatement psDel = con.prepareStatement(sqlDel)) {
                psDel.setInt(1, idFactura);
                psDel.executeUpdate();
            }

            return true;

        } catch (SQLException e) {
            System.err.println("Error al reintegrar stock: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista los detalles de una factura.
     * @param idFactura ID de la factura
     * @return Lista de DetalleFactura
     */
    public List<DetalleFactura> listarDetalles(int idFactura) {
        String sql = "SELECT * FROM detalle_factura WHERE id_factura=?";
        List<DetalleFactura> lista = new ArrayList<>();

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFactura);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearDetalle(rs, false));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al listar detalles: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Lista los detalles de una factura incluyendo el nombre del artículo.
     * @param idFactura ID de la factura
     * @return Lista de DetalleFactura con nombre de artículo
     */
    public List<DetalleFactura> listarDetallesConArticulo(int idFactura) {
        String sql = "SELECT d.id_detalle, d.id_factura, d.id_articulo, a.nombre AS nombre_articulo, " +
                     "d.cantidad, d.precio_unitario, d.subtotal " +
                     "FROM detalle_factura d " +
                     "JOIN articulos a ON d.id_articulo = a.id_articulo " +
                     "WHERE d.id_factura=?";
        List<DetalleFactura> lista = new ArrayList<>();

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFactura);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearDetalle(rs, true));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al listar detalles con artículo: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Mapea un ResultSet a un objeto DetalleFactura.
     * @param rs ResultSet
     * @param incluirNombreArticulo true si se debe incluir el nombre del artículo
     * @return DetalleFactura mapeado
     * @throws SQLException en caso de error
     */
    private DetalleFactura mapearDetalle(ResultSet rs, boolean incluirNombreArticulo) throws SQLException {
        DetalleFactura d = new DetalleFactura();
        d.setIdDetalle(rs.getInt("id_detalle"));
        d.setIdFactura(rs.getInt("id_factura"));
        d.setIdArticulo(rs.getInt("id_articulo"));
        d.setCantidad(rs.getInt("cantidad"));
        d.setPrecioUnitario(rs.getDouble("precio_unitario"));
        d.setSubtotal(rs.getDouble("subtotal"));
        if (incluirNombreArticulo) {
            d.setNombreArticulo(rs.getString("nombre_articulo"));
        }
        return d;
    }
}

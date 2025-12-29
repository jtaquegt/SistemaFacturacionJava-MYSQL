package dao;

import config.ConexionBD;
import models.DetalleFactura;
import java.sql.*;
import java.util.ArrayList;

public class DetalleFacturaDAO {

    // Guardar detalle
    public boolean guardarDetalle(DetalleFactura detalle) {
        String sql = "INSERT INTO detalle_factura(id_factura, id_articulo, cantidad, precio_unitario, subtotal) VALUES(?,?,?,?,?)";
        ArticuloDAO articuloDAO = new ArticuloDAO();
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, detalle.getIdFactura());
            ps.setInt(2, detalle.getIdArticulo());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setDouble(5, detalle.getSubtotal());

            if (ps.executeUpdate() > 0) {
                articuloDAO.descontarStock(detalle.getIdArticulo(), detalle.getCantidad());
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar detalle: " + e.getMessage());
        }
        return false;
    }

    // Reintegrar stock al cancelar factura
    public boolean reintegrarStock(int idFactura) {
        String sql = "SELECT id_articulo, cantidad FROM detalle_factura WHERE id_factura=?";
        ArticuloDAO articuloDAO = new ArticuloDAO();
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFactura);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                articuloDAO.aumentarStock(rs.getInt("id_articulo"), rs.getInt("cantidad"));
            }

            String sqlDel = "DELETE FROM detalle_factura WHERE id_factura=?";
            try (PreparedStatement psDel = con.prepareStatement(sqlDel)) {
                psDel.setInt(1, idFactura);
                psDel.executeUpdate();
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Error al reintegrar stock: " + e.getMessage());
            return false;
        }
    }

    // Listar detalles
    public ArrayList<DetalleFactura> listarDetalles(int idFactura) {
        ArrayList<DetalleFactura> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle_factura WHERE id_factura=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFactura);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(construirDetalle(rs));

        } catch (SQLException e) {
            System.out.println("Error al listar detalles: " + e.getMessage());
        }
        return lista;
    }

    // Listar detalles con nombre del artículo
    public ArrayList<DetalleFactura> listarDetallesConArticulo(int idFactura) {
        ArrayList<DetalleFactura> lista = new ArrayList<>();
        String sql = "SELECT d.id_detalle, d.id_factura, d.id_articulo, a.nombre AS nombre_articulo, " +
                     "d.cantidad, d.precio_unitario, d.subtotal " +
                     "FROM detalle_factura d JOIN articulos a ON d.id_articulo = a.id_articulo " +
                     "WHERE d.id_factura=?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFactura);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) lista.add(construirDetalleConArticulo(rs));

        } catch (SQLException e) {
            System.out.println("Error al listar detalles con artículo: " + e.getMessage());
        }
        return lista;
    }

    private DetalleFactura construirDetalle(ResultSet rs) throws SQLException {
        DetalleFactura d = new DetalleFactura();
        d.setIdDetalle(rs.getInt("id_detalle"));
        d.setIdFactura(rs.getInt("id_factura"));
        d.setIdArticulo(rs.getInt("id_articulo"));
        d.setCantidad(rs.getInt("cantidad"));
        d.setPrecioUnitario(rs.getDouble("precio_unitario"));
        d.setSubtotal(rs.getDouble("subtotal"));
        return d;
    }

    private DetalleFactura construirDetalleConArticulo(ResultSet rs) throws SQLException {
        DetalleFactura d = construirDetalle(rs);
        d.setNombreArticulo(rs.getString("nombre_articulo"));
        return d;
    }
}

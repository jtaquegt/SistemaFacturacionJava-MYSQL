package dao;

import config.ConexionBD;
import models.DetalleFactura;
import models.Articulo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar los detalles de las facturas.
 * Permite agregar, listar, eliminar y reintegrar stock de artículos.
 */
public class DetalleFacturaDAO {

    /**
     * Agrega un detalle a una factura.
     * @param detalle Objeto DetalleFactura
     * @return true si se agregó correctamente
     */
    public boolean agregarDetalle(DetalleFactura detalle) {
        String sql = "INSERT INTO detalle_factura (id_factura, id_articulo, cantidad, precio) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, detalle.getIdFactura());
            ps.setInt(2, detalle.getIdArticulo());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecio());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al agregar detalle de factura: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista los detalles de una factura junto con información del artículo.
     * @param idFactura ID de la factura
     * @return Lista de DetalleFactura
     */
    public List<DetalleFactura> listarDetallesConArticulo(int idFactura) {
        List<DetalleFactura> lista = new ArrayList<>();
        String sql = "SELECT df.*, a.nombre AS nombre_articulo " +
                     "FROM detalle_factura df " +
                     "JOIN articulos a ON df.id_articulo = a.id_articulo " +
                     "WHERE df.id_factura = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFactura);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetalleFactura df = new DetalleFactura();
                    df.setIdDetalle(rs.getInt("id_detalle"));
                    df.setIdFactura(rs.getInt("id_factura"));
                    df.setIdArticulo(rs.getInt("id_articulo"));
                    df.setCantidad(rs.getInt("cantidad"));
                    df.setPrecio(rs.getDouble("precio"));
                    df.setNombreArticulo(rs.getString("nombre_articulo"));
                    lista.add(df);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar detalles de factura: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Reintegra el stock de los artículos de una factura cancelada.
     * @param idFactura ID de la factura
     * @return true si el stock se reintegró correctamente
     */
    public boolean reintegrarStock(int idFactura) {
        ArticuloDAO articuloDAO = new ArticuloDAO();
        List<DetalleFactura> detalles = listarDetallesConArticulo(idFactura);
        boolean exito = true;

        for (DetalleFactura detalle : detalles) {
            boolean resultado = articuloDAO.aumentarStock(detalle.getIdArticulo(), detalle.getCantidad());
            if (!resultado) exito = false;
        }
        return exito;
    }

    /**
     * Elimina todos los detalles de una factura.
     * @param idFactura ID de la factura
     * @return true si se eliminaron correctamente
     */
    public boolean eliminarDetalles(int idFactura) {
        String sql = "DELETE FROM detalle_factura WHERE id_factura = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFactura);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error al eliminar detalles de factura: " + e.getMessage());
            return false;
        }
    }
}

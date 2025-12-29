package dao;

import config.ConexionBD;
import models.Factura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la gestión de facturas.
 * Permite generar números correlativos, guardar, cancelar, listar, buscar y eliminar facturas.
 */
public class FacturaDAO {

    private final DetalleFacturaDAO detalleDAO = new DetalleFacturaDAO();

    /**
     * Genera el siguiente número de factura correlativo.
     * @return siguiente número de factura
     */
    public int generarNumeroFactura() {
        int numero = 1;
        String sql = "SELECT MAX(numero_factura) AS max_num FROM facturas";

        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                numero = rs.getInt("max_num") + 1;
            }

        } catch (SQLException e) {
            System.err.println("Error al generar número de factura: " + e.getMessage());
        }

        return numero;
    }

    /**
     * Guarda una factura en la base de datos.
     * @param factura Factura a guardar
     * @return true si se guardó correctamente, false en caso de error
     */
    public boolean guardarFactura(Factura factura) {
        String sql = "INSERT INTO facturas(numero_factura, id_cliente, id_empleado, total, numero_caja) VALUES(?,?,?,?,?)";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, factura.getNumeroFactura());
            ps.setInt(2, factura.getIdCliente());
            ps.setInt(3, factura.getIdEmpleado());
            ps.setDouble(4, factura.getTotal());
            ps.setInt(5, factura.getNumeroCaja());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        factura.setIdFactura(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al guardar factura: " + e.getMessage());
        }

        return false;
    }

    /**
     * Cancela una factura, reintegra stock y elimina los registros.
     * @param idFactura ID de la factura
     * @return true si se canceló correctamente
     */
    public boolean cancelarFactura(int idFactura) {
        if (detalleDAO.reintegrarStock(idFactura)) {
            String sql = "DELETE FROM facturas WHERE id_factura=?";
            try (Connection con = ConexionBD.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, idFactura);
                ps.executeUpdate();
                return true;

            } catch (SQLException e) {
                System.err.println("Error al cancelar factura: " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * Lista todas las facturas.
     * @return lista de Factura
     */
    public List<Factura> listarFacturas() {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM facturas";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearFactura(rs, false));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar facturas: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Busca una factura por su número de factura.
     * @param numeroFactura número de factura
     * @return Factura encontrada o null si no existe
     */
    public Factura buscarFactura(int numeroFactura) {
        String sql = "SELECT * FROM facturas WHERE numero_factura=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numeroFactura);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Factura f = mapearFactura(rs, true);
                    f.setDetalles(detalleDAO.listarDetallesConArticulo(f.getIdFactura()));
                    return f;
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar factura: " + e.getMessage());
        }

        return null;
    }

    /**
     * Elimina una factura y sus detalles por número de factura.
     * @param numeroFactura número de factura
     * @return true si se eliminó correctamente
     */
    public boolean eliminarFacturaPorNumero(int numeroFactura) {
        Integer idFactura = obtenerIdFactura(numeroFactura);
        if (idFactura == null) return false;

        // Eliminar detalles
        String sqlDetalles = "DELETE FROM detalle_factura WHERE id_factura=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement psDet = con.prepareStatement(sqlDetalles)) {
            psDet.setInt(1, idFactura);
            psDet.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar detalles: " + e.getMessage());
            return false;
        }

        // Eliminar factura
        String sqlFactura = "DELETE FROM facturas WHERE id_factura=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement psFac = con.prepareStatement(sqlFactura)) {
            psFac.setInt(1, idFactura);
            return psFac.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar factura: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el ID de una factura a partir del número de factura.
     */
    private Integer obtenerIdFactura(int numeroFactura) {
        String sql = "SELECT id_factura FROM facturas WHERE numero_factura=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numeroFactura);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("id_factura");
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener ID de factura: " + e.getMessage());
        }
        return null;
    }

    /**
     * Mapea un ResultSet a un objeto Factura.
     */
    private Factura mapearFactura(ResultSet rs, boolean incluirDetalles) throws SQLException {
        Factura f = new Factura();
        f.setIdFactura(rs.getInt("id_factura"));
        f.setNumeroFactura(rs.getInt("numero_factura"));
        f.setIdCliente(rs.getInt("id_cliente"));
        f.setIdEmpleado(rs.getInt("id_empleado"));
        f.setFecha(rs.getTimestamp("fecha"));
        f.setTotal(rs.getDouble("total"));
        f.setNumeroCaja(rs.getInt("numero_caja"));
        return f;
    }
}

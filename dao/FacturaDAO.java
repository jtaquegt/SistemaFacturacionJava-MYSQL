package dao;

import config.ConexionBD;
import models.Factura;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO para gestión de facturas.
 * Incluye métodos para crear, buscar, listar y cancelar facturas.
 */
public class FacturaDAO {

    /**
     * Genera el siguiente número de factura correlativo.
     * @return Número de factura siguiente
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
     * @param factura Objeto Factura
     * @return true si la operación fue exitosa
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
     * Cancela una factura, reintegrando stock.
     * @param idFactura ID de la factura
     * @return true si se eliminó correctamente
     */
    public boolean cancelarFactura(int idFactura) {
        DetalleFacturaDAO detalleDAO = new DetalleFacturaDAO();
        if (detalleDAO.reintegrarStock(idFactura)) {
            String sql = "DELETE FROM facturas WHERE id_factura=?";
            try (Connection con = ConexionBD.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idFactura);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Error al cancelar factura: " + e.getMessage());
            }
        }
        return false;
    }

    /**
     * Lista todas las facturas registradas.
     * @return Lista de Factura
     */
    public ArrayList<Factura> listarFacturas() {
        ArrayList<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM facturas";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Factura f = new Factura();
                f.setIdFactura(rs.getInt("id_factura"));
                f.setNumeroFactura(rs.getInt("numero_factura"));
                f.setIdCliente(rs.getInt("id_cliente"));
                f.setIdEmpleado(rs.getInt("id_empleado"));
                f.setFecha(rs.getTimestamp("fecha"));
                f.setTotal(rs.getDouble("total"));
                f.setNumeroCaja(rs.getInt("numero_caja"));
                lista.add(f);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar facturas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Busca una factura por su número.
     * @param numeroFactura Número de factura
     * @return Objeto Factura o null si no existe
     */
    public Factura buscarFactura(int numeroFactura) {
        Factura factura = null;
        String sql = "SELECT * FROM facturas WHERE numero_factura=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numeroFactura);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    factura = new Factura();
                    factura.setIdFactura(rs.getInt("id_factura"));
                    factura.setNumeroFactura(rs.getInt("numero_factura"));
                    factura.setIdCliente(rs.getInt("id_cliente"));
                    factura.setIdEmpleado(rs.getInt("id_empleado"));
                    factura.setFecha(rs.getTimestamp("fecha"));
                    factura.setTotal(rs.getDouble("total"));
                    factura.setNumeroCaja(rs.getInt("numero_caja"));

                    DetalleFacturaDAO detalleDAO = new DetalleFacturaDAO();
                    factura.setDetalles(detalleDAO.listarDetallesConArticulo(factura.getIdFactura()));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar factura: " + e.getMessage());
        }

        return factura;
    }
}

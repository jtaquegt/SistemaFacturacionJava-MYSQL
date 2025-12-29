package dao;

import config.ConexionBD;
import models.Factura;
import java.sql.*;
import java.util.ArrayList;

public class FacturaDAO {

    // Generar siguiente número correlativo de factura
    public int generarNumeroFactura() {
        int numero = 1;
        String sql = "SELECT MAX(numero_factura) AS max_num FROM facturas";
        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) numero = rs.getInt("max_num") + 1;

        } catch (SQLException e) {
            System.out.println("Error al generar número de factura: " + e.getMessage());
        }
        return numero;
    }

    // Guardar factura
    public boolean guardarFactura(Factura factura) {
        String sql = "INSERT INTO facturas(numero_factura, id_cliente, id_empleado, total, numero_caja) VALUES(?,?,?,?,?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, factura.getNumeroFactura());
            ps.setInt(2, factura.getIdCliente());
            ps.setInt(3, factura.getIdEmpleado());
            ps.setDouble(4, factura.getTotal());
            ps.setInt(5, factura.getNumeroCaja());

            if (ps.executeUpdate() > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) factura.setIdFactura(rs.getInt(1));
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error al guardar factura: " + e.getMessage());
        }
        return false;
    }

    // Cancelar factura y reintegrar stock
    public boolean cancelarFactura(int idFactura) {
        DetalleFacturaDAO detalleDAO = new DetalleFacturaDAO();
        if (detalleDAO.reintegrarStock(idFactura)) {
            String sql = "DELETE FROM facturas WHERE id_factura=?";
            try (Connection con = ConexionBD.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idFactura);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                System.out.println("Error al cancelar factura: " + e.getMessage());
            }
        }
        return false;
    }

    // Listar todas las facturas
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
            System.out.println("Error al listar facturas: " + e.getMessage());
        }
        return lista;
    }

    // Buscar factura por número
    public Factura buscarFactura(int numeroFactura) {
        Factura factura = null;
        String sql = "SELECT * FROM facturas WHERE numero_factura=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numeroFactura);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                factura = new Factura();
                factura.setIdFactura(rs.getInt("id_factura"));
                factura.setNumeroFactura(rs.getInt("numero_factura"));
                factura.setIdCliente(rs.getInt("id_cliente"));
                factura.setIdEmpleado(rs.getInt("id_empleado"));
                factura.setFecha(rs.getTimestamp("fecha"));
                factura.setTotal(rs.getDouble("total"));
                factura.setNumeroCaja(rs.getInt("numero_caja"));

                // Obtener detalles
                DetalleFacturaDAO detalleDAO = new DetalleFacturaDAO();
                factura.setDetalles(detalleDAO.listarDetallesConArticulo(factura.getIdFactura()));
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar factura: " + e.getMessage());
        }
        return factura;
    }

    // Eliminar factura por número
    public boolean eliminarFacturaPorNumero(int numeroFactura) {
        int idFactura = -1;
        String sqlId = "SELECT id_factura FROM facturas WHERE numero_factura=?";
        String sqlDetalles = "DELETE FROM detalle_factura WHERE id_factura=?";
        String sqlFactura = "DELETE FROM facturas WHERE numero_factura=?";

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement psId = con.prepareStatement(sqlId)) {

            psId.setInt(1, numeroFactura);
            ResultSet rs = psId.executeQuery();
            if (rs.next()) idFactura = rs.getInt("id_factura");
            else return false;

        } catch (SQLException e) {
            System.out.println("Error al buscar ID de factura: " + e.getMessage());
            return false;
        }

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement psDet = con.prepareStatement(sqlDetalles)) {

            psDet.setInt(1, idFactura);
            psDet.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar detalles: " + e.getMessage());
            return false;
        }

        try (Connection con = ConexionBD.getConexion();
             PreparedStatement psFac = con.prepareStatement(sqlFactura)) {

            psFac.setInt(1, numeroFactura);
            return psFac.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar factura: " + e.getMessage());
            return false;
        }
    }
}

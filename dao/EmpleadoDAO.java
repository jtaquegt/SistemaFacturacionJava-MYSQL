package dao;

import config.ConexionBD;
import models.Empleado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la gestión de empleados en la base de datos.
 * Permite agregar, eliminar, actualizar, buscar y listar empleados.
 */
public class EmpleadoDAO {

    /**
     * Agrega un nuevo empleado a la base de datos.
     * @param empleado Empleado a agregar
     * @return true si se insertó correctamente, false en caso de error
     */
    public boolean agregarEmpleado(Empleado empleado) {
        String sql = "INSERT INTO empleados (codigo, nombre, puesto, salario) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, empleado.getCodigo());
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getPuesto());
            ps.setDouble(4, empleado.getSalario());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al agregar empleado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un empleado por ID.
     * @param idEmpleado ID del empleado
     * @return true si se eliminó correctamente, false en caso de error
     */
    public boolean eliminarEmpleado(int idEmpleado) {
        String sql = "DELETE FROM empleados WHERE id_empleado = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza los datos de un empleado existente.
     * @param empleado Empleado con datos actualizados
     * @return true si se actualizó correctamente, false en caso de error
     */
    public boolean actualizarEmpleado(Empleado empleado) {
        String sql = "UPDATE empleados SET codigo=?, nombre=?, puesto=?, salario=? WHERE id_empleado=?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, empleado.getCodigo());
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getPuesto());
            ps.setDouble(4, empleado.getSalario());
            ps.setInt(5, empleado.getIdEmpleado());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Busca un empleado por su código.
     * @param codigo Código del empleado
     * @return Empleado encontrado o null si no existe
     */
    public Empleado buscarPorCodigo(String codigo) {
        String sql = "SELECT * FROM empleados WHERE codigo = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearEmpleado(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar empleado: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lista todos los empleados.
     * @return Lista de empleados
     */
    public List<Empleado> listarEmpleados() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleados";

        try (Connection con = ConexionBD.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(mapearEmpleado(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
        }

        return lista;
    }

    /**
     * Obtiene un empleado por su ID.
     * @param idEmpleado ID del empleado
     * @return Empleado encontrado o null si no existe
     */
    public Empleado obtenerPorId(int idEmpleado) {
        String sql = "SELECT * FROM empleados WHERE id_empleado = ?";
        try (Connection con = ConexionBD.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearEmpleado(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener empleado por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Mapea un ResultSet a un objeto Empleado.
     * @param rs ResultSet con los datos del empleado
     * @return Empleado mapeado
     * @throws SQLException en caso de error de lectura
     */
    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        Empleado e = new Empleado();
        e.setIdEmpleado(rs.getInt("id_empleado"));
        e.setCodigo(rs.getString("codigo"));
        e.setNombre(rs.getString("nombre"));
        e.setPuesto(rs.getString("puesto"));
        e.setSalario(rs.getDouble("salario"));
        return e;
    }
}

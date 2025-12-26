package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // URL de conexión a la base de datos MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/tienda_mas?useSSL=false&serverTimezone=UTC";
    // Usuario de la base de datos
    private static final String USER = "root";
    // Contraseña de la base de datos (CAMBIAR en producción)
    private static final String PASSWORD = "Admin";

    // Objeto conexión estático para singleton
    private static Connection conexion;

    /**
     * Obtiene la conexión a la base de datos.
     * Si no existe o está cerrada, crea una nueva conexión.
     *
     * @return Connection objeto conexión a la base de datos
     */
    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver"); // cargar driver MySQL (opcional desde Java 6)
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión exitosa a MySQL");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver de MySQL no encontrado: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
        return conexion;
    }

    /**
     * Cierra la conexión a la base de datos si está abierta.
     */
    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("Conexión cerrada");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

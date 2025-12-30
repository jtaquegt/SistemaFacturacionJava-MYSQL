package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase de conexión a la base de datos MySQL.
 * 
 * Esta versión está preparada para repositorio público:
 * - Se usan placeholders para usuario y contraseña.
 * - Manejo de excepciones robusto.
 * - Permite abrir y cerrar la conexión de manera segura.
 */
public class ConexionBD {

    // URL de conexión (ajustar según entorno)
    private static final String URL = "jdbc:mysql://localhost:3306/BASE_DE_DATOS?useSSL=false&serverTimezone=UTC";
    // Usuario de la base de datos (placeholder)
    private static final String USER = "USUARIO";
    // Contraseña de la base de datos (placeholder)
    private static final String PASSWORD = "CONTRASEÑA";

    // Objeto Connection estático para reutilizar la conexión
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
                // Cargar driver MySQL (opcional en Java modernos)
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Crear la conexión
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión exitosa a la base de datos MySQL");
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
                System.out.println("Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

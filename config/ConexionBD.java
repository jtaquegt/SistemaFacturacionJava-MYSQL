package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // URL de conexión a la base de datos MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/bd_super_tiendas_mas?useSSL=false&serverTimezone=UTC";
    // Usuario de la base de datos
    private static final String USER = "root";
    // Contraseña de la base de datos - CAMBIAR en entorno de producción
    private static final String PASSWORD = "Admin";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            // Conexión exitosa, se puede registrar log aquí si se desea
            System.out.println("Conexión exitosa a MySQL");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }
}

package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
     private static final String URL = "jdbc:mysql://localhost:3306/sitios_turisticos";
    private static final String USUARIO = "root";  
    private static final String CLAVE = "";  
    private static Connection conexion = null;

    public static Connection getConexion() {
        if (conexion == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Asegura la carga del driver
                conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
                System.out.println("Conexión exitosa a la base de datos");
            } catch (ClassNotFoundException e) {
                System.out.println("Error: No se encontró el driver de MySQL.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("Error al conectar con la base de datos.");
                e.printStackTrace();
            }
        }
        return conexion;
    }
}

package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author userx
 */
public class Conexion {
    private static String ruta = "jdbc:mysql://localhost:3306/sitios_turisticos";
    private static String user = "root";
    private static String pass = "";
    private static Connection conexion;
    private static Statement consulta;
    
    public static Connection conexi() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(ruta, user, pass);
                consulta = conexion.createStatement();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public static Statement consultas() {
        try {
            if (consulta == null || consulta.isClosed()) {
                if (conexion == null || conexion.isClosed()) {
                    conexi();
                } else {
                    consulta = conexion.createStatement();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consulta;
    }

    public static void cerrarConexion() {
        try {
            if (consulta != null && !consulta.isClosed()) {
                consulta.close();
            }
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection obtenerConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexi();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
            conexion = DriverManager.getConnection(ruta, user, pass);
            consulta = conexion.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public static Statement consultas() {
        return consulta;
    }

    public static void cerrarConexion() {
        try {
            if (consulta != null) {
                consulta.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection obtenerConexion() {
        return conexion;
    }
    
}



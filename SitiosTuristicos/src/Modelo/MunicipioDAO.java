/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author User
 */
import Modelo.Conexion;
import Modelo.Municipio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MunicipioDAO {
    
    // Obtener todos los municipios
    public List<Municipio> obtenerTodos() throws SQLException {
        List<Municipio> municipios = new ArrayList<>();
        String query = "SELECT * FROM Municipio";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Municipio municipio = new Municipio();
                municipio.setId(rs.getInt("id"));
                municipio.setNombreMunicipio(rs.getString("nombre_municipio"));
                municipio.setDescripcion(rs.getString("descripcion"));
                municipio.setBandera(rs.getString("bandera"));
                
                municipios.add(municipio);
            }
        }
        
        return municipios;
    }
    
    // Obtener municipio por ID
    public Municipio obtenerPorId(int id) throws SQLException {
        String query = "SELECT * FROM Municipio WHERE id = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Municipio municipio = new Municipio();
                    municipio.setId(rs.getInt("id"));
                    municipio.setNombreMunicipio(rs.getString("nombre_municipio"));
                    municipio.setDescripcion(rs.getString("descripcion"));
                    municipio.setBandera(rs.getString("bandera"));
                    
                    return municipio;
                }
            }
        }
        
        return null;
    }
    
    // Obtener municipio por nombre
    public Municipio obtenerPorNombre(String nombre) throws SQLException {
        String query = "SELECT * FROM Municipio WHERE nombre_municipio = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, nombre);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Municipio municipio = new Municipio();
                    municipio.setId(rs.getInt("id"));
                    municipio.setNombreMunicipio(rs.getString("nombre_municipio"));
                    municipio.setDescripcion(rs.getString("descripcion"));
                    municipio.setBandera(rs.getString("bandera"));
                    
                    return municipio;
                }
            }
        }
        
        return null;
    }
}
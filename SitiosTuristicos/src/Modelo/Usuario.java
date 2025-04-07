package Modelo;

import static Modelo.Conexion.conexi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Usuario {
    private int id;
    private String documento;
    private String nombre;
    private String apellido;
    private String correo;
    private String clave;

    public Usuario() {
        
    }

    public Usuario(int id, String documento, String nombre, String apellido, String correo, String clave) {
        this.id = id;
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
    public boolean Login() {
        Connection con = conexi(); 
        String sql = "SELECT * FROM usuario WHERE correo = ? AND clave = ?";

        try {
            if (con == null) {
                System.out.println("Error: La conexión es nula.");
                return false;
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, this.correo);
            ps.setString(2, this.clave);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true; // Inicio de sesión exitoso
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta de login.");
            e.printStackTrace();
        }

        return false; // Si no encontró el usuario
    }
}

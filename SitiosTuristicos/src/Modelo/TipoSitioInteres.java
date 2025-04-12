package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TipoSitioInteres {
    private int id;
    private String tipo;
    
    private static final String TABLA_MUNICIPIO = "Municipio";
    private static final String TABLA_SITIOS = "sitios_interes";
    private static final String TABLA_TIPO_SITIO = "tipo_sitio_interes";
    
    // Nombre correcto de la tabla en la base de datos
    private static final String TABLA = "tipo_sitio_interes";
    
    public TipoSitioInteres() {
    }

    public TipoSitioInteres(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return tipo;
    }
    
    /**
     * Obtiene todos los tipos de sitios de interés
     * @return Lista de tipos de sitios
     * @throws SQLException Si ocurre un error en la base de datos
     */
    
    // Método que obtiene sitios y municipios relacionados con el texto de búsqueda
    public static List<String> obtenerSitiosPorBusqueda(String texto) throws SQLException {
        List<String> resultados = new ArrayList<>();
        String query = "SELECT m.nombre_municipio AS municipio, s.nombre_sitio AS sitio "
                     + "FROM " + TABLA_MUNICIPIO + " m "
                     + "JOIN " + TABLA_SITIOS + " s ON m.id = s.id_municipio_fk "
                     + "JOIN " + TABLA_TIPO_SITIO + " t ON s.id_tipo_sitio_fk = t.id "
                     + "WHERE m.nombre_municipio LIKE ? OR s.nombre_sitio LIKE ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, "%" + texto + "%");
            ps.setString(2, "%" + texto + "%");
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String municipio = rs.getString("municipio");
                    String sitio = rs.getString("sitio");
                    resultados.add(municipio + " - " + sitio);
                }
            }
        }
        return resultados;
    }
    
    public static List<TipoSitioInteres> obtenerTodos() throws SQLException {
        List<TipoSitioInteres> tipos = new ArrayList<>();
        String query = "SELECT id, tipo FROM " + TABLA + " ORDER BY tipo";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                TipoSitioInteres tipo = new TipoSitioInteres();
                tipo.setId(rs.getInt("id"));
                tipo.setTipo(rs.getString("tipo"));
                
                tipos.add(tipo);
            }
        }
        
        return tipos;
    }
    
    /**
     * Obtiene un tipo de sitio de interés por su ID
     * @param id ID del tipo de sitio
     * @return Objeto TipoSitioInteres o null si no se encuentra
     * @throws SQLException Si ocurre un error en la base de datos
     */
    public static TipoSitioInteres obtenerPorId(int id) throws SQLException {
        String query = "SELECT id, tipo FROM " + TABLA + " WHERE id = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    TipoSitioInteres tipo = new TipoSitioInteres();
                    tipo.setId(rs.getInt("id"));
                    tipo.setTipo(rs.getString("tipo"));
                    
                    return tipo;
                }
            }
        }
        
        return null;
    }
}
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SitioInteresDAO {
    
    // Nombre correcto de la tabla en la base de datos
    private static final String TABLA = "sitios_interes";

    // Obtener todos los sitios
    public List<SitioInteres> obtenerTodos() throws SQLException {
        List<SitioInteres> sitios = new ArrayList<>();
        String query = "SELECT s.id, s.nombre_sitio, s.descripcion, s.precio, "
                + "s.id_municipio_fk, s.distancia, s.id_tipo_sitio_fk, "
                + "m.nombre_municipio, t.tipo AS tipo_sitio "
                + "FROM " + TABLA + " s "
                + "JOIN Municipio m ON s.id_municipio_fk = m.id "
                + "JOIN tipo_sitio_interes t ON s.id_tipo_sitio_fk = t.id "
                + "ORDER BY s.nombre_sitio";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                SitioInteres sitio = crearSitioDesdeResultSet(rs);
                sitios.add(sitio);
            }
        }
        return sitios;
    }
    
    // Obtener sitio por ID
    public SitioInteres obtenerPorId(int id) throws SQLException {
        String query = "SELECT s.id, s.nombre_sitio, s.descripcion, s.precio, "
                + "s.id_municipio_fk, s.distancia, s.id_tipo_sitio_fk, "
                + "m.nombre_municipio, t.tipo AS tipo_sitio "
                + "FROM " + TABLA + " s "
                + "JOIN Municipio m ON s.id_municipio_fk = m.id "
                + "JOIN tipo_sitio_interes t ON s.id_tipo_sitio_fk = t.id "
                + "WHERE s.id = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return crearSitioDesdeResultSet(rs);
                }
            }
        }
        return null;
    }
    
    // Obtener sitios por municipio
    public List<SitioInteres> obtenerPorMunicipio(int idMunicipio) throws SQLException {
        List<SitioInteres> sitios = new ArrayList<>();
        String query = "SELECT s.id, s.nombre_sitio, s.descripcion, s.precio, "
                + "s.id_municipio_fk, s.distancia, s.id_tipo_sitio_fk, "
                + "m.nombre_municipio, t.tipo AS tipo_sitio "
                + "FROM " + TABLA + " s "
                + "JOIN Municipio m ON s.id_municipio_fk = m.id "
                + "JOIN tipo_sitio_interes t ON s.id_tipo_sitio_fk = t.id "
                + "WHERE s.id_municipio_fk = ? "
                + "ORDER BY s.nombre_sitio";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idMunicipio);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SitioInteres sitio = crearSitioDesdeResultSet(rs);
                    sitios.add(sitio);
                }
            }
        }
        return sitios;
    }
    
    // Obtener sitios por nombre de municipio
   public List<SitioInteres> obtenerPorNombreMunicipio(String nombreMunicipio) throws SQLException {
    List<SitioInteres> sitios = new ArrayList<>();
    String query = "SELECT s.id, s.nombre_sitio, s.descripcion, s.precio, "
            + "s.id_municipio_fk, s.distancia, s.id_tipo_sitio_fk, "
            + "m.nombre_municipio, t.tipo AS tipo_sitio "
            + "FROM " + TABLA + " s "
            + "JOIN Municipio m ON s.id_municipio_fk = m.id "
            + "JOIN tipo_sitio_interes t ON s.id_tipo_sitio_fk = t.id "
            + "WHERE m.nombre_municipio = ? "
            + "ORDER BY s.nombre_sitio";
    
    try (Connection conn = Conexion.obtenerConexion();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, nombreMunicipio);
        
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                SitioInteres sitio = new SitioInteres();
                sitio.setId(rs.getInt("id"));
                sitio.setNombreSitio(rs.getString("nombre_sitio"));
                sitio.setDescripcion(rs.getString("descripcion"));
                sitio.setPrecio(rs.getDouble("precio"));
                sitio.setIdMunicipioFk(rs.getInt("id_municipio_fk"));
                sitio.setDistancia(rs.getFloat("distancia"));
                sitio.setIdTipoSitioFk(rs.getInt("id_tipo_sitio_fk"));
                sitio.setNombreMunicipio(rs.getString("nombre_municipio"));
                sitio.setTipoSitio(rs.getString("tipo_sitio"));
                sitios.add(sitio);
            }
        }
    }
    
    // Ahora cargar los servicios para cada sitio
    for (SitioInteres sitio : sitios) {
        cargarServicios(sitio);
    }
    
    return sitios;
}

// Método para cargar los servicios de un sitio
private void cargarServicios(SitioInteres sitio) throws SQLException {
    sitio.setTieneAlojamiento(tieneServicioDeTipo(sitio.getId(), 1));
    sitio.setTieneAlimentacion(tieneServicioDeTipo(sitio.getId(), 2));
    sitio.setTieneTransporte(tieneServicioDeTipo(sitio.getId(), 3));
}

    
    // Agregar nuevo sitio de interés
    public boolean agregar(SitioInteres sitio) throws SQLException {
        String query = "INSERT INTO " + TABLA 
                + " (nombre_sitio, descripcion, precio, id_municipio_fk, distancia, id_tipo_sitio_fk) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, sitio.getNombreSitio());
            stmt.setString(2, sitio.getDescripcion());
            stmt.setDouble(3, sitio.getPrecio());
            stmt.setInt(4, sitio.getIdMunicipioFk());
            stmt.setFloat(5, sitio.getDistancia());
            stmt.setInt(6, sitio.getIdTipoSitioFk());
            
            int filasAfectadas = stmt.executeUpdate();
            
            // Si se insertó correctamente, agregar servicios si existen
            if (filasAfectadas > 0) {
                // Obtener el ID generado para el sitio
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idSitio = generatedKeys.getInt(1);
                        
                        // Añadir servicios si están habilitados
                        if (sitio.isTieneAlojamiento()) {
                            agregarServicio(idSitio, 1); // 1 = ID tipo alojamiento
                        }
                        
                        if (sitio.isTieneAlimentacion()) {
                            agregarServicio(idSitio, 2); // 2 = ID tipo alimentación
                        }
                        
                        if (sitio.isTieneTransporte()) {
                            agregarServicio(idSitio, 3); // 3 = ID tipo transporte
                        }
                    }
                }
            }
            
            return filasAfectadas > 0;
        }
    }
    
    // Método auxiliar para agregar un servicio
    private void agregarServicio(int idSitio, int tipoServicio) throws SQLException {
        String query = "INSERT INTO servicios (nombre_servicio, tipo, id_sitio_fk) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            String nombreServicio = "";
            switch (tipoServicio) {
                case 1:
                    nombreServicio = "Alojamiento";
                    break;
                case 2:
                    nombreServicio = "Alimentación";
                    break;
                case 3:
                    nombreServicio = "Transporte";
                    break;
            }
            
            stmt.setString(1, nombreServicio);
            stmt.setInt(2, tipoServicio);
            stmt.setInt(3, idSitio);
            
            stmt.executeUpdate();
        }
    }
    
    // Actualizar un sitio de interés existente
    public boolean actualizar(SitioInteres sitio) throws SQLException {
        String query = "UPDATE " + TABLA + " SET "
                + "nombre_sitio = ?, "
                + "descripcion = ?, "
                + "precio = ?, "
                + "id_municipio_fk = ?, "
                + "distancia = ?, "
                + "id_tipo_sitio_fk = ? "
                + "WHERE id = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, sitio.getNombreSitio());
            stmt.setString(2, sitio.getDescripcion());
            stmt.setDouble(3, sitio.getPrecio());
            stmt.setInt(4, sitio.getIdMunicipioFk());
            stmt.setFloat(5, sitio.getDistancia());
            stmt.setInt(6, sitio.getIdTipoSitioFk());
            stmt.setInt(7, sitio.getId());
            
            int filasAfectadas = stmt.executeUpdate();
            
            // Actualizar servicios
            if (filasAfectadas > 0) {
                // Primero eliminar todos los servicios existentes
                eliminarServicios(sitio.getId());
                
                // Luego agregar los servicios actualizados
                if (sitio.isTieneAlojamiento()) {
                    agregarServicio(sitio.getId(), 1);
                }
                
                if (sitio.isTieneAlimentacion()) {
                    agregarServicio(sitio.getId(), 2);
                }
                
                if (sitio.isTieneTransporte()) {
                    agregarServicio(sitio.getId(), 3);
                }
            }
            
            return filasAfectadas > 0;
        }
    }
    
    // Método auxiliar para eliminar todos los servicios de un sitio
    private void eliminarServicios(int idSitio) throws SQLException {
        String query = "DELETE FROM servicios WHERE id_sitio_fk = ?";
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idSitio);
            stmt.executeUpdate();
        }
    }
    
    // Eliminar un sitio de interés
    public boolean eliminar(int id) throws SQLException {
        // Primero eliminar los servicios asociados
        eliminarServicios(id);
        
        // Luego eliminar las imágenes asociadas
        eliminarImagenes(id);
        
        // Luego eliminar las calificaciones asociadas
        eliminarCalificaciones(id);
        
        // Finalmente, eliminar el sitio
        String query = "DELETE FROM " + TABLA + " WHERE id = ?";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        }
    }
    
    // Método auxiliar para eliminar imágenes relacionadas a un sitio
    private void eliminarImagenes(int idSitio) throws SQLException {
        String query = "DELETE FROM imagenes WHERE id_sitio_fk = ?";
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idSitio);
            stmt.executeUpdate();
        }
    }
    
    // Método auxiliar para eliminar calificaciones relacionadas a un sitio
    private void eliminarCalificaciones(int idSitio) throws SQLException {
        String query = "DELETE FROM calificaciones WHERE id_sitio_fk = ?";
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idSitio);
            stmt.executeUpdate();
        }
    }
    
    // Contar sitios por municipio sin servicios
    public int contarSitiosSinServicios(String nombreMunicipio) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + TABLA + " s "
                + "JOIN Municipio m ON s.id_municipio_fk = m.id "
                + "WHERE m.nombre_municipio = ? "
                + "AND NOT EXISTS (SELECT 1 FROM servicios srv WHERE srv.id_sitio_fk = s.id)";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, nombreMunicipio);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
    // Contar sitios por municipio con alojamiento
    public int contarSitiosConAlojamiento(String nombreMunicipio) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + TABLA + " s "
                + "JOIN Municipio m ON s.id_municipio_fk = m.id "
                + "WHERE m.nombre_municipio = ? "
                + "AND EXISTS (SELECT 1 FROM servicios srv WHERE srv.id_sitio_fk = s.id AND srv.tipo = 1)";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, nombreMunicipio);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
    // Contar sitios por municipio con todos los servicios
    public int contarSitiosConTodosServicios(String nombreMunicipio) throws SQLException {
        String query = "SELECT COUNT(*) FROM " + TABLA + " s "
                + "JOIN Municipio m ON s.id_municipio_fk = m.id "
                + "WHERE m.nombre_municipio = ? "
                + "AND EXISTS (SELECT 1 FROM servicios srv1 WHERE srv1.id_sitio_fk = s.id AND srv1.tipo = 1) "
                + "AND EXISTS (SELECT 1 FROM servicios srv2 WHERE srv2.id_sitio_fk = s.id AND srv2.tipo = 2) "
                + "AND EXISTS (SELECT 1 FROM servicios srv3 WHERE srv3.id_sitio_fk = s.id AND srv3.tipo = 3)";
        
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, nombreMunicipio);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
    // Verificar si un sitio tiene un tipo de servicio específico
    private boolean tieneServicioDeTipo(int idSitio, int tipoServicio) throws SQLException {
        String query = "SELECT COUNT(*) FROM servicios WHERE id_sitio_fk = ? AND tipo = ?";
        try (Connection conn = Conexion.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, idSitio);
            stmt.setInt(2, tipoServicio);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    // Método auxiliar para crear un objeto SitioInteres desde un ResultSet
    private SitioInteres crearSitioDesdeResultSet(ResultSet rs) throws SQLException {
        SitioInteres sitio = new SitioInteres();
        sitio.setId(rs.getInt("id"));
        sitio.setNombreSitio(rs.getString("nombre_sitio"));
        sitio.setDescripcion(rs.getString("descripcion"));
        sitio.setPrecio(rs.getDouble("precio"));
        sitio.setIdMunicipioFk(rs.getInt("id_municipio_fk"));
        sitio.setDistancia(rs.getFloat("distancia"));
        sitio.setIdTipoSitioFk(rs.getInt("id_tipo_sitio_fk"));
        sitio.setNombreMunicipio(rs.getString("nombre_municipio"));
        sitio.setTipoSitio(rs.getString("tipo_sitio"));
        
        // Recuperar información de servicios
        int idSitio = sitio.getId();
        sitio.setTieneAlojamiento(tieneServicioDeTipo(idSitio, 1));  // 1 = ID tipo alojamiento
        sitio.setTieneAlimentacion(tieneServicioDeTipo(idSitio, 2)); // 2 = ID tipo alimentación
        sitio.setTieneTransporte(tieneServicioDeTipo(idSitio, 3));   // 3 = ID tipo transporte
        
        return sitio;
    }
}
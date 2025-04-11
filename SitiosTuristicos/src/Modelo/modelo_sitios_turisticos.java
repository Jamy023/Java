
package Modelo;

import static Modelo.Conexion.conexi;
import static Modelo.Conexion.obtenerConexion;
import java.awt.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import Vista.vista_sitios_turisticos;
import java.net.URL;

public class modelo_sitios_turisticos {
    
    public static Map<Integer, List<String>> obtenerImagenesPorSitio(String nombreSitio) {
        Map<Integer, List<String>> imagenesPorServicio = new HashMap<>();
        String sql = "SELECT i.imagen, i.id_tipo_servicio_fk FROM imagenes i " +
                     "JOIN sitios_interes s ON i.id_sitio_fk = s.id " +
                     "WHERE s.nombre_sitio = ?";

        try {
            // Obtener la conexión utilizando la clase Conexion
            Connection con = conexi(); 

            // Preparar la consulta SQL
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombreSitio);

            // Ejecutar la consulta
            ResultSet rs = ps.executeQuery();

            // Procesar los resultados
            while (rs.next()) {
                String rutaImagen = rs.getString("imagen");
                int tipoServicio = rs.getInt("id_tipo_servicio_fk");

                // Si el tipo de servicio ya tiene imágenes, agregar la nueva imagen
                imagenesPorServicio.computeIfAbsent(tipoServicio, k -> new ArrayList<>()).add(rutaImagen);
            }

            // Cerrar recursos
            rs.close();
            ps.close();
            con.close();  // No olvides cerrar la conexión

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return imagenesPorServicio;
    }
     
    public String obtenerDescripcionYPrecioPorSitio(String nombreSitio) {
        String descripcion = null;
        double precio = 0.0;
        double promedioEstrellas = 0.0;

        // Consulta para descripción y precio
        String queryInfo = "SELECT descripcion, precio FROM sitios_interes WHERE nombre_sitio = ?";

        // Consulta para obtener promedio de estrellas usando subconsulta
        String queryCalificacion = 
            "SELECT AVG(c.estrellas) AS promedio " +
            "FROM calificaciones c " +
            "JOIN sitios_interes s ON c.id_sitio_fk = s.id " +
            "WHERE s.nombre_sitio = ?";

        try {
            Connection con = conexi();

            // 1. Obtener descripción y precio
            PreparedStatement stmtInfo = con.prepareStatement(queryInfo);
            stmtInfo.setString(1, nombreSitio);
            ResultSet rsInfo = stmtInfo.executeQuery();

            if (rsInfo.next()) {
                descripcion = rsInfo.getString("descripcion");
                precio = rsInfo.getDouble("precio");
            }

            rsInfo.close();
            stmtInfo.close();

            // 2. Obtener calificación promedio
            PreparedStatement stmtCalif = con.prepareStatement(queryCalificacion);
            stmtCalif.setString(1, nombreSitio);
            ResultSet rsCalif = stmtCalif.executeQuery();

            if (rsCalif.next()) {
                promedioEstrellas = rsCalif.getDouble("promedio");
            }

            rsCalif.close();
            stmtCalif.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al obtener los datos del sitio: " + e.getMessage();
        }

        if (descripcion == null) {
            return "No se encontró información para el sitio: " + nombreSitio;
        }

        return descripcion + "\nPrecio: $" + precio + "\nCalificación promedio: " + String.format("%.1f", promedioEstrellas) + " estrellas";
    }
    
    public Map<Integer, String> obtenerServiciosPorSitio(String nombreSitio) {
        Map<Integer, String> serviciosMap = new HashMap<>();
        String sql = "SELECT s.id, s.nombre_servicio " +
                     "FROM servicios s " +
                     "JOIN sitios_interes si ON s.id_sitio_fk = si.id " +
                     "WHERE si.nombre_sitio = ?";

        try (Connection con = Conexion.conexi();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            // Establecemos el parámetro del nombre del sitio
            ps.setString(1, nombreSitio);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Se agrega el id y el nombre del servicio al map.
                    serviciosMap.put(rs.getInt("id"), rs.getString("nombre_servicio"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return serviciosMap;
    }
    
    public void cargarEstrellas(vista_sitios_turisticos vista, boolean click) {
        if (!click) {
            String foto = "/img/estrella.png"; // Ruta relativa dentro del proyecto
            URL url = getClass().getResource(foto);

            if (url == null) {
                System.err.println("No se encontró la imagen: " + foto);
                return;
            }

            ImageIcon icon = new ImageIcon(url);
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(
                vista.estrella1.getWidth(), 
                vista.estrella1.getHeight(), 
                Image.SCALE_SMOOTH
            );
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Aplicar la imagen a las 5 estrellas
            vista.estrella1.setText(null);
            vista.estrella1.setIcon(scaledIcon);

            vista.estrella2.setText(null);
            vista.estrella2.setIcon(scaledIcon);

            vista.estrella3.setText(null);
            vista.estrella3.setIcon(scaledIcon);

            vista.estrella4.setText(null);
            vista.estrella4.setIcon(scaledIcon);

            vista.estrella5.setText(null);
            vista.estrella5.setIcon(scaledIcon);
        }
    }

    public void estrellaCambiarColor(vista_sitios_turisticos vista, int numeroEstrella) {
        String foto = "/img/estrellaColor.png"; // Ruta relativa desde recursos (src/imagenes/)
        URL url = getClass().getResource(foto);

        if (url == null) {
            System.err.println("No se encontró la imagen: " + foto);
            return;
        }

        ImageIcon icon = new ImageIcon(url);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(vista.estrella1.getWidth(), vista.estrella1.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Cambiar iconos según el número de estrella
        if (numeroEstrella >= 1) vista.estrella1.setIcon(scaledIcon);
        if (numeroEstrella >= 2) vista.estrella2.setIcon(scaledIcon);
        if (numeroEstrella >= 3) vista.estrella3.setIcon(scaledIcon);
        if (numeroEstrella >= 4) vista.estrella4.setIcon(scaledIcon);
        if (numeroEstrella >= 5) vista.estrella5.setIcon(scaledIcon);
    }

    public static void insertarCalificacion(int estrellas, String nombreSitio) {
        String correo = "ejemplo@gmail.com";
        String comentario = ""; // puedes agregar uno si quieres
        LocalDate fecha = LocalDate.now();
        

        // Primero, obtener el ID del sitio
        String obtenerIdSitio = "SELECT id FROM sitios_interes WHERE nombre_sitio = ?";

        // Luego, insertar la calificación
        String insertar = "INSERT INTO calificaciones (correo, estrellas, comentario, fecha, id_sitio_fk) " +
                          "VALUES (?, ?, ?, ?, ?)";
        if(estrellas != 0)
        {
            try (Connection con = conexi();
                PreparedStatement psId = con.prepareStatement(obtenerIdSitio)) {

                psId.setString(1, nombreSitio);
                ResultSet rs = psId.executeQuery();

                if (rs.next()) {
                    int idSitio = rs.getInt("id");

                    try (PreparedStatement psInsertar = con.prepareStatement(insertar)) {
                        psInsertar.setString(1, correo);
                        psInsertar.setInt(2, estrellas);
                        psInsertar.setString(3, comentario);
                        psInsertar.setDate(4, java.sql.Date.valueOf(fecha));
                        psInsertar.setInt(5, idSitio);

                        psInsertar.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Calificación insertada correctamente.");

                    }

                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el sitio con nombre: " + nombreSitio, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else
        {
            JOptionPane.showMessageDialog(null, "Debes poner una calificacion primero", "Error", JOptionPane.ERROR_MESSAGE);
        } 
        
    }
    
    public String obtenerImagenComoLlegarPorSitio(String nombreSitio) {
        String rutaImagen = null;

        String sql = "SELECT i.imagen " +
                     "FROM imagenes i " +
                     "JOIN sitios_interes s ON i.id_sitio_fk = s.id " +
                     "JOIN tipo_servicio ts ON i.id_tipo_servicio_fk = ts.id " +
                     "WHERE s.nombre_sitio = ? AND ts.tipo = 'Como llegar' " +
                     "LIMIT 1";

        try (Connection con = conexi(); // Tu método para obtener conexión
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreSitio);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                rutaImagen = rs.getString("imagen");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rutaImagen;
    }
    
    public String obtenerImagen360PorSitio(String nombreSitio) {
        String rutaImagen = null;

        String sql = "SELECT i.imagen " +
                     "FROM imagenes i " +
                     "JOIN sitios_interes s ON i.id_sitio_fk = s.id " +
                     "JOIN tipo_servicio ts ON i.id_tipo_servicio_fk = ts.id " +
                     "WHERE s.nombre_sitio = ? AND ts.tipo = 'img360' " +
                     "LIMIT 1";

        try (Connection con = conexi(); // Tu método para obtener la conexión
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreSitio);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                rutaImagen = rs.getString("imagen");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rutaImagen;
    }
    
    public void mostrarImagen(String ruta, vista_sitios_turisticos vista) {
        java.net.URL url = getClass().getResource(ruta);

        if (url == null) {
            System.err.println("No se encontró la imagen en: " + ruta);
            vista.img.setText("Imagen no encontrada");
            vista.img.setIcon(null);
            return;
        }

        ImageIcon icon = new ImageIcon(url);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(
            vista.img.getWidth(),
            vista.img.getHeight(),
            Image.SCALE_SMOOTH
        );
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        vista.img.setText(null);
        vista.img.setIcon(scaledIcon);
    }

    
    public String obtenerImagenRestaurantePorSitio(String nombreSitio) {
        String rutaImagen = null;

        String query = "SELECT img.imagen " +
                       "FROM imagenes img " +
                       "JOIN sitios_interes si ON img.id_sitio_fk = si.id " +
                       "JOIN tipo_servicio ts ON img.id_tipo_servicio_fk = ts.id " +
                       "WHERE si.nombre_sitio = ? AND ts.tipo = 'Restaurantes' LIMIT 1";

        try (Connection conn = conexi();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreSitio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                rutaImagen = rs.getString("imagen");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rutaImagen;
    }

}

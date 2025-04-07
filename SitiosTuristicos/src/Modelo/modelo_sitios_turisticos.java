
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
        Map<Integer, String> serviciosPorTipo = new HashMap<>();
        String sql = "SELECT DISTINCT ts.id, ts.tipo " +
                     "FROM servicios s " +
                     "JOIN tipo_servicio ts ON s.tipo = ts.id " +
                     "JOIN sitios_interes si ON s.id_sitio_fk = si.id " +
                     "WHERE si.nombre_sitio = ?";

        try (Connection con = conexi(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nombreSitio);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idTipoServicio = rs.getInt("id");
                String nombreTipoServicio = rs.getString("tipo");
                serviciosPorTipo.put(idTipoServicio, nombreTipoServicio);
            }

            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return serviciosPorTipo;
    }
    
    public void cargarEstrellas(vista_sitios_turisticos vista, boolean click)
    {
        if(click == false)
        {
            String foto = "C:\\Users\\userx\\Documents\\Java ADSO\\SitiosTuristicos\\src\\imagenes\\estrella.png";
            ImageIcon icon = new ImageIcon(foto);

            Image image = icon.getImage();

            Image scaledImage = image.getScaledInstance(vista.estrella1.getWidth(), vista.estrella1.getHeight(), Image.SCALE_SMOOTH);

            ImageIcon scaledIcon = new ImageIcon(scaledImage);
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
    
    public void estrellaCambiarColor(vista_sitios_turisticos vista, int numeroEstrella)
    {
        String foto = "C:\\Users\\userx\\Documents\\Java ADSO\\SitiosTuristicos\\src\\imagenes\\estrellaColor.png";
        ImageIcon icon = new ImageIcon(foto);

        Image image = icon.getImage();

        Image scaledImage = image.getScaledInstance(vista.estrella1.getWidth(), vista.estrella1.getHeight(), Image.SCALE_SMOOTH);

        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        vista.estrella1.setText(null);
        vista.estrella1.setIcon(scaledIcon);
        
        if(numeroEstrella > 1)
        {
            vista.estrella2.setText(null);
            vista.estrella2.setIcon(scaledIcon);
            
            if(numeroEstrella > 2)
            {
                vista.estrella3.setText(null);
                vista.estrella3.setIcon(scaledIcon);
                
                if(numeroEstrella > 3)
                {
                    vista.estrella4.setText(null);
                    vista.estrella4.setIcon(scaledIcon);
                    
                    if(numeroEstrella > 4)
                    {
                        vista.estrella5.setText(null);
                        vista.estrella5.setIcon(scaledIcon);
                    }
                }
            }
        }
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
}

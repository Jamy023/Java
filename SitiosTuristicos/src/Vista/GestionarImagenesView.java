package Vista;

import Modelo.modelo_sitios_turisticos;
import Controlador.controlador_sitios_turisticos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class GestionarImagenesView extends JFrame {
 private final modelo_sitios_turisticos modelo;
    private final String nombreSitio;
    private JPanel panelImagenes;
    private controlador_sitios_turisticos controlador;
    private JLabel imagenSeleccionada = null; // Variable para rastrear la imagen seleccionada

    public GestionarImagenesView(modelo_sitios_turisticos modelo, String nombreSitio) {
    this.modelo = modelo;
    this.nombreSitio = nombreSitio;
    // Elimina esta línea incorrecta:
    // this.controlador = controlador;

    setTitle("Gestión de Imágenes");
    setSize(800, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new java.awt.Color(245, 249, 244));

    inicializarComponentes();
    cargarImagenes();
}

    public void setControlador(controlador_sitios_turisticos controlador) {
        this.controlador = controlador;
    }

   private void inicializarComponentes() {
       
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Panel para mostrar imágenes
        panelImagenes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelImagenes.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(panelImagenes);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Botones para agregar y eliminar imágenes
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnAgregar = new JButton("Agregar Imagen");
        btnAgregar.addActionListener(e -> agregarImagen(nombreSitio));
        
        JButton btnEliminar = new JButton("Eliminar Imagen");
        btnEliminar.addActionListener(e -> eliminarImagen(nombreSitio));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    public void cargarImagenes() {
        panelImagenes.removeAll();

        // Obtener imágenes del modelo usando el nombre del sitio
        Map<Integer, List<String>> imagenes = modelo.obtenerImagenesPorSitio(nombreSitio);

        // Validar que el mapa de imágenes no sea nulo
        if (imagenes == null || imagenes.isEmpty()) {
            JLabel lblMensaje = new JLabel("No hay imágenes disponibles.");
            lblMensaje.setHorizontalAlignment(SwingConstants.CENTER);
            lblMensaje.setForeground(Color.GRAY);
            panelImagenes.add(lblMensaje);
        } else {
            for (Map.Entry<Integer, List<String>> entry : imagenes.entrySet()) {
                List<String> rutas = entry.getValue();
                for (String ruta : rutas) {
                    JLabel lblImagen = crearLabelImagen(ruta);
                    panelImagenes.add(lblImagen);
                }
            }
        }

        panelImagenes.revalidate();
        panelImagenes.repaint();
    }

      private JLabel crearLabelImagen(String ruta) {
        JLabel lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(150, 150));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        try {
            // Verificar si la ruta ya contiene "img/" y evitar duplicar
            if (!ruta.startsWith("C:\\") && !ruta.contains("img/")) {
                ruta = new File("src/img/" + ruta).getAbsolutePath();
            } else if (!ruta.startsWith("C:\\")) {
                ruta = new File("src/" + ruta).getAbsolutePath();
            }

            // Imprimir la ruta para depuración
            System.out.println("Intentando cargar imagen desde: " + ruta);

            ImageIcon icon = new ImageIcon(ruta);
            if (icon.getIconWidth() == -1) {
                throw new Exception("Imagen no encontrada o inválida: " + ruta);
            }
            Image image = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(image));
            lblImagen.setToolTipText(ruta); // Establecer la ruta como tooltip para obtenerla al eliminar
            
            // Agregar el listener directamente aquí
            lblImagen.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    seleccionarImagen(lblImagen);
                }
            });
            
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
            lblImagen.setText("Imagen no encontrada");
        }

        return lblImagen;
    }
        private void seleccionarImagen(JLabel imagen) {
        // Quitar el borde de selección de todas las imágenes
        Component[] componentes = panelImagenes.getComponents();
        for (Component c : componentes) {
            if (c instanceof JLabel) {
                ((JLabel) c).setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }
        }
        
        // Establecer el borde de selección para la imagen actual
        imagen.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
        imagenSeleccionada = imagen;
        System.out.println("Imagen seleccionada: " + imagen.getToolTipText());
    }
 public void agregarImagen(String nombreSitio) {
        JFileChooser fileChooser = new JFileChooser(new File("src/img"));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes", "jpg", "png", "jpeg"));
        int opcion = fileChooser.showOpenDialog(this);
        if (opcion == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getName();

            // Pedir el tipo de imagen al usuario
            String[] opciones = {"Como llegar", "Restaurantes", "sitio de interes", "Parqueadero", "Alojamiento"};
            String tipoSeleccionado = (String) JOptionPane.showInputDialog(
                this,
                "Seleccione el tipo de imagen:",
                "Tipo de Imagen",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
            );

            if (tipoSeleccionado != null) {
                int tipoImagen = switch (tipoSeleccionado) {
                    case "Como llegar" -> 1;
                    case "Restaurantes" -> 2;
                    case "sitio de interes" -> 3;
                    case "Parqueadero" -> 4;
                    case "Alojamiento" -> 6;
                    default -> -1; // Tipo inválido
                       
                };
                modelo.agregarImagenPorNombreSitio(nombreSitio, ruta, tipoImagen);
                cargarImagenes();

                if (tipoImagen != -1) {

                } else {
                    JOptionPane.showMessageDialog(this, "Tipo de imagen no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

private void eliminarImagen(String nombreSitio) {
    if (imagenSeleccionada == null) {
        JOptionPane.showMessageDialog(this, "Seleccione una imagen para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String rutaCompleta = imagenSeleccionada.getToolTipText();
    if (rutaCompleta != null && !rutaCompleta.trim().isEmpty()) {
        // Extraer solo el nombre del archivo de la ruta completa
        String nombreArchivo = new File(rutaCompleta).getName();
        System.out.println("Ruta completa: " + rutaCompleta);
        System.out.println("Intentando eliminar imagen: " + nombreArchivo);
        
        // Mostrar diálogo de confirmación
        int confirmacion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar esta imagen?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Llamar al modelo para eliminar la imagen
            boolean eliminado = modelo.quitarImagenPorNombreSitio(nombreArchivo);
            if (eliminado) {
                JOptionPane.showMessageDialog(this, "Imagen eliminada correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            cargarImagenes(); // Recargar imágenes después de eliminar
        }
    } else {
        System.err.println("No se pudo obtener la ruta de la imagen seleccionada.");
        JOptionPane.showMessageDialog(this, "Error al obtener la ruta de la imagen.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

// Corrigiendo el método para agregar selección de imágenes
private void agregarSeleccionImagen() {
    Component[] componentes = panelImagenes.getComponents();
    for (Component componente : componentes) {
        if (componente instanceof JLabel lblImagen) {
            lblImagen.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    // Quitar bordes de selección de todas las imágenes
                    for (Component c : panelImagenes.getComponents()) {
                        if (c instanceof JLabel) {
                            ((JLabel) c).setBorder(BorderFactory.createLineBorder(Color.GRAY));
                        }
                    }
                    // Establecer borde de selección para la imagen actual
                    lblImagen.setBorder(new LineBorder(Color.BLUE, 3));
                }
            });
        }
    }

}
}  
package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class VentanaTuristica extends JFrame {
    
    // Panel interno que maneja el renderizado y la interacción con la imagen
    private class PanelImagen360 extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
        
        private BufferedImage imagen;
        private double zoom = 1.0;
        private int desplazamientoX = 0, desplazamientoY = 0;
        private int ultimoMouseX, ultimoMouseY;
        private double velocidadX = 0, velocidadY = 0;
        private Timer timerInercia;
        
        public PanelImagen360(BufferedImage img) {
            
            this.imagen = img;
            
            addMouseMotionListener(this);
            addMouseListener(this);
            addMouseWheelListener(this);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            setPreferredSize(new Dimension(800, 600));
            setMinimumSize(new Dimension(400, 300));
            
            timerInercia = new Timer(32, e -> {
                desplazamientoX -= velocidadX;
                desplazamientoY -= velocidadY;

                velocidadX *= 0.90;
                velocidadY *= 0.90;
                
                if (Math.abs(velocidadX) < 0.5 && Math.abs(velocidadY) < 0.5) {
                    velocidadX = 0;
                    velocidadY = 0;
                    timerInercia.stop();
                }

                repaint();
            });
        }

        @Override
        protected void paintComponent(Graphics g) { 
            super.paintComponent(g);                
            if (imagen != null) {
                Graphics2D g2 = (Graphics2D) g;
                int anchoPanel = getWidth();
                int altoPanel = getHeight();

                int anchoImagen = (int) (imagen.getWidth() * zoom);
                int altoImagen = (int) (imagen.getHeight() * zoom);

                for (int x = -anchoImagen; x < anchoPanel + anchoImagen; x += anchoImagen) {
                    for (int y = -altoImagen; y < altoPanel + altoImagen; y += altoImagen) {
                        g2.drawImage(imagen, x - (desplazamientoX % anchoImagen), y - (desplazamientoY % altoImagen),
                                anchoImagen, altoImagen, this);
                    }
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int dx = e.getX() - ultimoMouseX;
            int dy = e.getY() - ultimoMouseY;

            desplazamientoX -= dx;
            desplazamientoY -= dy;

            velocidadX = dx;
            velocidadY = dy;

            ultimoMouseX = e.getX();
            ultimoMouseY = e.getY();

            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            ultimoMouseX = e.getX();
            ultimoMouseY = e.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (velocidadX != 0 || velocidadY != 0) {
                timerInercia.start();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            timerInercia.stop();
        }

        @Override public void mouseClicked(MouseEvent e) {}
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            double factorZoom = e.getPreciseWheelRotation() < 0 ? 1.1 : 0.9;
            double nuevoZoom = zoom * factorZoom;

            // Zoom entre 0.5x y 3.0x para una correcta navegación de la foto en 360
            if (nuevoZoom < 0.5 || nuevoZoom > 3.0) return;

            int mouseX = e.getX();
            int mouseY = e.getY();

            double relX = (mouseX + desplazamientoX) / zoom;
            double relY = (mouseY + desplazamientoY) / zoom;

            zoom = nuevoZoom;

            desplazamientoX = (int) (relX * zoom - mouseX);
            desplazamientoY = (int) (relY * zoom - mouseY);

            repaint();
        }
        
        public void configurarVistaInicial(double zoom, int desplazamientoX, int desplazamientoY) {
            this.zoom = zoom;
            this.desplazamientoX = desplazamientoX;
            this.desplazamientoY = desplazamientoY;
            repaint();
        }
    }

    private PanelImagen360 panelImagen;
    
    public VentanaTuristica(String rutaImagen) {
        super("Visor 360°");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Carga de la imagen con múltiples estrategias
        BufferedImage imagenCargada = cargarImagen(rutaImagen);
        
        if (imagenCargada != null) {
            panelImagen = new PanelImagen360(imagenCargada);
            add(panelImagen, BorderLayout.CENTER);
            
            // Configuramos la vista inicial después de que la ventana sea visible
            SwingUtilities.invokeLater(() -> {
                panelImagen.configurarVistaInicial(0.5, -250, 600);
            });
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen desde: " + rutaImagen, 
                    "Error al cargar imagen", JOptionPane.ERROR_MESSAGE);
            // Crear un panel vacío para que al menos la ventana se muestre
            add(new JPanel(), BorderLayout.CENTER);
        }
    }
    
    // Método mejorado para cargar imágenes usando múltiples estrategias
    private BufferedImage cargarImagen(String rutaImagen) {
        BufferedImage imagen = null;
        
        try {
            // Estrategia 1: Intenta cargar como recurso (con o sin "/" inicial)
            String recursoPath = rutaImagen.startsWith("/") ? rutaImagen : "/" + rutaImagen;
            InputStream is = getClass().getResourceAsStream(recursoPath);
            
            if (is != null) {
                imagen = ImageIO.read(is);
                System.out.println("Imagen cargada como recurso: " + recursoPath);
                return imagen;
            }
            
            // Estrategia 2: Intenta cargar directamente como archivo
            File archivo = new File(rutaImagen);
            if (archivo.exists()) {
                imagen = ImageIO.read(archivo);
                System.out.println("Imagen cargada como archivo: " + archivo.getAbsolutePath());
                return imagen;
            }
            
            // Estrategia 3: Buscar en diferentes rutas relativas
            String[] rutasAlternativas = {
                "src/" + rutaImagen,
                rutaImagen.startsWith("src/") ? rutaImagen : "src/" + rutaImagen,
                rutaImagen.replace("src/", ""),
                "resources/" + rutaImagen,
                "../resources/" + rutaImagen
            };
            
            for (String ruta : rutasAlternativas) {
                archivo = new File(ruta);
                if (archivo.exists()) {
                    imagen = ImageIO.read(archivo);
                    System.out.println("Imagen cargada desde ruta alternativa: " + archivo.getAbsolutePath());
                    return imagen;
                }
            }
            
            // Si no se encontró, lanzar error descriptivo
            throw new Exception("No se encontró el archivo en ninguna de las rutas probadas. " +
                    "Ruta original: " + rutaImagen);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al cargar la imagen: " + e.getMessage() + "\n" +
                    "Ruta intentada: " + rutaImagen, 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;
        }
    }

    // Método main para pruebas
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Puedes probar con diferentes rutas
            VentanaTuristica ventana = new VentanaTuristica("src/img/IMG_3635.jpg");
            ventana.setVisible(true);
        });
    }
}
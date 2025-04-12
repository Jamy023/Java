/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;



public class VentanaTuristica extends JPanel  implements MouseMotionListener, MouseListener, MouseWheelListener {
    
    private BufferedImage imagen;
    private double zoom = 1.0;
    private int desplazamientoX = 0, desplazamientoY = 0;
    private int ultimoMouseX, ultimoMouseY;

    private double velocidadX = 0, velocidadY = 0;
    private Timer timerInercia;
    
    public VentanaTuristica(String rutaImagen) {
        try {
            imagen = ImageIO.read(new File(rutaImagen));
            addMouseMotionListener(this);
            addMouseListener(this);
            addMouseWheelListener(this);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            setPreferredSize(new Dimension(800, 600));
            setMinimumSize(new Dimension(400, 300));
            setLayout(new BorderLayout());
            


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

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen: " + e.getMessage());
        }
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

    public static void main(String[] args) {
        JFrame ventana = new JFrame("Visor 360°");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        int anchoVentana = 800;
        int altoVentana = 600;
        ventana.setSize(anchoVentana, altoVentana);

        // 2. Centrado preciso en pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - anchoVentana) / 2;
        int y = (screenSize.height - altoVentana) / 3; 
        ventana.setLocation(x, y);

        VentanaTuristica visor = new VentanaTuristica("src/img/IMG_3635.jpg");
        ventana.add(visor);
        visor.setSize(ventana.getSize());
        
        
        
        ventana.pack();
        ventana.setVisible(true);

        SwingUtilities.invokeLater(() -> {
            visor.zoom = 0.5;
            visor.desplazamientoX = -150;
            visor.desplazamientoY = 2100;
            visor.repaint();
        });
    }
}








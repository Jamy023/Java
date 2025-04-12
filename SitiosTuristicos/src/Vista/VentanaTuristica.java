package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class VentanaTuristica extends JFrame implements MouseMotionListener, MouseListener, MouseWheelListener {

    private BufferedImage imagen;
    private double zoom = 1.0;
    private int desplazamientoX = 0, desplazamientoY = 0;
    private int ultimoMouseX, ultimoMouseY;
    private double velocidadX = 0, velocidadY = 0;
    private Timer timerInercia;

    public VentanaTuristica(String rutaImagen) {
        super("Foto 360°");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel() {
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
        };

        try {
            // Primero intenta cargar desde recursos internos (rutas abreviadas)
            java.net.URL recurso = getClass().getResource(rutaImagen);
            if (recurso != null) {
                imagen = ImageIO.read(recurso);
            } else {
                // Si no se encuentra como recurso, intenta cargar como archivo externo
                imagen = ImageIO.read(new File(rutaImagen));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen: " + e.getMessage());
        }

        panel.addMouseMotionListener(this);
        panel.addMouseListener(this);
        panel.addMouseWheelListener(this);
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panel.setPreferredSize(new Dimension(800, 600));

        setContentPane(panel);

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
    public void mouseDragged(MouseEvent e) {
        int deltaX = e.getX() - ultimoMouseX;
        int deltaY = e.getY() - ultimoMouseY;

        desplazamientoX += deltaX;
        desplazamientoY += deltaY;

        velocidadX = -deltaX;
        velocidadY = -deltaY;

        repaint();

        ultimoMouseX = e.getX();
        ultimoMouseY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        ultimoMouseX = e.getX();
        ultimoMouseY = e.getY();
        timerInercia.stop();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        timerInercia.start();
    }

    @Override public void mouseMoved(MouseEvent e) {}
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double delta = 0.05f * e.getPreciseWheelRotation();
        zoom -= delta;
        if (zoom < 0.1) zoom = 0.1;
        if (zoom > 5.0) zoom = 5.0;
        repaint();
    }

    // Método main para probarla
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaTuristica("/img/LasPavas360.JPEG").setVisible(true);
        });
    }
}

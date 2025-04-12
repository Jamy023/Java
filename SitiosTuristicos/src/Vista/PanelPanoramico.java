package vista;

import javax.swing.*;
import java.awt.*;

public class PanelPanoramico extends JLabel {
    private final Image imagenPanoramica;
    private int posicionX = 0;
    private int velocidad = 1;
    private boolean autoMovimiento = true;
    private int inicioArrastreX;

    public PanelPanoramico(String rutaImagen) {
        // Se busca el recurso usando la ruta abreviada proporcionada
        java.net.URL urlImagen = getClass().getResource(rutaImagen);
        if (urlImagen != null) {
            imagenPanoramica = new ImageIcon(urlImagen).getImage();
        } else {
            // Si no se encuentra, se asigna una imagen por defecto o se notifica el error
            System.out.println("No se encontró la imagen en la ruta: " + rutaImagen);
            imagenPanoramica = null;
        }

        // Establece el tamaño preferido basado en la imagen (si existe)
        if (imagenPanoramica != null) {
            setPreferredSize(new Dimension(800, imagenPanoramica.getHeight(null)));
        } else {
            setPreferredSize(new Dimension(800, 600));
        }
        
        // Timer para movimiento automático
        Timer timer = new Timer(30, e -> {
            if (autoMovimiento && imagenPanoramica != null) {
                posicionX -= velocidad;
                if (posicionX <= -imagenPanoramica.getWidth(null)) {
                    posicionX = 0;
                }
                repaint();
            }
        });
        timer.start();

        // Eventos del mouse para mover manualmente
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                inicioArrastreX = e.getX();
                autoMovimiento = false;
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                autoMovimiento = true;
            }
        });

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                int desplazamiento = e.getX() - inicioArrastreX;
                posicionX += desplazamiento;
                inicioArrastreX = e.getX();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenPanoramica != null) {
            int imgWidth = imagenPanoramica.getWidth(null);
            // Dibuja la imagen dos veces para crear un efecto de desplazamiento infinito
            g.drawImage(imagenPanoramica, posicionX, 0, this);
            g.drawImage(imagenPanoramica, posicionX + imgWidth, 0, this);
        }
    }
}


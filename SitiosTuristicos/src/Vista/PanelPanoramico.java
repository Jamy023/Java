/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;

/**
 *
 * @author userx
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelPanoramico extends JLabel {
    private final Image imagenPanoramica;
    private int posicionX = 0;
    private int velocidad = 1;
    private boolean autoMovimiento = true;
    private int inicioArrastreX;

    public PanelPanoramico(String rutaImagen) {
        imagenPanoramica = new ImageIcon(rutaImagen).getImage();
        setPreferredSize(new Dimension(800, imagenPanoramica.getHeight(null))); 

        // Timer para movimiento automático
        Timer timer = new Timer(30, e -> {
            if (autoMovimiento) {
                posicionX -= velocidad;
                if (posicionX <= -imagenPanoramica.getWidth(null)) {
                    posicionX = 0;
                }
                repaint();
            }
        });
        timer.start();

        // Eventos del mouse para mover manualmente
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                inicioArrastreX = e.getX();
                autoMovimiento = false;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                autoMovimiento = true;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
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
        int imgWidth = imagenPanoramica.getWidth(null);

        // Dibuja la imagen dos veces para el efecto infinito
        g.drawImage(imagenPanoramica, posicionX, 0, this);
        g.drawImage(imagenPanoramica, posicionX + imgWidth, 0, this);
    }
}

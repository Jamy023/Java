/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Controlador.controlador_sitios_turisticos;
import static Vista.Vista_Municipio_Florencia.aplicarEstiloEstetico;
import java.awt.Image;
import javax.swing.ImageIcon;
import Modelo.modelo_sitios_turisticos;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author userx
 */
public class vista_sitios_turisticos extends javax.swing.JFrame {

    /**
     * Creates new form vista_sitios_turisticos
     */
    String foto ="C:\\Users\\userx\\Documents\\Java ADSO\\SitiosTuristicos\\src\\imagenes\\fondo.png";
    Controlador.controlador_sitios_turisticos controlador;
    Modelo.modelo_sitios_turisticos modelo = new Modelo.modelo_sitios_turisticos();
    boolean click = false;
    public int numeroEstrella = 0;
    private Color hoverColor = new Color(0, 75, 173); // Color hover para botones
    private Color buttonColor = new Color(0, 102, 204); // Azul moderno
    
    public vista_sitios_turisticos(String sitio_turistico) {
        initComponents();
        

        controlador = new controlador_sitios_turisticos(this, modelo, sitio_turistico);
        controlador.iniciar();
        
        aplicarEstiloEstetico(btn_volver);
        aplicarEstiloEstetico(btn_vista360);
        aplicarEstiloEstetico(btn_calificar);
        
        aplicarEstiloTituloTurismo(lblNombre_sitio);
        Subtitulo(lbl_descripcion);
        Subtitulo(lbl_servicios);
        
        personalizarEstrellas();
    
        
        
    }
    private void personalizarEstrellas() {
        // Configurar colores principales
        getContentPane().setBackground(new Color(245, 245, 245));
        // Estilo para las estrellas
        Font estrellaFont = new Font("Segoe UI", Font.PLAIN, 36);
        for(JLabel estrella : new JLabel[]{estrella1, estrella2, estrella3, estrella4, estrella5}) {
            estrella.setFont(estrellaFont);
            estrella.setText("☆");
            estrella.setForeground(new Color(200, 200, 200));
        }
        // Estilo botón calificar
        btn_calificar.setBackground(new Color(76, 175, 80));
        btn_calificar.setForeground(Color.WHITE);
        btn_calificar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(56, 142, 60)),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        btn_calificar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Estilo imagen principal
        img.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }
    
    public static void aplicarEstiloTituloTurismo(JLabel titulo) {
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titulo.setForeground(new Color(33, 67, 121)); // Azul inspirado en el mar
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        titulo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Efecto de relieve y sombra
        titulo.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
            
            @Override
            public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            
            // Configuración de calidad
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

            // Fondo degradado oceánico
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(173, 216, 230), // Azul claro
                c.getWidth(), 0, new Color(240, 248, 255) // Azul blanquecino
            );
            g2.setPaint(gradient);
            g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 25, 25);

            // Sombra del texto
            g2.setColor(new Color(0, 0, 0, 50));
            g2.setFont(c.getFont());
            FontMetrics fm = g2.getFontMetrics();
            String text = ((JLabel)c).getText();
            int x = (c.getWidth() - fm.stringWidth(text)) / 2;
            int y = ((c.getHeight() - fm.getHeight()) / 2) + fm.getAscent() + 2;
            g2.drawString(text, x, y);

            // Texto principal
            g2.setColor(c.getForeground());
            g2.drawString(text, x, y - 2);

            g2.dispose();
        }
    });

    // Efecto hover turístico
    titulo.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            titulo.setForeground(new Color(0, 102, 153)); // Azul más intenso
            titulo.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 0, new Color(255, 165, 0))); // Subrayado naranja
        }

        @Override
        public void mouseExited(MouseEvent e) {
                titulo.setForeground(new Color(33, 67, 121));
                titulo.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
            }
        });
    }
    public static void Subtitulo(JLabel subtitulo) {
    subtitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
    subtitulo.setForeground(new Color(0, 102, 51)); // Verde bosque
    subtitulo.setHorizontalAlignment(JLabel.CENTER);
    subtitulo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    
    // Efecto de fondo sutil con degradado
    subtitulo.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Degradado azul-verde suave
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(173, 216, 230), // Azul claro
                c.getWidth(), 0, new Color(144, 238, 144)  // Verde pastel
            );
            g2.setPaint(gradient);
            g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15);
            
            super.paint(g2, c);
            g2.dispose();
        }
    });

    // Efecto hover natural
    subtitulo.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            subtitulo.setForeground(new Color(0, 153, 76)); // Verde más vivo
            subtitulo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(218, 165, 32)), // Oro terroso
                BorderFactory.createEmptyBorder(10, 20, 8, 20)
            ));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            subtitulo.setForeground(new Color(0, 102, 51));
            subtitulo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        }
    });
    
    // Efecto de texto con relieve sutil
    subtitulo.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
        @Override
        protected void paintEnabledText(JLabel l, Graphics g, String s, int textX, int textY) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            
            // Sombra ligera
            g2.setColor(new Color(255, 255, 255, 80));
            g2.drawString(s, textX + 1, textY + 1);
            
            // Texto principal
            g2.setColor(l.getForeground());
            g2.drawString(s, textX, textY);
            
            g2.dispose();
            }
        });
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        img = new javax.swing.JLabel();
        lblNombre_sitio = new javax.swing.JLabel();
        btn_volver = new javax.swing.JButton();
        lblDescripcion = new javax.swing.JLabel();
        lbl_descripcion = new javax.swing.JLabel();
        lbl_servicios = new javax.swing.JLabel();
        estrella5 = new javax.swing.JLabel();
        estrella1 = new javax.swing.JLabel();
        estrella2 = new javax.swing.JLabel();
        estrella3 = new javax.swing.JLabel();
        estrella4 = new javax.swing.JLabel();
        Servicios = new javax.swing.JPanel();
        btn_vista360 = new javax.swing.JButton();
        btn_calificar = new javax.swing.JButton();
        lblPrecio = new javax.swing.JLabel();
        fondo = new javax.swing.JLabel();
        lblCalificacion = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(245, 245, 245));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        img.setText("jLabel1");
        img.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(img, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 111, 1720, 340));

        lblNombre_sitio.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 36)); // NOI18N
        lblNombre_sitio.setText("jLabel1");
        getContentPane().add(lblNombre_sitio, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 980, 60));

        btn_volver.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        btn_volver.setText("Volver al mapa");
        btn_volver.setBorder(null);
        btn_volver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_volverMouseClicked(evt);
            }
        });
        getContentPane().add(btn_volver, new org.netbeans.lib.awtextra.AbsoluteConstraints(1710, 10, 194, 60));

        lblDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        lblDescripcion.setText("jLabel1");
        lblDescripcion.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblDescripcion.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lblDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 550, 590, 320));

        lbl_descripcion.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        lbl_descripcion.setText("DESCRIPCION DEL LUGAR");
        getContentPane().add(lbl_descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 490, -1, -1));

        lbl_servicios.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        lbl_servicios.setText("Servicios disponibles");
        getContentPane().add(lbl_servicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 480, -1, -1));

        estrella5.setText("jLabel2");
        estrella5.setPreferredSize(new java.awt.Dimension(50, 50));
        estrella5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                estrella5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                estrella5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                estrella5MouseExited(evt);
            }
        });
        getContentPane().add(estrella5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1820, 910, 50, 50));

        estrella1.setText("jLabel2");
        estrella1.setPreferredSize(new java.awt.Dimension(50, 50));
        estrella1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                estrella1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                estrella1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                estrella1MouseExited(evt);
            }
        });
        getContentPane().add(estrella1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1580, 910, 50, 50));

        estrella2.setText("jLabel2");
        estrella2.setPreferredSize(new java.awt.Dimension(50, 50));
        estrella2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                estrella2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                estrella2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                estrella2MouseExited(evt);
            }
        });
        getContentPane().add(estrella2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1640, 910, 50, 50));

        estrella3.setText("jLabel2");
        estrella3.setPreferredSize(new java.awt.Dimension(50, 50));
        estrella3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                estrella3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                estrella3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                estrella3MouseExited(evt);
            }
        });
        getContentPane().add(estrella3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1700, 910, 50, 50));

        estrella4.setText("jLabel2");
        estrella4.setPreferredSize(new java.awt.Dimension(50, 50));
        estrella4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                estrella4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                estrella4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                estrella4MouseExited(evt);
            }
        });
        getContentPane().add(estrella4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1760, 910, 50, 50));

        Servicios.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout ServiciosLayout = new javax.swing.GroupLayout(Servicios);
        Servicios.setLayout(ServiciosLayout);
        ServiciosLayout.setHorizontalGroup(
            ServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );
        ServiciosLayout.setVerticalGroup(
            ServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 368, Short.MAX_VALUE)
        );

        getContentPane().add(Servicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 530, 310, -1));

        btn_vista360.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_vista360.setText("vista 360°");
        getContentPane().add(btn_vista360, new org.netbeans.lib.awtextra.AbsoluteConstraints(1530, 10, -1, 60));

        btn_calificar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_calificar.setText("Calificar");
        getContentPane().add(btn_calificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1670, 970, -1, -1));

        lblPrecio.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblPrecio.setText("jLabel1");
        getContentPane().add(lblPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 530, 190, 60));

        fondo.setBackground(new java.awt.Color(245, 245, 245));
        getContentPane().add(fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        lblCalificacion.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblCalificacion.setText("jLabel1");
        getContentPane().add(lblCalificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 630, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void estrella1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella1MouseEntered
        modelo.cargarEstrellas(this, false);
        modelo.estrellaCambiarColor(this, 1);
    }//GEN-LAST:event_estrella1MouseEntered

    private void estrella2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella2MouseEntered
        modelo.cargarEstrellas(this, false);
        modelo.estrellaCambiarColor(this, 2);
    }//GEN-LAST:event_estrella2MouseEntered

    private void estrella3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella3MouseEntered
        modelo.cargarEstrellas(this, false);
        modelo.estrellaCambiarColor(this, 3);
    }//GEN-LAST:event_estrella3MouseEntered

    private void estrella4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella4MouseClicked
        modelo.cargarEstrellas(this, false);
        if(click == true && (numeroEstrella != 4))
        {
            modelo.cargarEstrellas(this, false);
            modelo.estrellaCambiarColor(this, 4);
            numeroEstrella = 4;
        }
        else if(click == true)
        {
            click = false;
            numeroEstrella = 0;
        }
        else
        {
            click = true;
            modelo.estrellaCambiarColor(this, 4);
            numeroEstrella = 4;
        }
    }//GEN-LAST:event_estrella4MouseClicked

    private void estrella4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella4MouseEntered
        modelo.cargarEstrellas(this, false);
        modelo.estrellaCambiarColor(this, 4);
    }//GEN-LAST:event_estrella4MouseEntered

    private void estrella5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella5MouseEntered
        modelo.cargarEstrellas(this, false);
        modelo.estrellaCambiarColor(this, 5);
    }//GEN-LAST:event_estrella5MouseEntered

    private void estrella1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella1MouseExited
        modelo.cargarEstrellas(this, false);
        if(click)
        {
            modelo.estrellaCambiarColor(this, numeroEstrella);
        }
    }//GEN-LAST:event_estrella1MouseExited

    private void estrella2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella2MouseExited
        modelo.cargarEstrellas(this, false);
        if(click)
        {
            modelo.estrellaCambiarColor(this, numeroEstrella);
        }
    }//GEN-LAST:event_estrella2MouseExited

    private void estrella3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella3MouseExited
        modelo.cargarEstrellas(this, false);
        if(click)
        {
            modelo.estrellaCambiarColor(this, numeroEstrella);
        }
    }//GEN-LAST:event_estrella3MouseExited

    private void estrella4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella4MouseExited
        modelo.cargarEstrellas(this, false);
        if(click)
        {
            modelo.estrellaCambiarColor(this, numeroEstrella);
        }
    }//GEN-LAST:event_estrella4MouseExited

    private void estrella5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella5MouseExited
        modelo.cargarEstrellas(this, false);
        if(click)
        {
            modelo.estrellaCambiarColor(this, numeroEstrella);
        }
    }//GEN-LAST:event_estrella5MouseExited

    private void estrella1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella1MouseClicked
        modelo.cargarEstrellas(this, false);
        if(click == true && (numeroEstrella != 1))
        {
            modelo.cargarEstrellas(this, false);
            modelo.estrellaCambiarColor(this, 1);
            numeroEstrella = 1;
        }
        else if(click == true)
        {
            click = false;
            numeroEstrella = 0;
        }
        else
        {
            click = true;
            modelo.estrellaCambiarColor(this, 1);
            numeroEstrella = 1;
        }
    }//GEN-LAST:event_estrella1MouseClicked

    private void estrella2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella2MouseClicked
        modelo.cargarEstrellas(this, false);
        if(click == true && (numeroEstrella != 2))
        {
            modelo.cargarEstrellas(this, false);
            modelo.estrellaCambiarColor(this, 2);
            numeroEstrella = 2;
        }
        else if(click == true)
        {
            click = false;
            numeroEstrella = 0;
        }
        else
        {
            click = true;
            modelo.estrellaCambiarColor(this, 2);
            numeroEstrella = 2;
        }
    }//GEN-LAST:event_estrella2MouseClicked

    private void estrella3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella3MouseClicked
        modelo.cargarEstrellas(this, false);
        if(click == true && (numeroEstrella != 3))
        {
            modelo.cargarEstrellas(this, false);
            modelo.estrellaCambiarColor(this, 3);
            numeroEstrella = 3;
        }
        else if(click == true)
        {
            click = false;
            numeroEstrella = 0;
        }
        else
        {
            click = true;
            modelo.estrellaCambiarColor(this, 3);
            numeroEstrella = 3;
        }
    }//GEN-LAST:event_estrella3MouseClicked

    private void estrella5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_estrella5MouseClicked
        modelo.cargarEstrellas(this, false);
        if(click == true && (numeroEstrella != 5))
        {
            modelo.cargarEstrellas(this, false);
            modelo.estrellaCambiarColor(this, 5);
            numeroEstrella = 5;
        }
        else if(click == true)
        {
            click = false;
            numeroEstrella = 0;
        }
        else
        {
            click = true;
            modelo.estrellaCambiarColor(this, 5);
            numeroEstrella = 5;
        }
    }//GEN-LAST:event_estrella5MouseClicked

    private void btn_volverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_volverMouseClicked
      //Vista_Municipio_Florencia r = new Vista_Municipio_Florencia();
      //r.setVisible(true);
      this.dispose();
    }//GEN-LAST:event_btn_volverMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(vista_sitios_turisticos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(vista_sitios_turisticos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(vista_sitios_turisticos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(vista_sitios_turisticos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new vista_sitios_turisticos("Las Pavas").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel Servicios;
    public javax.swing.JButton btn_calificar;
    public javax.swing.JButton btn_vista360;
    public javax.swing.JButton btn_volver;
    public javax.swing.JLabel estrella1;
    public javax.swing.JLabel estrella2;
    public javax.swing.JLabel estrella3;
    public javax.swing.JLabel estrella4;
    public javax.swing.JLabel estrella5;
    public javax.swing.JLabel fondo;
    public javax.swing.JLabel img;
    public javax.swing.JLabel lblCalificacion;
    public javax.swing.JLabel lblDescripcion;
    public javax.swing.JLabel lblNombre_sitio;
    public javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lbl_descripcion;
    private javax.swing.JLabel lbl_servicios;
    // End of variables declaration//GEN-END:variables
}

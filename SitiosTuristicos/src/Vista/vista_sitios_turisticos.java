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
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author userx
 */
public class vista_sitios_turisticos extends javax.swing.JFrame {

    /**
     * Creates new form vista_sitios_turisticos
     */

    Controlador.controlador_sitios_turisticos controlador;
    Modelo.modelo_sitios_turisticos modelo = new Modelo.modelo_sitios_turisticos();
    boolean click = false;
    public int numeroEstrella = 0;
    private Color hoverColor = new Color(0, 75, 173); // Color hover para botones
    private Color buttonColor = new Color(0, 102, 204); // Azul moderno
    
    public vista_sitios_turisticos(String sitio_turistico) {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        

        controlador = new controlador_sitios_turisticos(this, modelo, sitio_turistico);
        controlador.iniciar();
        
        aplicarEstiloEstetico(btn_volver);
        aplicarEstiloEstetico(btn_vista360);
        aplicarEstiloEstetico(btn_calificar);
        aplicarEstiloEstetico2(Servicios);
        
        aplicarEstiloEstetico3(lblNombre);
        Subtitulo(lbl_descripcion);
        Subtitulo(lbl_descripcion1);
        Subtitulo(lbl_descripcion4);
        Subtitulo(lbl_servicios);
        
        personalizarEstrellas();
        fondo(foto, "src\\img\\FONDO33.png");
        
        
        
    }


    public void fondo(JLabel label, String ruta) {
        ImageIcon icon = new ImageIcon("src\\img\\FONDO33.png");
        Image img = icon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
    }
        public static void aplicarEstiloEstetico2(JPanel panel) {
        panel.setBackground(new Color(230, 255, 230)); // Verde muy claro estilo SENA
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setOpaque(false);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(new Color(210, 245, 210)); // Efecto al pasar el mouse
                panel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(new Color(230, 255, 230));
                panel.repaint();
            }
        });

        // 🎨 Estilo gráfico personalizado (bordes redondeados)
        panel.setUI(new javax.swing.plaf.PanelUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                int w = c.getWidth();
                int h = c.getHeight();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(panel.getBackground());
                g2.fillRoundRect(0, 0, w, h, 30, 30); // Bordes redondeados

                g2.dispose();
                super.paint(g, c);
            }
        });
    }
        
        
    public static void aplicarEstiloEstetico3(JLabel label) {
    label.setFont(new Font("Segoe UI", Font.BOLD, 16));
    label.setForeground(new Color(33, 37, 41)); // Gris oscuro sobrio
    label.setOpaque(true);
    label.setBackground(new Color(144, 238, 144)); // Verde suave estilo SENA
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setVerticalAlignment(SwingConstants.CENTER);
    label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    // Efecto hover con MouseListener
    label.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            label.setBackground(new Color(124, 218, 124)); // Verde más intenso
            label.repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            label.setBackground(new Color(144, 238, 144));
            label.repaint();
        }
    });

    // 🎨 Estilo gráfico personalizado con bordes redondeados
    label.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            int w = c.getWidth();
            int h = c.getHeight();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(label.getBackground());
            g2.fillRoundRect(0, 0, w, h, 30, 30); // Bordes redondeados

            g2.dispose();
            super.paint(g, c); // Dibuja el texto y demás
        }
        });
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

        jPanel1 = new javax.swing.JPanel();
        btn_volver = new javax.swing.JButton();
        lbl_descripcion1 = new javax.swing.JLabel();
        estrella1 = new javax.swing.JLabel();
        btn_vista360 = new javax.swing.JButton();
        lbl_descripcion4 = new javax.swing.JLabel();
        lbl_servicios = new javax.swing.JLabel();
        estrella3 = new javax.swing.JLabel();
        btn_calificar = new javax.swing.JButton();
        estrella2 = new javax.swing.JLabel();
        estrella4 = new javax.swing.JLabel();
        lbl_descripcion = new javax.swing.JLabel();
        lblCalificacion = new javax.swing.JLabel();
        estrella5 = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        img = new javax.swing.JLabel();
        Servicios = new javax.swing.JPanel();
        lblPrecio = new javax.swing.JLabel();
        foto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 51, 51));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(java.awt.Color.lightGray);
        jPanel1.setPreferredSize(new java.awt.Dimension(1920, 1080));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_volver.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        btn_volver.setText("Volver al mapa");
        btn_volver.setBorder(null);
        btn_volver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_volverMouseClicked(evt);
            }
        });
        jPanel1.add(btn_volver, new org.netbeans.lib.awtextra.AbsoluteConstraints(1700, 20, 194, 60));

        lbl_descripcion1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        lbl_descripcion1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_descripcion1.setText("DESCRIPCION DEL LUGAR");
        jPanel1.add(lbl_descripcion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 530, -1));

        estrella1.setBackground(new java.awt.Color(255, 51, 51));
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
        jPanel1.add(estrella1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1610, 920, 50, 50));

        btn_vista360.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_vista360.setText("vista 360°");
        jPanel1.add(btn_vista360, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 20, 190, 60));

        lbl_descripcion4.setFont(new java.awt.Font("Segoe Script", 2, 14)); // NOI18N
        lbl_descripcion4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_descripcion4.setText("PRECIO DE LA ENTRADA");
        jPanel1.add(lbl_descripcion4, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 20, 330, 50));

        lbl_servicios.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        lbl_servicios.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_servicios.setText("Servicios disponibles");
        jPanel1.add(lbl_servicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(1490, 250, 390, -1));

        estrella3.setBackground(java.awt.Color.gray);
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
        jPanel1.add(estrella3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1730, 920, 50, 50));

        btn_calificar.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        btn_calificar.setText("Calificar");
        jPanel1.add(btn_calificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1700, 980, -1, -1));

        estrella2.setBackground(java.awt.Color.gray);
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
        jPanel1.add(estrella2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1670, 920, 50, 50));

        estrella4.setBackground(java.awt.Color.gray);
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
        jPanel1.add(estrella4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1790, 920, 50, 50));

        lbl_descripcion.setFont(new java.awt.Font("Segoe Script", 2, 14)); // NOI18N
        lbl_descripcion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_descripcion.setText("PROMEDIO DE  ESTRELLAS ");
        jPanel1.add(lbl_descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, 400, 50));

        lblCalificacion.setBackground(new java.awt.Color(255, 255, 255));
        lblCalificacion.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblCalificacion.setForeground(new java.awt.Color(255, 255, 255));
        lblCalificacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCalificacion.setText("jLabel1");
        jPanel1.add(lblCalificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 590, 190, 60));

        estrella5.setBackground(java.awt.Color.gray);
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
        jPanel1.add(estrella5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1850, 920, 50, 50));

        lblDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        lblDescripcion.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        lblDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        lblDescripcion.setText("jLabel1");
        lblDescripcion.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanel1.add(lblDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 480, 360));

        lblNombre.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 36)); // NOI18N
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombre.setText("jLabel1");
        lblNombre.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 590, 50));

        img.setText("jLabel1");
        img.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(img, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 250, 840, 550));

        Servicios.setBackground(java.awt.Color.lightGray);

        javax.swing.GroupLayout ServiciosLayout = new javax.swing.GroupLayout(Servicios);
        Servicios.setLayout(ServiciosLayout);
        ServiciosLayout.setHorizontalGroup(
            ServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        ServiciosLayout.setVerticalGroup(
            ServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        jPanel1.add(Servicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(1510, 310, 360, 120));

        lblPrecio.setBackground(new java.awt.Color(255, 255, 255));
        lblPrecio.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblPrecio.setForeground(new java.awt.Color(255, 255, 255));
        lblPrecio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPrecio.setText("jLabel1");
        jPanel1.add(lblPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 70, 190, 60));
        jPanel1.add(foto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

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
    public javax.swing.JLabel foto;
    public javax.swing.JLabel img;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JLabel lblCalificacion;
    public javax.swing.JLabel lblDescripcion;
    public javax.swing.JLabel lblNombre;
    public javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lbl_descripcion;
    private javax.swing.JLabel lbl_descripcion1;
    private javax.swing.JLabel lbl_descripcion4;
    private javax.swing.JLabel lbl_servicios;
    // End of variables declaration//GEN-END:variables
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author COMPUCENTER-DM
 */
public class Vista_Municipio_Florencia extends javax.swing.JFrame {

    /**
     * Creates new form Vista_Municipio_Florencia
     */
    public Vista_Municipio_Florencia() {
        initComponents();
     
        
        foto(lbl_foto, "src\\img\\florencia.png");
        aplicarEstiloEstetico(btn_las_pavas);
        aplicarEstiloEstetico(btn_cascada);
        aplicarEstiloEstetico(jButton3);
        
    }
       public static void aplicarEstiloEstetico(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setForeground(new Color(33, 37, 41)); // Gris oscuro sobrio
        boton.setBackground(new Color(144, 238, 144)); // Verde suave estilo SENA
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.setOpaque(false);

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(124, 218, 124)); // Un verde un poco más intenso
                boton.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(144, 238, 144));
                boton.repaint();
            }
        });

        // 🎨 Estilo gráfico personalizado (minimalista y redondeado)
        boton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                int w = c.getWidth();
                int h = c.getHeight();

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(boton.getBackground());
                g2.fillRoundRect(0, 0, w, h, 30, 30); // Bordes redondeados

                g2.dispose();
                super.paint(g, c);
            }
        });
    }

    public void foto(JLabel label, String ruta) {
        ImageIcon icon = new ImageIcon("src\\img\\florencia.png");
        Image img = icon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));

    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn_las_pavas = new javax.swing.JButton();
        btn_cascada = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        lbl_foto = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(320, 150));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_las_pavas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/punto interes.png"))); // NOI18N
        btn_las_pavas.setText("Las Pavas");
        btn_las_pavas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_las_pavasMouseClicked(evt);
            }
        });
        getContentPane().add(btn_las_pavas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 190, 50));

        btn_cascada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/punto interes.png"))); // NOI18N
        btn_cascada.setText("Cascada la Avispa ");
        getContentPane().add(btn_cascada, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, 200, 50));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/punto interes.png"))); // NOI18N
        jButton3.setText("Reserva Las Palmas");
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 80, 220, 40));

        lbl_foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/florencia.png"))); // NOI18N
        lbl_foto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(lbl_foto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 720));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_las_pavasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_las_pavasMouseClicked
        
    }//GEN-LAST:event_btn_las_pavasMouseClicked
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
            java.util.logging.Logger.getLogger(Vista_Municipio_Florencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Vista_Municipio_Florencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Vista_Municipio_Florencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Vista_Municipio_Florencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Vista_Municipio_Florencia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cascada;
    public javax.swing.JButton btn_las_pavas;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel lbl_foto;
    // End of variables declaration//GEN-END:variables
}

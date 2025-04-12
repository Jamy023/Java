/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista;

import Controlador.Menu_Principal_C;
import Controlador.MenuPrincipalControlador;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class Menu_Principal extends javax.swing.JFrame {

    public javax.swing.JLabel FondoDesenfoque;
    public javax.swing.JButton jbt_admin;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jlb_fondo;
   
    
    public Menu_Principal() {
        initComponents();
        FondoDesenfoque.setVisible(false);
        jbt_admin.setContentAreaFilled(false); 
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        fondo(jlb_fondo, "src\\img\\foto menu.png");
        
        new Menu_Principal_C(this);
        
        InicioSesion vistaLogin = new InicioSesion();
        MenuPrincipalControlador controlador = new MenuPrincipalControlador(vistaLogin,this);
    }
    
    public void fondo(JLabel label, String ruta) {
        ImageIcon icon = new ImageIcon("src\\img\\foto menu.png");
        Image img = icon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));

    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        FondoDesenfoque = new javax.swing.JLabel();
        jbt_admin = new javax.swing.JButton();
        JLabel jLabel5 = new javax.swing.JLabel();
        JLabel jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jlb_fondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FondoDesenfoque.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/FondoDesenfoque.jpg"))); // NOI18N
        FondoDesenfoque.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(FondoDesenfoque, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        jbt_admin.setBorder(null);
        getContentPane().add(jbt_admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 970, 100, 100));

        jLabel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1360, 450, 486, 434));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, 400, 60));
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 310, 1040, 704));

        jlb_fondo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(jlb_fondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1920, 1080));

        pack();
    }// </editor-fold>                        

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
            java.util.logging.Logger.getLogger(Menu_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu_Principal().setVisible(true);
                
            }
        });
    }
    

}
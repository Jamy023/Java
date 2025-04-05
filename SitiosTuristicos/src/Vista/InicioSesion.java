
package Vista;

import Controlador.UsuarioControlador;
import Modelo.Usuario;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class InicioSesion extends javax.swing.JFrame {

    private UsuarioControlador controlador;
    
    public InicioSesion() {
        initComponents();
        //CREAR EL MODELO Y ASIGNAR EL CONTROLADOR
        Usuario modelo = new Usuario();
        controlador = new UsuarioControlador(modelo, this);
        
    }

   
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnInicio = new javax.swing.JButton();
        txtusuario = new javax.swing.JTextField();
        pass = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(600, 300));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Perpetua", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 51));
        jLabel5.setText("ADMINISTRACION");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, 210, 40));

        jLabel4.setFont(new java.awt.Font("Perpetua", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 51));
        jLabel4.setText("PANEL DE");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 70, 150, -1));

        jLabel3.setFont(new java.awt.Font("Perpetua", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 51));
        jLabel3.setText("Contraseña");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 260, 120, -1));

        jLabel2.setFont(new java.awt.Font("Perpetua", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 51));
        jLabel2.setText("Usuario");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 90, -1));

        btnInicio.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnInicio.setText("Iniciar sesión");
        btnInicio.setBorder(null);
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });
        getContentPane().add(btnInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 360, 130, 40));

        txtusuario.setBackground(new java.awt.Color(168, 236, 168));
        txtusuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtusuario.setBorder(null);
        getContentPane().add(txtusuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, 300, 40));

        pass.setBackground(new java.awt.Color(168, 236, 168));
        pass.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pass.setBorder(null);
        getContentPane().add(pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, 300, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fon.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 760, 460));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInicioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        EstiloInicioAdmin.aplicarEstilo();
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
                InicioSesion ventana = new InicioSesion();
                SwingUtilities.updateComponentTreeUI(ventana); 
                ventana.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnInicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    public javax.swing.JPasswordField pass;
    public javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}

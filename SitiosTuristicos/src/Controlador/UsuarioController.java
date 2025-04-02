package Controlador;

import Modelo.Usuario;
import Vista.InicioSesion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class UsuarioController implements ActionListener {
    private Usuario modelo;
    private InicioSesion vista;

    public UsuarioController(Usuario modelo, InicioSesion vista) {
        this.modelo = modelo;
        this.vista = vista;
        
        // Agregar listener al botón de inicio de sesión
        this.vista.getButton_Inicio().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getButton_Inicio()) {
            iniciarSesion();
        }
    }

    private void iniciarSesion() {
        String correo = vista.getTxtUsuario().getText();  
        String clave = new String(vista.getPass().getPassword());
        
        modelo.setCorreo(correo);
        modelo.setClave(clave);
        
        if (modelo.Login()) {
            JOptionPane.showMessageDialog(vista, "Inicio de sesión exitoso.");
            // Aquí puedes abrir la siguiente ventana o interfaz principal
        } else {
            JOptionPane.showMessageDialog(vista, "Correo o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


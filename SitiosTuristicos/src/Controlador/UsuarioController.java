package Controlador;

import Modelo.Usuario;
import Vista.InicioSesion;
import Vista.MenuAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class UsuarioController implements ActionListener {
    private Usuario modelo;
    private InicioSesion vista;

    public UsuarioController(Usuario modelo, InicioSesion vista) {
        this.modelo = modelo;
        this.vista = vista;
        
        //AGREGAR EL LISTENER AL BOTON DE INICIO DE SESION
        this.vista.getButton_Inicio().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getButton_Inicio()) {
            iniciarSesion();
        }
    }
    
    //BOOLEANO PARA VALIDAR EL FORMATO DEL CORREO ELECTRONICO
    private boolean esCorreoValido(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, correo);
    }
    
    //METODO PARA EL INICIO DE SESION DEL ADMINISTRADOR
    private void iniciarSesion() {
        String correo = vista.getTxtUsuario().getText();  
        String clave = new String(vista.getPass().getPassword());
        
        modelo.setCorreo(correo);
        modelo.setClave(clave);
        
        //VALIDA EL CORREO INGRESADO
        if (!esCorreoValido(correo)) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingrese un correo electrónico válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (modelo.Login()) {
            JOptionPane.showMessageDialog(vista, "Inicio de sesión exitoso.");
            MenuAdmin menu = new MenuAdmin();
            menu.setVisible(true);
            vista.dispose();
        } else {
            JOptionPane.showMessageDialog(vista, "Correo o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


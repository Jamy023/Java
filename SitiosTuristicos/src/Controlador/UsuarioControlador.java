/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Usuario;
import Vista.InicioSesion;
import Vista.MenuAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import Vista.PanelAdminMunicipio;

/**
 *
 * @author danie
 */
public class UsuarioControlador implements ActionListener {
    
    private Usuario modelo;
    private InicioSesion vista;
   
    
    public UsuarioControlador(Usuario modelo, InicioSesion vista ){
        this.modelo = modelo;
        this.vista = vista;
        
        this.vista.btnInicio.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btnInicio) {
            Iniciar_Sesion();
        }
    }
    
    //BOOLEANO PARA VALIDAR EL FORMATO DEL CORREO ELECTRONICO
    private boolean esCorreoValido(String correo) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(regex, correo);
    }
    
    public void Iniciar_Sesion() {
    String correo = vista.txtusuario.getText();
    String clave = new String(vista.pass.getPassword());
    
    modelo.setCorreo(correo);
    modelo.setClave(clave);
    
     //VALIDA EL CORREO INGRESADO
    if (!esCorreoValido(correo)) {
        JOptionPane.showMessageDialog(vista, "Por favor, ingrese un correo electrónico válido.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (modelo.Login()) {
        JOptionPane.showMessageDialog(vista, "Inicio de sesión exitoso.");
        
        // Obtener el municipio asociado al usuario (esto depende de tu modelo de datos)
        String nombreMunicipio = ""; // Reemplaza esto con el código para obtener el municipio del usuario
        
        // Abrir el panel de administración de municipio
        PanelAdminMunicipio panelMunicipio = new PanelAdminMunicipio(nombreMunicipio);
        panelMunicipio.cargarDatos();
        panelMunicipio.setVisible(true);
        panelMunicipio.cargarDatos();
        
        
        
        //vista.dispose();
    } else {
        JOptionPane.showMessageDialog(vista, "Correo o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}

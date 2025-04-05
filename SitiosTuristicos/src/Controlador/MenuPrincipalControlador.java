/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.EstiloInicioAdmin;
import Vista.InicioSesion;
import Vista.Menu_Principal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

/**
 *
 * @author danie
 */
public class MenuPrincipalControlador implements ActionListener {
    private Menu_Principal vistaMenu;
    private InicioSesion vistaLogin;
    
    public MenuPrincipalControlador(InicioSesion vistaLogin, Menu_Principal vistaMenu){
        this.vistaLogin = vistaLogin;
        this.vistaMenu = vistaMenu;
        
        this.vistaMenu.jbt_admin.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vistaMenu.jbt_admin){
            EstiloInicioAdmin.aplicarEstilo();
            InicioSesion inicio = new InicioSesion();
            SwingUtilities.updateComponentTreeUI(inicio);
            inicio.setVisible(true);
            vistaMenu.FondoDesenfoque.setVisible(true);
            
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Usuario;
import Vista.EstiloInicioAdmin;
import Vista.InicioSesion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Vista.Menu_Principal;

/**
 *
 * @author COMPUCENTER-DM
 */
public class Vista_Principal implements ActionListener {
    
    private InicioSesion vista;
    private Menu_Principal Menu;
    
        
    public Vista_Principal(Menu_Principal Menu, InicioSesion vista) {
        this.Menu = Menu;
        this.vista = vista;
        this.Menu.jbt_admin.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       if (e.getSource() == Menu.jbt_admin) {
            InicioSesion inicio = new InicioSesion();
            inicio.setVisible(true);
            Menu.dispose();
            System.out.println("camilo soplapollas");
        } 
        
        
    }
    
    
}

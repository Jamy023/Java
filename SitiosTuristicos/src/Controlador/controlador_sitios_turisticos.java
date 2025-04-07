/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Modelo.modelo_sitios_turisticos;
import Vista.vista_sitios_turisticos;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import vistas.VentanaTuristica;


/**
 *
 * @author userx
 */
public class controlador_sitios_turisticos implements ActionListener{
    private vista_sitios_turisticos vista;
    private modelo_sitios_turisticos modelo;
    private String sitio_turistico;
    private  Map<Integer, List<String>> imagenes;
    
    public controlador_sitios_turisticos(vista_sitios_turisticos vista, modelo_sitios_turisticos modelo, String sitio_turistico) {
        this.vista = vista;
        this.modelo = modelo;
        this.sitio_turistico = sitio_turistico;
        this.imagenes = modelo.obtenerImagenesPorSitio(sitio_turistico);
        
        vista.calificar.addActionListener(this);
        vista.vista360.addActionListener(this);
    }
    
    public void iniciar() {
        // Obtener la descripción y el precio del sitio
        String descripcionYPrecio = modelo.obtenerDescripcionYPrecioPorSitio(sitio_turistico);

        
        vista.setTitle("Sitios turisticos");
        vista.setLocationRelativeTo(null);
        
        vista.lblNombre_sitio.setText(sitio_turistico);
        
        /*
        List<String> grupoSitioInteres = imagenes.get(3);
        
        ImageIcon icon = new ImageIcon(grupoSitioInteres.get(0));

        Image image = icon.getImage();

        Image scaledImage = image.getScaledInstance(vista.img.getWidth(), vista.img.getHeight(), Image.SCALE_SMOOTH);

        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        vista.img.setText(null);
        vista.img.setIcon(scaledIcon);*/
        
        vista.lblDescripcion.setText("<html>" + descripcionYPrecio.replace("\n", "<br>") + "</html>");
        cargarServicios("charco azul");
        
        modelo.cargarEstrellas(vista, false);
    }
    
    
    public void cargarServicios(String nombreSitio) { 
        // Obtener servicios por sitio
        Map<Integer, String> servicios = modelo.obtenerServiciosPorSitio(nombreSitio);

        // Limpiar el panel antes de agregar nuevos botones
        vista.Servicios.removeAll();
        vista.Servicios.setLayout(new GridLayout(0, 2, 10, 10)); // btnServicio

        // Crear y agregar botones dinámicamente
        for (Map.Entry<Integer, String> entry : servicios.entrySet()) {
            String tipoServicio = entry.getValue();
            JButton btnServicio = new JButton(tipoServicio);
            btnServicio.setPreferredSize(new Dimension(150, 40));

            btnServicio.addActionListener(e -> {
                switch (tipoServicio) {
                    case "Guía turística":
                        JOptionPane.showMessageDialog(null, "Mostrando información del guía turístico.");
                        // Aquí puedes abrir una vista o cargar info del guía
                        break;
                    case "Transporte":
                        JOptionPane.showMessageDialog(null, "Mostrando opciones de transporte.");
                        break;
                    case "sitio de interes":
                        JOptionPane.showMessageDialog(null, "Ya estás viendo un sitio de interés.");
                        break;
                    case "Como llgar":
                        JOptionPane.showMessageDialog(null, "Mostrando cómo llegar al sitio.");
                        break;
                    case "Restaurantes":
                        JOptionPane.showMessageDialog(null, "Mostrando restaurantes cercanos.");
                        break;
                    case "Alojamiento":
                        JOptionPane.showMessageDialog(null, "Mostrando opciones de alojamiento.");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Servicio no reconocido: " + tipoServicio);
                }
            });

            vista.Servicios.add(btnServicio);
        }

        // Refrescar la interfaz gráfica
        vista.Servicios.revalidate();
        vista.Servicios.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.calificar) {
            modelo_sitios_turisticos.insertarCalificacion(vista.numeroEstrella, sitio_turistico);   
        }
        else if(e.getSource() == vista.vista360) {
            VentanaTuristica foto360 = new VentanaTuristica("C:\\Users\\userx\\Documents\\Java ADSO\\SitiosTuristicos\\src\\imagenes\\sena360.JPEG");
            foto360.setVisible(true);
        }
    }
}

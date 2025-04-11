/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.lang.String;

public class VentanaTuristica extends JFrame {
    public VentanaTuristica(String rutaImagen) {
        setTitle("Información Turística");
        setSize(1500, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        String hola;

        // Contenedor principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // 🌍 Imagen panorámica en un JLabel
        PanelPanoramico visor = new PanelPanoramico(rutaImagen);
        panel.add(visor, BorderLayout.CENTER);

        // 🔘 Botón de cerrar centrado abajo
        JButton btnServicio = new JButton("Cerrar");
        btnServicio.setPreferredSize(new Dimension(180, 50));
        btnServicio.setFont(new Font("Arial", Font.BOLD, 18)); // Texto más grande y negrita

        btnServicio.addActionListener(e -> {
            this.dispose();
        });

        // 📦 Panel inferior para centrar el botón
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.add(btnServicio);

        panel.add(panelInferior, BorderLayout.SOUTH);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaTuristica("C:\\Users\\userx\\Documents\\Java ADSO\\SitiosTuristicos\\src\\imagenes\\sena360.JPEG").setVisible(true);
        });
    }
}

package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaTuristica extends JFrame {
    public VentanaTuristica(String rutaImagen) {
        setTitle("Información Turística");
        setSize(1500, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Utiliza la ruta abreviada directamente (por ejemplo, "/img/LasPavas360.JPEG")
        // Se podría imprimir un mensaje de depuración si se desea:
        System.out.println("Cargando imagen desde: " + rutaImagen);

        // Contenedor principal
        JPanel panel = new JPanel(new BorderLayout());

        // Se pasa la ruta abreviada a PanelPanoramico
        PanelPanoramico visor = new PanelPanoramico(rutaImagen);
        panel.add(visor, BorderLayout.CENTER);

        // Botón para cerrar
        JButton btnServicio = new JButton("Cerrar");
        btnServicio.setPreferredSize(new Dimension(180, 50));
        btnServicio.setFont(new Font("Arial", Font.BOLD, 18));
        btnServicio.addActionListener(e -> {
            this.dispose();
        });
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.add(btnServicio);
        panel.add(panelInferior, BorderLayout.SOUTH);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaTuristica("/img/LasPavas360.JPEG").setVisible(true);
        });
    }
}


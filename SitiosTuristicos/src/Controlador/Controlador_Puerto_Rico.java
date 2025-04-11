package Controlador;

import Vista.Vista_Municipio_Puerto_Rico;
import Vista.vista_sitios_turisticos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class Controlador_Puerto_Rico implements ActionListener {

    private Vista_Municipio_Puerto_Rico vista;

    public Controlador_Puerto_Rico(Vista_Municipio_Puerto_Rico vista) {
        this.vista = vista;

        this.vista.getBtn_Cascada_Salado().addActionListener(this);
        this.vista.getBtn_Pozo_Frio().addActionListener(this);
        this.vista.getBtn_Salto_Tigre().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == vista.getBtn_Cascada_Salado()) {
            abrirSitio("Cascada el Salado", vista);
        } else if (source == vista.getBtn_Pozo_Frio()) {
            abrirSitio("Pozo Monte frío", vista);
        } else if (source == vista.getBtn_Salto_Tigre()) {
            abrirSitio("Salto del tigre", vista);
        }
    }

    private void abrirSitio(String nombreSitio, JFrame vistaActual) {
        vista_sitios_turisticos nuevaVista = new vista_sitios_turisticos(nombreSitio);
        nuevaVista.setVisible(true);
        vistaActual.dispose();
    }
}

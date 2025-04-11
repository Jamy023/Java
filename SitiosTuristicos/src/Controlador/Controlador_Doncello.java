package Controlador;

import Vista.VIsta_Municipio_Doncello;
import Vista.vista_sitios_turisticos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class Controlador_Doncello implements ActionListener {

    private VIsta_Municipio_Doncello vista;

    public Controlador_Doncello(VIsta_Municipio_Doncello vista) {
        this.vista = vista;

        this.vista.getBtn_Anayancito().addActionListener(this);
        this.vista.getBtn_Fin_Estres().addActionListener(this);
        this.vista.getBtn_Munay().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == vista.getBtn_Anayancito()) {
            abrirSitio("Cascada Anayancito", vista);
        } else if (source == vista.getBtn_Fin_Estres()) {
            abrirSitio("Cascada fin del estrés", vista);
        } else if (source == vista.getBtn_Munay()) {
            abrirSitio("Tierras del Munay", vista);
        }
    }

    private void abrirSitio(String nombreSitio, JFrame vistaActual) {
        vista_sitios_turisticos nuevaVista = new vista_sitios_turisticos(nombreSitio);
        nuevaVista.setVisible(true);
        vistaActual.dispose();
    }
}

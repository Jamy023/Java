package Controlador;

import Vista.Vista_Municipio_Florencia;
import Vista.vista_sitios_turisticos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




    public class Controlador_Florencia implements ActionListener {

    private Vista_Municipio_Florencia vista;

    public Controlador_Florencia(Vista_Municipio_Florencia vista) {
        this.vista = vista;

        // Asignar eventos
        this.vista.getBtn_Las_Pavas().addActionListener(this);
        this.vista.getBtn_cascada().addActionListener(this);
        this.vista.getBtn_Reserva_Natural().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == vista.getBtn_Las_Pavas()) {
            abrirSitio("Cascada el Salado");
        } else if (source == vista.getBtn_cascada()) {
            abrirSitio("Cascada la Avispa");
        } else if (source == vista.getBtn_Reserva_Natural()) {
            abrirSitio("Reserva Natural Las Palmas"); 
        }
        
    }

    private void abrirSitio(String nombreSitio) {
        vista_sitios_turisticos nuevaVista = new vista_sitios_turisticos(nombreSitio);
        nuevaVista.setVisible(true);
        vista.dispose();
    }
}



package Vista;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class EstiloInicioAdmin {
    public static void main(String[] args)throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatLightLaf());
        UIManager.put("TextComponent.arc", 100);
        UIManager.put("Button.arc", 100);
        SwingUtilities.updateComponentTreeUI(inicio);
        inicio.setVisible(true);
    }
    public static final InicioSesion inicio = new InicioSesion();
}


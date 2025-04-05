package Vista;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class EstiloInicioAdmin {
    private static boolean estiloAplicado = false;

    public static void aplicarEstilo() {
        if (!estiloAplicado) {
            try {
                UIManager.setLookAndFeel(new FlatLightLaf());
                UIManager.put("TextComponent.arc", 100);
                UIManager.put("Button.arc", 100);
                estiloAplicado = true;
            } catch (UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }
        }
    }
}

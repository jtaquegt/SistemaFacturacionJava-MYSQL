package app;

import javax.swing.SwingUtilities;
import vistas.FrmPrincipal;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Inicializar y mostrar la ventana principal del sistema
                FrmPrincipal principal = new FrmPrincipal();
                principal.setVisible(true);
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(
                        null,
                        "Error al iniciar la aplicación: " + e.getMessage(),
                        "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                );
                e.printStackTrace(); // Para depuración
            }
        });
    }   
}

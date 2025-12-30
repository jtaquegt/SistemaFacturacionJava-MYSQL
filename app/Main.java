package app;

import javax.swing.SwingUtilities;
import vistas.FrmPrincipal;

/**
 * Clase principal del sistema de facturación.
 * Punto de entrada de la aplicación.
 * 
 * Esta versión es segura para repositorio público y sirve como ejemplo
 * de buenas prácticas en la inicialización de una GUI con Swing.
 */
public class Main {

    public static void main(String[] args) {
        // Ejecutar la inicialización de la interfaz gráfica en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                // Crear e inicializar la ventana principal
                FrmPrincipal principal = new FrmPrincipal();
                principal.setVisible(true);

            } catch (Exception e) {
                // Mostrar mensaje amigable al usuario en caso de error
                javax.swing.JOptionPane.showMessageDialog(
                        null,
                        "Ha ocurrido un error al iniciar la aplicación. Por favor, contacte al administrador.",
                        "Error de inicio",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                );

                // Imprimir detalles del error en consola para depuración
                // (en producción, esto podría reemplazarse por un logger)
                e.printStackTrace();
            }
        });
    }   
}

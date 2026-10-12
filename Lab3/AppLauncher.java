package Lab3;

import javax.swing.SwingUtilities;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ApplicationWindow window = new ApplicationWindow();
            window.setVisible(true);
        });
    }
}
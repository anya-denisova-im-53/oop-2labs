package Lab1; 
import javax.swing.*;
import java.awt.*;

public class MainApp extends JFrame {
    private String displayedText = "Select an action via the 'Work' or 'Work2' menu";

    public MainApp() {
        setTitle("Laboratory Work No. 1 - Student No. 9");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem itemExit = new JMenuItem("Exit");
        itemExit.addActionListener(e -> System.exit(0));
        menuFile.add(itemExit);

        JMenu menuWork1 = new JMenu("Work");
        JMenuItem itemWork1 = new JMenuItem("Option 1 (Slider)");
        itemWork1.addActionListener(e -> openWork1Dialog());
        menuWork1.add(itemWork1);

        JMenu menuWork2 = new JMenu("Work2");
        JMenuItem itemWork2 = new JMenuItem("Option 2 (Dialog Chain)");
        itemWork2.addActionListener(e -> openWork2DialogChain());
        menuWork2.add(itemWork2);

        menuBar.add(menuFile);
        menuBar.add(menuWork1);
        menuBar.add(menuWork2);
        setJMenuBar(menuBar);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.drawString(displayedText, 50, 100);
            }
        };
        add(panel);
    }

    private void openWork1Dialog() {
        Work1Dialog dialog = new Work1Dialog(this);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            int value = dialog.getSelectedValue();
            displayedText = "Selected number from slider: " + value;
            repaint();
        }
    }

    private void openWork2DialogChain() {
        Work2FirstDialog firstDialog = new Work2FirstDialog(this);
        firstDialog.setVisible(true);
    }

    public void setDisplayedText(String text) {
        this.displayedText = text;
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainApp().setVisible(true);
        });
    }
}

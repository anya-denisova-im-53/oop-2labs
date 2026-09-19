package Lab1; 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
        JMenuItem itemWork1 = new JMenuItem("Execute Work 1");
        itemWork1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Work1Dialog dialog = new Work1Dialog(MainApp.this);
                dialog.setVisible(true);
                if (dialog.isConfirmed()) {
                    displayedText = "Selected group from the list: " + dialog.getSelectedValue();
                    repaint();
                }
            }
        });
        menuWork1.add(itemWork1);

        JMenu menuWork2 = new JMenu("Work2");
        JMenuItem itemWork2 = new JMenuItem("Execute Work 2");
        itemWork2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Work2Dialog dialog = new Work2Dialog(MainApp.this);
                dialog.setVisible(true);
                if (dialog.isConfirmed()) {
                    displayedText = "Successfully completed the chain of two dialog windows!";
                    repaint();
                }
            }
        });
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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainApp().setVisible(true);
        });
    }
}

package Lab1;
import javax.swing.*;
import java.awt.*;

public class Work2Dialog extends JDialog {
    private boolean finalResult = false;

    public Work2Dialog(JFrame parent) {
        super(parent, "Dialog 1 of 2", true);
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new FlowLayout());

        add(new JLabel("This is the first dialog window. Click 'Next'"));

        JButton btnNext = new JButton("Next >");
        JButton btnCancel = new JButton("Cancel");

        btnNext.addActionListener(e -> {
            dispose(); 
            openSecondStep(parent);
        });

        btnCancel.addActionListener(e -> {
            finalResult = false;
            dispose();
        });

        add(btnNext);
        add(btnCancel);
    }

    private void openSecondStep(JFrame parent) {
        JDialog secondDlg = new JDialog(parent, "Dialog 2 of 2", true);
        secondDlg.setSize(300, 150);
        secondDlg.setLocationRelativeTo(parent);
        secondDlg.setLayout(new FlowLayout());

        secondDlg.add(new JLabel("Second window. Choose an action:"));

        JButton btnBack = new JButton("< Back");
        JButton btnYes = new JButton("Yes");
        JButton btnCancel2 = new JButton("Cancel");

        btnBack.addActionListener(e -> {
            secondDlg.dispose();
            new Work2Dialog(parent).setVisible(true); 
        });

        btnYes.addActionListener(e -> {
            finalResult = true;
            secondDlg.dispose();
            dispose();
        });

        btnCancel2.addActionListener(e -> {
            finalResult = false;
            secondDlg.dispose();
            dispose();
        });

        secondDlg.add(btnBack);
        secondDlg.add(btnYes);
        secondDlg.add(btnCancel2);
        secondDlg.setVisible(true);
    }

    public boolean isConfirmed() {
        return finalResult;
    }
}
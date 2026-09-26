package Lab1;
import javax.swing.*;
import java.awt.*;

public class Work2FirstDialog extends JDialog {
    private MainApp parentApp;

    public Work2FirstDialog(MainApp parent) {
        super(parent, "Dialog 1 of 2", true);
        this.parentApp = parent;
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setLayout(new FlowLayout());

        add(new JLabel("Step 1: Click Next to continue."));

        JButton btnNext = new JButton("Next >");
        JButton btnCancel = new JButton("Cancel");

        btnNext.addActionListener(e -> {
            dispose();
            Work2SecondDialog secondDialog = new Work2SecondDialog(parentApp);
            secondDialog.setVisible(true); 
        });

        btnCancel.addActionListener(e -> dispose());

        add(btnNext);
        add(btnCancel);
    }
}

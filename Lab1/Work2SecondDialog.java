package Lab1;
import javax.swing.*;
import java.awt.*;

public class Work2SecondDialog extends JDialog {
    private MainApp parentApp;

    public Work2SecondDialog(MainApp parent) {
        super(parent, "Dialog 2 of 2", true);
        this.parentApp = parent;
        setSize(320, 150);
        setLocationRelativeTo(parent);
        setLayout(new FlowLayout());

        add(new JLabel("Step 2: Choose an action."));

        JButton btnBack = new JButton("< Back");
        JButton btnYes = new JButton("Yes");
        JButton btnCancel = new JButton("Cancel");

        btnBack.addActionListener(e -> {
            dispose(); 
            Work2FirstDialog firstDialog = new Work2FirstDialog(parentApp);
            firstDialog.setVisible(true); 
        });

        btnYes.addActionListener(e -> {
            parentApp.setDisplayedText("Chain dialog completed successfully!");
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        add(btnBack);
        add(btnYes);
        add(btnCancel);
    }
}

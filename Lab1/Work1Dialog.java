package Lab1;
import javax.swing.*;
import java.awt.*;

public class Work1Dialog extends JDialog {
    private JList<String> listView;
    private boolean confirmed = false;
    private String selectedValue = "";

    public Work1Dialog(JFrame parent) {
        super(parent, "Select Faculty Group (ListBox)", true);
        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        String[] groups = {"IM-51", "IM-52", "IM-53 (Your Group)", "IM-54", "IK-51", "IK-52"};
        listView = new JList<>(groups);
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(listView), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnOk = new JButton("Yes");
        JButton btnCancel = new JButton("Cancel");

        btnOk.addActionListener(e -> {
            if (listView.getSelectedValue() != null) {
                selectedValue = listView.getSelectedValue();
                confirmed = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an item from the list!");
            }
        });

        btnCancel.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttonPanel.add(btnOk);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String getSelectedValue() {
        return selectedValue;
    }
}

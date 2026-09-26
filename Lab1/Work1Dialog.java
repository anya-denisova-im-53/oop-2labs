package Lab1;
import javax.swing.*;
import java.awt.*;

public class Work1Dialog extends JDialog {
    private JSlider slider;
    private boolean confirmed = false;


    public Work1Dialog(JFrame parent) {
        super(parent, "Slider Dialog", true);
        setSize(350, 180);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        slider = new JSlider(1, 100, 50);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Select a number (1 - 100)"));
        centerPanel.add(slider, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnYes = new JButton("Yes");
        JButton btnCancel = new JButton("Cancel");

        btnYes.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        btnCancel.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        buttonPanel.add(btnYes);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public int getSelectedValue() {
        return slider.getValue();
    }
}

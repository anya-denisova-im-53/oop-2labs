package Lab5;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ObjectTableFrame extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private ObjectSelectionListener listener;

    public ObjectTableFrame(JFrame owner, ObjectSelectionListener listener) {
        this.listener = listener;
        setTitle("Таблиця введених об'єктів (Лаб. 5)");
        setSize(500, 320);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 

        String[] columns = {"Назва", "X1", "Y1", "X2", "Y2"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);

   
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0 && listener != null) {
                    listener.onObjectSelected(row);
                }
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton deleteButton = new JButton("Видалити вибраний об'єкт");
        deleteButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0 && listener != null) {
                listener.onObjectDeleted(row);
            }
        });
        add(deleteButton, BorderLayout.SOUTH);
    }

    public void refreshTable(ArrayList<GeometricFigure> storage) {
        model.setRowCount(0);
        for (GeometricFigure fig : storage) {
            Object[] row = {fig.getTitle(), fig.getX1(), fig.getY1(), fig.getX2(), fig.getY2()};
            model.addRow(row);
        }
    }
}
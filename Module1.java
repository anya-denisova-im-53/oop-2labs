import javax.swing.*;
import java.awt.*;

public class Module1 {
    // Внутрішня статична змінна для збереження вибраного значення
    private static String chosenValue = null;

    // Інтерфейсна функція модуля (аналог extern int Func_MOD1(...))
    public static String funcMod1(JFrame parentFrame) {
        chosenValue = null;

        // Створення модального діалогового вікна
        JDialog dialog = new JDialog(parentFrame, "Вибір групи факультету (Робота 1)", true);
        dialog.setSize(320, 240);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setLayout(new BorderLayout());

        // Список груп факультету (згідно з методичкою)
        String[] facultyGroups = {
            "ІП-11 (Інженерія програмного забезпечення)",
            "ІП-12 (Інженерія програмного забезпечення)",
            "ІП-13 (Інженерія програмного забезпечення)",
            "ІП-14 (Інженерія програмного забезпечення)",
            "ІП-15 (Інженерія програмного забезпечення)"
        };

        JList<String> listComponent = new JList<>(facultyGroups);
        listComponent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listComponent.setSelectedIndex(0);
        
        dialog.add(new JScrollPane(listComponent), BorderLayout.CENTER);

        // Панель з кнопками [Так] та [Відміна]
        JPanel buttonPanel = new JPanel();
        JButton btnOk = new JButton("Так");
        JButton btnCancel = new JButton("Відміна");

        btnOk.addActionListener(e -> {
            chosenValue = listComponent.getSelectedValue();
            dialog.dispose(); // Закриття вікна з успіхом
        });

        btnCancel.addActionListener(e -> {
            chosenValue = null; // Скасування
            dialog.dispose();
        });

        buttonPanel.add(btnOk);
        buttonPanel.add(btnCancel);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setVisible(true); // Відображення модального діалогу
        return chosenValue;
    }
}
import javax.swing.*;
import java.awt.*;

public class Module2 {

    // Головна керуюча функція модуля 2 (аналог extern int Func_MOD2(...))
    public static boolean funcMod2(JFrame parentFrame) {
        while (true) {
            // Крок 1: Показуємо перше вікно діалогу
            int step1Result = showFirstStepDialog(parentFrame);
            if (step1Result <= 0) {
                return false; // Натиснуто "Відміна" або закриття вікна (хрестик)
            }

            // Крок 2: Показуємо друге вікно діалогу
            int step2Result = showSecondStepDialog(parentFrame);
            if (step2Result == 1) {
                return true;  // Натиснуто "Так" на другому вікні — завершення успіхом
            } else if (step2Result == -1) {
                continue;     // Натиснуто "< Назад", цикл повторюється (повернення на крок 1)
            } else {
                return false; // Натиснуто "Відміна" на другому вікні
            }
        }
    }

    // Реалізація першого вікна діалогу
    private static int showFirstStepDialog(JFrame parent) {
        final int[] codeResult = {0}; // 0 - відміна, 1 - далі

        JDialog dialog = new JDialog(parent, "Робота2 - Крок 1", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        dialog.add(new JLabel("Це перше вікно. Натисніть кнопку Далі."));

        JButton btnNext = new JButton("Далі >");
        JButton btnCancel = new JButton("Відміна");

        btnNext.addActionListener(e -> {
            codeResult[0] = 1;
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> {
            codeResult[0] = 0;
            dialog.dispose();
        });

        dialog.add(btnNext);
        dialog.add(btnCancel);
        dialog.setVisible(true);

        return codeResult[0];
    }

    // Реалізація другого вікна діалогу (з підтримкою кнопки Назад)
    private static int showSecondStepDialog(JFrame parent) {
        final int[] codeResult = {0}; // -1 - назад, 0 - відміна, 1 - так

        JDialog dialog = new JDialog(parent, "Робота2 - Крок 2", tagCheck(350), 150);
        dialog.setSize(360, 150);
        dialog.setLocationRelativeTo(parent);
        dialog.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 20));

        dialog.add(new JLabel("Друге вікно. Можна повернутися назад."));

        JButton btnBack = new JButton("< Назад");
        JButton btnYes = new JButton("Так");
        JButton btnCancel = new JButton("Відміна");

        btnBack.addActionListener(e -> {
            codeResult[0] = -1; // Код повернення назад
            dialog.dispose();
        });

        btnYes.addActionListener(e -> {
            codeResult[0] = 1;  // Код успішного завершення
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> {
            codeResult[0] = 0;  // Код відміни
            dialog.dispose();
        });

        dialog.add(btnBack);
        dialog.add(btnYes);
        dialog.add(btnCancel);
        dialog.setVisible(true);

        return codeResult[0];
    }

    private static int tagCheck(int val) {
        return val;
    }
}
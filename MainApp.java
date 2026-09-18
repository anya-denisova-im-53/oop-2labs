import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp extends JFrame {
    // Змінні для виведення результатів роботи на екран головного вікна (аналог TextOut)
    private String work1ResultText = "Результат Роботи 1: ще не вибрано";
    private String work2ResultText = "Результат Роботи2: ще не завершено";

    public MainApp() {
        // Налаштування головного вікна програми (аналог CreateWindow / WinMain)
        setTitle("Лабораторна робота №1 - Варіант 9 (Менеджер)");
        setSize(650, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Створення головного меню програми (аналог CreateMenu)
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuFile = new JMenu("Файл");
        JMenuItem itemExit = new JMenuItem("Вихід");
        itemExit.addActionListener(e -> System.exit(0));
        menuFile.add(itemExit);

        // Меню "Дії" з пунктами "Робота" та "Робота2" (згідно з вимогами методички)
        JMenu menuActions = new JMenu("Дії");
        
        // Пункт меню "Робота" -> Варіант B1 = 3 (Список ListBox)
        JMenuItem itemWork1 = new JMenuItem("Робота");
        itemWork1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Виклик інтерфейсної функції з Module1
                String selectedGroup = Module1.funcMod1(MainApp.this);
                if (selectedGroup != null) {
                    work1ResultText = "Обрана група (Робота 1): " + selectedGroup;
                } else {
                    work1ResultText = "Робота 1 була скасована.";
                }
                repaint(); // Примусове оновлення вікна (аналог InvalidateRect / WM_PAINT)
            }
        });

        // Пункт меню "Робота2" -> Варіант B2 = 2 (Два вікна діалогу з кнопкою Назад)
        JMenuItem itemWork2 = new JMenuItem("Робота2");
        itemWork2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Виклик інтерфейсної функції з Module2
                boolean success = Module2.funcMod2(MainApp.this);
                if (success) {
                    work2ResultText = "Робота2: успішно пройдено двовіконний діалог!";
                } else {
                    work2ResultText = "Робота2: діалог скасовано користувачем.";
                }
                repaint();
            }
        });

        menuActions.add(itemWork1);
        menuActions.add(itemWork2);

        JMenu menuAbout = new JMenu("Довідка");
        JMenuItem itemAboutInfo = new JMenuItem("Про програму...");
        itemAboutInfo.addActionListener(e -> JOptionPane.showMessageDialog(
                MainApp.this, 
                "Лабораторна робота №1\nСтудент: №9 у списку\nБазова мова: Java (еквівалент ООП)", 
                "Про програму", 
                JOptionPane.INFORMATION_MESSAGE
        ));
        menuAbout.add(itemAboutInfo);

        menuBar.add(menuFile);
        menuBar.add(menuActions);
        menuBar.add(menuAbout);
        setJMenuBar(menuBar);

        // Створення клієнтської області головного вікна для відображення текстів (WM_PAINT)
        JPanel canvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setFont(new Font("Arial", Font.BOLD, 14));
                // Виведення результатів на екран (аналог функції TextOut у C++ WinAPI)
                g.drawString(work1ResultText, 40, 50);
                g.drawString(work2ResultText, 40, 90);
            }
        };
        add(canvasPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainApp().setVisible(true);
        });
    }
}

package Lab3;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

public class ApplicationWindow extends JFrame {
    private DrawingCanvas canvasPanel;
    private JRadioButtonMenuItem menuDot, menuSegment, menuBox, menuOval;
    private JButton btnDot, btnSegment, btnBox, btnOval;

    public ApplicationWindow() {
        setTitle("Лабораторна робота №3 - Варіант 10 (Java)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        canvasPanel = new DrawingCanvas();
        add(canvasPanel, BorderLayout.CENTER);


        JMenuBar mainMenuBar = new JMenuBar();
        
        JMenu menuFile = new JMenu("Файл");
        JMenuItem itemExit = new JMenuItem("Вихід");
        itemExit.addActionListener(e -> System.exit(0));
        menuFile.add(itemExit);


        JMenu menuObjects = new JMenu("Об'єкти");
        ButtonGroup toolsGroup = new ButtonGroup();

        menuDot = new JRadioButtonMenuItem("Крапка", true);
        menuSegment = new JRadioButtonMenuItem("Лінія");
        menuBox = new JRadioButtonMenuItem("Прямокутник");
        menuOval = new JRadioButtonMenuItem("Еліпс");

        toolsGroup.add(menuDot);
        toolsGroup.add(menuSegment);
        toolsGroup.add(menuBox);
        toolsGroup.add(menuOval);

        menuObjects.add(menuDot);
        menuObjects.add(menuSegment);
        menuObjects.add(menuBox);
        menuObjects.add(menuOval);


        JMenu menuHelp = new JMenu("Довідка");
        JMenuItem itemAbout = new JMenuItem("Про програму...");
        itemAbout.addActionListener(e -> showAboutDialog());
        menuHelp.add(itemAbout);

  
        mainMenuBar.add(menuFile);
        mainMenuBar.add(menuObjects);
        mainMenuBar.add(menuHelp);
        setJMenuBar(mainMenuBar);

        JToolBar mainToolBar = new JToolBar();
        mainToolBar.setFloatable(false);

        btnDot = new JButton("Крапка");
        btnDot.setToolTipText("Малювання точкових об'єктів");

        btnSegment = new JButton("Лінія");
        btnSegment.setToolTipText("Малювання відрізків ліній");

        btnBox = new JButton("Прямокутник");
        btnBox.setToolTipText("Малювання прямокутників");

        btnOval = new JButton("Еліпс");
        btnOval.setToolTipText("Малювання еліпсів");

        mainToolBar.add(btnDot);
        mainToolBar.add(btnSegment);
        mainToolBar.add(btnBox);
        mainToolBar.add(btnOval);

        add(mainToolBar, BorderLayout.NORTH);


        ActionListener dotAction = e -> changeTool(0);
        ActionListener segmentAction = e -> changeTool(1);
        ActionListener boxAction = e -> changeTool(2);
        ActionListener ovalAction = e -> changeTool(3);

        menuDot.addActionListener(dotAction);
        btnDot.addActionListener(dotAction);

        menuSegment.addActionListener(segmentAction);
        btnSegment.addActionListener(segmentAction);

        menuBox.addActionListener(boxAction);
        btnBox.addActionListener(boxAction);

        menuOval.addActionListener(ovalAction);
        btnOval.addActionListener(ovalAction);
    }

    private void changeTool(int toolIndex) {
        canvasPanel.setActiveToolType(toolIndex);

   
        menuDot.setSelected(toolIndex == 0);
        menuSegment.setSelected(toolIndex == 1);
        menuBox.setSelected(toolIndex == 2);
        menuOval.setSelected(toolIndex == 3);
    }

    private void showAboutDialog() {
        String infoMessage = "Лабораторна робота №3\n" +
                             "Тема: Розробка графічного інтерфейсу користувача\n" +
                             "Варіант: 10 (Студент №9)\n\n" +
                             "Особливості варіанта:\n" +
                             "• Статичний масив: 110 об'єктів\n" +
                             "• Слід малювання: синя суцільна лінія\n" +
                             "• Прямокутник: біле заповнення\n" +
                             "• Еліпс: без заповнення";

        JOptionPane.showMessageDialog(this, infoMessage, "Про програму / Довідка", JOptionPane.INFORMATION_MESSAGE);
    }
}
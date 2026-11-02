package Lab4;   
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainLab4 extends JFrame {
    private MyEditor editor = new MyEditor();

    public MainLab4() {
        setTitle("Лабораторна робота №4 - Варіант 9 ");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

       
        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                editor.onPaint(g);
            }
        };
        canvas.setBackground(Color.WHITE);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                editor.onLBdown(e.getX(), e.getY());
                canvas.repaint();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                editor.onLBup(e.getX(), e.getY());
                canvas.repaint();
            }
        });
        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                editor.onMouseMove(e.getX(), e.getY());
                canvas.repaint();
            }
        });

        add(canvas, BorderLayout.CENTER);

       
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("Файл");
        JMenu menuObjects = new JMenu("Об'єкти");
        JMenu menuHelp = new JMenu("Довідка");

        JMenuItem itemPoint = new JMenuItem("Крапка");
        JMenuItem itemLine = new JMenuItem("Лінія");
        JMenuItem itemRect = new JMenuItem("Прямокутник");
        JMenuItem itemEllipse = new JMenuItem("Еліпс");
        JMenuItem itemLineOO = new JMenuItem("Лінія з кружечками");
        JMenuItem itemCube = new JMenuItem("Каркас куба");

        itemPoint.addActionListener(e -> updateMode(new PointEdit4(), "Крапка"));
        itemLine.addActionListener(e -> updateMode(new LineEdit4(), "Лінія"));
        itemRect.addActionListener(e -> updateMode(new RectEdit4(), "Прямокутник"));
        itemEllipse.addActionListener(e -> updateMode(new EllipseEdit4(), "Еліпс"));
        itemLineOO.addActionListener(e -> updateMode(new LineOOEdit(), "Лінія з кружечками"));
        itemCube.addActionListener(e -> updateMode(new CubeEdit(), "Каркас куба"));

        menuObjects.add(itemPoint);
        menuObjects.add(itemLine);
        menuObjects.add(itemRect);
        menuObjects.add(itemEllipse);
        menuObjects.add(itemLineOO);
        menuObjects.add(itemCube);

        menuBar.add(menuFile);
        menuBar.add(menuObjects);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);

        
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton btnPoint = new JButton("• Крапка");
        btnPoint.setToolTipText("Створити об'єкт: Крапка");
        JButton btnLine = new JButton("╱ Лінія");
        btnLine.setToolTipText("Створити об'єкт: Лінія");
        JButton btnRect = new JButton("□ Прямокутник");
        btnRect.setToolTipText("Створити об'єкт: Прямокутник (від центру)");
        JButton btnEllipse = new JButton("◯ Еліпс");
        btnEllipse.setToolTipText("Створити об'єкт: Еліпс (блакитне заповнення)");
        JButton btnLineOO = new JButton("—O-O— Лінія з круж.");
        btnLineOO.setToolTipText("Створити складений об'єкт: Лінія з кружечками (множинне успадкування)");
        JButton btnCube = new JButton("⌂ Куб");
        btnCube.setToolTipText("Створити складений об'єкт: Каркас куба (множинне успадкування)");

        btnPoint.addActionListener(e -> updateMode(new PointEdit4(), "Крапка"));
        btnLine.addActionListener(e -> updateMode(new LineEdit4(), "Лінія"));
        btnRect.addActionListener(e -> updateMode(new RectEdit4(), "Прямокутник"));
        btnEllipse.addActionListener(e -> updateMode(new EllipseEdit4(), "Еліпс"));
        btnLineOO.addActionListener(e -> updateMode(new LineOOEdit(), "Лінія з кружечками"));
        btnCube.addActionListener(e -> updateMode(new CubeEdit(), "Каркас куба"));

        toolBar.add(btnPoint);
        toolBar.add(btnLine);
        toolBar.add(btnRect);
        toolBar.add(btnEllipse);
        toolBar.addSeparator();
        toolBar.add(btnLineOO);
        toolBar.add(btnCube);

        add(toolBar, BorderLayout.NORTH);
    }

    private void updateMode(Editor4 editorType, String name) {
        editor.start(editorType, name);
        setTitle("Лабораторна робота №4 - Варіант 9 | Режим: " + name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainLab4().setVisible(true));
    }
}
package Lab2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Lab2 extends JFrame {
    private ShapeEditor editor = new ShapeEditor();

    public Lab2() {
        setTitle("Lab 2 - Object-Oriented Editor (Student No. 9)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

  
        JMenuBar menuBar = new JMenuBar();
        
        
        JMenu menuFile = new JMenu("Файл");
        JMenuItem itemExit = new JMenuItem("Вихід");
        itemExit.addActionListener(e -> System.exit(0));
        menuFile.add(itemExit);

        
        JMenu menuObjects = new JMenu("Об'єкти");
        JMenuItem itemPoint = new JMenuItem("Крапка");
        JMenuItem itemLine = new JMenuItem("Лінія");
        JMenuItem itemRect = new JMenuItem("Прямокутник");
        JMenuItem itemEllipse = new JMenuItem("Еліпс");

        itemPoint.addActionListener(e -> {
            editor.startPoint();
            setTitle("Lab 2 - Режим: Крапка");
        });
        itemLine.addActionListener(e -> {
            editor.startLine();
            setTitle("Lab 2 - Режим: Лінія");
        });
        itemRect.addActionListener(e -> {
            editor.startRect();
            setTitle("Lab 2 - Режим: Прямокутник");
        });
        itemEllipse.addActionListener(e -> {
            editor.startEllipse();
            setTitle("Lab 2 - Режим: Еліпс");
        });

        menuObjects.add(itemPoint);
        menuObjects.add(itemLine);
        menuObjects.add(itemRect);
        menuObjects.add(itemEllipse);


        JMenu menuHelp = new JMenu("Довідка");
        JMenuItem itemAbout = new JMenuItem("Про програму");
        itemAbout.addActionListener(e -> JOptionPane.showMessageDialog(this, "Lab 2 OOP. Student variant #9"));
        menuHelp.add(itemAbout);

        menuBar.add(menuFile);
        menuBar.add(menuObjects);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);

        
        DrawingPanel canvas = new DrawingPanel(editor);
        add(canvas);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Lab2().setVisible(true);
        });
    }
}

class DrawingPanel extends JPanel {
    private ShapeEditor editor;

    public DrawingPanel(ShapeEditor editor) {
        this.editor = editor;
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                editor.onMouseDown(e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                editor.onMouseUp(e.getX(), e.getY());
                repaint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                editor.onMouseMove(e.getX(), e.getY());
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        editor.drawAll(g);
        editor.drawRubber(g);
    }
}
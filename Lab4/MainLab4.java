package Lab4;   
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class MainLab4 extends JFrame {
    private MyEditor editor = new MyEditor();

    public MainLab4() {
        setTitle("Лабораторна робота №4 - Варіант 9");
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
        JMenuItem itemExit = new JMenuItem("Вихід");
        itemExit.addActionListener(e -> System.exit(0));
        menuFile.add(itemExit);

        JMenu menuObjects = new JMenu("Об'єкти");
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

        JMenu menuHelp = new JMenu("Довідка");
        JMenuItem itemAbout = new JMenuItem("Про програму...");
        itemAbout.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Лабораторна робота №4 (Java Swing)\nВаріант 9",
                "Довідка",
                JOptionPane.INFORMATION_MESSAGE
        ));
        menuHelp.add(itemAbout);

        menuBar.add(menuFile);
        menuBar.add(menuObjects);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);

       
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        JButton btnPoint = createShapeButton(ShapeIconType.POINT, "Крапка", () -> updateMode(new PointEdit4(), "Крапка"));
        JButton btnLine = createShapeButton(ShapeIconType.LINE, "Лінія", () -> updateMode(new LineEdit4(), "Лінія"));
        JButton btnRect = createShapeButton(ShapeIconType.RECT, "Прямокутник", () -> updateMode(new RectEdit4(), "Прямокутник"));
        JButton btnEllipse = createShapeButton(ShapeIconType.ELLIPSE, "Еліпс", () -> updateMode(new EllipseEdit4(), "Еліпс"));
        JButton btnLineOO = createShapeButton(ShapeIconType.LINE_OO, "Лінія з кружечками", () -> updateMode(new LineOOEdit(), "Лінія з кружечками"));
        JButton btnCube = createShapeButton(ShapeIconType.CUBE, "Каркас куба", () -> updateMode(new CubeEdit(), "Каркас куба"));

        toolBar.add(btnPoint);
        toolBar.add(btnLine);
        toolBar.add(btnRect);
        toolBar.add(btnEllipse);
        toolBar.addSeparator();
        toolBar.add(btnLineOO);
        toolBar.add(btnCube);

        add(toolBar, BorderLayout.NORTH);
    }

    private JButton createShapeButton(ShapeIconType type, String tooltip, Runnable action) {
        JButton button = new JButton(createDrawnShapeIcon(type, 24, 24));
        button.setToolTipText(tooltip);
        button.setFocusable(false);
        button.addActionListener(e -> action.run());
        return button;
    }

    private void updateMode(Editor4 editorType, String name) {
        editor.start(editorType, name);
        setTitle("Лабораторна робота №4 - Варіант 9 | Режим: " + name);
    }

    private enum ShapeIconType {
        POINT, LINE, RECT, ELLIPSE, LINE_OO, CUBE
    }

    
    private Icon createDrawnShapeIcon(ShapeIconType type, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(1.6f));
        g2.setColor(Color.BLACK);

        switch (type) {
            case POINT:
                g2.fillOval(width / 2 - 3, height / 2 - 3, 6, 6);
                break;
            case LINE:
                g2.drawLine(5, height - 5, width - 5, 5);
                break;
            case RECT:
                g2.drawRect(5, 6, width - 10, height - 12);
                break;
            case ELLIPSE:
                g2.setColor(new Color(0, 255, 255));
                g2.fillOval(5, 6, width - 10, height - 12);
                g2.setColor(Color.BLACK);
                g2.drawOval(5, 6, width - 10, height - 12);
                break;
            case LINE_OO:
                g2.drawLine(6, height - 6, width - 6, 6);
                g2.setColor(new Color(0, 255, 255));
                g2.fillOval(3, height - 9, 6, 6);
                g2.fillOval(width - 9, 3, 6, 6);
                g2.setColor(Color.BLACK);
                g2.drawOval(3, height - 9, 6, 6);
                g2.drawOval(width - 9, 3, 6, 6);
                break;
            case CUBE:
                int s = 11;
                int off = 5;
                int fx = 4, fy = 8;
                int bx = fx + off, by = fy - off;
                g2.drawRect(fx, fy, s, s);
                g2.drawRect(bx, by, s, s);
                g2.drawLine(fx, fy, bx, by);
                g2.drawLine(fx + s, fy, bx + s, by);
                g2.drawLine(fx, fy + s, bx, by + s);
                g2.drawLine(fx + s, fy + s, bx + s, by + s);
                break;
        }

        g2.dispose();
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainLab4().setVisible(true));
    }
}
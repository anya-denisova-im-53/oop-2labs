package Lab5;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class DrawingCanvasApp extends JFrame implements ObjectSelectionListener {
    private final ObjectManager manager;
    private final ObjectTableFrame tableFrame;
    private JPanel canvas;

    private int startX, startY, currentX, currentY;
    private boolean isDrawing = false;
    private String selectedMode = "Rectangle";

    public DrawingCanvasApp() {
        setTitle("Лабораторна робота №5 - Варіант 9");
        setSize(950, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        manager = ObjectManager.getInstance();
        tableFrame = new ObjectTableFrame(this, this);
        manager.setTableWindow(tableFrame);

        buildMenu();
        buildToolBar();
        buildCanvasArea();
    }

    private void setMode(String mode, String title) {
        this.selectedMode = mode;
        setTitle(title);
    }

    private Icon createShapeIcon(String type) {
        int w = 28, h = 28;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(Color.BLACK);

        switch (type) {
            case "Dot":
                g2.fillOval(11, 11, 6, 6);
                break;
            case "Line":
                g2.drawLine(6, 22, 22, 6);
                g2.fillOval(19, 7, 4, 4);
                break;
            case "Rectangle":
                g2.drawRect(6, 7, 16, 14);
                break;
            case "Ellipse":
                g2.setColor(new Color(0, 255, 255));
                g2.fillOval(6, 8, 16, 12);
                g2.setColor(Color.BLACK);
                g2.drawOval(6, 8, 16, 12);
                break;
           case "LineOO":
                g2.drawLine(8, 20, 20, 8);
                g2.setColor(new Color(0, 255, 255));
                g2.fillOval(4, 17, 7, 7);
                g2.fillOval(17, 4, 7, 7);
                g2.setColor(Color.BLACK);
                g2.drawOval(4, 17, 7, 7);
                g2.drawOval(17, 4, 7, 7);
                break;
            case "Cube":
                g2.setStroke(new BasicStroke(1.6f));
                g2.drawRect(5, 10, 12, 12);
                g2.drawRect(10, 5, 12, 12);
                g2.drawLine(5, 10, 10, 5);
                g2.drawLine(17, 10, 22, 5);
                g2.drawLine(5, 22, 10, 17);
                g2.drawLine(17, 22, 22, 17);
                break;
        }

        g2.dispose();
        return new ImageIcon(img);
    }

    private JButton createToolButton(String mode, String title, String tooltip, Icon icon) {
        JButton btn = new JButton(icon);
        btn.setToolTipText(tooltip);
        btn.setPreferredSize(new Dimension(42, 42));
        btn.setMinimumSize(new Dimension(42, 42));
        btn.setMaximumSize(new Dimension(42, 42));
        btn.setFocusPainted(false);
        Border raised = BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED);
        btn.setBorder(raised);
        btn.addActionListener(e -> setMode(mode, title));
        return btn;
    }

    private void buildMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("Файл");
        JMenuItem openItem = new JMenuItem("Відкрити з файлу...");
        openItem.addActionListener(e -> openAnyFile());

        JMenuItem saveAsItem = new JMenuItem("Зберегти як...");
        saveAsItem.addActionListener(e -> saveToAnyFile());

        JMenuItem tableItem = new JMenuItem("Таблиця об'єктів");
        tableItem.addActionListener(e -> tableFrame.setVisible(true));

        JMenuItem exitItem = new JMenuItem("Вихід");
        exitItem.addActionListener(e -> System.exit(0));

        menuFile.add(openItem);
        menuFile.add(saveAsItem);
        menuFile.add(tableItem);
        menuFile.addSeparator();
        menuFile.add(exitItem);

        JMenu menuObjects = new JMenu("Об'єкти");
        JMenuItem itemDot = new JMenuItem("Крапка", createShapeIcon("Dot"));
        JMenuItem itemLine = new JMenuItem("Лінія", createShapeIcon("Line"));
        JMenuItem itemRect = new JMenuItem("Прямокутник", createShapeIcon("Rectangle"));
        JMenuItem itemEllipse = new JMenuItem("Еліпс", createShapeIcon("Ellipse"));
        JMenuItem itemLineOO = new JMenuItem("Лінія з кружечками", createShapeIcon("LineOO"));
        JMenuItem itemCube = new JMenuItem("Каркас куба", createShapeIcon("Cube"));

        itemDot.addActionListener(e -> setMode("Dot", "Режим вводу крапок"));
        itemLine.addActionListener(e -> setMode("Line", "Режим вводу ліній"));
        itemRect.addActionListener(e -> setMode("Rectangle", "Режим вводу прямокутників"));
        itemEllipse.addActionListener(e -> setMode("Ellipse", "Режим вводу еліпсів"));
        itemLineOO.addActionListener(e -> setMode("LineOO", "Режим вводу ліній з кружечками"));
        itemCube.addActionListener(e -> setMode("Cube", "Режим вводу каркасів куба"));

        menuObjects.add(itemDot);
        menuObjects.add(itemLine);
        menuObjects.add(itemRect);
        menuObjects.add(itemEllipse);
        menuObjects.add(itemLineOO);
        menuObjects.add(itemCube);

        JMenu menuHelp = new JMenu("Довідка");
        JMenuItem aboutItem = new JMenuItem("Про програму");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Лабораторна робота №5 — Варіант 9\nSingleton Меєрса, таблиця об'єктів, завантаження/збереження файлу.",
                "Довідка",
                JOptionPane.INFORMATION_MESSAGE
        ));
        menuHelp.add(aboutItem);

        menuBar.add(menuFile);
        menuBar.add(menuObjects);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);
    }

    private void buildToolBar() {
        JToolBar toolBar = new JToolBar("Інструменти");
        toolBar.setFloatable(false);
        toolBar.setBackground(new Color(240, 240, 240));

        toolBar.add(createToolButton("Dot", "Режим вводу крапок", "Крапка", createShapeIcon("Dot")));
        toolBar.add(createToolButton("Line", "Режим вводу ліній", "Лінія", createShapeIcon("Line")));
        toolBar.add(createToolButton("Rectangle", "Режим вводу прямокутників", "Прямокутник (від центру до кута)", createShapeIcon("Rectangle")));
        toolBar.add(createToolButton("Ellipse", "Режим вводу еліпсів", "Еліпс", createShapeIcon("Ellipse")));
        toolBar.add(Box.createHorizontalStrut(8));
        toolBar.add(createToolButton("LineOO", "Режим вводу ліній з кружечками", "Лінія з кружечками", createShapeIcon("LineOO")));
        toolBar.add(createToolButton("Cube", "Режим вводу каркасів куба", "Каркас куба", createShapeIcon("Cube")));

        add(toolBar, BorderLayout.NORTH);
    }

    private void openAnyFile() {
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setDialogTitle("Виберіть будь-який файл з фігурами");

        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File chosen = chooser.getSelectedFile();
            manager.loadFromFile(chosen);
            canvas.repaint();
            JOptionPane.showMessageDialog(this, "Дані успішно завантажено з файлу: " + chosen.getName());
        }
    }

    private void saveToAnyFile() {
        JFileChooser chooser = new JFileChooser(new File("."));
        chooser.setDialogTitle("Зберегти фігури у файл");
        chooser.setSelectedFile(new File("shapes_log.txt"));

        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File target = chooser.getSelectedFile();
            manager.saveAllToFile(target);
            JOptionPane.showMessageDialog(this, "Збережено у файл: " + target.getName());
        }
    }

    private void buildCanvasArea() {
        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                manager.renderAll(g);

                if (isDrawing && g instanceof Graphics2D) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    float[] dash = {6f, 4f};
                    g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dash, 0f));
                    g2.setColor(Color.RED);

                    if (selectedMode.equals("Line") || selectedMode.equals("LineOO")) {
                        g2.drawLine(startX, startY, currentX, currentY);
                    } else if (selectedMode.equals("Rectangle")) {
                        int w = Math.abs(currentX - startX) * 2;
                        int h = Math.abs(currentY - startY) * 2;
                        g2.drawRect(startX - w / 2, startY - h / 2, w, h);
                    } else if (selectedMode.equals("Ellipse")) {
                        int x = Math.min(startX, currentX);
                        int y = Math.min(startY, currentY);
                        int w = Math.abs(currentX - startX);
                        int h = Math.abs(currentY - startY);
                        g2.drawOval(x, y, w, h);
                    } else if (selectedMode.equals("Cube")) {
                        int left = Math.min(startX, currentX);
                        int top = Math.min(startY, currentY);
                        int size = Math.max(Math.abs(currentX - startX), Math.abs(currentY - startY));
                        int offset = Math.max(10, size / 3);
                        g2.drawRect(left, top + offset, size, size);
                        g2.drawRect(left + offset, top, size, size);
                    }
                    g2.dispose();
                }
            }
        };

        canvas.setBackground(Color.WHITE);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                currentX = startX;
                currentY = startY;
                isDrawing = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isDrawing = false;
                GeometricFigure newFig = null;

                switch (selectedMode) {
                    case "Dot":
                        DotFigure d = new DotFigure();
                        d.setCoordinates(e.getX(), e.getY(), e.getX(), e.getY());
                        newFig = d;
                        break;
                    case "Line":
                        LineFigure l = new LineFigure();
                        l.setCoordinates(startX, startY, e.getX(), e.getY());
                        newFig = l;
                        break;
                    case "Rectangle":
                        RectangleFigure r = new RectangleFigure();
                        r.setCoordinates(startX, startY, e.getX(), e.getY());
                        newFig = r;
                        break;
                    case "Ellipse":
                        EllipseFigure el = new EllipseFigure();
                        el.setCoordinates(startX, startY, e.getX(), e.getY());
                        newFig = el;
                        break;
                    case "LineOO":
                        LineOOFigure loo = new LineOOFigure();
                        loo.setCoordinates(startX, startY, e.getX(), e.getY());
                        newFig = loo;
                        break;
                    case "Cube":
                        CubeFigure cb = new CubeFigure();
                        cb.setCoordinates(startX, startY, e.getX(), e.getY());
                        newFig = cb;
                        break;
                }

                if (newFig != null) {
                    manager.addFigure(newFig, true);
                }
                canvas.repaint();
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                canvas.repaint();
            }
        });

        add(canvas, BorderLayout.CENTER);
    }

    @Override
    public void onObjectSelected(int index) {
        manager.selectFigure(index);
        canvas.repaint();
    }

    @Override
    public void onObjectDeleted(int index) {
        manager.deleteFigure(index);
        canvas.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrawingCanvasApp().setVisible(true));
    }
}
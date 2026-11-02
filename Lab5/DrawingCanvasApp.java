package Lab5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DrawingCanvasApp extends JFrame implements ObjectSelectionListener {
    private ObjectManager manager;
    private ObjectTableFrame tableFrame;
    private JPanel canvas;
    
    private int startX, startY, currentX, currentY;
    private boolean isDrawing = false;
    private String selectedMode = "Rectangle";

    public DrawingCanvasApp() {
        setTitle("Графічний редактор - Лабораторна робота №5 (Варіант 9)");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        manager = ObjectManager.getInstance();
        tableFrame = new ObjectTableFrame(this, this);
        manager.setTableWindow(tableFrame);

        buildMenu();
        buildCanvasArea();

       
        manager.loadFromFile("shapes_log.txt");
    }

    private void buildMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuFile = new JMenu("Файл");
        JMenuItem loadItem = new JMenuItem("Завантажити з файлу");
        loadItem.addActionListener(e -> {
            manager.loadFromFile("shapes_log.txt");
            canvas.repaint();
            JOptionPane.showMessageDialog(this, "Дані успішно завантажено з файлу!");
        });
        JMenuItem exitItem = new JMenuItem("Вихід");
        exitItem.addActionListener(e -> System.exit(0));
        menuFile.add(loadItem);
        menuFile.addSeparator();
        menuFile.add(exitItem);

        JMenu menuObjects = new JMenu("Об'єкти");
        JMenuItem itemDot = new JMenuItem("Крапка");
        JMenuItem itemLine = new JMenuItem("Лінія");
        JMenuItem itemRect = new JMenuItem("Прямокутник");
        JMenuItem itemEllipse = new JMenuItem("Еліпс");

        itemDot.addActionListener(e -> selectedMode = "Dot");
        itemLine.addActionListener(e -> selectedMode = "Line");
        itemRect.addActionListener(e -> selectedMode = "Rectangle");
        itemEllipse.addActionListener(e -> selectedMode = "Ellipse");

        menuObjects.add(itemDot);
        menuObjects.add(itemLine);
        menuObjects.add(itemRect);
        menuObjects.add(itemEllipse);

        JMenu menuView = new JMenu("Вікно");
        JMenuItem itemTable = new JMenuItem("Показати таблицю об'єктів");
        itemTable.addActionListener(e -> tableFrame.setVisible(true));
        menuView.add(itemTable);

        menuBar.add(menuFile);
        menuBar.add(menuObjects);
        menuBar.add(menuView);
        setJMenuBar(menuBar);
    }

    private void buildCanvasArea() {
        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                manager.renderAll(g);

                if (isDrawing) {
                    g.setColor(Color.RED);
                    if (selectedMode.equals("Line")) {
                        g.drawLine(startX, startY, currentX, currentY);
                    } else if (selectedMode.equals("Rectangle")) {
                        int w = Math.abs(currentX - startX) * 2;
                        int h = Math.abs(currentY - startY) * 2;
                        g.drawRect(startX - w / 2, startY - h / 2, w, h);
                    } else if (selectedMode.equals("Ellipse")) {
                        int x = Math.min(startX, currentX);
                        int y = Math.min(startY, currentY);
                        int w = Math.abs(currentX - startX);
                        int h = Math.abs(currentY - startY);
                        g.drawOval(x, y, w, h);
                    }
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

                if (selectedMode.equals("Dot")) {
                    DotFigure d = new DotFigure();
                    d.setCoordinates(e.getX(), e.getY(), e.getX(), e.getY());
                    newFig = d;
                } else if (selectedMode.equals("Line")) {
                    LineFigure l = new LineFigure();
                    l.setCoordinates(startX, startY, e.getX(), e.getY());
                    newFig = l;
                } else if (selectedMode.equals("Rectangle")) {
                    RectangleFigure r = new RectangleFigure();
                    r.setCoordinates(startX, startY, e.getX(), e.getY());
                    newFig = r;
                } else if (selectedMode.equals("Ellipse")) {
                    EllipseFigure el = new EllipseFigure();
                    el.setCoordinates(startX, startY, e.getX(), e.getY());
                    newFig = el;
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
        SwingUtilities.invokeLater(() -> {
            new DrawingCanvasApp().setVisible(true);
        });
    }
}
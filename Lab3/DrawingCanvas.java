package Lab3;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingCanvas extends JPanel {
    public static final int CAPACITY = 110; 
    private Shape[] shapes = new Shape[CAPACITY];
    private int shapeCount = 0;
    
    private int activeToolType = 0;
    private int startX, startY, currentX, currentY;
    private boolean isDrawing = false;

    public DrawingCanvas() {
        setBackground(Color.WHITE);

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = currentX = e.getX();
                startY = currentY = e.getY();
                isDrawing = true;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDrawing) {
                    currentX = e.getX();
                    currentY = e.getY();
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!isDrawing) return;
                isDrawing = false;

                if (shapeCount >= CAPACITY) return;

                Shape newShape = null;
                switch (activeToolType) {
                    case 0: 
                        newShape = new PointShape();
                        newShape.set(currentX, currentY, currentX, currentY);
                        break;
                    case 1: 
                        newShape = new LineShape();
                        newShape.set(startX, startY, currentX, currentY);
                        break;
                    case 2: 
                        newShape = new RectShape();
                        int w = Math.abs(currentX - startX);
                        int h = Math.abs(currentY - startY);
                        newShape.set(startX - w, startY - h, startX + w, startY + h);
                        break;
                    case 3: 
                        newShape = new EllipseShape();
                        newShape.set(startX, startY, currentX, currentY);
                        break;
                }

                if (newShape != null) {
                    shapes[shapeCount++] = newShape;
                }
                repaint();
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public void setActiveToolType(int toolType) {
        this.activeToolType = toolType;
    }

    public int getActiveToolType() {
        return this.activeToolType;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        

        for (int i = 0; i < shapeCount; i++) {
            if (shapes[i] != null) {
                shapes[i].draw(g);
            }
        }


        if (isDrawing) {
            g.setColor(Color.BLUE);
            switch (activeToolType) {
                case 1: 
                    g.drawLine(startX, startY, currentX, currentY);
                    break;
                case 2: 
                    int w = Math.abs(currentX - startX);
                    int h = Math.abs(currentY - startY);
                    g.drawRect(startX - w, startY - h, w * 2, h * 2);
                    break;
                case 3:
                    int rx = Math.min(startX, currentX);
                    int ry = Math.min(startY, currentY);
                    int rw = Math.abs(currentX - startX);
                    int rh = Math.abs(currentY - startY);
                    g.drawOval(rx, ry, rw, rh);
                    break;
            }
        }
    }
}
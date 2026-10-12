package Lab3;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingCanvas extends JPanel {
    public static final int CAPACITY = 110;
    private GraphicalObject[] objectArray = new GraphicalObject[CAPACITY];
    private int elementCounter = 0;
    private int activeToolType = 0; 

    private int currentX1, currentY1, currentX2, currentY2;
    private boolean isProcessDrawing = false;

    public DrawingCanvas() {
        setBackground(Color.WHITE);

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentX1 = currentX2 = e.getX();
                currentY1 = currentY2 = e.getY();
                isProcessDrawing = true;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (isProcessDrawing) {
                    currentX2 = e.getX();
                    currentY2 = e.getY();
                    repaint(); 
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (!isProcessDrawing) return;
                isProcessDrawing = false;

                if (elementCounter >= CAPACITY) return;

                GraphicalObject newObject = null;
                switch (activeToolType) {
                    case 0: 
                        newObject = new DotElement();
                        newObject.setCoordinates(currentX2, currentY2, currentX2, currentY2);
                        break;
                    case 1:
                        newObject = new SegmentElement();
                        newObject.setCoordinates(currentX1, currentY1, currentX2, currentY2);
                        break;
                    case 2:
                        newObject = new BoxElement();
                        newObject.setCoordinates(currentX1, currentY1, currentX2, currentY2);
                        break;
                    case 3: 
                        newObject = new OvalElement();
                        int rx = Math.abs(currentX2 - currentX1);
                        int ry = Math.abs(currentY2 - currentY1);
                        newObject.setCoordinates(currentX1 - rx, currentY1 - ry, currentX1 + rx, currentY1 + ry);
                        break;
                }

                if (newObject != null) {
                    objectArray[elementCounter++] = newObject;
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


        for (int i = 0; i < elementCounter; i++) {
            if (objectArray[i] != null) {
                objectArray[i].draw(g);
            }
        }

    
        if (isProcessDrawing) {
            g.setColor(Color.BLUE);
            g.drawLine(currentX1, currentY1, currentX2, currentY2);
        }
    }
}
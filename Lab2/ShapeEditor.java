package Lab2;
import java.awt.*;

public class ShapeEditor {
    // Динамічний масив вказівників на 109 об'єктів (N = 9 + 100 = 109, 9 mod 3 = 0)[cite: 52, 106, 108]
    private Shape[] shapes = new Shape[109];
    private int count = 0;
    private Editor currentEditor = null;

    public void startPoint() { currentEditor = new PointEditor(); }
    public void startLine() { currentEditor = new LineEditor(); }
    public void startRect() { currentEditor = new RectEditor(); }
    public void startEllipse() { currentEditor = new EllipseEditor(); }

    public void onMouseDown(int x, int y) {
        if (currentEditor != null) currentEditor.onMouseDown(x, y);
    }

    public void onMouseMove(int x, int y) {
        if (currentEditor != null) currentEditor.onMouseMove(x, y);
    }

    public void onMouseUp(int x, int y) {
        if (currentEditor != null && count < 109) {
            Shape newShape = currentEditor.onMouseUp(x, y);
            if (newShape != null) {
                shapes[count++] = newShape;
            }
        }
    }

    public void drawAll(Graphics g) {
        for (int i = 0; i < count; i++) {
            if (shapes[i] != null) {
                shapes[i].draw(g);
            }
        }
    }

    public void drawRubber(Graphics g) {
        if (currentEditor != null) {
            currentEditor.drawRubber(g);
        }
    }
}
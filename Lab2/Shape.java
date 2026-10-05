package Lab2;
import java.awt.*;

abstract class Shape {
    protected int x1, y1, x2, y2;

    public void setCoordinates(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public abstract void draw(Graphics g);
}

class PointShape extends Shape {
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x1, y1, 2, 2);
    }
}

class LineShape extends Shape {
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);
    }
}

class RectShape extends Shape {
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        // Чорний контур без заповнення (9 mod 5 = 4)[cite: 53, 106]
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int w = Math.abs(x2 - x1);
        int h = Math.abs(y2 - y1);
        g.drawRect(x, y, w, h);
    }
}

class EllipseShape extends Shape {
    @Override
    public void draw(Graphics g) {
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int w = Math.abs(x2 - x1);
        int h = Math.abs(y2 - y1);

        // Блакитне заповнення (9 mod 6 = 3) та чорний контур (9 mod 5 = 4)[cite: 44, 53, 54, 106]
        g.setColor(new Color(0, 255, 255));
        g.fillOval(x, y, w, h);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, w, h);
    }
}
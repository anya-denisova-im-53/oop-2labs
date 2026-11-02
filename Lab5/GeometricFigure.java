package Lab5;
import java.awt.*;

abstract class GeometricFigure {
    protected int x1, y1, x2, y2;
    protected boolean selected = false;

    public void setCoordinates(int x1, int y1, int x2, int y2) {
        this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
    }

    public int getX1() { return x1; }
    public int getY1() { return y1; }
    public int getX2() { return x2; }
    public int getY2() { return y2; }

    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }

    public abstract void draw(Graphics g);
    public abstract String getTitle();
}

class DotFigure extends GeometricFigure {
    @Override
    public void draw(Graphics g) {
        g.setColor(selected ? Color.RED : Color.BLACK);
        g.fillRect(x1, y1, 4, 4);
    }
    @Override
    public String getTitle() { return "Крапка"; }
}

class LineFigure extends GeometricFigure {
    @Override
    public void draw(Graphics g) {
        g.setColor(selected ? Color.RED : Color.BLACK);
        g.drawLine(x1, y1, x2, y2);
    }
    @Override
    public String getTitle() { return "Лінія"; }
}

class RectangleFigure extends GeometricFigure {
    @Override
    public void draw(Graphics g) {
        
        int w = Math.abs(x2 - x1) * 2;
        int h = Math.abs(y2 - y1) * 2;
        int x = x1 - w / 2;
        int y = y1 - h / 2;
        g.setColor(selected ? Color.RED : Color.BLACK);
        g.drawRect(x, y, w, h);
    }
    @Override
    public String getTitle() { return "Прямокутник"; }
}

class EllipseFigure extends GeometricFigure {
    @Override
    public void draw(Graphics g) {
       
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int w = Math.abs(x2 - x1);
        int h = Math.abs(y2 - y1);
        
        g.setColor(new Color(0, 255, 255)); 
        g.fillOval(x, y, w, h);
        g.setColor(selected ? Color.RED : Color.BLACK); 
        g.drawOval(x, y, w, h);
    }
    @Override
    public String getTitle() { return "Еліпс"; }
}
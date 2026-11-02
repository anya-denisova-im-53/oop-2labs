package Lab4;
import java.awt.*;

abstract class Shape4 {
    protected int x1, y1, x2, y2;

    public void setCoordinates(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public abstract void draw(Graphics g);
}

class PointFig4 extends Shape4 {
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x1, y1, 3, 3);
    }
}

class LineFig4 extends Shape4 {
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);
    }
}

class RectFig4 extends Shape4 {
    @Override
    public void draw(Graphics g) {
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int w = Math.abs(x2 - x1);
        int h = Math.abs(y2 - y1);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, w, h);
    }
}

class EllipseFig4 extends Shape4 {
    @Override
    public void draw(Graphics g) {
        int x = Math.min(x1, x2);
        int y = Math.min(y1, y2);
        int w = Math.abs(x2 - x1);
        int h = Math.abs(y2 - y1);
        
        g.setColor(new Color(0, 255, 255)); 
        g.fillOval(x, y, w, h);
        g.setColor(Color.BLACK);
        g.drawOval(x, y, w, h);
    }
}


class LineOOShape extends Shape4 {
    private LineFig4 line = new LineFig4();
    private EllipseFig4 circle1 = new EllipseFig4();
    private EllipseFig4 circle2 = new EllipseFig4();

    @Override
    public void setCoordinates(int x1, int y1, int x2, int y2) {
        super.setCoordinates(x1, y1, x2, y2);
        line.setCoordinates(x1, y1, x2, y2);
        circle1.setCoordinates(x1 - 5, y1 - 5, x1 + 5, y1 + 5);
        circle2.setCoordinates(x2 - 5, y2 - 5, x2 + 5, y2 + 5);
    }

    @Override
    public void draw(Graphics g) {
        line.draw(g);
        circle1.draw(g);
        circle2.draw(g);
    }
}


class CubeShape extends Shape4 {
    private RectFig4 frontRect = new RectFig4();
    private RectFig4 backRect = new RectFig4();
    private LineFig4 l1 = new LineFig4();
    private LineFig4 l2 = new LineFig4();
    private LineFig4 l3 = new LineFig4();
    private LineFig4 l4 = new LineFig4();

    @Override
    public void setCoordinates(int x1, int y1, int x2, int y2) {
        super.setCoordinates(x1, y1, x2, y2);
        int minX = Math.min(x1, x2);
        int maxX = Math.max(x1, x2);
        int minY = Math.min(y1, y2);
        int maxY = Math.max(y1, y2);
        
        int totalW = maxX - minX;
        int totalH = maxY - minY;
        
        int size = Math.min(totalW, totalH);
        if (size < 25) size = 50; 
        
        int offset = size / 3; 
        
        
        int fX = minX;
        int fY = minY + offset;
        frontRect.setCoordinates(fX, fY, fX + size, fY + size);
        
       
        int bX = minX + offset;
        int bY = minY;
        backRect.setCoordinates(bX, bY, bX + size, bY + size);
        
    
        l1.setCoordinates(fX, fY, bX, bY);
        l2.setCoordinates(fX + size, fY, bX + size, bY);
        l3.setCoordinates(fX, fY + size, bX, bY + size);
        l4.setCoordinates(fX + size, fY + size, bX + size, bY + size);
    }

    @Override
    public void draw(Graphics g) {
        frontRect.draw(g);
        backRect.draw(g);
        l1.draw(g);
        l2.draw(g);
        l3.draw(g);
        l4.draw(g);
    }
}
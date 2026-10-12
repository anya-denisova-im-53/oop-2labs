package Lab3;
import java.awt.Graphics;
import java.awt.Color;

public abstract class GraphicalObject {
    protected int startX, startY, endX, endY;

    public void setCoordinates(int x1, int y1, int x2, int y2) {
        this.startX = x1;
        this.startY = y1;
        this.endX = x2;
        this.endY = y2;
    }

    public abstract void draw(Graphics g);
}


class DotElement extends GraphicalObject {
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(startX, startY, 2, 2);
    }
}


class SegmentElement extends GraphicalObject {
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(startX, startY, endX, endY);
    }
}


class BoxElement extends GraphicalObject {
    @Override
    public void draw(Graphics g) {
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);

        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);
    }
}


class OvalElement extends GraphicalObject {
    @Override
    public void draw(Graphics g) {
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);

        g.setColor(Color.BLACK);
        g.drawOval(x, y, width, height);
    }
}
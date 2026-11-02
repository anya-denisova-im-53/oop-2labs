package Lab4;
import java.awt.*;

abstract class Editor4 {
    protected int startX, startY, currentX, currentY;
    protected boolean active = false;

    public void onMouseDown(int x, int y) {
        startX = x; startY = y;
        currentX = x; currentY = y;
        active = true;
    }

    public void onMouseMove(int x, int y) {
        currentX = x; currentY = y;
    }

    public abstract Shape4 onMouseUp(int x, int y);

    public void drawRubber(Graphics g) {
        if (!active) return;
        Graphics2D g2d = (Graphics2D) g;
       
        float[] dash = { 6.0f, 6.0f };
        g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
        g2d.setColor(Color.RED);
        drawPreview(g2d);
    }

    protected abstract void drawPreview(Graphics g);
}

class PointEdit4 extends Editor4 {
    @Override public Shape4 onMouseUp(int x, int y) {
        active = false; PointFig4 p = new PointFig4(); p.setCoordinates(x, y, x, y); return p;
    }
    @Override protected void drawPreview(Graphics g) {}
}

class LineEdit4 extends Editor4 {
    @Override public Shape4 onMouseUp(int x, int y) {
        active = false; LineFig4 l = new LineFig4(); l.setCoordinates(startX, startY, x, y); return l;
    }
    @Override protected void drawPreview(Graphics g) { g.drawLine(startX, startY, currentX, currentY); }
}

class RectEdit4 extends Editor4 {
    @Override public Shape4 onMouseUp(int x, int y) {
        active = false; RectFig4 r = new RectFig4(); r.setCoordinates(startX, startY, x, y); return r;
    }
    @Override protected void drawPreview(Graphics g) {
        int w = Math.abs(currentX - startX) * 2;
        int h = Math.abs(currentY - startY) * 2;
        g.drawRect(startX - w / 2, startY - h / 2, w, h);
    }
}

class EllipseEdit4 extends Editor4 {
    @Override public Shape4 onMouseUp(int x, int y) {
        active = false; EllipseFig4 e = new EllipseFig4(); e.setCoordinates(startX, startY, x, y); return e;
    }
    @Override protected void drawPreview(Graphics g) {
        int x = Math.min(startX, currentX); int y = Math.min(startY, currentY);
        g.drawOval(x, y, Math.abs(currentX - startX), Math.abs(currentY - startY));
    }
}

class LineOOEdit extends Editor4 {
    @Override public Shape4 onMouseUp(int x, int y) {
        active = false; LineOOShape s = new LineOOShape(); s.setCoordinates(startX, startY, x, y); return s;
    }
    @Override protected void drawPreview(Graphics g) {
        g.drawLine(startX, startY, currentX, currentY);
        g.drawOval(startX - 5, startY - 5, 10, 10);
        g.drawOval(currentX - 5, currentY - 5, 10, 10);
    }
}

class CubeEdit extends Editor4 {
    @Override public Shape4 onMouseUp(int x, int y) {
        active = false; CubeShape s = new CubeShape(); s.setCoordinates(startX, startY, x, y); return s;
    }
    @Override protected void drawPreview(Graphics g) {
        g.drawRect(Math.min(startX, currentX), Math.min(startY, currentY), Math.abs(currentX - startX), Math.abs(currentY - startY));
    }
}
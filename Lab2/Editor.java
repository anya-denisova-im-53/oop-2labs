package Lab2;
import java.awt.*;

abstract class Editor {
    protected int startX, startY, currentX, currentY;
    protected boolean active = false;

    public void onMouseDown(int x, int y) {
        startX = x;
        startY = y;
        currentX = x;
        currentY = y;
        active = true;
    }

    public void onMouseMove(int x, int y) {
        currentX = x;
        currentY = y;
    }

    public abstract Shape onMouseUp(int x, int y);

    public void drawRubber(Graphics g) {
        if (!active) return;
        Graphics2D g2d = (Graphics2D) g;
        // Суцільна червона лінія для гумового сліду (9 mod 4 = 1)[cite: 52, 106]
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(1f));
        drawShapePreview(g2d);
    }

    protected abstract void drawShapePreview(Graphics g);
}

class PointEditor extends Editor {
    @Override
    public Shape onMouseUp(int x, int y) {
        active = false;
        PointShape pt = new PointShape();
        pt.setCoordinates(x, y, x, y);
        return pt;
    }
    @Override
    protected void drawShapePreview(Graphics g) {}
}

class LineEditor extends Editor {
    @Override
    public Shape onMouseUp(int x, int y) {
        active = false;
        LineShape line = new LineShape();
        line.setCoordinates(startX, startY, x, y);
        return line;
    }
    @Override
    protected void drawShapePreview(Graphics g) {
        g.drawLine(startX, startY, currentX, currentY);
    }
}

class RectEditor extends Editor {
    @Override
    public void onMouseDown(int x, int y) {
        // Ввід від центру до кута (9 mod 2 = 1)[cite: 53, 106]
        super.onMouseDown(x, y);
    }

    @Override
    public Shape onMouseUp(int x, int y) {
        active = false;
        RectShape rect = new RectShape();
        int dx = x - startX;
        int dy = y - startY;
        rect.setCoordinates(startX - dx, startY - dy, startX + dx, startY + dy);
        return rect;
    }

    @Override
    protected void drawShapePreview(Graphics g) {
        int dx = currentX - startX;
        int dy = currentY - startY;
        int rx = startX - dx;
        int ry = startY - dy;
        g.drawRect(rx, ry, Math.abs(dx * 2), Math.abs(dy * 2));
    }
}

class EllipseEditor extends Editor {
    @Override
    public Shape onMouseUp(int x, int y) {
        active = false;
        EllipseShape el = new EllipseShape();
        el.setCoordinates(startX, startY, x, y);
        return el;
    }

    @Override
    protected void drawShapePreview(Graphics g) {
        int x = Math.min(startX, currentX);
        int y = Math.min(startY, currentY);
        int w = Math.abs(currentX - startX);
        int h = Math.abs(currentY - startY);
        g.drawOval(x, y, w, h);
    }
}
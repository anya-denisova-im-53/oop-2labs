package Lab4;
import java.awt.*;
import java.util.ArrayList;

public class MyEditor {
    private ArrayList<Shape4> pshape = new ArrayList<>();
    private Editor4 pCurEditor = new PointEdit4();
    private String currentModeName = "Крапка";

    public void start(Editor4 newEditor, String modeName) {
        pCurEditor = newEditor;
        currentModeName = modeName;
    }

    public String getCurrentModeName() { return currentModeName; }

    public void onLBdown(int x, int y) { pCurEditor.onMouseDown(x, y); }
    public void onMouseMove(int x, int y) { pCurEditor.onMouseMove(x, y); }
    
    public void onLBup(int x, int y) {
        if (pshape.size() < 109) { 
            Shape4 s = pCurEditor.onMouseUp(x, y);
            if (s != null) pshape.add(s);
        }
    }

    public void onPaint(Graphics g) {
        for (Shape4 s : pshape) {
            if (s != null) s.draw(g);
        }
        pCurEditor.drawRubber(g);
    }
}
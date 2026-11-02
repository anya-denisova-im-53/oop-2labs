package Lab5;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

class ObjectManager {
    // Singleton Меєрса
    private static class Holder {
        private static final ObjectManager INSTANCE = new ObjectManager();
    }

    private ArrayList<GeometricFigure> storage = new ArrayList<>();
    private ObjectTableFrame tableWindow;

    private ObjectManager() {}

    public static ObjectManager getInstance() {
        return Holder.INSTANCE;
    }

    public void setTableWindow(ObjectTableFrame tableWindow) {
        this.tableWindow = tableWindow;
    }

    public void addFigure(GeometricFigure fig, boolean saveToFile) {
        if (storage.size() < 109) { // N = 9 + 100 = 109
            storage.add(fig);
            if (saveToFile) {
                appendToFile(fig);
            }
            if (tableWindow != null) {
                tableWindow.refreshTable(storage);
            }
        }
    }

    private void appendToFile(GeometricFigure fig) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("shapes_log.txt", true))) {
            writer.write(fig.getTitle() + "\t" + fig.getX1() + "\t" + fig.getY1() + "\t" + fig.getX2() + "\t" + fig.getY2() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        storage.clear();
        File file = new File(filename);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name")) continue;
                String[] parts = line.split("\t");
                if (parts.length >= 5) {
                    String name = parts[0];
                    int x1 = Integer.parseInt(parts[1]);
                    int y1 = Integer.parseInt(parts[2]);
                    int x2 = Integer.parseInt(parts[3]);
                    int y2 = Integer.parseInt(parts[4]);

                    GeometricFigure fig = null;
                    if (name.equals("Крапка")) fig = new DotFigure();
                    else if (name.equals("Лінія")) fig = new LineFigure();
                    else if (name.equals("Прямокутник")) fig = new RectangleFigure();
                    else if (name.equals("Еліпс")) fig = new EllipseFigure();

                    if (fig != null) {
                        fig.setCoordinates(x1, y1, x2, y2);
                        storage.add(fig);
                    }
                }
            }
            if (tableWindow != null) {
                tableWindow.refreshTable(storage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectFigure(int index) {
        for (int i = 0; i < storage.size(); i++) {
            storage.get(i).setSelected(i == index);
        }
    }

    public void deleteFigure(int index) {
        if (index >= 0 && index < storage.size()) {
            storage.remove(index);
            if (tableWindow != null) {
                tableWindow.refreshTable(storage);
            }
            rewriteFile();
        }
    }

    private void rewriteFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("shapes_log.txt"))) {
            writer.write("Name\tX1\tY1\tX2\tY2\n");
            for (GeometricFigure fig : storage) {
                writer.write(fig.getTitle() + "\t" + fig.getX1() + "\t" + fig.getY1() + "\t" + fig.getX2() + "\t" + fig.getY2() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void renderAll(Graphics g) {
        for (GeometricFigure f : storage) {
            if (f != null) f.draw(g);
        }
    }
}
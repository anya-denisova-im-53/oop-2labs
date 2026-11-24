package Lab5;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

class ObjectManager {
    private static class Holder {
        private static final ObjectManager INSTANCE = new ObjectManager();
    }

    private final ArrayList<GeometricFigure> storage = new ArrayList<>();
    private ObjectTableFrame tableWindow;
    private File currentFile = new File("shapes_log.txt");

    private ObjectManager() {}

    public static ObjectManager getInstance() {
        return Holder.INSTANCE;
    }

    public void setTableWindow(ObjectTableFrame tableWindow) {
        this.tableWindow = tableWindow;
    }

    public void addFigure(GeometricFigure fig, boolean saveToFile) {
        if (fig == null) return;
        if (storage.size() < 109) { 
            storage.add(fig);
            if (saveToFile) {
                appendToFile(fig, currentFile);
            }
            if (tableWindow != null) {
                tableWindow.refreshTable(storage);
            }
        }
    }

    private void appendToFile(GeometricFigure fig, File targetFile) {
        if (targetFile == null) return;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile, true))) {
            writer.write(fig.getTitle() + "\t" + fig.getX1() + "\t" + fig.getY1() + "\t" + fig.getX2() + "\t" + fig.getY2() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(File file) {
        if (file == null || !file.exists()) return;
        this.currentFile = file;
        storage.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("Name")) continue;

                String[] parts = line.split("\t");
                if (parts.length >= 5) {
                    String name = parts[0].trim();
                    int x1 = Integer.parseInt(parts[1].trim());
                    int y1 = Integer.parseInt(parts[2].trim());
                    int x2 = Integer.parseInt(parts[3].trim());
                    int y2 = Integer.parseInt(parts[4].trim());

                    GeometricFigure fig = createFigureByTitle(name);
                    if (fig != null && storage.size() < 109) {
                        fig.setCoordinates(x1, y1, x2, y2);
                        storage.add(fig);
                    }
                }
            }
            if (tableWindow != null) {
                tableWindow.refreshTable(storage);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private GeometricFigure createFigureByTitle(String title) {
        switch (title) {
            case "Крапка":
                return new DotFigure();
            case "Лінія":
                return new LineFigure();
            case "Прямокутник":
                return new RectangleFigure();
            case "Еліпс":
                return new EllipseFigure();
            case "Лінія з кружечками":
                return new LineOOFigure();
            case "Каркас куба":
                return new CubeFigure();
            default:
                return null;
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
            rewriteFile(currentFile);
        }
    }

    public void saveAllToFile(File targetFile) {
        if (targetFile == null) return;
        this.currentFile = targetFile;
        rewriteFile(targetFile);
    }

    private void rewriteFile(File targetFile) {
        if (targetFile == null) return;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile, false))) {
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
            if (f != null) {
                f.draw(g);
            }
        }
    }
}
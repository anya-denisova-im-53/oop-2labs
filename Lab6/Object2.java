package Lab6;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.net.*;
import java.util.Random;

public class Object2 extends JFrame {
    private JTextArea textArea;
    private static final int PORT = 5001;

    public Object2() {
        setTitle("Object2 - Генератор Матриці");
        setSize(400, 300);
        setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        setVisible(true);

       
        new Thread(this::startServer).start();
    }

    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String line = in.readLine();
                    if (line != null) {
                        String[] parts = line.split(",");
                        int n = Integer.parseInt(parts[0]);
                        int min = Integer.parseInt(parts[1]);
                        int max = Integer.parseInt(parts[2]);

                        generateAndCopyToClipboard(n, min, max);
                        out.println("DONE"); 
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateAndCopyToClipboard(int n, int min, int max) {
        int[][] matrix = new int[n][n];
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = rand.nextInt((max - min) + 1) + min;
                sb.append(matrix[i][j]).append(j == n - 1 ? "" : "\t");
            }
            if (i < n - 1) sb.append("\n");
        }

        String matrixText = sb.toString();
        
        
        SwingUtilities.invokeLater(() -> textArea.setText("Згенерована матриця " + n + "x" + n + ":\n\n" + matrixText));

        
        StringSelection selection = new StringSelection(matrixText);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Object2::new);
    }
}
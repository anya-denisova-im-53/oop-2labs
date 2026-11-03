package Lab6;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.net.*;

public class Object3 extends JFrame {
    private JLabel lblResult;
    private JTextArea txtMatrixView;
    private static final int PORT = 5002;

    public Object3() {
        setTitle("Object3 - Обчислення Детермінанта");
        setSize(400, 300);
        setLocation(520, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        lblResult = new JLabel("Детермінант = ", SwingConstants.CENTER);
        lblResult.setFont(new Font("Serif", Font.BOLD, 18));
        add(lblResult, BorderLayout.NORTH);

        txtMatrixView = new JTextArea();
        txtMatrixView.setEditable(false);
        txtMatrixView.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(txtMatrixView), BorderLayout.CENTER);

        setVisible(true);

       
        new Thread(this::startServer).start();
    }

    private void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    String cmd = in.readLine();
                    if ("CALCULATE".equals(cmd)) {
                        readClipboardAndCompute();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readClipboardAndCompute() {
        try {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            String data = (String) clipboard.getData(DataFlavor.stringFlavor);

            if (data == null || data.isEmpty()) return;

            String[] lines = data.trim().split("\n");
            int n = lines.length;
            double[][] matrix = new double[n][n];

            for (int i = 0; i < n; i++) {
                String[] row = lines[i].trim().split("\t");
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = Double.parseDouble(row[j]);
                }
            }

            double det = calculateDeterminant(matrix);

            SwingUtilities.invokeLater(() -> {
                txtMatrixView.setText(" Зчитана матриця з Clipboard:\n\n" + data);
                lblResult.setText("Детермінант (det) = " + String.format("%.2f", det));
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private double calculateDeterminant(double[][] matrix) {
        int n = matrix.length;
        if (n == 1) return matrix[0][0];
        if (n == 2) return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        double det = 0;
        for (int i = 0; i < n; i++) {
            double[][] subMatrix = new double[n - 1][n - 1];
            for (int j = 1; j < n; j++) {
                int colIndex = 0;
                for (int k = 0; k < n; k++) {
                    if (k == i) continue;
                    subMatrix[j - 1][colIndex++] = matrix[j][k];
                }
            }
            det += Math.pow(-1, i) * matrix[0][i] * calculateDeterminant(subMatrix);
        }
        return det;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Object3::new);
    }
}
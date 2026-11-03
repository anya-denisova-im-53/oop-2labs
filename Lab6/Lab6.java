package Lab6;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Lab6 extends JFrame {
    private JTextField txtN, txtMin, txtMax;
    private static final int PORT_OBJ2 = 5001;
    private static final int PORT_OBJ3 = 5002;

    public Lab6() {
        setTitle("Lab6 - Менеджер (Варіант 1)");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel(" Розмірність n:"));
        txtN = new JTextField("3");
        add(txtN);

        add(new JLabel(" Min значення:"));
        txtMin = new JTextField("1");
        add(txtMin);

        add(new JLabel(" Max значення:"));
        txtMax = new JTextField("10");
        add(txtMax);

        JButton btnStart = new JButton("Виконати");
        btnStart.addActionListener(e -> new Thread(this::runWorkflow).start());
        add(btnStart);

        setVisible(true);
    }

    private void runWorkflow() {
        try {
            int n = Integer.parseInt(txtN.getText());
            int min = Integer.parseInt(txtMin.getText());
            int max = Integer.parseInt(txtMax.getText());

           
            ensureProcessRunning("Lab6.Object2", PORT_OBJ2);
            
            
            sendDataToSocket(PORT_OBJ2, n + "," + min + "," + max);

            
            ensureProcessRunning("Lab6.Object3", PORT_OBJ3);
            
           
            sendDataToSocket(PORT_OBJ3, "CALCULATE");

        } catch (Exception ex) {
            ex.printStackTrace();
            SwingUtilities.invokeLater(() -> 
                JOptionPane.showMessageDialog(this, "Помилка виконання: " + ex.getMessage())
            );
        }
    }

    private void sendDataToSocket(int port, String message) throws IOException, InterruptedException {
        Socket socket = null;
        
        for (int i = 0; i < 5; i++) {
            try {
                socket = new Socket("localhost", port);
                break;
            } catch (IOException e) {
                Thread.sleep(500);
            }
        }

        if (socket == null) {
            throw new IOException("Не вдалося підключитися до порту " + port);
        }

        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            
            out.println(message);
            if (port == PORT_OBJ2) {
                String response = in.readLine(); 
                if (!"DONE".equals(response)) {
                    throw new IOException("Помилка генерації в Object2");
                }
            }
        } finally {
            socket.close();
        }
    }

    private void ensureProcessRunning(String className, int port) {
        if (!isServerRunning(port)) {
            try {
                String javaHome = System.getProperty("java.home");
                String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
                
               
                File classDir = new File(Lab6.class.getProtectionDomain().getCodeSource().getLocation().toURI());
                String classpath = classDir.getAbsolutePath();

                ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);
                builder.inheritIO(); 
                builder.start();
                
                Thread.sleep(1000); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isServerRunning(int port) {
        try (Socket socket = new Socket("localhost", port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Lab6::new);
    }
}
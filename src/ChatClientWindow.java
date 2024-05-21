import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class ChatClientWindow extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JTextField loginField = new JTextField("логин");
    private final JPasswordField passwordField = new JPasswordField("пароль");
    private final JTextField ipAddressField = new JTextField("ip адресс");
    private final JTextField portField = new JTextField("порт");
    private final JTextField messageField = new JTextField();
    private final JTextArea chatArea = new JTextArea();
    private final JButton connectButton = new JButton("соеденить");
    private final JButton sendButton = new JButton("отправить");

    public ChatClientWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, WIDTH, HEIGHT);
        setTitle("Chat Client");
        setResizable(false);

        JPanel serverPanel = new JPanel();
        serverPanel.setLayout(new GridLayout(4, 2));
        serverPanel.add(new JLabel("Login:"));
        serverPanel.add(loginField);
        serverPanel.add(new JLabel("Password:"));
        serverPanel.add(passwordField);
        serverPanel.add(new JLabel("IP Address:"));
        serverPanel.add(ipAddressField);
        serverPanel.add(new JLabel("Port:"));
        serverPanel.add(portField);
        serverPanel.add(connectButton);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        messagePanel.add(messageField, BorderLayout.CENTER);
        messagePanel.add(sendButton, BorderLayout.EAST);

        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        setLayout(new BorderLayout());
        add(serverPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(messagePanel, BorderLayout.SOUTH);

        setVisible(true);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessageToServer();
            }
        });

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });

        loadChatHistoryFromFile();
    }

    private void connectToServer() {
        try {
            String ip = ipAddressField.getText();
            int port = Integer.parseInt(portField.getText());
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            chatArea.append("Подключен к серверу.\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Недопустимый номер порта.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Щшибка подключения к серверу: " + ex.getMessage());
        }
    }

    private void sendMessageToServer() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            out.println(message);
            logChatMessageToFile(message);
            messageField.setText("");
        }
    }

    private void logChatMessageToFile(String message) {
        try (FileWriter writer = new FileWriter("chat.txt", true)) {
            writer.write(message + "\n");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadChatHistoryFromFile() {
        try (FileReader reader = new FileReader("chat.txt")) {
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                chatArea.append(line + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}




package chatapplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatApplication {
    private DatagramSocket socket;
    private Map<InetAddress, ChatPanel> users = new HashMap<>();
    private int localPort = 12345;  // Local port to listen on
    private ExecutorService executor = Executors.newCachedThreadPool();

    public ChatApplication() {
        try {
            socket = new DatagramSocket(localPort);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Chat App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel chatPanel = new JPanel();
        chatPanel.setLayout(new GridLayout(0, 1));
        frame.add(new JScrollPane(chatPanel), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        JTextField inputField = new JTextField();
        JButton sendButton = new JButton("Send");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(inputField.getText());
                inputField.setText("");
            }
        });

        frame.setVisible(true);

        receiveMessages();
    }

    public void sendMessage(String message) {
        try {
            byte[] data = message.getBytes();
            for (InetAddress user : users.keySet()) {
                DatagramPacket packet = new DatagramPacket(data, data.length, user, localPort);
                socket.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void receiveMessages() {
        while (true) {
            try {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                InetAddress senderAddress = packet.getAddress();
                String message = new String(packet.getData(), 0, packet.getLength());

                if (!users.containsKey(senderAddress)) {
                    ChatPanel chatPanel = new ChatPanel(senderAddress);
                    users.put(senderAddress, chatPanel);
                }

                users.get(senderAddress).displayMessage("Received: " + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ChatPanel {
        private InetAddress userAddress;
        private JTextArea messageArea;

        public ChatPanel(InetAddress userAddress) {
            this.userAddress = userAddress;

            JFrame userFrame = new JFrame("Chat with " + userAddress.getHostAddress());
            userFrame.setSize(400, 300);

            messageArea = new JTextArea();
            messageArea.setEditable(false);
            JTextField inputField = new JTextField();
            JButton sendButton = new JButton("Send");

            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sendMessage(inputField.getText());
                    displayMessage("Sent: " + inputField.getText());
                    inputField.setText("");
                }
            });

            userFrame.setLayout(new BorderLayout());
            userFrame.add(new JScrollPane(messageArea), BorderLayout.CENTER);
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new BorderLayout());
            inputPanel.add(inputField, BorderLayout.CENTER);
            inputPanel.add(sendButton, BorderLayout.EAST);
            userFrame.add(inputPanel, BorderLayout.SOUTH);

            userFrame.setVisible(true);
        }

        public void displayMessage(String message) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    messageArea.append(message + "\n");
                }
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatApplication();
            }
        });
    }
}

package com.codealpha.task2;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AIChatbot extends JFrame {
  
	private static final long serialVersionUID = 1L;
	private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private Map<String, String> responses;

    public AIChatbot() {
        // Initialize AI Training Data (Rule-based NLP)
        trainBot();

        // UI Setup
        setTitle("CodeAlpha AI Chatbot");
        setSize(450, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- 1. CHAT AREA ---
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chatArea.setBackground(new Color(245, 245, 245));
        chatArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // --- 2. INPUT PANEL ---
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        inputPanel.setBackground(Color.WHITE);

        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        sendButton = new JButton("SEND");
        sendButton.setBackground(new Color(41, 128, 185));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // --- EVENTS ---
        sendButton.addActionListener(e -> handleChat());
        inputField.addActionListener(e -> handleChat());

        // Welcome message
        appendMessage("Bot: Hello! I am your AI assistant. How can I help you today?");
    }

    private void trainBot() {
        responses = new HashMap<>();
        // FAQ Training Data
        responses.put("hello", "Greetings! How can I assist you with your Java tasks?");
        responses.put("hi", "Hi there! Need help with your CodeAlpha project?");
        responses.put("who are you", "I am a Java-based AI chatbot built for CodeAlpha Task 3.");
        responses.put("java", "Java is a powerful, object-oriented programming language.");
        responses.put("help", "I can answer questions about Java, tell you who I am, or just chat!");
        responses.put("name", "My name is AlphaBot. What's yours?");
        responses.put("datatypes in java", "Integer, Float ,Boolean,String,Character,Double,etc are the datatypes in java");
        responses.put("JVM stands for", "Java Virtual Machine");
        responses.put("Features of java ", "java is open source , platform independant, robust.");
        
    }

    private void handleChat() {
        String userText = inputField.getText().toLowerCase().trim();
        if (userText.isEmpty()) return;

        appendMessage("You: " + inputField.getText());
        inputField.setText("");

        // Simulated Processing Delay
        Timer timer = new Timer(500, e -> {
            String botResponse = getResponse(userText);
            appendMessage("Bot: " + botResponse);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private String getResponse(String input) {
        // Simple NLP: Keyword matching
        for (String key : responses.keySet()) {
            if (input.contains(key)) {
                return responses.get(key);
            }
        }
        return "I'm sorry, I don't understand that yet. Can you try asking about 'Java' or 'Help'?";
    }

    private void appendMessage(String msg) {
        chatArea.append(msg + "\n\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AIChatbot().setVisible(true));
    }
}

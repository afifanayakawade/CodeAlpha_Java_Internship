package com.codealpha.task1;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentGradeTracker extends JFrame {
    
    private ArrayList<Double> gradesList = new ArrayList<>();
    private JTextField nameInput, gradeInput;
    private DefaultTableModel tableModel;
    private JLabel avgDisplay, highDisplay, lowDisplay;

    private final Color PRIMARY_COLOR = new Color(44, 62, 80);    
    private final Color ACCENT_COLOR = new Color(52, 152, 219);   
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241); 
    private final Color WHITE = Color.WHITE;

    public StudentGradeTracker() {
        setTitle("CodeAlpha | Student Grade Tracker");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
        setLayout(new BorderLayout(20, 20));

      
        JPanel header = new JPanel();
        header.setBackground(PRIMARY_COLOR);
        header.setPreferredSize(new Dimension(0, 70));
        JLabel title = new JLabel("STUDENT GRADE DASHBOARD");
        title.setForeground(WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBorder(new EmptyBorder(15, 0, 0, 0));
        header.add(title);
        add(header, BorderLayout.NORTH);

       
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(BACKGROUND_COLOR);
        leftPanel.setBorder(new EmptyBorder(0, 25, 0, 10));

        JPanel card = new JPanel(new GridLayout(6, 1, 10, 10));
        card.setBackground(WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));

        card.add(new JLabel("Student Full Name:"));
        nameInput = new JTextField();
        card.add(nameInput);

        card.add(new JLabel("Current Grade (0-100):"));
        gradeInput = new JTextField();
        card.add(gradeInput);

        JButton addButton = createStyledButton("ADD TO SYSTEM", ACCENT_COLOR);
        card.add(addButton);

        JButton clearButton = createStyledButton("RESET ALL", new Color(231, 76, 60));
        card.add(clearButton);

        leftPanel.add(card);
        leftPanel.add(Box.createVerticalGlue());
        add(leftPanel, BorderLayout.WEST);

        
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BACKGROUND_COLOR);
        tablePanel.setBorder(new EmptyBorder(0, 0, 0, 25));

        String[] columns = {"NAME", "SCORE"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        
    
        table.setRowHeight(30);
        table.setSelectionBackground(ACCENT_COLOR);
        table.getTableHeader().setBackground(PRIMARY_COLOR);
        table.getTableHeader().setForeground(WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(WHITE);
        tablePanel.add(scrollPane);
        add(tablePanel, BorderLayout.CENTER);

  
        JPanel footer = new JPanel(new GridLayout(1, 3, 20, 0));
        footer.setBackground(BACKGROUND_COLOR);
        footer.setBorder(new EmptyBorder(10, 25, 25, 25));

        avgDisplay = createStatCard("AVERAGE", "-");
        highDisplay = createStatCard("HIGHEST", "-");
        lowDisplay = createStatCard("LOWEST", "-");

        footer.add(avgDisplay);
        footer.add(highDisplay);
        footer.add(lowDisplay);
        add(footer, BorderLayout.SOUTH);

        // Logic
        addButton.addActionListener(e -> addStudent());
        clearButton.addActionListener(e -> reset());
    }

    private JLabel createStatCard(String title, String value) {
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + title + "<br><span style='font-size: 18px; color: #3498db;'>" + value + "</span></div></html>", SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(WHITE);
        label.setBorder(new LineBorder(new Color(210, 210, 210), 1));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return label;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void addStudent() {
        try {
            String name = nameInput.getText().trim();
            double grade = Double.parseDouble(gradeInput.getText());

            if (name.isEmpty() || grade < 0 || grade > 100) throw new Exception();

            gradesList.add(grade);
            tableModel.addRow(new Object[]{name.toUpperCase(), grade});
            updateStats();
            
            nameInput.setText("");
            gradeInput.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input! Enter name and grade (0-100).");
        }
    }

    private void updateStats() {
        double sum = 0, high = gradesList.get(0), low = gradesList.get(0);
        for (double g : gradesList) {
            sum += g;
            if (g > high) high = g;
            if (g < low) low = g;
        }
        avgDisplay.setText("<html><div style='text-align: center;'>AVERAGE<br><span style='font-size: 18px; color: #3498db;'>" + String.format("%.2f", sum/gradesList.size()) + "</span></div></html>");
        highDisplay.setText("<html><div style='text-align: center;'>HIGHEST<br><span style='font-size: 18px; color: #3498db;'>" + high + "</span></div></html>");
        lowDisplay.setText("<html><div style='text-align: center;'>LOWEST<br><span style='font-size: 18px; color: #3498db;'>" + low + "</span></div></html>");
    }

    private void reset() {
        gradesList.clear();
        tableModel.setRowCount(0);
        avgDisplay.setText("<html><div style='text-align: center;'>AVERAGE<br><span style='font-size: 18px; color: #3498db;'>-</span></div></html>");
            }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradeTracker().setVisible(true));
    }
}

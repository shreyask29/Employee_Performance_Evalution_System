import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Main extends JFrame {
    private final JTextField nameField = new JTextField(12);
    private final JTextField communicationField = new JTextField(4);
    private final JTextField teamworkField = new JTextField(4);
    private final JTextField productivityField = new JTextField(4);

    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"Employee Name", "Communication", "Teamwork",
                    "Productivity", "Average Score", "Rating"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public Main() {
        setTitle("Employee Performance Evaluation System");
        setSize(850, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel("Employee Performance Evaluation System",
                SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 20));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Enter Employee Details"));
        formPanel.add(new JLabel("Employee Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Communication Score (0-10):"));
        formPanel.add(communicationField);
        formPanel.add(new JLabel("Teamwork Score (0-10):"));
        formPanel.add(teamworkField);
        formPanel.add(new JLabel("Productivity Score (0-10):"));
        formPanel.add(productivityField);

        JButton addButton = new JButton("Add Employee");
        JButton clearButton = new JButton("Clear Fields");

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttons.add(addButton);
        buttons.add(clearButton);

        JPanel inputArea = new JPanel(new BorderLayout(8, 8));
        inputArea.setBorder(BorderFactory.createEmptyBorder(0, 12, 8, 12));
        inputArea.add(formPanel, BorderLayout.CENTER);
        inputArea.add(buttons, BorderLayout.SOUTH);

        JTable table = new JTable(model);
        table.setRowHeight(24);
        table.setFillsViewportHeight(true);

        JPanel centerPanel = new JPanel(new BorderLayout(8, 8));
        centerPanel.add(inputArea, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(table), BorderLayout.CENTER);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
        add(centerPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> addEmployee());
        clearButton.addActionListener(e -> clearFields());
    }

    private void addEmployee() {
        String employeeName = nameField.getText().trim();

        try {
            if (employeeName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the employee name.");
                nameField.requestFocus();
                return;
            }

            int communication = Integer.parseInt(communicationField.getText().trim());
            int teamwork = Integer.parseInt(teamworkField.getText().trim());
            int productivity = Integer.parseInt(productivityField.getText().trim());

            if (!isValidScore(communication) || !isValidScore(teamwork)
                    || !isValidScore(productivity)) {
                JOptionPane.showMessageDialog(this,
                        "Please enter whole-number scores from 0 to 10.");
                return;
            }

            double average = (communication + teamwork + productivity) / 3.0;
            String rating = getRating(average);

            model.addRow(new Object[]{
                    employeeName, communication, teamwork, productivity,
                    String.format(java.util.Locale.US, "%.2f", average), rating
            });

            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Scores must be whole numbers from 0 to 10.");
        }
    }

    private boolean isValidScore(int score) {
        return score >= 0 && score <= 10;
    }

    private String getRating(double average) {
        if (average >= 8) {
            return "Excellent";
        } else if (average >= 6) {
            return "Good";
        } else if (average >= 4) {
            return "Average";
        } else {
            return "Needs Improvement";
        }
    }

    private void clearFields() {
        nameField.setText("");
        communicationField.setText("");
        teamworkField.setText("");
        productivityField.setText("");
        nameField.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

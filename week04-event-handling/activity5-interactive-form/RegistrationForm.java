import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RegistrationForm extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    private JRadioButton otherRadio;
    private ButtonGroup genderGroup;
    private JCheckBox sportsCheck;
    private JCheckBox musicCheck;
    private JCheckBox readingCheck;
    private JComboBox<String> countryBox;
    private JTextArea commentsArea;
    private JLabel statusLabel;
    private JButton submitButton;
    private JButton clearButton;
    private JButton exitButton;

    public RegistrationForm() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Interactive Registration Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header Panel
        JLabel headerLabel = new JLabel("User Registration Form", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 22));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(headerLabel, BorderLayout.NORTH);

        // Form Fields Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 1. Full Name
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.7;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // 2. Email Address
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.3;
        JLabel emailLabel = new JLabel("Email Address:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 13));
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);

        // 3. Password
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.3;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 13));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 0.7;
        passwordField = new JPasswordField(20);
        formPanel.add(passwordField, gbc);

        // 4. Gender (Radio Buttons)
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.3;
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Arial", Font.BOLD, 13));
        formPanel.add(genderLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 3; gbc.weightx = 0.7;
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        otherRadio = new JRadioButton("Other");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderGroup.add(otherRadio);
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        genderPanel.add(otherRadio);
        formPanel.add(genderPanel, gbc);

        // 5. Interests (Checkboxes)
        gbc.gridx = 0; gbc.gridy = 4; gbc.weightx = 0.3;
        JLabel interestsLabel = new JLabel("Interests:");
        interestsLabel.setFont(new Font("Arial", Font.BOLD, 13));
        formPanel.add(interestsLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 4; gbc.weightx = 0.7;
        JPanel interestsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        sportsCheck = new JCheckBox("Sports");
        musicCheck = new JCheckBox("Music");
        readingCheck = new JCheckBox("Reading");
        interestsPanel.add(sportsCheck);
        interestsPanel.add(musicCheck);
        interestsPanel.add(readingCheck);
        formPanel.add(interestsPanel, gbc);

        // 6. Country (Dropdown)
        gbc.gridx = 0; gbc.gridy = 5; gbc.weightx = 0.3;
        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setFont(new Font("Arial", Font.BOLD, 13));
        formPanel.add(countryLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 5; gbc.weightx = 0.7;
        String[] countries = {"Select Country", "Kenya", "Uganda", "Tanzania", "Rwanda", "United States", "United Kingdom", "Canada"};
        countryBox = new JComboBox<>(countries);
        formPanel.add(countryBox, gbc);

        // 7. Comments (TextArea)
        gbc.gridx = 0; gbc.gridy = 6; gbc.weightx = 0.3;
        JLabel commentsLabel = new JLabel("Comments:");
        commentsLabel.setFont(new Font("Arial", Font.BOLD, 13));
        formPanel.add(commentsLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 6; gbc.weightx = 0.7;
        commentsArea = new JTextArea(4, 20);
        commentsArea.setLineWrap(true);
        commentsArea.setWrapStyleWord(true);
        JScrollPane commentsScroll = new JScrollPane(commentsArea);
        formPanel.add(commentsScroll, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Bottom Section: Status Label + Action Buttons
        JPanel southPanel = new JPanel(new BorderLayout(5, 5));
        southPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));

        statusLabel = new JLabel("Status: Ready", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 13));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        southPanel.add(statusLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setPreferredSize(new Dimension(100, 35));

        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setPreferredSize(new Dimension(100, 35));

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setPreferredSize(new Dimension(100, 35));

        buttonPanel.add(submitButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);

        // Attach Action Handlers
        submitButton.addActionListener(this::submitButtonActionPerformed);
        clearButton.addActionListener(this::clearButtonActionPerformed);
        exitButton.addActionListener(this::exitButtonActionPerformed);
    }

    // Submit Button Action Handler
    private void submitButtonActionPerformed(ActionEvent evt) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Validation
        if (name.isEmpty()) {
            statusLabel.setText("Error: Please enter your name");
            statusLabel.setForeground(Color.RED);
            return;
        }
        if (email.isEmpty() || !email.contains("@")) {
            statusLabel.setText("Error: Please enter a valid email");
            statusLabel.setForeground(Color.RED);
            return;
        }
        if (password.length() < 6) {
            statusLabel.setText("Error: Password must be at least 6 characters");
            statusLabel.setForeground(Color.RED);
            return;
        }

        // Gender Selection
        String gender = "Not specified";
        if (maleRadio.isSelected()) gender = "Male";
        else if (femaleRadio.isSelected()) gender = "Female";
        else if (otherRadio.isSelected()) gender = "Other";

        // Interests
        StringBuilder interests = new StringBuilder();
        if (sportsCheck.isSelected()) interests.append("Sports ");
        if (musicCheck.isSelected()) interests.append("Music ");
        if (readingCheck.isSelected()) interests.append("Reading ");

        // Country & Comments
        String country = (String) countryBox.getSelectedItem();
        String comments = commentsArea.getText().trim();

        String message = "Registration Successful!\n\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Gender: " + gender + "\n" +
                "Interests: " + (interests.length() > 0 ? interests.toString() : "None") + "\n" +
                "Country: " + country + "\n" +
                "Comments: " + (comments.isEmpty() ? "None" : comments);

        JOptionPane.showMessageDialog(
                this, message, "Registration Complete",
                JOptionPane.INFORMATION_MESSAGE
        );
        statusLabel.setText("Registration submitted successfully!");
        statusLabel.setForeground(new Color(0, 128, 0));
    }

    // Clear Button Action Handler
    private void clearButtonActionPerformed(ActionEvent evt) {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        genderGroup.clearSelection();
        sportsCheck.setSelected(false);
        musicCheck.setSelected(false);
        readingCheck.setSelected(false);
        countryBox.setSelectedIndex(0);
        commentsArea.setText("");
        statusLabel.setText("Status: Form cleared");
        statusLabel.setForeground(Color.BLACK);
    }

    // Exit Button Action Handler
    private void exitButtonActionPerformed(ActionEvent evt) {
        int choice = JOptionPane.showConfirmDialog(
                this, "Are you sure you want to exit?", "Confirm Exit",
                JOptionPane.YES_NO_OPTION
        );
        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegistrationForm().setVisible(true);
        });
    }
}

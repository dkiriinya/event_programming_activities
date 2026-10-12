// ============================================================
// COMPONENT SHOWCASE
// ============================================================
// Demonstrates all major Swing GUI components:
// JLabel, JTextField, JPasswordField, JCheckBox, JRadioButton,
// JComboBox, JTextArea (with JScrollPane), and JButton.
// ============================================================
import javax.swing.*;
import java.awt.*;

public class ComponentShowcase {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShow());
    }

    private static void createAndShow() {
        JFrame frame = new JFrame("Component Showcase");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        // Use a vertical grid layout: 8 rows, 1 column
        frame.setLayout(new GridLayout(8, 1, 10, 10));

        // 1. JLabel
        JLabel titleLabel = new JLabel("Component Showcase", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        frame.add(titleLabel);

        // 2. JLabel + JTextField
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(new JLabel("Username:"));
        row1.add(new JTextField(20));
        frame.add(row1);

        // 3. JLabel + JPasswordField
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(new JLabel("Password:"));
        row2.add(new JPasswordField(20));
        frame.add(row2);

        // 4. JCheckBox
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.add(new JCheckBox("Remember me"));
        row3.add(new JCheckBox("Subscribe to newsletter"));
        frame.add(row3);

        // 5. JRadioButtons in a group
        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row4.add(new JLabel("Gender:"));
        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");
        JRadioButton other = new JRadioButton("Other");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(male);
        genderGroup.add(female);
        genderGroup.add(other);
        row4.add(male);
        row4.add(female);
        row4.add(other);
        frame.add(row4);

        // 6. JComboBox
        JPanel row5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row5.add(new JLabel("Country:"));
        String[] countries = {"Kenya", "Tanzania", "Uganda", "Rwanda", "Ethiopia"};
        row5.add(new JComboBox<>(countries));
        frame.add(row5);

        // 7. JTextArea (with scroll pane)
        JPanel row6 = new JPanel(new BorderLayout());
        row6.add(new JLabel("Comments:"), BorderLayout.NORTH);
        row6.add(new JScrollPane(new JTextArea(3, 40)), BorderLayout.CENTER);
        frame.add(row6);

        // 8. Buttons
        JPanel row7 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton submit = new JButton("Submit");
        submit.setBackground(new Color(100, 200, 100)); // green
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Arial", Font.BOLD, 14));
        JButton cancel = new JButton("Cancel");
        row7.add(submit);
        row7.add(cancel);
        frame.add(row7);

        frame.setVisible(true);

        System.out.println("Components displayed on: "
                + Thread.currentThread().getName());
    }
}

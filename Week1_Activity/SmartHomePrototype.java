import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * Activity 5: Event-Driven System Prototype
 * Smart Home Controller demonstrating Swing event handling and EDT thread safety.
 */
public class SmartHomePrototype extends JFrame {
    private JLabel statusLabel;
    private JTextArea eventLog;
    private JToggleButton lightsToggle;
    private JToggleButton securityToggle;
    private JButton doorButton;

    public SmartHomePrototype() {
        setTitle("Smart Home Controller");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Status Panel (NORTH)
        statusLabel = new JLabel("System Ready", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(230, 240, 250));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        add(statusLabel, BorderLayout.NORTH);

        // Event Log (CENTER)
        eventLog = new JTextArea(12, 35);
        eventLog.setEditable(false);
        eventLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(eventLog), BorderLayout.CENTER);

        // Control Panel (SOUTH)
        JPanel controlPanel = new JPanel(new GridLayout(3, 1, 6, 6));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 1. Lights Toggle
        lightsToggle = new JToggleButton("Lights: OFF");
        lightsToggle.setFont(new Font("Arial", Font.PLAIN, 14));
        lightsToggle.addActionListener(e -> {
            if (lightsToggle.isSelected()) {
                lightsToggle.setText("Lights: ON");
                logEvent("Lights turned ON");
                statusLabel.setText("Living Room Lights: ON");
            } else {
                lightsToggle.setText("Lights: OFF");
                logEvent("Lights turned OFF");
                statusLabel.setText("Living Room Lights: OFF");
            }
        });

        // 2. Security Toggle
        securityToggle = new JToggleButton("Security: DISARMED");
        securityToggle.setFont(new Font("Arial", Font.PLAIN, 14));
        securityToggle.addActionListener(e -> {
            if (securityToggle.isSelected()) {
                securityToggle.setText("Security: ARMED");
                logEvent("Security system ARMED");
                statusLabel.setText("Security System: ARMED");
                statusLabel.setBackground(new Color(255, 230, 200));
            } else {
                securityToggle.setText("Security: DISARMED");
                logEvent("Security system DISARMED");
                statusLabel.setText("Security System: DISARMED");
                statusLabel.setBackground(new Color(230, 240, 250));
            }
        });

        // 3. Door Sensor Trigger Button
        doorButton = new JButton("Simulate Door Sensor Trigger");
        doorButton.setFont(new Font("Arial", Font.PLAIN, 14));
        doorButton.addActionListener(e -> {
            logEvent("Door sensor triggered");
            if (securityToggle.isSelected()) {
                logEvent("ALARM! Unauthorized intrusion detected!");
                statusLabel.setText("ALARM ACTIVE! Intruder Detected!");
                statusLabel.setBackground(new Color(255, 120, 120));
            } else {
                logEvent("Door opened safely (Security is disarmed)");
                statusLabel.setText("Door Opened (Safe Entry)");
            }
        });

        controlPanel.add(lightsToggle);
        controlPanel.add(securityToggle);
        controlPanel.add(doorButton);
        add(controlPanel, BorderLayout.SOUTH);

        setSize(480, 450);
        setLocationRelativeTo(null);
    }

    private void logEvent(String event) {
        String timestamp = new Date().toString();
        eventLog.append("[" + timestamp + "] " + event + "\n");
        eventLog.setCaretPosition(eventLog.getDocument().getLength());
    }

    public static void main(String[] args) {
        // Start Swing application on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new SmartHomePrototype().setVisible(true);
        });
    }
}
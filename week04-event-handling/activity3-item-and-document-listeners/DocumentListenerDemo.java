import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class DocumentListenerDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("DocumentListener Demo — Live Character Count");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout(10, 10));

            JLabel title = new JLabel("Write a short bio (max 200 chars)", SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 16));
            frame.add(title, BorderLayout.NORTH);

            JTextArea bioArea = new JTextArea(8, 40);
            bioArea.setLineWrap(true);
            bioArea.setWrapStyleWord(true);
            bioArea.setFont(new Font("Arial", Font.PLAIN, 14));
            JScrollPane scrollPane = new JScrollPane(bioArea);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            frame.add(scrollPane, BorderLayout.CENTER);

            JLabel counterLabel = new JLabel("Characters: 0 / 200", SwingConstants.CENTER);
            counterLabel.setFont(new Font("Arial", Font.BOLD, 14));
            counterLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
            frame.add(counterLabel, BorderLayout.SOUTH);

            DocumentListener listener = new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) { updateCount(); }

                @Override
                public void removeUpdate(DocumentEvent e) { updateCount(); }

                @Override
                public void changedUpdate(DocumentEvent e) { /* Plain text unchanged */ }

                private void updateCount() {
                    int length = bioArea.getText().length();
                    counterLabel.setText("Characters: " + length + " / 200");
                    if (length > 200) {
                        counterLabel.setForeground(Color.RED);
                    } else {
                        counterLabel.setForeground(Color.BLACK);
                    }
                }
            };

            bioArea.getDocument().addDocumentListener(listener);
            frame.setVisible(true);
        });
    }
}

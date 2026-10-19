import javax.swing.*;
import java.awt.*;

public class ContactListDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Contact List — Custom Renderer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);

            String[] contacts = {
                "Alice — Online",
                "Bob — Offline",
                "Charlie — Online",
                "Diana — Away",
                "Edward — Offline"
            };

            JList<String> contactList = new JList<>(contacts);
            contactList.setFont(new Font("Arial", Font.PLAIN, 14));

            // Custom Renderer
            contactList.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(
                        JList<?> list, Object value, int index,
                        boolean isSelected, boolean cellHasFocus) {
                    
                    JLabel label = (JLabel) super.getListCellRendererComponent(
                            list, value, index, isSelected, cellHasFocus);
                    
                    String text = value.toString();
                    if (text.contains("Online")) {
                        label.setIcon(new ColorDot(Color.GREEN));
                    } else if (text.contains("Away")) {
                        label.setIcon(new ColorDot(Color.ORANGE));
                    } else {
                        label.setIcon(new ColorDot(Color.GRAY));
                    }
                    
                    label.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
                    return label;
                }
            });

            JTextArea info = new JTextArea(3, 30);
            info.setEditable(false);
            contactList.addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting()) {
                    info.setText("Selected: " + contactList.getSelectedValue());
                }
            });

            frame.add(new JScrollPane(contactList), BorderLayout.CENTER);
            frame.add(new JScrollPane(info), BorderLayout.SOUTH);
            frame.setVisible(true);
        });
    }

    static class ColorDot implements Icon {
        private final Color color;
        ColorDot(Color color) {
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillOval(x, y, 12, 12);
        }

        @Override public int getIconWidth() { return 14; }
        @Override public int getIconHeight() { return 14; }
    }
}

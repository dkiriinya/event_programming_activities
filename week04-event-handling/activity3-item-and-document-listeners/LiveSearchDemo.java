import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class LiveSearchDemo {
    private static final String[] FRUITS = {
        "Apple", "Apricot", "Banana", "Blueberry", "Cherry",
        "Grape", "Grapefruit", "Lemon", "Mango", "Orange",
        "Peach", "Pear", "Pineapple", "Strawberry", "Watermelon"
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Live Search Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 500);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout(10, 10));

            JTextField searchField = new JTextField();
            searchField.setFont(new Font("Arial", Font.PLAIN, 16));
            searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.LIGHT_GRAY)
            ));
            frame.add(searchField, BorderLayout.NORTH);

            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (String fruit : FRUITS) {
                listModel.addElement(fruit);
            }

            JList<String> resultList = new JList<>(listModel);
            resultList.setFont(new Font("Arial", Font.PLAIN, 14));
            frame.add(new JScrollPane(resultList), BorderLayout.CENTER);

            searchField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) { filter(); }

                @Override
                public void removeUpdate(DocumentEvent e) { filter(); }

                @Override
                public void changedUpdate(DocumentEvent e) { }

                private void filter() {
                    String query = searchField.getText().toLowerCase();
                    listModel.clear();
                    for (String fruit : FRUITS) {
                        if (fruit.toLowerCase().contains(query)) {
                            listModel.addElement(fruit);
                        }
                    }
                }
            });

            frame.setVisible(true);
        });
    }
}

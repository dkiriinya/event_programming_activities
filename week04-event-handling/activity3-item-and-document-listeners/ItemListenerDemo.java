import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemListener;

public class ItemListenerDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("ItemListener Demo — Pizza Order");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 500);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout(10, 10));

            JLabel title = new JLabel("  Build Your Pizza", SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 20));
            frame.add(title, BorderLayout.NORTH);

            JPanel content = new JPanel();
            content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
            content.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

            // Radio buttons for size selection
            JLabel sizeLabel = new JLabel("Size:");
            sizeLabel.setFont(new Font("Arial", Font.BOLD, 14));
            content.add(sizeLabel);

            JRadioButton small = new JRadioButton("Small ($5)");
            JRadioButton medium = new JRadioButton("Medium ($8)");
            JRadioButton large = new JRadioButton("Large ($11)");
            small.setSelected(true);

            ButtonGroup sizeGroup = new ButtonGroup();
            sizeGroup.add(small);
            sizeGroup.add(medium);
            sizeGroup.add(large);

            content.add(small);
            content.add(medium);
            content.add(large);
            content.add(Box.createVerticalStrut(15));

            // Checkboxes for toppings
            JLabel toppingsLabel = new JLabel("Toppings:");
            toppingsLabel.setFont(new Font("Arial", Font.BOLD, 14));
            content.add(toppingsLabel);

            JCheckBox cheese = new JCheckBox("Extra Cheese ($1.50)");
            JCheckBox pepperoni = new JCheckBox("Pepperoni ($2.00)");
            JCheckBox mushrooms = new JCheckBox("Mushrooms ($1.00)");
            JCheckBox olives = new JCheckBox("Olives ($0.75)");

            content.add(cheese);
            content.add(pepperoni);
            content.add(mushrooms);
            content.add(olives);

            frame.add(content, BorderLayout.CENTER);

            JLabel totalLabel = new JLabel("Total: $5.00", SwingConstants.CENTER);
            totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
            totalLabel.setForeground(new Color(0, 100, 0));
            totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
            frame.add(totalLabel, BorderLayout.SOUTH);

            // ItemListener updating total price dynamically
            ItemListener priceUpdater = e -> {
                double total = 0.0;
                if (small.isSelected())  total += 5.0;
                if (medium.isSelected()) total += 8.0;
                if (large.isSelected())  total += 11.0;

                if (cheese.isSelected())    total += 1.50;
                if (pepperoni.isSelected()) total += 2.00;
                if (mushrooms.isSelected()) total += 1.00;
                if (olives.isSelected())    total += 0.75;

                totalLabel.setText(String.format("Total: $%.2f", total));
            };

            small.addItemListener(priceUpdater);
            medium.addItemListener(priceUpdater);
            large.addItemListener(priceUpdater);
            cheese.addItemListener(priceUpdater);
            pepperoni.addItemListener(priceUpdater);
            mushrooms.addItemListener(priceUpdater);
            olives.addItemListener(priceUpdater);

            frame.setVisible(true);
        });
    }
}

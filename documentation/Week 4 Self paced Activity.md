# Mastering Interactive Swing Applications — Week 4 Complete Reference Guide

This document combines and converts the complete content from **Week 4 Learning Outcomes.pdf** and **Week 4 Self-Paced Activity.pdf** into a structured Markdown reference.

---

## Section 1: Week 4 Learning Outcomes & Summary

### Overview
During Week 4, the curriculum transitions from building static GUIs to creating interactive, event-driven Swing applications. Applications learn to respond dynamically to user actions, including button clicks, typing, checkbox toggles, and keyboard shortcuts.

---

### Learning Outcome 1: Implement `ActionListener` for Buttons and Menu Items Using Multiple Techniques

#### Summary of Listener Techniques
| Technique | Description | Best Used For |
| :--- | :--- | :--- |
| **Named Class** | Implements `ActionListener` as a separate class; reusable across multiple components | Shared logic, maintaining listener state |
| **Anonymous Inner Class** | Defines the listener inline (`new ActionListener() { ... }`) | Pre-Java 8 legacy codebase maintenance |
| **Lambda Expression** | Concise, modern syntax (`e -> { ... }`) introduced in Java 8+ | Default choice for modern Java code |
| **Method Reference** | Direct reference (`ClassName::method`) when handler method already exists | Clean delegation to existing methods |
| **Event Source Detection** | Distinguishes component using `e.getSource()` or `e.getActionCommand()` | Single shared listener across multiple controls |

#### Real-World Application & Career Impact
* **ATMs & Banking Apps:** Keypads, action buttons ("Transfer", "Pay Bills", "Logout") use `ActionListener`.
* **E-Commerce & POS Terminals:** "Place Order", "Scan", and "Total" triggers validate inputs and execute transactions.
* **IDEs & Software:** Menu bars ("File → Save", "Run → Debug") rely on action listeners.

---

### Learning Outcome 2: Handle State Changes in Checkboxes, Radio Buttons, and Text Fields

#### Listener Mapping by Component State
| Component | Primary Listener | Event Method / Behavior |
| :--- | :--- | :--- |
| `JCheckBox` | `ItemListener` | `itemStateChanged()` — Fires on check state toggle (checked ↔ unchecked) |
| `JRadioButton` | `ItemListener` | `itemStateChanged()` — Fires when selected (and other grouped buttons deselect) |
| `JComboBox` | `ActionListener` / `ItemListener` | Fires when selected drop-down option changes |
| `JTextField` | `DocumentListener` | `insertUpdate()`, `removeUpdate()`, `changedUpdate()` — Fires on every keystroke |
| `JTextArea` | `DocumentListener` | Monitors insertion, removal, or formatting changes in real-time |
| `ButtonGroup` | N/A | Logical grouping ensuring mutually exclusive radio selection |

#### Real-World Application
* **Amazon Checkout:** Selecting payment type reveals corresponding fields; quantity changes update totals in real time.
* **Google Search & Autocomplete:** Keystroke tracking via `DocumentListener` triggers live suggestions.
* **Character Counters:** Live character count validation (e.g., Twitter/X 280-character limit).

---

### Learning Outcome 3: Differentiate Between and Use Appropriate Listener Interfaces

#### Complete Component-to-Listener Mapping
| Component | Primary Listener | Secondary / Alternative Listener |
| :--- | :--- | :--- |
| `JButton` | `ActionListener` | `MouseListener` (hover/click effects) |
| `JCheckBox` | `ItemListener` | `ActionListener` |
| `JRadioButton` | `ItemListener` | `ActionListener` |
| `JComboBox` | `ActionListener` | `ItemListener` |
| `JTextField` | `DocumentListener` | `ActionListener` (triggers on Enter key) |
| `JTextArea` | `DocumentListener` | — |
| `JSlider` | `ChangeListener` | — |
| `JList` | `ListSelectionListener` | — |
| `JTable` | `ListSelectionListener` | `TableModelListener` |
| `JFrame` / `JDialog` | `WindowListener` | — |
| Canvas / Drawing Area | `MouseListener` / `MouseMotionListener` | `KeyListener` |

---

### Learning Outcome 4: Build Interactive Swing Applications with Proper Event Handling

#### Summary of Built Applications
1. **First Button App:** Basic `ActionListener` displaying message popups.
2. **Listener Techniques Demo:** Demonstrating named classes, anonymous classes, lambdas, and method references.
3. **Event Source Demo:** Identifying fired controls via `e.getSource()`.
4. **Pizza Order App:** Dynamic total calculation via `ItemListener`.
5. **Character Counter & Live Search:** Keystroke monitoring and real-time filtering via `DocumentListener`.
6. **Counter Application:** Full state management, bounds checking, and root pane keyboard shortcuts (↑, ↓, R).
7. **Interactive Registration Form:** Multi-input form with input validation, error messaging, and clearing logic.
8. **Drawing App & Keyboard Mover:** Low-level event processing with `MouseAdapter` and `KeyAdapter`.

---

### Self-Assessment Checklist
- [x] Implement `ActionListener` in all four styles (named, anonymous, lambda, method reference).
- [x] Identify specific event sources using `e.getSource()` and `e.getActionCommand()`.
- [x] Use `ItemListener` for checkboxes and radio buttons.
- [x] Use `DocumentListener` for real-time text tracking.
- [x] Match any major Swing component to its appropriate listener interface.
- [x] Build multi-field form applications with validation logic.
- [x] Configure keyboard hotkeys using `KeyStroke` and `AbstractAction`.
- [x] Utilize `MouseListener`, `MouseMotionListener`, and `KeyListener` for interactive UI components.
- [x] Version control and push project code to GitHub repository.

---

## Section 2: Self-Paced Activity Handbook & Code Reference

### Environment Setup & Directory Structure

Working directory layout:
```
week04-event-handling/
├── activity1-actionlistener-basics/
├── activity2-listener-techniques/
├── activity3-item-and-document-listeners/
├── activity4-counter-app/
├── activity5-interactive-form/
├── activity6-complete-app/
└── activity7-mouse-and-key/
```

Tools utilized: **VS Code**, **NetBeans** (optional drag-and-drop form designer), **Git / GitHub**.

---

### Activity 1: `ActionListener` Basics — First Interactive Button

#### Event Handling Anatomy
1. **Event Source:** Component generating the event (e.g., `JButton`).
2. **Event:** The `ActionEvent` object containing event metadata.
3. **Event Listener:** Object implementing `ActionListener` interface waiting for events.
4. **Event Handler:** The `actionPerformed(ActionEvent e)` method body containing execution logic.

#### Implementation (`FirstButton.java`)
```java
import javax.swing.*;
import java.awt.*;

public class FirstButton {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FirstButton::createAndShow);
    }

    private static void createAndShow() {
        JFrame frame = new JFrame("First Interactive Button");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50));

        JButton clickButton = new JButton("Click Me!");

        // Add ActionListener using a lambda expression
        clickButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                frame,
                "You clicked the button!",
                "Button Clicked",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        frame.add(clickButton);
        frame.setVisible(true);
    }
}
```

---

### Activity 2: Listener Implementation Styles & Event Sources

#### Listener Styles (`ListenerDemo.java`)
```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerDemo {
    // Style 1: Named Class
    static class NamedClassListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Style 1: Named class listener fired!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ListenerDemo::createAndShow);
    }

    private static void createAndShow() {
        JFrame frame = new JFrame("Three Listener Techniques");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        // Technique 1: Named Class
        JButton btn1 = new JButton("Style 1: Named Class");
        btn1.addActionListener(new NamedClassListener());
        frame.add(btn1);

        // Technique 2: Anonymous Inner Class
        JButton btn2 = new JButton("Style 2: Anonymous Inner Class");
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Style 2: Anonymous inner class fired!");
            }
        });
        frame.add(btn2);

        // Technique 3: Lambda Expression
        JButton btn3 = new JButton("Style 3: Lambda");
        btn3.addActionListener(e -> {
            System.out.println("Style 3: Lambda expression fired!");
        });
        frame.add(btn3);

        // Bonus Technique: Method Reference
        JButton btn4 = new JButton("Style 4: Method Reference");
        btn4.addActionListener(ListenerDemo::handleButtonClick);
        frame.add(btn4);

        frame.setVisible(true);
    }

    private static void handleButtonClick(ActionEvent e) {
        System.out.println("Style 4: Method reference fired!");
    }
}
```

#### Event Source Identification (`EventSourceDemo.java`)
```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class EventSourceDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Finding the Event Source");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 300);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new GridLayout(4, 1, 10, 10));

            JLabel resultLabel = new JLabel("Click a button...", SwingConstants.CENTER);
            resultLabel.setFont(new Font("Arial", Font.BOLD, 16));

            JButton addButton = new JButton("Add");
            JButton subtractButton = new JButton("Subtract");
            JButton resetButton = new JButton("Reset");

            // Shared ActionListener for all buttons
            ActionListener sharedListener = e -> {
                Object source = e.getSource();
                String command = e.getActionCommand();

                if (source == addButton) {
                    resultLabel.setText("You clicked: ADD");
                } else if (source == subtractButton) {
                    resultLabel.setText("You clicked: SUBTRACT");
                } else if (source == resetButton) {
                    resultLabel.setText("You clicked: RESET");
                }
                System.out.println("Command: " + command);
            };

            addButton.addActionListener(sharedListener);
            subtractButton.addActionListener(sharedListener);
            resetButton.addActionListener(sharedListener);

            frame.add(addButton);
            frame.add(subtractButton);
            frame.add(resetButton);
            frame.add(resultLabel);
            frame.setVisible(true);
        });
    }
}
```

---

### Activity 3: `ItemListener` and `DocumentListener`

#### Pizza Order App (`ItemListenerDemo.java`)
```java
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
```

#### Live Character Count (`DocumentListenerDemo.java`)
```java
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
```

#### Live Search Demo (`LiveSearchDemo.java`)
```java
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
            searchField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
```

---

### Activity 4: Counter Application with Keyboard Shortcuts

#### Counter Application (`CounterApp.java`)
```java
import javax.swing.*;
import java.awt.*;

public class CounterApp {
    private int count = 0;
    private final int MIN_VALUE = 0;
    private final int MAX_VALUE = 20;

    private JLabel countLabel;
    private JLabel statusLabel;
    private JButton incrementButton;
    private JButton decrementButton;
    private JButton resetButton;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CounterApp().createAndShow());
    }

    private void createAndShow() {
        JFrame frame = new JFrame("Counter Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        countLabel = new JLabel("0", SwingConstants.CENTER);
        countLabel.setFont(new Font("Arial", Font.BOLD, 72));
        frame.add(countLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        decrementButton = new JButton("−");
        decrementButton.setFont(new Font("Arial", Font.BOLD, 24));
        decrementButton.setPreferredSize(new Dimension(80, 60));

        incrementButton = new JButton("+");
        incrementButton.setFont(new Font("Arial", Font.BOLD, 24));
        incrementButton.setPreferredSize(new Dimension(80, 60));

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));

        buttonPanel.add(decrementButton);
        buttonPanel.add(incrementButton);
        buttonPanel.add(resetButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        statusLabel = new JLabel("Ready", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(statusLabel, BorderLayout.NORTH);

        // Event Handlers
        incrementButton.addActionListener(e -> {
            if (count < MAX_VALUE) {
                count++;
                updateDisplay();
                statusLabel.setText("Incremented to " + count);
            } else {
                statusLabel.setText("Maximum reached!");
            }
        });

        decrementButton.addActionListener(e -> {
            if (count > MIN_VALUE) {
                count--;
                updateDisplay();
                statusLabel.setText("Decremented to " + count);
            } else {
                statusLabel.setText("Minimum reached!");
            }
        });

        resetButton.addActionListener(e -> {
            count = 0;
            updateDisplay();
            statusLabel.setText("Counter reset");
        });

        // Keyboard Shortcut Bindings
        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("UP"), "increment");
        frame.getRootPane().getActionMap().put("increment", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                incrementButton.doClick();
            }
        });

        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("DOWN"), "decrement");
        frame.getRootPane().getActionMap().put("decrement", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                decrementButton.doClick();
            }
        });

        frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke("R"), "reset");
        frame.getRootPane().getActionMap().put("reset", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                resetButton.doClick();
            }
        });

        frame.setVisible(true);
    }

    private void updateDisplay() {
        countLabel.setText(String.valueOf(count));
        if (count >= MAX_VALUE) {
            countLabel.setForeground(Color.RED);
        } else if (count <= MIN_VALUE) {
            countLabel.setForeground(new Color(200, 100, 0));
        } else {
            countLabel.setForeground(Color.BLACK);
        }
    }
}
```

---

### Activity 5: Interactive Registration Form Handlers

#### Event Handler Methods (`RegistrationForm.java`)
```java
// Submit Button Action Handler
private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {
    String name = nameField.getText().trim();
    String email = emailField.getText().trim();
    String password = new String(passwordField.getPassword());

    // Validation
    if (name.isEmpty()) {
        statusLabel.setText("Error: Please enter your name");
        statusLabel.setForeground(java.awt.Color.RED);
        return;
    }
    if (email.isEmpty() || !email.contains("@")) {
        statusLabel.setText("Error: Please enter a valid email");
        statusLabel.setForeground(java.awt.Color.RED);
        return;
    }
    if (password.length() < 6) {
        statusLabel.setText("Error: Password must be at least 6 characters");
        statusLabel.setForeground(java.awt.Color.RED);
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

    javax.swing.JOptionPane.showMessageDialog(
            this, message, "Registration Complete",
            javax.swing.JOptionPane.INFORMATION_MESSAGE
    );
    statusLabel.setText("Registration submitted successfully!");
    statusLabel.setForeground(new java.awt.Color(0, 128, 0));
}

// Clear Button Action Handler
private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {
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
    statusLabel.setForeground(java.awt.Color.BLACK);
}

// Exit Button Action Handler
private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
    int choice = javax.swing.JOptionPane.showConfirmDialog(
            this, "Are you sure you want to exit?", "Confirm Exit",
            javax.swing.JOptionPane.YES_NO_OPTION
    );
    if (choice == javax.swing.JOptionPane.YES_OPTION) {
        System.exit(0);
    }
}
```

---

### Activity 6: Mouse and Key Event Handling

#### Freehand Drawing (`DrawingApp.java`)
```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DrawingApp extends JFrame {
    private List<Point> points = new ArrayList<>();

    public DrawingApp() {
        setTitle("Simple Drawing App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                for (Point p : points) {
                    g.fillOval(p.x - 3, p.y - 3, 6, 6);
                }
            }
        };
        canvas.setBackground(Color.WHITE);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                points.clear();
                points.add(e.getPoint());
                canvas.repaint();
            }
        });

        canvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                points.add(e.getPoint());
                canvas.repaint();
            }
        });

        add(canvas);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DrawingApp::new);
    }
}
```

#### Keyboard Movement (`KeyboardMover.java`)
```java
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardMover extends JFrame {
    private int x = 250;
    private int y = 200;
    private JPanel gamePanel;

    public KeyboardMover() {
        setTitle("Keyboard Mover — Arrow Keys to Move");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.fillOval(x, y, 40, 40);
                g.setColor(Color.BLACK);
                g.drawString("Use arrow keys to move the ball", 20, 20);
            }
        };

        gamePanel.setBackground(Color.WHITE);
        gamePanel.setFocusable(true);
        add(gamePanel);

        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                int step = 10;
                if (key == KeyEvent.VK_UP)    y -= step;
                if (key == KeyEvent.VK_DOWN)  y += step;
                if (key == KeyEvent.VK_LEFT)  x -= step;
                if (key == KeyEvent.VK_RIGHT) x += step;

                x = Math.max(0, Math.min(x, gamePanel.getWidth() - 40));
                y = Math.max(0, Math.min(y, gamePanel.getHeight() - 40));
                gamePanel.repaint();
            }
        });

        SwingUtilities.invokeLater(gamePanel::requestFocusInWindow);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(KeyboardMover::new);
    }
}
```

---

### Activity 7: Git Commands & GitHub Submission Workflow

```powershell
# Navigate to repository root
cd "C:\Users\RUICT\Desktop\projects\event-driven-programming-java"

# Inspect repository status
git status

# Stage all files
git add .

# Commit changes
git commit -m "Complete Week 4: Event Handling in Swing
- Activity 1: First interactive button (ActionListener basics)
- Activity 2: Three listener techniques + getSource()
- Activity 3: ItemListener (pizza order) + DocumentListener (live counter)
- Activity 4: Counter app with keyboard shortcuts
- Activity 5: Interactive Registration Form (NetBeans drag-and-drop)
- Activity 6: Mouse drawing app + keyboard mover"

# Push to main branch
git push origin main
```

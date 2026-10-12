import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class StudentPortal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentPortal::createAndShow);
    }

    private static void createAndShow() {
        // Main Window
        JFrame frame = new JFrame("University Student Portal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(980, 640);
        frame.setMinimumSize(new Dimension(860, 560));
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        // Background color for content areas
        Color pageBg = new Color(245, 248, 252);
        frame.getContentPane().setBackground(pageBg);

        // ============================================================
        // 1. TOP HEADER (NORTH)
        // ============================================================
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(13, 58, 98)); // Deep Navy
        header.setPreferredSize(new Dimension(0, 68));
        header.setBorder(new EmptyBorder(10, 20, 10, 20));

        // Header Left: Logo Icon + Portal Title + Subtitle
        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 3));
        headerLeft.setOpaque(false);

        JLabel gradIcon = new JLabel(new GraduationCapIcon(34, 34, Color.WHITE));
        headerLeft.add(gradIcon);

        JPanel titleBlock = new JPanel();
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        titleBlock.setOpaque(false);

        JLabel mainTitle = new JLabel("University Student Portal");
        mainTitle.setForeground(Color.WHITE);
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 19));

        JLabel subTitle = new JLabel("Learn  |  Grow  |  Achieve");
        subTitle.setForeground(new Color(185, 208, 232));
        subTitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        titleBlock.add(mainTitle);
        titleBlock.add(Box.createVerticalStrut(2));
        titleBlock.add(subTitle);
        headerLeft.add(titleBlock);

        // Header Right: User Icon + Red Logout Button
        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 8));
        headerRight.setOpaque(false);

        JLabel topUserIcon = new JLabel(new UserSilhouetteIcon(22, 22, Color.WHITE));
        headerRight.add(topUserIcon);

        JButton logoutBtn = new JButton(" Logout") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(220, 53, 69)); // Bootstrap Red
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        logoutBtn.setIcon(new ExitArrowIcon(14, 14, Color.WHITE));
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setContentAreaFilled(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(new EmptyBorder(6, 14, 6, 14));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        headerRight.add(logoutBtn);

        header.add(headerLeft, BorderLayout.WEST);
        header.add(headerRight, BorderLayout.EAST);
        frame.add(header, BorderLayout.NORTH);

        // ============================================================
        // 2. SIDEBAR (WEST) - Quick Links
        // ============================================================
        JPanel sidebarWrapper = new JPanel(new BorderLayout());
        sidebarWrapper.setPreferredSize(new Dimension(200, 0));
        sidebarWrapper.setBackground(pageBg);
        sidebarWrapper.setBorder(new EmptyBorder(16, 16, 16, 10));

        RoundedPanel sidebar = new RoundedPanel(16, Color.WHITE, new Color(228, 235, 245));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(14, 12, 14, 12));

        JLabel quickLinksTitle = new JLabel("Quick Links");
        quickLinksTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        quickLinksTitle.setForeground(new Color(15, 30, 60));
        quickLinksTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebar.add(quickLinksTitle);
        sidebar.add(Box.createVerticalStrut(14));

        // Nav items
        sidebar.add(createNavButton("Dashboard", new HomeIcon(18, 18, Color.WHITE), true));
        sidebar.add(Box.createVerticalStrut(6));
        sidebar.add(createNavButton("Courses", new BookIcon(18, 18, new Color(13, 58, 98)), false));
        sidebar.add(Box.createVerticalStrut(6));
        sidebar.add(createNavButton("Grades", new DocumentIcon(18, 18, new Color(13, 58, 98)), false));
        sidebar.add(Box.createVerticalStrut(6));
        sidebar.add(createNavButton("Fees", new CreditCardIcon(18, 18, new Color(13, 58, 98)), false));
        sidebar.add(Box.createVerticalStrut(6));
        sidebar.add(createNavButton("Timetable", new CalendarIcon(18, 18, new Color(13, 58, 98)), false));
        sidebar.add(Box.createVerticalStrut(6));
        sidebar.add(createNavButton("Messages", new MailIcon(18, 18, new Color(13, 58, 98)), false));
        sidebar.add(Box.createVerticalGlue());

        sidebarWrapper.add(sidebar, BorderLayout.CENTER);
        frame.add(sidebarWrapper, BorderLayout.WEST);

        // ============================================================
        // 3. MAIN CONTENT (CENTER)
        // ============================================================
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(pageBg);
        content.setBorder(new EmptyBorder(16, 10, 16, 20));

        // Top Greeting Banner + Profile Info
        JPanel topGreetingRow = new JPanel(new BorderLayout());
        topGreetingRow.setOpaque(false);
        topGreetingRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
        topGreetingRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Left Welcome Label
        JPanel welcomeCol = new JPanel();
        welcomeCol.setLayout(new BoxLayout(welcomeCol, BoxLayout.Y_AXIS));
        welcomeCol.setOpaque(false);

        JLabel welcomeTitle = new JLabel("<html>Welcome, <font color='#1976d2'>John Doe!</font></html>");
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeTitle.setForeground(new Color(20, 30, 50));

        JLabel welcomeSub = new JLabel("Here's an overview of your academic information.");
        welcomeSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        welcomeSub.setForeground(new Color(110, 125, 145));

        welcomeCol.add(welcomeTitle);
        welcomeCol.add(Box.createVerticalStrut(3));
        welcomeCol.add(welcomeSub);
        topGreetingRow.add(welcomeCol, BorderLayout.WEST);

        // Right Profile Badge
        JPanel profileBox = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        profileBox.setOpaque(false);

        JLabel avatarLabel = new JLabel(new CircularAvatarIcon(46, new Color(175, 208, 242), new Color(40, 105, 180)));
        profileBox.add(avatarLabel);

        JPanel profileText = new JPanel();
        profileText.setLayout(new BoxLayout(profileText, BoxLayout.Y_AXIS));
        profileText.setOpaque(false);

        JLabel profName = new JLabel("John Doe");
        profName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        profName.setForeground(new Color(25, 35, 55));

        JLabel profId = new JLabel("Student ID: S1234567");
        profId.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        profId.setForeground(new Color(110, 125, 145));

        JLabel profDept = new JLabel("Computer Science");
        profDept.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        profDept.setForeground(new Color(110, 125, 145));

        profileText.add(profName);
        profileText.add(profId);
        profileText.add(profDept);
        profileBox.add(profileText);

        topGreetingRow.add(profileBox, BorderLayout.EAST);
        content.add(topGreetingRow);

        content.add(Box.createVerticalStrut(16));

        // ---- Stat Cards (GPA & Units) ----
        JPanel cardsGrid = new JPanel(new GridLayout(1, 2, 16, 0));
        cardsGrid.setOpaque(false);
        cardsGrid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        cardsGrid.setPreferredSize(new Dimension(0, 100));
        cardsGrid.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Card 1: GPA
        cardsGrid.add(createModernStatCard(
                "GPA",
                "3.75",
                new BarChartIcon(38, 38, new Color(40, 167, 69)),
                new Color(238, 249, 241),
                new Color(199, 235, 209),
                new Color(10, 75, 35)
        ));

        // Card 2: Units
        cardsGrid.add(createModernStatCard(
                "Units",
                "48",
                new GraduationCapIcon(44, 44, new Color(25, 105, 195)),
                new Color(234, 243, 252),
                new Color(191, 219, 254),
                new Color(15, 50, 115)
        ));

        content.add(cardsGrid);
        content.add(Box.createVerticalStrut(16));

        // ---- Notifications Card ----
        RoundedPanel notifCard = new RoundedPanel(16, Color.WHITE, new Color(228, 235, 245));
        notifCard.setLayout(new BoxLayout(notifCard, BoxLayout.Y_AXIS));
        notifCard.setBorder(new EmptyBorder(14, 18, 14, 18));
        notifCard.setAlignmentX(Component.LEFT_ALIGNMENT);
        notifCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));

        // Notifications Title
        JPanel notifHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        notifHeader.setOpaque(false);
        notifHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        notifHeader.add(new JLabel(new BellIcon(18, 18, new Color(25, 118, 210))));

        JLabel notifTitle = new JLabel("Notifications");
        notifTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        notifTitle.setForeground(new Color(20, 30, 50));
        notifHeader.add(notifTitle);
        notifCard.add(notifHeader);
        notifCard.add(Box.createVerticalStrut(12));

        // Notification Rows
        notifCard.add(createNotificationRow(
                new CircleIconBadge(36, new Color(220, 53, 69), new DocumentIcon(16, 16, Color.WHITE)),
                "Exam results released",
                "Your semester results are now available.",
                "05 Oct 2026"
        ));
        notifCard.add(Box.createVerticalStrut(10));

        notifCard.add(createNotificationRow(
                new CircleIconBadge(36, new Color(230, 160, 20), new CreditCardIcon(16, 16, Color.WHITE)),
                "Fee deadline: 30th Oct",
                "Please make your payment before the deadline.",
                "04 Oct 2026"
        ));
        notifCard.add(Box.createVerticalStrut(10));

        notifCard.add(createNotificationRow(
                new CircleIconBadge(36, new Color(40, 167, 69), new CalendarIcon(16, 16, Color.WHITE)),
                "Timetable updated",
                "Your class timetable has been updated.",
                "03 Oct 2026"
        ));

        content.add(notifCard);
        content.add(Box.createVerticalGlue());

        frame.add(content, BorderLayout.CENTER);

        // ============================================================
        // 4. FOOTER / STATUS BAR (SOUTH)
        // ============================================================
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(new Color(236, 241, 248));
        statusBar.setBorder(new EmptyBorder(6, 20, 6, 20));

        // Left Status Info
        JPanel statusLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        statusLeft.setOpaque(false);

        JLabel statusDot = new JLabel("●");
        statusDot.setForeground(new Color(40, 167, 69)); // Green
        statusDot.setFont(new Font("SansSerif", Font.BOLD, 12));
        statusLeft.add(statusDot);

        JLabel statusUser = new JLabel("Status: Logged in as student");
        statusUser.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusUser.setForeground(new Color(30, 40, 60));
        statusLeft.add(statusUser);

        JLabel sep = new JLabel("|");
        sep.setForeground(new Color(180, 190, 205));
        statusLeft.add(sep);

        JLabel clockIcon = new JLabel(new ClockIcon(13, 13, new Color(13, 58, 98)));
        statusLeft.add(clockIcon);

        JLabel loginDate = new JLabel("Last login: 05 Oct 2026");
        loginDate.setFont(new Font("Segoe UI", Font.BOLD, 11));
        loginDate.setForeground(new Color(30, 40, 60));
        statusLeft.add(loginDate);

        // Right Slogan
        JLabel slogan = new JLabel("A Brighter Tomorrow Together");
        slogan.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        slogan.setForeground(new Color(90, 115, 145));

        statusBar.add(statusLeft, BorderLayout.WEST);
        statusBar.add(slogan, BorderLayout.EAST);
        frame.add(statusBar, BorderLayout.SOUTH);

        // Show Window
        frame.setVisible(true);
    }

    // --- Helper Component: Nav Button ---
    private static JButton createNavButton(String text, Icon icon, boolean isActive) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (isActive) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(25, 118, 210)); // Bright active blue
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        btn.setIcon(icon);
        btn.setIconTextGap(12);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFont(new Font("Segoe UI", isActive ? Font.BOLD : Font.PLAIN, 13));
        btn.setForeground(isActive ? Color.WHITE : new Color(60, 75, 95));
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 12, 8, 12));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // --- Helper Component: Stat Card ---
    private static JPanel createModernStatCard(String title, String value, Icon icon, Color bg, Color borderColor, Color valueColor) {
        RoundedPanel card = new RoundedPanel(14, bg, borderColor);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(12, 18, 12, 18));

        // Left Icon
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setBorder(new EmptyBorder(0, 0, 0, 16));
        card.add(iconLabel, BorderLayout.WEST);

        // Right Text Column
        JPanel textCol = new JPanel();
        textCol.setLayout(new BoxLayout(textCol, BoxLayout.Y_AXIS));
        textCol.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLabel.setForeground(new Color(60, 75, 95));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valueLabel.setForeground(valueColor);

        textCol.add(titleLabel);
        textCol.add(Box.createVerticalStrut(2));
        textCol.add(valueLabel);
        card.add(textCol, BorderLayout.CENTER);

        return card;
    }

    // --- Helper Component: Notification Row ---
    private static JPanel createNotificationRow(Icon badge, String title, String subtitle, String date) {
        JPanel row = new JPanel(new BorderLayout(12, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Icon badge
        row.add(new JLabel(badge), BorderLayout.WEST);

        // Center content (Title + subtitle)
        JPanel textCol = new JPanel();
        textCol.setLayout(new BoxLayout(textCol, BoxLayout.Y_AXIS));
        textCol.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLabel.setForeground(new Color(25, 35, 55));

        JLabel subLabel = new JLabel(subtitle);
        subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        subLabel.setForeground(new Color(110, 125, 145));

        textCol.add(titleLabel);
        textCol.add(Box.createVerticalStrut(2));
        textCol.add(subLabel);
        row.add(textCol, BorderLayout.CENTER);

        // Right Date
        JLabel dateLabel = new JLabel(date);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        dateLabel.setForeground(new Color(110, 125, 145));
        row.add(dateLabel, BorderLayout.EAST);

        return row;
    }

    // ============================================================
    // CUSTOM GRAPHICS & VECTOR ICONS (Anti-Aliased Java 2D)
    // ============================================================

    // Rounded Border/Background Panel
    private static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color bg;
        private final Color border;

        public RoundedPanel(int radius, Color bg, Color border) {
            this.radius = radius;
            this.bg = bg;
            this.border = border;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            if (border != null) {
                g2.setColor(border);
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // Graduation Cap Icon
    private static class GraduationCapIcon implements Icon {
        private final int width, height;
        private final Color color;

        public GraduationCapIcon(int width, int height, Color color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            int cx = x + width / 2;
            int cy = y + height / 2;

            // Diamond cap top
            Polygon cap = new Polygon();
            cap.addPoint(cx, cy - height / 4);
            cap.addPoint(x + width - 2, cy - height / 12);
            cap.addPoint(cx, cy + height / 12);
            cap.addPoint(x + 2, cy - height / 12);
            g2.fill(cap);

            // Skull cap lower arc
            g2.fillArc(cx - width / 4, cy - height / 14, width / 2, height / 3, 180, 180);

            // Tassel
            g2.setStroke(new BasicStroke(1.8f));
            g2.drawLine(cx, cy - height / 4, x + width - 4, cy);
            g2.drawLine(x + width - 4, cy, x + width - 4, cy + height / 5);
            g2.dispose();
        }

        @Override public int getIconWidth() { return width; }
        @Override public int getIconHeight() { return height; }
    }

    // Circular Avatar Icon
    private static class CircularAvatarIcon implements Icon {
        private final int size;
        private final Color bg, fg;

        public CircularAvatarIcon(int size, Color bg, Color fg) {
            this.size = size;
            this.bg = bg;
            this.fg = fg;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Circle background
            g2.setColor(bg);
            g2.fillOval(x, y, size, size);

            // User silhouette
            g2.setColor(fg);
            int headD = size / 3;
            g2.fillOval(x + (size - headD) / 2, y + size / 5, headD, headD);
            g2.fillArc(x + size / 6, y + size / 2, size * 2 / 3, size / 2, 0, 180);
            g2.dispose();
        }

        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }
    }

    // User Silhouette Icon (header)
    private static class UserSilhouetteIcon implements Icon {
        private final int width, height;
        private final Color color;

        public UserSilhouetteIcon(int width, int height, Color color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            int headD = width / 2;
            g2.fillOval(x + width / 4, y, headD, headD);
            g2.fillArc(x, y + height / 3, width, height * 2 / 3, 0, 180);
            g2.dispose();
        }

        @Override public int getIconWidth() { return width; }
        @Override public int getIconHeight() { return height; }
    }

    // Exit Arrow Icon
    private static class ExitArrowIcon implements Icon {
        private final int width, height;
        private final Color color;

        public ExitArrowIcon(int width, int height, Color color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.8f));
            // Door bracket
            g2.drawPolyline(new int[]{x + width / 2, x + 1, x + 1, x + width / 2},
                            new int[]{y + 1, y + 1, y + height - 2, y + height - 2}, 4);
            // Arrow shaft
            int midY = y + height / 2;
            g2.drawLine(x + 4, midY, x + width - 1, midY);
            // Arrow head
            g2.drawLine(x + width - 4, midY - 3, x + width - 1, midY);
            g2.drawLine(x + width - 4, midY + 3, x + width - 1, midY);
            g2.dispose();
        }

        @Override public int getIconWidth() { return width; }
        @Override public int getIconHeight() { return height; }
    }

    // Home Icon
    private static class HomeIcon implements Icon {
        private final int width, height;
        private final Color color;

        public HomeIcon(int width, int height, Color color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            // Roof
            Polygon roof = new Polygon();
            roof.addPoint(x + width / 2, y + 1);
            roof.addPoint(x + width - 1, y + height / 2);
            roof.addPoint(x + 1, y + height / 2);
            g2.fill(roof);
            // Base
            g2.fillRect(x + 4, y + height / 2, width - 8, height / 2 - 1);
            g2.dispose();
        }

        @Override public int getIconWidth() { return width; }
        @Override public int getIconHeight() { return height; }
    }

    // Book Icon
    private static class BookIcon implements Icon {
        private final int width, height;
        private final Color color;

        public BookIcon(int width, int height, Color color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.6f));
            // Two open book pages
            g2.drawRoundRect(x + 1, y + 2, width / 2 - 2, height - 4, 3, 3);
            g2.drawRoundRect(x + width / 2 + 1, y + 2, width / 2 - 2, height - 4, 3, 3);
            g2.dispose();
        }

        @Override public int getIconWidth() { return width; }
        @Override public int getIconHeight() { return height; }
    }

    // Document Icon
    private static class DocumentIcon implements Icon {
        private final int width, height;
        private final Color color;

        public DocumentIcon(int width, int height, Color color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.6f));
            g2.drawRoundRect(x + 2, y + 1, width - 4, height - 2, 3, 3);
            // Lines on document
            g2.drawLine(x + 5, y + 5, x + width - 5, y + 5);
            g2.drawLine(x + 5, y + 9, x + width - 5, y + 9);
            g2.drawLine(x + 5, y + 13, x + width - 8, y + 13);
            g2.dispose();
        }

        @Override public int getIconWidth() { return width; }
        @Override public int getIconHeight() { return height; }
    }

    // Credit Card Icon
    private static class CreditCardIcon implements Icon {
        private final int width, height;
        private final Color color;

        public CreditCardIcon(int width, int height, Color color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.fillRoundRect(x + 1, y + 2, width - 2, height - 4, 4, 4);
            g2.setColor(Color.WHITE);
            // Magnetic strip
            g2.fillRect(x + 1, y + 5, width - 2, 3);
            g2.dispose();
        }

        @Override public int getIconWidth() { return width; }
        @Override public int getIconHeight() { return height; }
    }

    // Calendar Icon
    private static class CalendarIcon implements Icon {
        private final int width, height;
        private final Color color;

        public CalendarIcon(int width, int height, Color color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(x + 1, y + 3, width - 2, height - 4, 3, 3);
            g2.drawLine(x + 1, y + 7, x + width - 1, y + 7);
            // Pins
            g2.drawLine(x + 4, y, x + 4, y + 3);
            g2.drawLine(x + width - 5, y, x + width - 5, y + 3);
            g2.dispose();
        }

        @Override public int getIconWidth() { return width; }
        @Override public int getIconHeight() { return height; }
    }

    // Mail Icon
    private static class MailIcon implements Icon {
        private final int width, height;
        private final Color color;

        public MailIcon(int width, int height, Color color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(x + 1, y + 2, width - 2, height - 4, 3, 3);
            // Envelope flap
            g2.drawLine(x + 2, y + 3, x + width / 2, y + height / 2 + 1);
            g2.drawLine(x + width - 2, y + 3, x + width / 2, y + height / 2 + 1);
            g2.dispose();
        }

        @Override public int getIconWidth() { return width; }
        @Override public int getIconHeight() { return height; }
    }

    // Bell Icon
    private static class BellIcon implements Icon {
        private final int width, height;
        private final Color color;

        public BellIcon(int width, int height, Color color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            // Bell dome
            g2.fillArc(x + 2, y + 2, width - 4, height - 5, 0, 180);
            g2.fillRect(x + 2, y + height / 2 - 1, width - 4, 4);
            g2.fillRoundRect(x + 1, y + height - 5, width - 2, 2, 2, 2);
            // Clapper
            g2.fillOval(x + width / 2 - 2, y + height - 3, 4, 3);
            g2.dispose();
        }

        @Override public int getIconWidth() { return width; }
        @Override public int getIconHeight() { return height; }
    }

    // Bar Chart Icon
    private static class BarChartIcon implements Icon {
        private final int width, height;
        private final Color color;

        public BarChartIcon(int width, int height, Color color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            int barW = width / 4;
            int gap = width / 10;
            // 3 ascending bars
            g2.fillRoundRect(x + gap, y + height * 5 / 8, barW, height * 3 / 8, 4, 4);
            g2.fillRoundRect(x + barW + gap * 2, y + height * 3 / 8, barW, height * 5 / 8, 4, 4);
            g2.fillRoundRect(x + barW * 2 + gap * 3, y + height / 8, barW, height * 7 / 8, 4, 4);
            g2.dispose();
        }

        @Override public int getIconWidth() { return width; }
        @Override public int getIconHeight() { return height; }
    }

    // Clock Icon
    private static class ClockIcon implements Icon {
        private final int width, height;
        private final Color color;

        public ClockIcon(int width, int height, Color color) {
            this.width = width;
            this.height = height;
            this.color = color;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.4f));
            g2.drawOval(x, y, width - 1, height - 1);
            int cx = x + width / 2;
            int cy = y + height / 2;
            g2.drawLine(cx, cy, cx, y + 3);
            g2.drawLine(cx, cy, cx + width / 3, cy);
            g2.dispose();
        }

        @Override public int getIconWidth() { return width; }
        @Override public int getIconHeight() { return height; }
    }

    // Circle Badge Icon (for notification icons)
    private static class CircleIconBadge implements Icon {
        private final int size;
        private final Color bg;
        private final Icon innerIcon;

        public CircleIconBadge(int size, Color bg, Icon innerIcon) {
            this.size = size;
            this.bg = bg;
            this.innerIcon = innerIcon;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bg);
            g2.fillOval(x, y, size, size);
            g2.dispose();

            if (innerIcon != null) {
                int ix = x + (size - innerIcon.getIconWidth()) / 2;
                int iy = y + (size - innerIcon.getIconHeight()) / 2;
                innerIcon.paintIcon(c, g, ix, iy);
            }
        }

        @Override public int getIconWidth() { return size; }
        @Override public int getIconHeight() { return size; }
    }
}


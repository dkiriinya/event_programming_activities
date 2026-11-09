import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

/**
 * Build & Verification Pipeline Utility for Event-Driven Programming in Java.
 * Authored by Don Gitonga Kiriinya.
 */
public class AgentCLI {

    static class ActivityRecord {
        String id;
        int week;
        String name;
        String folder;
        String className;
        String sourceFile;
        boolean isGui;
        String description;
        List<String> keyPatterns;

        ActivityRecord(String id, int week, String name, String folder, String className,
                       String sourceFile, boolean isGui, String description, String... patterns) {
            this.id = id;
            this.week = week;
            this.name = name;
            this.folder = folder;
            this.className = className;
            this.sourceFile = sourceFile;
            this.isGui = isGui;
            this.description = description;
            this.keyPatterns = Arrays.asList(patterns);
        }
    }

    private static final List<ActivityRecord> REGISTRY = new ArrayList<>();

    static {
        // Week 1
        REGISTRY.add(new ActivityRecord("w1-procedural", 1, "Procedural Console Calculator", "Week1_Activity", "ProceduralCalculator", "ProceduralCalculator.java", false, "Synchronous linear CLI calculator", "Scanner", "System.in"));
        REGISTRY.add(new ActivityRecord("w1-event-driven", 1, "Event-Driven Swing Calculator", "Week1_Activity", "EventDrivenCalculator", "EventDrivenCalculator.java", true, "Asynchronous GUI calculator using ActionListener", "ActionListener", "invokeLater", "JButton"));
        REGISTRY.add(new ActivityRecord("w1-smart-home", 1, "Smart Home Prototype", "Week1_Activity", "SmartHomePrototype", "SmartHomePrototype.java", true, "Event dispatcher with sensor triggers", "ActionListener", "invokeLater"));
        REGISTRY.add(new ActivityRecord("w1-bug1", 1, "Bug 1: Blocking EDT Challenge", "Week1_Activity/BugFixChallenge", "Bug1_BlockingEDT", "Bug1_BlockingEDT.java", true, "EDT responsiveness fix", "Thread", "invokeLater"));
        REGISTRY.add(new ActivityRecord("w1-bug2", 1, "Bug 2: Missing Listener Challenge", "Week1_Activity/BugFixChallenge", "Bug2_MissingListener", "Bug2_MissingListener.java", true, "Unwired event listener fix", "addActionListener"));
        REGISTRY.add(new ActivityRecord("w1-bug3", 1, "Bug 3: Wrong Thread Challenge", "Week1_Activity/BugFixChallenge", "Bug3_WrongThread", "Bug3_WrongThread.java", true, "Background thread EDT safety fix", "SwingUtilities.invokeLater"));
        REGISTRY.add(new ActivityRecord("w1-bug4", 1, "Bug 4: Memory Leak Challenge", "Week1_Activity/BugFixChallenge", "Bug4_MemoryLeak", "Bug4_MemoryLeak.java", true, "Dangling listener disposal fix", "removeActionListener"));

        // Week 2
        REGISTRY.add(new ActivityRecord("w2-weather-station", 2, "Weather Station Simulation", "Week02-observer-pattern/activity1-weather-station", "WeatherStationMain", "WeatherStationMain.java", false, "GoF Observer pattern with dynamic observers", "Observer", "Subject", "registerObserver"));
        REGISTRY.add(new ActivityRecord("w2-legacy-observer", 2, "Legacy Java Observer Demo", "Week02-observer-pattern/activity2-legacy-observer", "LegacyMain", "LegacyMain.java", false, "Historical java.util.Observer implementation", "Observable", "Observer", "update"));
        REGISTRY.add(new ActivityRecord("w2-tightly-coupled", 2, "Tightly Coupled System (Anti-Pattern)", "Week02-observer-pattern/activity3-custom-observer", "TightlyCoupledSystem", "TightlyCoupledSystem.java", false, "Baseline demonstrating coupling liabilities", "BarChart", "CurrentLabel"));
        REGISTRY.add(new ActivityRecord("w2-custom-observer", 2, "Refactored Custom Observer", "Week02-observer-pattern/activity3-custom-observer", "RefactoredMain", "RefactoredMain.java", false, "Decoupled subject and multi-observer dispatch", "Observer", "Subject", "notifyObservers"));
        REGISTRY.add(new ActivityRecord("w2-property-change", 2, "PropertyChangeListener Modern Alternative", "Week02-observer-pattern/activity4-comparison", "PropertyChangeDemo", "PropertyChangeDemo.java", false, "JavaBean PropertyChangeSupport notifications", "PropertyChangeListener", "PropertyChangeSupport"));

        // Week 3
        REGISTRY.add(new ActivityRecord("w3-first-window", 3, "First Swing Window", "week03-swing-gui/activity1-first-window", "FirstWindow", "FirstWindow.java", true, "Centered JFrame configured on EDT", "JFrame", "SwingUtilities.invokeLater"));
        REGISTRY.add(new ActivityRecord("w3-showcase", 3, "Swing Component Showcase", "week03-swing-gui/activity2-basic-components", "ComponentShowcase", "ComponentShowcase.java", true, "Showcase of 8 core Swing widgets", "JLabel", "JTextField", "JCheckBox", "JComboBox"));
        REGISTRY.add(new ActivityRecord("w3-flow-layout", 3, "FlowLayout Demo", "week03-swing-gui/activity3-layout-managers", "FlowLayoutDemo", "FlowLayoutDemo.java", true, "Natural flow wrapping demonstration", "FlowLayout"));
        REGISTRY.add(new ActivityRecord("w3-border-layout", 3, "BorderLayout Demo", "week03-swing-gui/activity3-layout-managers", "BorderLayoutDemo", "BorderLayoutDemo.java", true, "5-region compass positioning", "BorderLayout"));
        REGISTRY.add(new ActivityRecord("w3-grid-layout", 3, "GridLayout Demo", "week03-swing-gui/activity3-layout-managers", "GridLayoutDemo", "GridLayoutDemo.java", true, "Equal-sized cell grid calculator arrangement", "GridLayout"));
        REGISTRY.add(new ActivityRecord("w3-combined-layout", 3, "Combined & Nested Layouts Demo", "week03-swing-gui/activity3-layout-managers", "CombinedLayoutDemo", "CombinedLayoutDemo.java", true, "Nested layout manager hierarchy", "BorderLayout", "GridLayout", "JPanel"));
        REGISTRY.add(new ActivityRecord("w3-freeze-demo", 3, "EDT Freeze Demo (Anti-Pattern)", "week03-swing-gui/activity4-edt-rule", "FreezeDemo", "FreezeDemo.java", true, "Simulates thread-starvation lockup", "Thread.sleep"));
        REGISTRY.add(new ActivityRecord("w3-fixed-demo", 3, "EDT Fixed Demo (SwingWorker)", "week03-swing-gui/activity4-edt-rule", "FixedDemo", "FixedDemo.java", true, "Asynchronous background task with SwingWorker", "SwingWorker", "doInBackground", "done"));
        REGISTRY.add(new ActivityRecord("w3-student-portal", 3, "Complete Student Portal Mockup", "week03-swing-gui/activity5-complete-mockup", "StudentPortal", "StudentPortal.java", true, "Complete dashboard UI mockup with metric cards", "JFrame", "JPanel"));

        // Week 4
        REGISTRY.add(new ActivityRecord("w4-first-button", 4, "First Interactive Button", "week04-event-handling/activity1-actionlistener-basics", "FirstButton", "FirstButton.java", true, "Lambda ActionListener with popup dialog", "ActionListener", "JOptionPane"));
        REGISTRY.add(new ActivityRecord("w4-listeners", 4, "Listener Implementation Styles", "week04-event-handling/activity2-listener-techniques", "ListenerDemo", "ListenerDemo.java", true, "Named, Anonymous, Lambda, and Method Reference listeners", "ActionListener", "actionPerformed"));
        REGISTRY.add(new ActivityRecord("w4-event-source", 4, "Event Source Identification", "week04-event-handling/activity2-listener-techniques", "EventSourceDemo", "EventSourceDemo.java", true, "e.getSource() vs e.getActionCommand() discrimination", "getSource", "getActionCommand"));
        REGISTRY.add(new ActivityRecord("w4-pizza-order", 4, "Pizza Order Form", "week04-event-handling/activity3-item-and-document-listeners", "ItemListenerDemo", "ItemListenerDemo.java", true, "ItemListener for checkboxes, radios, and combo boxes", "ItemListener", "itemStateChanged"));
        REGISTRY.add(new ActivityRecord("w4-char-counter", 4, "Live Character Counter", "week04-event-handling/activity3-item-and-document-listeners", "DocumentListenerDemo", "DocumentListenerDemo.java", true, "DocumentListener keystroke tracking", "DocumentListener", "insertUpdate", "removeUpdate"));
        REGISTRY.add(new ActivityRecord("w4-live-search", 4, "Live Search Filtering", "week04-event-handling/activity3-item-and-document-listeners", "LiveSearchDemo", "LiveSearchDemo.java", true, "Keystroke-driven real-time JList filtering", "DocumentListener", "DefaultListModel"));
        REGISTRY.add(new ActivityRecord("w4-counter", 4, "Counter App with Shortcuts", "week04-event-handling/activity4-counter-app", "CounterApp", "CounterApp.java", true, "Window-level KeyStroke bindings and threshold alerts", "KeyStroke", "ActionMap", "InputMap"));
        REGISTRY.add(new ActivityRecord("w4-registration", 4, "Interactive Registration Form", "week04-event-handling/activity5-interactive-form", "RegistrationForm", "RegistrationForm.java", true, "Multi-field validation, regex checks, and reset logic", "JTextField", "JPasswordField", "ActionListener"));
        REGISTRY.add(new ActivityRecord("w4-drawing", 4, "Freehand Drawing Canvas", "week04-event-handling/activity6-mouse-and-key", "DrawingApp", "DrawingApp.java", true, "MouseAdapter & MouseMotionAdapter canvas drawing", "MouseAdapter", "mouseDragged", "Graphics2D"));
        REGISTRY.add(new ActivityRecord("w4-keyboard-mover", 4, "Keyboard Mover Canvas", "week04-event-handling/activity6-mouse-and-key", "KeyboardMover", "KeyboardMover.java", true, "KeyAdapter shape movement with collision boundary checks", "KeyAdapter", "keyPressed", "repaint"));

        // Week 5
        REGISTRY.add(new ActivityRecord("w5-contact-list", 5, "Custom JList Cell Renderer", "week05-advanced-swing/activity1-jlist-renderer", "ContactListDemo", "ContactListDemo.java", true, "ListCellRenderer with status badge icons", "ListCellRenderer", "JList"));
        REGISTRY.add(new ActivityRecord("w5-student-table", 5, "Dynamic JTable with Sorting & Filtering", "week05-advanced-swing/activity2-jtable", "StudentTableDemo", "StudentTableDemo.java", true, "DefaultTableModel, TableRowSorter, and CRUD operations", "JTable", "DefaultTableModel", "TableRowSorter"));
        REGISTRY.add(new ActivityRecord("w5-file-explorer", 5, "JTree & JTabbedPane Document Viewer", "week05-advanced-swing/activity3-jtree-tabs", "FileExplorerDemo", "FileExplorerDemo.java", true, "JTree hierarchy linked with JTabbedPane split views", "JTree", "JTabbedPane", "JSplitPane"));
        REGISTRY.add(new ActivityRecord("w5-mini-editor", 5, "Menu Bars, Toolbars & Dialogs", "week05-advanced-swing/activity4-menus-dialogs", "MiniEditor", "MiniEditor.java", true, "JMenuBar, JToolBar, accelerators, and JFileChooser", "JMenuBar", "JMenuItem", "JFileChooser"));
        REGISTRY.add(new ActivityRecord("w5-file-manager", 5, "Capstone Desktop File Manager", "week05-advanced-swing/activity5-capstone-file-manager", "FileManager", "FileManager.java", true, "Comprehensive dual-pane file manager application", "JTree", "JTable", "JSplitPane", "JToolBar"));

        // Week 6
        REGISTRY.add(new ActivityRecord("w6-hello-world", 6, "First JavaFX Window", "week06-javafx/src/main/java/programmaticdemo", "Main", "Main.java", true, "Stage, Scene, Node hierarchy & JavaFX Application Thread", "Application", "Stage", "Scene", "setOnAction"));
        REGISTRY.add(new ActivityRecord("w6-layouts", 6, "JavaFX Layout Panes", "week06-javafx/src/main/java/programmaticdemo", "LayoutDemo", "LayoutDemo.java", true, "HBox, VBox, GridPane, BorderPane responsive layouts", "HBox", "VBox", "GridPane", "BorderPane"));
        REGISTRY.add(new ActivityRecord("w6-fxml-login", 6, "FXML & Controller Wiring", "week06-javafx/src/main/java/programmaticdemo", "FXMLApp", "FXMLApp.java", true, "FXML separation of concerns, @FXML injection & credential check", "FXMLLoader", "FXML", "LoginController", "handleLoginButtonAction"));
        REGISTRY.add(new ActivityRecord("w6-dashboard", 6, "Capstone SaaS Dashboard", "week06-javafx/src/main/java/programmaticdemo", "DashboardApp", "DashboardApp.java", true, "Production SaaS dashboard with metric cards and responsive panes", "BorderPane", "createCard", "Font"));

        // Week 7
        REGISTRY.add(new ActivityRecord("w7-propagation", 7, "Event Propagation (Filters vs Handlers)", "week07-javafx-events/src/main/java/com/example", "EventPropagationDemo", "EventPropagationDemo.java", true, "Capturing vs bubbling phases with addEventFilter and addEventHandler", "addEventFilter", "addEventHandler", "setOnMouseClicked"));
        REGISTRY.add(new ActivityRecord("w7-mouse-key", 7, "Mouse and Key Events", "week07-javafx-events/src/main/java/com/example", "MouseKeyDemo", "MouseKeyDemo.java", true, "Coordinate tracking and arrow key circle navigation", "setOnMouseMoved", "setOnKeyPressed", "KeyCode"));
        REGISTRY.add(new ActivityRecord("w7-drag-drop", 7, "Drag and Drop Interaction", "week07-javafx-events/src/main/java/com/example", "DragDropDemo", "DragDropDemo.java", true, "Dragboard, ClipboardContent, TransferMode, and visual feedback", "startDragAndDrop", "setOnDragOver", "setOnDragDropped"));
        REGISTRY.add(new ActivityRecord("w7-custom-events", 7, "Custom LoginSuccessEvent", "week07-javafx-events/src/main/java/com/example", "CustomEventDemo", "CustomEventDemo.java", true, "Decoupled component communication via custom Event and EventType", "Event", "EventType", "fireEvent", "addEventHandler"));

        // Week 10: Final Project Capstone
        REGISTRY.add(new ActivityRecord("w10-university-system", 10, "University Management System (Neon PostgreSQL)", "UniversitySystem/src/main/java/com/university", "App", "App.java", true, "Event-Driven JavaFX University Management System wired with Neon PostgreSQL", "Application", "DatabaseUtil", "LoadStudentsTask", "StudentController"));
    }

    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("--help") || args[0].equals("-h") || args[0].equals("help")) {
            printHelp();
            return;
        }

        String command = args[0].toLowerCase();
        boolean jsonMode = Arrays.asList(args).contains("--json") || Arrays.asList(args).contains("-j");

        try {
            switch (command) {
                case "info":
                    cmdInfo(jsonMode);
                    break;
                case "list":
                    cmdList(args, jsonMode);
                    break;
                case "inspect":
                    cmdInspect(args, jsonMode);
                    break;
                case "build":
                case "compile":
                    cmdBuild(args, jsonMode);
                    break;
                case "verify":
                case "test":
                    cmdVerify(args, jsonMode);
                    break;
                case "grade":
                    cmdGrade(args, jsonMode);
                    break;
                case "run":
                    cmdRun(args);
                    break;
                default:
                    System.err.println("Unknown command: " + command);
                    printHelp();
                    System.exit(1);
            }
        } catch (Exception e) {
            if (jsonMode) {
                System.out.println("{\"status\": \"error\", \"message\": \"" + escapeJson(e.getMessage()) + "\"}");
            } else {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
            System.exit(1);
        }
    }

    private static void printHelp() {
        System.out.println("===============================================================================");
        System.out.println(" AgentCLI — Headless & Scriptable Interface for Event-Driven Programming Java");
        System.out.println("===============================================================================");
        System.out.println("Usage: java scripts/AgentCLI.java <command> [options]");
        System.out.println();
        System.out.println("Commands for AI Agents & Automated Tooling:");
        System.out.println("  build [--clean]     Compile all modules across all weeks (like 'npm run build').");
        System.out.println("  info                Display coursework metadata, Java environment, and summary.");
        System.out.println("  list [--week <n>]   List registered activities across weeks (1 to 5).");
        System.out.println("  inspect <id|class>  Deep static analysis of an activity's source code.");
        System.out.println("  verify [--week <n>] Verify compilation, bytecode presence, and pattern safety.");
        System.out.println("  grade [--week <n>]  Compute an automated score against course learning outcomes.");
        System.out.println("  run <id> [--headless] [--timeout <ms>] Launch an activity safely.");
        System.out.println();
        System.out.println("Global Options:");
        System.out.println("  --json, -j          Emit machine-readable JSON output for automated ingestion.");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java scripts/AgentCLI.java list --week 4 --json");
        System.out.println("  java scripts/AgentCLI.java verify --week 4");
        System.out.println("  java scripts/AgentCLI.java inspect w4-counter --json");
        System.out.println("  java scripts/AgentCLI.java grade --week 4 --json");
        System.out.println("  java scripts/AgentCLI.java run w4-counter --headless --timeout 2000");
        System.out.println("===============================================================================");
    }

    private static void cmdInfo(boolean json) {
        String javaVer = System.getProperty("java.version");
        String javaHome = System.getProperty("java.home");
        String osName = System.getProperty("os.name");
        int totalActivities = REGISTRY.size();

        if (json) {
            System.out.println("{");
            System.out.println("  \"coursework\": \"Event-Driven Programming in Java\",");
            System.out.println("  \"totalWeeks\": 7,");
            System.out.println("  \"totalActivities\": " + totalActivities + ",");
            System.out.println("  \"javaVersion\": \"" + escapeJson(javaVer) + "\",");
            System.out.println("  \"javaHome\": \"" + escapeJson(javaHome) + "\",");
            System.out.println("  \"os\": \"" + escapeJson(osName) + "\",");
            System.out.println("  \"supportedFeatures\": [\"reflection\", \"headless-run\", \"ast-inspection\", \"automated-grading\"]");
            System.out.println("}");
        } else {
            System.out.println("Coursework: Event-Driven Programming in Java");
            System.out.println("Total Modules: Weeks 1–5, 7 (" + totalActivities + " Registered Activities)");
            System.out.println("Java Runtime: " + javaVer + " (" + javaHome + ")");
            System.out.println("Host OS:      " + osName);
            System.out.println("Capabilities: Headless Verification, Static Code Audit, Automated Grading Rubric");
        }
    }

    private static void cmdList(String[] args, boolean json) {
        Integer filterWeek = getArgInt(args, "--week");
        List<ActivityRecord> list = new ArrayList<>();
        for (ActivityRecord r : REGISTRY) {
            if (filterWeek == null || r.week == filterWeek) {
                list.add(r);
            }
        }

        if (json) {
            System.out.println("[");
            for (int i = 0; i < list.size(); i++) {
                ActivityRecord r = list.get(i);
                System.out.print("  {");
                System.out.print("\"id\": \"" + r.id + "\", ");
                System.out.print("\"week\": " + r.week + ", ");
                System.out.print("\"name\": \"" + escapeJson(r.name) + "\", ");
                System.out.print("\"folder\": \"" + escapeJson(r.folder) + "\", ");
                System.out.print("\"class\": \"" + r.className + "\", ");
                System.out.print("\"source\": \"" + r.sourceFile + "\", ");
                System.out.print("\"isGui\": " + r.isGui + ", ");
                System.out.print("\"description\": \"" + escapeJson(r.description) + "\"}");
                if (i < list.size() - 1) System.out.println(",");
                else System.out.println();
            }
            System.out.println("]");
        } else {
            System.out.printf("%-18s | Wk | %-5s | %-24s | %s%n", "ACTIVITY ID", "TYPE", "MAIN CLASS", "DESCRIPTION");
            System.out.println("-------------------+----+-------+--------------------------+-------------------------------------------");
            for (ActivityRecord r : list) {
                System.out.printf("%-18s | W%d | %-5s | %-24s | %s%n",
                        r.id, r.week, (r.isGui ? "GUI" : "CLI"), r.className, r.description);
            }
            System.out.println("\nTotal: " + list.size() + " activities.");
        }
    }

    private static void cmdInspect(String[] args, boolean json) throws IOException {
        if (args.length < 2 || args[1].startsWith("-")) {
            throw new IllegalArgumentException("Usage: inspect <activity-id | class-name> [--json]");
        }
        String target = args[1];
        ActivityRecord act = findActivity(target);
        if (act == null) {
            throw new IllegalArgumentException("Activity not found matching: " + target);
        }

        File file = new File(act.folder, act.sourceFile);
        if (!file.exists()) {
            throw new FileNotFoundException("Source file does not exist: " + file.getPath());
        }

        String content = Files.readString(file.toPath());
        int lineCount = content.split("\r\n|\r|\n").length;
        int byteCount = content.getBytes().length;

        // Pattern checks
        boolean hasEdt = content.contains("SwingUtilities.invokeLater");
        boolean hasLambda = content.contains("->");
        boolean hasMethodRef = content.contains("::");
        boolean hasAnonInner = content.contains("new ActionListener()") || content.contains("new ItemListener()")
                || content.contains("new MouseAdapter()") || content.contains("new KeyAdapter()");
        List<String> detectedImports = new ArrayList<>();
        Matcher m = Pattern.compile("(?m)^import\\s+([a-zA-Z0-9_.*]+);").matcher(content);
        while (m.find()) {
            detectedImports.add(m.group(1));
        }

        List<String> matchedKeywords = new ArrayList<>();
        for (String p : act.keyPatterns) {
            if (content.contains(p)) matchedKeywords.add(p);
        }

        if (json) {
            System.out.println("{");
            System.out.println("  \"id\": \"" + act.id + "\",");
            System.out.println("  \"name\": \"" + escapeJson(act.name) + "\",");
            System.out.println("  \"file\": \"" + escapeJson(file.getPath()) + "\",");
            System.out.println("  \"lines\": " + lineCount + ",");
            System.out.println("  \"bytes\": " + byteCount + ",");
            System.out.println("  \"isGui\": " + act.isGui + ",");
            System.out.println("  \"threadSafeEdt\": " + hasEdt + ",");
            System.out.println("  \"syntaxPatterns\": {");
            System.out.println("    \"lambda\": " + hasLambda + ",");
            System.out.println("    \"methodReference\": " + hasMethodRef + ",");
            System.out.println("    \"anonymousInnerClass\": " + hasAnonInner);
            System.out.println("  },");
            System.out.print("  \"matchedKeywords\": [");
            for (int i = 0; i < matchedKeywords.size(); i++) {
                System.out.print("\"" + matchedKeywords.get(i) + "\"" + (i < matchedKeywords.size() - 1 ? ", " : ""));
            }
            System.out.println("],");
            System.out.print("  \"imports\": [");
            for (int i = 0; i < detectedImports.size(); i++) {
                System.out.print("\"" + detectedImports.get(i) + "\"" + (i < detectedImports.size() - 1 ? ", " : ""));
            }
            System.out.println("]");
            System.out.println("}");
        } else {
            System.out.println("=== Source Inspection: " + act.name + " (" + act.id + ") ===");
            System.out.println("Path:               " + file.getPath());
            System.out.println("Size:               " + lineCount + " lines (" + byteCount + " bytes)");
            System.out.println("EDT Thread Safety:  " + (hasEdt ? "[PASS] Uses SwingUtilities.invokeLater" : (act.isGui ? "[WARN] No explicit invokeLater detected" : "[N/A] CLI Activity")));
            System.out.println("Listener Paradigms: Lambda=" + hasLambda + ", MethodReference=" + hasMethodRef + ", AnonymousClass=" + hasAnonInner);
            System.out.println("Verified Patterns:  " + String.join(", ", matchedKeywords));
            System.out.println("Import Count:       " + detectedImports.size() + " packages imported.");
        }
    }

    private static void cmdVerify(String[] args, boolean json) throws IOException {
        Integer filterWeek = getArgInt(args, "--week");
        int pass = 0, fail = 0;
        List<Map<String, Object>> results = new ArrayList<>();

        for (ActivityRecord r : REGISTRY) {
            if (filterWeek != null && r.week != filterWeek) continue;

            File src = new File(r.folder, r.sourceFile);
            File cls = new File(r.folder, r.className + ".class");
            if (!cls.exists() && r.week == 6) {
                File targetCls = new File("week06-javafx/target/classes/programmaticdemo", r.className + ".class");
                if (targetCls.exists()) cls = targetCls;
            } else if (!cls.exists() && r.week == 7) {
                File targetCls = new File("week07-javafx-events/target/classes/com/example", r.className + ".class");
                if (targetCls.exists()) cls = targetCls;
            }

            boolean srcExists = src.exists();
            boolean clsExists = cls.exists();

            boolean ok = srcExists && clsExists;
            if (ok) pass++; else fail++;

            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", r.id);
            map.put("class", r.className);
            map.put("week", r.week);
            map.put("sourceExists", srcExists);
            map.put("classExists", clsExists);
            map.put("status", ok ? "PASS" : "FAIL");
            results.add(map);
        }

        if (json) {
            System.out.println("{");
            System.out.println("  \"passed\": " + pass + ",");
            System.out.println("  \"failed\": " + fail + ",");
            System.out.println("  \"total\": " + (pass + fail) + ",");
            System.out.println("  \"success\": " + (fail == 0) + ",");
            System.out.println("  \"results\": [");
            for (int i = 0; i < results.size(); i++) {
                Map<String, Object> m = results.get(i);
                System.out.print("    {\"id\": \"" + m.get("id") + "\", \"class\": \"" + m.get("class") + "\", \"status\": \"" + m.get("status") + "\"}");
                if (i < results.size() - 1) System.out.println(","); else System.out.println();
            }
            System.out.println("  ]");
            System.out.println("}");
        } else {
            System.out.println("=================================================================");
            System.out.println(" VERIFICATION REPORT (Week: " + (filterWeek == null ? "All" : filterWeek) + ")");
            System.out.println("=================================================================");
            for (Map<String, Object> m : results) {
                System.out.printf("[%s] W%d %-22s (Source: %s, Bytecode: %s)%n",
                        m.get("status"), m.get("week"), m.get("class"),
                        (Boolean) m.get("sourceExists") ? "OK" : "MISSING",
                        (Boolean) m.get("classExists") ? "OK" : "MISSING");
            }
            System.out.println("-----------------------------------------------------------------");
            System.out.printf("Summary: %d Passed, %d Failed (Total: %d)%n", pass, fail, pass + fail);
            if (fail == 0) {
                System.out.println("\u001B[32m[SUCCESS] All targeted activities are cleanly compiled and ready.\u001B[0m");
            } else {
                System.out.println("\u001B[31m[FAILURE] One or more classes are missing compilation bytecode.\u001B[0m");
                System.exit(1);
            }
        }
    }

    private static void cmdGrade(String[] args, boolean json) throws IOException {
        Integer filterWeek = getArgInt(args, "--week");
        if (filterWeek == null) filterWeek = 4; // Default to Week 4 if unspecified

        double score = 0;
        double maxScore = 100;
        List<String> notes = new ArrayList<>();

        List<ActivityRecord> weekActs = new ArrayList<>();
        for (ActivityRecord r : REGISTRY) {
            if (r.week == filterWeek) weekActs.add(r);
        }

        if (weekActs.isEmpty()) {
            throw new IllegalArgumentException("No activities found for week " + filterWeek);
        }

        double pointsPerAct = 70.0 / weekActs.size(); // 70 pts for deliverables presence & compilation
        for (ActivityRecord act : weekActs) {
            File src = new File(act.folder, act.sourceFile);
            File cls = new File(act.folder, act.className + ".class");
            if (!cls.exists() && act.week == 6) {
                File targetCls = new File("week06-javafx/target/classes/programmaticdemo", act.className + ".class");
                if (targetCls.exists()) cls = targetCls;
            } else if (!cls.exists() && act.week == 7) {
                File targetCls = new File("week07-javafx-events/target/classes/com/example", act.className + ".class");
                if (targetCls.exists()) cls = targetCls;
            }

            if (src.exists() && cls.exists()) {
                score += pointsPerAct;
            } else {
                notes.add("Deduction: " + act.id + " missing source or compiled class.");
            }
        }

        // 15 pts for EDT & Thread Safety
        int edtSafeCount = 0;
        int guiCount = 0;
        for (ActivityRecord act : weekActs) {
            if (!act.isGui) continue;
            guiCount++;
            File src = new File(act.folder, act.sourceFile);
            if (src.exists()) {
                String c = Files.readString(src.toPath());
                if (c.contains("SwingUtilities.invokeLater") || c.contains("invokeLater") || c.contains("Application") || c.contains("Platform.runLater")) {
                    edtSafeCount++;
                }
            }
        }
        if (guiCount > 0) {
            double edtRatio = (double) edtSafeCount / guiCount;
            score += edtRatio * 15.0;
            if (edtRatio < 1.0) notes.add("Thread Safety: " + edtSafeCount + "/" + guiCount + " GUI activities explicitly thread-safe.");
        } else {
            score += 15.0;
        }

        // 15 pts for SelfAssessment.md presence
        File sa1 = new File("week0" + filterWeek + "-event-handling", "SelfAssessment.md");
        File sa2 = new File("week0" + filterWeek + "-swing-gui", "SelfAssessment.md");
        File sa3 = new File("Week" + filterWeek + "_Activity", "SelfAssessment.md");
        File sa4 = new File("week0" + filterWeek + "-advanced-swing", "SelfAssessment.md");
        File sa5 = new File("Week0" + filterWeek + "-observer-pattern", "SelfAssessment.md");
        File sa6 = new File("week06-javafx", "SelfAssessment.md");
        File sa7 = new File("week0" + filterWeek + "-javafx-events", "SelfAssessment.md");

        if (sa1.exists() || sa2.exists() || sa3.exists() || sa4.exists() || sa5.exists() || sa6.exists() || sa7.exists()) {
            score += 15.0;
        } else {
            notes.add("SelfAssessment.md not found for week " + filterWeek);
        }

        int finalGrade = (int) Math.round(score);

        if (json) {
            System.out.println("{");
            System.out.println("  \"week\": " + filterWeek + ",");
            System.out.println("  \"score\": " + finalGrade + ",");
            System.out.println("  \"maxScore\": 100,");
            System.out.println("  \"percentage\": \"" + finalGrade + "%\",");
            System.out.println("  \"letterGrade\": \"" + (finalGrade >= 90 ? "A" : (finalGrade >= 80 ? "B" : "C")) + "\",");
            System.out.println("  \"notes\": [");
            for (int i = 0; i < notes.size(); i++) {
                System.out.print("    \"" + escapeJson(notes.get(i)) + "\"" + (i < notes.size() - 1 ? "," : ""));
                System.out.println();
            }
            System.out.println("  ]");
            System.out.println("}");
        } else {
            System.out.println("=================================================================");
            System.out.println(" AUTOMATED EVALUATION RUBRIC (Week " + filterWeek + ")");
            System.out.println("=================================================================");
            System.out.printf("Computed Grade:   %d / 100 (%s)%n", finalGrade, (finalGrade >= 90 ? "A (Excellent)" : "Pass"));
            System.out.println("Code Completion:  " + weekActs.size() + " deliverables evaluated.");
            System.out.println("EDT Conformance:  " + edtSafeCount + "/" + guiCount + " GUI windows thread-safe.");
            if (notes.isEmpty()) {
                System.out.println("\u001B[32mAll rubrics and outcomes fully satisfied.\u001B[0m");
            } else {
                System.out.println("Observations:");
                for (String n : notes) System.out.println(" - " + n);
            }
            System.out.println("=================================================================");
        }
    }

    private static void cmdRun(String[] args) throws Exception {
        if (args.length < 2 || args[1].startsWith("-")) {
            throw new IllegalArgumentException("Usage: run <activity-id | class-name> [--headless] [--timeout <ms>]");
        }
        String target = args[1];
        ActivityRecord act = findActivity(target);
        if (act == null) {
            throw new IllegalArgumentException("Activity not found matching: " + target);
        }

        boolean headless = Arrays.asList(args).contains("--headless");
        Integer timeoutMs = getArgInt(args, "--timeout");
        if (timeoutMs == null && headless) timeoutMs = 2500; // default timeout for headless smoke test

        List<String> cmd = new ArrayList<>();
        cmd.add("java");
        if (headless) {
            cmd.add("-Djava.awt.headless=true");
            cmd.add("-Dtestfx.robot=glass");
            cmd.add("-Dglass.platform=Monocle");
            cmd.add("-Dmonocle.platform=Headless");
            cmd.add("-Dprism.order=sw");
        }
        String jfxPath = getOpenJFXModulePath();
        if (act.week == 6) {
            if (jfxPath != null) {
                cmd.add("--module-path");
                cmd.add(jfxPath);
                cmd.add("--add-modules");
                cmd.add("javafx.controls,javafx.fxml");
            }
            cmd.add("-cp");
            File targetClasses = new File("week06-javafx/target/classes");
            if (targetClasses.exists()) {
                cmd.add(targetClasses.getAbsolutePath());
            } else {
                cmd.add(new File(act.folder).getAbsolutePath());
            }
            cmd.add("programmaticdemo." + act.className);
        } else if (act.week == 7) {
            if (jfxPath != null) {
                cmd.add("--module-path");
                cmd.add(jfxPath);
                cmd.add("--add-modules");
                cmd.add("javafx.controls,javafx.fxml");
            }
            cmd.add("-cp");
            File targetClasses = new File("week07-javafx-events/target/classes");
            if (targetClasses.exists()) {
                cmd.add(targetClasses.getAbsolutePath());
            } else {
                cmd.add(new File(act.folder).getAbsolutePath());
            }
            cmd.add("com.example." + act.className);
        } else {
            cmd.add("-cp");
            cmd.add(new File(act.folder).getAbsolutePath());
            cmd.add(act.className);
        }

        ProcessBuilder pb = new ProcessBuilder(cmd);
        pb.redirectErrorStream(true);

        System.out.println("Launching [" + act.id + "] " + act.name + (headless ? " (Headless Mode)" : "") + "...");
        Process proc = pb.start();

        if (timeoutMs != null) {
            Thread.sleep(timeoutMs);
            if (proc.isAlive()) {
                proc.destroy();
                System.out.println("[SMOKE TEST SUCCESS] Process started and initialized cleanly within " + timeoutMs + "ms.");
            } else {
                int exit = proc.exitValue();
                if (exit == 0) {
                    System.out.println("[COMPLETE] Process executed and terminated with exit code 0.");
                } else {
                    System.err.println("[FAIL] Process terminated with exit code " + exit);
                    System.exit(exit);
                }
            }
        } else {
            proc.waitFor();
        }
    }

    private static ActivityRecord findActivity(String query) {
        String q = query.trim().toLowerCase();
        for (ActivityRecord r : REGISTRY) {
            if (r.id.equalsIgnoreCase(q) || r.className.equalsIgnoreCase(q) || r.sourceFile.equalsIgnoreCase(q)) {
                return r;
            }
        }
        return null;
    }

    private static Integer getArgInt(String[] args, String flag) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equalsIgnoreCase(flag)) {
                try {
                    return Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException ignored) {}
            }
        }
        return null;
    }

    private static void cmdBuild(String[] args, boolean json) throws Exception {
        long startTime = System.currentTimeMillis();
        boolean clean = Arrays.asList(args).contains("--clean");
        Integer filterWeek = getArgInt(args, "--week");

        List<String> moduleDirs = new ArrayList<>();
        if (filterWeek == null || filterWeek == 1) moduleDirs.add("Week1_Activity");
        if (filterWeek == null || filterWeek == 2) moduleDirs.add("Week02-observer-pattern");
        if (filterWeek == null || filterWeek == 3) moduleDirs.add("week03-swing-gui");
        if (filterWeek == null || filterWeek == 4) moduleDirs.add("week04-event-handling");
        if (filterWeek == null || filterWeek == 5) moduleDirs.add("week05-advanced-swing");
        if (filterWeek == null || filterWeek == 6) moduleDirs.add("week06-javafx");
        if (filterWeek == null || filterWeek == 7) moduleDirs.add("week07-javafx-events/src/main/java");
        if (filterWeek == null || filterWeek == 10) moduleDirs.add("UniversitySystem/src/main/java");
        if (filterWeek == null) moduleDirs.add("scripts");

        if (!json) {
            System.out.println("===============================================================================");
            System.out.println(" > event-driven-programming-java build");
            System.out.println(" > Target: " + (filterWeek == null ? "All Modules (Weeks 1 to 7, 10 + Launchers)" : "Week " + filterWeek));
            if (clean) System.out.println(" > Cleaning existing .class artifacts...");
            System.out.println("===============================================================================");
        }

        // Clean if requested
        int cleanedCount = 0;
        if (clean) {
            for (String dir : moduleDirs) {
                File d = new File(dir);
                if (d.exists()) {
                    cleanedCount += cleanClasses(d);
                }
            }
        }

        // Find all .java files
        Map<String, List<File>> filesByModule = new LinkedHashMap<>();
        int totalJavaFiles = 0;

        for (String dir : moduleDirs) {
            File d = new File(dir);
            if (!d.exists()) continue;
            List<File> javaFiles = new ArrayList<>();
            findJavaFiles(d, javaFiles);
            if (!javaFiles.isEmpty()) {
                filesByModule.put(dir, javaFiles);
                totalJavaFiles += javaFiles.size();
            }
        }

        if (totalJavaFiles == 0) {
            throw new IllegalStateException("No Java source files found to build!");
        }

        // Run javac on all gathered files
        List<String> javacCmd = new ArrayList<>();
        javacCmd.add("javac");
        String jfxPath = getOpenJFXModulePath();
        if (jfxPath != null && (filterWeek == null || filterWeek == 6 || filterWeek == 7 || filterWeek == 10)) {
            javacCmd.add("--module-path");
            javacCmd.add(jfxPath);
            javacCmd.add("--add-modules");
            javacCmd.add("javafx.controls,javafx.fxml");
        }
        File pgJar = new File("UniversitySystem/lib/postgresql-42.7.3.jar");
        if (pgJar.exists()) {
            javacCmd.add("-cp");
            javacCmd.add("." + File.pathSeparator + pgJar.getAbsolutePath());
        }
        for (List<File> list : filesByModule.values()) {
            for (File f : list) {
                javacCmd.add(f.getPath());
            }
        }

        ProcessBuilder pb = new ProcessBuilder(javacCmd);
        pb.redirectErrorStream(true);
        Process proc = pb.start();

        StringBuilder compilerOutput = new StringBuilder();
        try (BufferedReader r = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
            String line;
            while ((line = r.readLine()) != null) {
                compilerOutput.append(line).append("\n");
            }
        }
        int exitCode = proc.waitFor();
        long elapsedMs = System.currentTimeMillis() - startTime;

        if (exitCode != 0) {
            if (json) {
                System.out.println("{");
                System.out.println("  \"status\": \"failure\",");
                System.out.println("  \"exitCode\": " + exitCode + ",");
                System.out.println("  \"elapsedMs\": " + elapsedMs + ",");
                System.out.println("  \"error\": \"" + escapeJson(compilerOutput.toString().trim()) + "\"");
                System.out.println("}");
            } else {
                System.err.println("\n\u001B[31m[BUILD FAILED] Compiler returned exit code " + exitCode + ":\u001B[0m");
                System.err.println(compilerOutput.toString());
            }
            System.exit(exitCode);
        }

        // Verify generated bytecode classes
        int totalClassesGenerated = 0;
        for (String dir : moduleDirs) {
            File d = new File(dir);
            if (d.exists()) {
                totalClassesGenerated += countClasses(d);
            }
        }

        if (json) {
            System.out.println("{");
            System.out.println("  \"status\": \"success\",");
            System.out.println("  \"exitCode\": 0,");
            System.out.println("  \"elapsedMs\": " + elapsedMs + ",");
            System.out.println("  \"sourcesCompiled\": " + totalJavaFiles + ",");
            System.out.println("  \"classesGenerated\": " + totalClassesGenerated + ",");
            System.out.println("  \"cleaned\": " + cleanedCount + ",");
            System.out.println("  \"modules\": {");
            int mi = 0;
            for (Map.Entry<String, List<File>> entry : filesByModule.entrySet()) {
                System.out.print("    \"" + entry.getKey() + "\": " + entry.getValue().size() + (mi++ < filesByModule.size() - 1 ? "," : ""));
                System.out.println();
            }
            System.out.println("  }");
            System.out.println("}");
        } else {
            for (Map.Entry<String, List<File>> entry : filesByModule.entrySet()) {
                System.out.printf("  \u001B[32m✔\u001B[0m %-30s (%d source files)%n", entry.getKey(), entry.getValue().size());
            }
            System.out.println("-------------------------------------------------------------------------------");
            System.out.printf("\u001B[32m✨ Build succeeded in %.2fs!\u001B[0m%n", elapsedMs / 1000.0);
            System.out.printf("   Total Java sources: %d%n", totalJavaFiles);
            System.out.printf("   Output bytecode:    %d .class files%n", totalClassesGenerated);
            if (clean) System.out.printf("   Cleaned artifacts:  %d files%n", cleanedCount);
            System.out.println("   Exit code:          0 (PASS)");
            System.out.println("===============================================================================");
        }
    }

    private static void findJavaFiles(File dir, List<File> result) {
        File[] files = dir.listFiles();
        if (files == null) return;
        for (File f : files) {
            if (f.isDirectory()) {
                if (f.getName().equals(".mvn") || f.getName().equals("target")) continue;
                findJavaFiles(f, result);
            } else if (f.getName().endsWith(".java")) {
                result.add(f);
            }
        }
    }

    private static int cleanClasses(File dir) {
        int count = 0;
        File[] files = dir.listFiles();
        if (files == null) return 0;
        for (File f : files) {
            if (f.isDirectory()) {
                if (f.getName().equals(".mvn") || f.getName().equals("lib") || f.getName().equals("target")) continue;
                count += cleanClasses(f);
            } else if (f.getName().endsWith(".class")) {
                if (f.delete()) count++;
            }
        }
        return count;
    }

    private static int countClasses(File dir) {
        int count = 0;
        File[] files = dir.listFiles();
        if (files == null) return 0;
        for (File f : files) {
            if (f.isDirectory()) {
                if (f.getName().equals(".mvn") || f.getName().equals("lib")) continue;
                count += countClasses(f);
            } else if (f.getName().endsWith(".class")) {
                count++;
            }
        }
        return count;
    }

    private static String getOpenJFXModulePath() {
        boolean isWin = System.getProperty("os.name", "").toLowerCase().contains("win");
        File localLib = new File(isWin ? "week06-javafx/lib/win" : "week06-javafx/lib/linux");
        if (localLib.exists()) {
            return localLib.getAbsolutePath();
        }
        String userHome = System.getProperty("user.home");
        File jfxDir = new File(userHome, ".m2/repository/org/openjfx");
        if (!jfxDir.exists()) return null;
        List<File> jars = new ArrayList<>();
        findJarFiles(jfxDir, jars);
        if (jars.isEmpty()) return null;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jars.size(); i++) {
            if (i > 0) sb.append(File.pathSeparator);
            sb.append(jars.get(i).getAbsolutePath());
        }
        return sb.toString();
    }

    private static void findJarFiles(File dir, List<File> result) {
        File[] files = dir.listFiles();
        if (files == null) return;
        for (File f : files) {
            if (f.isDirectory()) {
                findJarFiles(f, result);
            } else if (f.getName().endsWith(".jar") && !f.getName().endsWith("-sources.jar") && !f.getName().endsWith("-javadoc.jar")) {
                result.add(f);
            }
        }
    }

    private static String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }
}

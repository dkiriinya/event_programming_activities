**Week 8 Self-Paced Guide: Properties and Bindings in JavaFX** 

**Complete Student Handbook — No Instructor Required** 

**How to Use This Handbook** 

This guide is designed so that you can complete Week 8 **entirely on your own**. Every activity  has: 

• Clear purpose and real-world connection 

• Step-by-step instructions 

• Exact code to type 

• Expected output (so you know if it worked) 

• Self-check questions with answers 

• Troubleshooting tips 

**Estimated time:** 8–10 hours (spread over multiple sessions) 

**Working directory:** C:\\Users\\RUICT\\Desktop\\projects\\event-driven-programming-java\\week08- javafx-bindings\\ 

**Tools you will use:** 

• **VS Code** (or NetBeans) — for coding 

• **GitHub** — for pushing your work 

**Learning Outcomes for Week 8** 

By the end of this guide, you will be able to: 

1\. **Use** JavaFX properties to create observable data models 

2\. **Implement** ChangeListeners and InvalidationListeners on properties 

3\. **Create** unidirectional and bidirectional bindings between UI and model 4\. **Build** a fully reactive UI that updates automatically with bindings

Page **1** of **18**   
**Tick each box as you complete it:** 

• □ Outcome 1 

• □ Outcome 2 

• □ Outcome 3 

• □ Outcome 4 

**Why Properties and Bindings? Real-World Applications** 

| Real-World   System | How Bindings Are Used  | Why It Matters |
| :---- | :---- | :---- |
| **Spreadsheets   (Excel)** | Changing cell A1 automatically   updates the SUM in cell A10. | This is the definition of a  "reactive" UI. |
| **E-Commerce   (Amazon)** | Checking "Gift Wrap" automatically  adds \$5.00 to the Total Label. | Avoids writing messy if-else  code inside button clicks. |
| **Text Editors   (Word)** | The word count at the bottom   updates instantly as you type. | Provides real-time feedback  without blocking the UI. |
| **Media Players** | Dragging the volume slider updates  the speaker icon (mute/unmute). | Keeps the UI perfectly   synchronized with the data  model. |

**Setup: Prepare Your Environment** 

1\. Open **VS Code**. 

2\. Create a new **JavaFX Maven project** named week08-javafx-bindings in: text 

C:\\Users\\RUICT\\Desktop\\projects\\event-driven-programming-java\\week08-javafx-bindings 3\. Ensure your pom.xml has the JavaFX dependencies (just like in Week 6).

Page **2** of **18**   
4\. Create a package named com.example. 

**Activity 1: Observable Properties (The Data Model)** 

**Purpose** 

Learn how to create data models using JavaFX properties instead of standard Java variables. **Learning Outcome addressed:** \#1 

**Time:** 45 minutes 

 **Real-World Application** 

Every data-driven application (CRMs, ERPs, Games) needs a Model. In JavaFX, models use  Property classes (like StringProperty or IntegerProperty). This allows the UI to "watch" the  model for changes. 

**Step 1.1: Write the Code** 

Create Person.java in com.example: 

java 

package com.example; 

import javafx.beans.property.IntegerProperty; 

import javafx.beans.property.SimpleIntegerProperty; 

import javafx.beans.property.SimpleStringProperty; 

import javafx.beans.property.StringProperty; 

public class Person { 

 *// 1\. Use Properties instead of standard String/int\!* 

 private final StringProperty name \= new SimpleStringProperty(""); 

 private final IntegerProperty age \= new SimpleIntegerProperty(0);

Page **3** of **18**   
 *// 2\. Standard Getters and Setters* 

 public String getName() { return name.get(); } 

 public void setName(String value) { name.set(value); } 

 public int getAge() { return age.get(); } 

 public void setAge(int value) { age.set(value); } 

 *// 3\. Property Getters (CRITICAL for Bindings\!)* 

 *// This allows other components to "bind" to this property.* 

 public StringProperty nameProperty() { return name; } 

 public IntegerProperty ageProperty() { return age; } 

} 

**Step 1.2: Self-Check Questions** 

**Q1: Why do we use** StringProperty **instead of a standard** String **in a JavaFX model?** 

A standard \`String\` is immutable and cannot notify anyone when it changes. A \`StringProperty\`  wraps the value and allows the UI to "listen" to it or "bind" to it, enabling automatic UI updates.  

**Q2: What is the purpose of the** nameProperty() **method?** 

It returns the property object itself (not the value). This is required so that other components  can bind to it or attach listeners to it (e.g., \`label.textProperty().bind(person.nameProperty())\`).  

**Activity 2: ChangeListener vs. InvalidationListener** 

**Purpose** 

Learn how to listen for changes on a Property and understand the difference between the two  listener types. 

**Learning Outcome addressed:** \#2

Page **4** of **18**   
**Time:** 45 minutes 

 **Real-World Application** 

**InvalidationListener:** Used when you just need to know "something changed" so you can trigger  a refresh (e.g., refreshing a table). 

**ChangeListener:** Used when you need to know exactly what the old value was and what the  new value is (e.g., an Undo/Redo system or auditing logs). 

**Step 2.1: Write the Code** 

Create ListenerDemo.java: 

java 

package com.example; 

import javafx.application.Application; 

import javafx.stage.Stage; 

public class ListenerDemo extends Application { 

 @Override 

 public void start(Stage primaryStage) { 

 Person person \= new Person(); 

 *// 1\. InvalidationListener (Fires when the property becomes "invalid")*  person.nameProperty().addListener((observable) \-\> { 

 System.out.println("1. \[InvalidationListener\] Name was changed\!");  }); 

 *// 2\. ChangeListener (Provides old and new values)*

Page **5** of **18**   
 person.nameProperty().addListener((observable, oldValue, newValue) \-\> {  System.out.println("2. \[ChangeListener\] Name changed from '"   \+ oldValue \+ "' to '" \+ newValue \+ "'"); 

 }); 

 *// Trigger the changes* 

 System.out.println("--- Setting name to Alice \---"); 

 person.setName("Alice"); 

 System.out.println("\\n--- Setting name to Bob \---"); 

 person.setName("Bob"); 

 System.out.println("\\n--- Setting name to Bob (same value) \---");  person.setName("Bob"); *// ChangeListener will NOT fire here\!*  } 

 public static void main(String\[\] args) { 

 launch(args); 

 } 

} 

**Step 2.2: Run and Observe** 

Run the app (it won't open a window, just print to the console). **Expected Output:** 

text 

\--- Setting name to Alice \--- 

1\. \[InvalidationListener\] Name was changed\!

Page **6** of **18**   
2\. \[ChangeListener\] Name changed from '' to 'Alice' 

\--- Setting name to Bob \--- 

1\. \[InvalidationListener\] Name was changed\! 

2\. \[ChangeListener\] Name changed from 'Alice' to 'Bob' 

\--- Setting name to Bob (same value) \--- 

*Notice how when you set the name to "Bob" a second time, neither listener fires\!* 

**Step 2.3: Self-Check Questions** 

**Q1: What is the main difference between an InvalidationListener and a ChangeListener?** 

An \`InvalidationListener\` only tells you that the property is no longer valid (something  changed). A \`ChangeListener\` provides the old value and the new value, giving you more  information. 

**Q2: If you set a property to the exact same value it already holds, do the listeners fire?** 

No. JavaFX optimizes properties by checking if the new value is actually different from the old  value. If they are the same, the listeners are not triggered.  

**Activity 3: Unidirectional Binding** 

**Purpose** 

Learn how to automatically sync one property to another. When the source changes, the target  updates automatically. 

**Learning Outcome addressed:** \#3 

**Time:** 60 minutes 

 **Real-World Application** 

**Live Character Counters:** Typing in a text area automatically updates a label showing the length.  You don't need an ActionListener or DocumentListener; you just bind the label to the text area's  length.

Page **7** of **18**   
**Step 3.1: Write the Code** 

Create UnidirectionalDemo.java: 

java 

package com.example; 

import javafx.application.Application; 

import javafx.scene.Scene; 

import javafx.scene.control.Label; 

import javafx.scene.control.TextField; 

import javafx.scene.layout.VBox; 

import javafx.stage.Stage; 

public class UnidirectionalDemo extends Application { 

 @Override 

 public void start(Stage primaryStage) { 

 TextField nameField \= new TextField(); 

 nameField.setPromptText("Type your name here...");  Label greetingLabel \= new Label("Hello, Guest"); 

 VBox root \= new VBox(20, nameField, greetingLabel);  root.setStyle("-fx-padding: 20; \-fx-alignment: center;"); 

 *// \---- UNIDIRECTIONAL BINDING \----*

Page **8** of **18**   
 *// As the TextField changes, the Label updates automatically.* 

 greetingLabel.textProperty().bind( 

 nameField.textProperty().concat(", welcome\!") 

 ); 

 Scene scene \= new Scene(root, 400, 200); 

 primaryStage.setTitle("Unidirectional Binding"); 

 primaryStage.setScene(scene); 

 primaryStage.show(); 

 } 

 public static void main(String\[\] args) { 

 launch(args); 

 } 

} 

**Step 3.2: Run and Test** 

1\. Run the app. 

2\. Type your name into the text field. Notice how the label updates in real-time. 

**Step 3.3: Self-Check Questions** 

**Q1: What does "unidirectional" mean in the context of bindings?** 

It means the data flows in only one direction. Changes to the source (the TextField) update the  target (the Label), but changes to the target do not affect the source.  

**Q2: What happens if you try to call** greetingLabel.setText("New Text") **on a bound label?** 

It will throw a \`java.lang.RuntimeException: A bound value cannot be set.\` You must call  \`.unbind()\` first before manually setting the value. 

Page **9** of **18**   
**Activity 4: Bidirectional Binding** 

**Purpose** 

Learn how to link two properties so that changes to *either one* update the other. **Learning Outcome addressed:** \#3 

**Time:** 45 minutes 

 **Real-World Application** 

**Settings Menus:** Dragging a Slider updates a TextField with the exact number, and typing a  number in the TextField moves the Slider to that position. 

**Step 4.1: Write the Code** 

Create BidirectionalDemo.java: 

java 

package com.example; 

import javafx.application.Application; 

import javafx.scene.Scene; 

import javafx.scene.control.Label; 

import javafx.scene.control.Slider; 

import javafx.scene.control.TextField; 

import javafx.scene.layout.VBox; 

import javafx.stage.Stage; 

public class BidirectionalDemo extends Application { 

 @Override 

 public void start(Stage primaryStage) {

Page **10** of **18**   
 Label instruction \= new Label("Drag the slider or type a number:"); 

 Slider slider \= new Slider(0, 100, 50); 

 TextField ageField \= new TextField("50"); 

 VBox root \= new VBox(20, instruction, slider, ageField); 

 root.setStyle("-fx-padding: 20; \-fx-alignment: center;"); 

 *// \---- BIDIRECTIONAL BINDING \----* 

 *// We need a converter because the Slider is a Double and the TextField is a String.*  ageField.textProperty().bindBidirectional( 

 slider.valueProperty(),  

 new javafx.util.converter.NumberStringConverter() 

 ); 

 Scene scene \= new Scene(root, 400, 200); 

 primaryStage.setTitle("Bidirectional Binding"); 

 primaryStage.setScene(scene); 

 primaryStage.show(); 

 } 

 public static void main(String\[\] args) { 

 launch(args); 

 } 

} 

**Step 4.2: Run and Test**

Page **11** of **18**   
1\. Run the app. 

2\. Drag the slider. The text field updates automatically. 

3\. Type 20 into the text field and press Enter. The slider jumps to 20\! 

**Step 4.3: Self-Check Questions** 

**Q1: What is the main difference between** bind() **and** bindBidirectional()**?** 

\`bind()\` (unidirectional) only updates the target when the source changes. \`bindBidirectional()\`  updates both properties, no matter which one is changed. 

**Q2: Why did we need** NumberStringConverter **in this activity?** 

Because we were binding a \`DoubleProperty\` (the Slider) to a \`StringProperty\` (the TextField).  The converter translates the number to a string, and vice versa.  

**Activity 5: Capstone \- Reactive Registration Form** 

**Purpose** 

Build a complete UI that uses bindings to handle validation and live updates without writing a  single button click handler for validation. 

**Learning Outcome addressed:** \#4 

**Time:** 90 minutes 

 **Real-World Application** 

This is how modern frameworks (like React, Vue, and JavaFX) handle forms. The "Submit"  button is automatically disabled until all fields are valid, and live previews update as you type. 

**Step 5.1: Write the Code** 

Create ReactiveFormApp.java: 

java 

package com.example;

Page **12** of **18**   
import javafx.application.Application; 

import javafx.beans.binding.Bindings; 

import javafx.geometry.Insets; 

import javafx.scene.Scene; 

import javafx.scene.control.\*; 

import javafx.scene.layout.GridPane; 

import javafx.scene.paint.Color; 

import javafx.stage.Stage; 

public class ReactiveFormApp extends Application { 

 @Override 

 public void start(Stage primaryStage) { 

 GridPane grid \= new GridPane(); 

 grid.setPadding(new Insets(20)); 

 grid.setHgap(10); 

 grid.setVgap(15); 

 *// UI Components* 

 TextField nameField \= new TextField(); 

 TextField emailField \= new TextField(); 

 TextArea bioField \= new TextArea(); 

 bioField.setPrefRowCount(3); 

 bioField.setWrapText(true); 


 Label charCountLabel \= new Label("0 / 150 characters");

Page **13** of **18**   
 Label previewLabel \= new Label("Preview will appear here");  previewLabel.setTextFill(Color.BLUE); 


 Button submitBtn \= new Button("Submit Registration"); 

 *// Build Layout* 

 grid.add(new Label("Name:"), 0, 0); 

 grid.add(nameField, 1, 0); 

 grid.add(new Label("Email:"), 0, 1); 

 grid.add(emailField, 1, 1); 

 grid.add(new Label("Bio:"), 0, 2); 

 grid.add(bioField, 1, 2); 

 grid.add(charCountLabel, 1, 3); 

 grid.add(previewLabel, 1, 4); 

 grid.add(submitBtn, 1, 5); 

 *// \============================================================  // THE MAGIC OF BINDINGS*  

 *// \============================================================* 

 *// 1\. LIVE CHARACTER COUNT* 

 charCountLabel.textProperty().bind( 

 bioField.textProperty().length().asString() 

 .concat(" / 150 characters") 

 );

Page **14** of **18**   
 *// 2\. LIVE PREVIEW* 

 previewLabel.textProperty().bind( 

 Bindings.concat("Hi, I am ", nameField.textProperty(), ". ", bioField.textProperty())  ); 

 *// 3\. VALIDATION: DISABLE SUBMIT BUTTON* 

 submitBtn.disableProperty().bind( 

 nameField.textProperty().isEmpty() 

 .or(emailField.textProperty().isEmpty()) 

 .or(emailField.textProperty().contains("@").not()) 

 .or(bioField.textProperty().length().greaterThan(150)) 

 ); 

 *// 4\. SUBMIT ACTION* 

 submitBtn.setOnAction(e \-\> { 

 Alert alert \= new Alert(Alert.AlertType.INFORMATION); 

 alert.setTitle("Success"); 

 alert.setHeaderText("Registration Successful"); 

 alert.setContentText("Welcome, " \+ nameField.getText() \+ "\!");  alert.showAndWait(); 

 }); 

 Scene scene \= new Scene(grid, 500, 450); 

 primaryStage.setTitle("Reactive Registration Form"); 

 primaryStage.setScene(scene); 

 primaryStage.show();

Page **15** of **18**   
 } 

 public static void main(String\[\] args) { 

 launch(args); 

 } 

} 

**Step 5.2: Run and Test** 

1\. Run the app. Notice the Submit Button is grayed out (disabled). 

2\. Type a name and an email (e.g., test@test.com). The button becomes **enabled**\! 3\. Delete the @ symbol. The button disables again. 

4\. Type in the Bio field. Watch the character count update in real-time. 5\. If you type more than 150 characters, the button disables again. 

**Step 5.3: Self-Check Questions** 

**Q1: Why is it better to bind the** disableProperty() **instead of using an** if **statement inside a**  KeyListener**?** 

Bindings are declarative and automatic. They eliminate the need to write code that constantly  checks every keystroke. It makes the code cleaner, easier to read, and impossible to get out of  sync.  

**Q2: What does the** .not() **method do in the email validation binding?** 

It inverts the boolean value. \`contains("@")\` returns true if the email has an @ symbol. \`.not()\`  changes it so the button is disabled if the email does \*not\* contain the @ symbol.  

**Push Your Work to GitHub** 

1\. Open the terminal in your project root: C:\\Users\\RUICT\\Desktop\\projects\\event-driven programming-java. 

2\. Stage, commit, and push your changes:

Page **16** of **18**   
powershell 

git add . 

git commit \-m "Complete Week 8: JavaFX Properties and Bindings 

\- Activity 1: Observable Properties (Person model) 

\- Activity 2: ChangeListener vs InvalidationListener 

\- Activity 3: Unidirectional Binding 

\- Activity 4: Bidirectional Binding 

\- Activity 5: Capstone Reactive Registration Form" 

git push origin main 

**Final Self-Assessment** 

**Learning Outcome 1: Use JavaFX Properties** 

• □ I can create a model using StringProperty and IntegerProperty. 

• □ I understand why properties need standard getters and property() getters. **Learning Outcome 2: Implement Listeners** 

• □ I know the difference between ChangeListener and InvalidationListener. • □ I can add a listener to a property. 

**Learning Outcome 3: Create Bindings** 

• □ I can bind a target property to a source property using .bind(). 

• □ I can create a bidirectional binding using .bindBidirectional(). 

**Learning Outcome 4: Build a Reactive UI** 

• □ I can bind a UI component's visibility or disable state to a conditional expression (e.g.,  isEmpty().or(...)). 

• □ I successfully completed the Reactive Registration Form. 

**If you can check all of these — you have fully achieved the Week 8 learning outcomes\!**

Page **17** of **18**   
Page **18** of **18**
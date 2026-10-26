**Week 7 Self-Paced Guide: Event Handling in JavaFX** 

**Complete Student Handbook — No Instructor Required** 

**How to Use This Handbook** 

This guide is designed so that you can complete Week 7 **entirely on your own**. Every activity  has: 

• Clear purpose and real-world connection 

• Step-by-step instructions 

• Exact code to type 

• Expected output (so you know if it worked) 

• Self-check questions with answers 

• Troubleshooting tips 

**Estimated time:** 8–10 hours (spread over multiple sessions) 

**Working directory:** C:\\Users\\RUICT\\Desktop\\projects\\event-driven-programming-java\\week07- javafx-events\\ 

**Tools you will use:** 

• **VS Code** (or NetBeans) — for coding 

• **GitHub** — for pushing your work 

**Learning Outcomes for Week 7** 

By the end of this guide, you will be able to: 

1\. **Handle** various event types in JavaFX including action, key, and mouse events 2\. **Differentiate** between event filters and event handlers 

3\. **Implement** drag-and-drop functionality in a JavaFX application 

4\. **Create** and fire custom events in JavaFX

Page **1** of **19**   
**Tick each box as you complete it:** 

• □ Outcome 1 

• □ Outcome 2 

• □ Outcome 3 

• □ Outcome 4 

**Why JavaFX Events? Real-World Applications** 

| Concept | Real-World   Application | Why It Matters |
| :---- | :---- | :---- |
| **Event Filters** | **ATM Security   Systems** | Intercepting the "Cancel" button to prevent users from  canceling a transaction after cash is dispensed. |
| **Mouse &   Keyboard   Events** | **Video Games &  CAD Software** | Moving a 3D model with a mouse or using arrow keys  to control a character. |
| **Drag and   Drop** | **Trello, Jira, OS   File Explorers** | Moving a file to a new folder, or dragging a task card  from "To Do" to "Done". |
| **Custom   Events** | **E-Commerce   Checkout** | When a payment is successful, a PaymentSuccessEvent  is fired, which triggers the Email service, Inventory  service, and Receipt service simultaneously. |

**Setup: Prepare Your Environment** 

1\. Open **VS Code** (or NetBeans). 

2\. Create a new **JavaFX Maven project** named week07-javafx-events in: text 

C:\\Users\\RUICT\\Desktop\\projects\\event-driven-programming-java\\week07-javafx-events

Page **2** of **19**   
3\. Ensure your pom.xml has the JavaFX dependencies (just like in Week 6). 4\. Create a package named com.example. 

**Activity 1: Event Filters vs. Event Handlers** 

**Purpose** 

Understand the two phases of JavaFX event propagation: **Capturing** (Filters) and **Bubbling** (Handlers). 

**Learning Outcome addressed:** \#2 

**Time:** 45 minutes 

 **Real-World Application** 

Think of an **airport security checkpoint**. 

• **Event Filter:** The security guard at the entrance (capturing phase). They check everyone  before they reach the gate. If they find a problem, they stop you there (e.consume()). 

• **Event Handler:** The gate agent (bubbling phase). They handle the boarding pass after  you arrive at the gate. 

**Step 1.1: Write the Code** 

Create a file EventPropagationDemo.java in com.example: 

java 

package com.example; 

import javafx.application.Application; 

import javafx.scene.Scene; 

import javafx.scene.control.Button; 

import javafx.scene.control.Label; 

import javafx.scene.layout.VBox; 

import javafx.stage.Stage;

Page **3** of **19**   
public class EventPropagationDemo extends Application { 

 @Override 

 public void start(Stage primaryStage) { 

 Label logLabel \= new Label("Click the button..."); 

 Button btn \= new Button("Click Me\!"); 

 VBox root \= new VBox(20, logLabel, btn); 

 root.setStyle("-fx-padding: 20; \-fx-alignment: center;"); 

 *// 1\. EVENT FILTER (Capturing Phase \- Top Down)* 

 *// This fires BEFORE the event reaches the button* 

 root.addEventFilter(javafx.scene.input.MouseEvent.MOUSE\_CLICKED, e \-\> {  System.out.println("1. \[FILTER\] Root intercepted the click\!");   

 *// Uncomment the line below to stop the event from reaching the button\!  // e.consume();*  

 }); 

 *// 2\. EVENT HANDLER ON ROOT (Bubbling Phase \- Bottom Up)  // This fires AFTER the button handles the event, as it bubbles up*  root.addEventHandler(javafx.scene.input.MouseEvent.MOUSE\_CLICKED, e \-\> {  System.out.println("3. \[HANDLER\] Root saw the click bubbling up\!");  });

Page **4** of **19**   
 *// 3\. EVENT HANDLER ON BUTTON (Bubbling Phase \- Target)*  btn.setOnMouseClicked(e \-\> { 

 System.out.println("2. \[HANDLER\] Button was clicked\!");  logLabel.setText("Button was clicked\!"); 

 }); 

 Scene scene \= new Scene(root, 400, 300); 

 primaryStage.setTitle("Event Propagation"); 

 primaryStage.setScene(scene); 

 primaryStage.show(); 

 } 

 public static void main(String\[\] args) { 

 launch(args); 

 } 

} 

**Step 1.2: Run and Observe** 

1\. Run the app. 

2\. Click the button. Look at the **Console/Terminal** output. 3\. **Expected Output:** 

text 

1\. \[FILTER\] Root intercepted the click\! 

2\. \[HANDLER\] Button was clicked\! 

3\. \[HANDLER\] Root saw the click bubbling up\! 

**Step 1.3: Self-Check Questions** 

**Q1: Which phase of event propagation does an Event Filter belong to?**

Page **5** of **19**   
The Capturing Phase:. It travels from the root node down to the target node. \</details\> **Q2: What does the** e.consume() **method do?** 

It stops the event from propagating any further. If called in a filter, the event will never reach the  target node or any handlers. 

**Q3: How do you register an Event Handler (Bubbling phase) in code?** 

Using the \`addEventHandler()\` method or convenience methods like \`setOnMouseClicked()\`,  \`setOnAction()\`, etc.  

**Activity 2: Mouse and Key Events** 

**Purpose** 

Learn how to track mouse movement/clicks and keyboard presses to create interactive, game like applications. 

**Learning Outcome addressed:** \#1 

**Time:** 60 minutes 

 **Real-World Application** 

**Photo Editors (Photoshop):** When you click and drag your mouse, the software tracks the exact  X/Y coordinates to paint a line. **Video Games:** Arrow keys control the player's movement. 

**Step 2.1: Write the Code** 

Create MouseKeyDemo.java: 

java 

package com.example; 

import javafx.application.Application; 

import javafx.scene.Scene; 

import javafx.scene.input.KeyCode; 

import javafx.scene.layout.Pane;

Page **6** of **19**   
import javafx.scene.paint.Color; 

import javafx.scene.shape.Circle; 

import javafx.scene.text.Text; 

import javafx.stage.Stage; 

public class MouseKeyDemo extends Application { 

 private double x \= 250; 

 private double y \= 200; 

 private final double STEP \= 15; *// How far to move per key press* 

 @Override 

 public void start(Stage primaryStage) { 

 Pane root \= new Pane(); 

 root.setStyle("-fx-background-color: \#f4f4f4;"); 

 *// The player character* 

 Circle player \= new Circle(x, y, 20, Color.BLUE); 


 *// A text label to show mouse coordinates* 

 Text mouseCoords \= new Text(10, 20, "Mouse Coordinates: X=0, Y=0");  root.getChildren().addAll(player, mouseCoords); 

 *// \---- MOUSE EVENTS \----* 

 *// Track mouse movement over the whole pane*

Page **7** of **19**   
 root.setOnMouseMoved(e \-\> { 

 mouseCoords.setText("Mouse Coordinates: X=" \+ e.getX() \+ ", Y=" \+ e.getY());  }); 

 *// \---- KEY EVENTS \----* 

 *// Note: Key events need focus. Setting the pane focusable allows it to listen.*  root.setFocusable(true); 

 root.setOnKeyPressed(e \-\> { 

 KeyCode key \= e.getCode(); 

 if (key \== KeyCode.UP) y \-= STEP; 

 if (key \== KeyCode.DOWN) y \+= STEP; 

 if (key \== KeyCode.LEFT) x \-= STEP; 

 if (key \== KeyCode.RIGHT) x \+= STEP; 

 *// Keep the circle inside the window bounds* 

 x \= Math.max(20, Math.min(x, 480)); 

 y \= Math.max(20, Math.min(y, 380)); 

 player.setCenterX(x); 

 player.setCenterY(y); 

 }); 

 Scene scene \= new Scene(root, 500, 400); 


 *// Ensure the pane gets focus as soon as the app starts* 

 primaryStage.setOnShown(e \-\> root.requestFocus());

Page **8** of **19**   
 primaryStage.setTitle("Mouse and Key Events"); 

 primaryStage.setScene(scene); 

 primaryStage.show(); 

 } 

 public static void main(String\[\] args) { 

 launch(args); 

 } 

} 

**Step 2.2: Run and Test** 

1\. Run the app. 

2\. Move your mouse around the window. The text at the top left updates with X/Y  coordinates. 

3\. Click inside the window once (to give it focus), then use the **Arrow keys** to move the  blue circle. 

**Step 2.3: Self-Check Questions** 

**Q1: Which method handles tracking the mouse's position as it moves without clicking?** \`setOnMouseMoved()\`  

**Q2: Why might a KeyEvent not fire when you press a key?** 

Because the component does not have \*\*focus\*\*. You must ensure the node is focusable and  has requested focus (\`root.requestFocus()\`).  

**Q3: How do you check which physical key was pressed in a** KeyEvent**?** 

By using the \`getCode()\` method on the event object (e.g., \`e.getCode() \== KeyCode.UP\`). 

Page **9** of **19**   
**Activity 3: Drag and Drop** 

**Purpose** 

Implement a drag-and-drop interaction where a user can pick up a component and drop it into a  specific target zone. 

**Learning Outcome addressed:** \#3 

**Time:** 75 minutes 

 **Real-World Application** 

**Jira / Trello:** Dragging a task from "To Do" to "Done". 

**Shopping Carts:** Dragging a product image into the cart icon to add it. 

**Step 3.1: Write the Code** 

Create DragDropDemo.java: 

java 

package com.example; 

import javafx.application.Application; 

import javafx.scene.Scene; 

import javafx.scene.control.Label; 

import javafx.scene.input.ClipboardContent; 

import javafx.scene.input.Dragboard; 

import javafx.scene.input.TransferMode; 

import javafx.scene.layout.VBox; 

import javafx.scene.paint.Color; 

import javafx.scene.shape.Rectangle; 

import javafx.stage.Stage;

Page **10** of **19**   
public class DragDropDemo extends Application { 

 @Override 

 public void start(Stage primaryStage) { 

 *// 1\. Create the Drag Source (The Item to move)* 

 Label itemToDrag \= new Label("Drag Me "); 

 itemToDrag.setStyle("-fx-background-color: \#f39c12; \-fx-padding: 10; \-fx-font-size: 16px; \-fx text-fill: white;"); 

 *// 2\. Create the Drop Target (The Zone)* 

 Rectangle dropZone \= new Rectangle(200, 100); 

 dropZone.setFill(Color.LIGHTGRAY); 

 dropZone.setStroke(Color.GRAY); 

 dropZone.setStrokeWidth(2); 


 Label dropLabel \= new Label("Drop Here\!"); 


 *// Container for the drop zone* 

 VBox dropContainer \= new VBox(10, dropZone, dropLabel); 

 dropContainer.setStyle("-fx-alignment: center;"); 

 *// \---- DRAG DETECTED (Source) \----* 

 itemToDrag.setOnDragDetected(event \-\> { 

 Dragboard db \= itemToDrag.startDragAndDrop(TransferMode.MOVE);  ClipboardContent content \= new ClipboardContent(); 

 content.putString(itemToDrag.getText());

Page **11** of **19**   
 db.setContent(content); 

 event.consume(); 

 }); 

 *// \---- DRAG OVER (Target) \----* 

 *// Tells JavaFX that this node accepts dragged items* 

 dropZone.setOnDragOver(event \-\> { 

 if (event.getGestureSource() \!= dropZone && event.getDragboard().hasString()) {  event.acceptTransferModes(TransferMode.MOVE); 

 } 

 event.consume(); 

 }); 

 *// \---- DRAG ENTERED (Visual Feedback) \----* 

 dropZone.setOnDragEntered(event \-\> { 

 if (event.getGestureSource() \!= dropZone && event.getDragboard().hasString()) {  dropZone.setFill(Color.LIGHTGREEN); *// Change color when hovering*  } 

 }); 

 *// \---- DRAG EXITED (Reset Visual Feedback) \----* 

 dropZone.setOnDragExited(event \-\> { 

 dropZone.setFill(Color.LIGHTGRAY); *// Reset color* 

 }); 

 *// \---- DRAG DROPPED (Target) \----*

Page **12** of **19**   
 dropZone.setOnDragDropped(event \-\> { 

 Dragboard db \= event.getDragboard(); 

 boolean success \= false; 


 if (db.hasString()) { 

 *// Handle the drop\!* 

 dropLabel.setText("Dropped: " \+ db.getString());  itemToDrag.setDisable(true); *// Disable the item after drop*  success \= true; 

 } 


 event.setDropCompleted(success); 

 event.consume(); 

 }); 

 VBox root \= new VBox(30, itemToDrag, dropContainer);  root.setStyle("-fx-padding: 30; \-fx-alignment: center;"); 

 Scene scene \= new Scene(root, 400, 300); 

 primaryStage.setTitle("Drag and Drop Demo");  primaryStage.setScene(scene); 

 primaryStage.show(); 

 } 

 public static void main(String\[\] args) { 

 launch(args);

Page **13** of **19**   
 } 

} 

**Step 3.2: Run and Test** 

1\. Run the app. 

2\. Click and hold the "Drag Me " label. 

3\. Drag it over the gray rectangle. The rectangle should turn **Green**. 

4\. Release the mouse button (Drop). The label below the rectangle should update to say  "Dropped: Drag Me ". 

**Step 3.3: Self-Check Questions** 

**Q1: Which event handler must be called on the source to initiate a drag operation?** \`setOnDragDetected()\`  

**Q2: What is the purpose of** event.acceptTransferModes(TransferMode.MOVE) **in the**  setOnDragOver **handler?** 

It tells JavaFX that the target node is willing to accept the dragged data. Without it, the drop will  be rejected.  

**Q3: How do you extract the data from the dragged item when it is dropped?** 

By calling \`event.getDragboard()\` on the drop event, and then checking/extracting the content  (e.g., \`db.getString()\`).  

**Activity 4: Custom Events** 

**Purpose** 

Create your own custom event and fire it across your application. This is used to decouple  components that shouldn't know about each other. 

**Learning Outcome addressed:** \#4 

**Time:** 60 minutes 

 **Real-World Application**

Page **14** of **19**   
**E-Commerce:** A "Checkout Button" doesn't need to know about the "Email Service" or the  "Inventory Database". It simply fires an OrderPlacedEvent. The Email and Inventory classes  listen for that event and do their jobs independently. 

**Step 4.1: Create the Custom Event Class** 

Create LoginSuccessEvent.java: 

java 

package com.example; 

import javafx.event.Event; 

import javafx.event.EventType; 

public class LoginSuccessEvent extends Event { 

 public static final EventType\<LoginSuccessEvent\> LOGIN\_SUCCESS \=   new EventType\<\>(Event.ANY, "LOGIN\_SUCCESS"); 

 private final String username; 

 public LoginSuccessEvent(String username) { 

 super(LOGIN\_SUCCESS); 

 this.username \= username; 

 } 

 public String getUsername() { 

 return username; 

 }

Page **15** of **19**   
} 

**Step 4.2: Write the Main App** 

Create CustomEventDemo.java: 

java 

package com.example; 

import javafx.application.Application; 

import javafx.scene.Scene; 

import javafx.scene.control.Button; 

import javafx.scene.control.Label; 

import javafx.scene.control.TextField; 

import javafx.scene.layout.VBox; 

import javafx.stage.Stage; 

public class CustomEventDemo extends Application { 

 @Override 

 public void start(Stage primaryStage) { 

 TextField usernameField \= new TextField(); 

 usernameField.setPromptText("Enter username");   

 Button loginBtn \= new Button("Login"); 

 Label statusLabel \= new Label("Please log in."); 

 VBox root \= new VBox(15, usernameField, loginBtn, statusLabel);  root.setStyle("-fx-padding: 20; \-fx-alignment: center;");

Page **16** of **19**   
 *// \---- FIRING THE CUSTOM EVENT \----* 

 loginBtn.setOnAction(e \-\> { 

 String username \= usernameField.getText(); 

 if (\!username.isEmpty()) { 

 *// Fire the custom event on the root node\!* 

 root.fireEvent(new LoginSuccessEvent(username)); 

 } else { 

 statusLabel.setText("Username cannot be empty."); 

 } 

 }); 

 *// \---- LISTENING FOR THE CUSTOM EVENT \----* 

 root.addEventHandler(LoginSuccessEvent.LOGIN\_SUCCESS, event \-\> {  *// This handler is completely decoupled from the button logic\!* 

 System.out.println("Custom Event Received\! User logged in: " \+ event.getUsername());  statusLabel.setText("Welcome back, " \+ event.getUsername() \+ "\!");   

 *// Example of another action triggered by the event* 

 usernameField.clear(); 

 }); 

 Scene scene \= new Scene(root, 400, 300); 

 primaryStage.setTitle("Custom Event Demo"); 

 primaryStage.setScene(scene); 

 primaryStage.show();

Page **17** of **19**   
 } 

 public static void main(String\[\] args) { 

 launch(args); 

 } 

} 

**Step 4.3: Run and Test** 

1\. Run the app. 

2\. Type a name (e.g., "Alice") and click Login. 

3\. The status label updates to "Welcome back, Alice\!", and the console prints the custom  event log. 

4\. Notice how the button click *only* fires the event. The code that updates the label is  completely separate. 

**Step 4.4: Self-Check Questions** 

**Q1: Why would you use a Custom Event instead of just calling a method directly?** 

To achieve loose coupling,: The component that fires the event doesn't need to know which  classes are listening or how many there are. This makes the code highly reusable and easier to  maintain.  

**Q2: Which class must your custom event extend?** 

\`javafx.event.Event\`  

**Q3: How do you define the type of your custom event?** 

By creating a static \`EventType\` field (e.g., \`new EventType\<\>(Event.ANY, "LOGIN\_SUCCESS")\`).  

**Push Your Work to GitHub** 

1\. Open the terminal in your project root: C:\\Users\\RUICT\\Desktop\\projects\\event-driven programming-java. 

2\. Stage, commit, and push your changes:

Page **18** of **19**   
powershell 

git add . 

git commit \-m "Complete Week 7: JavaFX Event Handling 

\- Activity 1: Event Propagation (Filters vs Handlers) 

\- Activity 2: Mouse and Key Events 

\- Activity 3: Drag and Drop 

\- Activity 4: Custom Events" 

git push origin main 

**Final Self-Assessment** 

**Learning Outcome 1: Handle action, key, and mouse events** 

• □ I can use setOnMouseMoved to track X/Y coordinates. 

• □ I can use setOnKeyPressed and KeyCode to move an object. 

**Learning Outcome 2: Differentiate between Event Filters and Handlers** 

• □ I understand that Filters capture events top-down, while Handlers bubble events  bottom-up. 

• □ I know how to use e.consume() to stop propagation. 

**Learning Outcome 3: Implement Drag and Drop** 

• □ I can create a drag source using setOnDragDetected and a Dragboard. • □ I can create a drop target using setOnDragOver and setOnDragDropped. **Learning Outcome 4: Create and fire custom events** 

• □ I can create a custom Event class and EventType. 

• □ I can fire the event using .fireEvent() and listen for it using .addEventHandler(). **If you can check all of these — you have fully achieved the Week 7 learning outcomes\!**

Page **19** of **19** 
**Week 9 Self-Paced Guide: Asynchronous Programming in JavaFX How to Use This Handbook** 

Work through each activity in order. Read the **Real-World Application** to  understand *why* you are learning this, type the code exactly as shown (read the  comments\!), run it, and answer the **Self-Check Questions**. 

**Estimated time:** 3–4 hours 

**Working directory:** Create your own directory 

**What You Will Achieve By The End:** 

1\. Identify operations that freeze the UI. 

2\. Implement background tasks using Task and Service. 

3\. Safely update the UI from background threads. 

4\. Provide real-time progress feedback to the user. 

**Setup: Create Your Project** 

1\. Open **VS Code**. 

2\. Create a new **JavaFX Maven project** named week09-async-javafx in your  event-driven-programming-java folder. 

3\. Ensure your pom.xml has the JavaFX dependencies. 

4\. Create a package named com.example. 

**Activity 1: The Freeze (LO1 \- Identifying Blocking Operations)  Real-World Application** 

**Windows File Explorer:** Have you ever tried to copy a massive folder, and  Windows Explorer completely freezes, turns white, and says "Not Responding"? 

Page 1 of 17   
That is because the developer put the file-copying code on the UI thread. We  must never do this. 

**Step 1.1: Write the Code** 

Create FreezeDemo.java: 

java 

package com.example; 

import javafx.application.Application; 

import javafx.scene.Scene; 

import javafx.scene.control.Button; 

import javafx.scene.control.Label; 

import javafx.scene.layout.VBox; 

import javafx.stage.Stage; 

public class FreezeDemo extends Application { 

 @Override 

 public void start(Stage primaryStage) { 

 Label statusLabel \= new Label("Ready"); 

 Button freezeButton \= new Button("Freeze the UI\!"); 

 *// THIS IS THE BAD WAY.* 

 *// We are calling Thread.sleep() directly inside the Event Handler.*

Page 2 of 17   
 *// This blocks the JavaFX Application Thread\!* 

 freezeButton.setOnAction(e \-\> { 

 statusLabel.setText("Processing..."); 

 try { 

 Thread.sleep(5000); *// Simulate a 5-second database query or download*  } catch (InterruptedException ex) { 

 ex.printStackTrace(); 

 } 

 statusLabel.setText("Done\!"); 

 }); 

 VBox root \= new VBox(20, freezeButton, statusLabel); 

 root.setStyle("-fx-padding: 20; \-fx-alignment: center;"); 

 Scene scene \= new Scene(root, 400, 200); 

 primaryStage.setTitle("The Freeze Demo"); 

 primaryStage.setScene(scene); 

 primaryStage.show(); 

 } 

 public static void main(String\[\] args) { 

 launch(args); 

 }

Page 3 of 17   
} 

**Step 1.2: Run and Observe** 

1\. Run the app and click the button. 

2\. **Try to move the window** — you can't\! 

3\. **Try to resize it** — no response\! 

4\. After 5 seconds, it finally updates to "Done\!". 

 **Self-Check Questions** 

**Q1: Why did the window freeze for 5 seconds?** 

Answer:-Because \`Thread.sleep(5000)\` was executed on the JavaFX Application  Thread. This prevented the thread from processing any new events (like moving  the window or repainting the screen) for 5 seconds.  

**Activity 2: The Fix (LO2, LO3 \- Task & Safe UI Updates)** 

 **Real-World Application** 

**Web Browsers:** When you load a heavy webpage, the browser tab stays  responsive. You can still click other tabs or cancel the load. This is because the  browser uses background threads to download the page. 

**Step 2.1: Write the Code** 

Create TaskDemo.java: 

java 

package com.example; 

import javafx.application.Application; 

import javafx.application.Platform;

Page 4 of 17   
import javafx.concurrent.Task; 

import javafx.scene.Scene; 

import javafx.scene.control.Button; 

import javafx.scene.control.Label; 

import javafx.scene.layout.VBox; 

import javafx.stage.Stage; 

public class TaskDemo extends Application { 

 @Override 

 public void start(Stage primaryStage) { 

 Label statusLabel \= new Label("Ready"); 

 Button startButton \= new Button("Start Background Task"); 

 startButton.setOnAction(e \-\> { 

 statusLabel.setText("Processing..."); 

 startButton.setEnabled(false); *// Prevent double-clicking* 

 *// 1\. Create a Background Task* 

 Task\<Void\> task \= new Task\<\>() { 

 @Override 

 protected Void call() throws Exception { 

 *// THIS RUNS IN THE BACKGROUND (Off the UI Thread)*

Page 5 of 17   
 Thread.sleep(5000);  

 return null; 

 } 

 }; 

 *// 2\. What to do when the task succeeds* 

 task.setOnSucceeded(event \-\> { 

 *// CRITICAL RULE: You cannot update the UI from the background  thread.* 

 *// You must use Platform.runLater() to send the update to the UI thread.*  Platform.runLater(() \-\> { 

 statusLabel.setText("Task Complete\!"); 

 startButton.setEnabled(true); 

 }); 

 }); 

 *// 3\. Start the task on a new Thread* 

 new Thread(task).start(); 

 }); 

 VBox root \= new VBox(20, startButton, statusLabel); 

 root.setStyle("-fx-padding: 20; \-fx-alignment: center;");

Page 6 of 17   
 Scene scene \= new Scene(root, 400, 200); 

 primaryStage.setTitle("Task Demo \- The Fix"); 

 primaryStage.setScene(scene); 

 primaryStage.show(); 

 } 

 public static void main(String\[\] args) { 

 launch(args); 

 } 

} 

**Step 2.2: Run and Test** 

1\. Run the app and click the button. 

2\. Notice that the button grays out and says "Processing...". 

3\. **Now try to move or resize the window** — It moves perfectly smoothly\! The  UI is not frozen. 

4\. After 5 seconds, the label updates to "Task Complete\!". 

 **Self-Check Questions** 

**Q1: Why did we use** Platform.runLater() **inside** setOnSucceeded**?** 

**Answer**: Because \`setOnSucceeded\` executes on the background thread. To safely  change the text of \`statusLabel\`, we must send that code back to the JavaFX UI  thread using \`Platform.runLater()\`. 

Page 7 of 17   
**Activity 3: Live Progress (LO4 \- Providing Feedback)** 

 **Real-World Application** 

**Downloading a File:** Imagine downloading a 1GB file without a progress bar. You  would think the app is frozen. Progress bars (determinate) tell the user exactly  how much work is left. (Indeterminate progress bars tell the user "I'm working,  but I don't know how long it will take"). 

**Step 3.1: Write the Code** 

Create ProgressDemo.java: 

java 

package com.example; 

import javafx.application.Application; 

import javafx.concurrent.Task; 

import javafx.geometry.Insets; 

import javafx.scene.Scene; 

import javafx.scene.control.Button; 

import javafx.scene.control.Label; 

import javafx.scene.control.ProgressBar; 

import javafx.scene.layout.VBox; 

import javafx.stage.Stage; 

public class ProgressDemo extends Application {

Page 8 of 17   
 @Override 

 public void start(Stage primaryStage) { 

 Label statusLabel \= new Label("Ready"); 

 ProgressBar progressBar \= new ProgressBar(0); *// Starts at 0%*  progressBar.setPrefWidth(300); 


 Button startButton \= new Button("Download File"); 

 startButton.setOnAction(e \-\> { 

 startButton.setEnabled(false); 

 *// 1\. Create the Task* 

 Task\<Void\> downloadTask \= new Task\<\>() { 

 @Override 

 protected Void call() throws Exception { 

 *// Simulate downloading 100 chunks of data*  for (int i \= 0; i \<= 100; i++) { 

 Thread.sleep(50); *// Simulate network delay*   

 *// 2\. Safely update the task's built-in properties  // These automatically sync with the UI thread\!*  updateProgress(i, 100); 

 updateMessage("Downloading... " \+ i \+ "%");

Page 9 of 17   
 } 

 return null; 

 } 

 }; 

 *// 3\. Bind the UI components to the task's properties* 

 *// This is MUCH cleaner than using Platform.runLater() for progress\!*  progressBar.progressProperty().bind(downloadTask.progressProperty());  statusLabel.textProperty().bind(downloadTask.messageProperty()); 

 *// 4\. When done, clean up* 

 downloadTask.setOnSucceeded(event \-\> { 

 startButton.setEnabled(true); 

 *// Unbind the label so we can set a final message manually*  statusLabel.textProperty().unbind();  

 statusLabel.setText("Download Complete\!"); 

 }); 

 *// 5\. Start the task* 

 new Thread(downloadTask).start(); 

 }); 

 VBox root \= new VBox(20, startButton, progressBar, statusLabel);

Page 10 of 17   
 root.setPadding(new Insets(20)); 

 root.setStyle("-fx-alignment: center;"); 

 Scene scene \= new Scene(root, 400, 200); 

 primaryStage.setTitle("Progress Bar Demo"); 

 primaryStage.setScene(scene); 

 primaryStage.show(); 

 } 

 public static void main(String\[\] args) { 

 launch(args); 

 } 

} 

**Step 3.2: Run and Test** 

1\. Run the app and click "Download File". 

2\. Watch the progress bar fill up smoothly from 0% to 100%, and the label  update in real-time. 

3\. Notice how we used updateProgress() and updateMessage() instead of  Platform.runLater(). This is the best practice for tasks\! 

 **Self-Check Questions** 

**Q1: What is the difference between** updateProgress() **and** Platform.runLater()**?** 

Answer:-updateProgress()\` is a built-in Task method that safely batches and sends  progress updates to the UI. \`Platform.runLater()\` sends an arbitrary block of code  to the UI thread. For progress, \`updateProgress()\` is preferred because it's  optimized. 

Page 11 of 17   
**Q2: Why did we call** statusLabel.textProperty().unbind() **at the end?** 

Answer:- Because the label was bound to the task's message. If we didn't unbind  it, trying to call \`statusLabel.setText("Download Complete\!")\` would throw an  exception (you cannot set a bound value manually).  

**Activity 4: The Service Class (Capstone)** 

 **Real-World Application** 

**Refreshing a Dashboard:** If you have a "Sync" button on a dashboard, users might  click it multiple times. A Task can only be run once. If you want a background job  that can be started, cancelled, and restarted (like refreshing data), you must use a  Service. 

**Step 4.1: Write the Code** 

Create ServiceDemo.java: 

java 

package com.example; 

import javafx.application.Application; 

import javafx.concurrent.Service; 

import javafx.concurrent.Task; 

import javafx.geometry.Insets; 

import javafx.scene.Scene; 

import javafx.scene.control.Button; 

import javafx.scene.control.Label; 

import javafx.scene.control.ProgressIndicator; 

import javafx.scene.layout.HBox;

Page 12 of 17   
import javafx.scene.layout.VBox; 

import javafx.stage.Stage; 

public class ServiceDemo extends Application { 

 *// 1\. Create a Service that can be restarted*  private class DataSyncService extends Service\<String\> {  @Override 

 protected Task\<String\> createTask() { 

 return new Task\<\>() { 

 @Override 

 protected String call() throws Exception {  *// Simulate a long download/query*  for (int i \= 0; i \<= 10; i++) { 

 if (isCancelled()) break; *// Allow cancellation*  Thread.sleep(500);  

 } 

 return "Data Synced Successfully\!";  } 

 }; 

 } 

 }

Page 13 of 17   
 @Override 

 public void start(Stage primaryStage) { 

 Label statusLabel \= new Label("Ready"); 


 *// Indeterminate progress spinner (infinite loading circle)*  ProgressIndicator spinner \= new ProgressIndicator();  spinner.setVisible(false);  

 Button syncBtn \= new Button("Sync Data");  Button cancelBtn \= new Button("Cancel");  cancelBtn.setDisable(true); 

 DataSyncService service \= new DataSyncService(); 

 syncBtn.setOnAction(e \-\> { 

 service.restart(); *// Starts or restarts the service*  statusLabel.setText("Syncing..."); 

 spinner.setVisible(true); 

 syncBtn.setDisable(true); 

 cancelBtn.setDisable(false); 

 });

Page 14 of 17   
 cancelBtn.setOnAction(e \-\> { 

 service.cancel(); *// Abort the task* 

 statusLabel.setText("Sync Cancelled."); 

 spinner.setVisible(false); 

 syncBtn.setDisable(false); 

 cancelBtn.setDisable(true); 

 }); 

 *// Handle Success* 

 service.setOnSucceeded(e \-\> { 

 statusLabel.setText(service.getValue()); *// Get returned result*  spinner.setVisible(false); 

 syncBtn.setDisable(false); 

 cancelBtn.setDisable(true); 

 }); 

 *// Handle Failure* 

 service.setOnFailed(e \-\> { 

 statusLabel.setText("Sync Failed: " \+ service.getException().getMessage());  spinner.setVisible(false); 

 syncBtn.setDisable(false); 

 cancelBtn.setDisable(true); 

 });

Page 15 of 17   
 HBox buttons \= new HBox(10, syncBtn, cancelBtn); 

 VBox root \= new VBox(20, buttons, spinner, statusLabel);  root.setPadding(new Insets(20)); 

 root.setStyle("-fx-alignment: center;"); 

 Scene scene \= new Scene(root, 400, 250); 

 primaryStage.setTitle("Service Demo"); 

 primaryStage.setScene(scene); 

 primaryStage.show(); 

 } 

 public static void main(String\[\] args) { 

 launch(args); 

 } 

} 

**Step 4.2: Run and Test** 

1\. Run the app. 

2\. Click **"Sync Data"**. The spinner appears, and the buttons toggle. 

3\. Click **"Cancel"**. The spinner disappears, and the status says "Sync  Cancelled." 

4\. Click **"Sync Data"** again. The service **restarts** without issues. Let it finish,  and see the success message.

Page 16 of 17   
 **Self-Check Questions** 

**Q1: What is the main difference between** Task **and** Service**?** 

**Answer**: \- A \`Task\` can only be executed once. A \`Service\` wraps a Task and can be  started, restarted, and cancelled multiple times, making it perfect for reusable  operations like "Refresh Data". \</details\> 

**Q2: How do you check inside the** call() **method if the user clicked "Cancel"?** 

**Answer**:-By calling \`isCancelled()\` inside the loop, so the background thread can  break out of the loop and stop working immediately.  

**Push Your Work to GitHub** 

1\. Open your terminal in the project root. 

2\. Save your work: 

powershell 

git add . 

git commit \-m "Complete Week 9: Asynchronous Programming in JavaFX" git push origin main 

**Congratulations\! You now know how to build modern, responsive applications  that handle heavy operations without ever freezing the user interface\!**

Page 17 of 17 
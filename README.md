# Event-Driven Programming in Java

Hands-on practical activities, guides, and implementations for Event-Driven Programming (EDP) and GUI development in Java.

## Documentation Directory

All comprehensive self-paced student handbooks are organized in the [`documentation/`](documentation/) directory:
- [Week 1 Self-Paced Practical Activities](documentation/Week%201%20Self%20paced%20Activity.md)
- [Week 3 Self-Paced GUI Application Guide](documentation/Week%203%20Self%20paced%20Activity.md)

---

## Repository Contents

### [Week 1: Fundamentals of Event-Driven Programming](Week1_Activity/)
- **Student Handbook:** [documentation/Week 1 Self paced Activity.md](documentation/Week%201%20Self%20paced%20Activity.md)
- **Activity 1:** Scavenger Hunt & Real-World EDP Systems Analysis ([SelfAssessment.md](Week1_Activity/SelfAssessment.md))
- **Activity 2:** Physical Simulation of the Event Queue and Event Loop ([SelfAssessment.md](Week1_Activity/SelfAssessment.md))
- **Activity 3:** Procedural vs. Event-Driven Side-by-Side Comparison
  - Procedural Implementation: [`ProceduralCalculator.java`](Week1_Activity/ProceduralCalculator.java)
  - Event-Driven Swing Implementation: [`EventDrivenCalculator.java`](Week1_Activity/EventDrivenCalculator.java)
- **Activity 4: Bug Fix Challenge** ([BugFixChallenge/](Week1_Activity/BugFixChallenge/))
  - Fixed EDT blocking, missing listener, background thread UI mutation, and memory leak bugs ([BugFixes.md](Week1_Activity/BugFixChallenge/BugFixes.md))
- **Activity 5: Design Your Own Event-Driven System**
  - Design Document: [`SystemDesign.md`](Week1_Activity/SystemDesign.md)
  - Working Swing Prototype: [`SmartHomePrototype.java`](Week1_Activity/SmartHomePrototype.java)
- **Activity 6:** Final Self-Assessment and Summary ([SelfAssessment.md](Week1_Activity/SelfAssessment.md))

---

### [Week 2: The Observer Pattern](Week02-observer-pattern/)
- Custom observer implementation and weather station demo
- Legacy `java.util.Observer` comparison
- Self-assessment: [SelfAssessment.md](Week02-observer-pattern/SelfAssessment.md)

---

### [Week 3: Building GUI Applications with Swing](week03-swing-gui/)
- **Student Handbook:** [documentation/Week 3 Self paced Activity.md](documentation/Week%203%20Self%20paced%20Activity.md)
- Layout managers, component arrangements, and Swing Event Dispatch Thread (EDT) safety
- Self-assessment: [SelfAssessment.md](week03-swing-gui/SelfAssessment.md)
# Bug Fix Documentation

## Bug 1: Blocking the EDT
- **Symptom:** UI freezes when button clicked
- **Cause:** Long operation runs on EDT
- **Fix:** Run operation in background thread, update UI on EDT
- **Code Change:** Moved the five-second operation into `new Thread(() -> {...}).start()` and used `SwingUtilities.invokeLater()` to update the status label.

## Bug 2: Missing Listener
- **Symptom:** Button click does nothing
- **Cause:** No `ActionListener` registered
- **Fix:** Added `ActionListener` to increment button
- **Code Change:** Registered an action listener on the increment button so clicking it increments the displayed value.

## Bug 3: Wrong Thread
- **Symptom:** Erratic behavior, potential crashes
- **Cause:** GUI updated from background thread
- **Fix:** Used `SwingUtilities.invokeLater()` for GUI updates
- **Code Change:** Wrapped the clock label update in `SwingUtilities.invokeLater()` so it executes on the EDT.

## Bug 4: Memory Leak
- **Symptom:** Memory usage grows over time
- **Cause:** References kept in list after removal from UI
- **Fix:** Clear the list when removing buttons
- **Code Change:** Added `createdButtons.clear()` to the “Remove All” action after removing the buttons from the panel.
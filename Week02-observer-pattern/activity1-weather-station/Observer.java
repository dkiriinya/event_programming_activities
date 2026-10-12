// ============================================================
// OBSERVER INTERFACE
// ============================================================
// Any class that wants to be notified of changes in the
// Subject must implement this interface.
// This is the "contract" that guarantees every observer has
// an update() method the Subject can call.
public interface Observer {

    // Called by the Subject whenever its state changes.
    // The three parameters carry the updated data (push model).
    void update(float temperature, float humidity, float pressure);
}

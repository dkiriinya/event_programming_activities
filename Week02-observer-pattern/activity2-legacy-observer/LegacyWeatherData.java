import java.util.Observable;

// ============================================================
// LEGACY SUBJECT using java.util.Observable (Deprecated!)
// ============================================================
// Notice: we must EXTEND Observable. This is a key limitation
// because Java only allows single inheritance.
@SuppressWarnings("deprecation")
public class LegacyWeatherData extends Observable {

    private float temperature;
    private float humidity;

    public void setMeasurements(float temperature, float humidity) {
        this.temperature = temperature;
        this.humidity = humidity;

        // Required before notifying (marks the object as changed)
        setChanged();

        // Notify all observers (no data passed — pull model)
        notifyObservers();
    }

    public float getTemperature() { return temperature; }
    public float getHumidity() { return humidity; }
}

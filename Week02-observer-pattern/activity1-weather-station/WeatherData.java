// ============================================================
// CONCRETE SUBJECT — WeatherData
// ============================================================
import java.util.ArrayList;
import java.util.List;

public class WeatherData implements Subject {

    // The list of registered observers.
    private List<Observer> observers;

    // The state being observed.
    private float temperature;
    private float humidity;
    private float pressure;

    public WeatherData() {
        // Initialize the list so we can add observers.
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
        System.out.println("[WeatherData] Observer registered: "
                + o.getClass().getSimpleName());
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
        System.out.println("[WeatherData] Observer removed: "
                + o.getClass().getSimpleName());
    }

    @Override
    public void notifyObservers() {
        // Loop through every observer and call update().
        for (Observer o : observers) {
            o.update(temperature, humidity, pressure);
        }
    }

    // Called whenever new measurements arrive.
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        System.out.println("\n[WeatherData] New measurements received!");
        notifyObservers(); // Fire the notification!
    }
}

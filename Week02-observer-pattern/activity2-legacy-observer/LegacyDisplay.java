import java.util.Observable;
import java.util.Observer;

// ============================================================
// LEGACY OBSERVER using java.util.Observer (Deprecated!)
// ============================================================
// The update() method takes the Observable and an Object arg.
@SuppressWarnings("deprecation")
public class LegacyDisplay implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof LegacyWeatherData) {
            LegacyWeatherData data = (LegacyWeatherData) o;
            System.out.println("Legacy Display -> Temp: "
                    + data.getTemperature() + "°C, Humidity: "
                    + data.getHumidity() + "%");
        }
    }
}

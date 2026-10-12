public class ForecastDisplay implements Observer {
    private float lastPressure = 1013.0f;

    @Override
    public void update(float temperature, float humidity, float pressure) {
        String forecast;
        if (pressure > lastPressure) {
            forecast = "Improving weather on the way!";
        } else if (pressure < lastPressure) {
            forecast = "Cooler, rainy weather coming.";
        } else {
            forecast = "More of the same.";
        }
        System.out.println("  [Forecast] " + forecast);
        lastPressure = pressure;
    }
}

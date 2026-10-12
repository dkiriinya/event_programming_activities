public class LegacyMain {
    public static void main(String[] args) {

        LegacyWeatherData data = new LegacyWeatherData();
        LegacyDisplay display = new LegacyDisplay();

        data.addObserver(display);

        data.setMeasurements(26.0f, 60.0f);
        data.setMeasurements(30.0f, 75.0f);
    }
}

public class WeatherStationMain {
    public static void main(String[] args) {

        // 1. Create the SUBJECT (WeatherData)
        WeatherData weatherData = new WeatherData();

        // 2. Create the OBSERVERS
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay();
        StatisticsDisplay statsDisplay = new StatisticsDisplay();
        ForecastDisplay forecastDisplay = new ForecastDisplay();

        // 3. REGISTER the observers with the subject
        weatherData.registerObserver(currentDisplay);
        weatherData.registerObserver(statsDisplay);
        weatherData.registerObserver(forecastDisplay);

        // 4. Simulate changing weather data
        weatherData.setMeasurements(25.0f, 65.0f, 1013.0f);
        weatherData.setMeasurements(28.0f, 70.0f, 1012.0f);
        weatherData.setMeasurements(22.0f, 60.0f, 1015.0f);

        // 5. Remove one observer and test again
        System.out.println("\n--- Removing ForecastDisplay ---");
        weatherData.removeObserver(forecastDisplay);
        weatherData.setMeasurements(20.0f, 55.0f, 1018.0f);
    }
}

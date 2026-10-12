public class StatisticsDisplay implements Observer {
    private float maxTemp = Float.MIN_VALUE;
    private float minTemp = Float.MAX_VALUE;

    @Override
    public void update(float temperature, float humidity, float pressure) {
        maxTemp = Math.max(maxTemp, temperature);
        minTemp = Math.min(minTemp, temperature);
        System.out.println("  [Statistics] Max: " + maxTemp
                + "°C, Min: " + minTemp + "°C");
    }
}

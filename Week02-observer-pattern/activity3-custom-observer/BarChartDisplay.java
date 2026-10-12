public class BarChartDisplay implements DataObserver {
    @Override
    public void onDataChanged(int newValue) {
        System.out.println("Bar chart height: " + newValue);
    }
}

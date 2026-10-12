public class RefactoredMain {
    public static void main(String[] args) {

        DataModel model = new DataModel();

        // Register observers
        model.addObserver(new LabelDisplay());
        model.addObserver(new BarChartDisplay());
        model.addObserver(new TextLogger());

        // Change data — all three observers react
        model.setValue(10);
        model.setValue(20);
        model.setValue(30);
    }
}

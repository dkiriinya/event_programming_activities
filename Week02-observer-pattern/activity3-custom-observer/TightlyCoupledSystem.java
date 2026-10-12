// ============================================================
// BEFORE REFACTORING: A tightly-coupled system
// ============================================================
// Notice how DataModel directly calls methods on every
// display class. If we add a new display, we must modify
// DataModel — that is TIGHT COUPLING. This is bad design.
public class TightlyCoupledSystem {

    // The DataModel directly references every display.
    static class DataModel {
        public void setValue(int value) {
            // Directly calling the UI updates — tightly coupled!
            new LabelDisplay().showValue(value);
            new BarChartDisplay().drawBar(value);
        }
    }

    static class LabelDisplay {
        public void showValue(int value) {
            System.out.println("Label shows: " + value);
        }
    }

    static class BarChartDisplay {
        public void drawBar(int value) {
            System.out.println("Bar chart height: " + value);
        }
    }

    public static void main(String[] args) {
        DataModel model = new DataModel();
        model.setValue(10);
        model.setValue(20);
    }
}

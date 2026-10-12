// Our custom Subject interface
public interface DataSubject {
    void addObserver(DataObserver o);
    void removeObserver(DataObserver o);
    void notifyObservers();
}

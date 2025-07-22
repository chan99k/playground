package chan99k.observer.custom;

public interface MySubject {
	void registerObserver(MyObserver observer);

	void removeObserver(MyObserver observer);

	void notifyObservers();
}

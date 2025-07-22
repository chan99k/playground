package chan99k.observer.func;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Subject {
	private final List<Consumer<String>> listeners = new ArrayList<>();

	public void addAnimal(String name) {
		System.out.println(name + " -> 동물원에 입장하였습니다.");
		notifyListeners(name);
	}

	public void registerListener(Consumer<String> listener) {
		listeners.add(listener);
	}

	public void withdrawListener(Consumer<String> listener) {
		listeners.remove(listener);
	}

	private void notifyListeners(String evtData) {
		listeners.forEach(listener -> listener.accept(evtData));
	}
}

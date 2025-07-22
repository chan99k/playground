package chan99k.observer.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class NewsChannel implements PropertyChangeListener {
	private String news;

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("프로퍼티 변경 감지: " + evt.getPropertyName());
		this.news = evt.getNewValue().toString();
		display();
	}

	public void display() {
		System.out.println("채널 수신 뉴스: " + news);
	}
}

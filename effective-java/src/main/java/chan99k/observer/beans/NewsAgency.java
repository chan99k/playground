package chan99k.observer.beans;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class NewsAgency {
	private String news;
	private final PropertyChangeSupport support;

	public NewsAgency() {
		this.support = new PropertyChangeSupport(this);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	/**
	 * "news" 프로퍼티가 oldNews 에서 newNews로 변경되었음을 통지
	 */
	public void setNews(String newNews) {
		String oldNews = this.news;
		this.news = newNews;
		support.firePropertyChange("news", oldNews, newNews);
	}
}

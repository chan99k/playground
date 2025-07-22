package chan99k.observer.beans;

public class ObserverDemo {
	public static void main(String[] args) {
		NewsAgency newsAgency = new NewsAgency();
		NewsChannel newsChannel = new NewsChannel();

		newsAgency.addPropertyChangeListener(newsChannel);

		newsAgency.setNews("속보: 자바 9에서 Observable이 deprecated 되었습니다.");
		newsAgency.setNews("어서 다들 도망치세요.");
	}
}

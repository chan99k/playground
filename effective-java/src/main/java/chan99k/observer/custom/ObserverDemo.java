package chan99k.observer.custom;

public class ObserverDemo {
	public static void main(String[] args) {
		OnlineShoppingMall mall = new OnlineShoppingMall();
		Customer c1 = new Customer("김철수");
		Customer c2 = new Customer("김영희");

		mall.registerObserver(c1);
		// mall.registerObserver(c1); // 동일한 사용자인지 확인을 해주지 않으면 알림이 중복 발생된다.
		mall.registerObserver(c2);

		mall.releaseNewProduct("Apple watch 10");

		mall.removeObserver(c2);

		mall.releaseNewProduct("Mac mini");
	}
}

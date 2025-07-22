package chan99k.observer.custom;

import java.util.ArrayList;
import java.util.List;

public class OnlineShoppingMall implements MySubject {
	private final List<MyObserver> subscriberList = new ArrayList<>();
	private String newProduct;

	@Override
	public void registerObserver(MyObserver observer) {
		subscriberList.add(observer);
	}

	@Override
	public void removeObserver(MyObserver observer) {
		subscriberList.remove(observer);
	}

	@Override
	public void notifyObservers() {
		subscriberList.forEach(sb -> sb.update(newProduct));
	}

	public void releaseNewProduct(String productName) {
		this.newProduct = productName;
		System.out.println("\n" + productName + "신상 공개!!");
		notifyObservers();
	}
}

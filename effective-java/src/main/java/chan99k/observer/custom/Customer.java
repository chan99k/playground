package chan99k.observer.custom;

public class Customer implements MyObserver {
	private final String name;

	public Customer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void update(String productName) {
		System.out.println(name + " 고객님," + productName + " 상품이 새로 입고되었습니다. ");
	}
}

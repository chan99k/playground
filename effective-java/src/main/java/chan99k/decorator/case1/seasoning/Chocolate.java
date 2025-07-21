package chan99k.decorator.case1.seasoning;

import chan99k.decorator.case1.base.IceCream;

public class Chocolate extends Decorator {
	IceCream iceCream;

	public Chocolate(IceCream iceCream) {
		this.iceCream = iceCream;
	}

	@Override
	public String getDescription() {
		return iceCream.getDescription() + " + 초콜릿 토핑 ";
	}

	@Override
	public int price() {
		return iceCream.price() + 1000;
	}
}

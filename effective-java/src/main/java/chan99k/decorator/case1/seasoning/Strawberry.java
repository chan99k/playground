package chan99k.decorator.case1.seasoning;

import chan99k.decorator.case1.base.IceCream;

public class Strawberry extends Decorator {
	IceCream iceCream;

	public Strawberry(IceCream iceCream) {
		this.iceCream = iceCream;
	}

	@Override
	public String getDescription() {
		return iceCream.getDescription() + " + 딸기 토핑";
	}

	@Override
	public int price() {
		return iceCream.price() + 500;
	}
}

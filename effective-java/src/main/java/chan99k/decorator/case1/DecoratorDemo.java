package chan99k.decorator.case1;

import chan99k.decorator.case1.base.IceCream;
import chan99k.decorator.case1.base.IceCreamCake;
import chan99k.decorator.case1.base.IceCreamCone;
import chan99k.decorator.case1.base.IcePop;
import chan99k.decorator.case1.seasoning.Chocolate;
import chan99k.decorator.case1.seasoning.Strawberry;

/**
 * 데코레이터 패턴
 * 객체의 결합을 통해 기능을 동적으로 유연하게 확장하는 패턴
 * 기본 기능에 추가할 수 있는 기능의 종류가
 */
public class DecoratorDemo {
	public static void main(String[] args) {
		IceCream iceCream1 = new IceCreamCone();
		System.out.println(iceCream1.getDescription() + " cost: " + iceCream1.price());
		// 기본 객체에 데코레이터를 첨가할 수 있다.
		IceCream iceCream2 = new IceCreamCake();
		iceCream2 = new Strawberry(iceCream2);
		System.out.println(iceCream2.getDescription() + " cost: " + iceCream2.price());

		IceCream iceCream3 = new IcePop();
		iceCream3 = new Chocolate(new Strawberry(iceCream3));
		System.out.println(iceCream3.getDescription() + "cost: " + iceCream3.price());

	}

}

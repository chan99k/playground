package chan99k.decorator.case2;

import chan99k.decorator.case2.base.AmericanStudent;
import chan99k.decorator.case2.seasoning.Art;
import chan99k.decorator.case2.seasoning.Science;

public class DecoratorDemo {
	public static void main(String[] args) {
		AmericanStudent g1 = new AmericanStudent();
		System.out.println("g1 = " + g1.getDescription());

		Science g2 = new Science(g1);
		System.out.println("g2 = " + g2.getDescription());

		Art g3 = new Art(g2);
		System.out.println("art = " + g3.getDescription());
	}
}

package chan99k.decorator.case1.seasoning;

import chan99k.decorator.case1.base.IceCream;

/**
 * 데코레이터 클래스의 형식은 그 클래스가 감싸고 있는 클래스의 형식을 반영한다.
 * ceCream 객체가 들어갈 자리에 들어갈 수 있어야 하므로 IceCream 클래스를 상속한다.
 */
public abstract class Decorator extends IceCream {
	/**
	 * 설명을 추가해야 하므로 서브클래스에서 꼭 구현하도록 강제한다.
	 * @return 설명
	 */
	public abstract String getDescription();
}

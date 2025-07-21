package chan99k.decorator.case1.base;

public class IceCreamCone extends IceCream {
	@Override
	public int price() {
		return 1200;
	}

	public IceCreamCone() {
		description = "아이스크림(콘)";
	}
}

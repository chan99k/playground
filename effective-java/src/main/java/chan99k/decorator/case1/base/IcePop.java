package chan99k.decorator.case1.base;

public class IcePop extends IceCream{
	@Override
	public int price() {
		return 1000;
	}

	public IcePop() {
		this.description = "아이스크림(바:막대)";
	}
}

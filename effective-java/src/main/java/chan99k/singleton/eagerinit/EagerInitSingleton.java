package chan99k.singleton.eagerinit;

public class EagerInitSingleton {
	/**
	 * 클래스 로드 시점에 만들어진다.
	 * 당장 사용하지 않을 수도 있는데 클래스 로드 시점에 미리 만들어 놓기 때문에 "메모리 누수 지점"이 될 수 있음
	 */
	private static final EagerInitSingleton INSTANCE = new EagerInitSingleton("products");
	private String name;

	private EagerInitSingleton(String name) {
		try {
			Thread.sleep(100);
			this.name = name;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public static EagerInitSingleton getInstance(String name) {
		return INSTANCE;
	}
}
